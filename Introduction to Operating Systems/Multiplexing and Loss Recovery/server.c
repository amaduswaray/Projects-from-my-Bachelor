#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <sys/select.h>
#include <sys/stat.h>
#include "packet.h"
#include "send_packet.h"
#include "packetfunc.h"
#include "rdp.h"



//definerer en liste som skal inneholde pakker med payloadene som skal bli sendt
packet **payloads;
int teller = 0;

void freePayloads(){
  teller++;
  for(int i = 0; i < teller; i++){
    freePacket(payloads[i]);
  }

  free(payloads);
  payloads = NULL;
  return;
}

//metode som deler opp fil til 99 bytes biter, og legger de i payloads lista
void delFil(char *filnavn){

    int fd, rc;
    struct stat filen;
    char buffer[CONTENT];
    memset(buffer, 0, CONTENT);

    FILE *fil = fopen(filnavn, "rb");
    if (fil==NULL) {
        perror("Failed to open file");
        exit(EXIT_FAILURE);
    }

    fd = fileno(fil);
    fstat(fd, &filen);

    //henter størrelsen på filen, og deler de opp i payload størrelsen
    //for å finne antall pakker som skal bli sent
    int numb = filen.st_size/CONTENT;

    payloads = malloc((numb+1)*BUFFSIZE);
    if(payloads == NULL){
      fprintf(stderr, "malloc failed. possibly out of memory\n" );
      exit(EXIT_FAILURE);
    }

    memset(buffer, 0, CONTENT);

    while ((rc=fread(&buffer, sizeof(char), CONTENT, fil))) {

        //lager pakker som inneholder payloadene
        packet *pakke = malloc(sizeof(struct packet) + rc);
        if(pakke == NULL){
          fprintf(stderr, "malloc failed. possibly out of memory\n" );
          exit(EXIT_FAILURE);
        }

        memset(pakke, 0, sizeof(struct packet) + rc);


        pakke->flag = 0x04;
        pakke->pktseq = teller%2;
        pakke->ackseq = teller%2;
        pakke->unassigned = 0;
        pakke->senderid = 0;
        pakke->recvid = 0;
        pakke->metadata = sizeof(struct packet) + rc;

        pakke->payload = malloc(pakke->metadata);
        if(pakke->payload == NULL){
          fprintf(stderr, "malloc failed. possibly out of memory\n" );
          exit(EXIT_FAILURE);
        }

        memset(pakke->payload, 0, pakke->metadata);

        memcpy(pakke->payload, buffer, rc);


        payloads[teller] = pakke;
        teller++;
        memset(buffer, 0, CONTENT);
    }

    fclose(fil);


    //lager siste pakken som signaliserer slutten
    payloads[teller] = createPacket(0,(teller)%2,(teller)%2,0,0,0,0,"");


  }

//metode som sjekker om en pakke har flagg 0x01
int isRequest(packet* pakke){
  if(pakke->flag == 0x01){
    return 1;
  }
  return 0;
}

//metode som sjekker om en pakke har flagg 0x02
int isTermination(packet* pakke){
  if(pakke->flag == 0x02){
    return 1;
  }
  return 0;
}


