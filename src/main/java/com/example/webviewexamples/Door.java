package com.example.webviewexamples;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.util.PriorityQueue;

public class Door extends Application {
    private Stage primaryStage;
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
        TilePane titlePane = new TilePane();
        // Get all nodes for scene
        PriorityQueue<Node> collected = Windows.get(0, 1);
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
        titlePane.getChildren().addAll(collected);
        return titlePane;
    }
}