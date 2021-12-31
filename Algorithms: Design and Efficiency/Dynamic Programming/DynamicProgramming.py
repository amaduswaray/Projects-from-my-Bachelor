class Table:#creats class for table and instance rariables
    def __init__(self, filename, outname, type):
        self.U = []
        self.alg = type
        self.n = None
        self.elements = None
        self.selection = []
        self.output = []
        self.filename = filename
        self.outname = outname


    def readfile(self):
        #reads filename
        #loops trhough every instance
        #creates the lists and elements for make Table
        #calls makeTable
        #cals bottum up
        #call write to file, that writes the output.

        file = open(self.filename)
        for line in file:#this loops runs the algorithm for each instance
            self.selection = []
            instance = line.split(" ")

            n = int(instance[1])
            self.n = n
            k = int(instance[0])

            t = []
            for element in instance[2:]:
                t.append(int(element))

            #if check, because input files varies. some had K as first number
            #while others had
            #runs bottom up
            if self.alg == 0:
                if len(t) == n:
                    s = sum(t)
                    self.elements = t
                    self.makeTable(t, s)
                    svar = self.bottomUp(k)
                    self.makeOutput(n,k,t,svar)
                    #self.printOutput()
                else:
                    svar = False
                    self.makeOutput(n,k,t,svar)
                    #self.printOutput()
                    continue

            #Runs top down
            elif self.alg == 1:
                if len(t) == n:
                    s = sum(t)
                    self.elements = t
                    self.makeTable(t, s)
                    svar = self.topDown(k,n-1)
                    self.makeOutput(n,k,t,svar)
                    #self.printOutput()
                else:
                    svar = False
                    self.makeOutput(n,k,t,svar)
                    #self.printOutput()
                    continue
        file.close()
        #self.printOutput()
        self.writeToFile()

        return

    def makeTable(self, elements, k): #list of instances, n amount of instances, k sum of instances
        self.U = [] #re-initialise the global array for each instances

        #creates a dictionary with values 0 to k as keys
        #the values are true or false
        for i in range(len(elements)):
            dict = {}
            for j in range(k+1):
                dict[j] = False#initialises every cell as false
            self.U.append(dict)

    #this is my top down algorithm
    def topDown(self, sum, index):
        #I use the main table as the memoization
        testdict = self.U[index]
        #each sell has their values saved
        if testdict[sum] == True:
            return True

        #defining my base steps for the recursion
        retur = None
        if sum == 0: #returns true when its sero
            return True
        elif sum < 0:#sum cant be negative
            return False
        elif index < 0: #index cant be negative
            return False
        #If the sum is less than the current Integer, check for the next integer
        elif sum < self.elements[index]:#lag en table som inneholder verdiene S
            retur = self.topDown(sum, index-1)
            return retur

        #else give an or operation on with a recursive call on the next Integers
        else:
            retur = self.decide( (self.topDown(sum - self.elements[index], index-1) ), (self.topDown(sum, index-1)) )

            #adding output to a list to have access
        self.U[index][sum] = retur
        selection = str(self.elements[index]) + "[" + str(index) + "]"
        if selection not in self.selection:
            self.selection.append(selection)
        return retur
#simple or function that gives a return value
    def decide(self,i1, i2):
        if i1 == True or i2 == True:
            return True

        return False

#this is my true bottom up algorithm
    def bottomUp(self,sum):
        #it simply iterates through the hashmaps of each integer
        for i in range(self.n):
            for j in range(sum+1):

                #every integer can give the value 0
                if j == 0:
                    self.U[i][j] = True
                elif i == 0:#for the first integer, we simply adjust the cell that has the same value
                    if j == self.U[i]:
                        self.U[i][j] = True
                    continue
                else:#for every cell, we check if a sum is summable, or if the Integer before it is
                    if j >= self.elements[i-1]:
                        self.U[i][j] = self.decide(self.U[i-1][j], self.U[i-1][j - self.elements[i-1]])

                    else:
                        self.U[i][j] = self.U[i-1][j]

        self.makeSelection(self.n, sum)
        return self.U[self.n - 1][sum];

#function that creates the selection output.
    def makeSelection(self, i, j):
        if j == 0:
            return

        if self.U[i-1][j] == True:
            self.makeSelection(i-1, j)

        if j>=self.elements[i-1]:
            if self.U[i-1][j - self.elements[i-1]] == True:
                selection = str(self.elements[i-1]) + "[" + str(i-1) + "]"
                self.selection.append(selection)
                self.makeSelection(i-1, j- self.elements[i-1])




#functuon that creates output text and stores it
    def makeOutput(self,n , k , element, svar):
        #write output;
        elmt = ""
        for x in element:
            elmt += str(x) + " "

        if svar == False:
            output = "INSTANCE " + str(n) + " " + str(k) + ": " + elmt + "\n NO"
            self.output.append(output)
        else:
            selection = ""
            for ting in self.selection:
                selection += str(ting) + " "
            output = "INSTANCE " + str(n) + " " + str(k) + ": " + elmt + "\n YES \n SELECTION " + selection
            self.output.append(output)

        return

    #Simple print function for testing
    def printOutput(self):
        for i in self.output:
            print(i)

    #writing output to file
    def writeToFile(self):
        f = open(self.outname, "w")
        for ting in self.output:
            f.write(ting + "\n")
        f.close


def topDown():
    instanse = Table("oppg2test.txt", "tull1.txt", 1)
    instanse.readfile()

def bottomUp():
    instanse = Table("oppg2test.txt", "tull2.txt", 0)
    instanse.readfile()
    return

#runs each algorithm
topDown()
bottomUp()
