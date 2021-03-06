/*
 * Copyright (c) 2016 EMC Corporation. All Rights Reserved.
 */
package com.emc.ia.sdk.sip.ingestion.dto;


public class JobInstance extends NamedLinkContainer {

  private String status;

  public JobInstance() {
    setStatus("SUCCESS");
  }

  public String getStatus() {
    return status;
  }

  public final void setStatus(String status) {
    this.status = status;
  }
}
