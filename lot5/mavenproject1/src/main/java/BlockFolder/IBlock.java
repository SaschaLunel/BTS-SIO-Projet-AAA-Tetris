/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BlockFolder;

/**
 *
 * @author SIO
 */
// Bloc I
class IBlock extends AbstractBlock {
    public IBlock() {
        super(new int[][][]{
            {{0, 0, 0, 0}, {1, 1, 1, 1}, {0, 0, 0, 0}}, // Orientation 0
            {{0, 1}, {0, 1}, {0, 1}, {0, 1}},           // Orientation 1
            {{0, 0, 0, 0}, {1, 1, 1, 1}, {0, 0, 0, 0}}, // Orientation 2
            {{0, 1}, {0, 1}, {0, 1}, {0, 1}}            // Orientation 3
        });
    }

    @Override
    public String getName() {
        return "IBlock";
    }
}
