
import javax.swing.SwingUtilities;

public class Main {
    public Main() {
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Board board = new Board();
            GUIBoard guiBoard = new GUIBoard(board, true);
            guiBoard.setVisible(true);

        });
    }
}