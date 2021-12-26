from node import Node

class Rack:

    def __init__(self):
        self._liste = []

    def settInn(self, node):
        self._liste.append(node)

    def getAntNoder(self):
        antall = len(self._liste)
        return antall

    def antProsessorer(self):
        antall = 0
        for node in self._liste:
            antall += node.antProsessorer()
        return antall


    def noderMedNokMinne(self, paakrevdMinne):
        godkjent = 0
        for node in self._liste:
            if node.nokMinne(paakrevdMinne):
                godkjent +=1
        return godkjent
