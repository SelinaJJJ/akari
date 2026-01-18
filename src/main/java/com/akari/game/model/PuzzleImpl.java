package com.akari.game.model;

public class PuzzleImpl implements Puzzle {
  private final int[][] board;
  private final int height;
  private final int width;
  private int boardValue;

  public PuzzleImpl(int[][] board) {
    if (board == null) throw new IllegalArgumentException();
    this.board = board;
    this.height = this.board.length;
    this.width = this.board[0].length;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public CellType getCellType(int r, int c) {
    if (r > getHeight() || c > getWidth()) throw new IndexOutOfBoundsException();
    this.boardValue = board[r][c];
    if (boardValue >= 0 && boardValue <= 4) return CellType.CLUE;
    else if (boardValue == 5) return CellType.WALL;
    else return CellType.CORRIDOR;
  }

  @Override
  public int getClue(int r, int c) {
    if (r < 0 || r > height || c < 0 || c > width) throw new IndexOutOfBoundsException();
    if (getCellType(r, c) != CellType.CLUE) throw new IllegalArgumentException();
    if (this.boardValue == 0) return 0;
    else if (this.boardValue == 1) return 1;
    else if (this.boardValue == 2) return 2;
    else if (this.boardValue == 3) return 3;
    else return 4;
  }
}
