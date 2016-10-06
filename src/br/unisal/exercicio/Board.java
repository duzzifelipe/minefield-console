// Only to support, keep this in mind (for this code):
// Y, M, I are related to ROWS
// X, N, J are related to COLS

package br.unisal.exercicio;

import java.util.Random;

public class Board {
    private final int MAX_BORDERER = 1;
    private int size_m = 0;
    private int size_n = 0;
    private int bomb = 0;
    private Point[][] points;

    public Board(int size_m, int size_n, int bomb) throws Exception {
        this.size_m = size_m;
        this.size_n = size_n;

        if (this.size_m >= 100 || this.size_n >= 100) {
            throw new Exception("Impossible to create this dimension.");

        } else if (bomb > size_m * size_n) {
            throw new Exception("Impossible to add more bombs than fields number.");
        }

        this.bomb = bomb;
        points = new Point[size_m][size_n];
    }

    public int getSize_m() {
        return size_m;
    }

    public int getSize_n() {
        return size_n;
    }

    /**
     * Deliver randomly (this.bomb) bombs
     *
     * @return
     */
    public int append_bombs() {
        for (int i = 0; i < this.bomb; i++) {
            boolean next = false;
            int pos_x = 0;
            int pos_y = 0;

            do {
                pos_x = random_num(this.size_n);
                pos_y = random_num(this.size_m);
                next = this.is_empty_point(pos_x, pos_y);

            } while (!next);

            this.points[pos_y][pos_x] = new Bomb();
        }

        this.fill_with_numbers();
        this.calculate_nearby_bombs();

        return 0;
    }

    /**
     * Open a board (x,y) position
     *
     * @param pos_x
     * @param pos_y
     * @return
     */
    public int open(int pos_x, int pos_y) {
        if (points[pos_y][pos_x] instanceof Bomb) {
            ((Bomb) points[pos_y][pos_x]).setFired(true);
            return -1;

        } else if (points[pos_y][pos_x] instanceof Number) {
            Number point = ((Number) points[pos_y][pos_x]);
            point.setOpen(true);

            if(this.allOpen()) {
                return 1;
            }

            return 0;
        }

        return -1;
    }

    /**
     * Display game's final message
     *
     * @return
     */
    public void finalize_game(int running) {
        this.display_board(true);

        if (running == -1) {
            System.out.println("\n" + Color.ANSI_RED + "YOU LOST!" + Color.ANSI_RESET);

        } else {
            System.out.println("\n" + Color.ANSI_GREEN + "YOU WON!" + Color.ANSI_RESET);
        }
    }

    /**
     * Prints board
     */
    public void display_board() {
        System.out.println("");

        this.print_board_header();

        for (int i = 0; i < this.size_m; i++) {

            this.print_line_helper(i);

            for (int j = 0; j < this.size_n; j++) {
                if (this.points[i][j] instanceof Number && ((Number) this.points[i][j]).isOpen()) {
                    Number point = (Number) this.points[i][j];

                    if (point.getSideBombs() == 0) {
                        System.out.print(Color.ANSI_CYAN + "_  " + Color.ANSI_RESET);

                    } else {
                        System.out.print(Color.ANSI_BLUE + "" + point.getSideBombs() + "  " + Color.ANSI_RESET);
                    }

                } else {
                    System.out.print(Color.ANSI_GREEN + "#  " + Color.ANSI_RESET);
                }
            }

            System.out.println("");
        }
    }

    /**
     * Prints board for final game final (all open)
     *
     * @param finished
     */
    public void display_board(boolean finished) {
        if (!finished) {
            display_board();
            return;
        }

        System.out.println("");
        for (int i = 0; i < this.size_m; i++) {

            this.print_line_helper(i);

            for (int j = 0; j < this.size_n; j++) {
                if (this.points[i][j] instanceof Bomb) {
                    System.out.print(Color.ANSI_RED + "*  " + Color.ANSI_RESET);

                } else {
                    if (((Number) this.points[i][j]).isOpen()) {
                        System.out.print(Color.ANSI_CYAN + "_  " + Color.ANSI_RESET);

                    } else {
                        System.out.print(Color.ANSI_GREEN + "#  " + Color.ANSI_RESET);
                    }
                }
            }
            System.out.println("");
        }
    }

