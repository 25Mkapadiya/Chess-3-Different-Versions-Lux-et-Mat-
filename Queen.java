import javax.swing.ImageIcon;
public class Queen extends Piece{
    public Queen(String color) {
        super("queen", color);
    }
    
    public ImageIcon getIcon() {
        if (color.equals("blue")) {
            return BLUE_ICON;
        }
        else return RED_ICON;
    }

    public boolean isValidMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        if(!board.isInside(toRow, toCol)) {
            return false;
        }

        int rowDiff = toRow - fromRow;
        int colDiff = toCol - fromCol;

        // Cannot stay in the same place
        if (rowDiff == 0 && colDiff == 0) {
            return false;
        }

        // Movement type 
        boolean isStraight = (rowDiff == 0 || colDiff == 0);
        boolean isDiagonal = Math.abs(rowDiff) == Math.abs(colDiff);

        if (!isStraight && !isDiagonal) {
            return false;  // queen can only move straight or diagonal
        }

        // Determine direction of movement for path checking
        int stepRow = Integer.compare(rowDiff, 0); // -1, 0, or 1
        int stepCol = Integer.compare(colDiff, 0);

        int r = fromRow + stepRow;
        int c = fromCol + stepCol;

        // Path check (no jumping over pieces)
        while (r != toRow || c != toCol) {
            if (board.getPiece(r, c) != null) {
                return false;  // meaning it is blocked
            }
            r += stepRow;
            c += stepCol;
        }

        // Final square must be empty or have an enemy piece (or can't have bulldog)
        Piece target = board.getPiece(toRow, toCol);
            if (target != null && (target.getColor().equals(this.color) || target instanceof Bulldog)) {
            return false;
        }
        return true;
    }


    private static final ImageIcon BLUE_ICON = loadIcon("resources/chess/blue_queen.png");

    private static final ImageIcon RED_ICON = loadIcon("resources/chess/red_queen.png");

    private static ImageIcon loadIcon(String path) {
        return new ImageIcon(path);
    }
    }
