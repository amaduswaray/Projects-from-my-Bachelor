#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <sys/select.h>
#include "packet.h"
#include "packetfunc.h"
#include "rdp.h"


//funskjon som lager pakker
packet* createPacket(unsigned char flag, unsigned char pktseq, unsigned char ackseq, unsigned char unassigned, int senderid, int recvid, int metadata, char *payload){

  //allokerer for pakke.
  //nok minne for selve strukten, og til char pekeren. som bare er metadata
  packet *pakke = malloc(sizeof(struct packet)+metadata);//allokerer nok minne til packet, og payload pekeren
  if(pakke == NULL){
    fprintf(stderr, "malloc failed. possibly out of memory\n" );
    exit(EXIT_FAILURE);
  }

  //setter alle verdiene
  pakke->flag = flag;
  pakke->pktseq = pktseq;
  pakke->ackseq = ackseq;
  pakke->unassigned = unassigned;
  pakke->senderid = senderid;
  pakke->recvid = recvid;
  pakke->metadata = metadata;

  //allokerer nok minne til payload
  pakke->payload = malloc(metadata);
  if(pakke->payload == NULL){
    fprintf(stderr, "malloc failed. possibly out of memory\n" );
    exit(EXIT_FAILURE);
  }

  memset(pakke->payload, 0, metadata);
  memcpy(pakke->payload, payload, metadata);

  return pakke;
}

//lager funksjon som gjør pakke til en char peker
char *serialize(packet *pakke){
  int offset, tmp;

  char *buffer = malloc(BUFFSIZE + pakke->metadata);//Må allokere for packet structen, og selve payload pekeren(siden den ikke blir sendt)
  memset(buffer, 0, (BUFFSIZE + pakke->metadata));


  offset = 0;
  //i leser det som ligger på variabel adressene, og størrelsen av de, til bufferet
  memcpy((buffer+offset), &pakke->flag, sizeof(unsigned char));
  offset += sizeof(unsigned char);
  memcpy((buffer+offset), &pakke->pktseq, sizeof(unsigned char));
  offset += sizeof(unsigned char);
  memcpy((buffer+offset), &pakke->ackseq, sizeof(unsigned char));
  offset += sizeof(unsigned char);
  memcpy((buffer+offset), &pakke->unassigned, sizeof(unsigned char));
  offset += sizeof(unsigned char);


  tmp = htonl(pakke->senderid);
  memcpy((buffer+offset), &tmp, sizeof(int));
  offset += sizeof(int);

  tmp = htonl(pakke->recvid);
  memcpy((buffer+offset), &tmp, sizeof(int));
  offset += sizeof(int);

  tmp = htonl(pakke->metadata);
  memcpy((buffer+offset), &tmp, sizeof(int));
  offset += sizeof(int);


  memcpy((buffer+offset), pakke->payload, pakke->metadata);


  return buffer;
}

//lager funksjon som gjør char peker til en pokke
packet* de_serialize(char *buffer){
  packet *pakke = malloc(BUFFSIZE);
  if(pakke == NULL){
    fprintf(stderr, "malloc failed. possibly out of memory\n" );
    exit(EXIT_FAILURE);
  }

  memset(pakke, 0, BUFFSIZE);


  if(!pakke){
    return NULL;
  }

  int offset = 0;
  //setter verdiene til packet structenq
  //leser lengen av hver variabel i bufferet, og setter det i adressen til variabelen til pakken
  memcpy(&pakke->flag, (buffer+offset), sizeof(unsigned char));
  offset += sizeof(unsigned char);
  memcpy(&pakke->pktseq, (buffer+offset), sizeof(unsigned char));
  offset += sizeof(unsigned char);
  memcpy(&pakke->ackseq, (buffer+offset), sizeof(unsigned char));
  offset += sizeof(unsigned char);
  memcpy(&pakke->unassigned, (buffer+offset), sizeof(unsigned char));
  offset += sizeof(unsigned char);

  pakke->senderid = ntohl(*((int*)&buffer[offset]));
  offset += sizeof(int);
  pakke->recvid = ntohl(*((int*)&buffer[offset]));
  offset += sizeof(int);
  pakke->metadata = ntohl(*((int*)&buffer[offset]));
  offset += sizeof(int);


  pakke->payload = malloc(pakke->metadata);
  if(pakke->payload == NULL){
    fprintf(stderr, "malloc failed. possibly out of memory\n" );
    exit(EXIT_FAILURE);
  }

  memcpy(pakke->payload, buffer+offset, pakke->metadata);

  return pakke;
}

//metode som freer pakker
void freePacket(packet *pakke){//freer pakken og payload pekeren
  free(pakke->payload);
  free(pakke);
}
