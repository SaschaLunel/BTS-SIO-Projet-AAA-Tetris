<?php
session_start(); // Démarre la session

//Vérifie si l'utilisateur est déjà connecté
if (isset($_SESSION['iduser'])) {
    header("Location: my_account.php");
    exit();
}

// Informations de connexion à la base de données
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "tetris_db";

// Configuration pour la limitation des tentatives
$max_attempts = 5; // Nombre maximum de tentatives
$lockout_duration = 120; // Durée de blocage en secondes (2 minutes)

// Fonction pour débloquer tous les comptes dont le délai est expiré
function unlockExpiredAccounts($conn, $lockout_duration) {
    try {
        $stmt = $conn->prepare("
            UPDATE users 
            SET account_locked = FALSE, failed_attempts = 0, lockout_time = NULL 
            WHERE account_locked = TRUE 
            AND lockout_time IS NOT NULL 
            AND TIMESTAMPDIFF(SECOND, lockout_time, NOW()) >= :lockout_duration
        ");
        $stmt->bindParam(':lockout_duration', $lockout_duration);
        $stmt->execute();
    } catch (PDOException $e) {
        error_log("Erreur lors du débloquage des comptes expirés : " . $e->getMessage());
    }
}

// Fonction pour vérifier et gérer le statut de blocage d'un utilisateur
function checkUserLockStatus($conn, $inputUsername, $lockout_duration) {
    try {
        $stmt = $conn->prepare("SELECT failed_attempts, account_locked, lockout_time FROM users WHERE pseudo = :username");
        $stmt->bindParam(':username', $inputUsername);
        $stmt->execute();
        $user = $stmt->fetch(PDO::FETCH_ASSOC);
        
        if (!$user) {
            return ['exists' => false, 'is_locked' => false, 'failed_attempts' => 0, 'time_remaining' => 0];
        }
        
        $is_locked = false;
        $time_remaining = 0;
        
        if ($user['account_locked'] && $user['lockout_time']) {
            $lockout_timestamp = strtotime($user['lockout_time']);
            $current_timestamp = time();
            $time_passed = $current_timestamp - $lockout_timestamp;
            
            // Vérifier si le délai de blocage est écoulé
            if ($time_passed >= $lockout_duration) {
                // Le délai est écoulé, débloquer le compte
                $unlockStmt = $conn->prepare("UPDATE users SET account_locked = FALSE, failed_attempts = 0, lockout_time = NULL WHERE pseudo = :username");
                $unlockStmt->bindParam(':username', $inputUsername);
                $unlockStmt->execute();
                $is_locked = false;
                $time_remaining = 0;
            } else {
                // Le compte est encore bloqué
                $time_remaining = $lockout_duration - $time_passed;
                $is_locked = true;
            }
        }
        
        return [
            'exists' => true,
            'is_locked' => $is_locked,
            'failed_attempts' => $user['failed_attempts'],
            'time_remaining' => max(0, $time_remaining)
        ];
        
    } catch (PDOException $e) {
        error_log("Erreur lors de la vérification du statut de blocage : " . $e->getMessage());
        return ['exists' => false, 'is_locked' => false, 'failed_attempts' => 0, 'time_remaining' => 0];
    }
}

// Fonction pour incrémenter les tentatives échouées
function incrementFailedAttempts($conn, $inputUsername, $max_attempts) {
    try {
        // Incrémenter le compteur de tentatives échouées
        $stmt = $conn->prepare("UPDATE users SET failed_attempts = failed_attempts + 1 WHERE pseudo = :username");
        $stmt->bindParam(':username', $inputUsername);
        $stmt->execute();
        
        // Vérifier si on doit bloquer le compte
        $checkStmt = $conn->prepare("SELECT failed_attempts FROM users WHERE pseudo = :username");
        $checkStmt->bindParam(':username', $inputUsername);
        $checkStmt->execute();
        $result = $checkStmt->fetch(PDO::FETCH_ASSOC);
        
        if ($result && $result['failed_attempts'] >= $max_attempts) {
            // Bloquer le compte
            $lockStmt = $conn->prepare("UPDATE users SET account_locked = TRUE, lockout_time = NOW() WHERE pseudo = :username");
            $lockStmt->bindParam(':username', $inputUsername);
            $lockStmt->execute();
            return true; // Compte bloqué
        }
        
        return false; // Compte non bloqué
        
    } catch (PDOException $e) {
        error_log("Erreur lors de l'incrémentation des tentatives : " . $e->getMessage());
        return false;
    }
}

// Fonction pour réinitialiser les tentatives après une connexion réussie
function resetFailedAttempts($conn, $inputUsername) {
    try {
        $stmt = $conn->prepare("UPDATE users SET failed_attempts = 0, account_locked = FALSE, lockout_time = NULL WHERE pseudo = :username");
        $stmt->bindParam(':username', $inputUsername);
        $stmt->execute();
    } catch (PDOException $e) {
        error_log("Erreur lors de la réinitialisation des tentatives : " . $e->getMessage());
    }
}

$error_message = "";
$success_message = "";
$is_locked = false;
$time_remaining = 0;
$failed_attempts = 0;

// Vérifie que le formulaire a été soumis
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $inputUsername = trim($_POST['username']);
    $inputPassword = $_POST['password'];

    try {
        // Connexion à la base de données
        $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
        $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        // Validation des entrées
        if (empty($inputUsername) || empty($inputPassword)) {
            $error_message = "Veuillez remplir tous les champs.";
        } else {
            // Vérifier le statut de blocage de l'utilisateur
            $lock_status = checkUserLockStatus($conn, $inputUsername, $lockout_duration);
            
            if (!$lock_status['exists']) {
                $error_message = "Nom d'utilisateur ou mot de passe incorrect.";
            } elseif ($lock_status['is_locked']) {
                $time_remaining = $lock_status['time_remaining'];
                $minutes = floor($time_remaining / 60);
                $seconds = $time_remaining % 60;
                $error_message = "Compte temporairement bloqué. Veuillez attendre " . 
                               sprintf("%02d:%02d", $minutes, $seconds) . " avant de réessayer.";
                $is_locked = true;
            } else {
                // Tenter la connexion
                $stmt = $conn->prepare("SELECT iduser, pseudo, mdp FROM users WHERE pseudo = :username");
                $stmt->bindParam(':username', $inputUsername);
                $stmt->execute();
                $user = $stmt->fetch(PDO::FETCH_ASSOC);

                // Vérifie si un utilisateur a été trouvé et si le mot de passe est correct
                if ($user && password_verify($inputPassword, $user['mdp'])) {
                    // Connexion réussie - réinitialiser les tentatives
                    resetFailedAttempts($conn, $inputUsername);
                    
                    // Stocke l'utilisateur dans la session
                    $_SESSION['iduser'] = $user['iduser'];
                    $_SESSION['username'] = $user['pseudo'];
                    $_SESSION['pseudo'] = $user['pseudo'];

                    // Redirige vers my_account.php
                    header("Location: my_account.php");
                    exit();
                } else {
                    // Échec de la connexion - incrémenter les tentatives
                    $account_locked = incrementFailedAttempts($conn, $inputUsername, $max_attempts);
                    
                    if ($account_locked) {
                        $error_message = "Nom d'utilisateur ou mot de passe incorrect. Compte temporairement bloqué pour 2 minutes.";
                        $is_locked = true;
                        $time_remaining = $lockout_duration;
                    } else {
                        $remaining_attempts = $max_attempts - ($lock_status['failed_attempts'] + 1);
                        $error_message = "Nom d'utilisateur ou mot de passe incorrect. Il vous reste $remaining_attempts tentative(s).";
                    }
                }
            }
        }
    } catch (PDOException $e) {
        error_log("Erreur de base de données : " . $e->getMessage());
        $error_message = "Erreur technique. Veuillez réessayer plus tard.";
    }

    // Ferme la connexion à la base de données
    $conn = null;
}

