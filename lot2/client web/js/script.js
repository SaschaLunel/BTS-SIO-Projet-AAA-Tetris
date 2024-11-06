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

// Fonction pour créer une grille de 10x20
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
document.getElementById('playButton').addEventListener('click', () => {
    window.open('game.html', '_blank'); // Ouvre game.html dans un nouvel onglet
});

// Écouteur d'événements pour le bouton Play de game.html
let startGameButton = document.getElementById('startGameButton');
if (startGameButton) {
    startGameButton.addEventListener('click', () => {
        console.log('Start Game Button clicked'); // Vérifie si le bouton est cliqué
        resetTimer(); // Réinitialise le timer
        console.log('Timer reset'); // Vérifie si la réinitialisation fonctionne
        startTimer(); // Démarre le timer
        console.log('Timer started'); // Vérifie si le timer démarre
        createGrid(); // Crée la grille de jeu
        console.log('Grid created'); // Vérifie si la grille est créée
    });
}


// Écouteur d'événements pour le bouton Quit de game.html
let quitGameButton = document.getElementById('quitGameButton');
if (quitGameButton) {
    quitGameButton.addEventListener('click', () => {
        stopTimer(); // Arrête le timer
        resetTimer(); // Réinitialise le timer
        document.getElementById('gameGrid').innerHTML = ''; // Efface la grille
        document.getElementById('gameGrid').classList.add('hidden'); // Cache la grille
    });
}

// Fonction pour afficher un message temporaire
let actionDisplay = document.getElementById('actionDisplay');
let resetMessageTimeout;
function showTemporaryMessage(message) {
    actionDisplay.textContent = message;
    clearTimeout(resetMessageTimeout);
    resetMessageTimeout = setTimeout(() => {
        actionDisplay.textContent = 'Appuyez sur une flèche pour voir l\'action';
    }, 1000);
}

// Fonction pour gérer les appuis sur les touches fléchées
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
