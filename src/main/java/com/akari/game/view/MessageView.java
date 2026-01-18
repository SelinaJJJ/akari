package com.akari.game.view;

import com.akari.game.controller.ClassicMvcController;
import com.akari.game.model.Model;
import com.akari.game.model.ModelObserver;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class MessageView implements FXComponent, ModelObserver {
  private final ClassicMvcController controller;
  private final Model model;
  private int seconds = 0;
  private final Timeline timeline;
  private final Label timer = new Label();
  private int index;

  public MessageView(ClassicMvcController controller, Model model) {
    if (controller == null || model == null) throw new IllegalArgumentException();
    this.controller = controller;
    this.model = model;
    this.index = model.getActivePuzzleIndex();
    this.timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateTimer()));
    this.timeline.setCycleCount(Animation.INDEFINITE);
    timeline.play();
  }

  @Override
  public Parent render() {
    VBox pane = new VBox();
    pane.getChildren().clear();
    pane.setAlignment(Pos.CENTER);

    pane.getChildren().add(timer);

    if (model.isSolved()) {
      int hours = seconds / 3600;
      int minutes = (seconds % 3600) / 60;
      int second = seconds % 60;
      timer.setText(String.format("%02d:%02d:%02d", hours, minutes, second));
      Label label =
          new Label(
              "YOU HAVE SOLVED THIS PUZZLE IN "
                  + String.format("%02d:%02d:%02d", hours, minutes, second)
                  + "!");

      seconds = 0;
      timeline.stop();
      pane.getChildren().add(label);
    } else if (model.getActivePuzzleIndex() != index) {
      seconds = 0;
      index = model.getActivePuzzleIndex();
      timeline.play();
    } else timeline.play();

    return pane;
  }

  @Override
  public void update(Model model) {}

  private void updateTimer() {
    // Update the label with the current time
    seconds++;
    int hours = seconds / 3600;
    int minutes = (seconds % 3600) / 60;
    int second = seconds % 60;
    timer.setText(String.format("%02d:%02d:%02d", hours, minutes, second));
  }
}
