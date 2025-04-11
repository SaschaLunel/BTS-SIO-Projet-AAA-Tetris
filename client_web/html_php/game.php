<?php
session_start(); // Démarre la session

// Vérifie si un utilisateur est connecté
$isUserLoggedIn = isset($_SESSION['iduser']);
$username = $isUserLoggedIn ? $_SESSION['username'] : 'Login';
?>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Now playing !</title>
    <link rel="stylesheet" href="../css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap" rel="stylesheet">
    <link rel="icon" type="image/x-icon" href="../images/8EB7C3.ico">
</head>

<body>
<body onload="document.body.focus();">

    <!-- Section pour les boutons en haut à droite -->
    <div class="header-buttons">
        <!-- Si l'utilisateur est connecté, afficher son nom -->
        <?php if ($isUserLoggedIn): ?>
            <button id="userButton"><?php echo htmlspecialchars($username); ?></button>
            <button id="logoutButton" onclick="window.location.href='logout.php'">Logout</button>
        <?php else: ?>
            <button id="LoginButton">Login</button>
        <?php endif; ?>
        <button id="GuestButton">Guest</button>
    </div>
    
    <!-- Timer -->
    <div id="timer">Time: 00:00</div>

    <!-- Grille de jeu au centre, masquée par défaut -->
    <div id="gameGrid" class="hidden"></div>

    <!-- Boutons de commande sur le côté droit, en bas -->
    <div class="side-buttons">
        <button id="startGameButton">Play</button>
        <button id="statsButton" onclick="window.location.href='stats.php'">Stats</button>
        <button id="settingsButton" onclick="window.location.href='settings.php'">Settings</button>
        <button id="quitGameButton">Quit</button>
    </div>
    
    <!-- Score, masqué par défaut -->
    <div id="score-box"><span id="score">Score: 0</span></div>

    <!-- Lien vers le script JavaScript -->
    <script src="../js/script_constversion.js"></script> 
    
</body>
</html>
