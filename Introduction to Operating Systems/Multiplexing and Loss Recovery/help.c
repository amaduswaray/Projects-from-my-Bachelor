

//CLIENT.c



  //rc = recv(fd, buffer, 1000, 0);
  //check_error(rc, "recv");
  rc = recvfrom(fd, buffer, 1000, 0, (struct sockaddr*)&origin, &addr_len);
  check_error(rc, "recvfrom");


  packet *recieved = de_serialize(buffer);
  printPacket(recieved);







  //char* mld = "Daniel";

  //sender pakken tilbake

  char* sender = serialize(recieved);
  rc = sendto(fd, sender, (sizeof(packet)+recieved->metadata), 0, (struct sockaddr*)&origin, sizeof(struct sockaddr_in));
  check_error(rc, "sendto");

  //char buffer2[50];

  //mottar pakken igjen
  rc = recvfrom(fd, buffer, 1000, 0, (struct sockaddr*)&origin, &addr_len);
  check_error(rc, "recvfrom");

  recieved = de_serialize(buffer);
  printf("\n");
  printf("\n");
  printf("Andre gang\n");
  printPacket(recieved);

  //printf("Vi fikk meldingen: %s\n", buffer2);



//SERER.C


//sender pakke
  char *text = "overforingsjamenerw";
  packet *gjeldende = createPacket(1,0,0,0,33,34,strlen(text), text);
  char *sender = serialize(gjeldende);

  rc = sendto(fd, sender, (sizeof(packet)+gjeldende->metadata), 0, (struct sockaddr*)&destination, sizeof(struct sockaddr_in));
  check_error(rc, "sendto");



//mottar samme pakke tilbake fra client
  rc = recvfrom(fd, buffer, 1000, 0, (struct sockaddr*)&origin, &addr_len);
  check_error(rc, "recvfrom");

  gjeldende = de_serialize(buffer);
  printf("Pakken er her\n");
  printPacket(gjeldende);


//sender pakken tilbake igjen
  sender = serialize(gjeldende);

  rc = sendto(fd, sender, (sizeof(packet)+gjeldende->metadata), 0, (struct sockaddr*)&origin, sizeof(struct sockaddr_in));
  check_error(rc, "sendto");





  //hoved loopen
  FD_ZERO(&fdSet);
  FD_SET(fd, &fdSet);
  tv.tv_sec = 2;
  tv.tv_usec = 0;

  rc = select(FD_SETSIZE, &fdSet, NULL, NULL, &tv);
  check_error(rc, "select");

  if(rc == 0){
    kobling** connections = hentForbindelser();
    int storrelse = hentAntKobl();

    for(int i=0; i<storrelse; i++){
      if(!connections[i]->closed){
        //rdp_wait(connections[i], fd);
        //lag en funskjon rdp wait
        //den kaller på recv, og venter på ack i 100 ms
        //dersom tiden går ut, ikke øk antall mottatt

        //forventer en pakke regardless
        printf("\n");
        packet* pakke = createPacket(0x04, connections[i]->antMottat, connections[i]->antMottat, 0, connections[i]->serverID, connections[i]->klientID, payloads[connections[i]->antMottat]->metadata, payloads[connections[i]->antMottat]->payload);
        rdp_write(connections[i], pakke, fd);
        //printf("\n");
        rdp_wait(connections[i], fd);

      }
    }
  }

  if(FD_ISSET(fd, &fdSet)){
    rc = recvfrom(fd, buffer, 1000, 0, (struct sockaddr*)&origin, &addr_len);
    check_error(rc, "recvfrom");


    packet* returned = de_serialize(buffer);

    if(isTermination(returned)){
      rdp_close(returned->recvid);//lag rdp close metoden
      term++;

      if(term == antFil){
        printf("Its over\n");
        break;
      }
      continue;
    }

    kobling* con = rdp_accept(returned, origin, fd);
    if(con != NULL){
      //sender første pakken
      packet* first = createPacket(0x04, con->antMottat, con->antMottat, 0, con->serverID, con->klientID, payloads[con->antMottat]->metadata, payloads[con->antMottat]->payload);
      rdp_write(con, first, fd);
      rdp_wait(con, fd);

    }
    //Senne
    //hvis null, ikke send pakke 1

    kobling** connections = hentForbindelser();
    int storrelse = hentAntKobl();

    for(int i=0; i<storrelse; i++){
      if(!connections[i]->closed){
        //rdp_wait(connections[i], fd);
        //lag en funskjon rdp wait
        //den kaller på recv, og venter på ack i 100 ms
        //dersom tiden går ut, ikke øk antall mottatt

        //forventer en pakke regardless
        printf("\n");
        packet* pakke = createPacket(0x04, connections[i]->antMottat, connections[i]->antMottat, 0, connections[i]->serverID, connections[i]->klientID, payloads[connections[i]->antMottat]->metadata, payloads[connections[i]->antMottat]->payload);
        rdp_write(connections[i], pakke, fd);
        //printf("\n");
        rdp_wait(connections[i], fd);

      }
    }


  }
  continue;