    /**
     * Checks if a (x,y) position is between board's limits
     *
     * @param pos_x
     * @param pos_y
     * @return
     */
    public boolean is_valid_position(int pos_x, int pos_y) {
        return pos_x <= this.getSize_n() && pos_x >= 0 && pos_y <= this.getSize_m() && pos_y >= 0;
    }


    /***
     *
     *
     * PRIVATE METHODS
     *
     *
     */

    /**
     * Runs nearby_bombs() for each Number position and save into the class
     */
    private void calculate_nearby_bombs() {
        for (int i = 0; i < size_m; i++) {
            for (int j = 0; j < size_n; j++) {
                if (this.points[i][j] instanceof Number) {
                    ((Number) this.points[i][j]).setSideBombs(this.nearby_bombs(j, i));
                }
            }
        }
    }

    /**
     * Count nearby bombs at a maximum of 5x5 fields from the position
     *
     * @param pos_x
     * @param pos_y
     * @return
     */
    private int nearby_bombs(int pos_x, int pos_y) {
        int bomb_count = 0;

        for (int i = this.getIMin(pos_y); i <= this.getIMax(pos_y); i++) {
            for (int j = this.getJMin(pos_x); j <= this.getJMax(pos_x); j++) {
                if (this.points[i][j] instanceof Bomb && !this.points[i][j].equals(this.points[pos_y][pos_x]))
                    bomb_count++;
            }
        }

        return bomb_count;
    }

    /**
     * Checks if a point has anything (no Bomb neither Number)
     *
     * @param pos_x
     * @param pos_y
     * @return
     */
    private boolean is_empty_point(int pos_x, int pos_y) {
        if (this.points[pos_y][pos_x] == null) {
            return true;
        }

        return false;
    }

    /**
     * Generates a random int between 0 and max (of x or y)
     *
     * @param max
     * @return
     */
    private int random_num(int max) {
        Random rand = new Random();
        return rand.nextInt(max);
    }

    /**
     * Runs all matrix positions to find non-filled with bombs, than put a Number inside it
     */

    private boolean allOpen() {
        int number_count = 0;

        for (int i = 0; i < this.size_m; i++) {
            for (int j = 0; j < this.size_n; j++) {
                if (this.points[i][j] instanceof Number && ((Number) this.points[i][j]).isOpen()) {
                    number_count ++;
                }
            }
        }

        return number_count == ((this.size_m * this.size_n) - this.bomb);
    }

    private void fill_with_numbers() {
        for (int i = 0; i < this.size_m; i++) {
            for (int j = 0; j < this.size_n; j++) {
                if (!(this.points[i][j] instanceof Bomb)) {
                    this.points[i][j] = new Number();
                }
            }
        }
    }

    private void print_board_header() {
        System.out.print(Color.ANSI_BLUE + "#  ");

        for (int j = 0; j < this.size_n; j++) {
            String space = "  ";

            if (j >= 9) {
                space = " ";
            }

            System.out.print((j + 1) + space);
        }

        System.out.println(Color.ANSI_RESET);
    }

    private void print_line_helper(int i) {
        String space = "  ";

        if (i >= 9) {
            space = " ";
        }

        System.out.print(Color.ANSI_BLUE + (i + 1) + space + Color.ANSI_RESET);
    }

    private int getIMin(int pos_y) {
        return (pos_y - this.MAX_BORDERER >= 0) ? (pos_y - this.MAX_BORDERER) : 0;
    }

    private int getIMax(int pos_y) {
        return (pos_y + this.MAX_BORDERER <= (this.size_m - 1)) ? (pos_y + this.MAX_BORDERER) : (this.size_m - 1);
    }

    private int getJMin(int pos_x) {
        return (pos_x - this.MAX_BORDERER >= 0) ? (pos_x - this.MAX_BORDERER) : 0;
    }

    private int getJMax(int pos_x) {
        return (pos_x + this.MAX_BORDERER <= (this.size_n - 1)) ? (pos_x + this.MAX_BORDERER) : (this.size_n - 1);
    }
}
