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

public class DoorAnimations {
    private String MESSAGE;

    public DoorAnimations(Rectangle menu_strip, String msg) {
        MESSAGE = msg;
        animate(menu_strip, MESSAGE);
    }

    private double[] xOffset_mouseEvent, yOffset_mouseEvent, zOffset_mouseEvent;
    private int i = -1;

    public DoorAnimations(Rectangle min, Rectangle max, Rectangle close) {
        MESSAGE = "";
        animate(min, max, close);
    }

    // Extra method for general use
    @SuppressWarnings("unused")
    public DoorAnimations(Node... v) {
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
                            new Stop(.0, Color.web("#ff9900")),
                            new Stop(.25, Color.web("#3ab584")),
                            new Stop(.5, Color.web("#000000")),
                            new Stop(.75, Color.web("#ff5203")),
                            new Stop(1., Color.web("#edc187"))));
            case "FAILED" -> {
                ft = new FillTransition(Duration.millis(1300), menu_strip,
                        Color.web("#ff0000FF"), Color.web("#e38f8fFF"));
                ft.setRate(.8);
                ft.setCycleCount(4);
                ft.setAutoReverse(true);
                ft.play();
                ft.setOnFinished(e -> animate(menu_strip, ""));
            }
            case "CANCELLED" -> {
                // For example expired certificate
                ft = new FillTransition(Duration.millis(1500), menu_strip,
                        Color.web("#edca18FF"), Color.web("#f0da6eFF"));
                ft.setRate(.8);
                ft.setCycleCount(4);
                ft.setAutoReverse(true);
                ft.play();
                ft.setOnFinished(e -> animate(menu_strip, ""));
            }
            case "SUCCEEDED" -> {
                ft = new FillTransition(Duration.millis(1500), menu_strip,
                        Color.web("#00FFCCFF"), Color.web("#00FF55FF"));
                ft.setRate(.8);
                ft.setCycleCount(4);
                ft.setAutoReverse(true);
                ft.play();
                ft.setOnFinished(e -> animate(menu_strip, ""));
            }
        }
    }

    public static void animate(Rectangle min, Rectangle max, Rectangle close) {
        /*
         * Following final variables may be useful in the future versions, so it is marked  for maintenance check
         */

        // Transition types required
        final TranslateTransition[] translateTransitions = new TranslateTransition[3];
        final ScaleTransition[] scaleTransitions = new ScaleTransition[3];
        final ParallelTransition[] parallelTransition = new ParallelTransition[1];
        final RotateTransition[] rotateTransitions = new RotateTransition[2];

        final double init_width__min = min.getWidth();
        final double init_width__max = max.getWidth();
        final double init_width__close = close.getWidth();

        final double slideTo__min = min.getTranslateX() - 7.0 * init_width__min;
        final double slideTo__max = max.getTranslateX() - 4.0 * init_width__max;
        final double slideTo__close = close.getTranslateX() - init_width__close;

        // translate to left

        // Scale min rectangle
        scaleTransitions[0] = new ScaleTransition(Duration.seconds(.75), min);
        scaleTransitions[0].setToX(3.0);
        scaleTransitions[0].setToY(min.getParent().getChildrenUnmodifiable().get(0).getLayoutBounds().getHeight() / 4);
        // translate left
        translateTransitions[0] = new TranslateTransition(Duration.seconds(.75), min);
        translateTransitions[0].setToX(slideTo__min);
        // rotate to 0 deg
        rotateTransitions[0] = new RotateTransition(Duration.seconds(.75), min);
        rotateTransitions[0].setToAngle(0);

        // Scale max rectangle
        scaleTransitions[1] = new ScaleTransition(Duration.seconds(.75), max);
        scaleTransitions[1].setToX(3.0);
        scaleTransitions[1].setToY(max.getParent().getChildrenUnmodifiable().get(0).getLayoutBounds().getHeight() / 4);
        // translate left
        translateTransitions[1] = new TranslateTransition(Duration.seconds(.75), max);
        translateTransitions[1].setToX(slideTo__max);
        translateTransitions[1].setToY(min.getTranslateY());

        // Scale close rectangle
        scaleTransitions[2] = new ScaleTransition(Duration.seconds(.75), close);
        scaleTransitions[2].setToX(3.0);
        scaleTransitions[2].setToY(close.getParent().getChildrenUnmodifiable().get(0).getLayoutBounds().getHeight() / 4);
        // translate left
        translateTransitions[2] = new TranslateTransition(Duration.seconds(.75), close);
        translateTransitions[2].setToX(slideTo__close);
        // rotate to 0 deg
        rotateTransitions[1] = new RotateTransition(Duration.seconds(.75), close);
        rotateTransitions[1].setToAngle(0);

        parallelTransition[0] = new ParallelTransition(translateTransitions[0],
                translateTransitions[1], translateTransitions[2],
                scaleTransitions[0], scaleTransitions[1], scaleTransitions[2],
                rotateTransitions[0], rotateTransitions[1]);
        parallelTransition[0].play();
    }
}
