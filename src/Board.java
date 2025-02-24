public class Board {
    private final int rows;
    private final int cols;
    private char[][] grid;
    
    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new char[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = '.';
            }
        }
    }
    
    public boolean canbePlace(Block block, int startRow, int startCol) {
        char[][] shape = block.getShape();
        
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] == block.getId()) {
                    int newRow = startRow + i;
                    int newCol = startCol + j;
                    if (!isInBounds(newRow, newCol) || !isCellEmpty(newRow, newCol)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    public void placeBlock(Block block, int startRow, int startCol) {
        char[][] shape = block.getShape();

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] == block.getId()) {
                    grid[startRow + i][startCol + j] = block.getId();
                }
            }
        }
    }
    
    public void removeBlock(Block block, int startRow, int startCol) {
        char[][] shape = block.getShape();
        
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] == block.getId()) {
                    grid[startRow + i][startCol + j] = '.';
                }
            }
        }
    }
    
    public boolean isFull() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == '.') {
                    return false;
                }
            }
        }
        return true;
    }
    
    public void printBoard() {
        String[] colors = {
            "\u001B[31m", 
            "\u001B[32m", 
            "\u001B[33m", 
            "\u001B[34m", 
            "\u001B[35m", 
            "\u001B[36m",
            "\u001B[37m",
            "\u001B[40m",
            "\u001B[41m",
            "\u001B[42m",
            "\u001B[44m",
            "\u001B[45m",
            "\u001B[46m",
            "\u001B[47m",
            "\u001B[91m", 
            "\u001B[92m", 
            "\u001B[93m", 
            "\u001B[94m", 
            "\u001B[95m", 
            "\u001B[96m",
            "\u001B[43m",
            "\u001B[30m",
            "\u001B[104m",
            "\u001B[105m",
            "\u001B[106m",
            "\u001B[107m"
        };
        String reset = "\u001B[0m";
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == '.') {
                    System.out.print('.');
                } else {
                    int colorId = grid[i][j] - 'A';
                    if (colorId >= 0 && colorId < colors.length) {
                        System.out.print(colors[colorId] + grid[i][j] + reset);
                    } else {
                        System.out.print(grid[i][j]);
                    }
                }
            }
            System.out.println();
        }
    }
    
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                str.append(grid[i][j]);
            }
            str.append('\n');
        }
        return str.toString();
    }

    public boolean isInBounds(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }
    
    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
    
    public boolean isCellEmpty(int row, int col) {
        return grid[row][col] == '.';
    }
}