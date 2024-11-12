// Écouteur d'événements pour le bouton GuestButton de LoginGuest.html
let guestButton = document.getElementById('GuestButton')
if(guestButton) {
    guestButton.addEventListener('click', () => {
    window.location.href = 'index.html'; // Ouvre index.html dans le même onglet
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

// Fonction pour créer la grille de jeu (lot3)
function createGrid() {
    let gameGrid = document.getElementById('gameGrid');
    gameGrid.innerHTML = ''; // Vide le contenu de la grille
    for (let i = 0; i < 200; i++) { // 10 * 20 = 200 cellules
        let cell = document.createElement('div');
        cell.classList.add('cell');
        gameGrid.appendChild(cell);
    }
    gameGrid.classList.remove('hidden'); // Affiche la grille après création
}

// Écouteur d'événements pour le bouton Play de index.html
let playButton = document.getElementById('playButton')
if (playButton) {
    playButton.addEventListener('click', () => {
        window.open('game.html', '_blank'); // Ouvre game.html dans un nouvel onglet
    });
}

// Écouteur d'événements pour le bouton Quit de index.html
let quitButton = document.getElementById('quitButton')
if (quitButton){
    quitButton.addEventListener('click', () => {
    window.location.href = 'LoginGuest.html'; // Ouvre LoginGuest.html dans le même onglet
    });
}

// Écouteur d'événements pour le bouton Play de game.html
let startGameButton = document.getElementById('startGameButton');
if (startGameButton) {
    startGameButton.addEventListener('click', () => {
        initGame();//écouteur modifié
    });
}

// Écouteur d'événements pour le bouton Quit de game.html
let quitGameButton = document.getElementById('quitGameButton');
if (quitGameButton) {
    quitGameButton.addEventListener('click', () => {
        resetTimer();      
        window.location.href = 'index.html'; //Ouvre index.html dans le même onglet
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

// Fonction de détection des input clavier (lot4)
document.addEventListener('keydown', (event) => {
    switch (event.key) {
        case 'ArrowRight':
            showTemporaryMessage('right');
            break;
        case 'ArrowLeft':
            showTemporaryMessage('left');
            break;
        case 'ArrowUp':
            showTemporaryMessage('slow down');
            break;
        case 'ArrowDown':
            showTemporaryMessage('hard drop');
            break;
        default:
            break;
    }
});

//Partie tétrominos lot4 WIP 

// Définir les formes des tétraminos
const tetrominos = {
    I: [
        [1, 11, 21, 31],  // Forme "I" en position verticale
        [10, 11, 12, 13]  // Forme "I" en position horizontale
    ],
    O: [
        [0, 1, 10, 11]  // Forme "O" (ne change pas de rotation)
    ],
    T: [
        [1, 10, 11, 12],   // Forme "T" position de base
        [1, 10, 11, 21],
        [10, 11, 12, 21],
        [1, 11, 12, 21]
    ],
    L: [
        [1, 11, 21, 22],   // Forme "L" position de base
        [10, 11, 12, 20],
        [0, 1, 11, 21],
        [10, 11, 12, 22]
    ],
    J: [
        [1, 11, 21, 20],   // Forme "J" position de base
        [10, 11, 12, 22],
        [1, 11, 21, 2],
        [0, 10, 11, 12]
    ],
    S: [
        [1, 2, 10, 11],    // Forme "S" position de base
        [0, 10, 11, 21]
    ],
    Z: [
        [0, 1, 11, 12],    // Forme "Z" position de base
        [1, 10, 11, 20]
    ]
};

//Fonctionnalité pour faire apparaître un tétrominos

let currentTetromino;
let currentRotation = 0;
let currentPosition = 4; // Position de départ en haut au centre de la grille (4ème colonne)

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
    currentPosition += 10; // Avance de 10 cases (1 rangée)
    drawTetromino();
    if (isAtBottom()) {
        fixTetromino();
        spawnTetromino();
    }
}
//Gestion du mouvement avec les flèches

document.addEventListener('keydown', (event) => {
    undrawTetromino();
    if (event.key === 'ArrowLeft' && canMove(-1)) {
        currentPosition -= 1;
    } else if (event.key === 'ArrowRight' && canMove(1)) {
        currentPosition += 1;
    } else if (event.key === 'ArrowDown') {
        moveDown();
    } else if (event.key === 'ArrowUp') {
        rotateTetromino();
    }
    drawTetromino();
});

function canMove(offset) {
    // Vérifie si le tétraminos peut se déplacer vers la gauche ou la droite
    return currentTetromino[currentRotation].every(index => {
        let newPos = currentPosition + index + offset;
        return newPos >= 0 && newPos < 200 && !document.querySelectorAll('.cell')[newPos].classList.contains('fixed');
    });
}

function rotateTetromino() {
    currentRotation = (currentRotation + 1) % currentTetromino.length;
}

//Fixer le tétrominos en bas et faire apparaître un nouveau

function isAtBottom() {
    // Vérifie si le tétraminos a atteint le bas de la grille ou un autre tétraminos
    return currentTetromino[currentRotation].some(index => {
        let newPos = currentPosition + index + 10;
        return newPos >= 200 || document.querySelectorAll('.cell')[newPos].classList.contains('fixed');
    });
}

function fixTetromino() {
    currentTetromino[currentRotation].forEach(index => {
        document.querySelectorAll('.cell')[currentPosition + index].classList.add('fixed');
    });
    checkCompletedRows(); // Vérifie les lignes complètes et les efface si nécessaire
    if (!isGameOver()) {
        spawnTetromino(); // Apparition d'un nouveau tétraminos
    } else {
        stopAutoDrop(); // Fin de la partie
        alert("Game Over!");
    }
}

//Vérification et effacement des lignes complètes

function checkCompletedRows() {
    const cells = document.querySelectorAll('.cell');
    for (let row = 0; row < 20; row++) {
        const start = row * 10;
        const end = start + 10;
        const rowCells = Array.from(cells).slice(start, end);

        if (rowCells.every(cell => cell.classList.contains('fixed'))) {
            rowCells.forEach(cell => cell.classList.remove('fixed', 'tetromino'));
            const removed = Array.from(cells).splice(start, 10);
            removed.forEach(cell => cell.remove());
            const newRow = Array(10).fill().map(() => {
                let cell = document.createElement('div');
                cell.classList.add('cell');
                return cell;
            });
            gameGrid.prepend(...newRow);
        }
    }
}


//Lancement du jeu 
function initGame() {
    resetTimer();
    startTimer();
    createGrid();  // Crée la grille visuelle
    spawnTetromino();  // Fait apparaître le premier tétraminos
    startAutoDrop();  // Démarre le mouvement automatique vers le bas
}

//Gestion de fin de jeu 

function isGameOver() {
    // Vérifie si une cellule de la première ligne est "fixed"
    return Array.from(document.querySelectorAll('.cell')).slice(0, 10).some(cell => cell.classList.contains('fixed'));
}