//dette er listen kall på server
if(rdp_listen()){
  printf("New connection\n");
  kobling* connection = rdp_accept(fd);
  if(connection != NULL){
    packet* sendes = createPacket(0x04, connection->antMottat, connection->antMottat, 0, connection->serverID, connection->klientID, payloads[connection->antMottat]->metadata, payloads[connection->antMottat]->payload);
    connection->lastRecievied = sendes;
    lastConnection = connection;
    rdp_write(connection, sendes, fd);
    continue;
  }

}


//dette er send pakke til alle i serveren

kobling** k = hentForbindelser();
int nas = hentAntKobl();

for(int i=0; i< nas; i++){
  k[i]->antMottat++;

  packet* sendes = createPacket(0x04, k[i]->antMottat, k[i]->antMottat, 0, k[i]->serverID, k[i]->klientID, payloads[k[i]->antMottat]->metadata, payloads[k[i]->antMottat]->payload);
  rdp_write(k[i], sendes, fd);
  k[i]->lastRecievied = sendes;
  lastConnection = k[i];
}




// is acks

else if(isAck(returned)){
  printf("Vi fikk akk\n");
  kobling* k = hentKobling(returned->recvid);
  if(k == NULL){
    perror("Failed to get connection");
    exit(EXIT_FAILURE);
  }


  k->antMottat++;
}



      kobling** k = hentForbindelser();
      int nas = hentAntKobl();
      printf("Sender alle pakker til klientene\n");
      for(int i=0; i< nas; i++){
        if(!k[i]->timeout && k[i]->antMottat != teller){
          packet* sendes = createPacket(0x04, k[i]->antMottat, k[i]->antMottat, 0, k[i]->serverID, k[i]->klientID, payloads[k[i]->antMottat]->metadata, payloads[k[i]->antMottat]->payload);
          rdp_write(k[i], sendes, fd);
          //if(rdp_wait(k[i], fd) && k[i]->antMottat != teller){
          //  k[i]->antMottat++;
          //}
          k[i]->lastRecievied = sendes;
          lastConnection = k[i];
        }



//hovedloop

else{//send alle pakkene til klientene, vent 100 ms på ack, også gå videre
  //aksepterer ack fra klient, og sender pakke

  /*kobling* k = hentKobling(returned->recvid);
  if(k == NULL){
    perror("Failed to get connection");
    exit(EXIT_FAILURE);
  }*/


  //k->antMottat++;

  //send samme pakke igjen dersom en ack ikke kommer
  //ikke øk mottat når det er samme pakke som skal sendes

  //printf("Sender pakke nr %d til ID: %d\n", k->antMottat, k->klientID);

  //packet* sendes = createPacket(0x04, k->antMottat, k->antMottat, 0, k->serverID, k->klientID, payloads[k->antMottat]->metadata, payloads[k->antMottat]->payload);
  //rdp_write(k, sendes, fd);

  //k->lastRecievied = sendes;
  //lastConnection = k;

  //freePacket(sendes);

  //lag setning som fortsetter loopen dersom det kommer noe i socket
  kobling** k = hentForbindelser();
  int nas = hentAntKobl();
  printf("Sender alle pakker til klientene\n");



  for(int i=0; i< nas; i++){

    if(wc == 0){

      if(!k[i]->timeout && k[i]->antMottat != teller){
        packet* sendes = createPacket(0x04, k[i]->antMottat, k[i]->antMottat, 0, k[i]->serverID, k[i]->klientID, payloads[k[i]->antMottat]->metadata, payloads[k[i]->antMottat]->payload);
        rdp_write(k[i], sendes, fd);

        /*if(rdp_wait(k[i], fd) && k[i]->antMottat != teller){
          k[i]->antMottat++;
        }

        if(rdp_wait(k[i], fd) == -2){
          term ++;
        }*/

        k[i]->lastRecievied = sendes;
        lastConnection = k[i];
      }
      //k[i]->antMottat++;

    }
    continue;

  }


}




else if(isAck(returned)){
  printf("Vi fikk akk\n");
  kobling* k = hentKobling(returned->recvid);
  if(k == NULL){
    perror("Failed to get connection");
    exit(EXIT_FAILURE);
  }


  k->antMottat++;
}

kobling** k = hentForbindelser();
int nas = hentAntKobl();
printf("Sender alle pakker til klientene\n");



for(int i=0; i< nas; i++){

  wc = select(FD_SETSIZE, &fdSet, NULL, NULL, NULL);

  if(FD_ISSET(fd, &fdSet)){
    continue;
  }
  else{
    if(!k[i]->timeout && k[i]->antMottat != teller){
      packet* sendes = createPacket(0x04, k[i]->antMottat, k[i]->antMottat, 0, k[i]->serverID, k[i]->klientID, payloads[k[i]->antMottat]->metadata, payloads[k[i]->antMottat]->payload);
      printf("Sender pakke nå\n");
      rdp_write(k[i], sendes, fd);

      /*if(rdp_wait(k[i], fd) && k[i]->antMottat != teller){
        k[i]->antMottat++;
      }

      if(rdp_wait(k[i], fd) == -2){
        term ++;
      }*/

      k[i]->lastRecievied = sendes;
      lastConnection = k[i];
    }
    //k[i]->antMottat++;

  }


}



        //while løkke
        /*printf("Sender pakker til alle klienter wc\n");

        kobling** k = hentForbindelser();
        int nas = hentAntKobl();

        for(int i=0; i<nas; i++){

          if(!k[i]->closed){

            packet* sendes = createPacket(0x04, k[i]->antMottat, k[i]->antMottat, 0, k[i]->serverID, k[i]->klientID, payloads[k[i]->antMottat]->metadata, payloads[k[i]->antMottat]->payload);
            rdp_write(k[i], sendes, fd);
            k[i]->lastRecievied = sendes;

            if(rdp_wait(k[i], fd) && k[i]->antMottat != teller){
              k[i]->antMottat++;
            }

          }

        }
*/


//definerer størrelsen til innholdet i payloaden til hver pakke
//#define CONTENT 999

//definerer sorrelsen som hver buffer skal ha
//#define BUFFSIZE sizeof(struct packet)+CONTENT



        //if time forskjell på koblingen er mindre enn 100 ms, så skal ant mottat økes
        //initialize timeval i koblingen når du sender første pakken
        //når vi mottar ack, så henter vi en ny timeval
        //så gjør vi timesub for å sammenligne tid
        //hvis tiden er større enn 100000 milli sekunder, så skal antMottat ikke økes
        //i tilleg skal k-> timeout settes til 1


        //k->antMottat++;

        //send pakke til alle tilkoblet klienter
        //send samme pakke igjen dersom en ack ikke kommer
        //ikke øk mottat når det er samme pakke som skal sendes

        //printf("Sender pakke nr %d til ID: %d\n", k->antMottat, k->klientID);


        //sjekk om rdp er 0
        //hvis null, si koblingen ble timet out
