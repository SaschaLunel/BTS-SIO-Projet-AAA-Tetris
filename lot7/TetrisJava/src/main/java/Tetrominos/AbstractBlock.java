package Tetrominos;

public abstract class AbstractBlock {
    private int[][][] rotations;  // Tableau 3D représentant les rotations de la pièce

    // Constructeur qui initialise les rotations
    public AbstractBlock(int[][][] rotations) {
        this.rotations = rotations;
    }

    // Fonction pour obtenir une rotation spécifique
    public int[][] getRotation(int rotation) {
        if (rotation < 0 || rotation >= rotations.length) {
            throw new IllegalArgumentException("Rotation invalide");
        }
        return rotations[rotation];  // Retourne la matrice correspondant à la rotation
    }

    // Fonction pour obtenir le nombre de rotations possibles
    public int getRotationCount() {
        return rotations.length;  // Retourne le nombre de rotations
    }

    // Méthode abstraite pour obtenir le nom de la pièce
    public abstract String getName();
    
    // Méthode pour définir la longueur (à titre d'exemple)
    public void setLength(int length) {
        // Logique de gestion de la longueur (si nécessaire)
    }
}
