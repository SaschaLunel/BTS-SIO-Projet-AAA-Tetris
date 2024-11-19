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
    <title>Lets Play Tetriscraft !</title>
    <link rel="stylesheet" href="../css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap" rel="stylesheet">
    <link rel="icon" type="image/x-icon" href="../images/8EB7C3.ico">
</head>

<body>
<h1 id="page_title"> Tetriscraft</h1>
    <!-- Section pour les boutons en haut à droite -->
    <div class="header-buttons">
        <?php if ($isUserLoggedIn): ?>
            <button id="userButton"><?php echo htmlspecialchars($username); ?></button>
            <!-- Formulaire pour le bouton Logout -->
            <form action="logout.php" method="POST" class="logout-form" style="display:inline;">
                <button type="submit" id="logoutButton">Logout</button>
            </form>
        <?php else: ?>
            <button id="LoginButton" onclick="window.location.href='login.php'">Login</button>
        <?php endif; ?>
        <button id="GuestButton">Guest</button>
    </div>

    <!-- Section centrale pour les boutons principaux -->
    <div class="center-buttons">
        <button id="playButton">Play</button>
        <button id="statsButton">Stats</button>
        <button id="settingsButton">Settings</button>
        <button id="quitButton">Quit</button>
    </div>

    <!-- Section pour le timer de test en haut à gauche -->
    <div id="timer-test">
        <div id="timer">Time: 00:00</div>
        <div class="timer-buttons">
            <button id="playTimer">Play</button>
            <button id="pauseTimer">Pause</button>
            <button id="resetTimer">Reset</button>
        </div>
    </div>

    <div id="inputTest">Appuyez sur une flèche pour voir l'action</div>
    
    <!-- Lien vers le script JavaScript -->
    <script>
        // Vérifie si l'utilisateur est connecté
        const isUserLoggedIn = <?php echo json_encode($isUserLoggedIn); ?>;

        // Gestion du clic sur le bouton userButton
        const userButton = document.getElementById('userButton');
        if (userButton) {
            userButton.addEventListener('click', () => {
                if (isUserLoggedIn) {
                    // Redirige vers my_account.php si connecté
                    window.location.href = 'my_account.php';
                }
            });
        }
    </script>
</body>
</html>
