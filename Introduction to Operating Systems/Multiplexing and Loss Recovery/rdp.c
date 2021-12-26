#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <sys/select.h>
#include "send_packet.h"
#include "rdp.h"
#include "packet.h"
#include "packetfunc.h"

//definerer antall koblinger som serveren skal få
int antKoblinger = 0;
kobling** forbindelser;

//lager styr på antall  nye connections som kommer
int newConnection = 0;
int antallKlienter;

//holder styr på antall pakker som
int antPakker;

void settKlienter(int antall){
  antallKlienter = antall;
}

void settPakker(int antall){
  antPakker = antall;
}

//allocerer dobbelpeker som skal ha koblingene
void initialize(int antall){
  forbindelser = malloc(sizeof(kobling)*antall);
  //sjekker at malloc fungerte. gjør dette hver gang jeg mallocer for structs
  if(forbindelser == NULL){
    fprintf(stderr, "malloc failed. possibly out of memory\n" );
    exit(EXIT_FAILURE);
  }
}


int hentAntKobl(){
  return antKoblinger;
}

kobling** hentForbindelser(){
  return forbindelser;
}

void freeKobling(kobling* k){
  freePacket(k->lastRecievied);
  free(k);
}

void freeAllKobling(){
  for(int i = 0; i < antKoblinger; i++){
    freeKobling(forbindelser[i]);
  }

  free(forbindelser);
  forbindelser = NULL;
  return;
}


//metode som sjekker om id til en request pakke allerede er koblet til server
int isConnected(packet* pakke){
  int i;
  for(i=0; i<antKoblinger; i++){
    if(pakke->senderid == forbindelser[i]->klientID){
      return 1;
    }
  }

  return 0;
}

//check error funksjon fra cbra, som sjekker funksjoner vi kaller på fungerer
void check_error(int res, char *msg){
  if(res == -1){
    perror(msg);

    exit(EXIT_FAILURE);
  }
}


//metode for å hente en enkel kobling fra lista
kobling* hentKobling(int kid){
  for(int i = 0; i < antKoblinger; i++){
    if(forbindelser[i]->klientID == kid){
      return forbindelser[i];
    }
  }
  return NULL;
}


//connect metode som sender request til server
void rdp_connect(struct sockaddr_in adresse, int fd, int kid){
  int rc;
  packet* request = createPacket(0x01,0,0,0,0,kid,0,"");
  char* requestSer = serialize(request);

  printf("Connecting to server\n");
  rc = send_packet(fd, requestSer, sizeof(struct packet), 0, (struct sockaddr*)&adresse, sizeof(struct sockaddr_in));
  check_error(rc, "send_packet");
  //øker new connection ved hver connect kall
  newConnection++;

  freePacket(request);
  free(requestSer);
}


//rdp accept metode som skal akseptere eller rejecte inkommende requests
struct kobling* rdp_accept(packet* request, struct sockaddr_in adresse, int fd){
  int rc;


  if(request->flag != 0x01){
    return NULL;
  }

//aksepterer connections når de allerede ikke er tilkoblet
    if(!isConnected(request) && antKoblinger != antallKlienter){

      //lager en kobling, allokerer nok til selve kobling structenm
      //og not til packet pekeren den har
      kobling *k = malloc(sizeof(kobling) + sizeof(packet));
      if(k == NULL){
        fprintf(stderr, "malloc failed. possibly out of memory\n" );
        exit(EXIT_FAILURE);
      }

      k->antMottat = 0;
      k->serverID = request->senderid;
      k->klientID = request->recvid;
      k->klientAddr = adresse;
      k->closed = 0;
      k->timeout = 0;
      fprintf(stdout, "CONNECTED <%d> <%d> \n", request->senderid, request->recvid);

      freePacket(request);

      //lager pakke som sier at forespørsel ble akseptert
      //metadata er satt til antall pakker som skal bli sendt
      //dette er for å kunne allokere en annen struct i klienten(kommentar i client)
      packet* accepted = createPacket(0x10,0,0,0,0,k->klientID,antPakker,"");
      char *acceptedSer = serialize(accepted);

      rc = send_packet(fd, acceptedSer, (sizeof(struct packet) + accepted->metadata),0, (struct sockaddr*)&adresse, sizeof(struct sockaddr_in));
      check_error(rc, "send_packet");

      freePacket(accepted);
      free(acceptedSer);
      forbindelser[antKoblinger] = k;
      antKoblinger++;

      return k;

    }
    else if(antKoblinger == antallKlienter){
      //husk å endre metadata på melding
      fprintf(stdout, "DISCONNECTED <%d> <%d> Message: Too many clients \n", request->senderid, request->recvid);

      //lager melding til klientID
      //er årsaken for disconnect
      char *msg = "Too many clients";

      //metadata i denne pakken må være lengden til meldingen, og ha 1 ekstra byte for 0 byte
      packet *notAccepted = createPacket(0x20,0,0,0,request->senderid,request->recvid,strlen(msg)+1,msg);
      char *notAcceptedSer = serialize(notAccepted);

      rc = send_packet(fd, notAcceptedSer, (sizeof(struct packet) + notAccepted->metadata),0, (struct sockaddr*)&adresse, sizeof(struct sockaddr_in));
      check_error(rc, "sendto");

      freePacket(request);
      freePacket(notAccepted);
      free(notAcceptedSer);
    }
    else{

      fprintf(stdout, "DISCONNECTED <%d> <%d> Message: Already connected \n", request->senderid, request->recvid);
        char *msg = "Already connected";
        packet *notAccepted = createPacket(0x20,0,0,0,request->senderid,request->recvid,strlen(msg)+1,msg);
        char *notAcceptedSer = serialize(notAccepted);

        rc = send_packet(fd, notAcceptedSer, (sizeof(struct packet) + notAccepted->metadata),0, (struct sockaddr*)&adresse, sizeof(struct sockaddr_in));
        check_error(rc, "sendto");

        freePacket(request);
        freePacket(notAccepted);
        free(notAcceptedSer);

    }
    //returnerer null dersom vi ikke fikk laget et kobling
    return NULL;

}


