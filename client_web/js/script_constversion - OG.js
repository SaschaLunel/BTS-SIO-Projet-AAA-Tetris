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
    if (!timerInterval) {
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
    timerElement.textContent = 'Time: 00:00';
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
    gameGrid.innerHTML = ''; // Vide le contenu de la grille
    for (let i = 0; i < GRID_SIZE; i++) {
        let cell = document.createElement('div');
        cell.classList.add('cell');
        gameGrid.appendChild(cell);
    }
    gameGrid.classList.remove('hidden'); // Affiche la grille après création
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
    inputTest.textContent = message;
    clearTimeout(resetMessageTimeout);
    resetMessageTimeout = setTimeout(() => {
        inputTest.textContent = 'Appuyez sur une flèche pour voir l\'action';
    }, 1000);
}

// Définir les formes des tétraminos
const tetrominos = {
    I: [
        [0, 1, 2, 3],  // Forme "I" en position verticale
        [0, GRID_WIDTH, GRID_WIDTH * 2, GRID_WIDTH * 3]  // Forme "I" en position horizontale
    ],
    O: [
        [0, 1, GRID_WIDTH, GRID_WIDTH + 1]  // Forme "O" (ne change pas de rotation)
    ],
    T: [
        [1, GRID_WIDTH, GRID_WIDTH + 1, GRID_WIDTH + 2],   // Forme "T" position de base
        [1, GRID_WIDTH, GRID_WIDTH + 1, GRID_WIDTH * 2 + 1],
        [GRID_WIDTH, GRID_WIDTH + 1, GRID_WIDTH + 2, GRID_WIDTH * 2 + 1],
        [1, GRID_WIDTH + 1, GRID_WIDTH + 2, GRID_WIDTH * 2 + 1]
    ],
    L: [
        [0, 1, 2, GRID_WIDTH],                // ┗━ (Position de départ)
        [1, GRID_WIDTH + 1, GRID_WIDTH * 2 + 1, 2], // ┃ (Rotation 90°)
        [GRID_WIDTH, GRID_WIDTH + 1, GRID_WIDTH + 2, 2 ], // ┛━ (Rotation 180°)
        [0, GRID_WIDTH, GRID_WIDTH * 2, GRID_WIDTH * 2 + 1]  // ┃ inversé (Rotation 270°)
    ],
    J: [
        [1, GRID_WIDTH + 1, GRID_WIDTH * 2 + 1, GRID_WIDTH * 2],   // Forme "J" position de base
        [GRID_WIDTH, GRID_WIDTH + 1, GRID_WIDTH + 2, GRID_WIDTH * 2 + 2],
        [1, GRID_WIDTH + 1, GRID_WIDTH * 2 + 1, 2],
        [0, GRID_WIDTH, GRID_WIDTH + 1, GRID_WIDTH + 2]
    ],
    S: [
        [1, 2, GRID_WIDTH, GRID_WIDTH + 1],    // Forme "S" position de base
        [0, GRID_WIDTH, GRID_WIDTH + 1, GRID_WIDTH * 2 + 1]
    ],
    Z: [
        [0, 1, GRID_WIDTH + 1, GRID_WIDTH + 2],    // Forme "Z" position de base
        [1, GRID_WIDTH, GRID_WIDTH + 1, GRID_WIDTH * 2]
    ]
};

//Fonctionnalité pour faire apparaître un tétrominos
let currentTetromino;
let currentRotation = 0;
let currentPosition = Math.floor(GRID_WIDTH / 2) - 1; // Position de départ au centre de la grille

function spawnTetromino() {
    console.log("🚀 Tentative de spawn d'un nouveau tétraminos");

    // On ne vérifie le Game Over QUE si des blocs sont déjà fixés
    if (document.querySelectorAll('.fixed').length > 0 && isGameOver()) {
        stopAutoDrop();
        console.log("💀 GAME OVER - Plus d’espace pour spawner un nouveau tétromino !");
        return;
    }

    const tetrominoKeys = Object.keys(tetrominos);
    const randomKey = tetrominoKeys[Math.floor(Math.random() * tetrominoKeys.length)];
    currentTetromino = tetrominos[randomKey];
    currentRotation = 0;
    currentPosition = 4; // Position de départ

    drawTetromino();
}



function drawTetromino() {
    console.log("drawTetro OK");
    const cells = document.querySelectorAll('.cell');
    currentTetromino[currentRotation].forEach(index => {
        cells[currentPosition + index].classList.add('tetromino');
    });
}

function undrawTetromino() {
    const cells = document.querySelectorAll('.cell');
    currentTetromino[currentRotation].forEach(index => {
         cells[currentPosition + index].classList.remove('tetromino');
    });
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
    undrawTetromino();
    currentPosition += GRID_WIDTH; // Avance d'une ligne
    drawTetromino();
    if (isAtBottom()) {
        fixTetromino();
        spawnTetromino();
    }
}

//Gestion du mouvement avec les flèches
function canMove(offset) {
    return currentTetromino[currentRotation].every(index => {
        let newPos = currentPosition + index + offset;

        // Vérifie si la nouvelle position est hors de la grille horizontalement
        const currentCol = (currentPosition + index) % GRID_WIDTH;
        const newCol = newPos % GRID_WIDTH;

        if (offset === -1 && newCol > currentCol) {
            // Déplacement vers la gauche : empêche de passer de la colonne 0 à la dernière colonne
            return false;
        }
        if (offset === 1 && newCol < currentCol) {
            // Déplacement vers la droite : empêche de passer de la dernière colonne à la colonne 0
            return false;
        }

        // Vérifie si la nouvelle position est valide (dans la grille et pas sur une cellule fixée)
        return newPos >= 0 && newPos < GRID_SIZE && !document.querySelectorAll('.cell')[newPos].classList.contains('fixed');
    });
}

