/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tetrominos;

/**
 *
 * @author SIO
 */
// Bloc S
public class SBlock extends AbstractBlock {
    public SBlock() {
        super(new int[][][]{
            {{0, 1, 1}, {1, 1, 0}},  // Orientation 0
            {{1, 0}, {1, 1}, {0, 1}}, // Orientation 1
            {{0, 1, 1}, {1, 1, 0}},  // Orientation 2
            {{1, 0}, {1, 1}, {0, 1}}  // Orientation 3
        });
    }

    @Override
    public String getName() {
        return "SBlock";
    }
}
