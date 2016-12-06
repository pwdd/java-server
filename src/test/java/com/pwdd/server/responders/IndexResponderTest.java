package com.pwdd.server.responders;

import com.pwdd.server.helpers.Helpers;
import org.junit.*;

import java.io.File;

import static org.junit.Assert.*;

public class IndexResponderTest {
  private String baseDir = "/src/test/java/com/pwdd/server/mocks/filesystem/";
  private File rootDirectory = new File(System.getProperty("user.dir") + baseDir);
  private String rootDirectoryPath = rootDirectory.getAbsolutePath();
  private IndexResponder index = new IndexResponder(rootDirectory);

  @Test
  public void canRespondRootTest() {
    assertTrue("Can respond to root uri",
        index.canRespond(rootDirectory));
  }

  @Test
  public void canRespondDirTest() {
    assertTrue("Can respond to uri of directories inside root directory",
        index.canRespond(new File (rootDirectoryPath, "/empty")));
  }

  @Test
  public void cannotRespondToHelloTest() {
    assertFalse("Cannot respond to /hello",
        index.canRespond(new File("/hello")));
  }

  @Test
  public void cannotRespondToFileTest() {
    assertFalse("Cannot respond to uri of a file",
        index.canRespond(new File (rootDirectoryPath, "/a.txt")));
  }

  @Test
  public void bodyForRootIsHTMLFileTest() {
    String body = Helpers.bytesToString(index.body(rootDirectory)).toLowerCase();
    assertTrue("Body for root is well formatted HTML",
        body.contains("<!doctype html><html>") &&
            body.contains("<head>") &&
            body.contains("</head>") &&
            body.contains("<body>") &&
            body.contains("</body>") &&
            body.contains("</html>"));
  }

  @Test
  public void bodyHasFileLinks() {
    String body = Helpers.bytesToString(index.body(new File (rootDirectoryPath, "/nested")));
    assertTrue("Body has file names", body.toLowerCase().contains("a.txt"));
    assertTrue("Body has all file names", body.toLowerCase().contains("b.txt"));
  }
}
