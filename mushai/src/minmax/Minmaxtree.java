package minmax;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mushai.Playboard;
import mushai.Model;
import mushai.Move;

/**
 *
 * @author MushAI
 * @deprecated - Use Minimax class instead.
 */
public class Minmaxtree {

    public int findBestOfMyMoves(Playboard board, int deap) {
        int bestMove = 0;
        if (deap == 0) {
            try {
                return Model.getBoardFitness(board);
            } catch (Exception ex) {
                Logger.getLogger(Minmaxtree.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (Move move : Model.getAllPossibleMoves(board)) {
            int temp = findBestOfEnemysMoves(Model.movePiece(board, move), deap - 1);
            if (temp > bestMove) {
                bestMove = temp;
            }

        }
        return bestMove;
    }

    public int findBestOfEnemysMoves(Playboard board, int deap) {
        int lowestPoint = +1000;
        for (Move move : Model.getAllPossibleMoves(board)) {
            int temp;
            try {
                temp = Model.getBoardFitness(Model.movePiece(board, move));
                if (temp < lowestPoint) {
                    lowestPoint = temp;
                }
            } catch (Exception ex) {
                Logger.getLogger(Minmaxtree.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (Move move : Model.getAllPossibleMoves(board)) {
            try {
                int temp = Model.getBoardFitness(Model.movePiece(board, move));
                if (temp == lowestPoint) {
                    return findBestOfMyMoves(Model.movePiece(board, move), deap);

                }
            } catch (Exception ex) {
                Logger.getLogger(Minmaxtree.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return -1;
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
 /*   private Move breadthFirst(int depth) {
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
  /*          if (queue.isEmpty()) {
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
*/
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


}
