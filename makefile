JC = javac
JFLAGS = -g
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	CommandParser.java \
	RBNode.java \
	RBTree.java \
	MinHeapNode.java \
	MinHeap.java \
	risingCity.java 
	
default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
