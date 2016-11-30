package com.pwdd.httpServer;

import org.junit.*;

import java.io.File;

import static org.junit.Assert.*;

public class DownloadableResponderTest {
  private DownloadableResponder downloadableResponder = new DownloadableResponder();

  @Test
  public void isDownloadableFileTest() {
    File file = new File("src/test/java/com/pwdd/httpServer/testFilesOne/a.html");
    assertTrue("File in root is downloadable", downloadableResponder.isDownloadable(file));
  }

  @Test
  public void isDownloadableNestedFileTest() {
    File file = new File("src/test/java/com/pwdd/httpServer/testFilesOne/nest/a.txt");
    assertTrue("File in nested dir is downloadable", downloadableResponder.isDownloadable(file));
  }

  @Test
  public void isDownloadableNonExistentFileTest() {
    File file = new File("foo.txt");
    assertFalse("Not downloadable if file does not exist", downloadableResponder.isDownloadable(file));
  }
}
