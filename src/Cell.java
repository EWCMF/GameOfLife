public class Cell {
    private int livingNeighbours;
    private boolean alive = false;

    public void update() {
        if (livingNeighbours == 3 && !alive) {
            alive = true;
        }
        else alive = livingNeighbours == 3 || livingNeighbours == 2 && alive;
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
