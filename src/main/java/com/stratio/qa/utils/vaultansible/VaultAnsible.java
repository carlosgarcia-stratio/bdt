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

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class VaultAnsible {

    Logger logger = LoggerFactory.getLogger(VaultAnsible.class);

    public final static String CHAR_ENCODING = "UTF-8";

    public static void decrypt(InputStream encryptedVault, OutputStream decryptedVault, String password) throws IOException {
        String encryptedValue = IOUtils.toString(encryptedVault, CHAR_ENCODING);
        decryptedVault.write(decrypt(encryptedValue.getBytes(), password));
    }

    public static byte[] decrypt(byte[] encrypted, String password) throws IOException {

        VaultAnsibleInfo vaultInfo = VaultAnsibleUtils.getVaultInfo(encrypted);
        if (!vaultInfo.isEncryptedVault()) {
            throw new IOException("File is not an Ansible Encrypted Vault");
        }

        if (!vaultInfo.isValidVault()) {
            throw new IOException("The vault is not a format we can handle - check the cypher.");
        }

        byte[] encryptedData = VaultAnsibleUtils.getVaultData(encrypted);
        return vaultInfo.getCypher().decrypt(encryptedData, password);
    }
}
