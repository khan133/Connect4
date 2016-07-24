import java.util.*;

public class Connect4 {
    public static final char NONE = ' ';
    public static final char RED = 'O';
    public static final char YELLOW = 'X';
    char[][] board;

    /**
     * Initializes the instance variables.
     */
    public Connect4() {
        this.board = new char[6][7];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                board[i][j] = NONE;
            }
        }
    }

    /**
     * Returns a copy of the current board
     *
     * @return a char matrix
     */
    public char[][] getBoard() {
        char[][] copy = new char[6][7];
        if (board != null) {
            for (int y = 0; y < board.length; y++) {
                for (int x = 0; x < board[y].length; x++) {
                    copy[y][x] = board[y][x];
                }
            }
            return copy;
        }
        return new char[0][0];
    }

    /**
     * Put a piece of the given color in the given column
     * The function returns the row where the piece have been
     * put. If the column is full it return -1.
     *
     * @param column a column of the board
     * @param color  RED or YELLOW
     */
    public int putPiece(int column, char color) {
        if (board[0][column] != NONE)
            return -1;
        int i;
        for (i = 5; i >= 0; i--) {
            if (board[i][column] == NONE) {
                board[i][column] = color;
                break;
            }
        }
        return i;
    }

    /**
     * Print the screen in the standard output
     */
    public void printScreen() {
        // Make the header of the board
        System.out.printf("\n ");
        for (int i = 0; i < board[0].length; ++i)
            System.out.printf("   %d", i);
        System.out.println();

        System.out.printf("  ");
        for (int i = 0; i < board[0].length; ++i)
            System.out.printf("----");
        System.out.println("-");

        // Print the board contents
        for (int i = 0; i < board.length; ++i) {
            System.out.printf("%c ", 'A' + i);
            for (int k = 0; k < board[0].length; ++k)
                System.out.printf("| %c ", board[i][k]);
            System.out.println("|");

            // print the line between each row
            System.out.printf("  ");
            for (int k = 0; k < board[0].length; ++k)
                System.out.printf("----");
            System.out.println("-");
        }
    }

    /**
     * Check if an alignment has been made using the given tile
     *
     * @param row
     * @param column
     * @return the color if there is an alignment, NONE otherwise.
     */
    public char checkAlignment(int row, int column) {
        int count = 0;
        char color = board[row][column];
        //up to down
        for (int i = 5; i >= 0; i--) {
            for (int k = 3; k >= 0; k--) {
                if (i + k <= 5) {
                    if (board[i + k][column] == color) {
                        count++;
                    } else {
                        count = 0;
                        break;
                    }
                    if (count == 4) {
                        return color;
                    }
                } else break;
            }
        }
        count = 0;
        //horizontal
        for (int j = 0; j <= 6; j++) {
            for (int f = 3; f >= 0; f--) {
                if (f + j <= 6) {
                    if (board[row][j + f] == color) {
                        count++;
                    } else {
                        count = 0;
                        break;
                    }
                    if (count == 4) {
                        return color;
                    }
                } else break;
            }
        }
        count = 0;
        //diagonal top to bottom
        for (int q = 5; q >= 0; q--) {
            for (int h = 0; h <= 6; h++) {
                for (int r = 3; r >= 0; r--) {
                    if (r + h <= 6 && r + q <= 5) {
                        if (board[q + r][h + r] == color) {
                            count++;
                        } else {
                            count = 0;
                            break;
                        }
                        if (count == 4) {
                            return color;
                        }
                    } else break;
                }
            }
        }
        count = 0;
        //diagonal bottom to top
        for (int q = 5; q >= 0; q--) {
            for (int h = 0; h <= 6; h++) {
                for (int r = 0; r <= 3; r++) {
                    if (r + h <= 6 && q - r <= 5 && q - r >= 0) {
                        if (board[q - r][h + r] == color) {
                            count++;
                        } else {
                            count = 0;
                            break;
                        }
                        if (count == 4) {
                            return color;
                        }
                    } else break;
                }
            }
        }
        return NONE;
    }

    /**
     * Launch the game for one game.
     */
    public void play() {
        char currentPlayer = RED;

        // Begin playing the game
        Scanner in = new Scanner(System.in);
        int col = -1;
        int row = -1;

        do {
            currentPlayer = currentPlayer == RED ? YELLOW : RED;
            this.printScreen();
            System.out.printf("Current player: '%c'\n", currentPlayer);

            // read and validate the input
            col = -1;
            row = -1;

            do {
                System.out.printf("Choose a column: ");
                String line = in.nextLine();


                if (line == null || line.length() != 1) {
                    // Invalid input, reask for one.
                    continue;
                }

                col = line.charAt(0) - '0';
                row = this.putPiece(col, currentPlayer);

            } while (row < 0);

        } while (this.checkAlignment(row, col) == NONE);

        this.printScreen();
        System.out.printf("\n!!! Winner is Player '%c' !!!\n", currentPlayer);
        in.close();

    }
}