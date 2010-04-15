package mushai;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;

/**
 * Controller class for game logic.
 */
public class Controller implements ActionListener {

    private Playboard board;
    private Window win;
    /**
     * The starting point for ongoing move
     */
    private Point moveStart;
    private ArrayList<Point> moves = new ArrayList<Point>();

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
        if (((JButton) e.getSource()).getText().equals("end turn")) {
            // när du trycker på end turn kommer du från första i listan till sista
            move(moves.get(0), moves.get(moves.size() - 1));
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



            System.out.println("x: " + xTile + " y: " + yTile);
            if (t.getPiece() != null) { // is piece on tile
                if (moves.isEmpty()) {
                    moves.add(new Point(xTile, yTile));
                } else {
                    t.setBackground(Color.RED);

                }
            } else {
                if (!moves.isEmpty()) {
                    canIMoveThere(moves.get(moves.size() - 1), new Point(xTile, yTile));
                }
            }

        }
    }

    private void move(Point start, Point end) {
        Tile origin = board.getTiles()[start.x][start.y];
        Piece p = origin.getPiece();
        origin.setPiece(null);
        board.getTiles()[end.x][end.y].setPiece(p);
        moveStart = null;
        changePlayer();

        board.update();
    }

    public void changePlayer() {


        /**nollställer brädet **/
        ArrayList<Player> arL = Settings.getPlayers();
        moves=new ArrayList<Point>();

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

    private void canIMoveThere(Point from, Point to) {

        System.out.println("from:" + from + "  to:" + to + "   size of moves:" + moves.size() + "bord size:" + board.getTiles().length);
        Square pi = (Square) board.getTiles()[moves.get(0).x][moves.get(0).y].getPiece();
        pi.updateMoves();

        for (Point tempPoint : pi.getMoves()) {
            boolean endTurn=true;

            Point newPoint = new Point(from.x + tempPoint.x, from.y + tempPoint.y);
            if (newPoint.x + tempPoint.x >= 0 && newPoint.x + tempPoint.x < Settings.getPlayboardSize()) {
                if (newPoint.y + tempPoint.y >= 0 && newPoint.y + tempPoint.y < Settings.getPlayboardSize()) {

                    if (board.getTiles()[newPoint.x][newPoint.y].getPiece() != null) {
                        newPoint = new Point(newPoint.x + tempPoint.x, newPoint.y + tempPoint.y);
                        endTurn=false;
                    }
                }
            }
            if (newPoint.equals(to)) {
                if(moves.size()<2 || !endTurn){
                    moves.add(to);
                }
                if(endTurn){
                    move(moves.get(0), moves.get(moves.size() - 1));
                }
                // drawLine mellan dessa rutor
            }
        }
    }
}
