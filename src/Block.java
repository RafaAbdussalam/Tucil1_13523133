import java.util.ArrayList;
import java.util.List;

public class Block {
    private char[][] shape;
    private char id;
    private List<char[][]> allOrientations;
    
    public Block(List<String> blockLines, char id) {
        this.id = id;
        this.shape = parser(blockLines);
        this.allOrientations = Orientations();
    }
    
    private char[][] parser(List<String> blockLines) {
        int maxRows = blockLines.size();
        int maxCols = 0;
        for (String line : blockLines) {
            maxCols = Math.max(maxCols, line.length());
        }
        
        char[][] blockShape = new char[maxRows][maxCols];
        for (int i = 0; i < maxRows; i++) {
            for (int j = 0; j < maxCols; j++) {
                blockShape[i][j] = '.';
            }
        }
        
        for (int i = 0; i < blockLines.size(); i++) {
            String line = blockLines.get(i);
            for (int j = 0; j < line.length(); j++) {
                blockShape[i][j] = line.charAt(j);
            }
        }
        return blockShape;
    }
    
    private List<char[][]> Orientations() {
        List<char[][]> orientations = new ArrayList<>();
        
        orientations.add(shape);
        
        for (int rotation = 1; rotation <= 4; rotation++) {
            char[][] rotated = rotateBlock(shape, rotation);
            orientations.add(rotated);
        }
        
        for (int mirror = 1; mirror <= 2; mirror++) {
            char[][] mirrored = mirror(shape);
            orientations.add(mirrored);
            
            for (int rotation = 1; rotation <= 4; rotation++) {
                char[][] rotatedMirrored = rotateBlock(mirrored, rotation);
                orientations.add(rotatedMirrored);
            }
        }
        
        return orientations;
    }
    
    private char[][] rotateBlock(char[][] block, int times) {
        for (int t = 0; t < times; t++) {
            int rows = block.length;
            int cols = block[0].length;
            char[][] rotated = new char[cols][rows];
            
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    rotated[j][rows-1-i] = block[i][j];
                }
            }
            block = rotated;
        }
        
        return block;
    }
    
    private char[][] mirror(char[][] block) {
        int rows = block.length;
        int cols = block[0].length;
        char[][] mirrored = new char[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                mirrored[i][cols-1-j] = block[i][j];
            }
        }
        return mirrored;
    }
    
    public char[][] getShape() {
        return shape;
    }
    
    public List<char[][]> getAllOrientations() {
        return allOrientations;
    }
    
    public char getId() {
        return id;
    }
}