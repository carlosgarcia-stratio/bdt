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

package com.stratio.qa.utils.vaultansible;

import com.stratio.qa.utils.vaultansible.decoders.CypherFactory;
import com.stratio.qa.utils.vaultansible.decoders.CypherInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VaultAnsibleInfo {

    private Logger logger = LoggerFactory.getLogger(VaultAnsibleInfo.class);

    private final static String INFO_SEPARATOR = ";";

    private final static int INFO_ELEMENTS = 3;

    private final static int MAGIC_PART = 0;

    private final static int CYPHER_PART = 2;

    private final static String VAULT_MAGIC = "$ANSIBLE_VAULT";

    private final static String VAULT_VERSION = "1.1";

    private boolean validVault;

    private String vaultCypher;

    public static String vaultInfoForCypher(String vaultCypher) {
        return VAULT_MAGIC + ";" + VAULT_VERSION + ";" + vaultCypher;
    }

    VaultAnsibleInfo(String infoLine) {
        logger.debug("Ansible Vault info: {}", infoLine);

        String[] infoParts = infoLine.split(INFO_SEPARATOR);
        if (infoParts.length == INFO_ELEMENTS) {
            if (infoParts[MAGIC_PART].equals(VAULT_MAGIC)) {
                validVault = true;
                vaultCypher = infoParts[CYPHER_PART];
            }
        }
    }

    boolean isEncryptedVault() {
        return validVault;
    }

    CypherInterface getCypher() {
        return CypherFactory.getCypher(vaultCypher);
    }

    boolean isValidVault() {
        return isEncryptedVault() && getCypher() != null;
    }
}
