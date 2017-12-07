package tech.lapsa.patterns.domain;

import java.util.Locale;

import tech.lapsa.java.commons.localization.Localized;

public abstract class Domain extends PojoDomain implements Localized {

    private static final long serialVersionUID = 1L;

    private final Locale toStringLocale;

    public Domain() {
	toStringLocale = Pojo.toStringLocaleOf(this.getClass());
    }

    @Override
    public String toString() {
	return regular(toStringLocale);
    }

    public void unlazy() {
    }

}
