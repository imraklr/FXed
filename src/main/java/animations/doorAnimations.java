package animations;

import javafx.animation.*;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.LinkedList;

public class doorAnimations {
    private String MESSAGE;

    public doorAnimations(Rectangle menu_strip, String msg) {
        MESSAGE = msg;
        animate(menu_strip, MESSAGE);
    }
    private double[] xOffset_mouseEvent, yOffset_mouseEvent, zOffset_mouseEvent;
    private int i = -1;
    public doorAnimations(Rectangle cover_util, Rectangle min, Rectangle max, Rectangle close) {
        MESSAGE = "";
        animate(cover_util, min, max, close);
    }

    public doorAnimations(Node... v) {
        xOffset_mouseEvent = new double[v.length];
        yOffset_mouseEvent = new double[v.length];
        zOffset_mouseEvent = new double[v.length];
        for (Node in : v) {
            ++i;
            var UserData = in.getUserData();
            if (UserData instanceof LinkedList<?>) {
                /*
                 * Linked List structure:
                 * 1st element - A String specifying type of animation(s) performed on node 'in':
                 *                                               TYPES
                 * FOLLOW (DESC: Follows the cursor or any component provided by a distance)
                 * MOVETO (DESC: Moves to the destined Node)
                 *
                 * 2nd element - Extent of animation.(Duration, Corresponding Distances or a String specification -
                 * 'ADJUSTED'.
                 * If 'ADJUSTED' is specified then it must also specify its root element which justifies this adjustment.
                 * This is purely a ratio. A ratio of node size and its root element.
                 * NOTE: There are several ways to obtain sizes. Listeners can also be attached.
                 * Give an option for re-adjust on resize("READJUST")
                 */
                var rmF = ((LinkedList<?>) UserData).removeFirst();
                if (rmF.equals("FOLLOW")) {
                    Duration duration = (Duration) ((LinkedList<?>) UserData).removeFirst();
                    var cDistsOrAdj = ((LinkedList<?>) UserData).removeFirst();
                    if (cDistsOrAdj instanceof String) {
                        if (cDistsOrAdj.equals("ADJUSTED")) {
                            // Adjusted to which Node/Type??
                            var thisAdjustedTo = ((LinkedList<?>) UserData).removeFirst();
                            if (thisAdjustedTo instanceof Node) {
                                // Here restricted translation can not be carried out
                                TranslateTransition translateTransition_toNode = new TranslateTransition((duration), in);
                                translateTransition_toNode.setFromX(in.getTranslateX());
                                translateTransition_toNode.setFromY(in.getTranslateY());
                                translateTransition_toNode.setFromZ(in.getTranslateZ());
                                translateTransition_toNode.setToX((((Node) thisAdjustedTo)).getTranslateX());
                                translateTransition_toNode.setToY(((Node) thisAdjustedTo).getTranslateY());
                                translateTransition_toNode.setToZ(((Node) thisAdjustedTo).getTranslateZ());
                                translateTransition_toNode.play();
                                translateTransition_toNode.setOnFinished(e -> translateTransition_toNode.stop());
                                ((Node) thisAdjustedTo).translateXProperty().addListener((observableValue, number, t1) -> {
                                    ((Node) thisAdjustedTo).setTranslateX((double) t1);
                                    TranslateTransition translateTransition = new TranslateTransition();
                                    translateTransition.setNode(in);
                                    translateTransition.setDuration((duration));
                                    translateTransition.setFromX(in.getTranslateX());
                                    translateTransition.setToX((double) t1);
                                    translateTransition.play();
                                    translateTransition.setOnFinished(e -> translateTransition.stop());
                                });
                                ((Node) thisAdjustedTo).translateYProperty().addListener((observableValue, number, t1) -> {
                                    ((Node) thisAdjustedTo).setTranslateY((double) t1);
                                    TranslateTransition translateTransition = new TranslateTransition();
                                    translateTransition.setNode(in);
                                    translateTransition.setDuration((duration));
                                    translateTransition.setFromY(in.getTranslateY());
                                    translateTransition.setToY((double) t1);
                                    translateTransition.play();
                                    translateTransition.setOnFinished(e -> translateTransition.stop());
                                });
                                ((Node) thisAdjustedTo).translateZProperty().addListener((observableValue, number, t1) -> {
                                    ((Node) thisAdjustedTo).setTranslateZ((double) t1);
                                    TranslateTransition translateTransition = new TranslateTransition();
                                    translateTransition.setNode(in);
                                    translateTransition.setDuration((duration));
                                    translateTransition.setFromZ(in.getTranslateX());
                                    translateTransition.setToZ((double) t1);
                                    translateTransition.play();
                                    translateTransition.setOnFinished(e -> translateTransition.stop());
                                });
                            } else if (thisAdjustedTo instanceof Cursor) {
                                double old_translateX = in.getTranslateX();
                                double old_translateY = in.getTranslateY();
                                double old_translateZ = in.getTranslateZ();
                                // Follow Mouse
                                in.setOnMouseMoved(mouseEvent -> {
                                    TranslateTransition translateTransition = new TranslateTransition();
                                    translateTransition.setNode(in);
                                    translateTransition.setFromX(translateTransition.getFromX());
                                    translateTransition.setFromY(translateTransition.getFromY());
                                    translateTransition.setFromZ(translateTransition.getFromZ());
                                    if (xOffset_mouseEvent[i] < mouseEvent.getX())
                                        translateTransition.setToX(in.getTranslateX() + mouseEvent.getX());
                                    else
                                        translateTransition.setToX(in.getTranslateX() - mouseEvent.getX());
                                    xOffset_mouseEvent[i] = mouseEvent.getX();
                                    if (yOffset_mouseEvent[i] < mouseEvent.getY())
                                        translateTransition.setToY(in.getTranslateY() + mouseEvent.getY());
                                    else
                                        translateTransition.setToY(in.getTranslateY() - mouseEvent.getY());
                                    yOffset_mouseEvent[i] = mouseEvent.getY();
                                    if (zOffset_mouseEvent[i] < mouseEvent.getZ())
                                        translateTransition.setToZ(in.getTranslateZ() + mouseEvent.getZ());
                                    else
                                        translateTransition.setToZ(in.getTranslateZ() - mouseEvent.getZ());
                                    zOffset_mouseEvent[i] = mouseEvent.getZ();
                                    in.setOnMouseExited(e1 -> {
                                        TranslateTransition translateTransition1 = new TranslateTransition();
                                        translateTransition1.setNode(in);
                                        translateTransition1.setToX(old_translateX);
                                        translateTransition1.setToY(old_translateY);
                                        translateTransition1.setToZ(old_translateZ);
                                        translateTransition1.play();
                                    });
                                    translateTransition.play();
                                });
                            }
                        }
                    } else if (cDistsOrAdj instanceof double[] arr) {
                        /*
                         * It is a coordinate that is to be followed in specified duration
                         * Here restricted translation can be done
                         * (x1, y1, z1), (x2, y2, z2)
                         * Traversal is only possible in constraints:
                         * Extreme X Left = x1, Extreme X Right = x2
                         * Extreme Y Left = y1, Extreme Y Right = y2
                         * Extreme Z Left = z1, Extreme Z Right = z2
                         */
                        // Follow which Node/Type?
                        var thisAdjustedTo = ((LinkedList<?>) UserData).removeFirst();
                        // Edit required in if block
                        if (thisAdjustedTo instanceof Node) {
                            // Here restricted translation can be carried out
                            TranslateTransition translateTransition_toNode = new TranslateTransition((duration), in);
                            translateTransition_toNode.setFromX(arr[0]);
                            translateTransition_toNode.setFromY(arr[1]);
                            translateTransition_toNode.setFromZ(arr[2]);
                            translateTransition_toNode.setToX(arr[3]);
                            translateTransition_toNode.setToY(arr[4]);
                            translateTransition_toNode.setToZ(arr[5]);
                            translateTransition_toNode.play();
                            ((Node) thisAdjustedTo).translateXProperty().addListener((observableValue, number, t1) -> {
                                ((Node) thisAdjustedTo).setTranslateX((double) t1);
                                TranslateTransition translateTransition = new TranslateTransition();
                                translateTransition.setNode(in);
                                translateTransition.setDuration((duration));
                                translateTransition.setFromX(in.getTranslateX());
                                translateTransition.setToX((double) t1);
                                translateTransition.play();
                            });
                            ((Node) thisAdjustedTo).translateYProperty().addListener((observableValue, number, t1) -> {
                                ((Node) thisAdjustedTo).setTranslateY((double) t1);
                                TranslateTransition translateTransition = new TranslateTransition();
                                translateTransition.setNode(in);
                                translateTransition.setDuration((duration));
                                translateTransition.setFromY(in.getTranslateY());
                                translateTransition.setToY((double) t1);
                                translateTransition.play();
                            });
                            ((Node) thisAdjustedTo).translateZProperty().addListener((observableValue, number, t1) -> {
                                ((Node) thisAdjustedTo).setTranslateZ((double) t1);
                                TranslateTransition translateTransition = new TranslateTransition();
                                translateTransition.setNode(in);
                                translateTransition.setDuration((duration));
                                translateTransition.setFromZ(in.getTranslateX());
                                translateTransition.setToZ((double) t1);
                                translateTransition.play();
                            });
                        }
                        // Edit required in else-if block
                        else if (thisAdjustedTo instanceof Cursor) {
                            double old_translateX = in.getTranslateX();
                            double old_translateY = in.getTranslateY();
                            double old_translateZ = in.getTranslateZ();
                            // Follow Mouse
                            in.setOnMouseMoved(mouseEvent -> {
                                TranslateTransition translateTransition = new TranslateTransition();
                                translateTransition.setNode(in);
                                translateTransition.setFromX(translateTransition.getFromX());
                                translateTransition.setFromY(translateTransition.getFromY());
                                translateTransition.setFromZ(translateTransition.getFromZ());
                                if (xOffset_mouseEvent[i] < mouseEvent.getX()) {
                                    if (in.getTranslateX() + mouseEvent.getX() <= arr[3]) {
                                        translateTransition.setToX(in.getTranslateX() + mouseEvent.getX());
                                        xOffset_mouseEvent[i] = mouseEvent.getX();
                                    }
                                } else {
                                    if (in.getTranslateX() - mouseEvent.getX() > arr[0]) {
                                        translateTransition.setToX(in.getTranslateX() - mouseEvent.getX());
                                        xOffset_mouseEvent[i] = mouseEvent.getX();
                                    }
                                }
                                if (yOffset_mouseEvent[i] < mouseEvent.getY()) {
                                    if (in.getTranslateY() + mouseEvent.getY() <= arr[4]) {
                                        translateTransition.setToY(in.getTranslateY() + mouseEvent.getY());
                                        yOffset_mouseEvent[i] = mouseEvent.getY();
                                    }
                                } else {
                                    if (in.getTranslateY() - mouseEvent.getY() > arr[1]) {
                                        translateTransition.setToY(in.getTranslateY() - mouseEvent.getY());
                                        yOffset_mouseEvent[i] = mouseEvent.getY();
                                    }
                                }
                                if (zOffset_mouseEvent[i] < mouseEvent.getZ()) {
                                    if (in.getTranslateZ() + mouseEvent.getZ() <= arr[5]) {
                                        translateTransition.setToZ(in.getTranslateZ() + mouseEvent.getZ());
                                        zOffset_mouseEvent[i] = mouseEvent.getZ();
                                    }
                                } else {
                                    if (in.getTranslateZ() - mouseEvent.getZ() > arr[2]) {
                                        translateTransition.setToZ(in.getTranslateZ() - mouseEvent.getZ());
                                        zOffset_mouseEvent[i] = mouseEvent.getZ();
                                    }
                                }
                                translateTransition.play();
                                in.setOnMouseExited(e1 -> {
                                    TranslateTransition translateTransition1 = new TranslateTransition();
                                    translateTransition1.setNode(in);
                                    translateTransition1.setToX(old_translateX);
                                    translateTransition1.setToY(old_translateY);
                                    translateTransition1.setToZ(old_translateZ);
                                    translateTransition1.play();
                                });
                            });
                        }
                    }
                }
            }
        }
    }

