package com.pwdd.server.responders.GET;

import org.junit.*;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.pwdd.server.helpers.Helpers;

public class DownloadableResponderTest {
  private final String rootDirectoryPath = "src/test/java/com/pwdd/server/mocks/filesystem/";
  private final DownloadableResponder downloadableResponder = new DownloadableResponder();

  @Test
  public void canRespondToFileTest() {
    assertTrue("Can download a file in an inner directory",
        downloadableResponder.canRespond(new File(rootDirectoryPath, "/nested/a.txt")));
  }

  @Test
  public void canRespondToRootFileTest() {
    assertTrue("Can download file in root",
        downloadableResponder.canRespond(new File(rootDirectoryPath,"/a.txt")));
  }

  @Test
  public void cannotRespondToHelloTest() {
    assertFalse("Cannot respond to '<root>/hello",
        downloadableResponder.canRespond(new File(rootDirectoryPath,"/hello")));
  }

  @Test
  public void cannotRespondToRootTest() {
    File root = new File(System.getProperty("user.dir"), "src/test/java/com/pwdd/server/mocks/filesystem");
    assertFalse("Cannot respond to '/'",
        downloadableResponder.canRespond(root));
  }

  @Test
  public void cannotRespondToDirTest() {
    assertFalse("Cannot respond to uri of a directory",
        downloadableResponder.canRespond(new File(rootDirectoryPath)));
  }

  @Test
  public void headerHasRightContentTypeTest() {
    String header = Helpers.inputStreamToString(downloadableResponder.header(new File("foo"), "date"));
    assertTrue("Content-Type is set to application/octet-stream",
        header.contains("application/octet-stream"));
  }

  @Test
  public void headerHasRightContentDisposition() {
    String header = Helpers.inputStreamToString(downloadableResponder.header(new File("foo"),"date"));
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
  public void isDownloadableImageTest() {
    File file = new File("src/test/java/com/pwdd/server/mocks/filesystem/jennings_bilas.jpeg");
    assertFalse("Not downloadable if file is jpeg", downloadableResponder.isDownloadable(file));
  }

  @Test
  public void isDownloadableGifTest() {
    File file = new File("src/test/java/com/pwdd/server/mocks/filesystem/monkey.gif");
    assertTrue("Not downloadable if file is jpeg", downloadableResponder.isDownloadable(file));
  }

  @Test
  public void bodyIsFileTest() throws IOException {
    File file = new File("src/test/java/com/pwdd/server/mocks/filesystem/monkey.gif");
    InputStream responseBody = downloadableResponder.body(file);
    byte[] responseBytes = Helpers.responseByteArray(responseBody);
    Path path = Paths.get("src/test/java/com/pwdd/server/mocks/filesystem/monkey.gif");
    byte[] fileBytes = Files.readAllBytes(path);
    assertArrayEquals("Response body is the file", responseBytes, fileBytes);
  }
}
