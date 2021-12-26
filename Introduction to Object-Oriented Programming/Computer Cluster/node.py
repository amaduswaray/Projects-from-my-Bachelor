class Node:
    def __init__(self, minne, antPros):
        self._minne = minne
        self._antPros = antPros


    def antProsessorer(self):
        return self._antPros


    def nokMinne(self, paakrevdMinne):
        if self._minne >= paakrevdMinne:
            return True
        else:
            return False
