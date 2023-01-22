package TabsManager;

import animations.DoorAnimations;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Worker;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.util.Duration;

@SuppressWarnings("unused")
public class Tabs extends GridPane {
    private final StackPane parent;
    private final double initExtendX, initExtendY;
    private final Rectangle2D screenBounds;
    private final Bounds rootBounds;
    WebView currentVisitingTab;
    private double curr_webView_X, curr_webView_Y;

    {
        screenBounds = Screen.getPrimary().getBounds();
    }

    public Tabs(StackPane parent) {
        super.setMaxHeight(30); // height can be obtained using Tabs.getHeight() method
        super.setHgap(10); // HGap can be obtained using Tabs.getHGap() method
        super.setVgap(0); // VGap can be obtained using Tabs.getVGap() method
        super.setTranslateY(parent.getChildrenUnmodifiable().get(0).getLayoutBounds().getHeight());
        super.setStyle("-fx-background-color: #982342;");
        this.parent = parent;
        rootBounds = parent.getScene().getRoot().getLayoutBounds();
        initExtendY = parent.getChildren().get(0).getLayoutBounds().getHeight();
        initExtendX = parent.getChildren().get(0).getLayoutBounds().getWidth();
        parent.getChildren().add(this);
    }

    public void create() {
        // Creates default page
        attachDefaultPage();
        animates();
    }

    public void create(String URL) {
        WebView webView = new WebView();
        currentVisitingTab = webView;
        webView.setMaxWidth(parent.getChildren().get(0).getLayoutBounds().getWidth());
        webView.setMaxHeight(Screen.getPrimary().getVisualBounds().getMaxY() - 30 - parent.getChildren().get(0).getLayoutBounds().getHeight());
        webView.setTranslateY(parent.getChildren().get(0).getLayoutBounds().getHeight() + 30);
        WebEngine webEngine = webView.getEngine();
        // Check for page errors/load problems
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.FAILED)
                DoorAnimations.animate((Rectangle) parent.getChildren().get(0), "FAILED");
            else if (newValue == Worker.State.CANCELLED)
                DoorAnimations.animate((Rectangle) parent.getChildren().get(0), "CANCELLED");
            else if (newValue == Worker.State.SUCCEEDED)
                DoorAnimations.animate((Rectangle) parent.getChildren().get(0), "SUCCEEDED");
        });
        webEngine.load(URL);
        webEngine.setJavaScriptEnabled(true);
        parent.getChildren().add(23, webView);
        animates();
    }

    private void attachDefaultPage() {
        WebView webView = new WebView();
        currentVisitingTab = webView;
        webView.setMaxWidth(parent.getChildren().get(0).getLayoutBounds().getWidth());
        webView.setMaxHeight(Screen.getPrimary().getVisualBounds().getMaxY() - 30 - parent.getChildren().get(0).getLayoutBounds().getHeight());
        webView.setTranslateY(parent.getChildren().get(0).getLayoutBounds().getHeight() + 30);
        WebEngine webEngine = webView.getEngine();
        // Check for page errors/load problems
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.FAILED)
                DoorAnimations.animate((Rectangle) parent.getChildren().get(0), "FAILED");
            else if (newValue == Worker.State.CANCELLED)
                DoorAnimations.animate((Rectangle) parent.getChildren().get(0), "CANCELLED");
            else if (newValue == Worker.State.SUCCEEDED)
                DoorAnimations.animate((Rectangle) parent.getChildren().get(0), "SUCCEEDED");
        });
        webEngine.load("https://openai.com/blog/chatgpt/");
        webEngine.setJavaScriptEnabled(true);
        parent.getChildren().add(23, webView);
    }

    private void animates() {
        final TranslateTransition[] translateTransition = {null, null};
        final ParallelTransition[] parallelTransitions = {null, null};
        final double initHeight = screenBounds.getHeight() - this.getHeight() - parent.getChildren().get(0).getLayoutBounds().getHeight();
        final double expandedHeight = initHeight - 20;
        this.setOnMouseEntered(mouseEvent -> {
            // make webView shorter
            currentVisitingTab.setMaxHeight(initHeight);
            // translate downwards
            translateTransition[0] = new TranslateTransition(Duration.seconds(.5), this);
            // add translateTransition[0] to parallelTransition
            translateTransition[0].setToY(initExtendY);
            // make webView translate
            translateTransition[1] = new TranslateTransition(Duration.seconds(.5), currentVisitingTab);
            translateTransition[1].setToY(this.getHeight() + initExtendY);
            parallelTransitions[0] = new ParallelTransition(translateTransition[0], translateTransition[1]);
            parallelTransitions[0].play();
        });
        this.setOnMouseExited(mouseEvent -> {
            // make webView longer
            currentVisitingTab.setMaxHeight(expandedHeight);
            // send this to back and translate upwards
            this.toBack();
            translateTransition[0] = new TranslateTransition(Duration.seconds(.5), this);
            translateTransition[0].setFromY(translateTransition[0].getFromY());
            translateTransition[0].setToY(parent.getChildren().get(0).getLayoutBounds().getHeight() - 10);
            // make webView translate
            translateTransition[1] = new TranslateTransition(Duration.seconds(.5), currentVisitingTab);
            translateTransition[1].setToY(this.getHeight() + 20);
            parallelTransitions[0] = new ParallelTransition(translateTransition[0], translateTransition[1]);
            parallelTransitions[0].play();
        });
    }

    private void url(String URL) {
    }

    private void createTabBar(String title) {
    }

    private void createTabStack() {
    }

    private <T> void setChangeListenerForNewTabAddition() {
        // Check regularly whether there is any change in the number of nodes for this
        this.getChildren().addListener((ListChangeListener<? super Node>) change ->
                System.out.println("Number of nodes in GridPane changed to: " + this.getChildren().size()));
    }

    private <T> void attachListenersFor() {
    }
}
