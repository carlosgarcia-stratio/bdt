/*
 * Copyright (C) 2014 Stratio (http://stratio.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.stratio.qa.models.marathon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GetServerInfoResponse {

    private String name;

    private String version;

    private String buildref;

    private Boolean elected;

    private String leader;

    private String frameworkId;

    private MarathonConfig marathon_config;

    private ZookeeperConfig zookeeper_config;

    private EventSubscriber event_subscriber;

    private HttpConfig http_config;

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getBuildref() {
        return buildref;
    }

    public Boolean getElected() {
        return elected;
    }

    public String getLeader() {
        return leader;
    }

    public String getFrameworkId() {
        return frameworkId;
    }

    public MarathonConfig getMarathon_config() {
        return marathon_config;
    }

    public ZookeeperConfig getZookeeper_config() {
        return zookeeper_config;
    }

    public EventSubscriber getEvent_subscriber() {
        return event_subscriber;
    }

    public HttpConfig getHttp_config() {
        return http_config;
    }

    public class MarathonConfig {

        private String master;

        private Integer failover_timeout;

        private String framework_name;

        private Boolean ha;

        private Boolean checkpoint;

        private Integer local_port_min;

        private Integer local_port_max;

        private String executor;

        private String hostname;

        private String webui_url;

        private String mesos_role;

        private Integer task_launch_timeout;

        private Integer task_reservation_timeout;

        private Integer reconciliation_initial_delay;

        private Integer reconciliation_interval;

        private String mesos_user;

        private Integer leader_proxy_connection_timeout_ms;

        private Integer leader_proxy_read_timeout_ms;

        private List<String> features = new ArrayList<>();

        private String mesos_leader_ui_url;

        public String getMaster() {
            return master;
        }

        public Integer getFailover_timeout() {
            return failover_timeout;
        }

        public String getFramework_name() {
            return framework_name;
        }

        public Boolean getHa() {
            return ha;
        }

        public Boolean getCheckpoint() {
            return checkpoint;
        }

        public Integer getLocal_port_min() {
            return local_port_min;
        }

        public Integer getLocal_port_max() {
            return local_port_max;
        }

        public String getExecutor() {
            return executor;
        }

        public String getHostname() {
            return hostname;
        }

        public String getWebui_url() {
            return webui_url;
        }

        public String getMesos_role() {
            return mesos_role;
        }

        public Integer getTask_launch_timeout() {
            return task_launch_timeout;
        }

        public Integer getTask_reservation_timeout() {
            return task_reservation_timeout;
        }

        public Integer getReconciliation_initial_delay() {
            return reconciliation_initial_delay;
        }

        public Integer getReconciliation_interval() {
            return reconciliation_interval;
        }

        public String getMesos_user() {
            return mesos_user;
        }

        public Integer getLeader_proxy_connection_timeout_ms() {
            return leader_proxy_connection_timeout_ms;
        }

        public Integer getLeader_proxy_read_timeout_ms() {
            return leader_proxy_read_timeout_ms;
        }

        public List<String> getFeatures() {
            return features;
        }

        public String getMesos_leader_ui_url() {
            return mesos_leader_ui_url;
        }
    }

    public class ZookeeperConfig {

        private String zk;

        private Integer zk_timeout;

        private Integer zk_session_timeout;

        private Integer zk_max_versions;

        public String getZk() {
            return zk;
        }

        public Integer getZk_timeout() {
            return zk_timeout;
        }

        public Integer getZk_session_timeout() {
            return zk_session_timeout;
        }

        public Integer getZk_max_versions() {
            return zk_max_versions;
        }
    }

    public class EventSubscriber {

        private String type;

        private Collection<String> http_endpoints;

        public String getType() {
            return type;
        }

        public Collection<String> getHttp_endpoints() {
            return http_endpoints;
        }
    }

    public class HttpConfig {

        private String http_port;

        private String https_port;

        public String getHttp_port() {
            return http_port;
        }

        public String getHttps_port() {
            return https_port;
        }
    }
}
