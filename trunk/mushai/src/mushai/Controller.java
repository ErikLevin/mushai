package mushai;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import minmax.MiniMax;
import mushai.Player.PlayerType;

/**
 * Controller class for game logic.
 */
public class Controller implements ActionListener {

    private Playboard board;
    private MiniMax minimax;

    public Playboard getBoard() {
        return board;
    }
    private Window win;
    private Point moveStart;
    private Player activePlayer;

    public Controller(Window tWin) {
        win = tWin;
        this.board = win.getBoard();
        minimax = new MiniMax(this, board);
        for (JButton temp : win.getButtons()) {
            temp.addActionListener(this);
        }
        for (int j = 0; j < Settings.getPlayboardSize(); j++) {
            for (int i = 0; i < Settings.getPlayboardSize(); i++) {
                board.getTiles()[i][j].addActionListener(this);
            }
        }
        changePlayer();
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
        if (((JButton) e.getSource()).getText().equals("reset turn")) {
            board.getTiles()[moveStart.x][moveStart.y].setBackground(Color.lightGray);
            board.getTiles()[moveStart.x][moveStart.y].repaint();
            moveStart = null;
        }
        if (((JButton) e.getSource()).getText().equals("end game")) {
            new Main();
            this.win.dispose();
        }



        System.out.println(((JButton) e.getSource()).getText());

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
                move(moveStart, pressedPoint);

            } else if (Model.getPiece(pressedPoint, board) != null) {
                if (moveStart == null) {

                    for (int i = 0; i < Settings.getNrOfPlayers(); i++) {
                        Player pl = Settings.getPlayers().get(i);
                        if (pl.isItMyTurn()) {

                            if (Model.getPiece(pressedPoint, board).color.equals(pl.getColor())) {
                                moveStart = pressedPoint;
                                System.out.println("runing marked");
                                board.getTiles()[pressedPoint.x][pressedPoint.y].setBackground(Color.lightGray.darker());
                                board.getTiles()[pressedPoint.x][pressedPoint.y].repaint();
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
            moveStart = null;
            if (Settings.paintGraphics()) {

                board.update();

            }

            changePlayer();
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
        //checkVictory();

        /**nollställer brädet **/
        ArrayList<Player> arL = Settings.getPlayers();
        int xTile = 0, yTile = 0;
        for (xTile = 0; xTile < Settings.getPlayboardSize(); xTile++) {//målar om hela brädet
            if (xTile == 0 || xTile == Settings.getPlayboardSize() - 1) {
                for (yTile = 0; yTile < Settings.getPlayboardSize(); yTile++) {
                    board.getTiles()[xTile][yTile].setBackground(Color.GRAY);
                    board.getTiles()[xTile][yTile].repaint();
                }
            } else {
                for (yTile = 0; yTile < Settings.getPlayboardSize(); yTile++) {
                    board.getTiles()[xTile][yTile].setBackground(Color.lightGray);
                    board.getTiles()[xTile][yTile].repaint();
                }
            }
        }
        /**räknar ut vems tur det är **/
        for (int i = 0; i < arL.size(); i++) {
            if (arL.get(i).isItMyTurn()) {
                arL.get(i).setMyTurn(false);
                Player thisPlayer = arL.get((i + 1) % arL.size());
                thisPlayer.setMyTurn(true);
                switch (thisPlayer.getType()) {
                    case MINIMAX:
                        move(minimax.findBestMove(4));
                        break;
                    case GENETIC:
                        thisPlayer.makeMove(board);
                        break;
                    default:
                }

                break;
            }
        }

        int win = Model.checkWin(board);
        if (win == 1) {
            JOptionPane.showMessageDialog(board, "player 1 won");
            board.resetBoard(Settings.getPlayboardSize() - 2, Settings.getPlayboardSize() - 2);
//            this.win.dispose();
        } else if (win == -1) {
            JOptionPane.showMessageDialog(board, "player 2 won");
            board.resetBoard(Settings.getPlayboardSize() - 2, Settings.getPlayboardSize() - 2);
        }
    }

    /**
     * Checks if someone has won.
     *
     * @TODO - Doesn't seem to work correctly...
     * @return
     */
    private boolean checkVictory() {
        boolean win = true;
        int i = 0;
        for (Player player : Settings.getPlayers()) {
            if (player.isItMyTurn()) {
                if (i == 0) {
                    for (Point point : Model.getYourPieces(board)) {
                        if (point.y != 0) {
                            win = false;
                            break;
                        }
                    }
                    if (win) {
                        JOptionPane.showMessageDialog(board, "player 1 vann");
                        player.addPoint();
                    }
                } else if (i == 1) {
                    for (Point point : Model.getYourPieces(board)) {
                        if (point.y != Settings.getPlayboardSize() - 1) {
                            win = false;
                            break;
                        }
                    }
                    if (win) {
                        player.addPoint();
                        JOptionPane.showMessageDialog(board, "player 2 vann");
                    }
                }
            }
            i++;
        }

        return win;
    }
}
