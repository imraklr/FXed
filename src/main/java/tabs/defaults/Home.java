package tabs.defaults;

import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
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
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        webView.setContextMenuEnabled(false); // When set false, right click option is disabled
        webView.setFontSmoothingType(FontSmoothingType.LCD); // Font Smoothing Type
        webView.setPrefSize(screenBounds.getMaxX() - 50, screenBounds.getMaxY() - 50);
        WebEngine webEngine = webView.getEngine();
        webEngine.load("https://www.google.com");

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
