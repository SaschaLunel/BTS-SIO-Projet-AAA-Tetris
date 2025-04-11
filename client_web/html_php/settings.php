<?php
session_start();

if (!isset($_SESSION['user_id'])) {
    header("Location: login.php");
    exit();
}

$servername = "localhost";
$username = "root";
$password = "";
$dbname = "tetris_db";

try {
    $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    $userId = $_SESSION['user_id'];
    $message = "";

    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
        $useExtraPieces = isset($_POST['use_extra_pieces']) ? 1 : 0;

        $stmt = $conn->prepare("UPDATE users SET use_extra_pieces = :useExtra WHERE iduser = :userId");
        $stmt->bindParam(':useExtra', $useExtraPieces);
        $stmt->bindParam(':userId', $userId);
        $stmt->execute();

        $message = "Paramètres mis à jour !";
    }

    $stmt = $conn->prepare("SELECT use_extra_pieces FROM users WHERE iduser = :userId");
    $stmt->bindParam(':userId', $userId);
    $stmt->execute();
    $settings = $stmt->fetch(PDO::FETCH_ASSOC);

} catch (PDOException $e) {
    echo "Erreur : " . $e->getMessage();
    exit();
}
?>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Settings</title>
    <link rel="stylesheet" href="../css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap" rel="stylesheet">
    
</head>
<body>

<h2 id="page_title">Game Settings</h2>
    <form action="settings.php" method="POST" class="settings-form">
       

        <div class="setting-group">
            <label>
                <input type="checkbox" name="use_extra_pieces" <?= $settings['use_extra_pieces'] ? 'checked' : '' ?>>
                Enable special shapes
            </label>
        </div>

        <div class="setting-group">
            <label for="music-volume">Music Volume</label>
            <input type="range" id="music-volume" name="music_volume" min="0" max="100" value="50">
        </div>

        <div class="setting-group">
            <label for="sfx-volume">Sound Effects Volume</label>
            <input type="range" id="sfx-volume" name="sfx_volume" min="0" max="100" value="50">
        </div>

        <button type="submit">Save Settings</button>

        <?php if (!empty($message)): ?>
            <div class="message"><?= htmlspecialchars($message) ?></div>
        <?php endif; ?>
    </form>

    <div class="conpage-buttons">
        <button onclick="window.location.href='my_account.php'">My Account</button>
        <button onclick="window.location.href='index.php'">Quit</button>
    </div>

</body>
</html>
