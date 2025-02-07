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
// Variables pour le timer (lot4)
let timerElement = document.getElementById('timer');
let seconds = 0;
let timerInterval;

// Fonction pour formater le temps en mm:ss (lot4)
function formatTime(seconds) {
    let minutes = Math.floor(seconds / 60);
    let remainingSeconds = seconds % 60;
    return (
        (minutes < 10 ? '0' : '') + minutes + ':' +
        (remainingSeconds < 10 ? '0' : '') + remainingSeconds
    );
}

// Fonction pour démarrer le timer (lot4)
function startTimer() {
    if (!timerInterval) {
        timerInterval = setInterval(() => {
            seconds++;
            timerElement.textContent = 'Time: ' + formatTime(seconds);
        }, 1000);
    }
}

// Fonction pour arrêter le timer (lot4)
function stopTimer() {
    clearInterval(timerInterval);
    timerInterval = null;
}

// Fonction pour réinitialiser le timer (lot4)
function resetTimer() {
    stopTimer();
    seconds = 0;
    timerElement.textContent = 'Time: 00:00';
}

// Ecouteur d'événements pour les boutons du timer (lot4)
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

// Fonction pour créer la grille de jeu (lot3)
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
let playButton = document.getElementById('playButton')
if (playButton) {
    playButton.addEventListener('click', () => {
        window.open('game.php', '_blank'); // Ouvre game.php dans un nouvel onglet
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
        [GRID_WIDTH, GRID_WIDTH + 1, GRID_WIDTH + 2, GRID_WIDTH * 2 + 2], // ┛━ (Rotation 180°)
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
    console.log("spawnTetro OK");
    const tetrominoKeys = Object.keys(tetrominos);
    const randomKey = tetrominoKeys[Math.floor(Math.random() * tetrominoKeys.length)];
    currentTetromino = tetrominos[randomKey];
    currentRotation = 0;
    currentPosition = 4; // Position de départ pour chaque nouveau tétraminos
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

function fixTetromino() {
    currentTetromino[currentRotation].forEach(index => {
        document.querySelectorAll('.cell')[currentPosition + index].classList.add('fixed');
    });
    checkCompletedRows(); // Vérifie les lignes complètes et les efface si nécessaire
    if (!isGameOver()) {
        spawnTetromino(); // Apparition d'un nouveau tétrominos
    } else {
        stopAutoDrop(); // Fin de la partie
        alert("Game Over!");
    }
}

//Gestion du score
let score = 0;
let scoreElement = document.getElementById('score'); 

//Vérification et effacement des lignes complètes

function checkCompletedRows() {
    const cells = document.querySelectorAll('.cell');
    let completedRows = 0; // Compteur de lignes complètes

    for (let row = 0; row < GRID_HEIGHT; row++) {
        const start = row * GRID_WIDTH;
        const end = start + GRID_WIDTH;
        const rowCells = Array.from(cells).slice(start, end);

        if (rowCells.every(cell => cell.classList.contains('fixed'))) {
            rowCells.forEach(cell => cell.classList.remove('fixed', 'tetromino'));
            completedRows++;

            // Descendre les lignes supérieures
            for (let i = start - 1; i >= 0; i--) {
                if (cells[i].classList.contains('fixed')) {
                    cells[i].classList.remove('fixed');
                    cells[i + GRID_WIDTH].classList.add('fixed');
                }
            }
        }
    }

    // Mise à jour du score en fonction du nombre de lignes complétées
    if (completedRows > 0) {
        score += completedRows * 100; // 100 points par ligne
        updateScoreDisplay();
    }
}

function updateScoreDisplay() {
    if (scoreElement) {
        scoreElement.textContent = `Score: ${score}`;
    }
}

//limitation des déplacements latéraux
let lateralMoveCount = 0;
const MAX_LATERAL_MOVES = 3; // Nombre maximum de déplacements latéraux avant de forcer la descente

//Lancement du jeu 
function initGame() { // Fonction d'initialisation du jeu
    resetTimer(); // Réinitialise le timer
    startTimer(); // Démarre le timer
    createGrid();  // Crée la grille visuelle
    spawnTetromino();  // Fait apparaître le premier tétrominos
    startAutoDrop();  // Démarre le mouvement automatique vers le bas
}

//Gestion de fin de jeu 
function isGameOver() { 
    return Array.from(document.querySelectorAll('.cell')).slice(0, GRID_WIDTH).some(cell => cell.classList.contains('fixed'));
}

document.addEventListener('DOMContentLoaded', () => {
    let inputTest = document.getElementById('inputTest');
    let gameGrid = document.getElementById('gameGrid');

    document.addEventListener('keydown', (event) => {
        if (inputTest && !gameGrid) {
            // Mode page d'accueil -> Tester les touches
            showTemporaryMessage(event.key);
        } else if (gameGrid) {
            // Mode jeu -> Gérer les mouvements
            if (event.repeat) return; // Empêche les répétitions involontaires

            undrawTetromino();

            if (event.key === 'ArrowLeft' && canMove(-1)) {
                currentPosition -= 1;
            } else if (event.key === 'ArrowRight' && canMove(1)) {
                currentPosition += 1;
            } else if (event.key === 'ArrowDown') {
                moveDown();
                lateralMoveCount = 0; // Réinitialisation du compteur
            } else if (event.key === 'ArrowUp') {
                rotateTetromino();
            }

            drawTetromino();
        }
    });
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


