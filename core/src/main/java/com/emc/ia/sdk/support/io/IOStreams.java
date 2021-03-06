/*
 * Copyright (c) 2016 EMC Corporation. All Rights Reserved.
 */
package com.emc.ia.sdk.support.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class IOStreams {

  private IOStreams() {
    throw new IllegalArgumentException(
        "IOStreams is a utility class with only static methods and should not be instantiated.");
  }

  /**
   * Utility method to copy the bytes from an InputStream to an OutputStream while also assembling a hash value in the
   * process.
   * @param in The source stream
   * @param out The target stream
   * @param bufferSize The size of the internal buffer
   * @param hashAssembler The HashAssembler to use.
   * @throws IOException if an error occurs when copying the streams
   */
  public static void copy(InputStream in, OutputStream out, int bufferSize, HashAssembler hashAssembler)
      throws IOException {
    byte[] buffer = new byte[bufferSize];
    int numRead = in.read(buffer);
    if (numRead == 0) {
      throw new IllegalArgumentException("Missing content");
    }
    while (numRead > 0) {
      out.write(buffer, 0, numRead);
      hashAssembler.add(buffer, numRead);
      numRead = in.read(buffer);
    }
  }
}
