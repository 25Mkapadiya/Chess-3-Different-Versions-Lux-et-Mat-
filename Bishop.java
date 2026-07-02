import javax.swing.ImageIcon;

public class Bishop extends Piece {

    private static final ImageIcon BLUE_ICON = loadIcon("resources/chess/blue_bishop.png");
    private static final ImageIcon RED_ICON  = loadIcon("resources/chess/red_bishop.png");

    public Bishop(String color) {
        super("bishop", color);
    }

    public ImageIcon getIcon() {
        return color.equals("blue") ? BLUE_ICON : RED_ICON;
    }

    public boolean isValidMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        if (!board.isInside(toRow, toCol)) return false;

        int rowDiff = toRow - fromRow;
        int colDiff = toCol - fromCol;

        // cannot stay in place
        if (rowDiff == 0 && colDiff == 0) return false;

        // must move diagonally
        if (Math.abs(rowDiff) != Math.abs(colDiff)) return false;

        // determine direction
        int stepRow = Integer.compare(rowDiff, 0); // -1 or 1
        int stepCol = Integer.compare(colDiff, 0); // -1 or 1

        int r = fromRow + stepRow;
        int c = fromCol + stepCol;

        // path must be clear
        while (r != toRow || c != toCol) {
            if (board.getPiece(r, c) != null) {
                return false;
            }
            r += stepRow;
            c += stepCol;
        }

        // final square: empty or enemy piece
        Piece target = board.getPiece(toRow, toCol);
        if (target != null && (target.getColor().equals(this.color) || target instanceof Bulldog)) {
        return false;
        }
        return true;
    }

    public static ImageIcon loadIcon(String path) {
        return new ImageIcon(path);
    }
}
