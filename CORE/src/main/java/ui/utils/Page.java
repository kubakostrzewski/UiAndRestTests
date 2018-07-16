package ui.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME) //bierz to pod uwagę kiedy działa aplikacja
public @interface Page {
    public Class[] value();
}
