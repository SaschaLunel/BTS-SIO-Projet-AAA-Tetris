console.log("✅ JS Chargé !");

// Écouteur d'événements pour le bouton GuestButton de LoginGuest.html
let guestButton = document.getElementById('GuestButton')
if(guestButton) {
    guestButton.addEventListener('click', () => {
    window.location.href = 'index.php'; // Ouvre index.php dans le même onglet
    });
}
// Écouteur d'événements pour le bouton LoginButton de LoginGuest.html
let LoginButton = document.getElementById('LoginButton')
if(LoginButton) {
    LoginButton.addEventListener('click', () => {
    window.location.href = 'login.php'; // Ouvre login.php dans le même onglet
    });
}
// Écouteur d'événements pour le bouton newaccButton de login.html
let newaccButton = document.getElementById('newaccButton')
if(newaccButton) {
    newaccButton.addEventListener('click', () => {
    window.location.href = 'create_account.php'; // Ouvre create_account.php dans le même onglet
    });
}
// Écouteur d'événements pour le bouton userButton de index.html
let userButton = document.getElementById('userButton')
if(userButton) {
    userButton.addEventListener('click', () => {
    window.location.href = 'my_account.php'; // Ouvre my_account.php dans le même onglet
    });
}

// Variables pour le timer
let timerElement = document.getElementById('timer');
let seconds = 0;
let timerInterval;

// Fonction pour formater le temps en mm:ss 
function formatTime(seconds) {
    let minutes = Math.floor(seconds / 60);
    let remainingSeconds = seconds % 60;
    return (
        (minutes < 10 ? '0' : '') + minutes + ':' +
        (remainingSeconds < 10 ? '0' : '') + remainingSeconds
    );
}

// Fonction pour démarrer le timer 
function startTimer() {
    if (!timerInterval && timerElement) {
        timerInterval = setInterval(() => {
            seconds++;
            timerElement.textContent = 'Time: ' + formatTime(seconds);
        }, 1000);
    }
}

// Fonction pour arrêter le timer 
function stopTimer() {
    clearInterval(timerInterval);
    timerInterval = null;
}

// Fonction pour réinitialiser le timer 
function resetTimer() {
    stopTimer();
    seconds = 0;
    if (timerElement) {
        timerElement.textContent = 'Time: 00:00';
    }
}

// Ecouteur d'événements pour les boutons du timer 
let startTimerButton = document.getElementById('playTimer')
if (startTimerButton){
    startTimerButton.addEventListener('click', () => {
        startTimer();
    });
}
let pauseTimerButton = document.getElementById('pauseTimer')
if (pauseTimerButton){
    pauseTimerButton.addEventListener('click', () => {
        stopTimer();
    });
}
let resetTimerButton = document.getElementById('resetTimer')
if (resetTimerButton){
    resetTimerButton.addEventListener('click', () => {
        resetTimer();
    });
}

//constantes de dimensions de la grille
const GRID_WIDTH = 10;
const GRID_HEIGHT = 20;
const GRID_SIZE = GRID_WIDTH * GRID_HEIGHT;

// Fonction pour créer la grille de jeu 
function createGrid() {
    let gameGrid = document.getElementById('gameGrid');
    if (!gameGrid) {
        console.error("❌ ERREUR : L'élément gameGrid n'existe pas !");
        return false;
    }
    
    gameGrid.innerHTML = ''; // Vide le contenu de la grille
    for (let i = 0; i < GRID_SIZE; i++) {
        let cell = document.createElement('div');
        cell.classList.add('cell');
        gameGrid.appendChild(cell);
    }
    gameGrid.classList.remove('hidden'); // Affiche la grille après création
    return true;
}

// Écouteur d'événements pour le bouton Play de index.php
let playButton = document.getElementById('playButton');
if (playButton) {
    playButton.addEventListener('click', () => {
        window.open('game.php', '_self'); // Ouvre game.php dans le même onglet
    });
}

// Écouteur d'événements pour le bouton Quit de index.php
let quitButton = document.getElementById('quitButton')
if (quitButton){
    quitButton.addEventListener('click', () => {
    window.location.href = 'LoginGuest.html'; // Ouvre LoginGuest.html dans le même onglet
    });
}

