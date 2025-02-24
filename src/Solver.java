import java.util.ArrayList;
import java.util.List;

public class Solver {
    private Board board;
    private List<Block> blocks;
    private long iterationCount;
    
    public Solver(Board board, List<Block> blocks) {
        this.board = board;
        this.blocks = blocks;
        this.iterationCount = 0;
    }
    
    public boolean solve() {
        return solvepuzzle(0);
    }
    
    private boolean solvepuzzle(int blockIndex) {
        if (blockIndex >= blocks.size()) {
            return true;
        }
        
        Block block = blocks.get(blockIndex);
        List<char[][]> orientations = block.getAllOrientations();

        List<Block> allBlock = new ArrayList<>();
        for(char[][] orientation : orientations) {
            List<String> orientationStrings = allblocktolist(orientation);
            allBlock.add(new Block(orientationStrings, block.getId()));
        }

        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                for (Block orientedBlocks : allBlock) {
                    iterationCount++;

                    if (board.canPlaceBlock(orientedBlocks, row, col)) {
                        board.placeBlock(orientedBlocks, row, col);
                        
                        if (solvepuzzle(blockIndex + 1)) {
                            return true;
                        } 
                        board.removeBlock(orientedBlocks, row, col);
                    }
                }
            }
        }
        
        return false;
    }
    
    private List<String> allblocktolist (char[][] orientation) {
        List<String> result = new ArrayList<>();
        for (char[] row : orientation) {
            result.add(new String(row));
        }
        return result;
    }
    
    public long getIterationCount() {
        return iterationCount;
    }
}