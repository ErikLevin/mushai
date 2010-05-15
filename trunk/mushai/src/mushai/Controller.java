package mushai;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import minmax.MiniMax;
import minmax.PlayboardModel;

/**
 * Controller class for game logic.
 */
public class Controller implements ActionListener {

    private Playboard board;
    private MiniMax minimax;
    private Semaphore moveSem;

    public Playboard getBoard() {
        return board;
    }
    private Window win;
    private Point moveStart;

    public Controller(Window tWin) {
        win = tWin;
        this.board = win.getBoard();
        minimax = new MiniMax(board);
        for (JButton temp : win.getButtons()) {
            temp.addActionListener(this);
        }
        for (int j = 0; j < Settings.getPlayboardSize(); j++) {
            for (int i = 0; i < Settings.getPlayboardSize(); i++) {
                board.getTiles()[i][j].addActionListener(this);
            }
        }
        moveSem = new Semaphore(0);

        // <editor-fold defaultstate="collapsed" desc="Paint borders">
        Tile borderTile;
        for (int i = 0; i < Settings.getPlayboardSize(); i++) {
            // left border
            borderTile = board.getTiles()[0][i];
            borderTile.setBackground(Color.GRAY);
            borderTile.repaint();

            // right border
            borderTile = board.getTiles()[Settings.getPlayboardSize() - 1][i];
            borderTile.setBackground(Color.GRAY);
            borderTile.repaint();
        }// </editor-fold>

        gameLoop();
    }

    /**
     * Window-less constructor, with only a Playboard. Will not listen to any button clicks.
     * 
     * @param board
     */
    public Controller(Playboard board) {
        this.board = board;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Quit")) {
            System.exit(0);
        }

        if (e.getSource().getClass() == Tile.class) { //A Tile was clicked
            Tile t = (Tile) e.getSource();
            int xTile = 0, yTile = 0;
            outer:
            for (xTile = 0; xTile < Settings.getPlayboardSize(); xTile++) {
                for (yTile = 0; yTile < Settings.getPlayboardSize(); yTile++) {

                    if (board.getTiles()[xTile][yTile] == t) {
                        break outer;
                    }
                }
            }

            Point pressedPoint = new Point(xTile, yTile);


            if (moveStart != null) {
                if (moveStart.x == pressedPoint.x && moveStart.y == pressedPoint.y) {  // same piece selected
                    moveStart = null;
                    System.out.println("piece deselected");
                    board.getTiles()[pressedPoint.x][pressedPoint.y].setBackground(Color.lightGray);
                    board.getTiles()[pressedPoint.x][pressedPoint.y].repaint();
                } else {
                    if (move(moveStart, pressedPoint)) { // If a move was successful
                        moveSem.release(); // Release semaphore to continue game loop
                    }

                    Tile startTile = board.getTiles()[moveStart.x][moveStart.y];
                    startTile.setBackground(Color.lightGray);
                    startTile.repaint();
                    moveStart = null;
                }

            } else if (Model.getPiece(pressedPoint, board) != null) {
                if (moveStart == null) {

                    for (int i = 0; i < Settings.getNrOfPlayers(); i++) {
                        Player pl = Settings.getPlayers().get(i);
                        if (pl.isItMyTurn()) {

                            if (Model.getPiece(pressedPoint, board).color.equals(pl.getColor())) {
                                moveStart = pressedPoint;
                                System.out.println("piece selected");
                                Tile selectedTile = board.getTiles()[pressedPoint.x][pressedPoint.y];
                                selectedTile.setBackground(Color.GREEN.darker());
                                selectedTile.repaint();
                            }
                        }
                    }

                }
            }
        }
    }

    /**
     * Moves a piece from one square to another. Returns true if move was successful,
     * false if it was for some reason not possible.
     *
     * @param start - start square of the move
     * @param end - end square of the move
     * @return - true if move was successful, false otherwise
     */
    public boolean move(Point start, Point end) {

//        System.out.println("Move: " + start + " to " + end);

        if (Model.pointOnBoard(start) && Model.pointOnBoard(end)
                && Model.getPiece(start, board) != null
                && Model.whereCanIMove2(start, board).contains(end)) {
            Tile origin = board.getTiles()[start.x][start.y];
            Piece p = origin.getPiece();
            origin.setPiece(null);
            board.getTiles()[end.x][end.y].setPiece(p);
            if (Settings.paintGraphics()) {
                board.update();
            }

//            changePlayer(); //do this in gameLoop
            return true;
        }
        return false;
    }

    public boolean move(Move chosenMove) {

        return move(chosenMove.getStart(), chosenMove.getEnd());
    }

    public boolean move(int fromX, int fromY, int toX, int toY) {
        return move(new Point(fromX, fromY), new Point(toX, toY));
    }

    public void changePlayer() {
        ArrayList<Player> arL = Settings.getPlayers();

        /**räknar ut vems tur det är **/
        for (int i = 0; i < arL.size(); i++) {
            if (arL.get(i).isItMyTurn()) {
                arL.get(i).setMyTurn(false);
                Player thisPlayer = arL.get((i + 1) % arL.size());
                thisPlayer.setMyTurn(true);
                break;
            }
        }
    }

    private void gameLoop() {
        int possibleMoves = 0;
        int moves = 0;
        while (true) {
//            possibleMoves += new PlayboardModel(board, Model.whoseTurnIsIt()).getAllPossibleMoves(Model.whoseTurnIsIt()).size();
//            moves++;

            // <editor-fold defaultstate="collapsed" desc="Check for win">
            int gameWin = Model.checkWin(board);
            if (gameWin != -1) {
                showWinnerMessage(Settings.getPlayer(gameWin));
//                System.out.println("Avg pos moves: " + possibleMoves / moves);
//                System.out.println("Number of moves: " + moves);
                System.exit(0);
            }// </editor-fold>

            // <editor-fold defaultstate="collapsed" desc="Make move">
            Player player = Settings.getCurrentPlayer();
            switch (player.getType()) {
                case HUMAN:
                    try {
                        moveSem.acquire(); // Wait until user makes a move
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case MINIMAX:
                    move(minimax.findBestMove(4));
                    break;
                case GENETIC:
                    System.out.println("I AM GENETIC!");
                    move(player.makeMove(board, Model.whoseTurnIsIt())); // @todo Shouldn't be 0.
                    break;
            }// </editor-fold>

            changePlayer();
        }
    }

    private void showWinnerMessage(Player p) {
        p.addPoint();
        JOptionPane.showMessageDialog(board, p.getPlayerName() + " won!");
        board.resetBoard(Settings.getPlayboardSize() - 2, Settings.getPlayboardSize() - 2);
    }
}
