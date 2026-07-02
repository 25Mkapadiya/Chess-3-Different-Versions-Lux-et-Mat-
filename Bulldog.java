import javax.swing.ImageIcon;
public class Bulldog extends Piece{
    //asked to be placed with each turn (do in the class, this is just the piece class)
    //does absolutely nothing 
    //cannot be captured 
    public Bulldog () {
        super ("bulldog", "bulldog"); 
    }

    public ImageIcon getIcon() {
        return BULLDOG_ICON;
    }

public boolean isValidMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        // never moves like a normal piece 
        return false;
    }

    private static final ImageIcon BULLDOG_ICON = loadIcon("resources/chess/bulldog.png");

    private static ImageIcon loadIcon(String path) {
        return new ImageIcon(path);
    }
}
