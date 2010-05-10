package minmax;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mushai.Controller;
import mushai.Playboard;
import mushai.Model;
import mushai.Move;
import mushai.Player;
import mushai.Settings;

/**
 *
 * @author MushAI
 */
public class MiniMax {

    private Controller controller; //Not used??
    private Playboard originalPlayboard;
    private PlayboardModel playboard;

    public MiniMax(Controller c, Playboard pb) {
        controller = c;
        originalPlayboard = pb;
    }

    public MiniMax(PlayboardModel board) {
        playboard = board;
    }

    public Move findBestMove(int depth) {
        int turn = Model.whoseTurnIsIt();
        playboard = new PlayboardModel(originalPlayboard, turn);
        MoveAndFitness maf = findBestMove(depth, null);
        Move move = maf.move;
        if (maf.fitness > 1000) {
            System.out.println("ZOMG 100000 fitness!!!!");
        }
        //Move move = breadthFirst(depth);
//        Move move = minimaxDecision(depth, playboard);

        ArrayList<Player> arL = Settings.getPlayers();
        if (turn == 0) {
            arL.get(0).setMyTurn(true);
            arL.get(1).setMyTurn(false);
        } else if (turn == 1) {
            arL.get(0).setMyTurn(false);
            arL.get(1).setMyTurn(true);
        }

        return move;
    }

