<?php
session_start(); // Démarre la session
//Vérifie si l'utilisateur est déjà connecté
if (isset($_SESSION['user_id'])) {
    header("Location: my_account.php");
    exit();
}

// Informations de connexion à la base de données
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "tetris_db";


// Vérifie que le formulaire a été soumis
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $inputUsername = $_POST['username'];
    $inputPassword = $_POST['password'];

    try {
        // Connexion à la base de données
        $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
        $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        // Requête pour vérifier le nom d'utilisateur et le mot de passe
        $stmt = $conn->prepare("SELECT iduser, pseudo, mdp FROM users WHERE pseudo = :username");
        $stmt->bindParam(':username', $inputUsername);

        // Exécute la requête
        $stmt->execute();
        $user = $stmt->fetch(PDO::FETCH_ASSOC);

        // Vérifie si un utilisateur a été trouvé et si le mot de passe est correct
        if ($user && password_verify($inputPassword, $user['mdp'])) {
            // Stocke l'utilisateur dans la session
            $_SESSION['user_id'] = $user['iduser'];
            $_SESSION['username'] = $user['pseudo'];

            // Redirige vers my_account.php
            header("Location: my_account.php");
            exit();
        } else {
            echo "Nom d'utilisateur ou mot de passe incorrect.";
        }
    } catch (PDOException $e) {
        echo "Erreur : " . $e->getMessage();
    }

    // Ferme la connexion à la base de données
    $conn = null;
}
?>

<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="../css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap" rel="stylesheet">
    <link rel="icon" type="image/x-icon" href="../images/8EB7C3.ico">
</head>

<body>
<h1 id="page_title"> Login</h1>
    <!-- Formulaire de connexion -->
    <form action="login.php" class="login-form" method="POST">
        <label for="username">Username :</label>
        <input type="text" id="username" name="username" required>
        <br>
        <label for="password">Password :</label>
        <input type="password" id="password" name="password" required>
        <br>
        <button type="submit">Login</button>
    </form>

    <!-- Section centrale pour les boutons supplémentaires -->
    <div class="conpage-buttons">
        <button id="newaccButton" onclick="location.href='create_account.php';">New account</button>
        <button id="GuestButton">Guest</button>
        <button id="forgotpswButton">Forgot password</button>
    </div>

    <!-- Lien vers le script JavaScript -->
    <script src="../js/script_constversion.js"></script> 
</body>

</html>