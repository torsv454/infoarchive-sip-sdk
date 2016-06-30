/*
 * Copyright (c) 2016 EMC Corporation. All Rights Reserved.
 */
package com.emc.ia.sdk.sip.ingestion;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.junit.Before;
import org.junit.Test;

import com.emc.ia.sdk.sip.ingestion.dto.Application;
import com.emc.ia.sdk.sip.ingestion.dto.Applications;
import com.emc.ia.sdk.sip.ingestion.dto.Database;
import com.emc.ia.sdk.sip.ingestion.dto.Databases;
import com.emc.ia.sdk.sip.ingestion.dto.Federations;
import com.emc.ia.sdk.sip.ingestion.dto.FileSystemFolder;
import com.emc.ia.sdk.sip.ingestion.dto.FileSystemFolders;
import com.emc.ia.sdk.sip.ingestion.dto.FileSystemRoot;
import com.emc.ia.sdk.sip.ingestion.dto.FileSystemRoots;
import com.emc.ia.sdk.sip.ingestion.dto.Holdings;
import com.emc.ia.sdk.sip.ingestion.dto.IngestionResponse;
import com.emc.ia.sdk.sip.ingestion.dto.ReceiverNodes;
import com.emc.ia.sdk.sip.ingestion.dto.ReceptionResponse;
import com.emc.ia.sdk.sip.ingestion.dto.Services;
import com.emc.ia.sdk.sip.ingestion.dto.Space;
import com.emc.ia.sdk.sip.ingestion.dto.SpaceRootFolder;
import com.emc.ia.sdk.sip.ingestion.dto.SpaceRootFolders;
import com.emc.ia.sdk.sip.ingestion.dto.SpaceRootLibraries;
import com.emc.ia.sdk.sip.ingestion.dto.Spaces;
import com.emc.ia.sdk.sip.ingestion.dto.Store;
import com.emc.ia.sdk.sip.ingestion.dto.Stores;
import com.emc.ia.sdk.sip.ingestion.dto.Tenant;
import com.emc.ia.sdk.support.io.RuntimeIoException;
import com.emc.ia.sdk.support.rest.Link;
import com.emc.ia.sdk.support.rest.LinkContainer;
import com.emc.ia.sdk.support.rest.RestClient;


public class WhenUsingInfoArchive implements InfoArchiveLinkRelations {

  private static final String BILLBOARD_URI = "http://foo.com/bar";
  private static final String AUTH_TOKEN = "XYZ123ABC";
  private static final String APPLICATION_NAME = "ApPlIcAtIoN";
  private static final String TENANT_NAME = "TeNaNt";

  private final Map<String, Link> links = new HashMap<String, Link>();
  private final Map<String, String> configuration = new HashMap<String, String>();
  private final RestClient restClient = mock(RestClient.class);
  private final InfoArchiveRestClient archiveClient = new InfoArchiveRestClient(restClient);
  private Services resource;
  private Applications applications;
  private Application application;

