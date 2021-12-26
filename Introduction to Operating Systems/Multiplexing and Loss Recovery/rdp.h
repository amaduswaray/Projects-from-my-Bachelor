#ifndef RDP_H
#define RDP_H

#include "packet.h"
//definerer content, som er størrelsen av innhold per payload
#define CONTENT 999
//defunerer BUFFSIZE som er størrelsen buffere som skal være pakker burde ha
//er bare størrelsen av pakke + content
#define BUFFSIZE sizeof(struct packet)+CONTENT

//lager struct kobling, som inneholder hver variabel som jeg synes er viktig
//står i readme
typedef struct kobling{
  int antMottat;
  int klientID;
  int serverID;
  int closed;
  int timeout;
  struct sockaddr_in klientAddr;
  packet* lastRecievied;
  struct timeval tiden;
}kobling;

void initialize(int antall);

void initializeRec(int antall);

void settKlienter(int antall);

void settPakker(int antall);

int hentAntKobl();

kobling** hentForbindelser();

void freeKobling(kobling* k);

void freeAllKobling();

kobling* hentKobling(int kid);

int hentRec();

packet** hentRecv();

int isConnected(packet* pakke);

void check_error(int res, char *msg);

void rdp_connect(struct sockaddr_in adresse, int fd, int kid);

struct kobling* rdp_accept(packet* pakke, struct sockaddr_in adresse, int fd);

int rdp_write(kobling* retur, packet* sendes, int fd);

int rdp_read(int fd, packet** p, int tall, int before);

void rdp_terminate(kobling* k);


#endif
