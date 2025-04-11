<?php
session_start(); // Démarre la session

// Vérifiez si l'utilisateur est connecté
if (!isset($_SESSION['iduser'])) {
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

$user_id = $_SESSION['iduser']; // Récupère l'ID utilisateur de la session

// Récupérer les scores de l'utilisateur
$sql = "SELECT score, dScore FROM score WHERE iduser = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("i", $user_id);
$stmt->execute();
$result = $stmt->get_result();

$scores = [];
while ($row = $result->fetch_assoc()) {
    $scores[] = $row;
}

// Gestion des filtres
$order = isset($_GET['order']) ? $_GET['order'] : 'date_desc'; // Par défaut, tri par date décroissante

if ($order == 'score_asc') {
    usort($scores, function($a, $b) {
        return $a['score'] - $b['score'];
    });
} elseif ($order == 'score_desc') {
    usort($scores, function($a, $b) {
        return $b['score'] - $a['score'];
    });
} elseif ($order == 'date_asc') {
    usort($scores, function($a, $b) {
        return strtotime($a['dScore']) - strtotime($b['dScore']);
    });
} else {
    // Par défaut, tri par date décroissante
    usort($scores, function($a, $b) {
        return strtotime($b['dScore']) - strtotime($a['dScore']);
    });
}

$conn->close();
?>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Stats</title>
<link rel="stylesheet" href="../css/style.css">
<link href="https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap" rel="stylesheet">
<link rel="icon" type="image/x-icon" href="../images/8EB7C3.ico">
</head>
<body>
    <h1 id="page_title">My Stats</h1>

    <!-- Filtres -->
    <div id="filters">
        <label>Sort by:</label>
        <select onchange="window.location.href='stats.php?order=' + this.value;">
            <option value="date_desc" <?= $order == 'date_desc' ? 'selected' : '' ?>>Newest first</option>
            <option value="date_asc" <?= $order == 'date_asc' ? 'selected' : '' ?>>Oldest first</option>
            <option value="score_desc" <?= $order == 'score_desc' ? 'selected' : '' ?>>Highest score first</option>
            <option value="score_asc" <?= $order == 'score_asc' ? 'selected' : '' ?>>Lowest score first</option>
        </select>
    </div>

   <!-- Tableau des scores -->
<table id="scores_table">
    <thead>
        <tr>
            <th>Score</th>
            <th>Date</th>
        </tr>
    </thead>
    <tbody>
        <?php if (!empty($scores)): ?>
            <?php foreach ($scores as $score): ?>
                <tr>
                    <td><?= htmlspecialchars($score['score'] ?? '') ?></td>
                    <td><?= htmlspecialchars($score['dScore'] ?? '') ?></td>
                </tr>
            <?php endforeach; ?>
        <?php else: ?>
            <tr>
                <td colspan="2">Aucun score enregistré.</td>
            </tr>
        <?php endif; ?>
    </tbody>
</table>

    <!-- Boutons à droite du menu pour jouer ou quitter le jeu -->
    <div id="right_buttons">
        <button onclick="window.location.href='game.php';">Play</button>
        <button onclick="window.location.href='index.php';">Quit</button>
    </div>

    <script src="../js/script_constversion.js"></script> 
</body>
</html>