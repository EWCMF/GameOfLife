public class Cell {
    private int livingNeighbours;
    private boolean alive = false;

    public void update() {
        // Rules for cell here.

        // Death of loneliness.
        if (livingNeighbours < 2 && alive) {
            alive = false;
        }
        // Death of overpopulation.
        else if (alive && livingNeighbours > 3) {
            alive = false;
        }
        // Birth
        else if (!alive && livingNeighbours == 3) {
            alive = true;
        }
    }

    public int getLivingNeighbours() {
        return livingNeighbours;
    }

    public void setLivingNeighbours(int livingNeighbours) {
        this.livingNeighbours = livingNeighbours;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
