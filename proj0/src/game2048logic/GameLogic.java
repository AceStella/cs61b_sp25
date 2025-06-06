package game2048logic;

import game2048rendering.Side;
import static game2048logic.MatrixUtils.rotateLeft;
import static game2048logic.MatrixUtils.rotateRight;

/**
 * @author  Josh Hug
 */
public class GameLogic {
    /** Moves the given tile up as far as possible, subject to the minR constraint.
     *
     * @param board the current state of the board
     * @param r     the row number of the tile to move up
     * @param c -   the column number of the tile to move up
     * @param minR  the minimum row number that the tile can land in, e.g.
     *              if minR is 2, the moving tile should move no higher than row 2.
     * @return      if there is a merge, returns the 1 + the row number where the merge occurred.
     *              if no merge occurs, then return 0.
     */
    public static int moveTileUpAsFarAsPossible(int[][] board, int r, int c, int minR) {
        int zeroPosi = r + 1;
        for (int i = r - 1; i >= minR; i--) {
            if (board[i][c] == 0) {
                zeroPosi = i;
            } else if (board[i][c] == board[r][c]) {
                board[i][c] *= 2;
                board[r][c] = 0;
                return 1 + i;
            } else {
                if (i != r - 1) {
                    board[i + 1][c] = board[r][c];
                    board[r][c] = 0;
                }
                return 0;
            }
        }
        if (zeroPosi == minR) {
            board[minR][c] = board[r][c];
            board[r][c] = 0;
        }
        return 0;
    }

    /**
     * Modifies the board to simulate the process of tilting column c
     * upwards.
     *
     * @param board     the current state of the board
     * @param c         the column to tilt up.
     */
    public static void tiltColumn(int[][] board, int c) {
        int minR = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i][c] != 0) {
                minR = moveTileUpAsFarAsPossible(board, i, c, minR);
            }
        }
    }

    /**
     * Modifies the board to simulate tilting all columns upwards.
     *
     * @param board     the current state of the board.
     */
    public static void tiltUp(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            tiltColumn(board, i);
        }
    }

    /**
     * Modifies the board to simulate tilting the entire board to
     * the given side.
     *
     * @param board the current state of the board
     * @param side  the direction to tilt
     */
    public static void tilt(int[][] board, Side side) {
        if (side == Side.EAST) {
            rotateLeft(board);
            tiltUp(board);
            rotateRight(board);
        } else if (side == Side.WEST) {
            rotateRight(board);
            tiltUp(board);
            rotateLeft(board);
        } else if (side == Side.SOUTH) {
            rotateRight(board);
            rotateRight(board);
            tiltUp(board);
            rotateRight(board);
            rotateRight(board);
        } else {
            tiltUp(board);
        }
    }
}
