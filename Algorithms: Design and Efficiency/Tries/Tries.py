class Trie: #excessive use of strip, due to output not being as expected
    def __init__(self, readname, outname):
        self.readname = readname#Trie class, initiates the instance variables
        self.outname = outname
        self.c = []
        self.n = None
        self.search = []
        self.roots = []
        self.order = []
        #self.output = open(outname, "w")

    def readfile(self):#readfile funcion
    #It simply reads the file and saves the alphabet in the class so its accessable
        file = open(self.readname)
        n = int(file.readline())
        self.n = n
        for i in range(n):
            self.c.append(file.readline())

        for line in file:
            self.search.append(str(line))

        file.close()


        return

    def construct(self):#method that constructs the collection of strings
        for x in self.c:
            print(x)

        y=0
        for string in self.c:#for loop to add each letter to the uncompressed trie

            y+=1
            count = 0
            testnode = Node(string[count])
            if testnode not in self.roots:# if the first letter isnt a root, then we add a new root
                #print("Rotnode")
                newNode = Node(string[count])#creates a new node of first letter
                self.roots.append(newNode)

                self.insert(newNode, string[1:])
                self.order.append(self.update(string))
                print(self.update(string))
                continue

            for i in range(len(self.roots)):#case when not adding a rootnode
                #print("Kom inn")

                gjeldene = Node(string[count])
                if gjeldene == self.roots[i]:
                    tmp = self.roots[i]

                    teller = 0#used these variables to test methods
                    slutt = False
                    while tmp == Node(string[count]):#if the next letters are the same, we move on
                        print(teller, "Bokstav:", string[count])
                        teller+=1
                        for children in tmp.getchildren():
                            if Node(string[count+1]) == children:
                                tmp = children
                                count+=1
                                break
                            else:
                                count+=1
                                slutt = True;
                                break
                            #count+=1
                            continue
                        if slutt == True:#extra because it would loop if the next letter in the string, was the same as the current node in the algoritm
                            break


                    #adds remaining letters of the string to the trie
                    self.insert(tmp, string[count:])
                    tmp.printString()
                    self.order.append(self.update(string))
                    print(self.update(string))
                    break
                continue



#functipn that inserts a letters from a string as nodes and child nodes
    def insert(self, node, string):
        #it simply creates a node, adds it to the trie, and so on
        count = 0
        newNode = Node(string[count])
        node.addChild(newNode)

        count+=1
        tmp = newNode
        while count < len(string):
            child = Node(string[count])
            tmp.addChild(child)
            tmp = child
            count+=1
        return

    def update(self, word):#method for getting the string version of how the trie currently looks
        streng = word.strip() + ":"

        for ting in self.roots:
            streng += str(self.addbracket("", ting)).strip()

        return streng.strip()

#recursive functions for making output strings
    def addword(self, streng, node):
        streng += str(node)
        #this functions simply uses a string, and adds string versions of itself to the string
        #it sends the string in the recursive call as wells
        #if a word has multiple children, we add a bracket and call that function
        if len(node.getchildren()) > 1:
            for child in node.getchildren():
                suffix = str(self.addbracket("", child))
                streng += suffix
            return streng.strip()

        elif len(node.getchildren()) == 0:
            return streng.strip()

        for child in node.getchildren():
            return str(self.addword(streng, child)).strip()

#this functions calls on the recursive formula, but adds a bracket to the final string
    def addbracket(self, streng, node):
        s = str(self.addword(streng, node))
        streng = " (" + s + ")"
        return streng.strip()

#recursive function for string search
    def searcher(self, word, node, teller):
        #these are my base cases
        #if a the word is too long, too short or non existant, return False
        #if the word is done, and the trie has no more child nodes, then thats a match
        #else, then we call the function of child nodes
        if teller == len(word)-1 and len(node.getchildren()) == 0:
            return True

        elif teller == len(word)-1 and len(node.getchildren()) > 0:
            return False

        elif teller < len(word)-1 and len(node.getchildren()) == 0:
            return False

        elif Node(word[teller+1]) not in node.getchildren():
            return False

        for children in node.getchildren():
            if Node(word[teller+1]) == children:
                return self.searcher(word, children, teller+1)

        return False


#function that calls on the rec search
    def recSerch(self, word):
        for root in self.roots:
            if Node(word[0]) == root:
                return self.searcher(word, root, 0)
        return False

#search function caller to make output text
    def wordSearch(self):
        for word in self.search:
            verdict = self.recSerch(word)

            if verdict == True:
                result = word.strip() + " YES"
                self.order.append(result);
            else:
                result = word.strip() + " NO"
                self.order.append(result);

#print functions for testing
    def print(self):
        for ting in self.roots:
            ting.printString()

    def printstuff(self):
        for ting in self.order:
            print(ting);
        #for ting in self.search:
            #print(ting)

    #function that writes to file
    def writeToFile(self):
        f = open(self.outname, "w")
        for ting in self.order:
            f.write(ting + "\n")
        f.close()
        return



#class node, that represents nodes in the trie.
#it has simple return functions, data and a list of children.
class Node:
    def __init__(self, data):
        self.children = []
        self.data = data

    def __str__(self):
        return self.data

    def __eq__(self, other):
        return self.data == other.data


    def data(self):
        return self.data

    def addChild(self, child):
        self.children.append(child)

    def getchildren(self):
        return self.children

    def printString(self):
        print(self.data)
        for barn in self.children:
            barn.printString()


#test program to initiate everything
def testProgram():
    treet = Trie("testset2.txt", "ny.txt")

    treet.readfile()
    treet.construct()
    treet.wordSearch()
    treet.printstuff()
    treet.writeToFile()
    #treet.print()

#make order
testProgram()
