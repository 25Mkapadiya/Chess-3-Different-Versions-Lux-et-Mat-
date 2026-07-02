import javax.swing.ImageIcon;

public class Knight extends Piece {

    //Icons
    private static final ImageIcon BLUE_ICON = loadIcon("resources/chess/blue_knight.png");
    private static final ImageIcon RED_ICON = loadIcon("resources/chess/red_knight.png");

    public Knight(String color) {
        super("knight", color);
    }

    public ImageIcon getIcon() {
        return color.equals("blue") ? BLUE_ICON : RED_ICON;
    }

    public boolean isValidMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {

        if (!board.isInside(toRow, toCol)) return false;

        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);

        // L-shape
        if (!((rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2))) return false;

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
