/*
 * Copyright (c) 2016 EMC Corporation. All Rights Reserved.
 */
package com.emc.ia.sdk.sip.ingestion;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.message.BasicHeader;
import org.junit.BeforeClass;
import org.junit.Test;

import com.emc.ia.sdk.sip.ingestion.dto.HomeResource;
import com.emc.ia.sdk.sip.ingestion.dto.ReceptionResponse;


public class WhenExecutingRestClient {

  private static final String URI = "http://identifiers.emc.com/aips";
  private static List<Header> headers = new ArrayList<Header>();

  private final HttpClient wrapper = mock(HttpClient.class);
  private final RestClient client = new RestClient(wrapper);

  @BeforeClass
  public static void initClass() {
    headers.add(new BasicHeader("AuthToken", "XYZ123ABC"));
    headers.add(new BasicHeader("Accept", "application/hal+json"));
  }

  @Test
  public void shouldExecuteGetSuccessfully() throws IOException {
    HttpGet getRequest = new HttpGet();
    HomeResource resource = new HomeResource();
    when(wrapper.httpGetRequest(URI, headers)).thenReturn(getRequest);
    when(wrapper.execute(getRequest, HomeResource.class)).thenReturn(resource);

    HomeResource homeResource = client.get(URI, headers, HomeResource.class);

    assertNotNull(homeResource);
    verify(wrapper).httpGetRequest(URI, headers);
    verify(wrapper).execute(any(HttpGet.class), eq(HomeResource.class));
  }

  @SuppressWarnings("unchecked")
  @Test(expected = IOException.class)
  public void shouldThrowExceptionWhenGetIsCalled() throws IOException {
    HttpGet getRequest = new HttpGet();
    when(wrapper.httpGetRequest(URI, headers)).thenReturn(getRequest);
    when(wrapper.execute(getRequest, HomeResource.class)).thenThrow(ClientProtocolException.class);
    client.get(URI, headers, HomeResource.class);
  }

  @SuppressWarnings("unchecked")
  @Test(expected = IOException.class)
  public void shouldThrowExceptionWhenGetIsCalled2() throws IOException {
    HttpGet getRequest = new HttpGet();
    when(wrapper.httpGetRequest(URI, headers)).thenReturn(getRequest);
    when(wrapper.execute(getRequest, HomeResource.class)).thenThrow(IOException.class);
    client.get(URI, headers, HomeResource.class);
  }

  @Test
  public void shouldExecutePutSuccessfully() throws IOException {
    HttpPut putRequest = new HttpPut();
    HomeResource resource = new HomeResource();
    when(wrapper.httpPutRequest(URI, headers)).thenReturn(putRequest);
    when(wrapper.execute(putRequest, HomeResource.class)).thenReturn(resource);

    HomeResource homeResource = client.put(URI, headers, HomeResource.class);

    assertNotNull(homeResource);
    verify(wrapper).httpPutRequest(URI, headers);
    verify(wrapper).execute(any(HttpPut.class), eq(HomeResource.class));
  }

  @SuppressWarnings("unchecked")
  @Test(expected = IOException.class)
  public void shouldThrowExceptionWhenPutIsCalled() throws IOException {
    HttpPut putRequest = new HttpPut();
    when(wrapper.httpPutRequest(URI, headers)).thenReturn(putRequest);
    when(wrapper.execute(putRequest, HomeResource.class)).thenThrow(ClientProtocolException.class);
    client.put(URI, headers, HomeResource.class);
  }

  @SuppressWarnings("unchecked")
  @Test(expected = IOException.class)
  public void shouldThrowExceptionWhenPutIsCalled2() throws IOException {
    HttpPut putRequest = new HttpPut();
    when(wrapper.httpPutRequest(URI, headers)).thenReturn(putRequest);
    when(wrapper.execute(putRequest, HomeResource.class)).thenThrow(IOException.class);
    client.put(URI, headers, HomeResource.class);
  }

  @Test
  public void shouldExecutePostSuccessfully() throws IOException {
    HttpPost postRequest = new HttpPost();
    ReceptionResponse resource = new ReceptionResponse();

    when(wrapper.httpPostRequest(URI, headers)).thenReturn(postRequest);
    when(wrapper.execute(postRequest, ReceptionResponse.class)).thenReturn(resource);

    String source = "This is the source of my input stream";
    InputStream in = IOUtils.toInputStream(source, "UTF-8");

    assertNotNull(client.post(URI, headers, "This is a test message", in, ReceptionResponse.class));

    verify(wrapper).httpPostRequest(URI, headers);
    verify(wrapper).execute(any(HttpPost.class), eq(ReceptionResponse.class));
  }

  @SuppressWarnings("unchecked")
  @Test(expected = IOException.class)
  public void shouldThrowExceptionWhenPostIsCalled() throws IOException {
    HttpPost postRequest = new HttpPost();
    String source = "This is the source of my input stream";
    InputStream in = IOUtils.toInputStream(source, "UTF-8");

    when(wrapper.httpPostRequest(URI, headers)).thenReturn(postRequest);
    when(wrapper.execute(postRequest, ReceptionResponse.class)).thenThrow(ClientProtocolException.class);
    client.post(URI, headers, "This is a test message", in, ReceptionResponse.class);
  }

  @SuppressWarnings("unchecked")
  @Test(expected = IOException.class)
  public void shouldThrowExceptionWhenPostIsCalled2() throws IOException {
    HttpPost postRequest = new HttpPost();
    String source = "This is the source of my input stream";
    InputStream in = IOUtils.toInputStream(source, "UTF-8");
    when(wrapper.httpPostRequest(URI, headers)).thenReturn(postRequest);
    when(wrapper.execute(postRequest, ReceptionResponse.class)).thenThrow(IOException.class);
    client.post(URI, headers, "This is a test message", in, ReceptionResponse.class);
}

  @Test
  public void shouldCloseHttpClientConnection() {
    client.close();

    verify(wrapper).close();
  }

}