// Écouteur d'événements pour le bouton Play de game.php
let startGameButton = document.getElementById('startGameButton');
if (startGameButton) {
    startGameButton.addEventListener('click', () => {
        initGame();//écouteur modifié
    });
}

// Écouteur d'événements pour le bouton Quit de game.php
let quitGameButton = document.getElementById('quitGameButton');
if (quitGameButton) {
    quitGameButton.addEventListener('click', () => {
        resetTimer();      
        window.location.href = 'index.php'; //Ouvre index.php dans le même onglet
    });
}

// Fonction d'affichage temporaire - test des input clavier (lot4)
let inputTest = document.getElementById('inputTest');
let resetMessageTimeout;
function showTemporaryMessage(message) {
    if (inputTest) {
        inputTest.textContent = message;
        clearTimeout(resetMessageTimeout);
        resetMessageTimeout = setTimeout(() => {
            inputTest.textContent = 'Press arrow key to see action';
        }, 1000);
    }
}

const standardTetrominos = {
    I: [
        [0, 0, 0, 0],
        [1, 1, 1, 1],
        [0, 0, 0, 0],
        [0, 0, 0, 0]
    ],
    O: [
        [1, 1, 0, 0],
        [1, 1, 0, 0],
        [0, 0, 0, 0],
        [0, 0, 0, 0]
    ],
    T: [
        [0, 1, 0, 0],
        [1, 1, 1, 0],
        [0, 0, 0, 0],
        [0, 0, 0, 0]
    ],
    S: [
        [0, 1, 1, 0],
        [1, 1, 0, 0],
        [0, 0, 0, 0],
        [0, 0, 0, 0]
    ],
    Z: [
        [1, 1, 0, 0],
        [0, 1, 1, 0],
        [0, 0, 0, 0],
        [0, 0, 0, 0]
    ],
    J: [
        [1, 0, 0, 0],
        [1, 1, 1, 0],
        [0, 0, 0, 0],
        [0, 0, 0, 0]
    ],
    L: [
        [0, 0, 1, 0],
        [1, 1, 1, 0],
        [0, 0, 0, 0],
        [0, 0, 0, 0]
    ]
};

const advancedTetrominos = {
    P: [
        [1, 1, 0, 0],
        [1, 1, 1, 0],
        [0, 0, 0, 0],
        [0, 0, 0, 0]
    ],
    W: [
        [1, 0, 0, 0],
        [1, 1, 0, 0],
        [0, 1, 1, 0],
        [0, 0, 0, 0]
    ],
    U: [
        [1, 0, 1, 0],
        [1, 1, 1, 0],
        [0, 0, 0, 0],
        [0, 0, 0, 0]
    ],
    F: [
        [0, 1, 1, 0],
        [0, 1, 0, 0],
        [1, 1, 0, 0],
        [0, 0, 0, 0]
    ],
    V: [
        [1, 0, 0, 0],
        [1, 0, 0, 0],
        [1, 1, 1, 0],
        [0, 0, 0, 0]
    ],
    Y: [
        [0, 1, 0, 0],
        [1, 1, 1, 0],
        [0, 1, 0, 0],
        [0, 0, 0, 0]
    ]
};
// Fonction pour obtenir le pool actuel de tétrominos
function getCurrentTetrominosPool() {
    let pool = { ...standardTetrominos };
    
    if (window.advancedTetrominosEnabled) {
        // Ajouter les tétrominos avancés au pool
        pool = { ...pool, ...advancedTetrominos };
    }
    
    return pool;
}

//Fonctionnalité pour faire apparaître un tétrominos
let currentTetromino;
let currentPosition = Math.floor(GRID_WIDTH / 2) - 1; // Position de départ au centre de la grille

