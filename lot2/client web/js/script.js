// Écouteur d'événements pour le bouton GuestButton de LoginGuest.html
document.getElementById('GuestButton').addEventListener('click', () => {
    window.location.href = 'index.html'; // Ouvre index.html dans le même onglet
});

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

// Fonction pour créer la grille de jeu
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
    document.getElementById('playButton').addEventListener('click', () => {
        window.open('game.html', '_blank'); // Ouvre game.html dans un nouvel onglet
    });
}

// Écouteur d'événements pour le bouton Quit de index.html
document.getElementById('quitButton').addEventListener('click', () => {
    window.location.href = 'LoginGuest.html'; // Ouvre LoginGuest.html dans le même onglet
});

// Écouteur d'événements pour le bouton Play de game.html
let startGameButton = document.getElementById('startGameButton');
if (startGameButton) {
    startGameButton.addEventListener('click', () => {
        resetTimer();
        startTimer();
        createGrid();
    });
}
// Écouteur d'événements pour le bouton Quit de game.html
let quitGameButton = document.getElementById('quitGameButton');
if (quitGameButton) {
    quitGameButton.addEventListener('click', () => {
        stopTimer();
        resetTimer();
        document.getElementById('gameGrid').innerHTML = '';
        document.getElementById('gameGrid').classList.add('hidden');
    });
}

// Fonction d'affichage temporaire - test des input clavier
let inputTest = document.getElementById('inputTest');
let resetMessageTimeout;
function showTemporaryMessage(message) {
    inputTest.textContent = message;
    clearTimeout(resetMessageTimeout);
    resetMessageTimeout = setTimeout(() => {
        inputTest.textContent = 'Appuyez sur une flèche pour voir l\'action';
    }, 1000);
}

// Fonction de détection des input clavier 
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
