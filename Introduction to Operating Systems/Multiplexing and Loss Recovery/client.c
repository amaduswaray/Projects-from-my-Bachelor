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
#include <time.h>
#include "packet.h"
#include "send_packet.h"
#include "packetfunc.h"
#include "rdp.h"

//definerer liste som inneholder mottate pakker
int antRec = 0;
packet** recievedPackets;

//funksjon som allokerer minne for dobelpekeren
void initializeRec(int antall){
  recievedPackets = malloc((sizeof(struct packet))*CONTENT*antall);
  if(recievedPackets == NULL){
    fprintf(stderr, "malloc failed. possibly out of memory\n" );
    exit(EXIT_FAILURE);
  }
}


//metode som freer lista
void freeRecPac(){
  free(recievedPackets);
  recievedPackets = NULL;
  return;
}


//metode som skriver til fil
void sendTilFil(int cid){
  int wc;
  char filename[100];
  sprintf(filename, "%d", cid);

//sjekker om en annen fil i directory har samme navn
  if(access(filename, F_OK) == 0){
    perror("This file exists");
    exit(EXIT_FAILURE);
  }

  FILE *fil1 = fopen(filename, "w+");

  for(int i = 0; i < antRec; i++){
    packet* pakke = recievedPackets[i];
    wc = fwrite(pakke->payload, pakke->metadata - sizeof(struct packet), 1, fil1);
    check_error(wc, "fwrite");
    freePacket(pakke);
  }

  fclose(fil1);

}



int main(int argc, char *argv[]){
//skriver ut kommandolinjene dersom bruker ikke skriver de korrekte
  if(argc < 4){
    fprintf(stderr, "Commandolines: %s  <IP-Adress> <Port> <Loss-Probability> \n", argv[0]);
    return EXIT_SUCCESS;
  }

  int fd, rc;
  struct sockaddr_in destination, origin;
  memset(&destination, 0, sizeof(destination));


  char *ip_addr = argv[1];
  int udp_port = atoi(argv[2]);
  float lossProb = strtof(argv[3], NULL);
  char buffer[CONTENT];
  socklen_t addr_len;
  addr_len = sizeof(struct sockaddr_in);

  set_loss_probability(lossProb);

  fd = socket(AF_INET, SOCK_DGRAM, 0);
  check_error(fd, "socket");

  destination.sin_family = AF_INET;
  destination.sin_port = htons(udp_port);
  rc = inet_pton(AF_INET, ip_addr, &destination.sin_addr.s_addr);
  check_error(rc, "inet_pton");

  //sjekker om det er en gyldig ip adresse
  if(!rc){
    fprintf(stderr, "Invalid IP: %s\n", ip_addr);
    return EXIT_FAILURE;
  }

//gir klienten en tilfeldig ID
  srand(clock());
  unsigned int kid = rand();

  //kobler klient til server
  rdp_connect(destination, fd, kid);


  //venter 1 sek på å motta pakke fra server
  //hvis ikke, terminerer jeg klienten
  fd_set fdSet;
  struct timeval tv;
  FD_ZERO(&fdSet);
  FD_SET(fd, &fdSet);
  tv.tv_sec = 1;
  tv.tv_usec = 0;
  rc = select(FD_SETSIZE, &fdSet, NULL, NULL, &tv);
  check_error(rc, "select");

  if(rc == 0){
    fprintf(stdout, "Server didn't accept connection in time\n");
    return EXIT_FAILURE;
  }

  //mottar forespørsel svar fra server
  rc = recvfrom(fd, buffer, CONTENT, 0, (struct sockaddr*)&origin, &addr_len);
  check_error(rc, "recvfrom");

  packet *verdict = de_serialize(buffer);
  if(verdict == NULL){
    perror("Feil i buffer fra Server");
    return EXIT_FAILURE;
  }

  //skriver ut DISCONNECTED hvis det har flag 0x20
  if(verdict->flag == 0x20){
    fprintf(stdout, "DISCONNECTED <%d> <%d> Message: %s \n", verdict->senderid, verdict->recvid, verdict->payload);
    freePacket(verdict);
    //freeRecPac();

    return EXIT_FAILURE;
  }

  else{
    fprintf(stdout, "CONNECTED <%d> <%d> \n", verdict->senderid, verdict->recvid);
    initializeRec(verdict->metadata);
    freePacket(verdict);

    //holder styr på hvilken pakke som kom før
    //og hvilken pakke som kom nå
    int before = -1;
    int x = 0%2;
    while(1){
      //read returnerer størrelsen av pakken som ble lest
      int size = rdp_read(fd, recievedPackets, antRec, before);

      //dersom størrelsen er 0, så vet vi at det er slutten av sendingen
      if(size == 0){
        printf("Sender terminasjon Ack\n");

        //vi sender terminasjons ack
        packet* term = createPacket(0x02, x, x, 0,0,kid,0,"");
        char* termAck = serialize(term);
        rc = send_packet(fd, termAck, BUFFSIZE, 0, (struct sockaddr*)&origin, sizeof(struct sockaddr_in));

        free(termAck);
        freePacket(term);
        break;
      }

      else if(size == -1){
        //får vi -1 som retur verdi, så har klienten mottat samme pakke
        //rdp read sender ack, så clienten vil bare gå til neste iterasjon
        continue;
      }

      else{
        //dersom pakken som  ble lest inneholder noe, så kan vi endre x og before
        before = x;
        //ta x ++%2
        x = (x+1)%2;
        antRec++;
        //øker antall mottatte pakker
      }

    }

    //til slutt så skriver vi til fil
    sendTilFil(kid);

  }

  //freer struct og lukker socket
  freeRecPac();
  close(fd);

  return EXIT_SUCCESS;
}
