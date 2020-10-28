
package com.stratio.qa.utils.vaultansible.decoders;

import de.rtner.security.auth.spi.PBKDF2Engine;
import de.rtner.security.auth.spi.PBKDF2Parameters;

import java.io.IOException;
import java.util.Arrays;

public class EncryptionKeychain {

    private static final String CHAR_ENCODING = "UTF-8";

    private final String password;

    private final byte[] salt;

    private final int keylen;

    private final int ivlen;

    private final int iterations;

    private final String algo;


    private byte[] encryptionKey;

    private byte[] hmacKey;

    private byte[] iv;

    EncryptionKeychain(byte[] salt, String password, int keylen, int ivlen, int iterations, String algo) {
        this.password = password;
        this.salt = salt;
        this.keylen = keylen;
        this.ivlen = ivlen;
        this.iterations = iterations;
        this.algo = algo;
    }

    private byte[] createRawKey() throws IOException {
        try {
            PBKDF2Parameters params = new PBKDF2Parameters(algo, CHAR_ENCODING, salt, iterations);
            int keylength = ivlen + 2 * keylen;
            PBKDF2Engine pbkdf2Engine = new PBKDF2Engine(params);
            return pbkdf2Engine.deriveKey(password, keylength);
        } catch (Exception ex) {
            throw new IOException("Cryptofailure: " + ex.getMessage());
        }

    }

    void createKeys() throws IOException {
        byte[] rawkeys = createRawKey();
        this.encryptionKey = getEncryptionKey(rawkeys);
        this.hmacKey = getHMACKey(rawkeys);
        this.iv = getIV(rawkeys);
    }

    private byte[] getEncryptionKey(byte[] keys) {
        return Arrays.copyOfRange(keys, 0, keylen);
    }

    private byte[] getHMACKey(byte[] keys) {
        return Arrays.copyOfRange(keys, keylen, keylen * 2);
    }

    private byte[] getIV(byte[] keys) {
        return Arrays.copyOfRange(keys, keylen * 2, keylen * 2 + ivlen);
    }

    byte[] getEncryptionKey() {
        return encryptionKey;
    }

    byte[] getHmacKey() {
        return hmacKey;
    }

    byte[] getIv() {
        return iv;
    }
}
