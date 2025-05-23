<?php
session_start(); // Démarre la session

// Vérifie si un utilisateur est connecté
$isUserLoggedIn = isset($_SESSION['iduser']);
$username = $isUserLoggedIn ? $_SESSION['username'] : 'Login';

// Charger les paramètres de l'utilisateur s'il est connecté
$useExtraPieces = 0; // Valeur par défaut

if ($isUserLoggedIn) {
    $servername = "localhost";
    $username_db = "root";
    $password_db = "";
    $dbname = "tetris_db";
    
    try {
        $conn = new mysqli($servername, $username_db, $password_db, $dbname);
        
        // Vérification de la connexion
        if ($conn->connect_error) {
            throw new Exception("Connexion échouée: " . $conn->connect_error);
        }
        
        $userId = $_SESSION['iduser'];
        
        $stmt = $conn->prepare("SELECT use_extra_pieces FROM users WHERE iduser = ?");
        $stmt->bind_param("i", $userId);
        $stmt->execute();
        $result = $stmt->get_result();
        
        if ($result->num_rows > 0) {
            $settings = $result->fetch_assoc();
            $useExtraPieces = $settings['use_extra_pieces'];
        }
        
        $stmt->close();
        $conn->close();
    } catch(Exception $e) {
        // En cas d'erreur, on garde simplement la valeur par défaut
    }
}
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

<body onload="document.body.focus();">
    <!-- Élément caché pour stocker les paramètres -->
    <div id="gameSettings" data-use-extra-pieces="<?php echo $useExtraPieces; ?>" style="display: none;"></div>

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
    
    <!-- Indicateur de mode de jeu - corrigé avec le bon id qui correspond au CSS -->
    <div id="gameModeIndicator" class="<?php echo $useExtraPieces == 1 ? 'advanced-mode-on' : ''; ?>">
        <?php echo $useExtraPieces == 1 ? 'Advanced mode: Special shapes' : 'Standard Mode'; ?>
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
    
    <!-- Zone de test pour les entrées clavier -->
    <div id="inputTest">Press arrow key to see action</div>
    
    <!-- Lien vers le script JavaScript -->
    <script src="../js/script_constversion.js"></script> 
    
    <script>
        //empechement de defillment ? 

// Empêcher le défilement avec les touches fléchées, espace, page up/down, etc.
document.addEventListener('keydown', function(e) {
    // Liste des codes de touches qui provoquent un défilement
    const scrollKeys = [
        32, // Espace
        33, // Page Up
        34, // Page Down
        35, // End
        36, // Home
        37, // Flèche gauche
        38, // Flèche haut
        39, // Flèche droite
        40  // Flèche bas
    ];
    
    // Si la touche pressée est dans la liste des touches de défilement
    if (scrollKeys.includes(e.keyCode)) {
        e.preventDefault(); // Empêche l'action par défaut (défilement)
        return false;
    }
});

// Empêcher le défilement avec la molette de la souris
document.addEventListener('wheel', function(e) {
    e.preventDefault();
}, { passive: false });

// Empêcher le défilement tactile sur mobile
document.addEventListener('touchmove', function(e) {
    e.preventDefault();
}, { passive: false });
    </script>
</body>
</html>