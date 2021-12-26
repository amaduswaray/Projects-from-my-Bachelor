from regneklynge import Regneklynge
from racks import Rack
from node import Node

class Datasenter:
    def __init__(self):
        self._datanett = {}


    def lesInnRegneklynge(self, filnavn):
        regneklynge = open(filnavn + ".txt")
        innhold = regneklynge.readlines()
        maxnoder = int(innhold[0])
        self._datanett[filnavn] = Regneklynge(maxnoder)

        for x in range(1, len(innhold)):
            noden = innhold[x].split()
            antall = int(noden[0])
            minne = int(noden[1])
            prosessorer = int(noden[2])
            for i in range(antall):
                nyNode = Node(minne,prosessorer)
                self._datanett[filnavn].settInnNode(nyNode)



    def skrivUtAlleRegneklynger(self):
        for i in self._datanett:
            print("Antall prosessorer i",i,":",str(self._datanett[i].antProsessorer()))
            print("Noder med minst 32GB minne i",i,":",str(self._datanett[i].noderMedNokMinne(32)))
            print("Noder med minst 64GB minne i",i,":",str(self._datanett[i].noderMedNokMinne(64)))
            print("Noder med minst 128GB minne i",i,":",str(self._datanett[i].noderMedNokMinne(128)))
            print("Totale antall racks i",i,":",str(self._datanett[i].antRacks()))



    def skrivUtRegneklynge(self, navn):
        print("Antall prosessorer i",navn,":",str(self._datanett[navn].antProsessorer()))
        print("Noder med minst 32GB minne i",navn,":",str(self._datanett[navn].noderMedNokMinne(32)))
        print("Noder med minst 64GB minne i",navn,":",str(self._datanett[navn].noderMedNokMinne(64)))
        print("Noder med minst 128GB minne i",navn,":",str(self._datanett[navn].noderMedNokMinne(128)))
        print("Totale antall racks i",navn,":",str(self._datanett[navn].antRacks()))
