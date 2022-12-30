package animations;

import javafx.animation.FillTransition;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class doorAnimations {
    private final String MESSAGE;

    public doorAnimations(Rectangle menu_strip, String msg) {
        MESSAGE = msg;
        animate(menu_strip, MESSAGE);
    }

    public doorAnimations(Rectangle[] recent_min_max_close, String msg) {
        MESSAGE = msg;
        animate(recent_min_max_close, msg);
    }

    private void animate(Rectangle menu_strip, String MESSAGE) {
        FillTransition ft;

        switch (MESSAGE) {
            case "" -> menu_strip.setFill(new LinearGradient(0.0, 1.0, 1.0, 1.0, true, CycleMethod.NO_CYCLE,
                    new Stop(0.0, Color.web("#0eed89")),
                    new Stop(1.0, Color.web("#f01394"))));
            case "ERROR" -> {
                ft = new FillTransition(Duration.millis(1300), menu_strip,
                        Color.web("#ff0000"), Color.web("#e38f8f"));
                ft.setCycleCount(1);
                ft.setAutoReverse(true);
                ft.play();
                ft.setOnFinished(e -> menu_strip.setFill(new LinearGradient(0.0, 1.0, 1.0, 1.0, true, CycleMethod.NO_CYCLE,
                        new Stop(0.0, Color.web("#0eed89")),
                        new Stop(1.0, Color.web("#f01394")))));
            }
            case "WARNING" -> {
                // For example expired certificate
                ft = new FillTransition(Duration.millis(1500), menu_strip,
                        Color.web("#edca18"), Color.web("#f0da6e"));
                ft.setCycleCount(1);
                ft.setAutoReverse(true);
                ft.play();
                ft.setOnFinished(e -> menu_strip.setFill(new LinearGradient(0.0, 1.0, 1.0, 1.0, true, CycleMethod.NO_CYCLE,
                        new Stop(0.0, Color.web("#0eed89")),
                        new Stop(1.0, Color.web("#f01394")))));
            }
        }
    }

    private void animate(Rectangle[] recent_min_max_close, String msg) {
        // recent, min, max, close
        
    }
}
