package watchers.dimens;

import javafx.scene.Node;
import watchers.dimens.interfaces.DefaultArraySize;

// Supressing for now because a feature has to removed in the future version
@SuppressWarnings("ALL")
public class Dimens {
    Node whichNode;
    Dimens[] relations;
    // Separations must be met only when it has some gap with parent/any node
    double leftSeparation, rightSeparation, topSeparation,
            bottomSeparation, sizeIn_x, sizeIn_y;
    @Deprecated
    double sizeIn_z;
    double height, width; // For 3D

    public Dimens(Node whichNode, double leftSeparation,
                  double rightSeparation, double topSeparation,
                  double bottomSeparation, double sizeIn_x,
                  double sizeIn_y, double sizeIn_z,
                  @DefaultArraySize Dimens[] relations) {
        this.whichNode = whichNode;
        this.relations = relations;
        this.leftSeparation = leftSeparation;
        this.rightSeparation = rightSeparation;
        this.topSeparation = topSeparation;
        this.bottomSeparation = bottomSeparation;
        this.sizeIn_z = sizeIn_z;
        this.sizeIn_x = sizeIn_x;
        this.sizeIn_y = sizeIn_y;

        // For 2D, last two relations will be null
        /*
         * Sequence of elements in relations array:
         * First element - top Node
         * Second element - left Node
         * Third element - bottom Node
         * Fourth element - right Node
         * Fifth element - back Node(3D)
         * Sixth element - front Node(3D)
         */

        // Attach allChangeListeners
        allChangeListeners_2D();
    }

    public Dimens(Node whichNode, double leftSeparation,
                  double rightSeparation, double topSeparation,
                  double bottomSeparation, double sizeIn_x,
                  double sizeIn_y, double sizeIn_z, double height,
                  double width, @DefaultArraySize Dimens[] relations) {
        this.whichNode = whichNode;
        this.relations = relations;
        this.leftSeparation = leftSeparation;
        this.rightSeparation = rightSeparation;
        this.topSeparation = topSeparation;
        this.bottomSeparation = bottomSeparation;
        this.sizeIn_z = sizeIn_z;
        this.sizeIn_x = sizeIn_x;
        this.sizeIn_y = sizeIn_y;
        this.height = height;
        this.width = width;

        /*
         * Sequence of elements in relations array:
         * First element - top Node
         * Second element - left Node
         * Third element - bottom Node
         * Fourth element - right Node
         * Fifth element - back Node(3D)
         * Sixth element - front Node(3D)
         */

        // Attach allChangeListeners
        allChangeListeners_3D();
    }

    private void allChangeListeners_2D() {
        /*
         * Relations have their own pre-assigned change listeners
         * so assign change listeners to the incoming one(whichNode).
         * If a change or specifically change in size in one node
         * happens then all other nodes are affected in the current
         * window(more accurately in its current parent).
         */
        /*
         * IMP: If we don't change in Z-axis, there is no need of
         * update in z-axis
         */
        // Listening for Resizes
        whichNode.layoutXProperty().addListener((observableValue, number, t1) -> {
            // Change all x's value accordingly
            Node node;
            for (Dimens d : relations)
                if (d != null) {
                    node = d.whichNode;
                    node.setLayoutX(node.getLayoutX() + (double) number);
                }
        });
        whichNode.layoutYProperty().addListener((observableValue, number, t1) -> {
            // Change all y's value accordingly
            Node node;
            for (Dimens d : relations)
                if (d != null) {
                    node = d.whichNode;
                    node.setLayoutY(node.getLayoutY() + (double) number);
                }
        });
        // Listening for Translations
        whichNode.translateXProperty().addListener((observableValue, number, t1) -> {

        });
        whichNode.translateYProperty().addListener((observableValue, number, t1) -> {

        });
        // TODO: Listening for Separations
        // Future Feature: Drag Listeners
    }

    public void allChangeListeners_3D() {

    }

    public Dimens getRelationalDimensions() {
        return this;
    }
}
