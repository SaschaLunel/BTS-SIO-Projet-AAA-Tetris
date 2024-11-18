/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BlockFolder;

/**
 *
 * @author SIO
 */

    // Bloc Z
class ZBlock extends AbstractBlock {
    public ZBlock() {
        super(new int[][][]{
            {{1, 1, 0}, {0, 1, 1}},  // Orientation 0
            {{0, 1}, {1, 1}, {1, 0}}, // Orientation 1
            {{1, 1, 0}, {0, 1, 1}},  // Orientation 2
            {{0, 1}, {1, 1}, {1, 0}}  // Orientation 3
        });
    }

    @Override
    public String getName() {
        return "ZBlock";
    }
}

