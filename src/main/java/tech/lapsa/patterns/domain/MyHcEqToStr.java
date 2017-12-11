package tech.lapsa.patterns.domain;

import java.lang.annotation.IncompleteAnnotationException;
import java.util.Locale;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import tech.lapsa.java.commons.reflect.MyAnnotations;

public final class MyHcEqToStr {

    public static enum ToStrStyle {
	DEFAULT_STYLE(ToStringStyle.DEFAULT_STYLE),
	JSON_STYLE(ToStringStyle.JSON_STYLE),
	MULTI_LINE_STYLE(ToStringStyle.MULTI_LINE_STYLE),
	NO_CLASS_NAME_STYLE(ToStringStyle.NO_CLASS_NAME_STYLE),
	NO_FIELD_NAMES_STYLE(ToStringStyle.NO_FIELD_NAMES_STYLE),
	SHORT_PREFIX_STYLE(ToStringStyle.SHORT_PREFIX_STYLE),
	SIMPLE_STYLE(ToStringStyle.SIMPLE_STYLE);

	private final ToStringStyle apacheStyle;

	private ToStrStyle(final ToStringStyle style) {
	    this.apacheStyle = style;
	}
    }

    public final static int primeOf(final Class<?> clazz) {
	return MyAnnotations.optionalOf(clazz, HashCodePrime.class) //
		.map(HashCodePrime::value) //
		.orElseThrow(() -> new IncompleteAnnotationException(HashCodePrime.class, "value")) //
		.intValue();
    }

    public final static int multiplierOf(final Class<?> clazz, final Integer prime) {
	return MyAnnotations.optionalOf(clazz, HashCodeMultiplier.class) //
		.map(HashCodeMultiplier::value) //
		.orElse(prime) //
		.intValue();
    }

    public final static ToStrStyle styleOf(final Class<?> clazz) {
	return MyAnnotations.optionalOf(clazz, ToStringStyleName.class) //
		.map(ToStringStyleName::value) //
		.orElse(ToStrStyle.SHORT_PREFIX_STYLE);
    }

    public final static Locale toStringLocaleOf(final Class<?> clazz) {
	return MyAnnotations.optionalOf(clazz, ToStringLanguageTag.class) //
		.map(ToStringLanguageTag::value) //
		.map(Locale::forLanguageTag) //
		.orElse(Locale.ENGLISH);
    }

    //

    public static final int hashCode(final Object obj, final int prime, final int multiplier) {
	return HashCodeBuilder.reflectionHashCode(prime, multiplier, obj, false);
    }

    public static int hashCode(final Object obj, final int prime) {
	return hashCode(obj, prime, prime);
    }

    public static final boolean equals(final Object obj, final Object other) {
	return EqualsBuilder.reflectionEquals(obj, other, false);
    }

    public static final String toString(final Object obj, final ToStrStyle style) {
	return ToStringBuilder.reflectionToString(obj, style.apacheStyle);
    }

}
