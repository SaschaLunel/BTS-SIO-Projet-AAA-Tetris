<?php
session_start();

// Vérifie si un utilisateur est connecté
$isUserLoggedIn = isset($_SESSION['iduser']);
$pseudo = $isUserLoggedIn ? $_SESSION['username'] : 'Login';
?>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Game Over - Тетрис</title>
    <link rel="stylesheet" href="../css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap" rel="stylesheet">
    <link rel="icon" type="image/x-icon" href="../images/8EB7C3.ico">
</head>

<body>

<h1 id="page_title" class="gameover-title">GAME OVER</h1>

<!-- Boutons en haut à droite -->
<div class="header-buttons">
    <?php if ($isUserLoggedIn): ?>
        <button id="userButton"><?= htmlspecialchars($pseudo ?? '') ?></button>
        <form action="logout.php" method="POST" class="logout-form" style="display:inline;">
            <button type="submit" id="logoutButton">Logout</button>
        </form>
    <?php else: ?>
        <button id="LoginButton" onclick="window.location.href='login.php'">Login</button>
    <?php endif; ?>
    <button id="GuestButton">Guest</button>
</div>

<!-- Score affiché -->
<div id="score-box-center" class="score-box">Score: 0</div>

<!-- Boutons centraux -->
<div class="center-buttons-GO">
    <button onclick="window.location.href='game.php'">Replay</button>
    <button onclick="window.location.href='stats.php'">Stats</button>
    <button onclick="window.location.href='settings.php'">Settings</button>
    <button onclick="window.location.href='index.php'">Quit</button>
</div>

<script src="../js/script_constversion.js"></script>

<script>
    // Récupération du score depuis le localStorage
    let lastScore = localStorage.getItem("lastScore") || 0;
    document.getElementById("score-box-center").textContent = `Score: ${lastScore}`;

    // Envoi du score si l'utilisateur est connecté
// Dans gameover.php
<?php if ($isUserLoggedIn): ?>
    console.log("Tentative d'enregistrement du score: " + lastScore);
    console.log("ID utilisateur: <?= $_SESSION['iduser'] ?>");
    
    fetch('save_score.php', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: `score=${lastScore}`,
    })
    .then(response => {
        console.log("Statut de la réponse:", response.status);
        return response.text();
    })
    .then(data => {
        console.log("Réponse du serveur:", data);
        // Afficher un message à l'utilisateur
        document.getElementById("score-box-center");
    })
    .catch(error => {
        console.error('Erreur:', error);
        // Afficher un message d'erreur à l'utilisateur
        document.getElementById("score-box-center").textContent += " (Erreur d'enregistrement)";
    });
<?php endif; ?>
</script>
</body>
</html>