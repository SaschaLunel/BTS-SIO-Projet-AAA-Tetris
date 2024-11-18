<?php
// Connexion à la base de données
$servername = "localhost";
$username = "root"; // Remplacez par votre nom d'utilisateur DB
$password = ""; // Remplacez par votre mot de passe DB
$dbname = "votre_db"; // Remplacez par le nom de votre base de données

$conn = new mysqli($servername, $username, $password, $dbname);

// Vérification de la connexion
if ($conn->connect_error) {
    die("Connexion échouée : " . $conn->connect_error);
}

// Démarrer la session pour récupérer l'utilisateur connecté
session_start();
$user_id = $_SESSION['user_id']; // Assurez-vous que l'ID utilisateur est stocké dans la session

// Récupérer les informations de l'utilisateur
$sql = "SELECT email, prenom, nom, pseudo FROM users WHERE id = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("i", $user_id);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows > 0) {
    $user = $result->fetch_assoc();
} else {
    die("Utilisateur non trouvé.");
}

// Mettre à jour les informations
if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST['update'])) {
    $email = $_POST['email'];
    $prenom = $_POST['prenom'];
    $nom = $_POST['nom'];
    $pseudo = $_POST['pseudo'];
    $password = password_hash($_POST['password'], PASSWORD_DEFAULT); // Hasher le mot de passe

    $sql = "UPDATE users SET email = ?, prenom = ?, nom = ?, pseudo = ?, password = ? WHERE id = ?";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("sssssi", $email, $prenom, $nom, $pseudo, $password, $user_id);

    if ($stmt->execute()) {
        echo "Informations mises à jour avec succès.";
    } else {
        echo "Erreur lors de la mise à jour : " . $conn->error;
    }
}

// Supprimer le compte
if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST['delete'])) {
    $sql = "DELETE FROM users WHERE id = ?";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("i", $user_id);

    if ($stmt->execute()) {
        session_destroy(); // Déconnecter l'utilisateur après suppression
        header("Location: login.php");
        exit();
    } else {
        echo "Erreur lors de la suppression : " . $conn->error;
    }
}

$conn->close();
?>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mon Compte</title>
    <link rel="stylesheet" href="../css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap" rel="stylesheet">
</head>
<body>
    <h1>Mon Compte</h1>

    <!-- Formulaire pour afficher et modifier les informations utilisateur -->
    <form action="mon_compte.php" method="POST">
        <label for="email">Email :</label>
        <input type="email" id="email" name="email" value="<?= htmlspecialchars($user['email']); ?>" required>
        <br>
        
        <label for="prenom">Prénom :</label>
        <input type="text" id="prenom" name="prenom" value="<?= htmlspecialchars($user['prenom']); ?>" required>
        <br>
        
        <label for="nom">Nom :</label>
        <input type="text" id="nom" name="nom" value="<?= htmlspecialchars($user['nom']); ?>" required>
        <br>
        
        <label for="pseudo">Pseudo :</label>
        <input type="text" id="pseudo" name="pseudo" value="<?= htmlspecialchars($user['pseudo']); ?>" required>
        <br>
        
        <label for="password">Nouveau mot de passe :</label>
        <input type="password" id="password" name="password">
        <small>(Laissez vide pour ne pas changer le mot de passe)</small>
        <br>
        
        <button type="submit" name="update">Mettre à jour</button>
    </form>

    <!-- Boutons Retour, Se déconnecter et Supprimer le compte -->
    <div class="conpage-buttons">
        <button onclick="location.href='dashboard.php'">Retour</button>
        <button onclick="location.href='logout.php'">Se déconnecter</button>
        <form action="mon_compte.php" method="POST" style="display:inline;">
            <button type="submit" name="delete" onclick="return confirm('Êtes-vous sûr de vouloir supprimer votre compte ? Cette action est irréversible.')">Supprimer le compte</button>
        </form>
    </div>
</body>
</html>