    // Solely for menu_strip animations
    public static void animate(Rectangle menu_strip, String MESSAGE) {
        FillTransition ft;

        switch (MESSAGE) {
            case "" -> // placeholder for animations for this linear gradient(if possible with current version of JavaFX)
                    menu_strip.setFill(new LinearGradient(0.0, 1.0, 1.0, 1.0, true, CycleMethod.NO_CYCLE,
                            new Stop(0.0, Color.web("#ff6600")),
                            new Stop(1.0, Color.web("#3ab584"))));
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

    public static void animate(Rectangle cover_util, Rectangle min, Rectangle max, Rectangle close) {

        double original_XPos_min = min.getTranslateX(), original_YPos_min = min.getTranslateY();
        // double original_min_angle = min.getRotate(), original_close_angle = close.getRotate();
        double original_XPos_max = max.getTranslateX(), original_YPos_max = max.getTranslateY();
        double original_XPos_close = close.getTranslateX(), original_YPos_close = close.getTranslateY();
        // double original_XPos_cover_util = cover_util.getTranslateX();
        // double original_YPos_cover_util = cover_util.getTranslateY();

        double[] size_cover_util = {cover_util.getWidth(), cover_util.getHeight()};
        double[] size_cover_min = {min.getWidth(), min.getHeight()};
        double[] size_cover_max = {max.getWidth(), max.getHeight()};
        double[] size_cover_close = {close.getWidth(), close.getHeight()};

        cover_util.setOnMouseEntered(e -> {
            ScaleTransition scaleTransition_cover_util = new ScaleTransition(Duration.seconds(1.3), cover_util);
            scaleTransition_cover_util.setByX(10.0);
            scaleTransition_cover_util.setByY(.1);
            scaleTransition_cover_util.play();

            TranslateTransition translateTransition_min = new TranslateTransition(Duration.seconds(1.3));
            translateTransition_min.setToX(original_XPos_min - 30);
            translateTransition_min.setToY(original_YPos_max / 1.5);
            ScaleTransition scaleTransition_min = new ScaleTransition(Duration.seconds(1.3));
            scaleTransition_min.setByX(1.);
            scaleTransition_min.setByY(6.);
            RotateTransition rotateTransition_min = new RotateTransition(Duration.seconds(1.6));
            rotateTransition_min.setToAngle(0.0);
            ParallelTransition parallelTransition_min = new ParallelTransition(
                    min, translateTransition_min, scaleTransition_min, rotateTransition_min);
            parallelTransition_min.play();

            TranslateTransition translateTransition_max = new TranslateTransition(Duration.seconds(1.3));
            translateTransition_max.setToX(original_XPos_max - 70);
            translateTransition_max.setToY(original_YPos_max / 1.5);
            ScaleTransition scaleTransition_max = new ScaleTransition(Duration.seconds(1.3));
            scaleTransition_max.setByX(1.);
            scaleTransition_max.setByY(6.);
            ParallelTransition parallelTransition_max = new ParallelTransition(
                    max, translateTransition_max, scaleTransition_max);
            parallelTransition_max.play();

            TranslateTransition translateTransition_close = new TranslateTransition(Duration.seconds(1.3));
            translateTransition_close.setToX(original_XPos_close - 110);
            translateTransition_close.setToY(original_YPos_max / 1.5);
            ScaleTransition scaleTransition_close = new ScaleTransition(Duration.seconds(1.3));
            scaleTransition_close.setByY(6.);
            scaleTransition_close.setByX(1.);
            RotateTransition rotateTransition_close = new RotateTransition(Duration.seconds(1.6));
            rotateTransition_close.setToAngle(0.0);
            ParallelTransition parallelTransition_close = new ParallelTransition(close,
                    translateTransition_close, scaleTransition_close, rotateTransition_close);
            parallelTransition_close.play();
        });
        // Following lambda block requires some changes
        cover_util.setOnMouseExited(e -> {
            // Transition, Scaling and rotation restoration
            TranslateTransition translateTransition_min = new TranslateTransition(Duration.seconds(1.3), min);
            translateTransition_min.setToX(original_XPos_min);
            translateTransition_min.setToY(original_YPos_min);
            ScaleTransition scaleTransition_min = new ScaleTransition(Duration.seconds(1.6), min);
            scaleTransition_min.setToX(size_cover_min[0]);
            scaleTransition_min.setToY(size_cover_min[1]);
            RotateTransition rotateTransition_min = new RotateTransition(Duration.seconds(1.3), min);
            rotateTransition_min.setToAngle(-60);
            ParallelTransition parallelTransition_min = new ParallelTransition(
                    translateTransition_min, scaleTransition_min, rotateTransition_min);
            parallelTransition_min.play();

            TranslateTransition translateTransition_close = new TranslateTransition(Duration.seconds(1.3), close);
            translateTransition_min.setToX(original_XPos_close);
            translateTransition_min.setToY(original_YPos_close);
            ScaleTransition scaleTransition_close = new ScaleTransition(Duration.seconds(1.6), close);
            scaleTransition_close.setToX(size_cover_close[0]);
            scaleTransition_close.setToY(size_cover_close[1]);
            RotateTransition rotateTransition_close = new RotateTransition(Duration.seconds(1.3), close);
            rotateTransition_min.setToAngle(60);
            ParallelTransition parallelTransition_close = new ParallelTransition(
                    translateTransition_close, scaleTransition_close, rotateTransition_close);
            parallelTransition_close.play();
        });
    }
}
