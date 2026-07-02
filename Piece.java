import javax.swing.ImageIcon;
public abstract class Piece {
    protected String type;   //piece type
    protected String color;  // blue or red
    protected boolean hasMoved;

    // constructor for a piece
    public Piece(String type, String color) {
        this.type = type;
        this.color = color;
        this.hasMoved = false;
    }

    public String getType() {
        return type;
    }

    public String getColor() {
        return color;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    //Checks whether moving this piece from (fromRow, fromCol) to (toRow, toCol) is legal, given the current board.
    
    public abstract boolean isValidMove(Board board, int fromRow, int fromCol, int toRow, int toCol);

    public abstract ImageIcon getIcon();
}
