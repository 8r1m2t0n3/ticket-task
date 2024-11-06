package org.andersen.homework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NullableWarning {

  String message() default "\033[3mWarning: Field [%s] is null in class [%s]!\033[0m";
}
