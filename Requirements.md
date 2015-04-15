# Introduction #

The intent is to explore how a genetic programming approach can be used to develop an artificial intelligence that plays a abstract strategy board game.

The ultimate vision for the end product is a complete, graphical Traverse game where a human player can play against our AI.


# Details #

## Programming language ##

The programming language we will use for the GUI will be Java.
For the genetic algorithm, a Java open source framework will probably be used.


## Game rules ##

### First version ###

  1. No long jumps
  1. Only two players
  1. 4x4 board
  1. Only square pieces
  1. Pieces can't be moved when they have reached the goal
  1. Pieces are removed when they have reached the goal
  1. No placement of pieces

### Future versions ###

The restrictive rules will be removed/relaxed one after the other, in the following order:
  1. Extend board, ultimately to 9x9
  1. Add more types of pieces
  1. Allow moving pieces that have reached goal (?)