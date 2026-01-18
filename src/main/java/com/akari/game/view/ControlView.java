package com.akari.game.view;

import com.akari.game.controller.ClassicMvcController;
import com.akari.game.model.Model;
import com.akari.game.model.ModelObserver;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ControlView implements FXComponent, ModelObserver {

  private final ClassicMvcController controller;
  private final Model model;
  private Button nextPuzzle;
  private Button prevPuzzle;
  private Button randPuzzle;
  private Button resetPuzzle;

  public ControlView(ClassicMvcController controller, Model model) {
    if (controller == null || model == controller) throw new IllegalArgumentException();
    this.controller = controller;
    this.model = model;
  }

  @Override
  public Parent render() {
    HBox layout = new HBox();
    layout.getChildren().clear();
    layout.setAlignment(Pos.CENTER);
    layout.setSpacing(10);

    // Previous puzzle button
    if (prevPuzzle == null) {
      prevPuzzle = new Button("<< Previous");
      prevPuzzle.getStyleClass().add("controlButton");
      prevPuzzle.setOnAction((ActionEvent event) -> controller.clickPrevPuzzle());
    }
    layout.getChildren().add(prevPuzzle);

    // Random puzzle button
    if (randPuzzle == null) {
      randPuzzle = new Button("Random");
      randPuzzle.getStyleClass().add("controlButton");
      randPuzzle.setOnAction((ActionEvent event) -> controller.clickRandPuzzle());
    }
    layout.getChildren().add(randPuzzle);

    // Next puzzle button
    if (nextPuzzle == null) {
      nextPuzzle = new Button("Next >>");
      nextPuzzle.getStyleClass().add("controlButton");
      nextPuzzle.setOnAction((ActionEvent event) -> controller.clickNextPuzzle());
    }
    layout.getChildren().add(nextPuzzle);

    VBox box2 = new VBox();
    box2.getChildren().clear();
    box2.setAlignment(Pos.CENTER);
    box2.setSpacing(10);

    // Reset puzzle button
    if (resetPuzzle == null) {
      resetPuzzle = new Button("Reset");
      resetPuzzle.getStyleClass().add("controlButton");
      resetPuzzle.setOnAction((ActionEvent event) -> controller.clickResetPuzzle());
    }
    box2.getChildren().add(layout);
    box2.getChildren().add(resetPuzzle);

    return box2;
  }

  @Override
  public void update(Model model) {}
}
