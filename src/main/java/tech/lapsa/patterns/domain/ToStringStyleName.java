package tech.lapsa.patterns.domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import tech.lapsa.patterns.domain.MyHcEqToStr.ToStrStyle;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ToStringStyleName {
    ToStrStyle value();
}
