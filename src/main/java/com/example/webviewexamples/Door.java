package com.example.webviewexamples;

import TabsManager.Tabs;
import animations.DoorAnimations;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import watchers.dimens.Dimens;
import watchers.dimens.Drags;

import java.io.File;

public class Door extends Application {
    private Stage primaryStage;
    private static StackPane parent;
    private Rectangle m_strip; // menu_strip
    int currTab = 0;
    private Label new_page_label, bookmarks_label, refresh_label, close_page_label, forward_label, backward_label;
    private Rectangle cover_util;
    private Rectangle minimize, maximize, close;
    private Circle new_page, new_page_effect_circle, bookmarks, bookmarks_effect_circle, refresh, refresh_effect_circle,
            backward, backward_effect_circle, forward, forward_effect_circle, close_page, close_page_effect_circle;

    public Door() {
    }

    public static void main(String[] args) {
        launch(args);
    }
    private Tabs tabs;

    private Parent createContent() {
        // Assume parent node to be a StackPane
        StackPane pane = new StackPane();
        pane.setAlignment(Pos.TOP_LEFT);
        // Get the designed Door
        parent = designDoor(pane);
        return pane;
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
        onSceneAttached();
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

    private StackPane designDoor(StackPane pane) {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();

        // Decorations:
        // menu_strip decorations
        {
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
            refresh_label.setScaleX(refresh.getRadius() / 6.0);
            refresh_label.setScaleY(refresh.getRadius() / 7.0);
            pane.getChildren().add(8, refresh_effect_circle);
            pane.getChildren().add(9, refresh_label);
            pane.getChildren().add(10, refresh);

            close_page = new Circle(m_strip.getHeight() / 2.5);
            close_page.setFill(Color.web("#FFFFFF"));
            close_page.setOpacity(0.3);
            close_page_effect_circle = new Circle(m_strip.getHeight() / 2.5);
            close_page_effect_circle.setFill(Color.web("#FFFFFF"));
            close_page_effect_circle.setOpacity(0.0);
            close_page_effect_circle.setId("menu_strip_effect_circle");
            close_page.setId("menu_strip_item_circles");
            close_page_label = new Label("✘");
            close_page_label.setScaleX(close_page.getRadius() / 7.0);
            close_page_label.setScaleY(close_page.getRadius() / 8.0);
            pane.getChildren().add(11, close_page_effect_circle);
            pane.getChildren().add(12, close_page_label);
            pane.getChildren().add(13, close_page);

            forward = new Circle(m_strip.getHeight() / 2.5);
            forward.setFill(Color.web("#FFFFFF"));
            forward.setOpacity(0.3);
            forward_effect_circle = new Circle(m_strip.getHeight() / 2.5);
            forward_effect_circle.setFill(Color.web("#FFFFFF"));
            forward_effect_circle.setOpacity(0.0);
            forward_effect_circle.setId("menu_strip_effect_circle");
            forward.setId("menu_strip_item_circles");
            forward_label = new Label("❱");
            forward_label.setScaleX(forward.getRadius() / 7.0);
            forward_label.setScaleY(forward.getRadius() / 8.0);
            pane.getChildren().add(13, forward_effect_circle);
            pane.getChildren().add(14, forward_label);
            pane.getChildren().add(15, forward);

            backward = new Circle(m_strip.getHeight() / 2.5);
            backward.setFill(Color.web("#FFFFFF"));
            backward.setOpacity(0.3);
            backward_effect_circle = new Circle(m_strip.getHeight() / 2.5);
            backward_effect_circle.setFill(Color.web("#FFFFFF"));
            backward_effect_circle.setOpacity(0.0);
            backward_effect_circle.setId("menu_strip_effect_circle");
            backward.setId("menu_strip_item_circles");
            backward_label = new Label("❰");
            backward_label.setScaleX(backward.getRadius() / 7.0);
            backward_label.setScaleY(backward.getRadius() / 8.0);
            pane.getChildren().add(16, backward_effect_circle);
            pane.getChildren().add(17, backward_label);
            pane.getChildren().add(18, backward);

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
            pane.getChildren().add(19, bookmarks_effect_circle);
            pane.getChildren().add(20, bookmarks_label);
            pane.getChildren().add(21, bookmarks);
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
        double averageWidth = (((bookmarks.getRadius() + new_page.getRadius() + refresh.getRadius() + close_page.getRadius() +
                forward.getRadius() + backward.getRadius()) * 2) + 5 * (m_strip.getWidth() / 200)) / 2;
        Dimens frame1 = new Dimens();
        new Dimens(new_page_effect_circle, frame1, 0, 0, 0, 0, 0, 0, 0, 0, m_strip.getHeight() / 20, m_strip.getWidth() / 2 - averageWidth, 0, 0);
        new Dimens(new_page, frame1, 0, 0, 0, 0, 0, 0, 0, 0, m_strip.getHeight() / 20, m_strip.getWidth() / 2 - new_page.getLayoutBounds().getWidth() / 2, 0, 0);
        new Dimens(new_page_label, frame1, -new_page.getRadius() / 20, 0, 0, new_page.getRadius() / 1.6, 0, 0, 0, 0, 0, 0, 0, 0);

        new Dimens(refresh, frame1, 0, 0, 0, refresh.getLayoutBounds().getWidth() + m_strip.getWidth() / 200, 0, 0, 0, 0, 0, 0, 0, 0);
        new Dimens(refresh_effect_circle, frame1, 0, 0, 0, refresh.getLayoutBounds().getWidth() + m_strip.getWidth() / 200, 0, 0, 0, 0, 0, 0, 0, 0);
        new Dimens(refresh_label, frame1, 0, 0, 0, refresh.getLayoutBounds().getWidth() + m_strip.getWidth() / 200 + refresh_label.getWidth() / 0.8, 0, 0, 0, 0, 0, 0, 0, 0);

        new Dimens(bookmarks, frame1, 0, 0, 0, bookmarks.getLayoutBounds().getWidth() + 5 * (m_strip.getWidth() / 200), 0, 0, 0, 0, 0, 0, 0, 0);
        new Dimens(bookmarks_effect_circle, frame1, 0, 0, 0, bookmarks.getLayoutBounds().getWidth() + 5 * (m_strip.getWidth() / 200), 0, 0, 0, 0, 0, 0, 0, 0);
        new Dimens(bookmarks_label, frame1, bookmarks.getRadius() / 20, 0, 0, bookmarks.getLayoutBounds().getWidth() + 5 * (m_strip.getWidth() / 200) + bookmarks_label.getWidth() / 2.5, 0, 0, 0, 0, 0, 0, 0, 0);

        new Dimens(close_page, frame1, 0, 0, 0, close_page.getLayoutBounds().getWidth() + 9 * (m_strip.getWidth() / 200), 0, 0, 0, 0, 0, 0, 0, 0);
        new Dimens(close_page_effect_circle, frame1, 0, 0, 0, close_page.getLayoutBounds().getWidth() + 9 * (m_strip.getWidth() / 200), 0, 0, 0, 0, 0, 0, 0, 0);
        new Dimens(close_page_label, frame1, close_page.getRadius() / 20, 0, 0, close_page.getLayoutBounds().getWidth() + 9.5 * (m_strip.getWidth() / 200), 0, 0, 0, 0, 0, 0, 0, 0);

        new Dimens(forward, frame1, 0, 0, 0, forward.getLayoutBounds().getWidth() + 13 * (m_strip.getWidth() / 200), 0, 0, 0, 0, 0, 0, 0, 0);
        new Dimens(forward_effect_circle, frame1, 0, 0, 0, forward.getLayoutBounds().getWidth() + 13 * (m_strip.getWidth() / 200), 0, 0, 0, 0, 0, 0, 0, 0);
        new Dimens(forward_label, frame1, forward.getRadius() / 20, 0, 0, forward.getLayoutBounds().getWidth() + 14.25 * (m_strip.getWidth() / 200), 0, 0, 0, 0, 0, 0, 0, 0);

        new Dimens(backward, frame1, 0, 0, 0, backward.getLayoutBounds().getWidth() + 17 * (m_strip.getWidth() / 200), 0, 0, 0, 0, 0, 0, 0, 0);
        new Dimens(backward_effect_circle, frame1, 0, 0, 0, backward.getLayoutBounds().getWidth() + 17 * (m_strip.getWidth() / 200), 0, 0, 0, 0, 0, 0, 0, 0);
        new Dimens(backward_label, frame1, backward.getRadius() / 20, 0, 0, backward.getLayoutBounds().getWidth() + 18 * (m_strip.getWidth() / 200), 0, 0, 0, 0, 0, 0, 0, 0);

        frame1.arrange();
    }

    private void doDynamics() {
        new Drags(m_strip, primaryStage);

        new DoorAnimations(m_strip, "");

        new DoorAnimations(cover_util, minimize, maximize, close);
    }

    private void onSceneAttached() {
        tabs = new Tabs(getParent());
        tabs.create();
    }

    private void attachDoorEvents() {
        final FadeTransition[] fadeTransitions = new FadeTransition[6];
        new_page.setOnMouseEntered(e -> {
            fadeTransitions[0] = new FadeTransition(Duration.seconds(.45), new_page_effect_circle);
            fadeTransitions[0].setFromValue(new_page_effect_circle.getOpacity());
            fadeTransitions[0].setToValue(1.);
            fadeTransitions[0].play();
        });
        new_page.setOnMouseClicked(e -> {
            // Whenever this event occurs, a default Home page tab must be added.
            fadeTransitions[0] = new FadeTransition(Duration.seconds(.45), new_page_effect_circle);
            // Perform some action and opacity restoration
            fadeTransitions[0].setFromValue(new_page_effect_circle.getOpacity());
            fadeTransitions[0].setToValue(.2);
            fadeTransitions[0].play();
        });
        new_page.setOnMouseExited(e -> {
            fadeTransitions[0] = new FadeTransition(Duration.seconds(.45), new_page_effect_circle);
            // Perform opacity restoration
            fadeTransitions[0].setFromValue(new_page_effect_circle.getOpacity());
            fadeTransitions[0].setToValue(.2);
            fadeTransitions[0].play();
        });
        bookmarks.setOnMouseEntered(e -> {
            fadeTransitions[1] = new FadeTransition(Duration.seconds(.45), bookmarks_effect_circle);
            fadeTransitions[1].setFromValue(bookmarks_effect_circle.getOpacity());
            fadeTransitions[1].setToValue(1.);
            fadeTransitions[1].play();
        });
        bookmarks.setOnMouseClicked(e -> {
            fadeTransitions[1] = new FadeTransition(Duration.seconds(.45), bookmarks_effect_circle);
            // Perform some action and opacity restoration
            fadeTransitions[1].setFromValue(bookmarks_effect_circle.getOpacity());
            fadeTransitions[1].setToValue(.2);
            fadeTransitions[1].play();
        });
        bookmarks.setOnMouseExited(e -> {
            fadeTransitions[1] = new FadeTransition(Duration.seconds(.45), bookmarks_effect_circle);
            // Perform opacity restoration
            fadeTransitions[1].setFromValue(bookmarks_effect_circle.getOpacity());
            fadeTransitions[1].setToValue(.2);
            fadeTransitions[1].play();
        });
        refresh.setOnMouseEntered(e -> {
            fadeTransitions[2] = new FadeTransition(Duration.seconds(.45), refresh_effect_circle);
            fadeTransitions[2].setFromValue(refresh_effect_circle.getOpacity());
            fadeTransitions[2].setToValue(1.);
            fadeTransitions[2].play();
        });
        refresh.setOnMouseClicked(e -> {
            // refresh the current page on current tab
            // Perform some action and opacity restoration
            fadeTransitions[2].setFromValue(refresh_effect_circle.getOpacity());
            fadeTransitions[2].setToValue(.2);
            fadeTransitions[2].play();
        });
        refresh.setOnMouseExited(e -> {
            fadeTransitions[2] = new FadeTransition(Duration.seconds(.45), refresh_effect_circle);
            // Perform opacity restoration
            fadeTransitions[2].setFromValue(refresh_effect_circle.getOpacity());
            fadeTransitions[2].setToValue(.2);
            fadeTransitions[2].play();
        });
        close_page.setOnMouseEntered(e -> {
            fadeTransitions[5] = new FadeTransition(Duration.seconds(.45), close_page_effect_circle);
            fadeTransitions[5].setFromValue(close_page_effect_circle.getOpacity());
            fadeTransitions[5].setToValue(1.);
            fadeTransitions[5].play();
        });
        close_page.setOnMouseClicked(e -> {
            fadeTransitions[5] = new FadeTransition(Duration.seconds(.45), close_page_effect_circle);
            // Perform some action and opacity restoration
            fadeTransitions[5].setFromValue(close_page_effect_circle.getOpacity());
            fadeTransitions[5].setToValue(.2);
            fadeTransitions[5].play();
        });
        close_page.setOnMouseExited(e -> {
            fadeTransitions[5] = new FadeTransition(Duration.seconds(.45), close_page_effect_circle);
            // Perform opacity restoration
            fadeTransitions[5].setFromValue(close_page_effect_circle.getOpacity());
            fadeTransitions[5].setToValue(.2);
            fadeTransitions[5].play();
        });
        forward.setOnMouseEntered(e -> {
            fadeTransitions[3] = new FadeTransition(Duration.seconds(.45), forward_effect_circle);
            fadeTransitions[3].setFromValue(forward_effect_circle.getOpacity());
            fadeTransitions[3].setToValue(1.);
            fadeTransitions[3].play();
        });
        forward.setOnMouseClicked(e -> {
            fadeTransitions[3] = new FadeTransition(Duration.seconds(.45), forward_effect_circle);
            // Perform some action and opacity restoration
            fadeTransitions[3].setFromValue(forward_effect_circle.getOpacity());
            fadeTransitions[3].setToValue(.2);
            fadeTransitions[3].play();
        });
        forward.setOnMouseExited(e -> {
            fadeTransitions[3] = new FadeTransition(Duration.seconds(.45), forward_effect_circle);
            // Perform opacity restoration
            fadeTransitions[3].setFromValue(forward_effect_circle.getOpacity());
            fadeTransitions[3].setToValue(.2);
            fadeTransitions[3].play();
        });
        backward.setOnMouseEntered(e -> {
            fadeTransitions[4] = new FadeTransition(Duration.seconds(.45), backward_effect_circle);
            fadeTransitions[4].setFromValue(backward_effect_circle.getOpacity());
            fadeTransitions[4].setToValue(1.);
            fadeTransitions[4].play();
        });
        backward.setOnMouseClicked(e -> {
            fadeTransitions[4] = new FadeTransition(Duration.seconds(.45), backward_effect_circle);
            // Perform some action and opacity restoration
            fadeTransitions[4].setFromValue(backward_effect_circle.getOpacity());
            fadeTransitions[4].setToValue(.2);
            fadeTransitions[4].play();
        });
        backward.setOnMouseExited(e -> {
            fadeTransitions[4] = new FadeTransition(Duration.seconds(.45), backward_effect_circle);
            // Perform opacity restoration
            fadeTransitions[4].setFromValue(backward_effect_circle.getOpacity());
            fadeTransitions[4].setToValue(.2);
            fadeTransitions[4].play();
        });
    }

    public StackPane getParent() {
        return parent;
    }
}