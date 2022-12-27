package tabs.defaults;

import javafx.scene.Node;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import tabs.Tabs;

import java.util.ArrayList;

public class Home extends Tabs {

    public Home(int accessIndex) {
        tabs.put(accessIndex, this);
        UI();
    }

    @Override
    protected void UI() {
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.load("https://www.stryve.online");

        components.add(webView);
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
