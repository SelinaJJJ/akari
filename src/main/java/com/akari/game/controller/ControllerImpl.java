package com.akari.game.controller;

import com.akari.game.model.CellType;
import com.akari.game.model.Model;
import com.akari.game.model.Puzzle;

import java.util.Random;

public class ControllerImpl implements ClassicMvcController {
  private final Model model;

  public ControllerImpl(Model model) {
    if (model == null) throw new IllegalArgumentException();
    this.model = model;
  }

  @Override
  public void clickNextPuzzle() {
    int newIndex = model.getActivePuzzleIndex() + 1;
    if (newIndex >= model.getPuzzleLibrarySize()) {
      newIndex = 0;
    }
    model.setActivePuzzleIndex(newIndex);
  }

  @Override
  public void clickPrevPuzzle() {
    int newIndex = model.getActivePuzzleIndex() - 1;
    if (newIndex < 0) {
      newIndex = model.getPuzzleLibrarySize() - 1;
    }
    model.setActivePuzzleIndex(newIndex);
  }

  @Override
  public void clickRandPuzzle() {
    Random rand = new Random();
    int newIndex = rand.nextInt(model.getPuzzleLibrarySize());
    while (newIndex == model.getActivePuzzleIndex()) {
      newIndex = rand.nextInt(model.getPuzzleLibrarySize());
    }
    model.setActivePuzzleIndex(newIndex);
  }

  @Override
  public void clickResetPuzzle() {
    model.resetPuzzle();
  }

  @Override
  public void clickCell(int r, int c) {
    Puzzle activePuzzle = model.getActivePuzzle();
    if (activePuzzle.getCellType(r, c) == CellType.CORRIDOR) {
      if (model.isLamp(r, c)) {
        model.removeLamp(r, c);
      } else {
        model.addLamp(r, c);
      }
    }
  }
}
