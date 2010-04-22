package mushai;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * Controller class for game logic.
 */
public class Controller implements ActionListener {

    private Playboard board;
    private Window win;
    private Point moveStart;

    public Controller(Window tWin) {
        win = tWin;
        this.board = win.getBoard();

        for (JButton temp : win.getButtons()) {
            temp.addActionListener(this);
        }
        for (int j = 0; j < Settings.getPlayboardSize(); j++) {
            for (int i = 0; i < Settings.getPlayboardSize(); i++) {
                board.getTiles()[i][j].addActionListener(this);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {


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

            Point presedPoint = new Point(xTile, yTile);


            if (moveStart != null) {
                move(moveStart, presedPoint);

            } else if (model.getPiece(presedPoint, board) != null) {
                if (moveStart == null) {

                    for (int i = 0; i < Settings.getNrOfPlayers(); i++) {
                        Player pl = Settings.getPlayers().get(i);
                        if (pl.isItMyturn()) {

                            if (model.getPiece(presedPoint, board).color.equals(pl.color)) {
                                moveStart = presedPoint;
                            }
                        }
                    }

                }
            }
        }
    }

    public void move(Point start, Point end) {

        if (model.whereCanIMove2(start, board).contains(end)) {
            Tile origin = board.getTiles()[start.x][start.y];
            Piece p = origin.getPiece();
            origin.setPiece(null);
            board.getTiles()[end.x][end.y].setPiece(p);
            moveStart = null;
            changePlayer();
            if (Settings.paintGraphics()) {
                board.update();
            }
        }
    }

    private void changePlayer() {
        checkViktory();


        /**nollställer brädet **/
        ArrayList<Player> arL = Settings.getPlayers();
        int xTile = 0, yTile = 0;
        for (xTile = 0; xTile < Settings.getPlayboardSize(); xTile++) {//målar om hela brädet
            for (yTile = 0; yTile < Settings.getPlayboardSize(); yTile++) {
                board.getTiles()[xTile][yTile].setBackground(Color.lightGray);
                board.getTiles()[xTile][yTile].repaint();
            }
        }
        /**räknar ut vems person det är **/
        for (int i = 0; i < Settings.getNrOfPlayers(); i++) {
            if (arL.get(i).isItMyturn()) {
                arL.get(i).isNotMyTurn();
                if (Settings.getNrOfPlayers() - 1 != i) {
                    arL.get(i + 1).isMyTurn();
                    break;
                } else {
                    arL.get(0).isMyTurn();
                    break;
                }

            }
        }
    }

    private boolean checkViktory() {
        boolean win = true;
        ArrayList<Point> pices = model.getYoursPieces(board);
        int i = 0;
        for (Player player : Settings.getPlayers()) {
            if (player.isItMyturn()) {
                System.out.println("i: " +i);
                if (i == 0) {
                    for (Point point : model.getYoursPieces(board)) {
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
                    for (Point point : model.getYoursPieces(board)) {
                        if (point.y != 3) {
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