function spawnTetromino() {
    console.log("🚀 Tentative de spawn d'un nouveau tétraminos");

    // Vérifier si la grille existe avant d'essayer d'y accéder
    const cells = document.querySelectorAll('.cell');
    if (cells.length === 0) {
        console.error("❌ ERREUR : Impossible de spawner un tétromino, la grille n'existe pas !");
        return false;
    }

    // Vérifier le Game Over seulement si des cellules fixes existent
    const fixedCells = document.querySelectorAll('.fixed');
    if (fixedCells.length > 0 && isGameOver()) {
        stopAutoDrop();
        console.log("💀 GAME OVER - Plus d'espace pour spawner un nouveau tétromino !");
        localStorage.setItem("lastScore", score);
        setTimeout(() => {
            window.location.href = 'gameover.php';
        }, 1000);
        return false;
    }

    const currentPool = getCurrentTetrominosPool();
    const tetrominoKeys = Object.keys(currentPool);
    
    // Vérifiez que le pool n'est pas vide
    if (tetrominoKeys.length === 0) {
        console.error("❌ ERREUR : Aucun tétromino disponible dans le pool !");
        return false;
    }
    
    const randomKey = tetrominoKeys[Math.floor(Math.random() * tetrominoKeys.length)];
    currentTetromino = currentPool[randomKey];
    
    // Afficher des informations de débogage
    console.log("Tétromino choisi:", randomKey);
    console.log("Matrice:", currentTetromino);
    
    // Calculer la position de départ pour centrer le tetromino
    // Le offset est la moitié de la largeur de la grille moins la moitié de la largeur du tetromino
    const tetrominoWidth = currentTetromino[0].length;
    currentPosition = Math.floor((GRID_WIDTH - tetrominoWidth) / 2);
    
    return drawTetromino();
}

// Modifions la fonction drawTetromino pour qu'elle fonctionne avec des matrices 2D
function drawTetromino() {
    console.log("drawTetro OK");
    const cells = document.querySelectorAll('.cell');
    
    if (cells.length === 0) {
        console.error("❌ ERREUR : Impossible de dessiner le tétromino, la grille n'existe pas !");
        return false;
    }
    
    if (!currentTetromino) {
        console.error("❌ ERREUR : Aucun tétromino actif !");
        return false;
    }
    
    // Parcours de la matrice du tetromino actuel
    for (let y = 0; y < currentTetromino.length; y++) {
        for (let x = 0; x < currentTetromino[y].length; x++) {
            // Si la cellule contient un 1, on la dessine
            if (currentTetromino[y][x] === 1) {
                const cellIndex = currentPosition + (y * GRID_WIDTH) + x;
                if (cellIndex >= 0 && cellIndex < GRID_SIZE) {
                    cells[cellIndex].classList.add('tetromino');
                }
            }
        }
    }
    return true;
}

// De même pour undrawTetromino
function undrawTetromino() {
    const cells = document.querySelectorAll('.cell');
    
    if (cells.length === 0) {
        console.error("❌ ERREUR : Impossible d'effacer le tétromino, la grille n'existe pas !");
        return false;
    }
    
    if (!currentTetromino) {
        console.error("❌ ERREUR : Aucun tétromino actif à effacer !");
        return false;
    }
    
    for (let y = 0; y < currentTetromino.length; y++) {
        for (let x = 0; x < currentTetromino[y].length; x++) {
            if (currentTetromino[y][x] === 1) {
                const cellIndex = currentPosition + (y * GRID_WIDTH) + x;
                if (cellIndex >= 0 && cellIndex < GRID_SIZE) {
                    cells[cellIndex].classList.remove('tetromino');
                }
            }
        }
    }
    return true;
}

//Mouvement automatique vers le bas
let dropInterval;

function startAutoDrop() {
    dropInterval = setInterval(moveDown, 1000); // Déplacement vers le bas toutes les secondes
}

function stopAutoDrop() {
    clearInterval(dropInterval);
}

function moveDown() {
    if (!undrawTetromino()) return;
    
    currentPosition += GRID_WIDTH; // Avance d'une ligne
    
    if (!drawTetromino()) return;
    
    if (isAtBottom()) {
        fixTetromino();
        // Suppression de l'appel à spawnTetromino ici car il est déjà appelé dans fixTetromino
    }
}

//Gestion du mouvement avec les flèches
function canMove(offset) {
    if (!currentTetromino) return false;
    
    for (let y = 0; y < currentTetromino.length; y++) {
        for (let x = 0; x < currentTetromino[y].length; x++) {
            if (currentTetromino[y][x] === 1) {
                const currentIndex = currentPosition + (y * GRID_WIDTH) + x;
                const newIndex = currentIndex + offset;
                
                // Vérifiez les limites horizontales
                const currentCol = (currentIndex % GRID_WIDTH);
                const newCol = newIndex % GRID_WIDTH;
                
                // Si on essaie de déplacer à gauche et ça fait un wrap-around
                if (offset === -1 && newCol === GRID_WIDTH - 1) {
                    return false;
                }
                
                // Si on essaie de déplacer à droite et ça fait un wrap-around
                if (offset === 1 && newCol === 0) {
                    return false;
                }
                
                // Vérifier si la nouvelle position est dans la grille et pas sur une cellule fixée
                if (newIndex < 0 || newIndex >= GRID_SIZE) {
                    return false;
                }
                
                const cells = document.querySelectorAll('.cell');
                if (cells.length === 0) return false;
                
                if (cells[newIndex].classList.contains('fixed')) {
                    return false;
                }
            }
        }
    }
    return true;
}

