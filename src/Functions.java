//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

public class Functions {
    public Functions() {
    }

    public static String fenReverser(String fen) {
        String line = "";
        String newFen = "";

        for(int i = 0; i < fen.length(); ++i) {
            char curr = fen.charAt(i);
            if (curr == ' ') {
                newFen = newFen + reverseString(line) + fen.substring(i);
                return newFen;
            }

            if (curr != '/') {
                line = line + curr;
            }

            if (curr == '/') {
                newFen = newFen + reverseString(line) + curr;
                line = "";
            }
        }

        return null;
    }

    public static String reverseString(String input) {
        if (input.isEmpty()) {
            return input;
        } else {
            char last = input.charAt(input.length() - 1);
            input = input.substring(0, input.length() - 1);
            return "" + last + reverseString(input);
        }
    }

    private static boolean isRightLeft(int S1, int S2) {
        return Math.abs(S1 - S2) == 1;
    }

    public boolean isNeighbour(int S1, int S2) {
        int front = S1 + 8;
        int back = S1 - 8;
        if (front == S2) {
            return true;
        } else if (back == S2) {
            return true;
        } else {
            if (front % 8 == 0) {
                if (S2 == front + 1 || S2 == S1 + 1) {
                    return true;
                }
            } else if (back % 8 == 0) {
                if (S2 == back + 1) {
                    return true;
                }
            } else if ((front + 1) % 8 == 0) {
                if (S2 == front - 1 || S2 == S1 - 1) {
                    return true;
                }
            } else {
                if ((back + 1) % 8 != 0) {
                    return isRightLeft(front, S2) || isRightLeft(back, S2) || isRightLeft(S1, S2);
                }

                if (S2 == back - 1) {
                    return true;
                }
            }

            return false;
        }
    }

    public String moveDirectionRook(int Start, int End) {
        if ((Start - End) % 8 == 0) {
            return Start < End ? "forward" : "backwards";
        } else {
            return End < Start ? "left" : "right";
        }
    }
}
