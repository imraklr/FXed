package tabs;

import com.example.webviewexamples.Door;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.paint.Paint;
import tabs.defaults.Home;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("ALL")
public abstract class Tabs {
    // Tabs Index
    protected static HashMap<Integer, Tabs> tabs;

    static {
        tabs = new HashMap<>();
    }

    protected ArrayList<Node> components;
    // In pixels(parent size)
    protected double[] defaultParentSize = {400, 400};

    {
        components = new ArrayList<>();
    }

    public static ArrayList<Node> get(int accessIndex) {
        createTabs();
        if (tabs == null)
            return null;
        return tabs.get(accessIndex).getComponents();
    }

    // TODO: Implement further methods to paint Parent node only
    public static void beautify(int accessIndex, Object... params) {
        // tabIndex is the tab we want to beautify
        // placeholder
        /*
            params[0] contains which mode is being used(bright/dark)
            params[1] contains ....
        */
        if ((int) params[0] == 1) {
            // switch to dark mode
            // Create a thread
            Thread taskThread = new Thread(() -> Platform.runLater(() -> {
                Door door = new Door();
                Parent p = door.getParent();
                double lowBound = 0.0;
                while (lowBound <= 1.0) {
                    p.getScene().setFill(Paint.valueOf("#123784"));
                    lowBound += 0.1;
                }
            }));
            taskThread.setDaemon(true);
            taskThread.start();
        }
    }

    private static void createTabs() {
        System.out.println("ENTERE");
        @SuppressWarnings("unused") Home ob1 = new Home(1);
    }

    public abstract ArrayList<Node> getComponents();

    protected abstract void setDefaultParentSize();

    protected abstract double[] getDefaultParentSize();

    protected abstract void UI();
}
