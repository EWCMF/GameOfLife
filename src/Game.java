public class Game {
    Cell[][] board;

    public void populateBoard(int x, int y) {
        board = new Cell[x][y];
        for (Cell[] cells : board) {
            for (Cell cell : cells) {
                cell = new Cell();
                boolean random = Math.random() > 0.5;
                if (random) {
                    cell.setAlive(true);
                }
            }
        }
    }
}
