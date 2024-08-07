//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

public class Rook extends Piece {
    private final boolean[] canCatch = new boolean[64];
    private final boolean isWhite;
    private int currSquare;
    private final Board board;
    private final Functions functions = new Functions();
    private int number = 4;
    private boolean hasMoved = false;

    public Rook(boolean isWhite, int currSquare, Board board) {
        this.board = board;
        this.isWhite = isWhite;
        this.currSquare = currSquare;
        if (!this.isWhite) {
            this.number += 6;
        }

    }

    public int type() {
        return this.number;
    }

    public boolean isKing() {
        return false;
    }

    public void setCurrSquare(int newS) {
        this.currSquare = newS;
    }

    public boolean canMoveTo(int Square) {
        return this.canCatch[Square];
    }

    public boolean canTake(int Square) {
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
        if (this.movePossible(newSquare)) {
            if (this.functions.isNeighbour(this.currSquare, newSquare)) {
                return this.board.movePossible(newSquare, this);
            }

            String direction = this.functions.moveDirectionRook(this.currSquare, newSquare);
            if (this.board.movePossible(newSquare, this)) {
                return this.moveThroughPossible(direction, newSquare);
            }
        }

        return false;
    }

    public boolean hasMoved() {
        return this.hasMoved;
    }

    public boolean move(int newSquare) {
        if (this.movePossible(newSquare)) {
            if (this.functions.isNeighbour(this.currSquare, newSquare)) {
                if (this.board.movePossible(newSquare, this)) {
                    this.board.move(this.currSquare, newSquare, this);
                    this.currSquare = newSquare;
                    this.hasMoved = true;
                    return true;
                }
            } else {
                String direction = this.functions.moveDirectionRook(this.currSquare, newSquare);
                if (this.board.movePossible(newSquare, this) && this.moveThroughPossible(direction, newSquare)) {
                    this.board.move(this.currSquare, newSquare, this);
                    this.currSquare = newSquare;
                    this.hasMoved = true;
                    return true;
                }
            }
        }

        return false;
    }

    public boolean canCastle() {
        return false;
    }

    public void moveOver() {
    }

    public boolean moveThroughPossible(String direction, int newSquare) {
        boolean canMove = true;
        int i;
        if (direction.equals("forward")) {
            for(i = this.currSquare + 8; i != newSquare && canMove; i += 8) {
                if (i < 0 || i > 63) {
                    return false;
                }

                canMove = this.board.moveThroughPossible(i);
            }
        }

        if (direction.equals("backwards")) {
            for(i = this.currSquare - 8; i != newSquare && canMove; i -= 8) {
                if (i < 0 || i > 63) {
                    return false;
                }

                canMove = this.board.moveThroughPossible(i);
            }
        }

        if (direction.equals("right")) {
            for(i = this.currSquare + 1; i != newSquare && canMove; ++i) {
                if (i < 0 || i > 63) {
                    return false;
                }

                canMove = this.board.moveThroughPossible(i);
            }
        }

        if (direction.equals("left")) {
            for(i = this.currSquare - 1; i != newSquare && canMove; --i) {
                if (i < 0 || i > 63) {
                    return false;
                }

                canMove = this.board.moveThroughPossible(i);
            }
        }

        return canMove;
    }

    public boolean isWhite() {
        return this.isWhite;
    }

    public boolean movePossible(int newSquare) {
        if (newSquare == this.currSquare) {
            return false;
        } else {
            String direction = this.functions.moveDirectionRook(this.currSquare, newSquare);
            if ((newSquare - this.currSquare) % 8 == 0) {
                if (newSquare == 8) {
                }

                return this.moveThroughPossible(direction, newSquare);
            } else {
                int i;
                if (newSquare < this.currSquare) {
                    for(i = this.currSquare; i > newSquare; --i) {
                        if (i % 8 == 0) {
                            return false;
                        }
                    }
                } else {
                    for(i = this.currSquare + 1; i <= newSquare; ++i) {
                        if (i % 8 == 0) {
                            return false;
                        }
                    }
                }

                return this.moveThroughPossible(direction, newSquare);
            }
        }
    }
}
