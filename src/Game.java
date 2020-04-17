public class Game {
    private Cell[][] board;

    public void populateBoard(int x, int y) {
        board = new Cell[x][y];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Cell();
                boolean random = Math.random() > 0.5;
                if (random) {
                    board[i][j].setAlive(true);
                }
            }
        }
    }

    public void update() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Cell currentCell = board[i][j];
                currentCell.setLivingNeighbours(0);

                // non special cases
                if (i > 0 && i < board.length - 1 && j > 0 && j < board[i].length - 1) {
                    for (int k = -1; k <= 1; k++) {
                        for (int l = -1; l <= 1; l++) {
                            if (board[i + k][j + l].isAlive() && !currentCell.equals(board[i + k][j + l])) {
                                increaseLivingNeighbour(currentCell);
                            }
                        }
                    }
                    continue;
                }

                // left
                if (i == 0 && j > 0 && j < board[i].length - 1) {
                    for (int k = 0; k < 1; k++) {
                        for (int l = -1; l <= 1; l++) {
                            if (board[i + k][j + l].isAlive() && !currentCell.equals(board[i + k][j + l])) {
                                increaseLivingNeighbour(currentCell);
                            }
                        }

                    }
                    continue;
                }
                // right
                if (i == board.length - 1 && j > 0 && j < board[i].length - 1) {
                    for (int k = -1; k < 0; k++) {
                        for (int l = -1; l <= 1; l++) {
                            if (board[i + k][j + l].isAlive() && !currentCell.equals(board[i + k][j + l])) {
                                increaseLivingNeighbour(currentCell);
                            }
                        }
                    }
                    continue;
                }

                // top
                if (j == 0 && i > 0 && i < board.length - 1) {
                    for (int k = -1; k <= 1; k++) {
                        for (int l = 0; l <= 1; l++) {
                            if (board[i + k][j + l].isAlive() && !currentCell.equals(board[i + k][j + l])) {
                                increaseLivingNeighbour(currentCell);
                            }
                        }
                    }
                    continue;
                }

                // bottom
                if (j == board[i].length - 1 && i > 0 && i < board.length - 1) {
                    for (int k = -1; k <= 1; k++) {
                        for (int l = -1; l <= 0; l++) {
                            if (board[i + k][j + l].isAlive() && !currentCell.equals(board[i + k][j + l])) {
                                increaseLivingNeighbour(currentCell);
                            }
                        }
                    }
                    continue;
                }

                // top-left
                if (i == 0 && j == 0) {
                    for (int k = 0; k <= 1; k++) {
                        for (int l = 0; l <= 1; l++) {
                            if (board[i + k][j + l].isAlive() && !currentCell.equals(board[i + k][j + l])) {
                                increaseLivingNeighbour(currentCell);
                            }
                        }
                    }
                    continue;
                }

                // top-right
                if (i == board.length - 1 && j == 0) {
                    for (int k = -1; k <= 0; k++) {
                        for (int l = 0; l <= 1; l++) {
                            if (board[i + k][j + l].isAlive() && !currentCell.equals(board[i + k][j + l])) {
                                increaseLivingNeighbour(currentCell);
                            }
                        }
                    }
                    continue;
                }

                // bottom-left
                if (i == 0 && j == board[i].length - 1) {
                    for (int k = 0; k <= 1; k++) {
                        for (int l = -1; l <= 0; l++) {
                            if (board[i + k][j + l].isAlive() && !currentCell.equals(board[i + k][j + l])) {
                                increaseLivingNeighbour(currentCell);
                            }
                        }
                    }
                    continue;
                }

                // bottom-right
                if (i == board.length - 1 && j == board[i].length - 1) {
                    for (int k = -1; k <= 0; k++) {
                        for (int l = -1; l <= 0; l++) {
                            if (board[i + k][j + l].isAlive() && !currentCell.equals(board[i + k][j + l])) {
                                increaseLivingNeighbour(currentCell);
                            }
                        }
                    }
                }
            }
        }

        for (Cell[] cells : board) {
            for (Cell cell : cells) {
                cell.update();
            }
        }
    }

    private void increaseLivingNeighbour(Cell cell) {
        cell.setLivingNeighbours(cell.getLivingNeighbours() + 1);
    }

    public Cell[][] getBoard() {
        return board;
    }
}
