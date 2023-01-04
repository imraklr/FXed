package logs;

import java.util.List;

public class Logs<T> {
    public static List<Logs> allLogs;
    T[] any;

    @SafeVarargs
    public Logs(T... v) {
        any = v;
        allLogs.add(this);
    }

    public static List<Logs> getLogs() {
        return allLogs;
    }
}
