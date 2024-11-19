<?php
session_start(); // Démarre la session

// Vérifiez si l'utilisateur est connecté
if (!isset($_SESSION['user_id'])) {
    header("Location: login.php");
    exit();
}

// Connexion à la base de données
$servername = "localhost";
$username = "root"; // Remplacez par votre nom d'utilisateur DB
$password = ""; // Remplacez par votre mot de passe DB
$dbname = "tetris_db"; // Remplacez par le nom de votre base de données

$conn = new mysqli($servername, $username, $password, $dbname);

// Vérification de la connexion
if ($conn->connect_error) {
    die("Connexion échouée : " . $conn->connect_error);
}

$user_id = $_SESSION['user_id']; // Récupère l'ID utilisateur de la session

// Récupérer les informations de l'utilisateur
$sql = "SELECT email, prenom, nom, pseudo, dBirth FROM users WHERE iduser = ?";
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
    $password = !empty($_POST['password']) ? password_hash($_POST['password'], PASSWORD_DEFAULT) : null;

    $sql = $password 
        ? "UPDATE users SET email = ?, prenom = ?, nom = ?, pseudo = ?, password = ? WHERE id = ?" 
        : "UPDATE users SET email = ?, prenom = ?, nom = ?, pseudo = ? WHERE id = ?";
    $stmt = $conn->prepare($sql);

    if ($password) {
        $stmt->bind_param("sssssi", $email, $prenom, $nom, $pseudo, $password, $user_id);
    } else {
        $stmt->bind_param("ssssi", $email, $prenom, $nom, $pseudo, $user_id);
    }

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
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>My Account</title>
<link rel="stylesheet" href="../css/style.css">
<link href="https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap" rel="stylesheet">
<link rel="icon" type="image/x-icon" href="../images/8EB7C3.ico">
</head>
<body>
    <h1 id="page_title"> My Account</h1>
    <form id="my_account_form" method="POST" action="">
        <label>Email:</label>
        <input type="email" name="email" value="<?= htmlspecialchars($user['email']) ?>" required>
        <br>

        <label>First name:</label>
        <input type="text" name="prenom" value="<?= htmlspecialchars($user['prenom']) ?>" required>
        <br>

        <label>Last name:</label>
        <input type="text" name="nom" value="<?= htmlspecialchars($user['nom']) ?>" required>
        <br>

        <label>Username:</label>
        <input type="text" name="pseudo" value="<?= htmlspecialchars($user['pseudo']) ?>" required>
        <br>

        <label>Birthday:</label>
        <input type="date" name="date_de_naissance" value="<?= htmlspecialchars($user['dBirth']) ?>" required>
        <br>

        <label>New password:</label>
        <input type="password" name="password">
        <br>

        <button type="submit" name="update">Update</button>
        <button type="submit" name="delete" onclick="return confirm('Voulez-vous vraiment supprimer votre compte ?');">Delete account</button>
    </form>
</body>
</html>
