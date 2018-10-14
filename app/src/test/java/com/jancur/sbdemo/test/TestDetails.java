/**
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */
package com.jancur.sbdemo.test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface TestDetails {
    
    String it() default "";

    String context() default "";
}
