/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BlockFolder;

/**
 *
 * @author SIO
 */
// Bloc O
class OBlock extends AbstractBlock {
    public OBlock() {
        super(new int[][][]{
            {{1, 1}, {1, 1}} // Toutes les orientations sont identiques
        });
    }

    @Override
    public String getName() {
        return "OBlock";
    }
}
