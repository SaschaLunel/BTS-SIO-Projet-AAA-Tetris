/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tetrominos;

/**
 *
 * @author SIO
 */
// Bloc T
public class TBlock extends AbstractBlock {
    public TBlock() {
        super(new int[][][]{
            {{0, 1, 0}, {1, 1, 1}},  // Orientation 0
            {{1, 0}, {1, 1}, {1, 0 }}, // Orientation 1
            {{1, 1, 1}, {0, 1, 0}},  // Orientation 2
            {{0, 1}, {1, 1}, {0, 1}}  // Orientation 3
        });
    }

    
    @Override
    public String getName() {
        return "TBlock";
    }
}
