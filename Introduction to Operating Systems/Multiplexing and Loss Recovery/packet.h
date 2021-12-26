#ifndef PACKET_H
#define PACKET_H

//lager pakke struct
typedef struct packet{
  unsigned char flag;
  unsigned char pktseq;
  unsigned char ackseq;
  unsigned char unassigned;
  int senderid;
  int recvid;
  int metadata;
  char *payload;
} packet;


#endif
