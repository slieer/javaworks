package com.annotion.simple;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;;

@Retention(value = RetentionPolicy.RUNTIME)
public @interface KeyValueAnnotation {
	public String key() default "name";
	public String value() default "xy";
}
