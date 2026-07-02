import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BulldogChess extends NormalChess {

    private Bulldog bulldog;
    private int bulldogRow;
    private int bulldogCol;
    private boolean bulldogMove = false;


    public BulldogChess() {
        //instance board with setup pieces
        //initialize handsome dan on a random square 3
        board = new Board(); 
        bulldog = new Bulldog();
        bulldogRow = 3;
        bulldogCol = 3;
        board.setPiece(bulldogRow, bulldogCol, bulldog);
        refreshButtonsFromBoard();
    }
    
    public JPanel getBoardPanel() {
        return boardPanel;
    }

    protected void handleClick(int row, int col) {
        if (bulldogMove) {
            handleBulldogClick(row, col);
        } else {
            // normal selection/move but don't allow selecting bulldog
            if (selectedRow == -1) {
                Piece p = board.getPiece(row, col);
                if (p != null && p.getColor().equals(currentPlayer) && !(p instanceof Bulldog)) {
                    selectedRow = row;
                    selectedCol = col;
                    System.out.println("Selected: " + p.getType() + " at (" + row + "," + col + ")");
                } else {
                    System.out.println("No selectable piece at (" + row + "," + col + ")");
                }
            } else {
                tryMove(selectedRow, selectedCol, row, col);
                selectedRow = -1;
                selectedCol = -1;
            }
        }
    }

    private void handleBulldogClick(int row, int col) {
        // bulldog must go on empty square
        if (board.getPiece(row, col) != null) {
            System.out.println("Bulldog must go on an empty square.");
            return;
        }

        // move the bulldog piece
        board.setPiece(bulldogRow, bulldogCol, null);
        board.setPiece(row, col, bulldog);
        bulldogRow = row;
        bulldogCol = col;

        bulldogMove = false;

        // now evaluate check/checkmate/stalemate, then switch turn
        String opponent = currentPlayer.equals("blue") ? "red" : "blue";

        boolean oppInCheck  = board.isKingInCheck(opponent);
        boolean oppHasMoves = board.hasAnyLegalMove(opponent);
          
        if (oppInCheck && !oppHasMoves) {
            JOptionPane.showMessageDialog(boardPanel,
                    opponent.toUpperCase() + " is in CHECKMATE! " +
                    currentPlayer.toUpperCase() + " wins.");
        } else if (oppInCheck) {
            JOptionPane.showMessageDialog(boardPanel,
                    opponent.toUpperCase() + " is in CHECK.");
        } else if (!oppHasMoves) {
            JOptionPane.showMessageDialog(boardPanel,
                    "STALEMATE! " + opponent.toUpperCase() + " has no legal moves.");
        }
        

        currentPlayer = opponent;
        refreshButtonsFromBoard();
    }

    public void showMessage(ChessBoardGUI chessBoardGUI, String messageToDisplay) {
        JOptionPane.showMessageDialog(chessBoardGUI, messageToDisplay);
    }

    protected void tryMove(int fromRow, int fromCol, int toRow, int toCol) {

        if (!board.isInside(fromRow, fromCol) || !board.isInside(toRow, toCol)) return;

        Piece piece = board.getPiece(fromRow, fromCol);
        if (piece == null) return;

        if (!piece.getColor().equals(currentPlayer)) {
            System.out.println("Not your turn!");
            return;
        }

        if (piece instanceof Bulldog) {
            System.out.println("You cannot move the bulldog as a normal piece.");
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

        System.out.println("Trying move " + piece.getType() +
                " from (" + fromRow + "," + fromCol + ") to (" + toRow + "," + toCol + ")");

        // simulate to enforce king safety
        Piece fromBackup = piece;
        Piece toBackup = target;

        board.setPiece(fromRow, fromCol, null);
        board.setPiece(toRow, toCol, fromBackup);

        boolean kingStillInCheck = board.isKingInCheck(currentPlayer);

        board.setPiece(fromRow, fromCol, fromBackup);
        board.setPiece(toRow, toCol, toBackup);

        if (kingStillInCheck) {
            System.out.println("Move leaves king in check; not allowed.");
            return;
        }

        // apply move
        board.movePiece(fromRow, fromCol, toRow, toCol);
        refreshButtonsFromBoard();

        // Bulldog Chess difference
        // Don't switch players or check checkmate yet.
        // Now the same player must move the bulldog.
        bulldogMove = true;
        System.out.println(currentPlayer + " must now move the bulldog.");
    }
}
