import javax.swing.ImageIcon;
public class Rook extends Piece{
    public Rook(String color) {
        super("rook", color);
    }
    
    public ImageIcon getIcon() {
        if (color.equals("blue")) {
            return BLUE_ICON;
        }
        else return RED_ICON;
    }

    public boolean isValidMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        if (!board.isInside(toRow, toCol)) {
            return false;
        }

        int rowDiff = toRow - fromRow;
        int colDiff = toCol - fromCol;

        // Must move in a straight line
        if (rowDiff != 0 && colDiff != 0) {
            return false;
        }

        // Don't allow staying in place
        if (rowDiff == 0 && colDiff == 0) {
            return false;
        }

        // Determine direction of movement
        int stepRow = Integer.compare(rowDiff, 0); 
        int stepCol = Integer.compare(colDiff, 0);

        int r = fromRow + stepRow;
        int c = fromCol + stepCol;

        // Check path is clear (no pieces in between)
        while (r != toRow || c != toCol) {
            if (board.getPiece(r, c) != null) {
                return false; // blocked
            }
            r += stepRow;
            c += stepCol;
        }

        // Final square: must be empty or contain enemy piece
        Piece target = board.getPiece(toRow, toCol);
            if (target != null && (target.getColor().equals(this.color) || target instanceof Bulldog)) {
            return false;
        }
        return true;
    }


    private static final ImageIcon BLUE_ICON = loadIcon("resources/chess/blue_rook.png");

    private static final ImageIcon RED_ICON = loadIcon("resources/chess/red_rook.png");

    private static ImageIcon loadIcon(String path) {
        return new ImageIcon(path);
    }
}

