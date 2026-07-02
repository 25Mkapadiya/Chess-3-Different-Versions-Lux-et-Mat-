import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CaptureChess extends NormalChess {

    public CaptureChess() {
        //instance board with setup pieces
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

        if (!board.isInside(fromRow, fromCol) || !board.isInside(toRow, toCol)) return;

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
        
        // simulate to enforce king safety
        Piece fromBackup = piece;
        Piece toBackup = target;



        board.setPiece(fromRow, fromCol, fromBackup);
        board.setPiece(toRow, toCol, toBackup);


        // apply move
        board.movePiece(fromRow, fromCol, toRow, toCol);

        String opponent = currentPlayer.equals("blue") ? "red" : "blue";


        if (!board.piecesOnBoard(opponent)) {
            showMessage(chessBoardGUI, currentPlayer.toUpperCase() + " WINS! " + opponent.toUpperCase() + " has no pieces left.");
        } else if (!board.piecesOnBoard(currentPlayer)) {
            showMessage(chessBoardGUI, opponent.toUpperCase() + " WINS! "  + currentPlayer.toUpperCase() + " has no pieces left.");
        }

        currentPlayer = opponent;
        refreshButtonsFromBoard();
    }

    public void showMessage(ChessBoardGUI chessBoardGUI, String messageToDisplay) {
        JOptionPane.showMessageDialog(chessBoardGUI, messageToDisplay);
    }
}