function canRotate() {
    if (!currentTetromino) return false;
    
    // Créer une matrice temporaire pour la rotation
    const rotated = [];
    const size = currentTetromino.length;
    
    for (let y = 0; y < size; y++) {
        rotated[y] = [];
        for (let x = 0; x < size; x++) {
            rotated[y][x] = currentTetromino[size - 1 - x][y];
        }
    }
    
    const cells = document.querySelectorAll('.cell');
    if (cells.length === 0) return false;
    
    // Vérifier si la rotation est possible
    for (let y = 0; y < size; y++) {
        for (let x = 0; x < size; x++) {
            if (rotated[y][x] === 1) {
                const cellIndex = currentPosition + (y * GRID_WIDTH) + x;
                const colPosition = (currentPosition % GRID_WIDTH) + x;
                
                // Vérifier si la position est valide
                if (
                    cellIndex < 0 || 
                    cellIndex >= GRID_SIZE || 
                    colPosition < 0 || 
                    colPosition >= GRID_WIDTH ||
                    cells[cellIndex].classList.contains('fixed')
                ) {
                    return false;
                }
            }
        }
    }
    return true;
}

function rotateTetromino() {
    if (!currentTetromino) return false;
    
    if (canRotate()) {
        undrawTetromino();
        // Créer une nouvelle matrice pour la rotation
        const rotated = [];
        const size = currentTetromino.length;
        
        for (let y = 0; y < size; y++) {
            rotated[y] = [];
            for (let x = 0; x < size; x++) {
                rotated[y][x] = currentTetromino[size - 1 - x][y];
            }
        }
        
        currentTetromino = rotated;
        drawTetromino();
        return true;
    }
    return false;
}

//Fixer le tétrominos en bas et faire apparaître un nouveau
function isAtBottom() {
    if (!currentTetromino) return false;
    
    const cells = document.querySelectorAll('.cell');
    if (cells.length === 0) return false;
    
    for (let y = 0; y < currentTetromino.length; y++) {
        for (let x = 0; x < currentTetromino[y].length; x++) {
            if (currentTetromino[y][x] === 1) {
                const cellIndex = currentPosition + (y * GRID_WIDTH) + x;
                const nextRowIndex = cellIndex + GRID_WIDTH;
                
                // Si on est au bas de la grille ou si la cellule en dessous est occupée
                if (nextRowIndex >= GRID_SIZE || 
                    (nextRowIndex < GRID_SIZE && cells[nextRowIndex].classList.contains('fixed'))) {
                    return true;
                }
            }
        }
    }
    return false;
}

//Fonction pour fixer le tétrominos
function fixTetromino() {
    if (!currentTetromino) {
        console.error("❌ ERREUR : Impossible de fixer le tétromino, aucun tétromino actif !");
        return false;
    }
    
    const cells = document.querySelectorAll('.cell');
    if (cells.length === 0) {
        console.error("❌ ERREUR : Impossible de fixer le tétromino, la grille n'existe pas !");
        return false;
    }
    
    for (let y = 0; y < currentTetromino.length; y++) {
        for (let x = 0; x < currentTetromino[y].length; x++) {
            if (currentTetromino[y][x] === 1) {
                const cellIndex = currentPosition + (y * GRID_WIDTH) + x;
                if (cellIndex >= 0 && cellIndex < GRID_SIZE) {
                    cells[cellIndex].classList.add('fixed');
                }
            }
        }
    }
    
    checkCompletedRows();
    if (isGameOver()) {
        stopAutoDrop();
        localStorage.setItem("lastScore", score);
        setTimeout(() => {
            window.location.href = 'gameover.php';
        }, 1000);
        return false;
    }
    
    return spawnTetromino();
}

