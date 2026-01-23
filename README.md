# jTressette

**jTressette** is a Java implementation of the traditional Italian card game **Tressette**, specifically designed for the **2-player variant** (often referred to as "Spizzichino" or "Tressette a due").

The project features a graphical user interface (GUI) built with JavaFX and implements an Artificial Intelligence (AI) opponent utilizing **Counterfactual Regret Minimization (CFR)** along with various heuristic strategies to simulate competitive gameplay.

## 📋 Features

* **1 vs 1 Gameplay:** Play Tressette against an AI opponent.
* **JavaFX GUI:** Visual interface representing the table, player hand, opponent hand (hidden), and played cards.
* **Advanced AI:**
    * **CFR (Counterfactual Regret Minimization):** Used for decision making in imperfect-information scenarios.
    * **Heuristic Strategies:** Includes `OffenseStrategy`, `DefenceStrategy`, `DominantSuitStrategy`, and composite strategies like `OPStrategy` to handle different game phases.
* **Game Engine Core:** Built on a decoupled core architecture (`jabtbg`) capable of supporting different game types.
* **Data Analysis:** Includes tools for collecting game data and analyzing algorithm performance.

## 🛠 Tech Stack

* **Language:** Java 21.
* **Build Tool:** Gradle 8.8.
* **UI Framework:** JavaFX 20.
* **Testing:** JUnit 5 (Jupiter).
* **Dependencies:** Google Guava.

## 🚀 Getting Started

### Prerequisites

* **Java Development Kit (JDK) 21** or higher.
* **Git** to clone the repository.

### Installation

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/gitvanni/jtressette.git](https://github.com/gitvanni/jtressette.git)
    cd jtressette
    ```

2.  **Build the project:**
    Use the included Gradle wrapper to build the project and run tests.
    * **Linux/macOS:**
        ```bash
        ./gradlew build
        ```
    * **Windows:**
        ```cmd
        gradlew.bat build
        ```

### Running the Application

To launch the game interface:

* **Linux/macOS:**
    ```bash
    ./gradlew run
    ```
* **Windows:**
    ```cmd
    gradlew.bat run
    ```

*Note: The main entry point is configured as `it.unicam.cs.tressette.core.Start`.*

## 📂 Project Structure

The project follows a modular architecture:

* **`com.lostrucos.jabtbg`**: The generic game engine core.
    * `core/`: Interfaces for `Game`, `GameState`, `Action`, `Player`, and `InformationSet`.
    * `algorithms/`: Implementation of the CFR Algorithm.
    * `dataanalysis/`: Tools for simulating games and analyzing trees.
* **`it.unicam.cs.tressette`**: The specific implementation for Tressette.
    * `cards/`: Logic for `Deck`, `Hand`, `Card`, `Suit`, and `Rank`.
    * `core/`: Tressette-specific game logic (`TressetteGame`, `TressetteRules`).
    * `strategies/`: Heuristic logic used by the AI (e.g., `StrongStrategy`, `RespondStrategy`).

