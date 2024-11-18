/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BlockFolder;

/**
 *
 * @author SIO
 */
    // Classe abstraite AbstractBlock
    public abstract class AbstractBlock implements ITetronimo {
    protected int[][][] shape;

    public AbstractBlock(int[][][] shape) {
        this.shape = shape;
    }

    @Override
    public int[][][] getShape() {
        return shape;
    }

    @Override
    public abstract String getName(); 
}

