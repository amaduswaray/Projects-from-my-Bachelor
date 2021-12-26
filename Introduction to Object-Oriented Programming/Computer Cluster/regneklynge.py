from racks import Rack

class Regneklynge:

    def __init__(self, noderPerRack):
        self._noderPerRack = noderPerRack
        self._regneklynge = []

    def settInnNode(self, node):
        telling = len(self._regneklynge)-1
        if len(self._regneklynge) == 0:
            nyrack = Rack()
            nyrack.settInn(node)
            self._regneklynge.append(nyrack)
        else:
            tellendeRack = self._regneklynge[telling]
            if tellendeRack.getAntNoder() == self._noderPerRack:
                nyereRack = Rack()
                nyereRack.settInn(node)
                self._regneklynge.append(nyereRack)
            else:
                tellendeRack.settInn(node)

    def antProsessorer(self):
        antall = 0
        for rack in self._regneklynge:
            antall += rack.antProsessorer()
        return antall

    def noderMedNokMinne(self, paakrevdMinne):
        antall = 0
        for rack in self._regneklynge:
            antall += rack.noderMedNokMinne(paakrevdMinne)
        return antall

    def antRacks(self):
        antall = len(self._regneklynge)
        return antall
        
    def daniel(self):
    
