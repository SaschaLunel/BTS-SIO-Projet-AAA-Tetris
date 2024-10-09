# Projet Scolaire : Création d'Application (Version Web & Java)

## INTRODUCTION. Calcul des Heures de Travail sur le Projet

Dans le cadre de notre projet, il est important de planifier le temps consacré au développement de notre application. À partir d'aujourd'hui, **5 octobre 2024**, nous planifions **12 heures** de travail par semaine (1,5jours/semaine). 

Pour déterminer le nombre total d'heures de travail jusqu'à **mi-mai 2025**, nous prenons en compte les éléments suivants :

- **Durée totale entre le 5 octobre 2024 et le 15 mai 2025 :** environ **26 semaines**.
- **Semaines de stage (où nous ne travaillons pas sur le projet) :** **10 semaines**.
- **Semaines de vacances à Noël :** **2 semaines**.

### Calcul des Semaines de Travail
- Semaines de travail effectives = **26 semaines - 10 semaines (stage) - 2 semaines (vacances)** = **14 semaines**.

### Total d'Heures de Travail
- Total d'heures = **14 semaines * 12 heures/semaine = 168 heures**.

Ainsi, nous disposerons de **3*168 heures** pour travailler sur notre projet avant mi-mai 2025.

## 1. Définition du projet _(3*3heures)_

### 1.1. Définir les objectifs du projet _(3*1heure)_:
   - Quels sont les besoins des utilisateurs ?
   - Quelles sont les fonctionnalités principales ?
   - Définir le public cible et le contexte d'utilisation.

### 1.2. Rédiger un cahier des charges  _(3*2heures)_ :
- **Laura :** _(4heures)_
  - Introduction
  - Objectifs
  - Permissions et rôles
  - Priorités
  - Estimation des coûts
  - Documentation

- **Thomas :** _(4heures)_
  - Parcours utilisateur
  - Spécifications techniques (technologies et sécurité)
  - Délais et jalons
  - Évolutivité
  - Gestion des risques
  - UI/UX

- **Sascha :** _(4heures)_
  - Fonctionnalités principales
  - Compatibilité
  - Contraintes de performance
  - Wireframes
  - Mises à jour
  - Identification des risques

---

## 2. Conception et Planification _(3*20heures)_

### 2.1. Architecture _(3*4heures)_:
   - **Version web** : Suivre l'architecture MVC (Modèle-Vue-Contrôleur).
   - **Version Java** : Conception orientée objet avec gestion des événements et interactions utilisateur.

### 2.2. Design de l'interface utilisateur _(3*8heures)_:
   - Prototypage de l'UI/UX pour les deux versions.
   - Utilisation de **HTML/CSS** pour le web et **Swing/JavaFX** pour Java.
   - Prendre en compte les aspects d’ergonomie et d’accessibilité.

### 2.3. Choix des technologies _(3*4heures)_:
   - **Version web :** 
     - Front-end : **HTML5, CSS3, JavaScript**.
     - Back-end : **PHP** pour la logique serveur.
     - Base de données : **SQL** avec **Wamp**.

   - **Version Java :**
     - Développement avec **Java** dans **NetBeans**.
     - Base de données : **SQL** via **JDBC** ou avec un framework comme **Hibernate**.

### 2.4. Planification des tâches _(3*4heures)_:
   - Gestion du projet avec **Trello** pour suivre les tâches et les deadlines.
   - Définir des jalons pour chaque étape clé.

### 2.5. Conception de la base de données _(3*4heures)_:
   - **Modèle Conceptuel de Données (MCD)** : 
     - Créer un schéma qui représente les entités, leurs attributs et les relations entre elles.
     - Définir les clés primaires et étrangères pour chaque table.
   - **Modèle Logique de Données (MLD)** :
     - Transformer le MCD en tables SQL prêtes à être implémentées.
   - Utilisation d'outils comme **MySQL Workbench** ou **DBDesigner** pour concevoir les modèles de base de données.

---

## 3. Développement _(3*96heures)_

### 3.1. Version Web _(3*48heures)_:
   - **Front-end :_(3*12heures)_** 
     - Développer l'interface utilisateur en **HTML/CSS** et ajouter des interactions avec **JavaScript**.
     - Valider le code HTML/CSS avec **w3validator** pour s'assurer qu'il respecte les normes.
   
   - **Back-end :_(3*36heures)_** 
     - Développer la logique serveur en **PHP**.
     - Implémenter la connexion à la base de données (CRUD avec **SQL**).

### 3.2. Version Java _(3*48heures)_:
   - **Interface utilisateur :_(3*24heures)_**
     - Créer les interfaces graphiques avec **Swing** ou **JavaFX**.
     - Gestion des événements utilisateurs (clics, entrées de données).
   
   - **Logique métier _(4heures)_:_(3*24heures)_**
     - Développer les fonctionnalités de base (gestion des utilisateurs, traitement de données) en **Java**.
     - Connexion à la base de données avec **JDBC** ou **Hibernate**.