  @Before
  public void init() throws IOException {
    configuration.put(InfoArchiveConfiguration.SERVER_URI, BILLBOARD_URI);
    configuration.put(InfoArchiveConfiguration.SERVER_AUTENTICATON_TOKEN, AUTH_TOKEN);
    configuration.put(InfoArchiveConfiguration.TENANT_NAME, TENANT_NAME);
    configuration.put(InfoArchiveConfiguration.DATABASE_NAME, APPLICATION_NAME);
    configuration.put(InfoArchiveConfiguration.DATABASE_ADMIN_PASSWORD, APPLICATION_NAME);
    configuration.put(InfoArchiveConfiguration.APPLICATION_NAME, APPLICATION_NAME);
    configuration.put(InfoArchiveConfiguration.HOLDING_NAME, APPLICATION_NAME);
    resource = new Services();
    Link link = mock(Link.class);
    Tenant tenant = new Tenant();
    application = new Application();
    applications = mock(Applications.class);
    Federations federations = mock(Federations.class);
    Spaces spaces = mock(Spaces.class);
    Space space = new Space();
    Databases databases = mock(Databases.class);
    Database database = new Database();
    FileSystemRoots fileSystemRoots = mock(FileSystemRoots.class);
    FileSystemRoot fileSystemRoot = new FileSystemRoot();
    when(fileSystemRoots.first()).thenReturn(fileSystemRoot);
    Holdings holdings = mock(Holdings.class);
    ReceiverNodes receiverNodes = mock(ReceiverNodes.class);
    SpaceRootLibraries libraries = mock(SpaceRootLibraries.class);
    SpaceRootFolders rootFolders = mock(SpaceRootFolders.class);
    SpaceRootFolder rootFolder = new SpaceRootFolder();
    FileSystemFolders systemFolders = mock(FileSystemFolders.class);
    FileSystemFolder systemFolder = new FileSystemFolder();
    Stores stores = mock(Stores.class);
    Store store = new Store();

    links.put(InfoArchiveLinkRelations.LINK_TENANT, link);
    links.put(InfoArchiveLinkRelations.LINK_APPLICATIONS, link);
    links.put(InfoArchiveLinkRelations.LINK_AIPS, link);
    links.put(InfoArchiveLinkRelations.LINK_INGEST, link);
    resource.setLinks(links);
    tenant.setLinks(links);
    application.setLinks(links);

    when(restClient.get(BILLBOARD_URI, Services.class)).thenReturn(resource);
    when(link.getHref()).thenReturn(BILLBOARD_URI);
    when(restClient.follow(any(LinkContainer.class), anyString(), eq(Applications.class))).thenReturn(applications);
    when(restClient.follow(any(LinkContainer.class), anyString(), eq(Federations.class))).thenReturn(federations);
    when(restClient.follow(any(LinkContainer.class), anyString(), eq(Spaces.class))).thenReturn(spaces);
    when(restClient.follow(any(LinkContainer.class), anyString(), eq(Databases.class))).thenReturn(databases);
    when(restClient.follow(any(LinkContainer.class), anyString(), eq(FileSystemRoots.class)))
        .thenReturn(fileSystemRoots);
    when(restClient.follow(any(LinkContainer.class), anyString(), eq(Holdings.class))).thenReturn(holdings);
    when(restClient.follow(any(LinkContainer.class), anyString(), eq(ReceiverNodes.class))).thenReturn(receiverNodes);
    when(restClient.follow(any(LinkContainer.class), anyString(), eq(SpaceRootLibraries.class))).thenReturn(libraries);
    when(restClient.follow(any(LinkContainer.class), anyString(), eq(SpaceRootFolders.class))).thenReturn(rootFolders);
    when(restClient.follow(any(LinkContainer.class), anyString(), eq(FileSystemFolders.class)))
        .thenReturn(systemFolders);
    when(restClient.follow(any(LinkContainer.class), anyString(), eq(Stores.class))).thenReturn(stores);

    when(applications.byName(APPLICATION_NAME)).thenReturn(application);

    when(restClient.createCollectionItem(eq(databases), eq(LINK_ADD), any(Database.class)))
        .thenReturn(database);
    when(restClient.createCollectionItem(eq(spaces), eq(LINK_ADD), any(Space.class)))
        .thenReturn(space);
    when(restClient.createCollectionItem(eq(rootFolders), eq(LINK_ADD), any(SpaceRootFolder.class))).thenReturn(rootFolder);
    when(restClient.createCollectionItem(eq(systemFolders), eq(LINK_ADD), any(FileSystemFolder.class))).thenReturn(systemFolder);
    when(restClient.createCollectionItem(eq(stores), eq(LINK_ADD), any(Store.class)))
        .thenReturn(store);
  }

  @Test
  public void shouldInitHeadersDuringObjectCreation() throws IOException {
    archiveClient.configure(configuration);

    verify(restClient).get(BILLBOARD_URI, Services.class);
    verify(restClient).follow(resource, InfoArchiveLinkRelations.LINK_TENANT, Tenant.class);
  }

  @Test (expected = RuntimeException.class)
  public void shouldThrowExceptionWileConfiguring() {
    archiveClient.configure(null);
  }

  @SuppressWarnings("unchecked")
  @Test (expected = RuntimeIoException.class)
  public void shouldWrapExceptionDuringConfiguration() throws IOException {
    when(restClient.get(BILLBOARD_URI, Services.class)).thenThrow(IOException.class);
    archiveClient.configure(configuration);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void shouldIngestSuccessfully() throws IOException {
    archiveClient.configure(configuration);

    String source = "This is the source of my input stream";
    InputStream sip = IOUtils.toInputStream(source, "UTF-8");

    ReceptionResponse receptionResponse = new ReceptionResponse();
    IngestionResponse ingestionResponse = mock(IngestionResponse.class);
    receptionResponse.setLinks(links);
    when(restClient.post(anyString(), any(HttpEntity.class), eq(ReceptionResponse.class)))
        .thenReturn(receptionResponse);
    when(restClient.put(anyString(), eq(IngestionResponse.class))).thenReturn(ingestionResponse);
    when(ingestionResponse.getAipId()).thenReturn("sip001");

    assertEquals(archiveClient.ingest(sip), "sip001");
  }

  @Test(expected = RuntimeException.class)
  public void ingestShouldThrowRuntimeExceptionWhenConfigureIsNotInvoked() throws IOException {
    String source = "This is the source of my input stream";
    InputStream sip = IOUtils.toInputStream(source, "UTF-8");
    archiveClient.ingest(sip);
  }

  @Test(expected = RuntimeException.class)
  public void ingestShouldThrowRuntimeExceptionWhenSipIsNull() throws IOException {
    archiveClient.ingest(null);
  }

  @Test(expected = NullPointerException.class)
  public void shouldThrowNullPointerExceptionWhenConfigurationParametersAreNull() throws IOException {
    Map<String, String> config = new HashMap<String, String>();
    archiveClient.configure(config);
  }

  @Test
  public void shouldCreateApplicationWhenNotFound() throws IOException {
    when(applications.byName(APPLICATION_NAME)).thenReturn(null);
    when(restClient.createCollectionItem(eq(applications), eq(LINK_ADD), any(Application.class)))
        .thenReturn(application);

    archiveClient.configure(configuration);

    verify(restClient)
        .createCollectionItem(eq(applications), eq(LINK_ADD), any(Application.class));
  }

}