# Supervisor meeting LV 1 #

Since Nine Men's Morris is already mathematically solved, we should choose a game that is not solved:
  * 12 Men's Morris was discussed
  * as was Taifho/Traverse
    * Perhaps start with a simpler board
    * Is there previous Taifho-research?

Should have an "objective" way to measure how good AI is
  * Benchmarking
  * Or play against humans
  * Compare with chess
  * Compare with "hand calculated" optimal solutions

Vad tillför gp utöver local search? I GP kombinerar man lösningar.

**Decision**: We change to Taifho/Traverse.

# Project meeting 0323 #

Should try to find out what kind of solvable Taifho/Traverse is (if at all): see "games solved.pdf"
Searching for previous research on Taifho AI or the solvability of Taifho yields no results...
Theoretical reasoning, inspired by "games solved.pdf"
  * We could in report show that 3x3 is trivial (second player wins).
  * 4x4 with squares
    * max 4 turns (?)
    * player 2 seems to have the advantage
  * No endgame in Traverse... Is it divergent? "games solved.pdf"
  * Maybe Shogi or another nonconvergent game is better to compare with.
A program that calculates all possible moves would be nice...

# The question of programming language #

  * According to book Lisp is useful for GP...
  * but we are most familiar with Java, esp. when it comes to GUI
    * Is there a way to run Lisp in Java?
    * Or we could write our own (just output from Lisp and into Java)
    * http://www.flownet.com/gat/papers/lisp-java.pdf
    * http://franz.com/support/documentation/current/doc/jlinker.htm
    * http://www.franz.com/support/documentation/6.2/doc/jil.htm
    * http://ieeexplore.ieee.org/stamp/stamp.jsp?arnumber=04145042

# Project meeting 0326 #

## Define concrete problem, question or task ##

Goal: Develop AI that plays the game reasonably well, compared with other benchmarks of similar games.

**conclusion**: We must read more to better define this.

## Search for useful programs, tools and benchmarks ##

### Programs, tools ###

  * Frameworks for genetic programming / algorithms
  * Perhaps frameworks for board games (but we're nearly finished with our own, so...)
  * Statistics tools (???)

### Benchmarks ###

  * Search methods applied to similar games, genetic programming for similar games
  * If it exists, planning etc. for similar games...
  * Hand-calculated optimal solution

## Toy solutions, small programs etc. ##

  * Our 4x4 will essentially be a "toy solution" compared to complete game
  * Test stuff we find in literature

## Design thoughts... ##

How about defining a game protocol and running a client-server solution for contact between players / AI and game rules? Game sends board state and all possible moves to client, client chooses one of the moves and sends its choice back. Reason: supporting platform and language independent AI implementations.

**decision**: Move google docs to wiki.

# Solvability #

Stuff from games solved.pdf.
Let's try to decide
  * Whether the game-theoretic value of the initial position is
    * First player wins
    * Second player wins
    * Draw
  * Divergent / convergent / neither
  * State-space complexity (if these are possible to know without 100 years research)
  * Game-tree complexity
for different sized boards. Check out table 4 in games solved.pdf. If we could do a table like that, it would look impressive! And awesome!
With this, we can decide what of the 4 categories in Fig. 1 Traverse belongs to. My prediction: The full game will be category 4 (unsolvable by any known methods), which we can use to motivate our attempts at a genetic approach.

# Meeting 100413 #

There are several frameworks for GP in Java:
  * [GenPro - Reflective Object Oriented Genetic Programming](http://code.google.com/p/genpro/) Open Source Framework. Extend with POJO's, generates plain Java code.
  * [JAGA - Extensible and pluggable open source API for implementing genetic algorithms and genetic programming applications](http://www.jaga.org)
  * [JGAP - Java Genetic Algorithms and Genetic Programming, an open-source framework](http://jgap.sourceforge.net)
  * [n-genes - Java Genetic Algorithms and Genetic Programming (stack oriented) framework](http://cui.unige.ch/spc/tools/n-genes/)
  * [Java GALib - Source Forge open source Java genetic algorithm library, complete with Javadocs and examples (see bottom of page)](http://www.softtechdesign.com/GA/EvolvingABetterSolution-GA.html)
  * [Groovy Java Genetic Programming](http://jgprog.sourceforge.net/)
  * [JCLEC - Evolutionary Computation Library in Java](http://jclec.sourceforge.net/) expression tree encoding, syntax tree encoding

How do we make an informed choice about which one to pick? We want:
  * suitable input/output. Sockets?
  * possibility to save best individual after one run of algorithm

# Supervisor meeting 100413 #
  * Risky to attempt something noone has done before
  * Split in two groups? One genetic, one that examines other search methods.
    * Genetic - Max & Erik
    * Other - Bark & Hasse

# Supervisor meeting 100420 #
  * Klara med regler
  * Minimax
    * (-100, 100), mät motståndarens avstånd minus sitt eget avstånd
  * GP
    * Leker med JGap, matte-exempel

We skip the piece placement phase. Could be "Future work" in report.
Fitness function brainstorming...
  * Coevolution: single elimination tournament between individuals.
  * Sensei evolution (just made that term up) - individuals train against preprogrammed agent
    * Random
    * Simple

## Assignment Work Division ##
  * Search: Levin
  * ML: Hasse
  * NLP: Max
  * Planning: Bark

# Supervisor meeting 100427 #
  * Next friday: prototype should be done. This means: Program should "do something". Play the game, but perhaps not well
  * Focus on assignment for now...
  * Then, we need to quickly get something to work
  * Supervisor: "Go on one more week and see how it goes. I do not expect perfect results from both methods. Minimax tree should be quite straightforward."
  * Course guy: "Could be interesting to see how a program that tries to lose works"