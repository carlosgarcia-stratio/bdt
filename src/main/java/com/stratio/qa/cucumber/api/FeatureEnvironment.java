package com.stratio.qa.cucumber.api;

public @interface FeatureEnvironment {

    Condition[] conditions();

    String[] features();
}
