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

package com.stratio.qa.utils.vaultansible.decoders;

import com.stratio.qa.utils.vaultansible.VaultAnsibleContent;
import com.stratio.qa.utils.vaultansible.VaultAnsibleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class CypherAES256 implements CypherInterface {

    Logger logger = LoggerFactory.getLogger(CypherAES256.class);

    final static String CYPHER_ID = "AES256";

    private final static int AES_KEYLEN = 256;

    private final static String CHAR_ENCODING = "UTF-8";

    private final static String KEYGEN_ALGO = "HmacSHA256";

    private final static String CYPHER_KEY_ALGO = "AES";

    private static final String CYPHER_ALGO = "AES/CTR/NoPadding";

    private final static int KEYLEN = 32;

    private final static int IVLEN = 16;

    private final static int ITERATIONS = 10000;

    private boolean hasValidAESProvider() {
        boolean canCrypt = false;
        try {
            int maxKeyLen = Cipher.getMaxAllowedKeyLength(CYPHER_ALGO);
            logger.debug("Available keylen: {}", maxKeyLen);
            if (maxKeyLen >= AES_KEYLEN) {
                canCrypt = true;
            } else {
                logger.warn("JRE doesn't support {} keylength for {}",
                        AES_KEYLEN, CYPHER_KEY_ALGO);
            }
        } catch (Exception ex) {
            logger.warn("Failed to check for proper cypher algorithms: {}", ex.getMessage());
        }
        return canCrypt;
    }

    private byte[] calculateHMAC(byte[] key, byte[] data) throws IOException {
        byte[] computedMac;

        try {
            SecretKeySpec hmacKey = new SecretKeySpec(key, KEYGEN_ALGO);
            Mac mac = Mac.getInstance(KEYGEN_ALGO);
            mac.init(hmacKey);
            computedMac = mac.doFinal(data);
        } catch (Exception ex) {
            throw new IOException("Error decrypting HMAC hash: " + ex.getMessage());
        }
        return computedMac;
    }

    private boolean verifyHMAC(byte[] hmac, byte[] key, byte[] data) throws IOException {
        byte[] calculated = calculateHMAC(key, data);
        return Arrays.equals(hmac, calculated);
    }

    private int paddingLength(byte[] decrypted) {
        if (decrypted.length == 0) {
            logger.debug("Empty decoded text has no padding.");
            return 0;
        }

        logger.debug("Padding length: {}", decrypted[decrypted.length - 1]);
        return decrypted[decrypted.length - 1];
    }

    private byte[] unpad(byte[] decrypted) {
        int length = decrypted.length - paddingLength(decrypted);
        return Arrays.copyOfRange(decrypted, 0, length);
    }

    public byte[] pad(byte[] cleartext) throws NoSuchPaddingException, NoSuchAlgorithmException {
        byte[] padded;

        int blockSize = Cipher.getInstance(CYPHER_ALGO).getBlockSize();
        logger.debug("Padding to block size: {}", blockSize);
        int padding_length = (blockSize - (cleartext.length % blockSize));
        if (padding_length == 0) {
            padding_length = blockSize;
        }
        padded = Arrays.copyOf(cleartext, cleartext.length + padding_length);
        padded[padded.length - 1] = (byte) padding_length;

        return padded;
    }

    private byte[] decryptAES(byte[] cypher, byte[] key, byte[] iv) throws IOException {

        SecretKeySpec keySpec = new SecretKeySpec(key, CYPHER_KEY_ALGO);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        try {
            Cipher cipher = Cipher.getInstance(CYPHER_ALGO);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] decrypted = cipher.doFinal(cypher);
            return unpad(decrypted);
        } catch (Exception ex) {
            throw new IOException("Failed to decrypt data: " + ex.getMessage());
        }
    }

    public byte[] decrypt(byte[] encryptedData, String password) throws IOException {
        byte[] decrypted = null;

        if (!hasValidAESProvider()) {
            throw new IOException("Missing valid AES256 provider - install unrestricted policy profiles.");
        }

        VaultAnsibleContent vaultContent = new VaultAnsibleContent(encryptedData);

        byte[] salt = vaultContent.getSalt();
        byte[] hmac = vaultContent.getHmac();
        byte[] cypher = vaultContent.getData();
        logger.debug("Salt: {} - {}", salt.length, VaultAnsibleUtils.hexit(salt, 100));
        logger.debug("HMAC: {} - {}", hmac.length, VaultAnsibleUtils.hexit(hmac, 100));
        logger.debug("Data: {} - {}", cypher.length, VaultAnsibleUtils.hexit(cypher, 100));

        EncryptionKeychain keys = new EncryptionKeychain(salt, password, KEYLEN, IVLEN, ITERATIONS, KEYGEN_ALGO);
        keys.createKeys();

        byte[] cypherKey = keys.getEncryptionKey();
        logger.debug("Key 1: {} - {}", cypherKey.length, VaultAnsibleUtils.hexit(cypherKey, 100));
        byte[] hmacKey = keys.getHmacKey();
        logger.debug("Key 2: {} - {}", hmacKey.length, VaultAnsibleUtils.hexit(hmacKey, 100));
        byte[] iv = keys.getIv();
        logger.debug("IV: {} - {}", iv.length, VaultAnsibleUtils.hexit(iv, 100));

        if (verifyHMAC(hmac, hmacKey, cypher)) {
            logger.debug("Signature matches - decrypting");
            decrypted = decryptAES(cypher, cypherKey, iv);
            logger.debug("Decoded:\n{}", new String(decrypted, CHAR_ENCODING));
        } else {
            throw new IOException("HMAC Digest doesn't match - possibly it's the wrong password.");
        }

        return decrypted;
    }

    public void decrypt(OutputStream decodedStream, byte[] encryptedData, String password) throws IOException {
        decodedStream.write(decrypt(encryptedData, password));
    }
}
