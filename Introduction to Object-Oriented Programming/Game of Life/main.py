#Importer spillebrettet
from spillebrett import Spillebrett

def main():
    #Lar brukeren velge antall rader og kolonner
    rad = int(input("Skriv inn hvor mange rader du vil ha: "))
    kolonne = int(input("Skriv inn hvor mange kolonner du vil ha: "))
    #lager spillebrett objekt med rad og kolonne som brukeren velger
    test = Spillebrett(rad,kolonne)
    #tegner brettet en gang forst
    test.tegnBrett()
    #lager variabel som skjekker om  brukeren vil fortsette eller ikke
    fortsett = input("Vil du fortsette? (Skriv q for å avslutte) ")
    #so lenge brukeren ikke avlsutter sa skal brettet oppdateres seg
    while fortsett.lower() != "q":
        test.oppdatering()
        test.tegnBrett()
        #spor brukeren om han vil fortsette etter hver oppdatering
        fortsett = input("Vil du fortsette? (Skriv q for å avslutte) ")

#kjorer main
main()
