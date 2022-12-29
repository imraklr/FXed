package watchers.dimens.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.PARAMETER)
public @interface DefaultArraySize {
    @Deprecated int size() default 6;
    // @Deprecated: replace with 4 for 2D(Removing 3D feature)
}