package com.gzz.common.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ChangeDataSource {
	String value() default "datasource1";
}
