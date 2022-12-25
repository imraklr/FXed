package com.example.webviewexamples;

import javafx.scene.Node;

import java.util.Collection;
import java.util.PriorityQueue;

@SuppressWarnings("ALL")
public abstract class Windows {
    // Windows Index
    protected static int winIdx;
    protected static Windows[] allWindows;

    // first time invocations
    static {
        // Initialising window index to zero
        winIdx = 0;
        // Number of windows must be a fixed
        allWindows = new Windows[10];
    }

    // In pixels(parent size)
    protected int[] defaultParentSize = {400, 400};
    // default name
    protected String title = "WebView Demonstrations";

    protected static final Collection<Node> get(int windowIndex, int untoPriority) {
        // unto initial capacity - untoPriority
        PriorityQueue<Node> fixedReturn = new PriorityQueue<>(untoPriority);
        var arr = allWindows[windowIndex].getWindowNodeComponents();
        if (arr == null)
            return null;
        int iter = 0;
        Node removed = null;
        while (iter < untoPriority) {
            // remove from original
            removed = allWindows[windowIndex].getWindowNodeComponents().remove();
            // add to fixedReturn PriorityQueue
            fixedReturn.add(removed);
            // add to the original which has been removed
            allWindows[windowIndex].getWindowNodeComponents().add(removed);
        }
        return fixedReturn;
    }

    protected abstract PriorityQueue<Node> getWindowNodeComponents();

    protected abstract void setDefaultParentSize();

    protected abstract double[] getDefaultParentSize();
}
