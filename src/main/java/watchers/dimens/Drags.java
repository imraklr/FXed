package watchers.dimens;

import javafx.scene.input.MouseButton;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Drags {
    // Data for menu_strip
    private static double xOffset = 0.0, yOffset = 0.0;

    // Constructor for menu_strip drag detection which is a rectangle
    public Drags(Rectangle menu_strip, Stage primaryStage) {
        menu_strip.setOnMousePressed(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                xOffset = primaryStage.getX() - e.getScreenX();
                yOffset = primaryStage.getY() - e.getScreenY();
            }
        });
        menu_strip.setOnMouseDragged(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                primaryStage.setX(e.getScreenX() + xOffset);
                primaryStage.setY(e.getScreenY() + yOffset);
            }
        });
    }
}
