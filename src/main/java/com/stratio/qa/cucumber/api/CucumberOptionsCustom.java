package com.stratio.qa.cucumber.api;

import cucumber.api.SnippetType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface CucumberOptionsCustom {
    boolean dryRun() default false;

    boolean strict() default false;

    FeatureEnvironment[] environments() default {};

    String[] glue() default {};

    String[] extraGlue() default {};

    String[] tags() default {};

    String[] plugin() default {};

    boolean monochrome() default false;

    String[] name() default {};

    SnippetType snippets() default SnippetType.UNDERSCORE;

    String[] junit() default {};
}
