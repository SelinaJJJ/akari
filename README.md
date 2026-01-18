# Akari - Light Up Puzzle Game

A JavaFX implementation of the classic **Akari** (Light Up) logic puzzle game.

![Akari Game Screenshot](docs/screenshot.png)

## About the Game

Akari is a logic puzzle where players place lamps on a grid to illuminate all corridor cells while following specific rules:

- **Lamps** illuminate cells in straight lines (up, down, left, right) until blocked by a wall or clue
- **Clue cells** display a number (0-4) indicating how many lamps must be placed adjacent to them
- **Wall cells** block light and cannot contain lamps
- **No two lamps** can illuminate each other (be in the same row/column without a wall between them)

The puzzle is solved when all corridors are lit, all clues are satisfied, and no lamps are illegally placed.

## Features

- 7 built-in puzzles of varying difficulty and sizes
- Visual feedback for lit corridors, satisfied clues, and illegal lamp placements
- Timer to track your solving time
- Navigation controls: Previous, Next, Random puzzle selection
- Reset button to clear the current puzzle
- Responsive UI that adapts to screen size

## Screenshots

| Game Board | Solved Puzzle |
|------------|---------------|
| ![Game](docs/game.png) | ![Solved](docs/solved.png) |

## Architecture

This project follows the **Model-View-Controller (MVC)** design pattern:

- **Model**: Manages puzzle state, lamp positions, and game logic
- **View**: Renders the UI using JavaFX components
- **Controller**: Handles user interactions and updates the model

The **Observer pattern** is used to keep the view synchronized with model changes.

## Requirements

- Java 9 or higher
- Maven 3.6+
- JavaFX 17

## Building and Running

### Using Maven (Recommended)

```bash
# Compile the project
mvn compile

# Run the application
mvn javafx:run

# Run tests
mvn test

# Package as executable JAR
mvn package
```

### Using IDE

1. Import as a Maven project
2. Run `com.akari.game.Main` class

## How to Play

1. **Click** on a corridor cell to place or remove a lamp
2. Use **Next/Previous** buttons to navigate between puzzles
3. Use **Random** to jump to a random puzzle
4. Use **Reset** to clear all lamps from the current puzzle
5. Watch the timer and try to solve puzzles quickly!

### Visual Guide

| Element | Appearance |
|---------|------------|
| Corridor (unlit) | White/Light gray |
| Corridor (lit) | Light blue |
| Lamp | Light bulb icon |
| Illegal lamp | Red background |
| Wall | Black |
| Clue (unsatisfied) | Steel blue with number |
| Clue (satisfied) | Light blue with number |

## License

MIT License - feel free to use and modify this project.

## Acknowledgments

- Puzzle concept: [Nikoli](https://www.nikoli.co.jp/en/)
- Try more puzzles: [puzzle-light-up.com](https://www.puzzle-light-up.com/)
