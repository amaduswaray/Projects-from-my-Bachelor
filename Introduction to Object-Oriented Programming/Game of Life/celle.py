#Lager klassen og konstroktoren. Status begynner som dod
class Celle:
    def __init__(self):
        self._status = "Doed"

#Lager str metode som sier hva som blir printet hvis denne klassen blir kalt paa
    def __str__(self):
        if self._status == "Levende":
            return "O"
        else:
            return "."
#Lager metode som setter status til dod
    def settDoed(self):
        self._status = "Doed"
        return self._status

#lager metode som setter status til levende
    def settLevende(self):
        self._status = "Levende"
        return self._status

#Skjekker hvis statusen er levende
    def erLevende(self):
        if self._status == "Levende":
            return True
        else:
            return False
#retunerer 0 eller . basert paa statusen til cellen
    def tegnerepresentasjon(self):
        if self.erLevende():
            return "O"
        else:
            return "."
