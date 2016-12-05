package com.pwdd.server.responders;

import org.junit.*;
import static org.junit.Assert.*;

import com.pwdd.server.helpers.Helpers;

public class HelloWorldResponderTest {
  private HelloWorldResponder helloWorldResponder = new HelloWorldResponder();
  private byte[] header = helloWorldResponder.header("/hello");

  @Test
  public void canRespondHelloURITest() {
    assertTrue("Can respond to uri '/hello'", helloWorldResponder.canRespond("/hello"));
  }

  @Test
  public void canRespondUpperCaseHelloTest() {
    assertTrue("Can respond to uppercase uri '/HELLO", helloWorldResponder.canRespond("/HELLO"));
  }

  @Test
  public void canRespondHelloWithTrailingSlashTest() {
    assertTrue("Can respond to '/hello/", helloWorldResponder.canRespond("/hello/"));
  }

  @Test
  public void cannotRespondEndHellosTest() {
    assertFalse("Cannot respond to '/foo/hello", helloWorldResponder.canRespond("/foo/hello"));
  }

  @Test
  public void cannotRespondStartHellosTest() {
    assertFalse("Cannot respond to '/hello/foo", helloWorldResponder.canRespond("/foo/hello"));
  }

  @Test
  public void headerHasProtocolVersionTest() {
    assertTrue("Header has protocol version", Helpers.bytesToString(header).contains("HTTP/1.1"));
  }

  @Test
  public void headerHasStatusCodeTest() {
    assertTrue("Header has status code", Helpers.bytesToString(header).contains("200 OK"));
  }

  @Test
  public void headerHasContentTypeTest() {
    assertTrue("Header has content type", Helpers.bytesToString(header).contains("text/plain"));
  }

  @Test
  public void headerHasDateTest() {
    assertTrue("Header has date", Helpers.bytesToString(header).contains("Date: "));
  }

  @Test
  public void bodyHasHellWorldTest() {
    byte[] body = helloWorldResponder.body("/hello");
    assertTrue("Body has 'hello, world", Helpers.bytesToString(body).contains("Hello, world"));
  }
}
