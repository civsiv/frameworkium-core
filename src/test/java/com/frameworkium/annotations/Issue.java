package com.frameworkium.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use this annotation to link to a single issue in JIRA.
 * Usage:
 * <pre>
 * &#064;Issue("PROJKEY-1")
 * public void testSomething() {
 *     ...
 * }
 * </pre>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Issue {
    String value();
}
