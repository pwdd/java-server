package com.pwdd.httpServer.responders;

import org.junit.*;
import static org.junit.Assert.*;

import java.io.File;

public class DownloadableResponderTest {
  private final DownloadableResponder downloadableResponder = new DownloadableResponder();

  @Test
  public void isDownloadableFileTest() {
    File file = new File("src/test/java/com/pwdd/httpServer/mocks/testFilesOne/a.html");
    assertTrue("File in root is downloadable", downloadableResponder.isDownloadable(file));
  }

  @Test
  public void isDownloadableNestedFileTest() {
    File file = new File("src/test/java/com/pwdd/httpServer/mocks/testFilesOne/nest/a.txt");
    assertTrue("File in nested dir is downloadable", downloadableResponder.isDownloadable(file));
  }

  @Test
  public void isDownloadableNonExistentFileTest() {
    File file = new File("foo.txt");
    assertFalse("Not downloadable if file does not exist", downloadableResponder.isDownloadable(file));
  }
}
