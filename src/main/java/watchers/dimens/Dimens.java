package watchers.dimens;

import javafx.scene.Node;

public class Dimens {
    private static Dimens hingeIterator;
    private Dimens hinge; // no need for this to be static
    private Node node;
    private double[] v;
    /*
     * NOTE: All distance calculations are POSITIVE and measured from (0, 0) of the scene.
     * Formal Parameter 'v' is a vararg parameter which keeps note of the following:
     * I)If node has 2D parameters, following are the possible parameters(in order):
     *      v[0] = distance from top to boundary of another node(gap)        (separation value; POSITIVE/NEGATIVE)
     *      v[1] = distance from left to boundary of another node(gap)       (separation value; POSITIVE/NEGATIVE)
     *      v[2] = distance from base to boundary of another node(gap)       (separation value; POSITIVE/NEGATIVE)
     *      v[3] = distance from right to boundary of another node(gap)      (separation value; POSITIVE/NEGATIVE)
     *      v[4] = angle in radians                          (angle; POSITIVE/NEGATIVE)
     *      v[5] = x                                     (distance value; POSITIVE)
     *      v[6] = y                                     (distance value; POSITIVE)
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

    public Dimens(Node node, Dimens intoRelation, double... v) {
        // The node with vararg array 'v' will be related with 'intoRelation' OR 'relations'
        this.node = node;
        this.v = v;
        double w = node.getScene().getWindow().getWidth();
        double h = node.getScene().getWindow().getHeight();

        // Zero checking for section III mentioned in the comment above
        // easy and rare conditions(mostly for hinge node at origin)
        v[10] = h - v[8];
        // if negative v[10] was supplied
        if (v[10] > h)
            v[10] -= h;
        v[11] = w - v[9];
        // if negative v[11] was supplied
        if (v[11] > w)
            v[11] -= w;

        intoRelation.relate(this);
    }

    private void relate(Dimens newcomer) {
        if (hinge == null) {
            hinge = newcomer;
            hingeIterator = hinge;
        } else {
            hingeIterator.hinge = newcomer;
            hingeIterator = hingeIterator.hinge;
        }
    }

    public void arrange() {
        Dimens temp = hinge;
        if (temp != null) {
            while (temp != null) {
                // All are placed relative to hinge node and hinge node is placed relative to hinge node itself
                temp.v[8] = temp.v[0] + hinge.v[8];
                temp.v[9] = temp.v[3] + hinge.v[9];
                /*
                 * For debugging purpose use this -
                 * System.out.println(temp.v[9]+" == "+hinge.v[9]+", "+temp.v[8]+" == "+hinge.v[8]);
                 */
                temp.node.setTranslateY(temp.v[8]);
                temp.node.setTranslateX(temp.v[9]);
                temp.node.setRotate(temp.v[4]); // Rotated
                temp = temp.hinge;
            }
        }
    }

    // The toString() method will be descriptive as indirect recursive calling is applied for cases of Dimens classes
    @Override
    public String toString() {
        String temp = "@Dimens" + String.format("%x", this.hashCode()) + " [";
        if (node != null)
            temp += node + ", ";
        else
            temp += "node=null, ";
        if (v != null) {
            temp += "\n\tdistance from top to boundary of another node=" + v[0] + ", ";
            temp += "distance from left to boundary of another node=" + v[1] + ", ";
            temp += "\n\tdistance from base to boundary of another node=" + v[2] + ", ";
            temp += "distance from right to boundary of another node=" + v[3] + ", ";
            temp += "\n\tangle in radians=" + v[4] + ", ";
            temp += "\n\tx=" + v[5] + ", ";
            temp += "\n\ty=" + v[6] + ", ";
            temp += "\n\tz=" + v[7] + ", ";
            temp += "\n\tdistance from top to boundary of visible scene=" + v[8] + ", ";
            temp += "distance from left to boundary of visible scene=" + v[9] + ", ";
            temp += "\n\tdistance from base to boundary of visible scene=" + v[10] + ", ";
            temp += "distance from right to boundary of visible scene=" + v[11] + ", " + "\n";
        } else
            temp += "v=null, ";
        if (hinge != null)
            temp += hinge + ", ";
        else
            temp += "hinge=null, ";

        temp += "\b\b \b]";

        return temp;
    }
}