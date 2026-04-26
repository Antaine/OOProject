package ie.tus.project.services;

import java.lang.ScopedValue;
import java.util.Locale;

public final class AppContext {

    public static final ScopedValue<Locale> CURRENT_LOCALE =
            ScopedValue.newInstance();

    private AppContext() {
    }
}