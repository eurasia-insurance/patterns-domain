package tech.lapsa.patterns.domain;

import java.io.Serializable;
import java.lang.annotation.IncompleteAnnotationException;
import java.util.Locale;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import tech.lapsa.java.commons.reflect.MyAnnotations;

public abstract class Pojo implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Integer prime;
    private final Integer multiplier;
    private final ToStringStyle style;

    public final static Integer primeOf(final Class<?> clazz) {
	return MyAnnotations.optionalOf(clazz, HashCodePrime.class) //
		.map(HashCodePrime::value) //
		.orElseThrow(() -> new IncompleteAnnotationException(HashCodePrime.class, "value"));
    }

    public final static Integer multiplierOf(final Class<?> clazz, final Integer prime) {
	return MyAnnotations.optionalOf(clazz, HashCodeMultiplier.class) //
		.map(HashCodeMultiplier::value) //
		.orElse(prime);
    }

    public final static ToStringStyle styleOf(final Class<?> clazz) {
	return MyAnnotations.optionalOf(clazz, ToStringStyleName.class) //
		.map(ToStringStyleName::value)
		.map(s -> {
		    switch (s) {
		    case "DEFAULT_STYLE":
			return ToStringStyle.DEFAULT_STYLE;
		    case "JSON_STYLE":
			return ToStringStyle.JSON_STYLE;
		    case "MULTI_LINE_STYLE":
			return ToStringStyle.MULTI_LINE_STYLE;
		    case "NO_CLASS_NAME_STYLE":
			return ToStringStyle.NO_CLASS_NAME_STYLE;
		    case "NO_FIELD_NAMES_STYLE":
			return ToStringStyle.NO_FIELD_NAMES_STYLE;
		    case "SHORT_PREFIX_STYLE":
			return ToStringStyle.SHORT_PREFIX_STYLE;
		    case "SIMPLE_STYLE":
			return ToStringStyle.SIMPLE_STYLE;
		    default:
			return null;
		    }
		}) //
		.orElse(ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public final static Locale toStringLocaleOf(final Class<?> clazz) {
	return MyAnnotations.optionalOf(clazz, ToStringLanguageTag.class) //
		.map(ToStringLanguageTag::value) //
		.map(Locale::forLanguageTag) //
		.orElse(Locale.ENGLISH);
    }

    public Pojo() {
	prime = Pojo.primeOf(this.getClass());
	multiplier = Pojo.multiplierOf(this.getClass(), prime);
	style = Pojo.styleOf(this.getClass());
    }

    @Override
    public String toString() {
	return ToStringBuilder.reflectionToString(this, style);
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