package com.stratio.qa.models.marathon;

import java.util.Collection;
import java.util.List;

public class Deployment {

    private Collection<String> affectedApps;

    private String id;

    private List<Step> steps;

    private Collection<Action> currentActions;

    private String version;

    private Integer currentStep;

    private Integer totalSteps;

    public Collection<String> getAffectedApps() {
        return affectedApps;
    }

    public String getId() {
        return id;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public Collection<Action> getCurrentActions() {
        return currentActions;
    }

    public String getVersion() {
        return version;
    }

    public Integer getCurrentStep() {
        return currentStep;
    }

    public Integer getTotalSteps() {
        return totalSteps;
    }

    public class Step {
        private List<Action> actions;

        public List<Action> getActions() {
            return actions;
        }
    }

    public class Action {

        private String type;

        private String app;

        public String getType() {
            return type;
        }

        public String getApp() {
            return app;
        }
    }
}
