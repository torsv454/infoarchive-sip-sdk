/*
 * Copyright (c) 2016 EMC Corporation. All Rights Reserved.
 */
package com.emc.ia.sdk.sip.ingestion.dto.result;

import java.util.ArrayList;
import java.util.List;

public class Tab {

  private String name;
  private String title;
  private String description;
  private List<Column> columns;
  private boolean exportEnabled;

  public Tab() {
    this(null);
  }

  public Tab(String name) {
    this.name = name;
    columns = new ArrayList<>();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Column> getColumns() {
    return columns;
  }

  public void setColumns(List<Column> columns) {
    this.columns = columns;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isExportEnabled() {
    return exportEnabled;
  }

  public void setExportEnabled(boolean exportEnabled) {
    this.exportEnabled = exportEnabled;
  }
}