// Vérifier et débloquer automatiquement les comptes expirés au chargement de la page
try {
    $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    
    // Débloquer tous les comptes dont le délai est expiré
    unlockExpiredAccounts($conn, $lockout_duration);
    
    $conn = null;
} catch (PDOException $e) {
    // Ignorer les erreurs lors du débloquage automatique
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
    <style>
        .error-message {
            color: #ff4444;
            background-color: rgba(0, 0, 0, 0.7);
            border: 1px solid #ff4444;
            padding: 15px;
            margin: 10px 0;
            border-radius: 8px;
            text-align: center;
            font-family: 'Press Start 2P', monospace;
            font-size: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
        }
        
        .success-message {
            color: #44ff44;
            background-color: rgba(0, 0, 0, 0.7);
            border: 1px solid #44ff44;
            padding: 15px;
            margin: 10px 0;
            border-radius: 8px;
            text-align: center;
            font-family: 'Press Start 2P', monospace;
            font-size: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
        }
        
        .lockout-timer {
            color: #ffaa00;
            font-family: 'Press Start 2P', monospace;
            font-size: 12px;
            text-align: center;
            margin: 15px 0;
            padding: 15px;
            background-color: rgba(0, 0, 0, 0.7);
            border: 1px solid #ffaa00;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
        }
        
        .form-disabled {
            opacity: 0.5;
            pointer-events: none;
        }
        
        .attempts-warning {
            color: #ffaa00;
            font-size: 10px;
            text-align: center;
            margin-top: 10px;
            font-family: 'Press Start 2P', monospace;
            background-color: rgba(0, 0, 0, 0.7);
            padding: 12px;
            border-radius: 8px;
            border: 1px solid #ffaa00;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
        }
    </style>
</head>

<body>
<h1 id="page_title">Login</h1>

    <?php if (!empty($error_message)): ?>
        <div class="error-message"><?php echo htmlspecialchars($error_message); ?></div>
    <?php endif; ?>
    
    <?php if (!empty($success_message)): ?>
        <div class="success-message"><?php echo htmlspecialchars($success_message); ?></div>
    <?php endif; ?>
    
    <?php if ($is_locked && $time_remaining > 0): ?>
        <div class="lockout-timer" id="lockoutTimer">
            <div>🔒 Compte temporairement bloqué</div>
            <div>Temps restant: <span id="timeRemaining"><?php echo sprintf("%02d:%02d", floor($time_remaining / 60), $time_remaining % 60); ?></span></div>
        </div>
        
        <script>
            let timeRemaining = <?php echo $time_remaining; ?>;
            
            function updateTimer() {
                if (timeRemaining <= 0) {
                    // Quand le timer arrive à 0, recharger la page pour débloquer
                    window.location.href = 'login.php';
                    return;
                }
                
                const minutes = Math.floor(timeRemaining / 60);
                const seconds = timeRemaining % 60;
                document.getElementById('timeRemaining').textContent = 
                    String(minutes).padStart(2, '0') + ':' + String(seconds).padStart(2, '0');
                
                timeRemaining--;
            }
            
            // Mettre à jour le timer chaque seconde
            const timerInterval = setInterval(updateTimer, 1000);
            
            // Nettoyer l'interval si la page est fermée
            window.addEventListener('beforeunload', function() {
                clearInterval(timerInterval);
            });
        </script>
    <?php endif; ?>

    <!-- Formulaire de connexion -->
    <form action="login.php" class="login-form <?php echo $is_locked ? 'form-disabled' : ''; ?>" method="POST">
        <label for="username">Username :</label>
        <input type="text" id="username" name="username" required <?php echo $is_locked ? 'disabled' : ''; ?>>
        <br>
        <label for="password">Password :</label>
        <input type="password" id="password" name="password" required <?php echo $is_locked ? 'disabled' : ''; ?>>
        <br>
        <button type="submit" <?php echo $is_locked ? 'disabled' : ''; ?>>Login</button>
    </form>

    <?php if (!$is_locked): ?>
        <div class="attempts-warning">
            ⚠️ Maximum 5 tentatives autorisées
        </div>
    <?php endif; ?>

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