//Gestion du score
let score = 0;
let scoreElement = document.getElementById('score'); 

//Vérification et effacement des lignes complètes
function checkCompletedRows() {
    const cells = document.querySelectorAll('.cell');
    if (cells.length === 0) return;
    
    let completedRows = 0;
    let rowsToRemove = [];

    for (let row = 0; row < GRID_HEIGHT; row++) {
        const start = row * GRID_WIDTH;
        const end = start + GRID_WIDTH;
        const rowCells = Array.from(cells).slice(start, end);

        if (rowCells.every(cell => cell.classList.contains('fixed'))) {
            rowsToRemove.push(row);
        }
    }

    if (rowsToRemove.length > 0) {
        rowsToRemove.forEach(row => {
            const start = row * GRID_WIDTH;
            const end = start + GRID_WIDTH;
            const rowCells = Array.from(cells).slice(start, end);

            rowCells.forEach(cell => {
                cell.classList.remove('fixed', 'tetromino');
            });

            // Descendre les lignes au-dessus
            for (let i = start - 1; i >= 0; i--) {
                if (cells[i].classList.contains('fixed')) {
                    cells[i].classList.remove('fixed');
                    cells[i + GRID_WIDTH].classList.add('fixed');
                }
            }
        });

        score += rowsToRemove.length * 100;
        updateScoreDisplay();

        // ✅ Attendre un petit délai avant de rafraîchir pour éviter les "traces"
        setTimeout(refreshGrid, 50);
    }
}

function refreshGrid() {
    const cells = document.querySelectorAll('.cell');
    if (cells.length === 0) return;

    // 1️⃣ D'abord, on enlève TOUS les affichages de tétrominos
    cells.forEach(cell => cell.classList.remove('tetromino'));

    // 2️⃣ Ensuite, on redessine les blocs fixés
    cells.forEach(cell => {
        if (cell.classList.contains('fixed')) {
            cell.classList.add('tetromino'); // ✅ Affiche uniquement les blocs qui doivent être là
        }
    });
    
    // Redessiner le tétromino actif
    if (currentTetromino) {
        drawTetromino();
    }
}

//Mise à jour de l'affichage du score
function updateScoreDisplay() {
    if (scoreElement) {
        scoreElement.textContent = `Score: ${score}`;
    }
}

//limitation des déplacements latéraux
let lateralMoveCount = 0;
const MAX_LATERAL_MOVES = 3; // Nombre maximum de déplacements latéraux avant de forcer la descente

//Lancement du jeu 
document.addEventListener("DOMContentLoaded", () => {
    console.log("✅ DOM chargé, script actif !");
    
    // Vérifier si on est sur la page de jeu en cherchant l'élément gameGrid
    let gameGrid = document.getElementById('gameGrid');
    if (gameGrid) {
        initGame();
    }
});

// Vérifier si la grille est bien créée avant de l'utiliser
function initGame() {
    console.log("🎮 Initialisation du jeu...");
    
    if (!createGrid()) {
        console.error("❌ ERREUR : Impossible d'initialiser le jeu, problème avec la création de la grille !");
        return;
    }
    
    resetTimer(); // Réinitialise le timer
    startTimer(); // Démarre le timer

    score = 0;
    updateScoreDisplay();
    
    if (spawnTetromino()) {
        startAutoDrop(); // Lance la descente automatique
    } else {
        console.error("❌ ERREUR : Impossible de faire apparaître un tétromino !");
    }
}

//Gestion de fin de jeu 
// Correction du Game Over check
function isGameOver() {
    let cells = document.querySelectorAll('.cell');
    if (cells.length === 0) {
        console.error("❌ ERREUR : Impossible de vérifier le Game Over, la grille est vide !");
        return false;
    }

    // Vérifier si la deuxième ligne (ligne de spawn) contient des blocs fixés
    let spawnZone = Array.from(cells).slice(0, GRID_WIDTH * 2);
    let gameOver = spawnZone.some(cell => cell.classList.contains('fixed'));

    if (gameOver) {
        console.log("🔍 GAME OVER détecté - La zone de spawn est occupée !");
    }
    
    return gameOver;
}

//Gestion des touches du clavier
let dropFastInterval = null; // Intervalle pour la descente rapide
let moveLeftInterval = null; // Intervalle pour le déplacement à gauche
let moveRightInterval = null; // Intervalle pour le déplacement à droite

