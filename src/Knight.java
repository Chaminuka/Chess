//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

public class Knight extends Piece {
    private final boolean isWhite;
    private int currSquare;
    private final Board board;
    private final boolean[] canCatch = new boolean[64];
    private int number = 3;

    public Knight(boolean isWhite, int currSquare, Board board) {
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

    public boolean canTake(int Square) {
        return this.canCatch[Square];
    }

    public boolean isKing() {
        return false;
    }

    public boolean canMoveTo(int Square) {
        return this.canCatch[Square];
    }

    public void canTake() {
        for(int i = 0; i < this.canCatch.length; ++i) {
            this.canCatch[i] = this.canMove(i);
        }

    }

    public boolean inCheck() {
        return false;
    }

    public void update() {
        this.canTake();
    }

    public boolean canMove(int newSquare) {
        return this.movePossible(newSquare) ? this.board.movePossible(newSquare, this) : false;
    }

    public boolean movePossible(int newSquare) {
        if (newSquare == this.currSquare + 17) {
            return (this.currSquare + 1) % 8 != 0;
        } else if (newSquare == this.currSquare + 15) {
            return this.currSquare % 8 != 0;
        } else if (newSquare == this.currSquare + 10) {
            return (this.currSquare + 1) % 8 != 0 && (this.currSquare + 2) % 8 != 0;
        } else if (newSquare == this.currSquare - 17) {
            return this.currSquare % 8 != 0;
        } else if (newSquare == this.currSquare - 15) {
            return (this.currSquare + 1) % 8 != 0;
        } else if (newSquare == this.currSquare - 10) {
            return this.currSquare % 8 != 0 && (this.currSquare - 1) % 8 != 0;
        } else if (newSquare == this.currSquare + 6) {
            return this.currSquare % 8 != 0 && (this.currSquare - 1) % 8 != 0;
        } else if (newSquare != this.currSquare - 6) {
            return false;
        } else {
            return (this.currSquare + 1) % 8 != 0 && (this.currSquare + 2) % 8 != 0;
        }
    }

    public boolean hasMoved() {
        return false;
    }

    public boolean canCastle() {
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
}
