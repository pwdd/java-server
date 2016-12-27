package com.pwdd.server.responders.GET;

import org.junit.*;
import static org.junit.Assert.*;

import com.pwdd.server.helpers.Helpers;

import java.io.File;
import java.io.InputStream;

public class HelloWorldResponderTest {
  private final HelloWorldResponder helloWorldResponder = new HelloWorldResponder();
  private final InputStream header = helloWorldResponder.header(new File("foo"), "date");

  @Test
  public void canRespondHelloURITest() {
    assertTrue("Can respond to uri '/hello'", helloWorldResponder.canRespond(new File("/hello")));
  }

  @Test
  public void canRespondUpperCaseHelloTest() {
    assertTrue("Can respond to uppercase uri '/HELLO", helloWorldResponder.canRespond(new File("/HELLO")));
  }

  @Test
  public void canRespondHelloWithTrailingSlashTest() {
    assertTrue("Can respond to '/hello/", helloWorldResponder.canRespond(new File("/hello/")));
  }

  @Test
  public void cannotRespondEndHellosTest() {
    assertFalse("Cannot respond to '/foo/hello", helloWorldResponder.canRespond(new File("/foo/hello")));
  }

  @Test
  public void cannotRespondStartHellosTest() {
    assertFalse("Cannot respond to '/hello/foo", helloWorldResponder.canRespond(new File("/hello/foo")));
  }

  @Test
  public void headerHasProtocolVersionTest() {
    assertTrue("Header has protocol version", Helpers.inputStreamToString(header).contains("HTTP/1.1"));
  }

  @Test
  public void headerHasStatusCodeTest() {
    assertTrue("Header has status code", Helpers.inputStreamToString(header).contains("200 OK"));
  }

  @Test
  public void headerHasContentTypeTest() {
    assertTrue("Header has content type", Helpers.inputStreamToString(header).contains("text/plain"));
  }

  @Test
  public void headerHasDateTest() {
    assertTrue("Header has date", Helpers.inputStreamToString(header).contains("Date: "));
  }

  @Test
  public void bodyHasHellWorldTest() {
    InputStream body = helloWorldResponder.body(new File("/hello"));
    assertTrue("Body has 'hello, world", Helpers.inputStreamToString(body).contains("Hello, world"));
  }
}