document.addEventListener('keydown', (event) => {
    // Vérifier si on a un tétromino actif et si on est sur la page de jeu
    if (!currentTetromino || document.querySelectorAll('.cell').length === 0) return;
    
    console.log("Touche appuyée :", event.key); // 🔍 Vérification des entrées clavier
    if (event.repeat) return; // Évite les déclenchements multiples immédiats

    if (!undrawTetromino()) return;

    if (event.key === 'ArrowLeft' && canMove(-1)) {
        currentPosition -= 1;
        
        // Déplacement en continu si la touche reste enfoncée
        if (!moveLeftInterval) {
            moveLeftInterval = setInterval(() => {
                if (canMove(-1)) {
                    undrawTetromino();
                    currentPosition -= 1;
                    drawTetromino();
                }
            }, 180); // 180ms entre chaque déplacement
        }
    } 

    else if (event.key === 'ArrowRight' && canMove(1)) {
        currentPosition += 1;
        
        // Déplacement en continu si la touche reste enfoncée
        if (!moveRightInterval) {
            moveRightInterval = setInterval(() => {
                if (canMove(1)) {
                    undrawTetromino();
                    currentPosition += 1;
                    drawTetromino();
                }
            }, 180); // 180ms entre chaque déplacement
        }
    }

    else if (event.key === 'ArrowDown') {
        moveDown(); // Descente immédiate
        lateralMoveCount = 0; // Réinitialisation

        // Activation de la descente continue
        if (!dropFastInterval) {
            dropFastInterval = setInterval(() => {
                moveDown();
            }, 100); // 100ms entre chaque descente
        }
    }

    else if (event.key === 'ArrowUp') {
        rotateTetromino();
    }

    drawTetromino();
});

// Arrêter la descente rapide quand on relâche la touche
document.addEventListener('keyup', (event) => {
    if (event.key === 'ArrowLeft' && moveLeftInterval) {
        clearInterval(moveLeftInterval);
        moveLeftInterval = null;
    }

    if (event.key === 'ArrowRight' && moveRightInterval) {
        clearInterval(moveRightInterval);
        moveRightInterval = null;
    }

    if (event.key === 'ArrowDown' && dropFastInterval) {
        clearInterval(dropFastInterval);
        dropFastInterval = null;
    }
});

// Fonction pour afficher temporairement la touche appuyée
document.addEventListener('keydown', (event) => {
    switch (event.key) {
        case 'ArrowLeft':
            showTemporaryMessage("⬅️ Left");
            break;
        case 'ArrowRight':
            showTemporaryMessage("➡️ Right");
            break;
        case 'ArrowDown':
            showTemporaryMessage("⬇️ Down");
            break;
        case 'ArrowUp':
            showTemporaryMessage("⬆️ Flip");
            break;
        default:
            // Optionnel : ne rien faire pour les autres touches
            break;
    }
});

function checkGameOver() {
    if (isGameOver()) {
        stopAutoDrop();
        console.log("💀 GAME OVER - Redirection vers l'écran de fin !");
        
        // ✅ Stocker le score dans le localStorage avant de quitter la page
        localStorage.setItem("lastScore", score);

        setTimeout(() => {
            window.location.href = 'gameover.php';
        }, 500); // Laisse 500ms avant la redirection
    }
}

function updateGameModeIndicator() {
    const indicator = document.getElementById('gameModeIndicator');
    if (!indicator) return;
    
    if (window.advancedTetrominosEnabled) {
        indicator.textContent = "Advanced mode: Special shapes";
        indicator.classList.add('advanced-mode-on');
    } else {
        indicator.textContent = "Standard Mode";
        indicator.classList.remove('advanced-mode-on');
    }
}

// Mettez à jour l'indicateur au chargement de la page
document.addEventListener("DOMContentLoaded", () => {
    updateGameModeIndicator();
    // Reste du code DOMContentLoaded...
});


// Variable globale pour contrôler l'utilisation des tétrominos avancés
let advancedTetrominosEnabled = false;

