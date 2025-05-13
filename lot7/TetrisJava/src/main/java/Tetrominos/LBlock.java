/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tetrominos;

/**
 *
 * @author SIO
 */
public class LBlock extends AbstractBlock {
    public LBlock() {
        super(new int[][][]{
            {{0, 0, 0}, {0, 1, 0}, {1, 1, 1}},
            {{0, 1, 0}, {1, 1, 0}, {0, 1, 0}},
            {{0, 0, 0}, {1, 1, 1}, {0, 1, 0}},
            {{0, 1, 0}, {0, 1, 1}, {0, 1, 0}}
        });
    }

    @Override
    public String getName() {
        return "LBlock";
    }
}


