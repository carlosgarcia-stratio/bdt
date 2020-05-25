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

import java.util.Map;

public class GetMetricsResponse {

    private String version;

    private Map<String, Gauge> gauges;

    private Map<String, Counter> counters;

    private Map<String, Histogram> histograms;

    private Map<String, Meter> meters;

    private Map<String, Timer> timers;

    public String getVersion() {
        return version;
    }

    public Map<String, Gauge> getGauges() {
        return gauges;
    }

    public Map<String, Counter> getCounters() {
        return counters;
    }

    public Map<String, Histogram> getHistograms() {
        return histograms;
    }

    public Map<String, Meter> getMeters() {
        return meters;
    }

    public Map<String, Timer> getTimers() {
        return timers;
    }

    static class Gauge {

        private Object value;

        public Object getValue() {
            return value;
        }
    }

    static class Counter {

        private Integer count;

        public Integer getCount() {
            return count;
        }
    }

    static class Histogram extends Counter {

        private Double max;

        private Double mean;

        private Double min;

        private Double p50;

        private Double p75;

        private Double p95;

        private Double p98;

        private Double p99;

        private Double p999;

        private Double stddev;

        public Double getMax() {
            return max;
        }

        public Double getMean() {
            return mean;
        }

        public Double getMin() {
            return min;
        }

        public Double getP50() {
            return p50;
        }

        public Double getP75() {
            return p75;
        }

        public Double getP95() {
            return p95;
        }

        public Double getP98() {
            return p98;
        }

        public Double getP99() {
            return p99;
        }

        public Double getP999() {
            return p999;
        }

        public Double getStddev() {
            return stddev;
        }
    }

    static class Meter extends Counter {

        private Double m15_rate;

        private Double m1_rate;

        private Double m5_rate;

        private Double mean_rate;

        private String units;

        public Double getM15_rate() {
            return m15_rate;
        }

        public Double getM1_rate() {
            return m1_rate;
        }

        public Double getM5_rate() {
            return m5_rate;
        }

        public Double getMean_rate() {
            return mean_rate;
        }

        public String getUnits() {
            return units;
        }
    }

    static class Timer extends Histogram {

        private Double m15_rate;

        private Double m1_rate;

        private Double m5_rate;

        private Double mean_rate;

        private String duration_units;

        private String rate_units;

        public Double getM15_rate() {
            return m15_rate;
        }

        public Double getM1_rate() {
            return m1_rate;
        }

        public Double getM5_rate() {
            return m5_rate;
        }

        public Double getMean_rate() {
            return mean_rate;
        }

        public String getDuration_units() {
            return duration_units;
        }

        public String getRate_units() {
            return rate_units;
        }
    }
}
