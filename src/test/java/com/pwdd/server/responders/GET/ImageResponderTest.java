package com.pwdd.server.responders.GET;

import com.pwdd.server.helpers.Helpers;
import com.pwdd.server.responders.GET.ImageResponder;
import org.junit.*;

import java.io.File;

import static org.junit.Assert.*;

public class ImageResponderTest {
  private final String rootDirectoryPath = "src/test/java/com/pwdd/server/mocks/filesystem";
  private final ImageResponder imageResponder = new ImageResponder(FileReader.getInstance());

  @Test
  public void canRespondToJPGTest() {
    assertTrue("Can respond to path to jpg file",
        imageResponder.canRespond(new File (rootDirectoryPath, "/jennings_bilas.jpg")));
  }

  @Test
  public void canRespondToJPEGTest() {
    assertTrue("Can respond to path to jpg file",
        imageResponder.canRespond(new File (rootDirectoryPath, "/battle.jpeg")));
  }

  @Test
  public void canRespondToPNGTest() {
    assertTrue("Can respond to path to jpg file",
        imageResponder.canRespond(new File (rootDirectoryPath, "/battle.png")));
  }

  @Test
  public void canRespondToPDFTest() {
    assertTrue("Can respond to path to jpg file",
        imageResponder.canRespond(new File (rootDirectoryPath, "/readme.pdf")));
  }

  @Test
  public void cannotRespondToGIFTest() {
    assertFalse("Can respond to path to jpg file",
        imageResponder.canRespond(new File (rootDirectoryPath, "/monkey.gif")));
  }

  @Test
  public void headerOfPdfTest() {
    String header = Helpers.inputStreamToString(imageResponder.header(new File("foo.pdf"), "today"));
    assertTrue("Has the right content type for pdf",
        header.contains("application/pdf"));
  }

  @Test
  public void headerOfJpgTest() {
    String header = Helpers.inputStreamToString(imageResponder.header(new File("foo.jpg"), "today"));
    assertTrue("Has the right content type for jpg",
        header.contains("image/jpeg"));
  }

  @Test
  public void headerOfPngTest() {
    String header = Helpers.inputStreamToString(imageResponder.header(new File("foo.png"), "today"));
    assertTrue("Has the right content type for png",
        header.contains("image/png"));
  }
}
