SRCDIR=src
BINDIR=bin

all: TestTSP TestVRP

TestTSP:
	javac -d $(BINDIR) $(SRCDIR)/tsp/*.java $(SRCDIR)/graph/*.java

TestVRP:
	javac -d $(BINDIR) $(SRCDIR)/vrp/*.java $(SRCDIR)/graph/*.java $(SRCDIR)/tsp/HeuristicTSP.java $(SRCDIR)/tsp/InsertHeuristicTSP.java

clean:
	rm -rf $(BINDIR)/*
