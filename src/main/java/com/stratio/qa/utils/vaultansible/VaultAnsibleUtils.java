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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;

public class VaultAnsibleUtils {

    Logger logger = LoggerFactory.getLogger(VaultAnsibleUtils.class);

    private static final int DEFAULT_LINE_LENGTH = 80;

    final protected static String LINE_BREAK = "\n";

    private static String join(String[] datalines) {
        return String.join("", Arrays.asList(datalines));
    }

    static byte[] unhex(String hexed) {
        int dataLen = hexed.length();
        byte[] output = new byte[dataLen / 2];
        for (int charIdx = 0; charIdx < dataLen; charIdx += 2) {
            output[charIdx / 2] = (byte) ((Character.digit(hexed.charAt(charIdx), 16) << 4)
                    + Character.digit(hexed.charAt(charIdx + 1), 16));
        }
        return output;
    }

    static String hexit(byte[] unhexed) {
        return hexit(unhexed, DEFAULT_LINE_LENGTH);
    }

    public static String hexit(byte[] unhexed, int lineLength) {
        String result = "";
        int colIdx = 0;
        for (byte val: unhexed) {
            result += String.format("%02x", val);
            colIdx++;
            if (lineLength > 0 && colIdx >= lineLength / 2) {
                result += LINE_BREAK;
                colIdx = 0;
            }
        }

        return result;
    }

    private static VaultAnsibleInfo getVaultInfo(String vaultData) {
        String infoString =  vaultData.substring(0, vaultData.indexOf(LINE_BREAK));
        return new VaultAnsibleInfo(infoString);
    }

    static VaultAnsibleInfo getVaultInfo(byte[] vaultData) {
        return getVaultInfo(new String(vaultData));
    }

    private static String cleanupData(String vaultData) {
        return vaultData.substring(vaultData.indexOf(LINE_BREAK) + 1);
    }

    static byte[] getVaultData(byte[] vaultData) {
        String rawData = join(cleanupData(new String(vaultData)).split(LINE_BREAK));
        return unhex(rawData);
    }
}
