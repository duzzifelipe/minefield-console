package br.unisal.exercicio;

import java.util.Random;

public class Board {
    private int size_m = 0;
    private int size_n = 0;
    private int bomb = 0;
    private Point[][] points;

    public Board(int size_m, int size_n, int bomb) throws Exception {
        this.size_m = size_m;
        this.size_n = size_n;
        
        if (bomb > size_m * size_n) {
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
     * @return
     */
    public int append_bombs() {
        for (int i = 0; i < this.bomb; i++) {
            boolean next = false;
            int pos_x = 0;
            int pos_y = 0;
            
            do {
                pos_x = random_num(this.size_m);
                pos_y = random_num(this.size_n);
                next = this.is_empty_point(pos_x, pos_y);
                
            } while (!next);
            
            this.points[pos_x][pos_y] = new Bomb();
        }
        
        this.fill_with_numbers();
        
        return 0;
    }

    /**
     * Open a board (x,y) position
     * @param pos_x
     * @param pos_y
     * @return
     */
    public boolean open(int pos_x, int pos_y) {
        if (points[pos_x][pos_y] instanceof Bomb) {
            ((Bomb) points[pos_x][pos_y]).setFired(true);
            return false;

        } else if(points[pos_x][pos_y] instanceof Number) {
            ((Number) points[pos_x][pos_y]).setOpen(true);
            return true;
        }

        return false;
    }

    /**
     * Display game's final message
     * @return
     */
    public String finalize_game() {
        return "Finished";
    }

    /**
     * Prints board
     */
    public void display_board() {
        for(int i = 0; i < this.size_m; i++) {
            for(int j = 0; j < this.size_n; j++) {
                if(this.points[i][j] instanceof Number) {
                    System.out.print(Color.ANSI_BLUE + "#  "  + Color.ANSI_RESET);
                
                } else if (this.points[i][j] instanceof Bomb) {
                    System.out.print(Color.ANSI_YELLOW + "#  " + Color.ANSI_RESET);
                }
            }
            System.out.println("");
        }
    }

    /**
     * Checks if a (x,y) position is between board's limits
     * @param pos_x
     * @param pos_y
     * @return
     */
    public boolean is_valid_position(int pos_x, int pos_y) {
        return pos_x <= this.getSize_m() && pos_x >= 0 && pos_y <= this.getSize_n() && pos_y >= 0;
    }

    /**
     * Checks if a point has anything (no Bomb neither Number)
     * @param pos_x
     * @param pos_y
     * @return
     */
    private boolean is_empty_point(int pos_x, int pos_y) {
        if(this.points[pos_x][pos_y]==null) {
            return true;
        }
        
        return false;
    }

    /**
     * Generates a random int between 0 and max (of x or y)
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
    private void fill_with_numbers() {
        for(int i = 0; i < this.size_m; i++) {
            for(int j = 0; j < this.size_n; j++) {
                if (!(this.points[i][j] instanceof Bomb)) {
                    this.points[i][j] = new Number();
                }
            }
        }
    }
}
