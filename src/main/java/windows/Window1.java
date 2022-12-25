package windows;

import com.example.webviewexamples.Windows;
import javafx.scene.Node;

import java.util.PriorityQueue;

//@SuppressWarnings("ALL")
@SuppressWarnings("ALL")
class Window1 extends Windows {
    private PriorityQueue<Node> windowNodeComponents;
    private String title;

    Window1() {
        // provide 'this' instance in the super class's allWindows array
        allWindows[winIdx++] = this;
        windowNodeComponents = new PriorityQueue<>();
    }

    Window1(String title) {
        this();
        this.title = title;
    }

    @Override
    public PriorityQueue<Node> getWindowNodeComponents() {
        return windowNodeComponents;
    }

    @Override
    protected void setDefaultParentSize() {
    }

    @Override
    protected double[] getDefaultParentSize() {
        return new double[0];
    }
}
