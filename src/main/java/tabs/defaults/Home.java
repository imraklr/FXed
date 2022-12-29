package tabs.defaults;

import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
        webView.setPageFill(Color.valueOf("#928378"));
        webView.setContextMenuEnabled(false); // When set false, right click option is disabled
        webView.setFontSmoothingType(FontSmoothingType.LCD); // Font Smoothing Type
        webView.setPrefSize(screenBounds.getMaxX() - 500, screenBounds.getMaxY() - 500);
        WebEngine webEngine = webView.getEngine();
        webEngine.load("https://www.google.com");

        Rectangle rectangle = new Rectangle(10, 10);
        rectangle.setTranslateX(100);
        rectangle.setTranslateY(300);

//        components.add(webView);
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
