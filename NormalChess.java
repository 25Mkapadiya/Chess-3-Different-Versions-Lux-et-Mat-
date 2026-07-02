import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NormalChess {
    protected static final int BOARD_SIZE = 8;
    
    // set up variables that define the frame and panel
    protected ChessBoardGUI chessBoardGUI;

    protected JPanel boardPanel;
    protected JButton[][] boardSquares;
    protected JFrame frame;

    protected Board board;
    protected String currentPlayer = "blue";

    // -1 shows that nothing is selected
    protected int selectedRow = -1;
    protected int selectedCol = -1;

    // consructor
    public NormalChess() {
        //instance board with setup pieces
        board = new Board(); 

        // get access to the frame
        this.chessBoardGUI = chessBoardGUI;

        // initialize the panel as an 8x8 grid of buttons
        boardPanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        boardSquares = new JButton[BOARD_SIZE][BOARD_SIZE];

        // initialize each button
        initBoardButtons();

        // reload the buttons 
        refreshButtonsFromBoard();
    }

    public JPanel getBoardPanel() {
        return boardPanel;
    }

    protected void initBoardButtons() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                JButton square = new JButton();
                square.setMargin(new Insets(0, 0, 0, 0));
                square.setFont(new Font("SansSerif", Font.BOLD, 16));

                // Color the squares like a chessboard
                if ((row + col) % 2 == 0) {
                    square.setBackground(Color.WHITE);
                } else {
                    square.setBackground(Color.GRAY);
                }

                // Store rows and columns inside the button
                final int r = row;
                final int c = col;
                square.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        handleClick(r, c);
                    }
                });

                boardSquares[row][col] = square;
                boardPanel.add(square);

                boardPanel.repaint();
            }
        }
    }

    //Sync button labels with the piece element in Board.
    protected void refreshButtonsFromBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Piece p = board.getPiece(row, col);
                JButton square = boardSquares[row][col];

                if (p == null) {
                    square.setIcon(null);
                    square.setText("");
                } else {
                    square.setIcon(p.getIcon());
                    square.setText("");
                }
            }
        }
    }
    
    // called when the squares are clicked
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
        // show the move coordinates
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

        String opponent = currentPlayer.equals("blue") ? "red" : "blue";

        boolean oppInCheck  = board.isKingInCheck(opponent);
        boolean oppHasMoves = board.hasAnyLegalMove(opponent);
        System.out.println("oppInCheck=" + oppInCheck + ", oppHasMoves=" + oppHasMoves);

        
        
        if (oppInCheck && !oppHasMoves) {
            showMessage(chessBoardGUI, opponent.toUpperCase() + " is in CHECKMATE! " + currentPlayer.toUpperCase() + " wins.");
            
            //JOptionPane.showMessageDialog(frame,
            //        opponent.toUpperCase() + " is in CHECKMATE! " +
            //        currentPlayer.toUpperCase() + " wins.");
        } else if (oppInCheck) {
            showMessage(chessBoardGUI, opponent.toUpperCase() + " is in CHECK.");

            //JOptionPane.showMessageDialog(frame,
            //       opponent.toUpperCase() + " is in CHECK.");
        } else if (!oppHasMoves) {
            showMessage(chessBoardGUI, "STALEMATE! " + opponent.toUpperCase() + " has no legal moves.");

            //JOptionPane.showMessageDialog(frame,
            //       "STALEMATE! " + opponent.toUpperCase() + " has no legal moves.");
        }
        
        currentPlayer = opponent;
        refreshButtonsFromBoard();
    }

    public void showMessage(ChessBoardGUI chessBoardGUI, String messageToDisplay) {
        JOptionPane.showMessageDialog(chessBoardGUI, messageToDisplay);
    }

    /*
    public static void main(String[] args) {
      SwingUtilities.invokeLater(() -> new NormalChess());
    }
    */
}


