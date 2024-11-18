<?php

session_start(); // Démarre la session

// Vérification de l'envoi du formulaire
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    // Connexion à la base de données
    $servername = "localhost";
    $username = "root";  // Nom d'utilisateur de la base de données
    $password = "";      // Mot de passe de la base de données
    $dbname = "tetris_db"; // Nom de la base de données

    try {
        $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
        // Configure le mode d'erreur PDO pour lever des exceptions
        $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        // Récupération des données du formulaire
        $email = $_POST['email'];
        $prenom = $_POST['prenom'];
        $nom = $_POST['nom'];
        $pseudo = $_POST['pseudo'];
        $dbirth = $_POST['dbirth'];
        $mdp = $_POST['mdp']; // Récupère le mot de passe

        // Hachage du mot de passe avant de l'enregistrer
        $hashed_password = password_hash($mdp, PASSWORD_DEFAULT);

        // Préparation de la requête d'insertion
        $stmt = $conn->prepare("INSERT INTO users (email, prenom, nom, pseudo, dBirth, mdp) 
                                VALUES (:email, :prenom, :nom, :pseudo, :dbirth, :mdp)");
        $stmt->bindParam(':email', $email);
        $stmt->bindParam(':prenom', $prenom);
        $stmt->bindParam(':nom', $nom);
        $stmt->bindParam(':pseudo', $pseudo);
        $stmt->bindParam(':dbirth', $dbirth);
        $stmt->bindParam(':mdp', $hashed_password);  // Lié le mot de passe haché

        // Exécution de la requête
        $stmt->execute();

        
        echo "Compte créé avec succès !";
    } catch (PDOException $e) {
        echo "Erreur : " . $e->getMessage();
    }

    // Fermeture de la connexion
    $conn = null;
}
?>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Créer un compte</title>
    <link rel="stylesheet" href="../css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap" rel="stylesheet">
    <link rel="icon" type="image/x-icon" href="../images/8EB7C3.ico">
</head>

<body>

    <!-- Formulaire de création de compte -->
    <form id="createaccount_form" action="create_account.php" method="POST">
        <label for="email">Email :</label>
        <input type="email" id="email" name="email" required>
        <br>
        <label for="prenom">First name :</label>
        <input type="text" id="prenom" name="prenom" required>
        <br>
        <label for="nom">Last name :</label>
        <input type="text" id="nom" name="nom" required>
        <br>
        <label for="pseudo">Username :</label>
        <input type="text" id="pseudo" name="pseudo" required>
        <br>
        <label for="dbirth">Date of birth</label>
        <input type="date" id="dbirth" name="dbirth" required>
        <br>
        
        <!-- Champ pour le mot de passe -->
        <label for="mdp">Password :</label>
        <input type="password" id="mdp" name="mdp" required>
        <br>

        <button id="createaccount_button" type="submit">Create account</button>
    </form>

    <!-- Section centrale pour les autres boutons -->
    <div class="conpage-buttons">
        <button id="loginButton">Login</button>
        <button id="GuestButton">Guest</button>
    </div>

    <!-- Lien vers le script JavaScript -->
    <script src="../js/script.js"></script> 

</body>
</html>
