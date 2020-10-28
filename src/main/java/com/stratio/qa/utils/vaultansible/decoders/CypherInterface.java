package com.stratio.qa.utils.vaultansible.decoders;

import java.io.IOException;
import java.io.OutputStream;

public interface CypherInterface {

    void decrypt(OutputStream decodedStream, byte[] data, String password) throws IOException;

    byte[] decrypt(byte[] encryptedData, String password) throws IOException;
}
