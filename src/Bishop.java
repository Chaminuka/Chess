//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

public class Bishop extends Piece {
    private final boolean isWhite;
    private final boolean[] canCatch = new boolean[64];
    private int currSquare;
    private final Board board;
    private int number = 2;

    public Bishop(boolean isWhite, int currSquare, Board board) {
        this.board = board;
        this.isWhite = isWhite;
        this.currSquare = currSquare;
        if (!this.isWhite) {
            this.number += 6;
        }

    }

    public void setCurrSquare(int newS) {
        this.currSquare = newS;
    }

    public int type() {
        return this.number;
    }

    public boolean isWhite() {
        return this.isWhite;
    }

    public boolean canMoveTo(int Square) {
        return this.canCatch[Square];
    }

    public boolean canTake(int Square) {
        return this.canCatch[Square];
    }

    public boolean isKing() {
        return false;
    }

    public void update() {
        this.canTake();
    }

    public void canTake() {
        for(int i = 0; i < this.canCatch.length; ++i) {
            this.canCatch[i] = this.canMove(i);
        }

    }

    public boolean inCheck() {
        return false;
    }

    public boolean movePossible(int newSquare) {
        if (newSquare == this.currSquare) {
            return false;
        } else if ((newSquare - this.currSquare) % 9 != 0 && (newSquare - this.currSquare) % 7 != 0) {
            return false;
        } else {
            int i;
            if ((newSquare - this.currSquare) % 9 == 0) {
                if (newSquare <= this.currSquare) {
                    for(i = this.currSquare - 9; i > newSquare; i -= 9) {
                        if (i % 8 == 0 || !this.board.moveThroughPossible(i)) {
                            return false;
                        }
                    }
                } else {
                    for(i = this.currSquare + 9; i < newSquare; i += 9) {
                        if ((i + 1) % 8 == 0 || !this.board.moveThroughPossible(i)) {
                            return false;
                        }
                    }
                }
            } else if (newSquare <= this.currSquare) {
                for(i = this.currSquare - 7; i > newSquare; i -= 7) {
                    if ((i + 1) % 8 == 0 || !this.board.moveThroughPossible(i)) {
                        return false;
                    }
                }
            } else {
                for(i = this.currSquare + 7; i < newSquare; i += 7) {
                    if (i % 8 == 0 || !this.board.moveThroughPossible(i)) {
                        return false;
                    }
                }
            }

            return true;
        }
    }

    public boolean canCastle() {
        return false;
    }

    public boolean hasMoved() {
        return false;
    }

    public void moveOver() {
    }

    public boolean move(int newSquare) {
        if (this.movePossible(newSquare) && this.board.movePossible(newSquare, this)) {
            this.board.move(this.currSquare, newSquare, this);
            this.currSquare = newSquare;
            return true;
        } else {
            return false;
        }
    }

    public boolean canMove(int newSquare) {
        return this.movePossible(newSquare) ? this.board.movePossible(newSquare, this) : false;
    }
}
