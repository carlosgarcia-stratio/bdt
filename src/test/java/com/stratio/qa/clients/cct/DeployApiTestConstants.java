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

package com.stratio.qa.clients.cct;

public class DeployApiTestConstants {

    public static final String getAppsResponseOK = "[\n" +
            "  {\n" +
            "    \"model\": \"pegaso-security\",\n" +
            "    \"version\": \"0.4.22\",\n" +
            "    \"resources\": {\n" +
            "      \"disk\": 36864,\n" +
            "      \"mem\": 14336,\n" +
            "      \"gpus\": 0,\n" +
            "      \"cpus\": 9,\n" +
            "      \"ports\": \"\"\n" +
            "    },\n" +
            "    \"status\": 2,\n" +
            "    \"healthy\": 1,\n" +
            "    \"actions\": [\n" +
            "      \"update\",\n" +
            "      \"restart\",\n" +
            "      \"stop\",\n" +
            "      \"upgrade\",\n" +
            "      \"delete\",\n" +
            "      \"open_service\"\n" +
            "    ],\n" +
            "    \"tasks\": [\n" +
            "      {\n" +
            "        \"id\": \"postgrestls.6a337844-a1b2-11ea-b6e9-70b3d580000b\",\n" +
            "        \"name\": \"postgrestls\",\n" +
            "        \"state\": \"TASK_RUNNING\",\n" +
            "        \"healthy\": 1,\n" +
            "        \"timestamp\": 1590759847000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 0,\n" +
            "          \"mem\": 1024,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 1,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.14\",\n" +
            "        \"calicoIP\": \"172.16.160.67\",\n" +
            "        \"marathonServiceName\": \"postgrestls\",\n" +
            "        \"logs\": null\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"19270be2-bab9-4071-8ae1-948a46375d42\",\n" +
            "        \"name\": \"pg-0003\",\n" +
            "        \"state\": \"TASK_RUNNING\",\n" +
            "        \"healthy\": 2,\n" +
            "        \"timestamp\": 1590759982000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 12288,\n" +
            "          \"mem\": 4096,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 2,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.10\",\n" +
            "        \"calicoIP\": \"172.16.125.70\",\n" +
            "        \"marathonServiceName\": \"postgrestls\",\n" +
            "        \"logs\": null\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"7bafefef-914c-4f22-9cf7-2f4396b9d2e6\",\n" +
            "        \"name\": \"pg-0002\",\n" +
            "        \"state\": \"TASK_RUNNING\",\n" +
            "        \"healthy\": 2,\n" +
            "        \"timestamp\": 1590759877000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 12288,\n" +
            "          \"mem\": 4096,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 2,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.18\",\n" +
            "        \"calicoIP\": \"172.16.36.72\",\n" +
            "        \"marathonServiceName\": \"postgrestls\",\n" +
            "        \"logs\": null\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"cbc903c4-a049-4e60-ba42-3af5ae895e17\",\n" +
            "        \"name\": \"pg-agent\",\n" +
            "        \"state\": \"TASK_RUNNING\",\n" +
            "        \"healthy\": 2,\n" +
            "        \"timestamp\": 1590965575000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 0,\n" +
            "          \"mem\": 1024,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 2,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.9\",\n" +
            "        \"calicoIP\": \"172.16.15.10\",\n" +
            "        \"marathonServiceName\": \"postgrestls\",\n" +
            "        \"logs\": null\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"d65c0b79-5d51-425a-9f47-abae10c964bd\",\n" +
            "        \"name\": \"pg-0001\",\n" +
            "        \"state\": \"TASK_RUNNING\",\n" +
            "        \"healthy\": 2,\n" +
            "        \"timestamp\": 1590759863000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 12288,\n" +
            "          \"mem\": 4096,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 2,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.17\",\n" +
            "        \"calicoIP\": \"172.16.126.135\",\n" +
            "        \"marathonServiceName\": \"postgrestls\",\n" +
            "        \"logs\": null\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"09029a51-0209-4ffc-a73b-b2e22c2cef8c\",\n" +
            "        \"name\": \"pg-agent\",\n" +
            "        \"state\": \"TASK_FAILED\",\n" +
            "        \"healthy\": 0,\n" +
            "        \"timestamp\": 1590965565000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 0,\n" +
            "          \"mem\": 0,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 0,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.9\",\n" +
            "        \"calicoIP\": \"10.200.6.9\",\n" +
            "        \"marathonServiceName\": \"postgrestls\",\n" +
            "        \"logs\": null\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"postgrestls.9e490308-a128-11ea-b6e9-70b3d580000b\",\n" +
            "        \"name\": \"postgrestls\",\n" +
            "        \"state\": \"TASK_KILLED\",\n" +
            "        \"healthy\": 0,\n" +
            "        \"timestamp\": 1590703718000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 0,\n" +
            "          \"mem\": 1024,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 1,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.16\",\n" +
            "        \"calicoIP\": \"10.200.6.16\",\n" +
            "        \"marathonServiceName\": \"postgrestls\",\n" +
            "        \"logs\": null\n" +
            "      }\n" +
            "    ],\n" +
            "    \"totalTasks\": 7,\n" +
            "    \"totalHealthyTasks\": 6,\n" +
            "    \"external\": [\n" +
            "      {\n" +
            "        \"type\": \"adminrouter\",\n" +
            "        \"url\": \"https://bootstrap.nightlybackward.labs.stratio.com/service/postgrestls\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"serviceName\": \"/postgrestls\",\n" +
            "    \"service\": \"postgres\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"model\": \"pegaso\",\n" +
            "    \"version\": \"0.4.22\",\n" +
            "    \"resources\": {\n" +
            "      \"disk\": 32044,\n" +
            "      \"mem\": 9216,\n" +
            "      \"gpus\": 0,\n" +
            "      \"cpus\": 11,\n" +
            "      \"ports\": \"\"\n" +
            "    },\n" +
            "    \"status\": 2,\n" +
            "    \"healthy\": 1,\n" +
            "    \"actions\": [\n" +
            "      \"update\",\n" +
            "      \"restart\",\n" +
            "      \"stop\",\n" +
            "      \"delete\",\n" +
            "      \"open_service\"\n" +
            "    ],\n" +
            "    \"tasks\": [\n" +
            "      {\n" +
            "        \"id\": \"hdfs-example.1863ddc3-a1b2-11ea-b6e9-70b3d580000b\",\n" +
            "        \"name\": \"hdfs-example\",\n" +
            "        \"state\": \"TASK_RUNNING\",\n" +
            "        \"healthy\": 1,\n" +
            "        \"timestamp\": 1590759720000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 0,\n" +
            "          \"mem\": 1024,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 1,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.12\",\n" +
            "        \"calicoIP\": \"172.16.202.196\",\n" +
            "        \"marathonServiceName\": \"hdfs-example\",\n" +
            "        \"logs\": null\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"055fcae9-a34e-4a23-a8e9-35d3357e2ee3\",\n" +
            "        \"name\": \"datanode-0003\",\n" +
            "        \"state\": \"TASK_RUNNING\",\n" +
            "        \"healthy\": 2,\n" +
            "        \"timestamp\": 1590760930000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 5120,\n" +
            "          \"mem\": 1024,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 1,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.9\",\n" +
            "        \"calicoIP\": \"172.16.15.8\",\n" +
            "        \"marathonServiceName\": \"hdfs-example\",\n" +
            "        \"logs\": null\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"1e651d00-bb7d-4720-bed2-f1ef7c3b7203\",\n" +
            "        \"name\": \"datanode-0002\",\n" +
            "        \"state\": \"TASK_RUNNING\",\n" +
            "        \"healthy\": 2,\n" +
            "        \"timestamp\": 1590760906000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 5120,\n" +
            "          \"mem\": 1024,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 1,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.10\",\n" +
            "        \"calicoIP\": \"172.16.125.72\",\n" +
            "        \"marathonServiceName\": \"hdfs-example\",\n" +
            "        \"logs\": null\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"59c5fea6-e253-4882-a8a5-1224c6a40d18\",\n" +
            "        \"name\": \"datanode-0001\",\n" +
            "        \"state\": \"TASK_RUNNING\",\n" +
            "        \"healthy\": 2,\n" +
            "        \"timestamp\": 1590760871000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 5120,\n" +
            "          \"mem\": 1024,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 1,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.17\",\n" +
            "        \"calicoIP\": \"172.16.126.138\",\n" +
            "        \"marathonServiceName\": \"hdfs-example\",\n" +
            "        \"logs\": null\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"b4601e4d-7ffc-4421-89f9-8126d04b869d\",\n" +
            "        \"name\": \"journalnode-0003\",\n" +
            "        \"state\": \"TASK_RUNNING\",\n" +
            "        \"healthy\": 2,\n" +
            "        \"timestamp\": 1590759795000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 2148,\n" +
            "          \"mem\": 1024,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 1,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.18\",\n" +
            "        \"calicoIP\": \"172.16.36.70\",\n" +
            "        \"marathonServiceName\": \"hdfs-example\",\n" +
            "        \"logs\": null\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"afb8345d-c5ea-4822-84d6-784bd92e0b8e\",\n" +
            "        \"name\": \"journalnode-0002\",\n" +
            "        \"state\": \"TASK_RUNNING\",\n" +
            "        \"healthy\": 2,\n" +
            "        \"timestamp\": 1590759761000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 2148,\n" +
            "          \"mem\": 1024,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 1,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.17\",\n" +
            "        \"calicoIP\": \"172.16.126.133\",\n" +
            "        \"marathonServiceName\": \"hdfs-example\",\n" +
            "        \"logs\": null\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"d25f1b02-2ae5-4fd8-8888-79b38fd6cd06\",\n" +
            "        \"name\": \"namenode-0002\",\n" +
            "        \"state\": \"TASK_RUNNING\",\n" +
            "        \"healthy\": 2,\n" +
            "        \"timestamp\": 1590760837000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 5120,\n" +
            "          \"mem\": 1024,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 2,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.18\",\n" +
            "        \"calicoIP\": \"172.16.36.76\",\n" +
            "        \"marathonServiceName\": \"hdfs-example\",\n" +
            "        \"logs\": null\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"91c3de8d-e68e-492a-a489-b95103d354e6\",\n" +
            "        \"name\": \"namenode-0001\",\n" +
            "        \"state\": \"TASK_RUNNING\",\n" +
            "        \"healthy\": 2,\n" +
            "        \"timestamp\": 1590760800000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 5120,\n" +
            "          \"mem\": 1024,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 2,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.17\",\n" +
            "        \"calicoIP\": \"172.16.126.137\",\n" +
            "        \"marathonServiceName\": \"hdfs-example\",\n" +
            "        \"logs\": null\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"e7105bcb-c29c-46f5-ba0c-dea512824231\",\n" +
            "        \"name\": \"journalnode-0001\",\n" +
            "        \"state\": \"TASK_RUNNING\",\n" +
            "        \"healthy\": 2,\n" +
            "        \"timestamp\": 1590759737000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 2148,\n" +
            "          \"mem\": 1024,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 1,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.16\",\n" +
            "        \"calicoIP\": \"172.16.137.134\",\n" +
            "        \"marathonServiceName\": \"hdfs-example\",\n" +
            "        \"logs\": null\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"57f18552-9580-45b6-b5a2-50626812e87b\",\n" +
            "        \"name\": \"namenode-0001\",\n" +
            "        \"state\": \"TASK_FAILED\",\n" +
            "        \"healthy\": 0,\n" +
            "        \"timestamp\": 1590759827000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 0,\n" +
            "          \"mem\": 0,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 0,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.17\",\n" +
            "        \"calicoIP\": \"10.200.6.17\",\n" +
            "        \"marathonServiceName\": \"hdfs-example\",\n" +
            "        \"logs\": null\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"535917b7-27bd-4e82-abef-e9666b350917\",\n" +
            "        \"name\": \"namenode-0002\",\n" +
            "        \"state\": \"TASK_FAILED\",\n" +
            "        \"healthy\": 0,\n" +
            "        \"timestamp\": 1590760756000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 0,\n" +
            "          \"mem\": 0,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 0,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.18\",\n" +
            "        \"calicoIP\": \"10.200.6.18\",\n" +
            "        \"marathonServiceName\": \"hdfs-example\",\n" +
            "        \"logs\": null\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"2ba05fb3-c42d-4b12-87fe-8031f71eaa03\",\n" +
            "        \"name\": \"namenode-0001\",\n" +
            "        \"state\": \"TASK_FAILED\",\n" +
            "        \"healthy\": 0,\n" +
            "        \"timestamp\": 1590760786000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 0,\n" +
            "          \"mem\": 0,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 0,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.17\",\n" +
            "        \"calicoIP\": \"10.200.6.17\",\n" +
            "        \"marathonServiceName\": \"hdfs-example\",\n" +
            "        \"logs\": null\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"6755f52c-0716-4de5-9a1b-e4f94288c65b\",\n" +
            "        \"name\": \"namenode-0002\",\n" +
            "        \"state\": \"TASK_FAILED\",\n" +
            "        \"healthy\": 0,\n" +
            "        \"timestamp\": 1590760793000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 0,\n" +
            "          \"mem\": 0,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 0,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.18\",\n" +
            "        \"calicoIP\": \"10.200.6.18\",\n" +
            "        \"marathonServiceName\": \"hdfs-example\",\n" +
            "        \"logs\": null\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"9bd14d4f-e12b-45e7-b05e-e057318e6f6e\",\n" +
            "        \"name\": \"namenode-0002\",\n" +
            "        \"state\": \"TASK_FAILED\",\n" +
            "        \"healthy\": 0,\n" +
            "        \"timestamp\": 1590760822000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 0,\n" +
            "          \"mem\": 0,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 0,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.18\",\n" +
            "        \"calicoIP\": \"10.200.6.18\",\n" +
            "        \"marathonServiceName\": \"hdfs-example\",\n" +
            "        \"logs\": null\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"hdfs-example.6fa29167-a128-11ea-b6e9-70b3d580000b\",\n" +
            "        \"name\": \"hdfs-example\",\n" +
            "        \"state\": \"TASK_KILLED\",\n" +
            "        \"healthy\": 0,\n" +
            "        \"timestamp\": 1590703728000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 0,\n" +
            "          \"mem\": 1024,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 1,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.13\",\n" +
            "        \"calicoIP\": \"10.200.6.13\",\n" +
            "        \"marathonServiceName\": \"hdfs-example\",\n" +
            "        \"logs\": null\n" +
            "      }\n" +
            "    ],\n" +
            "    \"totalTasks\": 15,\n" +
            "    \"totalHealthyTasks\": 14,\n" +
            "    \"external\": [\n" +
            "      {\n" +
            "        \"type\": \"adminrouter\",\n" +
            "        \"url\": \"https://bootstrap.nightlybackward.labs.stratio.com/service/hdfs-example\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"serviceName\": \"/hdfs-example\",\n" +
            "    \"service\": \"hdfs\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"model\": \"cct-deploy-api-basic\",\n" +
            "    \"version\": \"0.4.22\",\n" +
            "    \"resources\": {\n" +
            "      \"disk\": 0,\n" +
            "      \"mem\": 4096,\n" +
            "      \"gpus\": 0,\n" +
            "      \"cpus\": 2,\n" +
            "      \"ports\": \"\"\n" +
            "    },\n" +
            "    \"status\": 2,\n" +
            "    \"healthy\": 1,\n" +
            "    \"actions\": [\n" +
            "      \"update\",\n" +
            "      \"restart\",\n" +
            "      \"stop\",\n" +
            "      \"upgrade\",\n" +
            "      \"delete\",\n" +
            "      \"open_service\"\n" +
            "    ],\n" +
            "    \"tasks\": [\n" +
            "      {\n" +
            "        \"id\": \"command-center_cct-deploy-api.1192b0d7-a0a7-11ea-b6e9-70b3d580000b\",\n" +
            "        \"name\": \"cct-deploy-api.command-center\",\n" +
            "        \"state\": \"TASK_RUNNING\",\n" +
            "        \"healthy\": 1,\n" +
            "        \"timestamp\": 1590645020000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 0,\n" +
            "          \"mem\": 4096,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 2,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.13\",\n" +
            "        \"calicoIP\": \"172.16.225.5\",\n" +
            "        \"marathonServiceName\": \"cct-deploy-api.command-center\",\n" +
            "        \"logs\": null\n" +
            "      }\n" +
            "    ],\n" +
            "    \"totalTasks\": 1,\n" +
            "    \"totalHealthyTasks\": 1,\n" +
            "    \"external\": [\n" +
            "      {\n" +
            "        \"type\": \"adminrouter\",\n" +
            "        \"url\": \"https://bootstrap.nightlybackward.labs.stratio.com/service/cct-deploy-api\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"serviceName\": \"/command-center/cct-deploy-api\",\n" +
            "    \"service\": \"command-center\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"model\": \"pegaso\",\n" +
            "    \"version\": \"0.4.22\",\n" +
            "    \"resources\": {\n" +
            "      \"disk\": 2000,\n" +
            "      \"mem\": 2048,\n" +
            "      \"gpus\": 0,\n" +
            "      \"cpus\": 2,\n" +
            "      \"ports\": \"\"\n" +
            "    },\n" +
            "    \"status\": 2,\n" +
            "    \"healthy\": 1,\n" +
            "    \"actions\": [\n" +
            "      \"update\",\n" +
            "      \"restart\",\n" +
            "      \"stop\",\n" +
            "      \"delete\",\n" +
            "      \"open_service\"\n" +
            "    ],\n" +
            "    \"tasks\": [\n" +
            "      {\n" +
            "        \"id\": \"command-center_stratio-arangodb.8ff446c0-a0a5-11ea-b6e9-70b3d580000b\",\n" +
            "        \"name\": \"stratio-arangodb.command-center\",\n" +
            "        \"state\": \"TASK_RUNNING\",\n" +
            "        \"healthy\": 1,\n" +
            "        \"timestamp\": 1590644366000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 2000,\n" +
            "          \"mem\": 2048,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 2,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.16\",\n" +
            "        \"calicoIP\": \"172.16.137.131\",\n" +
            "        \"marathonServiceName\": \"stratio-arangodb.command-center\",\n" +
            "        \"logs\": null\n" +
            "      }\n" +
            "    ],\n" +
            "    \"totalTasks\": 1,\n" +
            "    \"totalHealthyTasks\": 1,\n" +
            "    \"external\": [\n" +
            "      {\n" +
            "        \"type\": \"adminrouter\",\n" +
            "        \"url\": \"https://bootstrap.nightlybackward.labs.stratio.com/service/stratio-arangodb\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"serviceName\": \"/command-center/stratio-arangodb\",\n" +
            "    \"service\": \"arango\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"model\": \"cct-ui-basic\",\n" +
            "    \"version\": \"0.4.22\",\n" +
            "    \"resources\": {\n" +
            "      \"disk\": 0,\n" +
            "      \"mem\": 256,\n" +
            "      \"gpus\": 0,\n" +
            "      \"cpus\": 0.5,\n" +
            "      \"ports\": \"\"\n" +
            "    },\n" +
            "    \"status\": 2,\n" +
            "    \"healthy\": 1,\n" +
            "    \"actions\": [\n" +
            "      \"update\",\n" +
            "      \"restart\",\n" +
            "      \"stop\",\n" +
            "      \"upgrade\",\n" +
            "      \"delete\",\n" +
            "      \"open_service\"\n" +
            "    ],\n" +
            "    \"tasks\": [\n" +
            "      {\n" +
            "        \"id\": \"command-center_cct-ui.43293d88-a0a7-11ea-b6e9-70b3d580000b\",\n" +
            "        \"name\": \"cct-ui.command-center\",\n" +
            "        \"state\": \"TASK_RUNNING\",\n" +
            "        \"healthy\": 1,\n" +
            "        \"timestamp\": 1590645089000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 0,\n" +
            "          \"mem\": 256,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 0.5,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.11\",\n" +
            "        \"calicoIP\": \"172.16.154.129\",\n" +
            "        \"marathonServiceName\": \"cct-ui.command-center\",\n" +
            "        \"logs\": null\n" +
            "      }\n" +
            "    ],\n" +
            "    \"totalTasks\": 1,\n" +
            "    \"totalHealthyTasks\": 1,\n" +
            "    \"external\": [\n" +
            "      {\n" +
            "        \"type\": \"adminrouter\",\n" +
            "        \"url\": \"https://bootstrap.nightlybackward.labs.stratio.com/service/cct-ui\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"serviceName\": \"/command-center/cct-ui\",\n" +
            "    \"service\": \"command-center\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"model\": \"basic\",\n" +
            "    \"version\": \"0.4.22\",\n" +
            "    \"resources\": {\n" +
            "      \"disk\": 0,\n" +
            "      \"mem\": 2048,\n" +
            "      \"gpus\": 0,\n" +
            "      \"cpus\": 2,\n" +
            "      \"ports\": \"\"\n" +
            "    },\n" +
            "    \"status\": 2,\n" +
            "    \"healthy\": 1,\n" +
            "    \"actions\": [\n" +
            "      \"update\",\n" +
            "      \"restart\",\n" +
            "      \"stop\",\n" +
            "      \"upgrade\",\n" +
            "      \"delete\"\n" +
            "    ],\n" +
            "    \"tasks\": [\n" +
            "      {\n" +
            "        \"id\": \"poolpostgreseos.2fda1d43-a0a6-11ea-b6e9-70b3d580000b\",\n" +
            "        \"name\": \"poolpostgreseos\",\n" +
            "        \"state\": \"TASK_RUNNING\",\n" +
            "        \"healthy\": 1,\n" +
            "        \"timestamp\": 1590644641000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 0,\n" +
            "          \"mem\": 1024,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 1,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.11\",\n" +
            "        \"calicoIP\": \"172.16.154.128\",\n" +
            "        \"marathonServiceName\": \"poolpostgreseos\",\n" +
            "        \"logs\": null\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"poolpostgreseos.2fd206f2-a0a6-11ea-b6e9-70b3d580000b\",\n" +
            "        \"name\": \"poolpostgreseos\",\n" +
            "        \"state\": \"TASK_RUNNING\",\n" +
            "        \"healthy\": 1,\n" +
            "        \"timestamp\": 1590644641000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 0,\n" +
            "          \"mem\": 1024,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 1,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.13\",\n" +
            "        \"calicoIP\": \"172.16.225.1\",\n" +
            "        \"marathonServiceName\": \"poolpostgreseos\",\n" +
            "        \"logs\": null\n" +
            "      }\n" +
            "    ],\n" +
            "    \"totalTasks\": 2,\n" +
            "    \"totalHealthyTasks\": 2,\n" +
            "    \"external\": [],\n" +
            "    \"serviceName\": \"/poolpostgreseos\",\n" +
            "    \"service\": \"pgbouncer\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"model\": \"cct-configuration-api-basic\",\n" +
            "    \"version\": \"0.4.22\",\n" +
            "    \"resources\": {\n" +
            "      \"disk\": 0,\n" +
            "      \"mem\": 512,\n" +
            "      \"gpus\": 0,\n" +
            "      \"cpus\": 1,\n" +
            "      \"ports\": \"\"\n" +
            "    },\n" +
            "    \"status\": 2,\n" +
            "    \"healthy\": 1,\n" +
            "    \"actions\": [\n" +
            "      \"update\",\n" +
            "      \"restart\",\n" +
            "      \"stop\",\n" +
            "      \"upgrade\",\n" +
            "      \"delete\",\n" +
            "      \"open_service\"\n" +
            "    ],\n" +
            "    \"tasks\": [\n" +
            "      {\n" +
            "        \"id\": \"command-center_cct-configuration-api.d3e31096-a0a6-11ea-b6e9-70b3d580000b\",\n" +
            "        \"name\": \"cct-configuration-api.command-center\",\n" +
            "        \"state\": \"TASK_RUNNING\",\n" +
            "        \"healthy\": 1,\n" +
            "        \"timestamp\": 1590644911000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 0,\n" +
            "          \"mem\": 512,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 1,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.13\",\n" +
            "        \"calicoIP\": \"172.16.225.4\",\n" +
            "        \"marathonServiceName\": \"cct-configuration-api.command-center\",\n" +
            "        \"logs\": null\n" +
            "      }\n" +
            "    ],\n" +
            "    \"totalTasks\": 1,\n" +
            "    \"totalHealthyTasks\": 1,\n" +
            "    \"external\": [\n" +
            "      {\n" +
            "        \"type\": \"adminrouter\",\n" +
            "        \"url\": \"https://bootstrap.nightlybackward.labs.stratio.com/service/cct-configuration-api\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"serviceName\": \"/command-center/cct-configuration-api\",\n" +
            "    \"service\": \"command-center\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"model\": \"pegaso\",\n" +
            "    \"version\": \"0.4.22\",\n" +
            "    \"resources\": {\n" +
            "      \"disk\": 0,\n" +
            "      \"mem\": 2048,\n" +
            "      \"gpus\": 0,\n" +
            "      \"cpus\": 2,\n" +
            "      \"ports\": \"\"\n" +
            "    },\n" +
            "    \"status\": 2,\n" +
            "    \"healthy\": 1,\n" +
            "    \"actions\": [\n" +
            "      \"update\",\n" +
            "      \"restart\",\n" +
            "      \"stop\",\n" +
            "      \"delete\",\n" +
            "      \"open_service\"\n" +
            "    ],\n" +
            "    \"tasks\": [\n" +
            "      {\n" +
            "        \"id\": \"gosecmanagement.4db92aed-a0a5-11ea-b6e9-70b3d580000b\",\n" +
            "        \"name\": \"gosecmanagement\",\n" +
            "        \"state\": \"TASK_RUNNING\",\n" +
            "        \"healthy\": 1,\n" +
            "        \"timestamp\": 1590644272000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 0,\n" +
            "          \"mem\": 2048,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 2,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.10\",\n" +
            "        \"calicoIP\": \"172.16.125.64\",\n" +
            "        \"marathonServiceName\": \"gosecmanagement\",\n" +
            "        \"logs\": null\n" +
            "      }\n" +
            "    ],\n" +
            "    \"totalTasks\": 1,\n" +
            "    \"totalHealthyTasks\": 1,\n" +
            "    \"external\": [\n" +
            "      {\n" +
            "        \"type\": \"adminrouter\",\n" +
            "        \"url\": \"https://bootstrap.nightlybackward.labs.stratio.com/service/gosecmanagement\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"serviceName\": \"/gosecmanagement\",\n" +
            "    \"service\": \"management\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"model\": \"pegaso\",\n" +
            "    \"version\": \"0.4.22\",\n" +
            "    \"resources\": {\n" +
            "      \"disk\": 0,\n" +
            "      \"mem\": 1024,\n" +
            "      \"gpus\": 0,\n" +
            "      \"cpus\": 2,\n" +
            "      \"ports\": \"\"\n" +
            "    },\n" +
            "    \"status\": 2,\n" +
            "    \"healthy\": 1,\n" +
            "    \"actions\": [\n" +
            "      \"update\",\n" +
            "      \"restart\",\n" +
            "      \"stop\",\n" +
            "      \"delete\"\n" +
            "    ],\n" +
            "    \"tasks\": [\n" +
            "      {\n" +
            "        \"id\": \"dyplon-http.fb859e7c-a0a4-11ea-b6e9-70b3d580000b\",\n" +
            "        \"name\": \"dyplon-http\",\n" +
            "        \"state\": \"TASK_RUNNING\",\n" +
            "        \"healthy\": 1,\n" +
            "        \"timestamp\": 1590644129000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 0,\n" +
            "          \"mem\": 1024,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 2,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.16\",\n" +
            "        \"calicoIP\": \"172.16.137.130\",\n" +
            "        \"marathonServiceName\": \"dyplon-http\",\n" +
            "        \"logs\": null\n" +
            "      }\n" +
            "    ],\n" +
            "    \"totalTasks\": 1,\n" +
            "    \"totalHealthyTasks\": 1,\n" +
            "    \"external\": [],\n" +
            "    \"serviceName\": \"/dyplon-http\",\n" +
            "    \"service\": \"dyplon-http\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"model\": \"cct-universe-basic\",\n" +
            "    \"version\": \"0.4.22\",\n" +
            "    \"resources\": {\n" +
            "      \"disk\": 0,\n" +
            "      \"mem\": 1024,\n" +
            "      \"gpus\": 0,\n" +
            "      \"cpus\": 1,\n" +
            "      \"ports\": \"\"\n" +
            "    },\n" +
            "    \"status\": 2,\n" +
            "    \"healthy\": 1,\n" +
            "    \"actions\": [\n" +
            "      \"update\",\n" +
            "      \"restart\",\n" +
            "      \"stop\",\n" +
            "      \"upgrade\",\n" +
            "      \"delete\",\n" +
            "      \"open_service\"\n" +
            "    ],\n" +
            "    \"tasks\": [\n" +
            "      {\n" +
            "        \"id\": \"command-center_cct-universe.9adef705-a0a6-11ea-b6e9-70b3d580000b\",\n" +
            "        \"name\": \"cct-universe.command-center\",\n" +
            "        \"state\": \"TASK_RUNNING\",\n" +
            "        \"healthy\": 1,\n" +
            "        \"timestamp\": 1590644815000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 0,\n" +
            "          \"mem\": 1024,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 1,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.13\",\n" +
            "        \"calicoIP\": \"172.16.225.3\",\n" +
            "        \"marathonServiceName\": \"cct-universe.command-center\",\n" +
            "        \"logs\": null\n" +
            "      }\n" +
            "    ],\n" +
            "    \"totalTasks\": 1,\n" +
            "    \"totalHealthyTasks\": 1,\n" +
            "    \"external\": [\n" +
            "      {\n" +
            "        \"type\": \"adminrouter\",\n" +
            "        \"url\": \"https://bootstrap.nightlybackward.labs.stratio.com/service/cct-universe/swagger-ui.html\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"serviceName\": \"/command-center/cct-universe\",\n" +
            "    \"service\": \"command-center\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"model\": \"pegaso\",\n" +
            "    \"version\": \"0.4.22\",\n" +
            "    \"resources\": {\n" +
            "      \"disk\": 36864,\n" +
            "      \"mem\": 13312,\n" +
            "      \"gpus\": 0,\n" +
            "      \"cpus\": 7,\n" +
            "      \"ports\": \"\"\n" +
            "    },\n" +
            "    \"status\": 2,\n" +
            "    \"healthy\": 1,\n" +
            "    \"actions\": [\n" +
            "      \"update\",\n" +
            "      \"restart\",\n" +
            "      \"stop\",\n" +
            "      \"upgrade\",\n" +
            "      \"delete\",\n" +
            "      \"open_service\"\n" +
            "    ],\n" +
            "    \"tasks\": [\n" +
            "      {\n" +
            "        \"id\": \"postgreseos.2f64d81b-a0a4-11ea-b6e9-70b3d580000b\",\n" +
            "        \"name\": \"postgreseos\",\n" +
            "        \"state\": \"TASK_RUNNING\",\n" +
            "        \"healthy\": 1,\n" +
            "        \"timestamp\": 1590643792000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 0,\n" +
            "          \"mem\": 1024,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 1,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.13\",\n" +
            "        \"calicoIP\": \"172.16.225.0\",\n" +
            "        \"marathonServiceName\": \"postgreseos\",\n" +
            "        \"logs\": null\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"2450415d-27a5-43ed-a319-01ce01ed5618\",\n" +
            "        \"name\": \"pg-0003\",\n" +
            "        \"state\": \"TASK_RUNNING\",\n" +
            "        \"healthy\": 2,\n" +
            "        \"timestamp\": 1590644058000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 12288,\n" +
            "          \"mem\": 4096,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 2,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.18\",\n" +
            "        \"calicoIP\": \"172.16.36.65\",\n" +
            "        \"marathonServiceName\": \"postgreseos\",\n" +
            "        \"logs\": null\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"856a1fb2-bf10-49e9-b687-c2273c3f8923\",\n" +
            "        \"name\": \"pg-0002\",\n" +
            "        \"state\": \"TASK_RUNNING\",\n" +
            "        \"healthy\": 2,\n" +
            "        \"timestamp\": 1590643967000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 12288,\n" +
            "          \"mem\": 4096,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 2,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.17\",\n" +
            "        \"calicoIP\": \"172.16.126.128\",\n" +
            "        \"marathonServiceName\": \"postgreseos\",\n" +
            "        \"logs\": null\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"e18ea8de-84df-4aab-a686-245c5978cf0e\",\n" +
            "        \"name\": \"pg-0001\",\n" +
            "        \"state\": \"TASK_RUNNING\",\n" +
            "        \"healthy\": 2,\n" +
            "        \"timestamp\": 1590643876000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 12288,\n" +
            "          \"mem\": 4096,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 2,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.16\",\n" +
            "        \"calicoIP\": \"172.16.137.129\",\n" +
            "        \"marathonServiceName\": \"postgreseos\",\n" +
            "        \"logs\": null\n" +
            "      }\n" +
            "    ],\n" +
            "    \"totalTasks\": 4,\n" +
            "    \"totalHealthyTasks\": 4,\n" +
            "    \"external\": [\n" +
            "      {\n" +
            "        \"type\": \"adminrouter\",\n" +
            "        \"url\": \"https://bootstrap.nightlybackward.labs.stratio.com/service/postgreseos\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"serviceName\": \"/postgreseos\",\n" +
            "    \"service\": \"postgres\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"model\": \"compatible\",\n" +
            "    \"version\": \"0.4.23-SNAPSHOT\",\n" +
            "    \"resources\": {\n" +
            "      \"disk\": 0,\n" +
            "      \"mem\": 4096,\n" +
            "      \"gpus\": 0,\n" +
            "      \"cpus\": 3,\n" +
            "      \"ports\": \"\"\n" +
            "    },\n" +
            "    \"status\": 2,\n" +
            "    \"healthy\": 1,\n" +
            "    \"actions\": [\n" +
            "      \"update\",\n" +
            "      \"restart\",\n" +
            "      \"stop\",\n" +
            "      \"delete\"\n" +
            "    ],\n" +
            "    \"tasks\": [\n" +
            "      {\n" +
            "        \"id\": \"rocket_rocket-test_rocket-test.8b0cb238-a1bc-11ea-b6e9-70b3d580000b\",\n" +
            "        \"name\": \"rocket-test.rocket-test.rocket\",\n" +
            "        \"state\": \"TASK_RUNNING\",\n" +
            "        \"healthy\": 1,\n" +
            "        \"timestamp\": 1590764175000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 0,\n" +
            "          \"mem\": 4096,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 3,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.12\",\n" +
            "        \"calicoIP\": \"172.16.202.200\",\n" +
            "        \"marathonServiceName\": \"rocket-test.rocket-test.rocket\",\n" +
            "        \"logs\": null\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"rocket_rocket-test_rocket-test.b9a8e016-a1bb-11ea-b6e9-70b3d580000b\",\n" +
            "        \"name\": \"rocket-test.rocket-test.rocket\",\n" +
            "        \"state\": \"TASK_KILLED\",\n" +
            "        \"healthy\": 0,\n" +
            "        \"timestamp\": 1590764174000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 0,\n" +
            "          \"mem\": 4096,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 3,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.15\",\n" +
            "        \"calicoIP\": \"10.200.6.15\",\n" +
            "        \"marathonServiceName\": \"rocket-test.rocket-test.rocket\",\n" +
            "        \"logs\": null\n" +
            "      }\n" +
            "    ],\n" +
            "    \"totalTasks\": 2,\n" +
            "    \"totalHealthyTasks\": 1,\n" +
            "    \"external\": [],\n" +
            "    \"serviceName\": \"/rocket/rocket-test/rocket-test\",\n" +
            "    \"service\": \"rocket\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"model\": \"cct-marathon-services-basic\",\n" +
            "    \"version\": \"0.4.22\",\n" +
            "    \"resources\": {\n" +
            "      \"disk\": 0,\n" +
            "      \"mem\": 512,\n" +
            "      \"gpus\": 0,\n" +
            "      \"cpus\": 0.5,\n" +
            "      \"ports\": \"\"\n" +
            "    },\n" +
            "    \"status\": 2,\n" +
            "    \"healthy\": 1,\n" +
            "    \"actions\": [\n" +
            "      \"update\",\n" +
            "      \"restart\",\n" +
            "      \"stop\",\n" +
            "      \"upgrade\",\n" +
            "      \"delete\",\n" +
            "      \"open_service\"\n" +
            "    ],\n" +
            "    \"tasks\": [\n" +
            "      {\n" +
            "        \"id\": \"command-center_cct-marathon-services.6c2f84b4-a0a6-11ea-b6e9-70b3d580000b\",\n" +
            "        \"name\": \"cct-marathon-services.command-center\",\n" +
            "        \"state\": \"TASK_RUNNING\",\n" +
            "        \"healthy\": 1,\n" +
            "        \"timestamp\": 1590644739000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 0,\n" +
            "          \"mem\": 512,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 0.5,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.13\",\n" +
            "        \"calicoIP\": \"172.16.225.2\",\n" +
            "        \"marathonServiceName\": \"cct-marathon-services.command-center\",\n" +
            "        \"logs\": null\n" +
            "      }\n" +
            "    ],\n" +
            "    \"totalTasks\": 1,\n" +
            "    \"totalHealthyTasks\": 1,\n" +
            "    \"external\": [\n" +
            "      {\n" +
            "        \"type\": \"adminrouter\",\n" +
            "        \"url\": \"https://bootstrap.nightlybackward.labs.stratio.com/service/cct-marathon-services/swagger-ui.html\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"serviceName\": \"/command-center/cct-marathon-services\",\n" +
            "    \"service\": \"command-center\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"model\": \"pegaso\",\n" +
            "    \"version\": \"0.4.22\",\n" +
            "    \"resources\": {\n" +
            "      \"disk\": 0,\n" +
            "      \"mem\": 1024,\n" +
            "      \"gpus\": 0,\n" +
            "      \"cpus\": 2,\n" +
            "      \"ports\": \"\"\n" +
            "    },\n" +
            "    \"status\": 2,\n" +
            "    \"healthy\": 1,\n" +
            "    \"actions\": [\n" +
            "      \"update\",\n" +
            "      \"restart\",\n" +
            "      \"stop\",\n" +
            "      \"delete\"\n" +
            "    ],\n" +
            "    \"tasks\": [\n" +
            "      {\n" +
            "        \"id\": \"marathonlb.bba8efa1-a0a5-11ea-b6e9-70b3d580000b\",\n" +
            "        \"name\": \"marathonlb\",\n" +
            "        \"state\": \"TASK_RUNNING\",\n" +
            "        \"healthy\": 1,\n" +
            "        \"timestamp\": 1590644435000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 0,\n" +
            "          \"mem\": 1024,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 2,\n" +
            "          \"ports\": \"[80-80, 443-443, 9090-9091, 10000-10100]\"\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.19\",\n" +
            "        \"calicoIP\": \"10.200.6.19\",\n" +
            "        \"marathonServiceName\": \"marathonlb\",\n" +
            "        \"logs\": null\n" +
            "      }\n" +
            "    ],\n" +
            "    \"totalTasks\": 1,\n" +
            "    \"totalHealthyTasks\": 1,\n" +
            "    \"external\": [],\n" +
            "    \"serviceName\": \"/marathonlb\",\n" +
            "    \"service\": \"marathon-lb\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"model\": \"pegaso\",\n" +
            "    \"version\": \"0.4.22\",\n" +
            "    \"resources\": {\n" +
            "      \"disk\": 3072,\n" +
            "      \"mem\": 13312,\n" +
            "      \"gpus\": 0,\n" +
            "      \"cpus\": 7,\n" +
            "      \"ports\": \"\"\n" +
            "    },\n" +
            "    \"status\": 2,\n" +
            "    \"healthy\": 1,\n" +
            "    \"actions\": [\n" +
            "      \"update\",\n" +
            "      \"restart\",\n" +
            "      \"stop\",\n" +
            "      \"delete\",\n" +
            "      \"open_service\"\n" +
            "    ],\n" +
            "    \"tasks\": [\n" +
            "      {\n" +
            "        \"id\": \"zkuserland.7aa585b5-a1b2-11ea-b6e9-70b3d580000b\",\n" +
            "        \"name\": \"zkuserland\",\n" +
            "        \"state\": \"TASK_RUNNING\",\n" +
            "        \"healthy\": 1,\n" +
            "        \"timestamp\": 1590759879000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 0,\n" +
            "          \"mem\": 1024,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 1,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.11\",\n" +
            "        \"calicoIP\": \"172.16.154.137\",\n" +
            "        \"marathonServiceName\": \"zkuserland\",\n" +
            "        \"logs\": null\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"3fc799f7-0bf7-4859-98c8-edd249513de5\",\n" +
            "        \"name\": \"zk-0003\",\n" +
            "        \"state\": \"TASK_RUNNING\",\n" +
            "        \"healthy\": 2,\n" +
            "        \"timestamp\": 1590760055000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 1024,\n" +
            "          \"mem\": 4096,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 2,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.11\",\n" +
            "        \"calicoIP\": \"172.16.154.138\",\n" +
            "        \"marathonServiceName\": \"zkuserland\",\n" +
            "        \"logs\": null\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"297baed4-43c6-4f7b-9a94-1accb21ecb9d\",\n" +
            "        \"name\": \"zk-0002\",\n" +
            "        \"state\": \"TASK_RUNNING\",\n" +
            "        \"healthy\": 2,\n" +
            "        \"timestamp\": 1590759974000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 1024,\n" +
            "          \"mem\": 4096,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 2,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.9\",\n" +
            "        \"calicoIP\": \"172.16.15.6\",\n" +
            "        \"marathonServiceName\": \"zkuserland\",\n" +
            "        \"logs\": null\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"bf86ff06-b99b-420b-92b8-0ddfc23f3756\",\n" +
            "        \"name\": \"zk-0001\",\n" +
            "        \"state\": \"TASK_RUNNING\",\n" +
            "        \"healthy\": 2,\n" +
            "        \"timestamp\": 1590759960000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 1024,\n" +
            "          \"mem\": 4096,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 2,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.10\",\n" +
            "        \"calicoIP\": \"172.16.125.69\",\n" +
            "        \"marathonServiceName\": \"zkuserland\",\n" +
            "        \"logs\": null\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"zkuserland.bddc5af9-a128-11ea-b6e9-70b3d580000b\",\n" +
            "        \"name\": \"zkuserland\",\n" +
            "        \"state\": \"TASK_KILLED\",\n" +
            "        \"healthy\": 0,\n" +
            "        \"timestamp\": 1590703716000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 0,\n" +
            "          \"mem\": 1024,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 1,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.15\",\n" +
            "        \"calicoIP\": \"10.200.6.15\",\n" +
            "        \"marathonServiceName\": \"zkuserland\",\n" +
            "        \"logs\": null\n" +
            "      }\n" +
            "    ],\n" +
            "    \"totalTasks\": 5,\n" +
            "    \"totalHealthyTasks\": 4,\n" +
            "    \"external\": [\n" +
            "      {\n" +
            "        \"type\": \"adminrouter\",\n" +
            "        \"url\": \"https://bootstrap.nightlybackward.labs.stratio.com/service/zkuserland\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"serviceName\": \"/zkuserland\",\n" +
            "    \"service\": \"zookeeper\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"model\": \"compatible\",\n" +
            "    \"version\": \"0.4.23-SNAPSHOT\",\n" +
            "    \"resources\": {\n" +
            "      \"disk\": 0,\n" +
            "      \"mem\": 4096,\n" +
            "      \"gpus\": 0,\n" +
            "      \"cpus\": 3,\n" +
            "      \"ports\": \"\"\n" +
            "    },\n" +
            "    \"status\": 2,\n" +
            "    \"healthy\": 1,\n" +
            "    \"actions\": [\n" +
            "      \"update\",\n" +
            "      \"restart\",\n" +
            "      \"stop\",\n" +
            "      \"delete\"\n" +
            "    ],\n" +
            "    \"tasks\": [\n" +
            "      {\n" +
            "        \"id\": \"rocket_rocket-carlos_rocket-carlos.a5d574a1-a1ba-11ea-b6e9-70b3d580000b\",\n" +
            "        \"name\": \"rocket-carlos.rocket-carlos.rocket\",\n" +
            "        \"state\": \"TASK_RUNNING\",\n" +
            "        \"healthy\": 1,\n" +
            "        \"timestamp\": 1590763361000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 0,\n" +
            "          \"mem\": 4096,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 3,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.15\",\n" +
            "        \"calicoIP\": \"172.16.129.132\",\n" +
            "        \"marathonServiceName\": \"rocket-carlos.rocket-carlos.rocket\",\n" +
            "        \"logs\": null\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"rocket_rocket-carlos_rocket-carlos.b84ddd86-a1b3-11ea-b6e9-70b3d580000b\",\n" +
            "        \"name\": \"rocket-carlos.rocket-carlos.rocket\",\n" +
            "        \"state\": \"TASK_KILLED\",\n" +
            "        \"healthy\": 0,\n" +
            "        \"timestamp\": 1590762059000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 0,\n" +
            "          \"mem\": 4096,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 3,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.12\",\n" +
            "        \"calicoIP\": \"10.200.6.12\",\n" +
            "        \"marathonServiceName\": \"rocket-carlos.rocket-carlos.rocket\",\n" +
            "        \"logs\": null\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"rocket_rocket-carlos_rocket-carlos.c46d0ecb-a1b7-11ea-b6e9-70b3d580000b\",\n" +
            "        \"name\": \"rocket-carlos.rocket-carlos.rocket\",\n" +
            "        \"state\": \"TASK_KILLED\",\n" +
            "        \"healthy\": 0,\n" +
            "        \"timestamp\": 1590763311000,\n" +
            "        \"resources\": {\n" +
            "          \"disk\": 0,\n" +
            "          \"mem\": 4096,\n" +
            "          \"gpus\": 0,\n" +
            "          \"cpus\": 3,\n" +
            "          \"ports\": null\n" +
            "        },\n" +
            "        \"host\": \"10.200.6.15\",\n" +
            "        \"calicoIP\": \"10.200.6.15\",\n" +
            "        \"marathonServiceName\": \"rocket-carlos.rocket-carlos.rocket\",\n" +
            "        \"logs\": null\n" +
            "      }\n" +
            "    ],\n" +
            "    \"totalTasks\": 3,\n" +
            "    \"totalHealthyTasks\": 1,\n" +
            "    \"external\": [],\n" +
            "    \"serviceName\": \"/rocket/rocket-carlos/rocket-carlos\",\n" +
            "    \"service\": \"rocket\"\n" +
            "  }\n" +
            "]";

}
