package br.unisal.exercicio;

public class Number extends Point {
    private boolean open = false;
    private int side_bombs = 0;

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isOpen() {
        return this.open;
    }

    public int getSideBombs() {
        return side_bombs;
    }

    public void setSideBombs(int side_bombs) {
        this.side_bombs = side_bombs;
    }
}
