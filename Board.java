import javax.swing.ImageIcon;
public class Board {
    // set up a 2D array of pieces
    private Piece[][] board;
    
    // Constructor
    public Board() {
        // board is an 8x8 array
        board = new Piece[8][8];

        // initialize the chess pieces 
        setupInitialPosition();
    }

    public boolean isInside(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }
    // used to initialize the board with pieces
    private void setupInitialPosition() {
        // clear board first
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                board[r][c] = null;
            }
        }

        // red pieces at the top (rows 0 and 1) 
        // red pawns on row 1
        for (int c = 0; c < 8; c++) {
            board[1][c] = new Pawn("red");
        }

        // red rooks
        board[0][0] = new Rook("red");
        board[0][7] = new Rook("red");

        // red knights
        board[0][1] = new Knight("red");
        board[0][6] = new Knight("red");

        // red bishops
        board[0][2] = new Bishop("red");
        board[0][5] = new Bishop("red");

        // red queen and king
        board[0][3] = new Queen("red");
        board[0][4] = new King("red");

        // blue pieces
        //board[3][4] = new Bulldog("blue");

        // blue pawns on row 6
        for (int c = 0; c < 8; c++) {
            board[6][c] = new Pawn("blue");
        }

        // blue rooks
        board[7][0] = new Rook("blue");
        board[7][7] = new Rook("blue");

        // blue knights
        board[7][1] = new Knight("blue");
        board[7][6] = new Knight("blue");

        // blue bishops
        board[7][2] = new Bishop("blue");
        board[7][5] = new Bishop("blue");

        // blue queen and king
        board[7][3] = new Queen("blue");
        board[7][4] = new King("blue");
    }
    
    // accesses piece on the specified square
    public Piece getPiece(int row, int col) {
        return board[row][col];
    }

    // assigns a given piece to a given square
    public void setPiece(int row, int col, Piece piece) {
        board[row][col] = piece;
    }

    // used to move a piece to given 'to' and 'from' coordinates
    public void movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        // piece at a given coordinate
        Piece p = getPiece(fromRow, fromCol);

        // piece reassigned to the second click
        board[toRow][toCol] = p;

        // initial square now has no piece
        board[fromRow][fromCol] = null;

        // updates boolean saying the piece has now moved
        if (p != null) {
            p.setHasMoved(true);
        }
    }
    
    // king methods for check and checkmate
    // find the king of the given color
    // check if king is currently in check
    public int[] findKing(String color) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = board[r][c];
                
                // if the piece resembles a King and the color is the same as the one asked for
                // return the location of the king
                if (p instanceof King && p.getColor().equals(color)) {
                    return new int[]{r, c};
                }
            }
        }
        // returns no position if the king is not on the board
        return null; 
    }

    public boolean isKingInCheck(String color) {
        // finds the location of the king
        int[] kingPos = findKing(color);

        // king is not in check if it is not on board
        if (kingPos == null) return false;
        
        // returns true if the square occupied by a king of any color is attacked by any enemy piece
        return isSquareAttacked(kingPos[0], kingPos[1], color);
    }

    // checks the cardinal directions from an initial position to an ending position to make sure 
    // no piece is in between each coordinate 
    private boolean isPathClearStraight(int fromRow, int fromCol, int toRow, int toCol) {
        // increment or decrement setup for row and col dependent on the initial position and the destination
        int rowDiff = Integer.compare(toRow, fromRow);
        int colDiff = Integer.compare(toCol, fromCol);

        // initializes the row and col need to check
        int r = fromRow + rowDiff;
        int c = fromCol + colDiff;

        // as long as the destination has not been reached, keep checking the next square
        while (r != toRow || c != toCol) {
            // if the square has a piece on it, it interferes with the piece user is trying to move
            if (board[r][c] != null) return false;
        
            r += rowDiff;
            c += colDiff;
        }
        return true;
    }

    // checks the diagonal directions from an initial position to an ending position to make sure
    // no piece is in between each coordinate
    private boolean isPathClearDiagonal(int fromRow, int fromCol, int toRow, int toCol) {
        // increment or decrement setup for row and col dependent on the initial position and the destination
        int rowDiff = (toRow > fromRow) ? 1 : -1;
        int colDiff = (toCol > fromCol) ? 1 : -1;

        // initializes the row and col need to check
        int r = fromRow + rowDiff;
        int c = fromCol + colDiff;

        // as long as the destination has not been reached, keep checking the next square
        while (r != toRow && c != toCol) {
            // if the square has a piece on it, it interferes with the piece user is trying to move
            if (board[r][c] != null) return false;
            
            r += rowDiff;
            c += colDiff;
        }
        return true;
    }

    // checks if the king square of any color is being targeted by another piece
    public boolean isSquareAttacked(int row, int col, String kingColor) {
        String enemyColor = kingColor.equals("blue") ? "red" : "blue";

        // loops through entire board
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                // gets piece at a certan square
                Piece p = getPiece(r, c);
                
                // if there is no piece, continue onto next iteration
                if (p == null) continue;

                // if the piece if the same color as king, continue onto next iteration
                if (!p.getColor().equals(enemyColor)) continue;

                // pawns
                if (p instanceof Pawn) {
                    // orient the direction the pawn can move in by checking the color
                    int dir = p.getColor().equals("blue") ? -1 : 1;
                    
                    // pawns attack diagonally so check the squares up and the left and right of each pawn
                    if (row == r + dir && (col == c + 1 || col == c - 1)) {
                        return true;
                    }
                }

                // knights
                else if (p instanceof Knight) {
                    // assign the change in rows and cols getting absolute difference between given 
                    // coordinates of king and coordinates of the knight
                    int dr = Math.abs(row - r);
                    int dc = Math.abs(col - c);
                    
                    // knights can jump in L chapes in 8 directions so account for all possibilities
                    // by making sure the coordinates of (dr, dc) are (2,1) or (1,2)
                    if (dr * dc == 2) {
                        return true;
                    }
                }

                // kings adjacent moves
                else if (p instanceof King) {
                    // assigning change in rows and cols getting absolute difference between given
                    // coordinates of king and coordinates of the king being checked
                    int dr = Math.abs(row - r);
                    int dc = Math.abs(col - c);
                    
                    // kings can move one square orthogonally so account for all 8 possibilities
                    if (dr <= 1 && dc <= 1 && (dr * dc <= 1)) {
                        return true;
                    }
                }

                // bishops + diagonal queen moves
                else if (p instanceof Bishop || p instanceof Queen) {
                    // assign the change in rows and cols (not absolute change since pieces can move backwards)
                    int dr = row - r;
                    int dc = col - c;

                    // there will always be a change in rows and check if the change in rows and cols is the same
                    if (Math.abs(dr) == Math.abs(dc) && dr != 0) {
                        // check if the diagonal path is clear up to king
                        if (isPathClearDiagonal(r, c, row, col)) {
                            return true;
                        }
                    }
                }

                // rooks + straight-queen moves
                if (p instanceof Rook || p instanceof Queen) {
                    // check the rows or cols in cardinal directions to the piece
                    if (r == row || c == col) {
                        // check if the direct path is clear up to the king
                        if (isPathClearStraight(r, c, row, col)) {
                            return true;
                        }
                    }
                }
            }
        }
        // the king square is not being attacked if all tests fail
        return false;
    }

    public boolean hasAnyLegalMove(String color) {
        // loops through entire board
        for (int fromRow = 0; fromRow < 8; fromRow++) {
            for (int fromCol = 0; fromCol < 8; fromCol++) {
                // gets a piece at certain square
                Piece piece = board[fromRow][fromCol];
                
                // if there is no piece, continue onto next iteration
                if (piece == null) continue;

                // if the piece if the same color as king, continue onto next iteration
                if (!piece.getColor().equals(color)) continue;

                // loop through entire board again but now checking if any valid destinations exist
                for (int toRow = 0; toRow < 8; toRow++) {
                    for (int toCol = 0; toCol < 8; toCol++) {
                        // if the square being looped through is the same, continue onto next iteration
                        if (fromRow == toRow && fromCol == toCol) continue;

                        // set up a target square as the destination square in current iteration 
                        Piece target = board[toRow][toCol];

                        // if a target exists and it is the same color as the piece being check, continue onto next iteration 
                        if (target != null && target.getColor().equals(color)) continue;

                        // if the move is not valid for a piece, continue onto next iteration
                        if (!piece.isValidMove(this, fromRow, fromCol, toRow, toCol)) continue;

                        // simulate the move
                        board[fromRow][fromCol] = null;
                        board[toRow][toCol] = piece;
                        boolean kingInCheck = isKingInCheck(color);
                        // undo simulated move
                        board[fromRow][fromCol] = piece;
                        board[toRow][toCol] = target;
                        // if the king is not in check, tell user that there are legal moves
                        if (!kingInCheck) {
                            System.out.println(
                                "[hasAnyLegalMove] " + color + " has legal move: " +
                                piece.getType() + " from (" + fromRow + "," + fromCol +
                                ") to (" + toRow + "," + toCol + ")"
                            );
                            return true;
                        }
                    }
                }
            }
        }
        
        // tell user that there are no legal moves if all tests fail
        System.out.println("[hasAnyLegalMove] " + color + " has NO legal moves.");
        return false;
    }

    // checks if the king is still on the board (specifically for bomb chess since this is the win condition)
    public boolean isKingOnBoard(String color) {
        // king initialized to exist
        boolean kingExists = true;

        // loop through entire board and check if a king exists on any of the squares
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece p = board[row][col];
                if (board[row][col] instanceof King && p.getColor().equals(color)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    // checks if any piece of a certain color is still on the board (specifically for capture chess since the win   condition is 
    // if one color has no pieces left)
    public boolean piecesOnBoard(String color) {
        // initialize pieces to not exist on board
        boolean piecesExist = false;
        
        // loops through entire board and check if any square has a piece
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece p = board[row][col];
                if (board[row][col] instanceof Piece && p.getColor().equals(color)) {
                    return true;
                }
            }
        }

        return false;
    }
}
