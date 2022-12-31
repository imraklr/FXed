package com.example.webviewexamples;

import animations.doorAnimations;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
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

public class Door extends Application {
    private Stage primaryStage;
    private static StackPane parent;
    private Rectangle m_strip; // menu_strip
    private Group recent;
    double recent_width;
    double recent_height;
    private Rectangle minimize, maximize, close;

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
        try {
            attachDimensClass();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            Rectangle menuStrip = new Rectangle(screenBounds.getMaxX(), screenBounds.getMaxY() / 26);
            m_strip = menuStrip;
            menuStrip.setId("menu_strip");
            pane.getChildren().add(0, menuStrip);
        }
        // min_max_close buttons(clickable rectangles)
        {
            recent_width = m_strip.getWidth() / 180 + 5;
            recent_height = m_strip.getHeight() / 20;
            double recent_arc_height = 10 * recent_height / 2;
            double recent_arc_width = 10 * recent_height / 2;
            minimize = new Rectangle(recent_width, recent_height); // angle = -60
            minimize.setArcHeight(recent_arc_height);
            minimize.setArcWidth(recent_arc_width);
            maximize = new Rectangle(recent_width, recent_height);
            maximize.setArcHeight(recent_arc_height);
            maximize.setArcWidth(recent_arc_width);
            close = new Rectangle(recent_width, recent_height); // angle = 60
            close.setArcHeight(recent_arc_height);
            close.setArcWidth(recent_arc_width);
            // Form triangle with this

            Rectangle recent_1 = new Rectangle(recent_width, recent_height);
            recent_1.setRotate(60);
            recent_1.setArcHeight(recent_arc_height);
            recent_1.setArcWidth(recent_arc_width);
            Rectangle recent_2 = new Rectangle(recent_width, recent_height);
            recent_2.setRotate(-60);
            recent_2.setArcHeight(recent_arc_height);
            recent_2.setArcWidth(recent_arc_width);
            Rectangle recent_3 = new Rectangle(recent_width, recent_height);
            recent_3.setTranslateY(-((Math.sqrt(3) / 4) * recent_width));
            recent_3.setArcHeight(recent_arc_height);
            recent_3.setArcWidth(recent_arc_width);
            recent = new Group(recent_1, recent_2, recent_3);
            recent.getChildren().get(0).setTranslateX(-(recent_width / 4));
            recent.getChildren().get(1).setTranslateX(recent_width / 4);

            pane.getChildren().addAll(recent, minimize, maximize, close);
        }
        return pane;
    }

    private void attachDimensClass() {
        // Prepare Dimensional Frame
        Dimens frame = new Dimens();
        // Consider Group 'recent' as hinge node
        new Dimens(recent, frame, 0, 0, 0, 0, 0, 0, 0, 0, m_strip.getHeight() / 2, m_strip.getWidth() - 160, 0, 0);
        new Dimens(minimize, frame, -(Math.sqrt(3) / 4) * recent_width, 0, 0, -recent_width / 4, -60, 0, 0, 0, 0, 0, 0, 0);
        new Dimens(maximize, frame, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        new Dimens(close, frame, -(Math.sqrt(3) / 4) * recent_width, 0, 0, recent_width / 4, 60, 0, 0, 0, 0, 0, 0, 0);
        frame.arrange();
        System.out.println(frame);

        // Do frame = new Dimens(); to start with another framing iff you don't require previous frame or if there are
        // animations/resize/drag available
    }

    private void doDynamics() {
        new Drags(m_strip, primaryStage);
        new doorAnimations(m_strip, "");
    }

    public StackPane getParent() {
        return parent;
    }
}