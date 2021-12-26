CFLAGS = -g -std=gnu11 -Wall -Wextra
BIN = client server

all: client server


client: client.c packet.h send_packet.c send_packet.h packetfunc.c packetfunc.h rdp.c rdp.h
	gcc $(CFLAGS) client.c send_packet.c packetfunc.c rdp.c -o client

server: server.c packet.h send_packet.c send_packet.h packetfunc.c packetfunc.h rdp.c rdp.h
	gcc $(CFLAGS) server.c send_packet.c packetfunc.c rdp.c -o server

clean:
	rm -f client server
