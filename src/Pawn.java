//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

public class Pawn extends Piece {
    private final boolean isWhite;
    private int times = 0;
    private boolean isPawn = true;
    private int currSquare;
    private final Board board;
    private boolean hasMoved = false;
    private final boolean[] canCatch = new boolean[64];
    private final boolean[] canMove = new boolean[64];
    private boolean firstMove = true;
    private final Functions functions = new Functions();
    private int number = 5;

    public Pawn(boolean isWhite, int currSquare, Board board) {
        this.board = board;
        this.isWhite = isWhite;
        this.currSquare = currSquare;
        if (!this.isWhite) {
            this.number += 6;
        }

    }

    public boolean canCastle() {
        return false;
    }

    public void moveOver() {
        if (this.times == 1) {
            this.hasMoved = false;
        }

        if (this.hasMoved) {
            ++this.times;
        }

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

    public boolean isKing() {
        return false;
    }

    public void canTake() {
        int i;
        if (this.isWhite) {
            for(i = 0; i < this.canCatch.length; ++i) {
                if (this.currSquare + 7 == i || this.currSquare + 9 == i) {
                    this.canCatch[i] = this.canMove(i);
                }

                this.canMove[i] = this.canMove(i);
            }
        } else {
            for(i = 0; i < this.canCatch.length; ++i) {
                if (this.currSquare - 7 == i || this.currSquare - 9 == i) {
                    this.canCatch[i] = this.canMove(i);
                }

                this.canMove[i] = this.canMove(i);
            }
        }

    }

    public boolean inCheck() {
        return false;
    }

    public void update() {
        this.canTake();
    }

    public boolean canMoveTo(int Square) {
        return this.canMove[Square];
    }

    public boolean canTake(int Square) {
        return this.canCatch[Square];
    }

    public boolean canMove(int newSquare) {
        return this.movePossible(newSquare) ? this.board.movePossible(newSquare, this) : false;
    }

    public boolean movePossible(int newSquare) {
        return this.isPawn ? this.movePossiblePawn(newSquare) : this.movePossibleQueen(newSquare);
    }

    private boolean movePossiblePawn(int newSquare) {
        if (this.isWhite) {
            if (this.firstMove && this.currSquare + 16 == newSquare && this.board.moveThroughPossible(this.currSquare + 8) && this.board.moveThroughPossible(this.currSquare + 16)) {
                return true;
            } else if (this.currSquare + 8 == newSquare) {
                return this.board.moveThroughPossible(newSquare);
            } else if (this.currSquare + 9 == newSquare && (this.currSquare + 1) % 8 != 0 && !this.board.moveThroughPossible(newSquare)) {
                return this.board.canCapture(this, newSquare);
            } else {
                return this.currSquare + 7 == newSquare && this.currSquare % 8 != 0 && !this.board.moveThroughPossible(newSquare) ? this.board.canCapture(this, newSquare) : false;
            }
        } else if (this.firstMove && this.currSquare - 16 == newSquare && this.board.moveThroughPossible(this.currSquare - 8) && this.board.moveThroughPossible(this.currSquare - 16)) {
            return true;
        } else if (this.currSquare - 8 == newSquare) {
            return this.board.moveThroughPossible(newSquare);
        } else if (this.currSquare - 9 == newSquare && (this.currSquare - 1) % 8 != 0 && !this.board.moveThroughPossible(newSquare)) {
            return this.board.canCapture(this, newSquare);
        } else {
            return this.currSquare - 7 == newSquare && this.currSquare % 8 != 0 && !this.board.moveThroughPossible(newSquare) ? this.board.canCapture(this, newSquare) : false;
        }
    }

    public boolean movePossibleQueen(int newSquare) {
        if (this.movePossibleDiagonally(newSquare)) {
            return true;
        } else if (this.movePossibleStraight(newSquare)) {
            String direction = this.functions.moveDirectionRook(this.currSquare, newSquare);
            return this.moveThroughPossibleStraight(direction, newSquare);
        } else {
            return false;
        }
    }

    public boolean moveThroughPossibleStraight(String direction, int newSquare) {
        boolean canMove = true;
        int i;
        if (direction.equals("forward")) {
            for(i = this.currSquare + 8; i != newSquare && canMove; i += 8) {
                canMove = this.board.moveThroughPossible(i);
            }
        }

        if (direction.equals("backwards")) {
            for(i = this.currSquare - 8; i != newSquare && canMove; i -= 8) {
                canMove = this.board.moveThroughPossible(i);
            }
        }

        if (direction.equals("right")) {
            for(i = this.currSquare + 1; i != newSquare && canMove; ++i) {
                canMove = this.board.moveThroughPossible(i);
            }
        }

        if (direction.equals("left")) {
            for(i = this.currSquare - 1; i != newSquare && canMove; --i) {
                canMove = this.board.moveThroughPossible(i);
            }
        }

        return canMove;
    }

    private boolean movePossibleDiagonally(int newSquare) {
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

    private boolean movePossibleStraight(int newSquare) {
        if (newSquare == this.currSquare) {
            return false;
        } else if ((newSquare - this.currSquare) % 8 == 0) {
            return true;
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

            return true;
        }
    }

    public boolean move(int newSquare) {
        if (this.movePossible(newSquare) && this.board.movePossible(newSquare, this)) {
            this.firstMove = false;
            this.board.move(this.currSquare, newSquare, this);
            if (this.currSquare + 16 == newSquare || this.currSquare - 16 == newSquare) {
                this.hasMoved = true;
            }

            this.currSquare = newSquare;
            if (this.currSquare > 55 && this.isPawn || this.currSquare < 8) {
                this.number = 1;
                if (!this.isWhite) {
                    this.number += 6;
                }

                this.isPawn = false;
            }

            return true;
        } else {
            return false;
        }
    }

    public void setCurrSquare(int newS) {
        this.currSquare = newS;
    }
}
