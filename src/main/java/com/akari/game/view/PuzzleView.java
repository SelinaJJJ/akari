package com.akari.game.view;

import com.akari.game.controller.ClassicMvcController;
import com.akari.game.model.CellType;
import com.akari.game.model.Model;
import com.akari.game.model.ModelObserver;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class PuzzleView implements FXComponent, ModelObserver {
  private final ClassicMvcController controller;
  private final Model model;

  public PuzzleView(ClassicMvcController controller, Model model) {
    if (controller == null || model == null) throw new IllegalArgumentException();
    this.model = model;
    this.controller = controller;
  }

  @Override
  public Parent render() {
    VBox pane2 = new VBox();
    pane2.getChildren().clear();
    pane2.setAlignment(Pos.CENTER);

    if (model.getActivePuzzleIndex() == 5) {
      Label label = new Label("Im possible Puzzle :)");
      label.setStyle("-fx-text-fill: black; -fx-font-size: 20px");
      pane2.getChildren().add(label);
    } else {
      Label label =
          new Label(
              "Puzzle " + (model.getActivePuzzleIndex() + 1) + "/" + model.getPuzzleLibrarySize());
      label.setStyle("-fx-text-fill: black; -fx-font-size: 20px");
      pane2.getChildren().add(label);
    }

    GridPane pane = new GridPane();
    pane.getChildren().clear();
    pane.setAlignment(Pos.CENTER);

    for (int row = 0; row < model.getActivePuzzle().getHeight(); row++) {
      for (int col = 0; col < model.getActivePuzzle().getWidth(); col++) {
        // Creates the Light Bulb image to be placed
        Image image = new Image("light-bulb.png");
        ImageView view = new ImageView(image);
        view.setFitWidth(35);
        view.setFitHeight(35);

        // CORRIDOR
        if (model.getActivePuzzle().getCellType(row, col) == CellType.CORRIDOR) {
          final int r = row;
          final int c = col;

          // Creates a new button in the designated place
          Button corridor = new Button();
          corridor.setPrefWidth(60);
          corridor.setPrefHeight(60);

          corridor.setOnAction((ActionEvent event) -> controller.clickCell(r, c));

          if (model.isLamp(r, c)) {
            corridor.setGraphic(view);
            if (model.isLampIllegal(r, c)) {
              corridor.setStyle("-fx-background-color: red");
            }
          } else if (model.isLit(r, c)) {
            corridor.setStyle("-fx-background-color: #87CEFA");
          }

          pane.add(corridor, col, row);
        }

        // CLUE
        else if (model.getActivePuzzle().getCellType(row, col) == CellType.CLUE) {
          Button clue = new Button();
          clue.setPrefWidth(60);
          clue.setPrefHeight(60);
          Label label1 = new Label("" + model.getActivePuzzle().getClue(row, col));
          label1.setAlignment(Pos.CENTER);
          clue.setGraphic(label1);
          clue.setStyle("-fx-background-color: #B0C4DE;");
          if (model.isClueSatisfied(row, col)) clue.setStyle("-fx-background-color: #F0F8FF;");
          pane.add(clue, col, row);
        }

        // WALL
        else {
          Button wall = new Button();
          wall.setPrefWidth(60);
          wall.setPrefHeight(60);
          wall.setStyle("-fx-background-color: black");
          pane.add(wall, col, row);
        }
      }
    }

    pane.setPadding(new Insets(10));
    pane.setHgap(3);
    pane.setVgap(3);
    pane2.getChildren().add(pane);
    return pane2;
  }

  @Override
  public void update(Model model) {}
}