---

## 4. Tests _(3*20heures)_

### 4.1. Tests unitaires _(3*8heures)_:
   - Utilisation de **JUnit** pour la version Java afin de vérifier le bon fonctionnement de chaque composant.
   - Pour la version Web, des tests unitaires peuvent être réalisés pour le code JavaScript si nécessaire.

### 4.2. Tests fonctionnels _(3*8heures)_:
   - Vérification de la conformité du produit avec les spécifications du cahier des charges.
   - Test des scénarios d'utilisation pour valider l'expérience utilisateur.

### 4.3. Outils d’analyse de code _(3*4heures)_:
   - Utilisation de **w3validator** pour vérifier la qualité du code HTML/CSS.
   - Pour le code Java, il faut déterminer un outil d'analyse comme **SonarQube** ou un autre.

---

## 5. Documentation _(3*8heures)_

### 5.1. Documentation technique _(3*4heures)_:
   - Utilisation de **Doxygen** pour générer la documentation de la version web.
   - Utilisation de **Javadoc** pour documenter le code Java.

### 5.2. Guide utilisateur _(3*4heures)_:
   - Rédaction d’un guide d'utilisation pour chaque version de l’application.

---

## 6. Déploiement _(3*8heures)_

### 6.1. Déploiement de la version web _(3*4heures)_:
   - Hébergement local avec **Wamp** ou sur un serveur distant.
   - Configuration des paramètres serveur, si nécessaire (PHP, SQL).

### 6.2. Déploiement de la version Java _(3*4heures)_:
   - Génération d’un exécutable **JAR** ou d’un installateur si nécessaire.

---

## 7. Maintenance et Support _(3*16heures)_

### 7.1. Suivi des bugs et retour utilisateurs _(3*12heures)_:
   - Collecte des retours après déploiement pour corriger les bugs et améliorer l’application.

### 7.2. Évolutivité _(3*4heures)_:
   - Planification d’ajouts de fonctionnalités ou d'améliorations futures.

---

# Résumé des Heures de Travail

Après avoir calculé les heures pour chaque phase de notre projet, nous arrivons à un total de **3*171 heures**. Cela inclut toutes les étapes, de la définition du projet à la maintenance et au support.

De plus, en ajoutant un vendredi après-midi de travail supplémentaire, nous sommes en bonne voie pour respecter nos délais et mener à bien notre projet dans les temps impartis.

# Projet Scolaire : Création d'Application (Version Web & Java)

## Tableau des Heures de Travail

| Phase                             | Tâches                           | Heures                 |
|-----------------------------------|----------------------------------|-----------------------|
| **1. Définition du projet**       |                                  | **3*3 heures**        |
|                                   | 1.1. Définir les objectifs       | 3*1 heures            |
|                                   | 1.2. Rédiger un cahier des charges| 3*2 heures            |
| **2. Conception et Planification** |                                  | **3*20 heures**       |
|                                   | 2.1. Architecture                | 3*4 heures            |
|                                   | 2.2. Design de l'interface utilisateur| 3*8 heures        |
|                                   | 2.3. Choix des technologies      | 3*4 heures            |
|                                   | 2.4. Planification des tâches     | 3*4 heures            |
|                                   | 2.5. Conception de la base de données| 3*4 heures       |
| **3. Développement**              |                                  | **3*96 heures**       |
|                                   | 3.1. Version Web                 | 3*48 heures           |
|                                   | 3.2. Version Java                | 3*48 heures           |
| **4. Tests**                      |                                  | **3*20 heures**       |
|                                   | 4.1. Tests unitaires             | 3*8 heures            |
|                                   | 4.2. Tests fonctionnels          | 3*8 heures            |
|                                   | 4.3. Outils d’analyse de code    | 3*4 heures            |
| **5. Documentation**              |                                  | **3*8 heures**        |
|                                   | 5.1. Documentation technique      | 3*4 heures            |
|                                   | 5.2. Guide utilisateur           | 3*4 heures            |
| **6. Déploiement**                |                                  | **3*8 heures**        |
|                                   | 6.1. Déploiement de la version web| 3*4 heures           |
|                                   | 6.2. Déploiement de la version Java| 3*4 heures          |
| **7. Maintenance et Support**     |                                  | **3*16 heures**       |
|                                   | 7.1. Suivi des bugs et retour utilisateurs| 3*12 heures   |
|                                   | 7.2. Évolutivité                 | 3*4 heures            |

---

## Total d'Heures

### **Total Final : 3*171 heures**
