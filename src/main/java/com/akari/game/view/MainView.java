package com.akari.game.view;

import com.akari.game.controller.ClassicMvcController;
import com.akari.game.model.Model;
import com.akari.game.model.ModelObserver;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Screen;

public class MainView implements FXComponent, ModelObserver {

  private final FXComponent puzzleView;
  private final FXComponent controlView;
  private final FXComponent messageView;
  private final Scene scene;

  public MainView(ClassicMvcController controller, Model model) {
    if (controller == null || model == null) throw new IllegalArgumentException();
    this.puzzleView = new PuzzleView(controller, model);
    this.controlView = new ControlView(controller, model);
    this.messageView = new MessageView(controller, model);
    // gets the size of the screen
    Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
    this.scene = new Scene(render(), screenBounds.getWidth(), screenBounds.getHeight());
    scene.getStylesheets().add("main.css");
    model.addObserver(this);
  }

  public Scene getScene() {
    return scene;
  }

  @Override
  public void update(Model model) {
    scene.setRoot(render());
  }

  @Override
  public Parent render() {
    BorderPane pane = new BorderPane();
    pane.setStyle("-fx-background-color: #778899;");

    pane.setTop(messageView.render());
    pane.setCenter(puzzleView.render());
    pane.setBottom(controlView.render());

    return pane;
  }
}
