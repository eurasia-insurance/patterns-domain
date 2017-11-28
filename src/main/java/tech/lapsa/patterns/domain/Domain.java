package tech.lapsa.patterns.domain;

import java.io.Serializable;
import java.lang.annotation.IncompleteAnnotationException;
import java.util.Locale;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import tech.lapsa.java.commons.localization.Localized;
import tech.lapsa.java.commons.reflect.MyAnnotations;

public abstract class Domain implements Serializable, Localized {

    private static final long serialVersionUID = 1L;

    private final Integer prime;
    private final Integer multiplier;
    private final Locale toStringLocale;

    public Domain() {
	this.prime = MyAnnotations.optionalOf(this.getClass(), HashCodePrime.class) //
		.map(HashCodePrime::value) //
		.orElseThrow(() -> new IncompleteAnnotationException(HashCodePrime.class, "value"));
	this.multiplier = MyAnnotations.optionalOf(this.getClass(), HashCodeMultiplier.class) //
		.map(HashCodeMultiplier::value) //
		.orElse(prime);
	this.toStringLocale = MyAnnotations.optionalOf(this.getClass(), ToStringLanguageTag.class) //
		.map(ToStringLanguageTag::value) //
		.map(Locale::forLanguageTag) //
		.orElse(Locale.ENGLISH);
    }

    @Override
    public String toString() {
	return regular(toStringLocale);
    }

    public void unlazy() {
    }

    @Override
    public final int hashCode() {
	return HashCodeBuilder.reflectionHashCode(prime, multiplier, this, false);
    }

    @Override
    public final boolean equals(final Object other) {
	return EqualsBuilder.reflectionEquals(this, other, false);
    }

}
