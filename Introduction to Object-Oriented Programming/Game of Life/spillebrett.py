#importerer random og celle
from random import randint
from celle import Celle

#Lager spillebrettet og konstroktoren
class Spillebrett:
    def __init__(self, rader, kolonner):
        self._rader = rader
        self._kolonner = kolonner
        self._rutenett = []
        for x in range(self._rader):
            self._rutenett.append([])
            for i in range(self._kolonner):
                self._rutenett[x].append(Celle())
#setter _generasjonsnummer til 0 og kaller paa generer, som randomizes rutenettet
        self._generasjonsnummer = 0
        self._generer()


#lager metoden som skal tegne brettet
    def tegnBrett(self):
        #begynner med aa lage 99 blanke linjer
        for x in range(99):
            print(" ")
            #Lager en nustet lokke som gaar gjennom objektene i listen og printer hver en av de.
        for x in self._rutenett:
            for i in x:
                if x.index(i) !=  len(x)-1:
                    print(i, end="")
                else:
                    print(i)
                    #printer _generasjonsnummer og antall kevende
        print("Generasjon:", self._generasjonsnummer)
        print("Antall levende:", self.finnAntallLevende())

#lager metoden oppdatering
    def oppdatering(self):
        levendetildod = []
        dodtillevende = []
#lager en nustet liste som gaar gjennom rutenettet
        for x in range(len(self._rutenett)):
            for i in range(len(self._rutenett[x])):
                #lager en variabel ilive som skal holde styr paa de naboene som er i live
                ilive = 0
                #kaller paaa finnNabo metoden
                finnUt = self.finnNabo(x,i)
                #gaar gjennom finn  nabo listen og skjekker de som er i  live
                #for hver celle som er i live blir det lagt til 1 i ilive variabelen
                for nabo in finnUt:
                    if nabo.erLevende():
                        ilive += 1
                        #deretter sjekker jeg hvis den bestemte cellen er levende. hvis ja saa skjekker den hvor mange naboer som lever
                        #er det mindre enn to ilive, eller mer enn 3 ilive saa legges den bestemte cellemn til lista levendetildod
                if self._rutenett[x][i].erLevende():
                    if ilive < 2:
                        levendetildod.append(self._rutenett[x][i])
                    elif ilive > 3:
                        levendetildod.append(self._rutenett[x][i])
                    #else:
                    #    self._rutenett[x][i].settLevende()
                    #hvid den bestemte cellen er dod, skjekker den om ilive er 3. da skal cellen legges til lista dodtillevende
                elif not self._rutenett[x][i].erLevende():
                    if ilive == 3:
                        dodtillevende.append(self._rutenett[x][i])
                        #gaar gjennom listene og setter de dod eller levende basert paa hvilke liste de er i.
        for levende in levendetildod:
            levende.settDoed()
        for dod in dodtillevende:
            dod.settLevende()
#oker generasjonsnummer for hver gang oppdateringblir kalt
        self._generasjonsnummer += 1

#lager metode som skal finne antall levende
#lager en teller med 0 i verdi, ogsaa gaar jeg gjennom cellene i lista og sjekker om de er levende
#hvis de er levende oker teller med 1.
#metoden retunerer antall levende
    def finnAntallLevende(self):
        teller = 0
        for liste in self._rutenett:
            for celle in liste:
                if celle.erLevende():
                    teller +=1
        return teller




#Lager en metode som genererer et tilfeldig antall levende og dode, med en sannsynlughet paa 1/3 for at cellene er levende
    def _generer(self):
        for list in self._rutenett:
            for celle in list:
                tall = randint(0,2)
                if tall == 0:
                    celle.settLevende()
                else:
                    celle.settDoed()



#lager metode finn nabo
    def finnNabo(self,rad,kolonne):
        #lager tom liste som skal inneholde naboene
        naboliste = []
        #lagger en nustet liste som skjekker cellene en posisjon foer og etter, og under og uver
        for x in range(-1,2):
            for i in range(-1,2):
                #definerer naboene som cellene som har posisjonen til den valgte posisjone pluss x og i som er tallene som tilsvarer posisjonene foer og etter
                cellerad = rad + x
                cellekol = kolonne + i
#setter en skjekk som skal vere sann
                sjekk = True
#Hvis nabotallene er det samme som den vslgte posisjonen, eller hvis de gaar utenfor spillebrettet saa blir skjekk false
                if cellerad == rad and cellekol == kolonne:
                    sjekk = False

                if cellerad >= self._rader or cellerad < 0:
                    sjekk = False

                if cellekol >= self._kolonner or cellekol < 0:
                    sjekk = False
#hvis skjekk er sann skal naboilista legge till naboene
                if sjekk:
                    naboliste.append(self._rutenett[cellerad][cellekol])
#lista blir retunert
        return naboliste
