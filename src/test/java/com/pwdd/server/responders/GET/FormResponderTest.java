package com.pwdd.server.responders.GET;

import com.pwdd.server.helpers.Helpers;
import org.junit.*;

import java.io.File;
import java.io.InputStream;

import static org.junit.Assert.*;

public class FormResponderTest {
  private final FormResponder formResponder = new FormResponder();
  private final InputStream header = formResponder.header(new File("foo"), "date");

  @Test
  public void canRespondHelloURITest() {
    assertTrue("Can respond to uri '/form'", formResponder.canRespond(new File("/form")));
  }

  @Test
  public void canRespondUpperCaseFormTest() {
    assertTrue("Can respond to uppercase uri '/FORM", formResponder.canRespond(new File("/FORM")));
  }

  @Test
  public void canRespondFormWithTrailingSlashTest() {
    assertTrue("Can respond to '/form/", formResponder.canRespond(new File("/form/")));
  }

  @Test
  public void cannotRespondEndFormTest() {
    assertFalse("Cannot respond to '/foo/form", formResponder.canRespond(new File("/foo/form")));
  }

  @Test
  public void cannotRespondStartFormTest() {
    assertFalse("Cannot respond to '/form/foo", formResponder.canRespond(new File("/form/foo")));
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
    assertTrue("Header has content type", Helpers.inputStreamToString(header).contains("text/html"));
  }

  @Test
  public void headerHasDateTest() {
    assertTrue("Header has date", Helpers.inputStreamToString(header).contains("Date: "));
  }

  @Test
  public void bodyHasFormTest() {
    InputStream body = formResponder.body(new File("/form"));
    assertTrue("Body has a <form>", Helpers.inputStreamToString(body).contains("<form method=\"post\""));
  }
}