//metode som starter hoved loopen
void executeLoop(int fd, int antFil){
    fd_set fdSet;
    char buffer[BUFFSIZE];
    int rc;
    struct sockaddr_in origin;
    socklen_t addr_len = sizeof(struct sockaddr_in);
    struct timeval tv;


    //variabel som holder styr på antall koblinger som har blitt terminert
    int term = 0;


    while(1){
      printf("\n");
      printf("\n");
      printf("\n");
      printf("Iterasjon:\n");

      //lager socket set får å bruke select
      FD_ZERO(&fdSet);
      FD_SET(fd, &fdSet);

      //setter en tid for at socketen skal bli varslet
      //dersom den tiden går ut, så betyr det at alle koblingene er timet ut
      tv.tv_sec = 1;
      tv.tv_usec = 0;
      rc = select(FD_SETSIZE, &fdSet, NULL, NULL, &tv);
      check_error(rc, "select");

      //dersom tiden går ut, så skal vi sende pakkene som gikk tapt
      if(rc == 0){


        printf("Sender alle pakker til klientene som ikke mottok ACK\n");
        kobling** notAcket = hentForbindelser();
        if(notAcket == NULL){
          perror("Koblingene har ikke blitt inisjialisert");
        }

        int nas = hentAntKobl();

        //sender de tapte pakkene til klientene
        for(int i=0; i< nas; i++){

          if(!notAcket[i]->closed){
            notAcket[i]->timeout = 0;
            printf("\n");
            printf("Fikk ikke ack fra ID %d. Time out. Sender pakke igjen\n", notAcket[i]->klientID);
            printf("Dette er sekvenstallet til pakken: %d\n", notAcket[i]->lastRecievied->pktseq);
            rdp_write(notAcket[i], notAcket[i]->lastRecievied, fd);
            printf("\n");
            continue;
          }

        }

      }




    //Sjekker om fd har fått en melding
    if(FD_ISSET(fd, &fdSet)){

      memset(&origin, 0, sizeof(origin));

      //mottar pakken
      rc = recvfrom(fd, buffer, CONTENT, 0, (struct sockaddr*)&origin, &addr_len);
      check_error(rc, "recvfrom");

      packet* returned = de_serialize(buffer);
      if(returned == NULL){
        perror("Mottok ikke buffer fra klient");
        continue;
      }


      printf("Retur sin flag: %d \n", returned->flag);
      if(isRequest(returned)){
        //aksepterer client request
        printf("Kom inn hit\n");
        kobling* connection = rdp_accept(returned, origin, fd);
        if(connection == NULL){
          perror("Klient did not get a connection");
          continue;
        }

        //legger til i global connection lista i rdp accept funksjon
        packet* sendes = createPacket(0x04, connection->antMottat, connection->antMottat, 0, connection->serverID, connection->klientID, payloads[connection->antMottat]->metadata, payloads[connection->antMottat]->payload);
        rc = rdp_write(connection, sendes, fd);
        if(rc == 0){
          printf("Sender kobling til timeout\n");
          continue;
        }

        connection->lastRecievied = sendes;
      }

      else if(isTermination(returned)){
        //terminerer kobling
        kobling* k = hentKobling(returned->recvid);
        if(k == NULL){
          perror("Failed to get connection");
          exit(EXIT_FAILURE);
        }

        //øker antall terminerte for hver gang en kobling blir terminert
        rdp_terminate(k);
        term++;

        freePacket(returned);

        //Dersom antall terminerte er lik antall filer som skal bli sendt, så er serveren ferdig
        if(term == antFil){
          printf("Alle pakker har blitt sendt\n");
          freePayloads();
          freeAllKobling();
          break;
        }

        continue;
      }

      else{
        //dersom pakken man mottar ikke er request eller terminasjon, så er det en

        //henter en kobling, og sjekker at den eksisterer
        kobling* k = hentKobling(returned->recvid);
        if(k == NULL){
          perror("Failed to get connection");
          exit(EXIT_FAILURE);
        }

        //hvis klienten har mottat alle pakker, så sier jeg at vi mottok siste ack
        if(k->antMottat == teller){
          printf("Mottok siste ack fra ID %d\n", k->klientID);
          freePacket(returned);
          continue;
        }

        //lager nye timevals, for å sammeligne med tiden som ligger i klienten
        struct timeval startTid, endTid;
        gettimeofday(&startTid, NULL);
        timersub(&startTid, &k->tiden, &endTid);

        //dersom tiden i klienten og tiden nå har en større forskjell enn 100 ms
        // så setter vi koblingen i en timeout
        if(endTid.tv_usec <= 100000){
          k->antMottat++;
        }
        else{
          printf("Setter ID %d i timeout\n", k->klientID);
          k->timeout = 1;
        }

        //sender neste pakke
        packet* sendes = createPacket(0x04, payloads[k->antMottat]->pktseq, payloads[k->antMottat]->ackseq, 0, k->serverID, k->klientID, payloads[k->antMottat]->metadata, payloads[k->antMottat]->payload);
        rdp_write(k, sendes, fd);

        freePacket(k->lastRecievied);
        k->lastRecievied = sendes;

        freePacket(returned);

      }



    }

  }

}


int main(int argc, char *argv[]){
//printer kommandolinjene, dersom bruker ikke skriver de korrekte
  if(argc < 5){
    fprintf(stderr, "Commandolines: %s  <Port> <File> <N files> <Loss-Probability> \n", argv[0]);
    return EXIT_SUCCESS;
  }

  int fd, rc;
  struct sockaddr_in destination;
  int udp_port = atoi(argv[1]);
  char* filnavn = argv[2];
  int n_files = atoi(argv[3]);
  float lossProb = strtof(argv[4], NULL);


  fd = socket(AF_INET, SOCK_DGRAM, 0);
  check_error(fd, "socket");

  destination.sin_family = AF_INET;
  destination.sin_port = htons(udp_port);
  destination.sin_addr.s_addr = INADDR_ANY;

  //binder socket
  rc = bind(fd, (struct sockaddr*)&destination, sizeof(struct sockaddr_in));
  check_error(rc, "bind");

  //kaller på funksjonen som kjører server
  set_loss_probability(lossProb);
  delFil(filnavn);
  initialize(n_files);
  settKlienter(n_files);
  settPakker(teller);

  executeLoop(fd, n_files);

  //lukker socket
  close(fd);
  return EXIT_SUCCESS;
}
