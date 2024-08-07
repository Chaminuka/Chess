//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUIBoard extends JFrame {
    private int currPieceIndex;
    private final boolean[] currMoves = new boolean[64];
    private final JPanel chessPanel;
    private BufferedImage all;
    private final Image[] imgs = new Image[12];
    private final Board board;
    private final JPanel[] squares = new JPanel[64];
    private boolean white;

    public GUIBoard(Board board, boolean white) {
        this.setTitle("Chess Board");
        this.setSize(700, 720);
        this.setLocationRelativeTo((Component)null);
        this.setDefaultCloseOperation(3);
        this.setResizable(false);
        this.board = board;
        this.white = white;
        this.chessPanel = new JPanel();
        this.chessPanel.setLayout(new GridLayout(8, 8));

        try {
            this.all = ImageIO.read(new File("C:\\Users\\chami\\IdeaProjects\\Chess\\out\\production\\Chess\\chess.png"));
            this.createPieces();
        } catch (IOException var4) {
            IOException e = var4;
            e.printStackTrace();
        }

        this.add(this.chessPanel, "Center");
        if (white) {
            this.drawChessBoard();
        } else {
            this.drawChessBoardBlack();
        }

    }

    private int squareCalculator(int row, int col) {
        return row * 8 + col;
    }

    private void drawChessBoard() {
        this.chessPanel.removeAll();

        for(int row = 0; row < 8; ++row) {
            for(int col = 0; col < 8; ++col) {
                JPanel square = new JPanel();
                if ((row + col) % 2 == 0) {
                    square.setBackground(new Color(235, 235, 208));
                } else {
                    square.setBackground(new Color(119, 148, 85));
                }

                int finalRow = 7 - row;
                int index = this.squareCalculator(finalRow, col);
                square.addMouseListener(new SquareClickListener(index));
                this.squares[index] = square;
                this.chessPanel.add(square);
            }
        }

        this.drawPiecesOnBoard(this.board.getSquares());
        this.chessPanel.revalidate();
        this.chessPanel.repaint();
    }

    private void drawChessBoardBlack() {
        this.chessPanel.removeAll();

        for(int row = 7; row > -1; --row) {
            for(int col = 7; col > -1; --col) {
                JPanel square = new JPanel();
                if ((row + col) % 2 == 0) {
                    square.setBackground(new Color(235, 235, 208));
                } else {
                    square.setBackground(new Color(119, 148, 85));
                }

                int finalRow = 7 - row;
                int index = this.squareCalculator(finalRow, col);
                square.addMouseListener(new SquareClickListener(index));
                this.squares[index] = square;
                this.chessPanel.add(square);
            }
        }

        this.drawPiecesOnBoard(this.board.getSquares());
        this.chessPanel.revalidate();
        this.chessPanel.repaint();
    }

    private void drawPiecesOnBoard(Square[] squares) {
        for(int i = 0; i < 64; ++i) {
            int row = i / 8;
            int col = i % 8;
            Piece currPiece = squares[i].piece;
            if (currPiece != null) {
                this.placePiece(currPiece, row, col);
            }
        }

    }

    private void placePiece(Piece piece, int row, int col) {
        JLabel pieceLabel = new JLabel(new ImageIcon(this.imgs[piece.type()]));
        int index;
        if (this.white) {
            index = (7 - row) * 8 + col;
        } else {
            index = row * 8 + (7 - col);
        }

        JPanel square = (JPanel)this.chessPanel.getComponent(index);
        square.removeAll();
        square.add(pieceLabel);
    }

    private void createPieces() {
        if (this.all != null) {
            int ind = 0;

            for(int y = 0; y < 400; y += 200) {
                for(int x = 0; x < 1200; x += 200) {
                    this.imgs[ind] = this.all.getSubimage(x, y, 200, 200).getScaledInstance(64, 64, 4);
                    ++ind;
                }
            }

        }
    }

    private class SquareClickListener extends MouseAdapter {
        private final int index;

        public SquareClickListener(int index) {
            this.index = index;
        }

        public void mousePressed(MouseEvent e) {
            boolean[] moves = GUIBoard.this.board.possibleMoves(this.index);
            Piece currPiece = GUIBoard.this.board.getSquare(this.index).piece;
            if (moves != null && currPiece != null && GUIBoard.this.board.turn(currPiece)) {
                GUIBoard.this.currPieceIndex = this.index;
                System.arraycopy(moves, 0, GUIBoard.this.currMoves, 0, 64);
            }

            if (GUIBoard.this.board.enPesantLeftPossible(GUIBoard.this.currPieceIndex) || GUIBoard.this.board.enPesantRightPossible(GUIBoard.this.currPieceIndex)) {
                GUIBoard.this.board.moveTo(GUIBoard.this.currPieceIndex, this.index);
                if (GUIBoard.this.white) {
                    GUIBoard.this.drawChessBoard();
                } else {
                    GUIBoard.this.drawChessBoardBlack();
                }
            }

            if (GUIBoard.this.currMoves[this.index] && GUIBoard.this.board.moveTo(GUIBoard.this.currPieceIndex, this.index)) {
                GUIBoard.this.board.moveTo(GUIBoard.this.currPieceIndex, this.index);
                if (GUIBoard.this.white) {
                    GUIBoard.this.drawChessBoard();
                } else {
                    GUIBoard.this.drawChessBoardBlack();
                }
            }

        }
    }
}
