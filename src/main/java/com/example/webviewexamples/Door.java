package com.example.webviewexamples;

import animations.doorAnimations;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tabs.Tabs;
import watchers.dimens.Dimens;
import watchers.dimens.Drags;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;

public class Door extends Application {
    private Stage primaryStage;
    private static StackPane parent;
    private Rectangle m_strip; // menu_strip
    private final LinkedList<Node> menuItems;

    {
        menuItems = new LinkedList<>();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        primaryStage.setWidth(Screen.getPrimary().getBounds().getWidth());
        primaryStage.setHeight(Screen.getPrimary().getBounds().getHeight());

        this.primaryStage = primaryStage;
        Scene sc = new Scene(createContent());
        sc.setFill(Color.TRANSPARENT);
        File f = new File("src/main/resources/com/example/webviewexamples/stylesheets/doorDecoration.css");
        sc.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));

        primaryStage.setScene(sc);
        primaryStage.show();

        // Attach dimension class to nodes
        attachDimensClass();
        // Attach dynamics
        doDynamics();
    }

    private Parent createContent() {
        // Assume parent node to be a StackPane
        StackPane pane = new StackPane();
        pane.setAlignment(Pos.TOP_LEFT);
        // Get the designed Door
        parent = designDoor(pane);
        // Get all nodes for scene
        Collection<Node> collected = Tabs.get(1);
        // TODO: Improve ERROR dialog
        if (collected == null) {
            GridPane gridPane = new GridPane();
            Label label = new Label();
            label.setText("ERROR: WINDOW CREATION FAILED");
            Button exit = new Button("EXIT");
            exit.setOnMouseClicked(e -> {
                // shutdown all threads before leaving and save any unsaved work
                primaryStage.close();
            });
            gridPane.setStyle("-fx-alignment: CENTER;" +
                    "");
            gridPane.add(label, 0, 0);
            gridPane.add(exit, 4, 11);

            return gridPane;
        }
        pane.getChildren().addAll(collected);
        return pane;
    }

    private StackPane designDoor(StackPane pane) {
        // Decorations:

        // menu_strip decorations
        {
            Rectangle2D screenBounds = Screen.getPrimary().getBounds();
            Rectangle menuStrip = new Rectangle(screenBounds.getMaxX(), screenBounds.getMaxY() / 25);
            m_strip = menuStrip;
            menuStrip.setId("menu_strip");
            pane.getChildren().add(0, menuStrip);
        }
        // min_max_close buttons
        {
            Button minBtn = new Button("-");
            minBtn.setOnMouseClicked(e -> primaryStage.setIconified(true));
            minBtn.setId("minBtn");
            menuItems.add(minBtn);
            Button maxBtn = new Button("^");
            maxBtn.setOnMouseClicked(e -> primaryStage.setMaximized(true));
            maxBtn.setId("maxBtn");
            menuItems.add(maxBtn);
            Button closeBtn = new Button("x");
            closeBtn.setId("closeBtn");
            closeBtn.setOnMouseClicked(e -> {
                // shutdown all threads before leaving and save any unsaved work
                primaryStage.close();
            });
            menuItems.add(closeBtn);

            closeBtn.setTranslateX(m_strip.getWidth() - 500);

            pane.getChildren().addAll(menuItems);
        }
        return pane;
    }

    private void attachDimensClass() {
        // Prepare Dimensional Frame
        Dimens frame = new Dimens();
    }

    private void doDynamics() {
        new Drags(m_strip, primaryStage);
        new doorAnimations(m_strip, "");
    }

    public StackPane getParent() {
        return parent;
    }
}