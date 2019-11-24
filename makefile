JC = javac
JFLAGS = -g
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	RBNode.java \
        RBTree.java \
        MinHeapNode.java \
        MinHeap.java \
        CommandParser.java \
        RisingCity.java
	
default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class