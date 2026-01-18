package com.akari.game.model;

import java.awt.Point;
import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;

public class ModelImpl implements Model {
  private final Set<Point> lamps;
  private final PuzzleLibrary library;
  private Puzzle activePuzzle;
  private int activePuzzleIndex;
  private final List<ModelObserver> observers;

  public ModelImpl(PuzzleLibrary library) {
    if (library == null) {
      throw new IllegalArgumentException();
    }
    this.library = library;
    this.activePuzzleIndex = 0;
    this.activePuzzle = library.getPuzzle(activePuzzleIndex);
    this.lamps = new HashSet<>();
    this.observers = new ArrayList<>();
  }

  @Override
  public void addLamp(int r, int c) {
    if (activePuzzle.getWidth() < c || r < 0 || activePuzzle.getHeight() < r || c < 0)
      throw new IndexOutOfBoundsException("Lamp is outside of current bound");
    if (activePuzzle.getCellType(r, c) != CellType.CORRIDOR)
      throw new IllegalArgumentException("Cell is not a corridor");
    this.lamps.add(new Point(r, c));
    notifyObservers();
  }

  @Override
  public void removeLamp(int r, int c) {
    if (activePuzzle.getWidth() < c || r < 0 || activePuzzle.getHeight() < r || c < 0)
      throw new IndexOutOfBoundsException("Lamp is outside of current bound");
    if (activePuzzle.getCellType(r, c) != CellType.CORRIDOR)
      throw new IllegalArgumentException("Cell is not a corridor");
    this.lamps.remove(new Point(r, c));
    notifyObservers();
  }

  @Override
  public boolean isLit(int r, int c) {
    if (activePuzzle.getWidth() < c || r < 0 || activePuzzle.getHeight() < r || c < 0)
      throw new IndexOutOfBoundsException("Lamp is outside of current bound");
    if (activePuzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("Cell is not a corridor");
    }
    for (Point lamp : lamps) {
      if (lamp.x == r || lamp.y == c) {
        if (isLineOfSightClear(lamp, r, c)) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public boolean isLamp(int r, int c) {
    if (activePuzzle.getWidth() < c || r < 0 || activePuzzle.getHeight() < r || c < 0)
      throw new IndexOutOfBoundsException("Lamp is outside of current bound");
    if (activePuzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("Cell is not a corridor");
    }
    return lamps.contains(new Point(r, c));
  }

  @Override
  public boolean isLampIllegal(int r, int c) {
    if (activePuzzle.getWidth() < c || r < 0 || activePuzzle.getHeight() < r || c < 0)
      throw new IndexOutOfBoundsException("Lamp is outside of current bound");
    if (activePuzzle.getCellType(r, c) != CellType.CORRIDOR)
      throw new IllegalArgumentException("Cell is not a corridor");
    Point lamp = new Point(r, c);
    for (Point otherLamp : lamps) {
      if (!lamp.equals(otherLamp) && (lamp.x == otherLamp.x || lamp.y == otherLamp.y)) {
        if (isLineOfSightClear(lamp, otherLamp)) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public Puzzle getActivePuzzle() {
    return activePuzzle;
  }

  @Override
  public int getActivePuzzleIndex() {
    return activePuzzleIndex;
  }

  @Override
  public void setActivePuzzleIndex(int index) {
    if (index < 0 || index >= library.size()) {
      throw new IndexOutOfBoundsException("Invalid puzzle index");
    }
    activePuzzleIndex = index;
    activePuzzle = library.getPuzzle(index);
    resetPuzzle();
  }

  @Override
  public int getPuzzleLibrarySize() {
    return library.size();
  }

  @Override
  public void resetPuzzle() {
    lamps.clear();
    notifyObservers();
  }

  @Override
  public boolean isSolved() {
    for (int r = 0; r < activePuzzle.getHeight(); r++) {
      for (int c = 0; c < activePuzzle.getWidth(); c++) {
        if (activePuzzle.getCellType(r, c) == CellType.CORRIDOR && !isLit(r, c)) return false;
        if (activePuzzle.getCellType(r, c) == CellType.CLUE) {
          int clue = activePuzzle.getClue(r, c);
          int adjacentLamps = countAdjacentLamps(r, c);
          if (clue >= 0 && clue != adjacentLamps) return false;
          if (clue == -1 && adjacentLamps > 0) return false;
        }
      }
    }
    for (Point lamp : lamps) {
      if (isLampIllegal(lamp.x, lamp.y)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    if (activePuzzle.getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException("Cell is not a clue");
    }
    int numLamps = 0;
    for (Point neighbor : getNeighbor(r, c)) {
      if (lamps.contains(new Point(neighbor.x, neighbor.y))) numLamps++;
    }
    return numLamps == activePuzzle.getClue(r, c);
  }

  @Override
  public void addObserver(ModelObserver observer) {
    observers.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    observers.remove(observer);
  }

  private boolean isLineOfSightClear(Point a, Point b) {
    if (a.x == b.x) {
      int start = Math.min(a.y, b.y);
      int end = Math.max(a.y, b.y);
      for (int i = start + 1; i < end; i++) {
        if (activePuzzle.getCellType(a.x, i) != CellType.CORRIDOR) {
          return false;
        }
      }
      return true;
    } else if (a.y == b.y) {
      int start = Math.min(a.x, b.x);
      int end = Math.max(a.x, b.x);
      for (int i = start + 1; i < end; i++) {
        if (activePuzzle.getCellType(i, a.y) != CellType.CORRIDOR) {
          return false;
        }
      }
      return true;
    } else {
      throw new IllegalArgumentException("Lamps are not aligned horizontally or vertically");
    }
  }

  private boolean isLineOfSightClear(Point lamp, int r, int c) {
    Point other = new Point(r, c);
    return isLineOfSightClear(lamp, other) && isLineOfSightClear(other, lamp);
  }

  private int countAdjacentLamps(int r, int c) {
    int count = 0;
    int maxRow = activePuzzle.getHeight() - 1;
    int maxCol = activePuzzle.getWidth() - 1;

    // Check cells to the top, bottom, left, and right
    // If cell is present then increment the count by one
    if (r > 0 && lamps.contains(new Point(r - 1, c))) { // Top
      count++;
    }
    if (r < maxRow && lamps.contains(new Point(r + 1, c))) { // Bottom
      count++;
    }
    if (c > 0 && lamps.contains(new Point(r, c - 1))) { // Left
      count++;
    }
    if (c < maxCol && lamps.contains(new Point(r, c + 1))) { // Right
      count++;
    }

    return count;
  }

  public Set<Point> getNeighbor(int r, int c) {
    Set<Point> neighbors = new HashSet<>();
    // Getting the neighbor of the bottom
    if (r > 0) {
      neighbors.add(new Point(r - 1, c));
    }
    // Getting the neighbor of the top
    if (r < activePuzzle.getHeight() - 1) {
      neighbors.add(new Point(r + 1, c));
    }
    // Getting the neighbor of the left
    if (c > 0) {
      neighbors.add(new Point(r, c - 1));
    }
    // Getting the neighbor of the right
    if (c < activePuzzle.getWidth() - 1) {
      neighbors.add(new Point(r, c + 1));
    }
    return neighbors;
  }

  public void notifyObservers() {
    for (ModelObserver observer : observers) observer.update(this);
  }
}
