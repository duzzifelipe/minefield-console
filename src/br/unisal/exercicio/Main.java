package br.unisal.exercicio;

import java.util.Scanner;

public class Main {
    private static Board board_1;
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = false;

        Main.construct_table();
        board_1.append_bombs();

        do {
            int pos_x = 0;
            int pos_y = 0;
            boolean type_cond = false;

            do {
                type_cond = false;

                System.out.println("");
                System.out.print("Type a X position: ");
                pos_x = sc.nextInt();
                System.out.print("Type a Y position: ");
                pos_y = sc.nextInt();
                type_cond = board_1.is_valid_position(pos_x, pos_y);

                if (type_cond) {
                    running = board_1.open(pos_x, pos_y);
                    board_1.display_board();

                } else {
                    System.out.println("\n" + Color.ANSI_RED + "Type a position inside the board limits." + Color.ANSI_RESET);
                }

            } while (!type_cond);

        } while(!running);

        board_1.finalize_game();
    }

    private static void construct_table() {
        boolean next = false;
        int rows = 0;
        int cols = 0;
        int bombs = 0;

        do {
            next = false;

            try {
                System.out.println("------- Table Configuration -------");
                System.out.print("Rows number: ");
                rows = sc.nextInt();
                System.out.print("Cols number: ");
                cols = sc.nextInt();
                System.out.print("Bombs number: ( <= " + (rows * cols) + "): ");
                bombs = sc.nextInt();

                board_1 = new Board(rows, cols, bombs);
                next = true;
            
            } catch (Exception e) {
                System.out.println("\n");
                System.out.println(Color.ANSI_RED + "Type an allowed number." + Color.ANSI_RESET);
                next = false;
            }
            
        } while (!next);
    }
}
