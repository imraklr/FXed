package watchers.dimens;

import javafx.scene.Node;

public class Dimens {
    private Dimens relations;
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
     */

    public Dimens() {
        // Constructor used for creation of Dimensional Frame
        relations = this;
    }

    public Dimens(Node node, Dimens intoRelation, double... v) throws Exception {
        // The node with vararg array 'v' will be related with 'intoRelation' OR 'relations'
        intoRelation.node = node;
        intoRelation.v = v;
        intoRelation.relate(node, v);
    }

    private void relate(Node node, double[] v) throws Exception {
        if (!haveNegativeCoordinates(v))
            if (hinge == null) {
                relations = this;
                hinge = relations;
            } else
                hinge.after_hinge = this;
        else if (hinge != null)
            hinge.before_hinge = this;
        else {
            Exception ex = new Exception("INSERTION OF NODES BEFORE " +
                    "THE INSERTION OF HINGE NODES IS NOT ACCEPTED");
            throw ex;
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
}