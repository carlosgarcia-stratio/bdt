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

package com.stratio.qa.models.cct.marathonServiceApi;

import java.math.BigDecimal;

public class Resources {

    private BigDecimal disk;

    private BigDecimal mem;

    public BigDecimal getDisk() {
        return disk;
    }

    public BigDecimal getMem() {
        return mem;
    }

    public BigDecimal getGpus() {
        return gpus;
    }

    public BigDecimal getCpus() {
        return cpus;
    }

    public String getPorts() {
        return ports;
    }

    private BigDecimal gpus;

    private BigDecimal cpus;

    private String ports;

}