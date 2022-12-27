package com.example.webviewexamples;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tabs.Tabs;

import java.util.Collection;

public class Door extends Application {
    private Stage primaryStage;
    private static Pane parent;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        Scene sc = new Scene(createContent());

        primaryStage.setScene(sc);
        primaryStage.show();
    }

    private Parent createContent() {
        // Assume parent node to be a Pane
        Pane pane = new Pane();
        parent = pane;
        // Get all nodes for scene
        Collection<Node> collected = Tabs.get(1);
        if (collected == null) {
            GridPane gridPane = new GridPane();
            Label label = new Label();
            label.setText("ERROR: WINDOW CREATION FAILED");
            Button exit = new Button("EXIT");
            exit.setOnMouseClicked(e -> {
                // shutdown all threads before leaving and save any unsaved work
                primaryStage.close();
            });
            gridPane.add(label, 0, 0);
            gridPane.add(exit, 4, 11);

            return gridPane;
        }
        pane.getChildren().addAll(collected);
        return pane;
    }

    public Pane getParent() {
        return parent;
    }
}