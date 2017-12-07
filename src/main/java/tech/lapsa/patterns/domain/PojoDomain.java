package tech.lapsa.patterns.domain;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public abstract class PojoDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Integer prime;
    private final Integer multiplier;
    private final ToStringStyle style;

    public PojoDomain() {
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