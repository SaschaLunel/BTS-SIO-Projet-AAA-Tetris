<?php
session_start();
error_log("Tentative d'enregistrement de score. User ID: " . $_SESSION['iduser'] . ", Score: " . $_POST['score']);

// Vérifie la connexion de l'utilisateur
if (!isset($_SESSION['iduser'])) {
    http_response_code(403);
    exit("Utilisateur non connecté.");
}

// Vérifie la présence du score
if (!isset($_POST['score'])) {
    http_response_code(400);
    exit("Score non fourni.");
}

$score = intval($_POST['score']);
$iduser = $_SESSION['iduser'];

// Connexion à la base de données
$host = 'localhost';
$dbname = 'tetris_db';
$username = 'root';
$password = '';

try {
    $pdo = new PDO("mysql:host=$host;dbname=$dbname;charset=utf8", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Insertion du score
    $stmt = $pdo->prepare("INSERT INTO score (iduser, score, dScore) VALUES (:iduser, :score, NOW())");
    $stmt->execute([
        ':iduser' => $iduser,
        ':score' => $score,
    ]);

    echo "Score enregistré avec succès.";
} catch (PDOException $e) {
    http_response_code(500);
    error_log("Erreur SQL: " . $e->getMessage());
    echo "Erreur lors de l'enregistrement du score : " . $e->getMessage();
}
error_log("Score enregistré avec succès pour l'utilisateur " . $iduser);
