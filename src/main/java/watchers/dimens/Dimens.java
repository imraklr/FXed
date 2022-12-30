package watchers.dimens;

import javafx.scene.Node;

public class Dimens {
    private Dimens before_hinge, hinge, after_hinge;
    private Node node;
    private double[] v;
    /*
     * Formal Parameter 'v' is a vararg parameter which keeps note of the following:
     * I)If node has 2D parameters, following are the possible parameters(in order):
     *      v[0] = top                                  (separation value; POSITIVE/NEGATIVE)
     *      v[1] = left                                 (separation value; POSITIVE/NEGATIVE)
     *      v[2] = base                                 (separation value; POSITIVE/NEGATIVE)
     *      v[3] = right                                (separation value; POSITIVE/NEGATIVE)
     *      v[4] = angle in radians                          (angle; POSITIVE/NEGATIVE)
     *      v[5] = x                                     (distance value; POSITIVE)
     *      v[6] = y                                     (distance value; POSITIVE)
     *      v[7] = z                                     (distance value; POSITIVE)
     * II)If node has 3D parameters, following are the possible parameters(in order):
     *      v[0] = top                                  (separation value; POSITIVE/NEGATIVE)
     *      v[1] = left                                 (separation value; POSITIVE/NEGATIVE)
     *      v[2] = base                                 (separation value; POSITIVE/NEGATIVE)
     *      v[3] = right                                (separation value; POSITIVE/NEGATIVE)
     *      v[4] = angle in steradians in 3D space      (separation value; POSITIVE/NEGATIVE)
     *      v[5] = x                                     (distance value; POSITIVE)
     *      v[6] = y                                     (distance value; POSITIVE)
     *      v[7] = z                                     (distance value; POSITIVE)
     * III)If node is a hinge node it contains four extra parameters(in order):
     *      v[8]  = distance from top to boundary of visible scene       (distance value; POSITIVE)(DEFAULT: CALCULATED)
     *      v[9]  = distance from left to boundary of visible scene      (distance value; POSITIVE)(DEFAULT: CALCULATED)
     *      v[10] = distance from base to boundary of visible scene      (distance value; POSITIVE)(DEFAULT: CALCULATED)
     *      v[11] = distance from right to boundary of visible scene     (distance value; POSITIVE)(DEFAULT: CALCULATED)
     */

    public Dimens() {
        // Constructor used for creation of Dimensional Frame
        // Initially Hinge node has no relationship
    }

    public Dimens(Node node, Dimens intoRelation, double... v) throws Exception {
        // The node with vararg array 'v' will be related with 'intoRelation' OR 'relations'
        this.node = node;
        this.v = v;
        intoRelation.relate(this);
    }

    private void relate(Dimens newcomer) throws Exception {
        if (!haveNegativeCoordinates(newcomer.v)) {
            if (hinge == null)
                hinge = newcomer;
            else
                hinge.after_hinge = newcomer;
        } else if (hinge != null)
            hinge.before_hinge = newcomer;
        else {
            throw new Exception("INSERTION OF NODES BEFORE " +
                    "THE INSERTION OF HINGE NODES IS NOT ACCEPTED");
        }
    }

    private boolean haveNegativeCoordinates(double[] v) {
        // Verify array 'v'
        // NOTE: v[4] is angle which can be negative
        for (int i = 0; i < v.length; i++)
            if (v[i] < 0 && i != 4)
                return true;
        return false;
    }

    public void arrange() {
        // Fix hinge first
        hinge.node.setTranslateX(hinge.v[9]);
        hinge.node.setTranslateY(hinge.v[8]);
        // Other fixes if available goes here
    }
}