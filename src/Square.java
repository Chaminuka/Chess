//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

public class Square {
    public Piece piece = null;
    private boolean isEmpty = true;
    public int underAttack = 0;

    public Square() {
    }

    public void place(Piece newPiece) {
        this.piece = newPiece;
        this.isEmpty = false;
    }

    public void remove() {
        this.piece = null;
        this.isEmpty = true;
    }

    public void setAttackStatus(int val) {
        if (val == 1) {
            if (this.underAttack == -1) {
                this.underAttack = 2;
            } else {
                this.underAttack = 1;
            }
        }

        if (val == -1) {
            if (this.underAttack == 1) {
                this.underAttack = 2;
            } else {
                this.underAttack = -1;
            }
        }

        if (val == 3) {
            this.underAttack = 0;
        }

    }

    public boolean movePossible(Piece currPiece) {
        if (this.isEmpty) {
            return true;
        } else {
            return this.piece.isWhite() != currPiece.isWhite();
        }
    }

    public boolean moveThroughPossible() {
        return this.isEmpty;
    }
}

