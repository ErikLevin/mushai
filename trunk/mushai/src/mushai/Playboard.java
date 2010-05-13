package mushai;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.JPanel;
import minmax.PlayboardModel;

/**
 * Graphical representation of the game board.
 */
public class Playboard extends JPanel {

    private Tile[][] tiles;

    public Playboard() {
        this(8, 8, 10);
    }

    public Playboard(int noPlayer1Pieces, int noPlayer2Pieces, int size) {
        super();

        int ps = size;
        Settings.setPlayboardSize(size);
        tiles = new Tile[ps][ps];
        setLayout(new GridLayout(ps, ps));
        this.setPreferredSize(new Dimension(200, 200));
        for (int j = 0; j < ps; j++) {
            for (int i = 0; i < ps; i++) {
                tiles[i][j] = new Tile();
                add(tiles[i][j]);
            }
        }

        resetBoard(noPlayer1Pieces, noPlayer2Pieces);
    }
    public Playboard(int[][] board){
        int length = board.length;
        tiles = new Tile[length][length];
        setLayout(new GridLayout(length,length));
        this.setPreferredSize(new Dimension(200,200));
        for (int j = 0; j < length; j++){
            for (int i = 0; i < length; i++){
                tiles[i][j] = new Tile();
                if (board[i][j] == PlayboardModel.PLAYER1_SQUARE)
                    tiles[i][j].setPiece(new Square(Settings.getPlayers().get(0).getColor(), DOWNWARDS));
                else if (board[i][j] == PlayboardModel.PLAYER2_SQUARE)
                    tiles[i][j].setPiece(new Square(Settings.getPlayers().get(1).getColor(), UPWARDS));
                else if (board[i][j] == PlayboardModel.PLAYER1_CIRCLE)
                    tiles[i][j].setPiece(new Circle(Settings.getPlayers().get(0).getColor(), DOWNWARDS));
                else if (board[i][j] == PlayboardModel.PLAYER2_CIRCLE)
                    tiles[i][j].setPiece(new Circle(Settings.getPlayers().get(1).getColor(), UPWARDS));
                else if (board[i][j] == PlayboardModel.PLAYER1_RHOMBUS)
                    tiles[i][j].setPiece(new Rhombus(Settings.getPlayers().get(0).getColor(), DOWNWARDS));
                else if (board[i][j] == PlayboardModel.PLAYER2_RHOMBUS)
                    tiles[i][j].setPiece(new Rhombus(Settings.getPlayers().get(1).getColor(), UPWARDS));
                else if (board[i][j] == PlayboardModel.PLAYER1_TRIANGLE)
                    tiles[i][j].setPiece(new Triangle(Settings.getPlayers().get(0).getColor(), DOWNWARDS));
                else if (board[i][j] == PlayboardModel.PLAYER2_TRIANGLE)
                    tiles[i][j].setPiece(new Triangle(Settings.getPlayers().get(1).getColor(), UPWARDS));
                add(tiles[i][j]);
            }
        }
        update();
    }
    public Playboard(int noPlayer1Pieces, int noPlayer2Pieces) {
        super();

        int ps = Settings.getPlayboardSize();
        tiles = new Tile[ps][ps];
        setLayout(new GridLayout(ps, ps));
        this.setPreferredSize(new Dimension(200, 200));
        for (int j = 0; j < ps; j++) {
            for (int i = 0; i < ps; i++) {
                tiles[i][j] = new Tile();
                add(tiles[i][j]);
            }
        }

        resetBoard(noPlayer1Pieces, noPlayer2Pieces);
    }
    /**
     * Initializes the Playboard, only with Square pieces for now.
     *
     * @param noPlayer1Pieces - Number of pieces that player 1 controls
     * @param noPlayer2Pieces - Number of pieces that player 2 controls
     */
    int DOWNWARDS = -1;
    int UPWARDS = 1;

    public void resetBoard(int noPlayer1Pieces, int noPlayer2Pieces) {
        clearBoard();
        noPlayer1Pieces = Settings.getPlayboardSize();
        noPlayer2Pieces = Settings.getPlayboardSize();

        for (int i = 1; i < noPlayer1Pieces - 1; i++) {


            if (i % 4 == 0) {
                tiles[i][0].setPiece(new Square(Settings.getPlayers().get(0).getColor(), DOWNWARDS));
            } else if (i % 4 == 1) {
                tiles[i][0].setPiece(new Triangle(Settings.getPlayers().get(0).getColor(), DOWNWARDS));
            } else if (i % 4 == 2) {
                tiles[i][0].setPiece(new Rhombus(Settings.getPlayers().get(0).getColor(), DOWNWARDS));
            } else {
                tiles[i][0].setPiece(new Circle(Settings.getPlayers().get(0).getColor(), DOWNWARDS));
            }
        }

        for (int i = 1; i < noPlayer2Pieces - 1; i++) {
            if (i % 4 == 3) {
                tiles[i][Settings.getPlayboardSize() - 1].setPiece(
                        new Square(Settings.getPlayers().get(1).getColor(), UPWARDS));
            } else if (i % 4 == 2) {
                tiles[i][Settings.getPlayboardSize() - 1].setPiece(
                        new Triangle(Settings.getPlayers().get(1).getColor(), UPWARDS));
            } else if (i % 4 == 1) {
                tiles[i][Settings.getPlayboardSize() - 1].setPiece(
                        new Rhombus(Settings.getPlayers().get(1).getColor(), UPWARDS));
            } else {
                tiles[i][Settings.getPlayboardSize() - 1].setPiece(
                        new Circle(Settings.getPlayers().get(1).getColor(), UPWARDS));
            }
        }
        update();
    }

    /**
     * Updates the Playboard so that pieces are shown in their correct places.
     * To be executed each time a move has been made.
     */
    public void update() {
        for (int j = 0; j < Settings.getPlayboardSize(); j++) {
            for (int i = 0; i < Settings.getPlayboardSize(); i++) {
                tiles[i][j].update(i, j);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Tile[] ts : tiles) {
            for (Tile t : ts) {
                t.paintComponents(g);
            }
        }
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    @Override
    public String toString() {
        String s = "";

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[j][i].getPiece() == null) {
                    s += "#";
                } else {
                    s += tiles[j][i].getPiece();
                }
                s += "\t";
            }
            s += "\n";
        }

        return s;
    }

    private void clearBoard() {
        for (Tile[] ts : tiles) {
            for (Tile t : ts) {
                t.setPiece(null);
            }
        }
        update();
    }
}
