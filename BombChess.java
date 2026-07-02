import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BombChess extends NormalChess {
    
    public BombChess() {
        // instance board with setup pieces
        board = new Board(); 
        refreshButtonsFromBoard();
    }

    public JPanel getBoardPanel() {
        return boardPanel;
    }

    //Called when the squares are clicked
    protected void handleClick(int row, int col) {
        if (selectedRow == -1) {
            // First click: select a piece
            Piece p = board.getPiece(row, col);
            if (p != null && p.getColor().equals(currentPlayer)) {
                selectedRow = row;
                selectedCol = col;
                System.out.println("Selected: " + p.getType() + " at (" + row + "," + col + ")");
            } else {
                System.out.println("No selectable piece at (" + row + "," + col + ")");
            }
        } else {
            // Second click: try to move selected piece
            tryMove(selectedRow, selectedCol, row, col);
            selectedRow = -1;
            selectedCol = -1;
        }
    }

    protected void tryMove(int fromRow, int fromCol, int toRow, int toCol) {

        // Don't do anything if any clicks are outside of the board
        if (!board.isInside(fromRow, fromCol) || !board.isInside(toRow, toCol)) return;

        // Access the piece located
        Piece piece = board.getPiece(fromRow, fromCol);
        if (piece == null) return;

        if (!piece.getColor().equals(currentPlayer)) {
            System.out.println("Not your turn!");
            return;
        }

        Piece target = board.getPiece(toRow, toCol);
        if (target != null && target.getColor().equals(currentPlayer)) {
            System.out.println("Cannot capture your own piece.");
            return;
        }

        if (!piece.isValidMove(board, fromRow, fromCol, toRow, toCol)) {
            System.out.println("Illegal move for " + piece.getType());
            return;
        }
        // DEBUG: show the move coordinates
        System.out.println("Trying move " + piece.getType() +
                   " from (" + fromRow + "," + fromCol + ") to (" + toRow + "," + toCol + ")");
        
        // apply move
        board.movePiece(fromRow, fromCol, toRow, toCol);

        if (target != null) {
            explodeAt(toRow, toCol);
        }

        String opponent = currentPlayer.equals("blue") ? "red" : "blue";

        boolean oppInCheck  = board.isKingInCheck(opponent);
        boolean oppHasMoves = board.hasAnyLegalMove(opponent);
        System.out.println("oppInCheck=" + oppInCheck + ", oppHasMoves=" + oppHasMoves);


        // Made this method to check if king is alive in Board.java
        if (!board.isKingOnBoard(currentPlayer)) {
            showMessage(chessBoardGUI, opponent.toUpperCase() + " WINS! (" + currentPlayer.toUpperCase() + " king exploded)");
        } else if (!board.isKingOnBoard(opponent)) {
            showMessage(chessBoardGUI, currentPlayer.toUpperCase() + " WINS! (" + opponent.toUpperCase() + " king exploded)");
        }

        currentPlayer = opponent;
        refreshButtonsFromBoard();
    }

    protected void explodeAt(int row, int col) {
        int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dc = {-1,  0,  1, -1, 1, -1,0, 1};

        // squares to destroy: center + 8 neighbors
        java.util.List<Point> squares = new java.util.ArrayList<>();

        squares.add(new Point(row, col));
        for (int i = 0; i < 8; i++) {
            int r = row + dr[i];
            int c = col + dc[i];
            if (board.isInside(r, c)) {
                squares.add(new Point(r, c));
            }
        }

        boolean blueKingKilled = false;
        boolean redKingKilled = false;

        for (Point p : squares) {
            Piece piece = board.getPiece(p.x, p.y);
            if (piece != null) {
                if (piece.getType().equals("King")) {
                    if (piece.getColor().equals("blue")) blueKingKilled = true;
                    else redKingKilled = true;
                }
            }
            board.setPiece(p.x, p.y, null);
        }

        refreshButtonsFromBoard();

    }

    public void showMessage(ChessBoardGUI chessBoardGUI, String messageToDisplay) {
        JOptionPane.showMessageDialog(chessBoardGUI, messageToDisplay);
    }

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BombChess());
    }

}
