//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

public class King extends Piece {
    private final boolean[] canMove = new boolean[64];
    private final boolean[] canCatch = new boolean[64];
    private final boolean isWhite;
    private boolean hasMoved = false;
    private int currSquare;
    private final Board board;
    private final Functions functions = new Functions();
    private int number = 0;
    private boolean canCastle = false;

    public King(boolean isWhite, int currSquare, Board board) {
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

    public boolean hasMoved() {
        return this.hasMoved;
    }

    public int type() {
        return this.number;
    }

    public boolean isWhite() {
        return this.isWhite;
    }

    public boolean canMoveTo(int Square) {
        return this.canMove[Square];
    }

    public boolean canTake(int Square) {
        return this.canCatch[Square];
    }

    public void update() {
        this.canMove();
        this.canTake();
    }

    public boolean isKing() {
        return true;
    }

    private void canMove() {
        for(int i = 0; i < this.canCatch.length; ++i) {
            this.canMove[i] = this.canMoveRaw(i);
        }

    }

    public void canTake() {
        for(int i = 0; i < this.canCatch.length; ++i) {
            this.canCatch[i] = this.canMove(i);
        }

    }

    public boolean inCheck() {
        return this.board.dangerous(this.isWhite, this.currSquare);
    }

    public void endRoutine() {
        this.canCastle = false;
    }

    public boolean movePossible(int newSquare) {
        if (newSquare == this.currSquare + 2 || newSquare == this.currSquare - 2) {
            if (this.board.castlePossible(this.currSquare, newSquare, this.isWhite)) {
                this.canCastle = true;
                return true;
            }

            this.canCastle = false;
        }

        if (!this.functions.isNeighbour(this.currSquare, newSquare)) {
            return false;
        } else if (this.currSquare % 8 == 0) {
            if (newSquare == this.currSquare - 1) {
                return false;
            } else if (newSquare == this.currSquare + 7) {
                return false;
            } else {
                return newSquare != this.currSquare - 9;
            }
        } else if ((this.currSquare + 1) % 8 == 0) {
            if (newSquare == this.currSquare + 1) {
                return false;
            } else if (newSquare == this.currSquare + 9) {
                return false;
            } else {
                return newSquare != this.currSquare - 7;
            }
        } else {
            return !this.board.dangerous(this.isWhite, newSquare);
        }
    }

    private boolean movePossibleRaw(int newSquare) {
        if (!this.functions.isNeighbour(this.currSquare, newSquare)) {
            return false;
        } else if (this.currSquare % 8 == 0) {
            if (newSquare == this.currSquare - 1) {
                return false;
            } else if (newSquare == this.currSquare + 7) {
                return false;
            } else {
                return newSquare != this.currSquare - 9;
            }
        } else if ((this.currSquare + 1) % 8 == 0) {
            if (newSquare == this.currSquare + 1) {
                return false;
            } else if (newSquare == this.currSquare + 9) {
                return false;
            } else {
                return newSquare != this.currSquare - 7;
            }
        } else {
            return true;
        }
    }

    public void moveOver() {
        this.canCastle = false;
    }

    public int kingSquare() {
        return this.currSquare;
    }

    public boolean move(int newSquare) {
        if (this.movePossible(newSquare) && this.board.movePossible(newSquare, this)) {
            this.board.move(this.currSquare, newSquare, this);
            this.currSquare = newSquare;
            this.hasMoved = true;
            return true;
        } else {
            return false;
        }
    }

    public boolean canCastle() {
        return this.canCastle;
    }

    public boolean canMoveRaw(int newSquare) {
        return this.movePossibleRaw(newSquare) ? this.board.movePossible(newSquare, this) : false;
    }

    public boolean canMove(int newSquare) {
        return this.movePossible(newSquare) ? this.board.movePossible(newSquare, this) : false;
    }
}
