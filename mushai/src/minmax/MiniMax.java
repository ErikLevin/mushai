/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author bark
 */
public class MiniMax {

    private Controller controller;
    private Playboard originalPlayboard;
    private PlayboardModel playboard;

    public MiniMax(Controller c, Playboard pb) {
        controller = c;
        originalPlayboard = pb;
    }

    public Move findBestMove(int depth) {
        int turn = Model.whoseTurnIsIt();
        playboard = new PlayboardModel(originalPlayboard, turn);
        MoveAndFitness maf = findBestMove(depth, null);
        Move move = maf.move;
        //Move move = breadthFirst(depth);
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
        if (lastMove != null) //System.out.println("after move: " + lastMove + " fitness is: " + playboard.getBoardFitness());
        {
            if (depth == 0 || playboard.getBoardFitness() > 1000 || playboard.getBoardFitness() == 0) {
                try {
                    return new MoveAndFitness(lastMove, playboard.getBoardFitness());
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
        //if (lastMove!=null)System.out.println("LAST: "+lastMove.getStart() + " ---> " + lastMove.getEnd() + ":::" + Model.getBoardFitness(playboard));
        //System.out.println(firstMove.getStart() + " ---> " + firstMove.getEnd() + ":::" + Model.getBoardFitness(playboard));
        MoveAndFitness maf = findBestMove(depth - 1, firstMove);
        bestValue = maf.fitness;
        playboard.movePiece(firstMove.getEnd(), firstMove.getStart());
        if (maf.fitness > 1000 || maf.fitness == 0) {
            //return new MoveAndFitness(firstMove, maf.fitness);
        }
        bestMove = firstMove;
        for (Move move : possibleMoves) {
            //if (depth == 4) System.out.println(move.getStart() + "--->"+ move.getEnd());
            //domove
            playboard.movePiece(move.getStart(), move.getEnd());
            maf = findBestMove(depth - 1, move);
            value = maf.fitness;
            //undomove
            playboard.movePiece(move.getEnd(), move.getStart());
            if (maf.fitness > 1000 || maf.fitness == 0) {
                //return new MoveAndFitness(move, maf.fitness);
            }
            if (value == 0 || value == 10000) {
                value += depth;
                maf.fitness += depth;
            }
            if (turn == 0) {
                if (value > bestValue) {
                    //if (lastMove!=null)System.out.println("LAST: "+lastMove.getStart() + " ---> " + lastMove.getEnd() + ":::" + Model.getBoardFitness(playboard));
                    //System.out.println(move.getStart() + " ---> " + move.getEnd() + ":::" + Model.getBoardFitness(playboard));
                    bestValue = value;
                    bestMove = move;
                    //System.out.println("depth: " + depth + " value: " + bestValue);
                }
            } else if (turn == 1) {
                if (value < bestValue) {
                    bestValue = value;
                    bestMove = move;
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
        int fitness = playboard.getBoardFitness();
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

            fitness = playboard.getBoardFitness();
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
