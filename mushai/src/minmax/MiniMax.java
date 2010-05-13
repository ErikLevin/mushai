package minmax;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;
import mushai.Playboard;
import mushai.Model;
import mushai.Move;

/**
 *
 * @author MushAI
 */
public class MiniMax {

    private Playboard originalPlayboard;
    private PlayboardModel playboard;

    public MiniMax(Playboard pb) {
        originalPlayboard = pb;
    }

    public MiniMax(PlayboardModel board) {
        playboard = board;
    }

    public Move findBestMove(int depth) {
        int turn = Model.whoseTurnIsIt();
        playboard = new PlayboardModel(originalPlayboard, turn);
        MoveAndFitness maf;
        Move move = null;
        if (playboard.endGameSituation(turn)) {
            move = findEndGameMove(depth - 1);
        } else {
            maf = findBestMove(depth, null);
            move = maf.move;
        }
        return move;
    }

    //Depth first traversing through the possible moves and their submoves
    //to find the best move.
    private MoveAndFitness findBestMove(int depth, Move lastMove) {
        //if we have checked far enough down depth-wise (reached a leaf) we return
        //with the current fitness-value. It also checks and returns if somebody has won.
        //Since the endgame is handled elsewhere, maybe we should only need check depth here
        //Only reason to handle it is if it actually has a large enough depth to investigate
        //from before endGame hits and until someone actually wins. Highly unlikely.
        //Please give supercomputer
        if (depth == 0 || playboard.getUtility() > 1000 || playboard.getUtility() < -1000) {
            try {
                return new MoveAndFitness(lastMove, playboard.getUtility());
            } catch (Exception ex) {
                Logger.getLogger(MiniMax.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //initiation of needed variables
        int bestValue, value, turn;
        Move bestMove;
        turn = Model.whoseTurnIsIt();

        List<Move> possibleMoves = playboard.getAllPossibleMoves(turn);
        int noOfPossibleMoves = possibleMoves.size();
        if (possibleMoves.isEmpty()) {
            throw new RuntimeException("CRITICAL ERROR EXCEPTION! NO POSSIBLE MOVES :(((((");
        }

        //Taking out first move from possible moves and set it as bestMove
        //so there is something to compare the rest of the moves with

        Move firstMove = possibleMoves.remove(0);
        playboard.movePiece(firstMove.getStart(), firstMove.getEnd());
        MoveAndFitness maf = findBestMove(depth - 1, firstMove);
        bestValue = maf.fitness;
        playboard.movePiece(firstMove.getEnd(), firstMove.getStart());
        bestMove = firstMove;

        //Is this random really necessary? None of the games seems to be random
        //when pitching two minimax-ai:s against each other
        double randomValue;
        Random randomizer = new Random();

        //Search through all possible moves to find the best move
        //Every move is made and then all possible submoves are checked until
        //depth has reached 0. After return of the best submoves, the value is
        //saved, the move undone and the move is compared to the bestMove
        for (Move move : possibleMoves) {
            //domove
            playboard.movePiece(move.getStart(), move.getEnd());

            //find best submove for this move
            maf = findBestMove(depth - 1, move);
            value = maf.fitness;
            //undomove
            playboard.movePiece(move.getEnd(), move.getStart());

            //adding depth probably no longer necessary because endgame is not handled here
            //If we ever reached a state where we could check a large enough depth,
            //this would be useful. But that is higly unlikely.
            if (value == -10000 || value == 10000) {
                if (turn == 0) {
                    value += depth;
                    maf.fitness += depth;
                } else {
                    value -= depth;
                    maf.fitness -= depth;
                }
            }
            //Not necessary? See earlier comments about random
            randomValue = randomizer.nextDouble();

            //depending on whos turn it is, a higher/lower fitness is wanted
            //question is if it should be changed to > and < instead of >= and <=
            //and removing of the randomcheck altogether. See earlier comments about that
            if (turn == 0) {
                if (value >= bestValue) {
                    if (!(value == bestValue && randomValue > (1 / noOfPossibleMoves))) {
                        bestValue = value;
                        bestMove = move;
                    }
                }
            } else if (turn == 1) {
                if (value <= bestValue) {
                    if (!(value == bestValue && randomValue > (1 / noOfPossibleMoves))) {
                        bestValue = value;
                        bestMove = move;
                    }
                }
            }
        }
        //This place should really not be reached... Ever... But it is possible..
        return new MoveAndFitness(bestMove, bestValue);
    }  

    private class MoveAndFitness {

        Move move;
        int fitness;
        int goalDepth;

        public MoveAndFitness(Move m, int f) {
            move = m;
            fitness = f;
        }

        public MoveAndFitness(Move m, int f, int gd) {
            move = m;
            fitness = f;
            goalDepth = gd;
        }
    }

    //When endGameSituation hits, this will be used instead of findBestMove.
    //This goes breadth first and goes through all possible moves regardless of
    //moves in earlier depth. It ignores the opponents moves completely.
    private Move findEndGameMove(int depth) {
        int currDepth = 0;
        int fitness = playboard.getUtility();
        int bestFitness = 0;
        int requestTurn = Model.whoseTurnIsIt();
        if (requestTurn == 1) {
            bestFitness = 0;
        }
        LinkedList<MoveAndPrevMoves> queue = new LinkedList<MoveAndPrevMoves>();
        for (Move m : playboard.getAllPossibleMoves(requestTurn)) {
            queue.add(new MoveAndPrevMoves(m, new LinkedList<Move>()));
        }
        Move move, bestMove, temp;
        bestMove = null;
        LinkedList<Move> prevMoves = new LinkedList<Move>();
        LinkedList<Move> prevClone = new LinkedList<Move>();
        while (currDepth <= depth) {
            if (queue.isEmpty()) {
                System.out.println("reached empty, maybe correct");
                System.out.println(currDepth);
                break;
            }
            MoveAndPrevMoves mapm = queue.pop();
            move = mapm.move;

            prevMoves = mapm.prevMoves;
            currDepth = prevMoves.size();
            //doallmoves
            for (Move m : prevMoves) {
                playboard.movePiece(m.getStart(), m.getEnd());
            }
            playboard.movePiece(move.getStart(), move.getEnd());

            fitness = playboard.getEndGameFitness(requestTurn);

            if (fitness > bestFitness) {
                bestFitness = fitness;
                if (prevMoves.isEmpty()) {
                    bestMove = move;
                } else {
                    bestMove = prevMoves.getFirst();
                }
            }
            if (playboard.checkWin() != 0) {
                System.out.println("possible win! should break! " + currDepth + " " + bestMove);
                for (Move m : prevMoves) {
                    System.out.println(m);
                }
                break;
            }

            prevClone = new LinkedList<Move>();
            for (Move m : prevMoves) {
                prevClone.add(m);
            }
            prevClone.add(move);
            if (currDepth < depth) {
                for (Move m : playboard.getAllPossibleMoves(requestTurn)) {
                    queue.add(new MoveAndPrevMoves(m, prevClone));
                }
            }
            playboard.movePiece(move.getEnd(), move.getStart());
            for (int i = prevMoves.size() - 1; i >= 0; i--) {
                temp = prevMoves.get(i);
                playboard.movePiece(temp.getEnd(), temp.getStart());
            }
        }
        if (bestMove == null) {
            System.out.println("RETURNING NULL AS BEST MOVE!");
        }
        return bestMove;
    }

    private class MoveAndPrevMoves {

        Move move;
        LinkedList<Move> prevMoves;

        public MoveAndPrevMoves(Move m, LinkedList<Move> pm) {
            prevMoves = pm;
            move = m;
        }
    }
}
