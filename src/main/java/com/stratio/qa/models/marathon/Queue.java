package com.stratio.qa.models.marathon;

import java.util.List;

public class Queue {

    private List<QueueTask> queue;

    public List<QueueTask> getQueue() {
        return queue;
    }

    public static class QueueTask {

        private VersionedApp app;

        private Integer count;

        private Delay delay;

        public VersionedApp getApp() {
            return app;
        }

        public Integer getCount() {
            return count;
        }

        public Delay getDelay() {
            return delay;
        }

        public static class Delay {

            private Integer timeLeftSeconds;

            private Boolean overdue;

            public Integer getTimeLeftSeconds() {
                return timeLeftSeconds;
            }

            public Boolean getOverdue() {
                return overdue;
            }
        }
    }
}
