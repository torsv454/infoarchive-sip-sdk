/*
 * Copyright (c) 2016 EMC Corporation. All Rights Reserved.
 */
package com.emc.ia.sdk.sip.ingestion.dto;


public class Sip {

  private String format;
  private String extractorImpl;

  public Sip() {
    setFormat("sip_zip");
    setExtractorImpl("com.emc.ia.reception.sip.extractor.impl.ZipSipExtractor");
  }

  public String getFormat() {
    return format;
  }

  public final void setFormat(String format) {
    this.format = format;
  }

  public String getExtractorImpl() {
    return extractorImpl;
  }

  public final void setExtractorImpl(String extractorImpl) {
    this.extractorImpl = extractorImpl;
  }

}