//lager write metode, som skal sende pakker
int rdp_write(kobling* retur, packet* sendes, int fd){

  //hvis en kobling er i timeout, så skal metoden returnere 0
  if(retur->timeout){
    return 0;
  }

  int rc;
  char* serSend = serialize(sendes);
  struct timeval noTid;

  printf("Sender pakke nr %d med pktseq %d til ID: %d\n", retur->antMottat, sendes->pktseq, retur->klientID);
  rc = send_packet(fd, serSend, BUFFSIZE/*(sizeof(struct packet)+sendes->metadata)*/, 0, (struct sockaddr*)&retur->klientAddr, sizeof(struct sockaddr_in));
  check_error(rc, "sendto");
  printf("Har sendt pakke\n");

  //detter kobling sin tid til tiden den ble sendt. bruker denne tiden for å sammenligne i server
  gettimeofday(&noTid, NULL);
  retur->tiden = noTid;
  free(serSend);
  return 1;
}


//lager read metode som mottar packer og sender ack
int rdp_read(int fd, packet** p, int tall, int before){
  //tok inn et tall som holder styre på hvilken pakke som ble sendt før
  int rc, bytes;
  struct sockaddr_in origin;
  socklen_t addr_len = sizeof(struct sockaddr_in);
  char buffer[BUFFSIZE];

  //reseter buffer i tilfelle det er bytes der
  memset(buffer, 0, BUFFSIZE);

  //resetter adressen i tilfelle det er bytes der
  memset(&origin, 0, sizeof(origin));
  rc = recvfrom(fd, buffer, BUFFSIZE, 0, (struct sockaddr*)&origin, &addr_len);
  check_error(rc, "recvfrom");


  packet* pakke = de_serialize(buffer);
  if(pakke == NULL){
    perror("Pakke ikke mallocet riktig");
  }



    //dersom metadata er 0, så er det siste pakken som server sender
    //sender dermed siste ack, men endrer returverdi på funksjonen
    if(pakke->metadata == 0){
      printf("Vi kom til siste\n");
      packet* ack = createPacket(0x08, (before+1)%2, (before+1)%2, 0, 0, pakke->recvid, 0, "");
      char* serAck = serialize(ack);
      printf("ID %d Sender Ack %d\n", pakke->recvid, (before+1)%2);
      //rc = sendto(fd, serAck, (sizeof(packet)+ack->metadata), 0, (struct sockaddr*)&origin, sizeof(struct sockaddr_in));
      rc = send_packet(fd, serAck, BUFFSIZE/*(sizeof(struct packet)+ack->metadata)*/, 0, (struct sockaddr*)&origin, sizeof(struct sockaddr_in));
      check_error(rc, "sendto");

      freePacket(ack);
      free(serAck);
      freePacket(pakke);
      return 0;
    }

  //dersom pakken som ble sendt er den samme, så skal samme ack sendes igjen
  if(pakke->pktseq == before){

    packet* ack = createPacket(0x08, before%2, before%2, 0, 0, pakke->recvid, 0, "");
    char* serAck = serialize(ack);
    printf("Ack %d ble ikke motatt, sender igjen\n", ack->pktseq);

    rc = send_packet(fd, serAck, BUFFSIZE, 0, (struct sockaddr*)&origin, sizeof(struct sockaddr_in));
    check_error(rc, "sendto");

    freePacket(ack);
    freePacket(pakke);
    free(serAck);
    return -1;
  }


//sender ack til server
  packet* ack = createPacket(0x08, (before+1)%2, (before+1)%2, 0, 0, pakke->recvid, 0, "");
  char* serAck = serialize(ack);
  printf("ID %d Sender Ack %d\n", pakke->recvid, (before+1)%2);
  //rc = sendto(fd, serAck, (sizeof(packet)+ack->metadata), 0, (struct sockaddr*)&origin, sizeof(struct sockaddr_in));
  rc = send_packet(fd, serAck, BUFFSIZE/*(sizeof(struct packet)+ack->metadata)*/, 0, (struct sockaddr*)&origin, sizeof(struct sockaddr_in));
  check_error(rc, "sendto");

  freePacket(ack);
  free(serAck);

  p[tall] = pakke;
  bytes = pakke->metadata;

  //freePacket(pakke);
  return bytes;

}

//termienerings metode som gjør en kobling stengt
void rdp_terminate(kobling* k){
  printf("Ferdig med pakke med ID %d\n", k->klientID);
  k->closed = 1;
}
