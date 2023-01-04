package watchers.dimens.resizers;

// Two type of resizers are here:
// 1) Manual Resizer(one that involves input from user)
// 2) Auto Resizer(Specified coordinates w/o animation)
// More can be added
public class Resizers<T> {
    private final String ManualResizer = "MANUAL";
    private final String AutoResizer = "AUTO";

    public <T> Resizers(String resizerType, T what, double coordinates) {
        // Validating
        if (resizerType.equalsIgnoreCase(ManualResizer)) {
            // do validation for manual resizer

        } else if (resizerType.equalsIgnoreCase(AutoResizer)) {
            // do validation for auto resizer
        }
    }
}
