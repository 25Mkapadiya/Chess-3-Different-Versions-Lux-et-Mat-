import javax.swing.ImageIcon;
public class Pawn extends Piece {

    public Pawn(String color) {
        super("pawn", color);
    }

    public ImageIcon getIcon() {
        if (color.equals("blue")) {
            return BLUE_ICON;
        }
        else return RED_ICON;
    }

    public boolean isValidMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {

        // direction of movement:
        // blue goes up (toward row 0): -1
        // red goes down (toward row 7): +1
        int dir = color.equals("blue") ? -1 : 1;

        int rowDiff = toRow - fromRow;
        int colDiff = toCol - fromCol;

        // Target must be on the board
        if (!board.isInside(toRow, toCol)) {
            return false;
        }

        // Can't stay on the same square
        if (rowDiff == 0 && colDiff == 0) {
            return false;
        }

        Piece target = board.getPiece(toRow, toCol);

        // Straight moves (no capturing) 
        if (colDiff == 0) {
            // One square forward
            if (rowDiff == dir) {
                // Must be empty
                return target == null;
            }

            // Two squares from starting rank
            int startRow = color.equals("blue") ? 6 : 1;

            if (!hasMoved && fromRow == startRow && rowDiff == 2 * dir) {
                int intermediateRow = fromRow + dir;
                // path must be clear and destination empty
                if (board.getPiece(intermediateRow, fromCol) == null &&
                    target == null) {
                    return true;
                }
            }

            return false;
        }

        // Diagonal capture 
        if (Math.abs(colDiff) == 1 && rowDiff == dir) {
            if (target == null)
            return false;
            if (target instanceof Bulldog)
            return false;
            return !target.getColor().equals(this.color);
        }

        // where to check for promotion and where to update piece?
        // promotion(board, toRow, toCol);        

        // Any other movement is illegal for a pawn
        return false;
    }




    private static final ImageIcon BLUE_ICON = loadIcon("resources/chess/blue_pawn.png");

    private static final ImageIcon RED_ICON = loadIcon("resources/chess/red_pawn.png");

    private static ImageIcon loadIcon(String path) {
        return new ImageIcon(path);
    }

}
