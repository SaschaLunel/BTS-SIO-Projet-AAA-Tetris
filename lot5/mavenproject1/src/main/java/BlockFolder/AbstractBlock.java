package BlockFolder;

/**
 *
 * @author SIO
 */
public abstract class AbstractBlock implements ITetronimo {
    protected int[][][] shape; // Les différentes formes du bloc (rotations)
    protected int length;      // La taille du bloc (par exemple, pour une grille 3x3)

    // Constructeur
    public AbstractBlock(int[][][] shape) {
        this.shape = shape;
        this.length = shape[0].length; // Longueur d'une des dimensions (par défaut, la taille des rotations)
    }

    // Implémentation de getShape() depuis ITetronimo
    @Override
    public int[][][] getShape() {
        return shape;
    }

    // Méthode abstraite pour obtenir le nom du bloc (à implémenter dans les sous-classes)
    @Override
    public abstract String getName();

    // Implémentation de la méthode length() depuis ITetronimo
    @Override
    public int length() {
        return length;
    }
}