function canRotate() {
    let nextRotation = (currentRotation + 1) % currentTetromino.length;
    return currentTetromino[nextRotation].every(index => {
        let newPos = currentPosition + index;
        return newPos >= 0 && newPos < GRID_SIZE && !document.querySelectorAll('.cell')[newPos].classList.contains('fixed');
    });
}

function rotateTetromino() {
    if (canRotate()) {
        undrawTetromino();
        currentRotation = (currentRotation + 1) % currentTetromino.length;
        drawTetromino();
    }
}

//Fixer le tétrominos en bas et faire apparaître un nouveau
function isAtBottom() {
    return currentTetromino[currentRotation].some(index => {
        let newPos = currentPosition + index + GRID_WIDTH;
        return newPos >= GRID_SIZE || document.querySelectorAll('.cell')[newPos].classList.contains('fixed');
    });
}
//Fonction pour fixer le tétrominos
function fixTetromino() {
    currentTetromino[currentRotation].forEach(index => {
        let cellIndex = currentPosition + index;
        let cells = document.querySelectorAll('.cell');

        if (!cells[cellIndex]) {
            console.error("❌ ERREUR : Cellule hors de la grille !");
            return;
        }
        cells[cellIndex].classList.add('fixed');
    });

    checkCompletedRows(); // Vérifie et nettoie les lignes complètes

    if (isGameOver()) {
        stopAutoDrop();
        console.log("💀 GAME OVER - Redirection vers gameover.php !");
        
        // ✅ **Stocker le score avant de rediriger**
        localStorage.setItem("lastScore", score);

        setTimeout(() => {
            window.location.href = 'gameover.php';
        }, 500);
        return;
    }

    spawnTetromino(); // Apparition d'un nouveau tétromino
}

//Gestion du score
let score = 0;
let scoreElement = document.getElementById('score'); 

//Vérification et effacement des lignes complètes
function checkCompletedRows() {
    let completedRows = 0;
    const cells = document.querySelectorAll('.cell');
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

    // 1️⃣ D'abord, on enlève TOUS les affichages de tétrominos
    cells.forEach(cell => cell.classList.remove('tetromino'));

    // 2️⃣ Ensuite, on redessine les blocs fixés
    cells.forEach(cell => {
        if (cell.classList.contains('fixed')) {
            cell.classList.add('tetromino'); // ✅ Affiche uniquement les blocs qui doivent être là
        }
    });
}
let completedRows = 0; // ✅ On initialise la variable pour éviter l'erreur

// Mise à jour du score en fonction du nombre de lignes complétées
    if (completedRows > 0) {
        score += completedRows * 100; // 100 points par ligne
        updateScoreDisplay();
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
    initGame();
});

// Vérifier si la grille est bien créée avant de l'utiliser
function initGame() {
    console.log("🎮 Initialisation du jeu...");
    
    createGrid(); // Crée la grille
    resetTimer(); // Réinitialise le timer
    startTimer(); // Démarre le timer

    if (!document.querySelectorAll('.cell').length) {
        console.error("❌ ERREUR : La grille n'a pas été générée !");
        return;
    }

    spawnTetromino(); // Fait apparaître le premier tétraminos
    startAutoDrop(); // Lance la descente automatique
}

//Gestion de fin de jeu 
// Correction du Game Over check
function isGameOver() {
    let cells = document.querySelectorAll('.cell');
    if (cells.length === 0) {
        console.error("❌ ERREUR : Impossible de vérifier le Game Over, la grille est vide !");
        return false;
    }

    let spawnZone = Array.from(cells).slice(GRID_WIDTH, GRID_WIDTH * 2); // Vérifie uniquement la 2e ligne
    let gameOver = spawnZone.some(cell => cell.classList.contains('fixed'));

    console.log("🔍 Vérification ligne de spawn :", gameOver);
    return gameOver;
}

//Gestion des touches du clavier
let dropFastInterval = null; // Intervalle pour la descente rapide
let moveLeftInterval = null; // Intervalle pour le déplacement à gauche
let moveRightInterval = null; // Intervalle pour le déplacement à droite


document.addEventListener('keydown', (event) => {
    console.log("Touche appuyée :", event.key); // 🔍 Vérification des entrées clavier
    if (event.repeat) return; // Évite les déclenchements multiples immédiats

    undrawTetromino();

    if (event.key === 'ArrowLeft' && canMove(-1)) {
        currentPosition -= 1;
        drawTetromino();

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
        drawTetromino();

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

// Fonction pour afficher temporairement la touche appuyée sur la page d'accueil
function showTemporaryMessage(message) {
    let inputTest = document.getElementById('inputTest');
    if (inputTest) {
        inputTest.textContent = `Touche pressée : ${message}`;
        clearTimeout(resetMessageTimeout);
        resetMessageTimeout = setTimeout(() => {
            inputTest.textContent = 'Appuyez sur une flèche pour voir l\'action';
        }, 1000);
    }
}

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

fixTetromino();
checkGameOver(); // Vérifie si la partie est finie après avoir fixé un bloc
