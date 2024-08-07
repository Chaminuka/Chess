//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Board {
    private boolean whiteTurn = true;
    private final Piece[] backup = new Piece[64];
    private final Square[] squares = new Square[64];
    private final List<Piece> pieces = new ArrayList();
    private int moveNum = 0;
    private int allMoves = 0;
    private int fiftyMoveNum = 0;

    public Board() {
        this.initiate1();
        this.initialize2();
        this.update();
    }

    private void initiate1() {
        for(int i = 0; i < 64; ++i) {
            this.squares[i] = new Square();
        }

    }

    public boolean dangerous(boolean white, int square) {
        int attackValue = this.squares[square].underAttack;
        if (attackValue == 2) {
            return true;
        } else if (white && attackValue == -1) {
            return true;
        } else {
            return !white && attackValue == 1;
        }
    }

    private void undoMove() {
        int i;
        for(i = 0; i < this.squares.length; ++i) {
            if (this.squares[i].piece != null) {
                this.squares[i].remove();
            }
        }

        for(i = 0; i < this.squares.length; ++i) {
            if (this.backup[i] != null) {
                this.squares[i].place(this.backup[i]);
            }
        }

    }

    public void updateTemp() {
        this.updateList();
        this.updateAttacked();
    }

    private char typeConverter(int type) {
        if (type == 0) {
            return 'K';
        } else if (type == 1) {
            return 'Q';
        } else if (type == 2) {
            return 'B';
        } else if (type == 3) {
            return 'N';
        } else if (type == 4) {
            return 'R';
        } else if (type == 5) {
            return 'P';
        } else if (type == 6) {
            return 'k';
        } else if (type == 7) {
            return 'q';
        } else if (type == 8) {
            return 'b';
        } else if (type == 9) {
            return 'n';
        } else if (type == 10) {
            return 'r';
        } else {
            return (char)(type == 11 ? 'p' : 'E');
        }
    }

    public String getFEN() {
        String fen = "";
        int counter = 0;
        int emptySquares = 0;

        for(int i = 63; i > -1; --i) {
            Square square = this.squares[i];
            if (counter > 7) {
                counter = 0;
                if (emptySquares > 0) {
                    fen = fen + emptySquares;
                    emptySquares = 0;
                }

                fen = fen + "/";
            }

            if (square.piece == null) {
                ++emptySquares;
            } else {
                if (emptySquares > 0) {
                    fen = fen + emptySquares;
                }

                fen = fen + this.typeConverter(square.piece.type());
                emptySquares = 0;
            }

            ++counter;
        }

        char turn = 'w';
        if (!this.whiteTurn) {
            turn = 'b';
        }

        fen = fen + " " + turn + " ";
        String castle = "";
        if (this.castleLegal(4, 6, true)) {
            castle = "K";
        }

        if (this.castleLegal(4, 2, true)) {
            castle = castle + "Q";
        }

        if (this.castleLegal(60, 62, false)) {
            castle = castle + "k";
        }

        if (this.castleLegal(60, 58, false)) {
            castle = castle + "q";
        }

        if (castle.isEmpty()) {
            castle = "- -";
        }

        String castleEnd = " - ";
        if (castle.equals("- -")) {
            castleEnd = " ";
        }

        fen = fen + castle + castleEnd + this.fiftyMoveNum + " " + this.moveNum;
        return Functions.fenReverser(fen);
    }

    public void update() {
        this.updateAttacked();
        this.updateList();

        for(int i = 0; i < this.squares.length; ++i) {
            this.backup[i] = this.squares[i].piece;
            if (this.squares[i].piece != null) {
                this.squares[i].piece.update();
            }
        }

    }

    public Square[] getSquares() {
        return this.squares;
    }

    public boolean castlePossible(int king, int direction, boolean color) {
        if ((king == 4 || king == 60) && (direction == king + 2 || direction == king - 2)) {
            Piece kingPiece = this.squares[king].piece;
            if (kingPiece == null) {
                return false;
            } else if (kingPiece.type() != 6 && kingPiece.type() != 0) {
                return false;
            } else {
                Piece rookPiece;
                int square;
                if (king < direction) {
                    rookPiece = this.squares[king + 3].piece;
                    if (rookPiece == null) {
                        return false;
                    } else if (!kingPiece.hasMoved() && !rookPiece.hasMoved()) {
                        for(square = king + 1; square < king + 3; ++square) {
                            if (!this.squares[square].moveThroughPossible() || this.dangerous(color, square)) {
                                return false;
                            }
                        }

                        return true;
                    } else {
                        return false;
                    }
                } else {
                    rookPiece = this.squares[king - 4].piece;
                    if (rookPiece == null) {
                        return false;
                    } else if (!kingPiece.hasMoved() && !rookPiece.hasMoved()) {
                        for(square = king - 1; square > king - 4; --square) {
                            if (!this.squares[square].moveThroughPossible()) {
                                return false;
                            }
                        }

                        return true;
                    } else {
                        return false;
                    }
                }
            }
        } else {
            return false;
        }
    }

    private boolean castleLegal(int king, int direction, boolean color) {
        if (king != 4 && king != 60 || direction != king + 2 && direction != king - 2) {
            return false;
        } else {
            Piece kingPiece = this.squares[king].piece;
            if (kingPiece == null) {
                return false;
            } else if (kingPiece.type() != 6 && kingPiece.type() != 0) {
                return false;
            } else {
                Piece rookPiece;
                int i;
                if (king < direction) {
                    rookPiece = this.squares[king + 3].piece;
                    if (rookPiece == null) {
                        return false;
                    } else if (!kingPiece.hasMoved() && !rookPiece.hasMoved()) {
                        for(i = king; i < king + 3; ++i) {
                            if (this.dangerous(color, i)) {
                                return false;
                            }
                        }

                        return true;
                    } else {
                        return false;
                    }
                } else {
                    rookPiece = this.squares[king - 4].piece;
                    if (rookPiece == null) {
                        return false;
                    } else if (!kingPiece.hasMoved() && !rookPiece.hasMoved()) {
                        for(i = king; i > king + 3; --i) {
                            if (this.dangerous(color, i)) {
                                return false;
                            }
                        }

                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
    }

    private int findKing(boolean white) {
        for(int i = 0; i < this.pieces.size(); ++i) {
            Piece piece = (Piece)this.pieces.get(i);
            if (piece.isWhite() == white && piece.isKing()) {
                King king = (King)piece;
                return king.kingSquare();
            }
        }

        return -1;
    }

    public boolean positionLegal() {
        int kingSquare = this.findKing(!this.whiteTurn);
        Piece king = this.squares[kingSquare].piece;
        if (king == null) {
            return true;
        } else {
            return !king.inCheck();
        }
    }

    public boolean moveLegal(Piece piece) {
        boolean white = piece.isWhite();
        int kingSquare = this.findKing(white);
        Piece king = this.squares[kingSquare].piece;
        if (king == null) {
            return false;
        } else {
            return !king.inCheck();
        }
    }

    public Square getSquare(int num) {
        return this.squares[num];
    }

    private void updateAttacked() {
        Square[] var1 = this.squares;
        int var2 = var1.length;

        int i;
        for(i = 0; i < var2; ++i) {
            Square square = var1[i];
            square.setAttackStatus(3);
        }

        Iterator var6 = this.pieces.iterator();

        while(var6.hasNext()) {
            Piece piece = (Piece)var6.next();

            for(i = 0; i < 64; ++i) {
                boolean[] check = new boolean[64];

                for(int j = 0; j < 64; ++j) {
                    check[i] = piece.canTake(i);
                }

                if (piece.canTake(i)) {
                    if (piece.isWhite()) {
                        this.squares[i].setAttackStatus(1);
                    } else {
                        this.squares[i].setAttackStatus(-1);
                    }
                }
            }
        }

    }

    private void updateList() {
        this.pieces.clear();

        for(int i = 0; i < 64; ++i) {
            if (this.squares[i].piece != null) {
                this.pieces.add(this.squares[i].piece);
            }
        }

    }

    private void initialize2() {
        this.squares[0].place(new Rook(true, 0, this));
        this.squares[7].place(new Rook(true, 7, this));
        this.squares[2].place(new Bishop(true, 2, this));
        this.squares[5].place(new Bishop(true, 5, this));
        this.squares[3].place(new Queen(true, 3, this));
        this.squares[4].place(new King(true, 4, this));
        this.squares[1].place(new Knight(true, 1, this));
        this.squares[6].place(new Knight(true, 6, this));
        this.squares[8].place(new Pawn(true, 8, this));
        this.squares[9].place(new Pawn(true, 9, this));
        this.squares[10].place(new Pawn(true, 10, this));
        this.squares[11].place(new Pawn(true, 11, this));
        this.squares[12].place(new Pawn(true, 12, this));
        this.squares[13].place(new Pawn(true, 13, this));
        this.squares[14].place(new Pawn(true, 14, this));
        this.squares[15].place(new Pawn(true, 15, this));
        this.squares[56].place(new Rook(false, 56, this));
        this.squares[63].place(new Rook(false, 63, this));
        this.squares[58].place(new Bishop(false, 58, this));
        this.squares[61].place(new Bishop(false, 61, this));
        this.squares[59].place(new Queen(false, 59, this));
        this.squares[60].place(new King(false, 60, this));
        this.squares[57].place(new Knight(false, 57, this));
        this.squares[62].place(new Knight(false, 62, this));
        this.squares[48].place(new Pawn(false, 48, this));
        this.squares[49].place(new Pawn(false, 49, this));
        this.squares[50].place(new Pawn(false, 50, this));
        this.squares[51].place(new Pawn(false, 51, this));
        this.squares[52].place(new Pawn(false, 52, this));
        this.squares[53].place(new Pawn(false, 53, this));
        this.squares[54].place(new Pawn(false, 54, this));
        this.squares[55].place(new Pawn(false, 55, this));
    }

    public boolean enPesantRightPossible(int pawnSquare) {
        Piece pawn = this.squares[pawnSquare].piece;
        if (pawn == null) {
            return false;
        } else if (pawn.type() != 5 && pawn.type() != 11) {
            return false;
        } else if (this.squares[pawnSquare + 1].piece == null) {
            return false;
        } else if (pawn.type() == 5 && pawnSquare < 40 && pawnSquare > 31 && this.squares[pawnSquare + 1].piece.hasMoved() && this.squares[pawnSquare + 1].piece.type() == 11) {
            return true;
        } else {
            return pawn.type() == 11 && pawnSquare < 32 && pawnSquare > 23 && this.squares[pawnSquare + 1].piece.hasMoved() && this.squares[pawnSquare + 1].piece.type() == 5;
        }
    }

    public boolean enPesantLeftPossible(int pawnSquare) {
        Piece pawn = this.squares[pawnSquare].piece;
        if (pawn == null) {
            return false;
        } else if (pawn.type() != 5 && pawn.type() != 11) {
            return false;
        } else if (this.squares[pawnSquare - 1].piece == null) {
            return false;
        } else if (pawn.type() == 5 && pawnSquare < 40 && pawnSquare > 31 && this.squares[pawnSquare - 1].piece.hasMoved() && this.squares[pawnSquare - 1].piece.type() == 11) {
            return true;
        } else {
            return pawn.type() == 11 && pawnSquare < 32 && pawnSquare > 23 && this.squares[pawnSquare - 1].piece.hasMoved() && this.squares[pawnSquare - 1].piece.type() == 5;
        }
    }

    public boolean[] possibleMoves(int pieceSquare) {
        Piece piece = this.squares[pieceSquare].piece;
        boolean[] moves = new boolean[64];
        if (piece == null) {
            return moves;
        } else {
            for(int i = 0; i < 64; ++i) {
                if (piece.isWhite() == this.whiteTurn && this.movePossible(i, piece) && piece.movePossible(i)) {
                    moves[i] = true;
                }
            }

            return moves;
        }
    }

    public boolean turn(Piece piece) {
        return piece.isWhite() == this.whiteTurn;
    }

    public boolean moveTo(int Start, int End) {
        if (this.squares[Start].piece != null) {
            if ((Start + 9 == End || End == Start - 7) && this.enPesantRightPossible(Start)) {
                this.squares[End].place(this.squares[Start].piece);
                this.squares[Start].remove();
                this.squares[Start + 1].remove();
                this.update();
                this.whiteTurn = !this.whiteTurn;
                ++this.allMoves;
                if (this.allMoves % 2 == 0) {
                    ++this.moveNum;
                }

                this.fiftyMoveNum = 0;
                return true;
            }

            if ((Start + 7 == End || End == Start - 9) && this.enPesantLeftPossible(Start)) {
                this.squares[End].place(this.squares[Start].piece);
                this.squares[Start].remove();
                this.squares[Start - 1].remove();
                this.update();
                this.whiteTurn = !this.whiteTurn;
                if (this.allMoves % 2 == 0) {
                    ++this.moveNum;
                }

                this.fiftyMoveNum = 0;
                return true;
            }
        }

        boolean placeRook = false;
        boolean directionRight = Start < End;
        Piece currPiece = this.squares[Start].piece;
        Piece caught = this.squares[End].piece;
        if (currPiece != null && currPiece.canCastle() && (End == Start + 2 || End == Start - 2)) {
            placeRook = true;
        }

        if (this.squares[Start].piece != null && this.squares[Start].piece.isWhite() == this.whiteTurn) {
            boolean retval = this.squares[Start].piece.move(End);
            if (placeRook && retval) {
                if (directionRight) {
                    this.squares[Start + 1].place(new Rook(!this.whiteTurn, Start + 1, this));
                } else {
                    this.squares[Start - 1].place(new Rook(!this.whiteTurn, Start - 1, this));
                }

                if (directionRight) {
                    this.squares[Start + 3].remove();
                } else {
                    this.squares[Start - 4].remove();
                }
            }

            if (retval) {
                if (this.allMoves % 2 == 0) {
                    ++this.moveNum;
                    System.out.println(this.getFEN());
                }

                if (currPiece.type() == 5 || currPiece.type() == 11 || caught != null) {
                    this.fiftyMoveNum = 0;
                }
            }

            return retval;
        } else {
            return false;
        }
    }

    public void move(int oldSquare, int square, Piece piece) {
        this.update();
        Iterator var4 = this.pieces.iterator();

        while(var4.hasNext()) {
            Piece subPiece = (Piece)var4.next();
            subPiece.moveOver();
        }

        this.whiteTurn = !this.whiteTurn;
        this.squares[square].place(piece);
        this.squares[oldSquare].remove();
        this.squares[square].piece.setCurrSquare(square);
        this.updateTemp();
        if (!this.positionLegal()) {
            this.squares[square].piece.setCurrSquare(oldSquare);
            this.undoMove();
            this.whiteTurn = !this.whiteTurn;
        }

        this.update();
    }

    public boolean movePossible(int newSquare, Piece currPiece) {
        if (newSquare > 63) {
            return false;
        } else {
            return newSquare < 0 ? false : this.squares[newSquare].movePossible(currPiece);
        }
    }

    public boolean canCapture(Piece piece, int Square) {
        return this.squares[Square].movePossible(piece);
    }

    public boolean moveThroughPossible(int Square) {
        return this.squares[Square].moveThroughPossible();
    }
}
