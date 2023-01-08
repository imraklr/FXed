package com.example.webviewexamples;

import animations.doorAnimations;
import javafx.animation.FadeTransition;
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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import tabs.Tabs;
import watchers.dimens.Dimens;
import watchers.dimens.Drags;

import java.io.File;
import java.util.Collection;

public class Door extends Application {
    private Stage primaryStage;
    private static StackPane parent;
    private Rectangle m_strip; // menu_strip
    private Circle new_page, new_page_effect_circle,
            bookmarks, bookmarks_effect_circle,
            refresh, refresh_effect_circle;
    private Label new_page_label, bookmarks_label, refresh_label;
    private Rectangle cover_util;
    private Rectangle minimize, maximize, close;

    public Door() {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(500);
        primaryStage.setWidth(Screen.getPrimary().getBounds().getWidth());
        primaryStage.setHeight(Screen.getPrimary().getBounds().getHeight());

        this.primaryStage = primaryStage;
        Scene sc = new Scene(createContent());
        sc.setFill(Color.TRANSPARENT);
        File f = new File("src/main/resources/com/example/webviewexamples/stylesheets/doorDecoration.css");
        sc.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));

        primaryStage.setScene(sc);
        primaryStage.show();
        // Resizer goes here for primary stage


        // Attach dimension class to nodes
        try {
            attachDimensClass();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Attach Door Events
        attachDoorEvents();
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
        pane.getChildren().addAll(8, collected);
        return pane;
    }

    private StackPane designDoor(StackPane pane) {
        // Decorations:

        // menu_strip decorations
        {
            Rectangle2D screenBounds = Screen.getPrimary().getBounds();
            Rectangle menuStrip = new Rectangle(screenBounds.getMaxX(), screenBounds.getMaxY() / 30);
            m_strip = menuStrip;
            menuStrip.setId("menu_strip");
            pane.getChildren().add(0, menuStrip);
        }
        // Provide a transparent rectangle which covers on which if mouse drag if available expands to animate
        // recent, min, max, close buttons(rectangles)
        cover_util = new Rectangle();
        cover_util.setId("menu_strip_cover_util");
        // min_max_close buttons(clickable rectangles)
        {
            double recent_width = m_strip.getWidth() / 180 + 5;
            double recent_height = m_strip.getHeight() / 20;
            cover_util.setWidth(recent_width + recent_width / 2);
            cover_util.setHeight(recent_width * 2);
            pane.getChildren().add(1, cover_util);
            double recent_arc_height = 10 * recent_height / 2;
            double recent_arc_width = 10 * recent_height / 2;
            minimize = new Rectangle(recent_width, recent_height); // angle = -60
            minimize.setArcHeight(recent_arc_height);
            minimize.setArcWidth(recent_arc_width);
            minimize.setId("minBtn");
            maximize = new Rectangle(recent_width, recent_height);
            maximize.setArcHeight(recent_arc_height);
            maximize.setArcWidth(recent_arc_width);
            maximize.setId("maxBtn");
            close = new Rectangle(recent_width, recent_height); // angle = 60
            close.setArcHeight(recent_arc_height);
            close.setArcWidth(recent_arc_width);
            close.setId("closeBtn");

            pane.getChildren().add(2, minimize);
            pane.getChildren().add(3, maximize);
            pane.getChildren().add(4, close);
        }
        // place activity buttons
        {
            new_page = new Circle(m_strip.getHeight() / 2.5);
            new_page.setFill(Color.web("#FFFFFF"));
            new_page.setOpacity(0.3);
            new_page_effect_circle = new Circle(m_strip.getHeight() / 2.5);
            new_page_effect_circle.setFill(Color.web("#FFFFFF"));
            new_page_effect_circle.setOpacity(0.0);
            new_page_effect_circle.setId("menu_strip_effect_circle");
            new_page.setId("menu_strip_item_circles");
            new_page_label = new Label("+");
            new_page_label.setScaleX(new_page.getRadius() / 5.0);
            new_page_label.setScaleY(new_page.getRadius() / 5.0);
            pane.getChildren().add(5, new_page_effect_circle);
            pane.getChildren().add(6, new_page_label);
            pane.getChildren().add(7, new_page);

            refresh = new Circle(m_strip.getHeight() / 2.5);
            refresh.setFill(Color.web("#FFFFFF"));
            refresh.setOpacity(0.3);
            refresh_effect_circle = new Circle(m_strip.getHeight() / 2.5);
            refresh_effect_circle.setFill(Color.web("#FFFFFF"));
            refresh_effect_circle.setOpacity(0.0);
            refresh_effect_circle.setId("menu_strip_effect_circle");
            refresh.setId("menu_strip_item_circles");
            refresh_label = new Label("↻");
            refresh_label.setScaleX(refresh.getRadius() / 5.0);
            refresh_label.setScaleY(refresh.getRadius() / 6.0);
            pane.getChildren().add(8, refresh_effect_circle);
            pane.getChildren().add(9, refresh_label);
            pane.getChildren().add(10, refresh);

            bookmarks = new Circle(m_strip.getHeight() / 2.5);
            bookmarks.setFill(Color.web("#FFFFFF"));
            bookmarks.setOpacity(0.3);
            bookmarks_effect_circle = new Circle(m_strip.getHeight() / 2.5);
            bookmarks_effect_circle.setFill(Color.web("#FFFFFF"));
            bookmarks_effect_circle.setOpacity(0.0);
            bookmarks_effect_circle.setId("menu_strip_effect_circle");
            bookmarks.setId("menu_strip_item_circles");
            bookmarks_label = new Label("\uD83D\uDD16");
            bookmarks_label.setScaleX(bookmarks.getRadius() / 10.0);
            bookmarks_label.setScaleY(bookmarks.getRadius() / 10.0);
            pane.getChildren().add(11, bookmarks_effect_circle);
            pane.getChildren().add(12, bookmarks_label);
            pane.getChildren().add(13, bookmarks);

        }
        return pane;
    }

    private void attachDimensClass() {
        // Prepare Dimensional Frame
        Dimens frame = new Dimens();
        // Consider Rectangle 'minimize' as hinge node
        // Only hinge node can have last four parameter not equal to zero
        new Dimens(minimize, frame, 0, 0, 0, 0, -60, 0, 0, 0, m_strip.getHeight() / 2, m_strip.getWidth() - minimize.getWidth() * 2, 0, 0);
        new Dimens(maximize, frame, ((Math.sqrt(3) * maximize.getWidth()) / 4), 0, 0, ((1 * maximize.getWidth()) / 4), 0, 0, 0, 0, 0, 0, 0, 0);
        new Dimens(close, frame, 0, 0, 0, close.getWidth() / 2, 60, 0, 0, 0, 0, 0, 0, 0);
        new Dimens(cover_util, frame, -(m_strip.getHeight() - ((Math.sqrt(3) * maximize.getWidth()) / 1.4)), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        frame.arrange();

        // Do frame = new Dimens(); to start with another framing iff you don't require previous frame or if there are
        // animations/resize/drag available
        Dimens frame1 = new Dimens();
        new Dimens(new_page_effect_circle, frame1, 0, 0, 0, 0, 0, 0, 0, 0, m_strip.getHeight() / 20, m_strip.getWidth() / 2 - new_page.getLayoutBounds().getWidth() / 2, 0, 0);
        new Dimens(new_page, frame1, 0, 0, 0, 0, 0, 0, 0, 0, m_strip.getHeight() / 20, m_strip.getWidth() / 2 - new_page.getLayoutBounds().getWidth() / 2, 0, 0);
        new Dimens(new_page_label, frame1, 0, 0, 0, new_page.getRadius() / 1.5, 0, 0, 0, 0, 0, 0, 0, 0);

        new Dimens(refresh, frame1, 0, 0, 0, refresh.getLayoutBounds().getWidth() + m_strip.getWidth() / 200, 0, 0, 0, 0, 0, 0, 0, 0);
        new Dimens(refresh_effect_circle, frame1, 0, 0, 0, refresh.getLayoutBounds().getWidth() + m_strip.getWidth() / 200, 0, 0, 0, 0, 0, 0, 0, 0);
        new Dimens(refresh_label, frame1, 0, 0, 0, refresh.getLayoutBounds().getWidth() + m_strip.getWidth() / 200 + refresh_label.getWidth(), 0, 0, 0, 0, 0, 0, 0, 0);

        new Dimens(bookmarks, frame1, 0, 0, 0, bookmarks.getLayoutBounds().getWidth() + 5 * (m_strip.getWidth() / 200), 0, 0, 0, 0, 0, 0, 0, 0);
        new Dimens(bookmarks_effect_circle, frame1, 0, 0, 0, bookmarks.getLayoutBounds().getWidth() + 5 * (m_strip.getWidth() / 200), 0, 0, 0, 0, 0, 0, 0, 0);
        new Dimens(bookmarks_label, frame1, 0, 0, 0, bookmarks.getLayoutBounds().getWidth() + 5 * (m_strip.getWidth() / 200) + bookmarks_label.getWidth() / 2.5, 0, 0, 0, 0, 0, 0, 0, 0);

        frame1.arrange();
    }

    private void doDynamics() {
        new Drags(m_strip, primaryStage);

        new doorAnimations(m_strip, "");

        new doorAnimations(cover_util, minimize, maximize, close);
    }

    private void attachDoorEvents() {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(.125), new_page_effect_circle);
        new_page.setOnMouseEntered(e -> {
            fadeTransition.setFromValue(new_page_effect_circle.getOpacity());
            fadeTransition.setToValue(1.);
            fadeTransition.play();
        });
        new_page.setOnMouseClicked(e -> {
            // Perform some action and opacity restoration
            fadeTransition.setFromValue(new_page_effect_circle.getOpacity());
            fadeTransition.setToValue(.2);
            fadeTransition.play();
        });
        new_page.setOnMouseExited(e -> {
            // Perform opacity restoration
            fadeTransition.setFromValue(new_page_effect_circle.getOpacity());
            fadeTransition.setToValue(.2);
            fadeTransition.play();
        });
        FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(.125), bookmarks_effect_circle);
        bookmarks.setOnMouseEntered(e -> {
            fadeTransition1.setFromValue(bookmarks_effect_circle.getOpacity());
            fadeTransition1.setToValue(1.);
            fadeTransition1.play();
        });
        bookmarks.setOnMouseClicked(e -> {
            // Perform some action and opacity restoration
            fadeTransition1.setFromValue(bookmarks_effect_circle.getOpacity());
            fadeTransition1.setToValue(.2);
            fadeTransition1.play();
        });
        bookmarks.setOnMouseExited(e -> {
            // Perform opacity restoration
            fadeTransition1.setFromValue(bookmarks_effect_circle.getOpacity());
            fadeTransition1.setToValue(.2);
            fadeTransition1.play();
        });
        FadeTransition fadeTransition2 = new FadeTransition(Duration.seconds(.125), refresh_effect_circle);
        refresh.setOnMouseEntered(e -> {
            fadeTransition2.setFromValue(refresh_effect_circle.getOpacity());
            fadeTransition2.setToValue(1.);
            fadeTransition2.play();
        });
        refresh.setOnMouseClicked(e -> {
            // Perform some action and opacity restoration
            fadeTransition2.setFromValue(refresh_effect_circle.getOpacity());
            fadeTransition2.setToValue(.2);
            fadeTransition2.play();
        });
        refresh.setOnMouseExited(e -> {
            // Perform opacity restoration
            fadeTransition2.setFromValue(refresh_effect_circle.getOpacity());
            fadeTransition2.setToValue(.2);
            fadeTransition2.play();
        });
    }

    public StackPane getParent() {
        return parent;
    }
}