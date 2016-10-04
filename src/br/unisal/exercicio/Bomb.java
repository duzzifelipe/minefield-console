package br.unisal.exercicio;

public class Bomb extends Point {
    private boolean fired = false;

    public void setFired(boolean fired) {
        this.fired = fired;
    }

    public boolean isFired() {
        return this.fired;
    }
}