// Fonction pour charger les paramètres depuis la session PHP
function loadGameSettings() {
    // Rechercher un élément avec un data-attribute qui contiendrait le paramètre
    const settingsElement = document.getElementById('gameSettings');
    if (settingsElement) {
        const useExtraPieces = settingsElement.getAttribute('data-use-extra-pieces');
        window.advancedTetrominosEnabled = (useExtraPieces === '1');
        console.log("✅ Paramètres chargés : formes spéciales = " + window.advancedTetrominosEnabled);
        updateGameModeIndicator();
    } else {
        console.log("ℹ️ Aucun paramètre trouvé, utilisation des valeurs par défaut");
        window.advancedTetrominosEnabled = false;
    }
}

// Mettre à jour cette partie dans votre code existant
document.addEventListener("DOMContentLoaded", () => {
    console.log("✅ DOM chargé, script actif !");
    
    // Charger les paramètres du jeu
    loadGameSettings();
    
    // Vérifier si on est sur la page de jeu en cherchant l'élément gameGrid
    let gameGrid = document.getElementById('gameGrid');
    if (gameGrid) {
        initGame();
    }
});

// Variable pour la gestion de la pause
let gamePaused = false;
let pauseOverlay = null; // Pour l'overlay de pause

// Fonction pour mettre le jeu en pause
function pauseGame() {
    if (gamePaused) return; // Évite de mettre en pause plusieurs fois
    
    gamePaused = true;
    stopAutoDrop(); // Arrête la descente automatique
    stopTimer(); // Arrête le timer
    
    // Création d'un overlay de pause si nécessaire
    if (!pauseOverlay) {
        pauseOverlay = document.createElement('div');
        pauseOverlay.id = 'pauseOverlay';
        pauseOverlay.innerHTML = '<div class="pause-message">PAUSE<br><span class="pause-hint">Appuyez sur Espace pour continuer</span></div>';
        pauseOverlay.style.position = 'absolute';
        pauseOverlay.style.top = '0';
        pauseOverlay.style.left = '0';
        pauseOverlay.style.width = '100%';
        pauseOverlay.style.height = '100%';
        pauseOverlay.style.backgroundColor = 'rgba(0, 0, 0, 0.7)';
        pauseOverlay.style.color = 'white';
        pauseOverlay.style.fontSize = '2em';
        pauseOverlay.style.display = 'flex';
        pauseOverlay.style.justifyContent = 'center';
        pauseOverlay.style.alignItems = 'center';
        pauseOverlay.style.zIndex = '1000';
        pauseOverlay.style.textAlign = 'center';
        
        // Ajouter un style pour le texte plus petit
        const style = document.createElement('style');
        style.textContent = `
        .pause-message {
            text-align: center;
            font-weight: bold;
        }
        .pause-hint {
            font-size: 0.5em;
            display: block;
            margin-top: 15px;
            opacity: 0.8;
        }`;
        document.head.appendChild(style);
        
        // Trouver le conteneur du jeu et ajouter l'overlay
        const gameContainer = document.getElementById('gameGrid')?.parentElement || document.body;
        gameContainer.style.position = 'relative'; // S'assurer que le conteneur a une position relative
        gameContainer.appendChild(pauseOverlay);
    } else {
        pauseOverlay.style.display = 'flex'; // Afficher l'overlay s'il existe déjà
    }
    
    console.log("⏸️ Jeu en pause");
    showTemporaryMessage("⏸️ PAUSE");
}

// Fonction pour reprendre le jeu
function resumeGame() {
    if (!gamePaused) return; // Évite de reprendre si le jeu n'est pas en pause
    
    gamePaused = false;
    startAutoDrop(); // Redémarre la descente automatique
    startTimer(); // Redémarre le timer
    
    // Cacher l'overlay de pause
    if (pauseOverlay) {
        pauseOverlay.style.display = 'none';
    }
    
    console.log("▶️ Jeu repris");
    showTemporaryMessage("▶️ JEU REPRIS");
}

// Fonction pour basculer entre pause et reprise
function togglePause() {
    if (gamePaused) {
        resumeGame();
    } else {
        pauseGame();
    }
}

// Modifier la gestion des touches du clavier pour inclure la pause
document.addEventListener('keydown', (event) => {
    // Gérer la pause avec la barre d'espace
    if (event.key === ' ' || event.code === 'Space') {
        event.preventDefault(); // Empêche le défilement de la page
        togglePause();
        return;
    }
    
    // Si le jeu est en pause, ne pas traiter les autres touches
    if (gamePaused) return;
    
    // Le reste de votre gestion des touches existante...
});