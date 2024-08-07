//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

public abstract class Piece {
    public Piece() {
    }

    public abstract boolean isWhite();

    public abstract boolean movePossible(int var1);

    public abstract boolean move(int var1);

    public abstract boolean canMoveTo(int var1);

    public abstract boolean canTake(int var1);

    public abstract boolean inCheck();

    public abstract void update();

    public abstract boolean hasMoved();

    public abstract void moveOver();

    public abstract boolean isKing();

    public abstract int type();

    public abstract boolean canCastle();

    public abstract void canTake();

    public abstract void setCurrSquare(int var1);
}

