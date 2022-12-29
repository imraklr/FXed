package watchers.dimens;

import javafx.stage.Stage;

@SuppressWarnings("unused")
public class Resizers {
    // Constructor for stage resizing
    public Resizers(Stage stage) {
        // Attaching Listeners
        stage.widthProperty().addListener((observableValue, number, t1) -> {
        });
        stage.heightProperty().addListener((observableValue, number, t1) -> {
        });

        /*
         * TODO: Future features:
         *  1) On full screen, fit the stage dimension according to monitors' dimensions(what is the
         *  shape of the corners)
         */
    }
}
