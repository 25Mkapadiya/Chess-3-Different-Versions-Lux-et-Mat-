import javax.swing.ImageIcon;

public class King extends Piece {

    // Icons
    private static final ImageIcon BLUE_ICON = loadIcon("resources/chess/blue_king.png");
    private static final ImageIcon RED_ICON  = loadIcon("resources/chess/red_king.png");

    public King(String color) {
        super("king", color);
    }

    
    public ImageIcon getIcon() {
        return color.equals("blue") ? BLUE_ICON : RED_ICON;
    }

    public boolean isValidMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {

        // must be on board
        if (!board.isInside(toRow, toCol)) {
            return false;
        }

        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);

        // cannot stay put
        if (rowDiff == 0 && colDiff == 0) {
            return false;
        }

        // king can only move 1 square in any direction (no castling for now)
        if (rowDiff > 1 || colDiff > 1) {
            return false;
        }

        // can't capture own piece or bulldog 
        Piece target = board.getPiece(toRow, toCol);
        if (target != null && (target.getColor().equals(this.color) || target instanceof Bulldog)) {
        return false;
        }

        return true;
    }

    private static ImageIcon loadIcon(String path) {
        return new ImageIcon(path);
    }
}
