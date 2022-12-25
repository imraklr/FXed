package com.example.webviewexamples;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Collection;

public class Door extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Scene sc = new Scene(createContent());

        primaryStage.setScene(sc);
        primaryStage.show();
    }

    private Parent createContent() {
        // Assume parent node to be a Pane
        Pane p = new Pane();
        // Get all nodes for scene
        Collection<? extends Node> collected = Windows.get(0, 1);
        if (collected == null) {
            Label l = new Label();
            l.setText("Window Creation Failed.");
            HBox hBox = new HBox(l);

            p.getChildren().add(hBox);
            return p;
        }
        p.getChildren().addAll(collected);
        return p;
    }
}