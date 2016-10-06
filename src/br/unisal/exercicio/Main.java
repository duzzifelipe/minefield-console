package br.unisal.exercicio;

import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {
    private static Board board_1;

    public static void main(String[] args) {
        int running = 0;

        Main.construct_table();
        board_1.append_bombs();
        board_1.display_board();

        do {
            int pos_x = 0;
            int pos_y = 0;
            boolean type_cond = false;

            do {
                type_cond = false;

                try {
                    Scanner sc = new Scanner(System.in);
                    System.out.println("");
                    System.out.print("Type a X position: ");
                    pos_x = sc.nextInt() - 1;
                    System.out.print("Type a Y position: ");
                    pos_y = sc.nextInt() - 1;
                    type_cond = board_1.is_valid_position(pos_x, pos_y);

                    // If all ok, open a position and save if success in 'running'
                    if (type_cond) {
                        running = board_1.open(pos_x, pos_y);
                        break;

                    } else {
                        System.out.println("\n" + Color.ANSI_RED + "Type a position inside the board limits." + Color.ANSI_RESET);
                        running = 0;
                    }

                } catch (InputMismatchException e) {
                    System.out.println(Color.ANSI_RED + "Type a number! " + Color.ANSI_RESET);
                }

            } while (type_cond);

            // Only display for winning game
            if (running == 0) {
                board_1.display_board();
            }

        } while (running == 0);

        board_1.finalize_game(running);
    }

    private static void construct_table() {
        boolean next = false;
        int rows = 0;
        int cols = 0;
        int bombs = 0;

        do {
            next = false;

            try {
                Scanner sc = new Scanner(System.in);
                System.out.println("------- Table Configuration -------");
                System.out.print("Rows number: ");
                rows = sc.nextInt();
                System.out.print("Cols number: ");
                cols = sc.nextInt();
                System.out.print("Bombs number: ( <= " + (rows * cols) + "): ");
                bombs = sc.nextInt();

                board_1 = new Board(rows, cols, bombs);
                next = true;

            } catch (InputMismatchException e) {
                System.out.println("\n" + Color.ANSI_RED + "Type a number!" + Color.ANSI_RESET + "\n");
                next = false;

            } catch (Exception e) {
                System.out.println("\n");
                System.out.println("\n" + Color.ANSI_RED + "Type an allowed number." + Color.ANSI_RESET + "\n");
                next = false;
            }

        } while (!next);
    }
}
