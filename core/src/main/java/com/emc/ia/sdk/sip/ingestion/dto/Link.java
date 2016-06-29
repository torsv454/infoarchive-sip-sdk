/*
 * Copyright (c) 2016 EMC Corporation. All Rights Reserved.
 */
package com.emc.ia.sdk.sip.ingestion.dto;


public class Link {

  private String href;

  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder(255);
    builder.append("Link [href=");
    builder.append(href);
    builder.append(" ]");
    return builder.toString();
  }

}