    private MoveAndFitness findBestMove(int depth, Move lastMove) {
        if (lastMove != null) //System.out.println("after move: " + lastMove + " fitness is: " + playboard.getUtility());
        {
            if (depth == 0 || playboard.getUtility() > 1000 || playboard.getUtility() < -1000) {
                try {
                    return new MoveAndFitness(lastMove, playboard.getUtility());
                } catch (Exception ex) {
                    Logger.getLogger(MiniMax.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        int bestValue, value, turn;
        Move bestMove;
        turn = Model.whoseTurnIsIt();
        List<Move> possibleMoves = playboard.getAllPossibleMoves(turn);
        if (possibleMoves.isEmpty()) {
            throw new RuntimeException("CRITICAL ERROR EXCEPTION! NO POSSIBLE MOVES :(((((");
        }

        Move firstMove = possibleMoves.remove(0);
        playboard.movePiece(firstMove.getStart(), firstMove.getEnd());
        MoveAndFitness maf = findBestMove(depth - 1, firstMove);
        bestValue = maf.fitness;
        playboard.movePiece(firstMove.getEnd(), firstMove.getStart());
        bestMove = firstMove;
        double randomValue;
        for (Move move : possibleMoves) {
            //if (depth == 4) System.out.println(move.getStart() + "--->"+ move.getEnd());
            //domove
            playboard.movePiece(move.getStart(), move.getEnd());
            maf = findBestMove(depth - 1, move);
            value = maf.fitness;
            //undomove
            playboard.movePiece(move.getEnd(), move.getStart());
            if (value == -10000 || value == 10000) {
                if (turn == 0) {
                    value += depth;
                    maf.fitness += depth;
                } else {
                    value -= depth;
                    maf.fitness -= depth;
                }
            }
            randomValue = Math.random();
            if (turn == 0) {
                if (value >= bestValue) {
                    if (!(value == bestValue && randomValue > 0.3)) {
                        bestValue = value;
                        bestMove = move;
                    }
                }
            } else if (turn == 1) {
                if (value <= bestValue) {
                    if (!(value == bestValue && randomValue > 0.3)) {
                        bestValue = value;
                        bestMove = move;
                    }
                }
            }
        }
        return new MoveAndFitness(bestMove, bestValue);
    }

    /*
    currDepth = 0
    for(Move m : getAllPossibleMoves)
    queue.push(previousMoves, m)
    while (currDepth <= depth)
    previousMoves, move = queue.pop()
    currDepth = previousMoves.length

    doAllMoves(previousMoves+move)

    if (gameDecided())
    break
    fitness = playboard.fitness
    if fitness > bestFitness
    bestFitness = fitness
    bestMove = previousMoves.first
    if currDeptj < depth
    for(Move m : getAllPossibleMoves)
    queue.push(previousMoves, m)

    undoAllMoves(move + previousMoves.reverse)*/
    private Move breadthFirst(int depth) {
        int currDepth = 0;
        int fitness = playboard.getUtility();
        int bestFitness = 0;
        if (playboard.getTurn() == 1) {
            bestFitness = 0;
        }
        LinkedList<MoveAndPrevMoves> queue = new LinkedList<MoveAndPrevMoves>();
        for (Move m : playboard.getAllPossibleMoves(playboard.getTurn())) {
            queue.add(new MoveAndPrevMoves(m, new LinkedList<Move>()));
        }
        Move move, bestMove, temp;
        bestMove = null;
        LinkedList<Move> prevMoves = new LinkedList<Move>();
        LinkedList<Move> prevClone = new LinkedList<Move>();
        while (currDepth <= depth) {
            /*System.out.println("queue:");
            for (MoveAndPrevMoves ma : queue){
            System.out.println("\tmove: " + ma.move + " prevMoves: ");
            for (Move prev : ma.prevMoves)
            System.out.println("\t\t" + prev);
            }*/
            if (queue.isEmpty()) {
                System.out.println("reached empty, maybe correct");
                System.out.println(currDepth);
                break;
            }
            MoveAndPrevMoves mapm = queue.pop();
            move = mapm.move;

            prevMoves = mapm.prevMoves;
            currDepth = prevMoves.size();
            //System.out.println(currDepth + " " + move);
            //doallmoves
            for (Move m : prevMoves) {
                playboard.movePiece(m.getStart(), m.getEnd());
            }
            playboard.movePiece(move.getStart(), move.getEnd());

            fitness = playboard.getUtility();
            System.out.println("turn: " + playboard.getTurn() + ", move made: " + move);
            System.out.println("fitness: " + fitness + ", bestfitness: " + bestFitness);
            if (playboard.getTurn() == 1) {
                if (fitness > bestFitness) {
                    bestFitness = fitness;
                    if (prevMoves.isEmpty()) {
                        bestMove = move;
                    } else {
                        bestMove = prevMoves.getFirst();
                    }
                }
            } else if (playboard.getTurn() == 0) {
                if (fitness < bestFitness) {
                    bestFitness = fitness;
                    if (prevMoves.isEmpty()) {
                        bestMove = move;
                    } else {
                        bestMove = prevMoves.getFirst();
                    }
                }
            }
            System.out.println("bestMove right now: " + bestMove);
            System.out.println("after check, fitness: " + fitness + ", bestfitness: " + bestFitness);
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
                for (Move m : playboard.getAllPossibleMoves(playboard.getTurn())) {
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
        System.out.println("move chosen: " + bestMove);
        return bestMove;
    }

    private Move minimaxDecision(int depth, PlayboardModel playboard) {
        int turn = Model.whoseTurnIsIt(); // ???
        List<Move> goodMoves = new LinkedList<Move>();
        int bestVal = Integer.MIN_VALUE;
        List<Move> successors = playboard.getAllPossibleMoves(turn);
        System.out.println("Turn: " + turn);

        for (Move m : successors) {
            PlayboardModel temp = playboard.clone();
            temp.movePiece(m);
            int v = maxValue(depth - 1, temp, turn);
            if (v == bestVal) {
                goodMoves.add(m);
            } else if (v > bestVal) {
                bestVal = v;
                goodMoves.clear();
                goodMoves.add(m);
            }
        }
        if (goodMoves.isEmpty()) {
            throw new RuntimeException("Minimax search didn't find a move!");
        } else {
            return goodMoves.get((int) (Math.random() * goodMoves.size()));
        }
    }

    private int maxValue(int depth, PlayboardModel playboard, int rootPlayer) {
        if (terminalTest(depth, playboard)) { // If game is over or max depth reached
//            if (rootPlayer == 0) {
            if (playboard.getUtility() > 100) {
                return playboard.getUtility() + 10 * depth;
            } else {
                return playboard.getUtility();
            }
//            } else {
//                return -playboard.getUtility() - depth;
//            }
        }

        int v = Integer.MIN_VALUE;
        List<Move> successors = playboard.getAllPossibleMoves(Model.whoseTurnIsIt()); // ??

        for (Move m : successors) {
            PlayboardModel temp = playboard.clone();
            temp.movePiece(m);
            v = Math.max(v, minValue(depth - 1, temp, rootPlayer));
        }
        return v;
    }

    private int minValue(int depth, PlayboardModel playboard, int rootPlayer) {
        if (terminalTest(depth, playboard)) { // If game is over or max depth reached
//            if (rootPlayer == 0) {
            if (playboard.getUtility() > 100) {
                return playboard.getUtility() + 10 * depth;
            } else {
                return playboard.getUtility();
            }//            } else {
//                return -playboard.getUtility() - depth;
//            }
        }

        int v = Integer.MAX_VALUE;
        List<Move> successors = playboard.getAllPossibleMoves(Model.whoseTurnIsIt()); // whose turn??

        for (Move m : successors) {
            PlayboardModel temp = playboard.clone();
            temp.movePiece(m);
            v = Math.min(v, maxValue(depth - 1, temp, rootPlayer));
        }
        return v;
    }

    private boolean terminalTest(int depth, PlayboardModel playboard) {
        return depth < 1 || playboard.checkWin() != 0;
    }

    private class MoveAndPrevMoves {

        Move move;
        LinkedList<Move> prevMoves;

        public MoveAndPrevMoves(Move m, LinkedList<Move> pm) {
            prevMoves = pm;
            move = m;
        }
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
}
