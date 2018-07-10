package com.ic.invoicecapture.connection.request;

import java.net.URI;
import java.util.Map;
import java.util.TreeMap;
import org.apache.http.Header;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.ic.invoicecapture.connection.RequestType;

public class HttpUriRequestBuilderTest {

  private static final URI TEST_URI = URI.create("http://hi.hi");
  private static final String HEADER_NAME = "test-header-name";
  private static final String HEADER_NAME2 = "test-header-name2";
  private static final String HEADER_VALUE = "test-header-value";
  private static final String HEADER_VALUE2 = "test-header-value2";
//  
//  @Test
//  public void addHeader_correctness() {
//    final String KEY = "test_key";
//    final String VALUE = "test_value";
//    
//    Map<String, String> headersMock = new TreeMap<>();
//    headersMock = EasyMock.createNiceMock(TreeMap.class);
//    headersMock.put(EasyMock.anyString(), EasyMock.anyString());
//    EasyMock.expectLastCall();
//    EasyMock.replay(headersMock);
//    
//    HttpUriRequestBuilder requestBuilder = new HttpUriRequestBuilder(headersMock);
//    requestBuilder.addHeader(KEY, VALUE);
//    
//    EasyMock.verify(headersMock);
//  }
  
  
  @Test
  public void build_requestTypeNull() {
    HttpUriRequestBuilder requestBuilder = new HttpUriRequestBuilder();
    requestBuilder.setUrl(TEST_URI);
    Assertions.assertThrows(IllegalArgumentException.class, requestBuilder::build);
  }
  
  @Test
  public void build_requestUrlNull() {
    HttpUriRequestBuilder requestBuilder = new HttpUriRequestBuilder();
    requestBuilder.setRequestType(RequestType.GET);
    Assertions.assertThrows(IllegalArgumentException.class, requestBuilder::build);
  }
  
  @Test
  public void build_get() {
    HttpUriRequestBuilder requestBuilder = new HttpUriRequestBuilder();
    requestBuilder.setRequestType(RequestType.GET);
    requestBuilder.setUrl(TEST_URI);
    HttpUriRequest request = requestBuilder.build();
    Assertions.assertNotEquals(null, request);
    Assertions.assertTrue(request instanceof HttpGet);
  }
  
  @Test
  public void build_noHeaders() {
    HttpUriRequestBuilder requestBuilder = new HttpUriRequestBuilder();
    requestBuilder.setRequestType(RequestType.GET);
    requestBuilder.setUrl(TEST_URI);
    
    HttpUriRequest request = requestBuilder.build();
    Assertions.assertNotEquals(request, null);
    Header[] headers = request.getAllHeaders();
    Assertions.assertEquals(0, headers.length);
  }
  
  @Test
  public void build_oneHeader() {
    HttpUriRequestBuilder requestBuilder = new HttpUriRequestBuilder();
    requestBuilder.setRequestType(RequestType.GET);
    requestBuilder.setUrl(TEST_URI);
    requestBuilder.addHeader(HEADER_NAME, HEADER_VALUE);
    
    HttpUriRequest request = requestBuilder.build();
    Assertions.assertNotEquals(request, null);
    Header[] headers = request.getHeaders(HEADER_NAME);
    Assertions.assertEquals(1, headers.length);
    Assertions.assertEquals(HEADER_VALUE, headers[0].getValue());
  }
  
  @Test
  public void build_headers() {
    HttpUriRequestBuilder requestBuilder = new HttpUriRequestBuilder();
    requestBuilder.setRequestType(RequestType.GET);
    requestBuilder.setUrl(TEST_URI);
    requestBuilder.addHeader(HEADER_NAME, HEADER_VALUE);
    requestBuilder.addHeader(HEADER_NAME2, HEADER_VALUE2);
    
    HttpUriRequest request = requestBuilder.build();
    Assertions.assertNotEquals(request, null);
    Header[] headers = request.getHeaders(HEADER_NAME);
    Assertions.assertEquals(1, headers.length);
    Assertions.assertEquals(HEADER_VALUE, headers[0].getValue());
    
    Header[] headers2 = request.getHeaders(HEADER_NAME2);
    Assertions.assertEquals(1, headers2.length);
    Assertions.assertEquals(HEADER_VALUE2, headers2[0].getValue());
  }
  
  
  @Test
  public void build_headersDuplicateKey() {
    HttpUriRequestBuilder requestBuilder = new HttpUriRequestBuilder();
    requestBuilder.setRequestType(RequestType.GET);
    requestBuilder.setUrl(TEST_URI);
    requestBuilder.addHeader(HEADER_NAME, HEADER_VALUE);
    requestBuilder.addHeader(HEADER_NAME, HEADER_VALUE2);
    
    HttpUriRequest request = requestBuilder.build();
    Assertions.assertNotEquals(request, null);
    Header[] headers = request.getHeaders(HEADER_NAME);
    Assertions.assertEquals(1, headers.length);
    Assertions.assertEquals(HEADER_VALUE2, headers[0].getValue());
  }
  
}
