<?php
session_start(); // Démarre la session

// Vérifie si un utilisateur est connecté
$isUserLoggedIn = isset($_SESSION['username']);
$username = $isUserLoggedIn ? $_SESSION['username'] : 'Login';
?>  

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Game Over - Tetriscraft</title>
    <link rel="stylesheet" href="../css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap" rel="stylesheet">
    <link rel="icon" type="image/x-icon" href="../images/8EB7C3.ico">
</head>

<body>

<h1 id="page_title" class="gameover-title"> GAME OVER </h1>

<!-- Section pour les boutons en haut à droite -->
<div class="header-buttons">
    <?php if ($isUserLoggedIn): ?>
        <button id="userButton"><?php echo htmlspecialchars($username); ?></button>
        <form action="logout.php" method="POST" class="logout-form" style="display:inline;">
            <button type="submit" id="logoutButton">Logout</button>
        </form>
    <?php else: ?>
        <button id="LoginButton" onclick="window.location.href='login.php'">Login</button>
    <?php endif; ?>
    <button id="GuestButton">Guest</button>
</div>

<!-- Section pour le score -->
<div id="score-box-center" class="score-box">Score: 0</div>


<script>
    // ✅ Récupérer le score depuis localStorage et l'afficher
    let finalScore = localStorage.getItem("lastScore") || 0;
    document.getElementById("finalScore").textContent = `Score: ${finalScore}`;
</script>

<!-- Section centrale pour les boutons -->
<div class="center-buttons-GO">
    <button id="replayButton" onclick="window.location.href='game.php'">Replay</button>
    <button id="statsButton">Stats</button>
    <button id="settingsButton">Settings</button>
    <button id="quitButton" onclick="window.location.href='index.php'">Quit</button>
</div>

<script src="../js/script_constversion.js"></script> 

<script>
    // Vérifie si l'utilisateur est connecté
    const isUserLoggedIn = <?php echo json_encode($isUserLoggedIn); ?>;

    if (isUserLoggedIn) {
        const userButton = document.getElementById('userButton');
        userButton.addEventListener('click', () => {
            window.location.href = 'my_account.php';
        });
    }
</script>
<script>
    // ✅ Récupérer le score stocké
    let lastScore = localStorage.getItem("lastScore") || 0;
    
    // ✅ Afficher le score dans le cadre
    document.getElementById("score-box-center").textContent = `Score: ${lastScore}`;
</script>

</body>
</html>
