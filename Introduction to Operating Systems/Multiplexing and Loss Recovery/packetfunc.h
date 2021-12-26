#ifndef PACKETFUNC_H
#define PACKETFUNC_H

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


void printPacket(packet *pakke);

packet* createPacket(unsigned char flag, unsigned char pktseq, unsigned char ackseq, unsigned char unassigned, int senderid, int recvid, int metadata, char *payload);

char *serialize(packet *pakke);

packet* de_serialize(char *buffer/*, unsigned int *size*/);

void sendPakke(int fd, packet *pakke, struct sockaddr_in dest);

void freePacket(packet *pakke);

#endif
