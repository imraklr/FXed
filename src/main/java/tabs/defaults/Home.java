package tabs.defaults;

import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import tabs.Tabs;

import java.util.ArrayList;

public class Home extends Tabs {

    public Home(int accessIndex) {
        tabs.put(accessIndex, this);
        UI();
    }

    @Override
    protected void UI() {
        Rectangle rectangle = new Rectangle(100, 200, Paint.valueOf("#193458"));
        components.add(rectangle);
    }

    @Override
    public ArrayList<Node> getComponents() {
        return components;
    }

    @Override
    protected void setDefaultParentSize() {
    }

    @Override
    protected double[] getDefaultParentSize() {
        return new double[0];
    }
}
