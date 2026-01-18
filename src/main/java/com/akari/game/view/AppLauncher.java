package com.akari.game.view;

import com.akari.game.SamplePuzzles;
import com.akari.game.controller.ClassicMvcController;
import com.akari.game.controller.ControllerImpl;
import com.akari.game.model.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppLauncher extends Application {
  @Override
  public void start(Stage stage) {
    PuzzleLibrary library = new PuzzleLibraryImpl();
    Puzzle puzzle1 = new PuzzleImpl(SamplePuzzles.PUZZLE_01);
    Puzzle puzzle2 = new PuzzleImpl(SamplePuzzles.PUZZLE_02);
    Puzzle puzzle3 = new PuzzleImpl(SamplePuzzles.PUZZLE_03);
    Puzzle puzzle4 = new PuzzleImpl(SamplePuzzles.PUZZLE_04);
    Puzzle puzzle5 = new PuzzleImpl(SamplePuzzles.PUZZLE_05);
    Puzzle puzzle6 = new PuzzleImpl(SamplePuzzles.PUZZLE_06);
    Puzzle puzzle7 = new PuzzleImpl(SamplePuzzles.PUZZLE_07);
    library.addPuzzle(puzzle1);
    library.addPuzzle(puzzle2);
    library.addPuzzle(puzzle3);
    library.addPuzzle(puzzle4);
    library.addPuzzle(puzzle5);
    library.addPuzzle(puzzle6);
    library.addPuzzle(puzzle7);
    Model model = new ModelImpl(library);
    ClassicMvcController controller = new ControllerImpl(model);
    MainView view = new MainView(controller, model);

    stage.setScene(view.getScene());
    stage.setTitle("Akari - Light Up Puzzle");
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}
