package com.pwdd.server.responders;

import org.junit.*;
import static org.junit.Assert.*;

import java.io.File;

import com.pwdd.server.helpers.Helpers;

public class DownloadableResponderTest {
  private final DownloadableResponder downloadableResponder = new DownloadableResponder();

  @Test
  public void canRespondToFileTest() {
    assertTrue("Can download file",
        downloadableResponder.canRespond("src/test/java/com/pwdd/server/mocks/filesystem/nested/a.txt"));
  }

  @Test
  public void canRespondToInnerFileTest() {
    assertTrue("Can download file",
        downloadableResponder.canRespond("src/test/java/com/pwdd/server/mocks/filesystem/a.txt"));
  }

  @Test
  public void cannotRespondToHelloTest() {
    assertFalse("Can download file",
        downloadableResponder.canRespond("/hello"));
  }

  @Test
  public void cannotRespondToRootTest() {
    File root = new File(System.getProperty("user.dir"), "src/test/java/com/pwdd/server/mocks/filesystem");
    assertFalse("Can download file",
        downloadableResponder.canRespond(root.getAbsolutePath()));
  }

  @Test
  public void cannotRespondToDirTest() {
    assertFalse("Can download file",
        downloadableResponder.canRespond("src/test/java/com/pwdd/server/mocks/filesystem/nested"));
  }

  @Test
  public void headerHasRightContentTypeTest() {
    String header = Helpers.bytesToString(downloadableResponder.header("date"));
    assertTrue("Content-Type is set to application/octet-stream",
        header.contains("application/octet-stream"));
  }

  @Test
  public void headerHasRightContentDisposition() {
    String header = Helpers.bytesToString(downloadableResponder.header("date"));
    assertTrue("Content-Disposition is set to attachment",
        header.contains("Content-Disposition: attachment"));
  }

  @Test
  public void isDownloadableFileTest() {
    File file = new File("src/test/java/com/pwdd/server/mocks/filesystem/a.txt");
    assertTrue("File in root is downloadable", downloadableResponder.isDownloadable(file));
  }

  @Test
  public void isDownloadableNestedFileTest() {
    File file = new File("src/test/java/com/pwdd/server/mocks/filesystem/nested/a.txt");
    assertTrue("File in nested dir is downloadable", downloadableResponder.isDownloadable(file));
  }

  @Test
  public void isDownloadableNonExistentFileTest() {
    File file = new File("foo.txt");
    assertFalse("Not downloadable if file does not exist", downloadableResponder.isDownloadable(file));
  }

  @Test
  public void isImageTrueTest() {
    File[] files = new File[] {
        new File("/foo.jpg"),
        new File("/foo.jpeg"),
        new File("/foo.png"),
        new File("/foo.gif"),
        new File("/foo.tiff")};
    assertTrue("Is true for images formats", checkAllFiles(files));
  }

  @Test
  public void isImageFalseTest() {
    File file = new File("/foo.html");
    assertFalse("Is false for non images", downloadableResponder.isImage(file));
  }

  @Test
  public void isPdfTrueTest() {
    File file = new File("/foo.pdf");
    assertTrue("Is true for .pdf", downloadableResponder.isPdf(file));
  }

  @Test
  public void isPdfFalseTest() {
    File file = new File("/foo.png");
    assertFalse("Is false for non .pdf", downloadableResponder.isPdf(file));
  }

  private boolean checkAllFiles(File[] files) {
    for (File file : files) {
      if (!downloadableResponder.isImage(file)) {
        return false;
      }
    }
    return true;
  }
}
