package com.pwdd.server.fileReaders.GET;

import com.pwdd.server.helpers.Helpers;
import com.pwdd.server.responders.GET.FileReader;
import org.junit.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileReaderTest {
  private final FileReader fileReader = FileReader.getInstance();

  @Test
  public void bodyReadsAnImageIntoBytes() throws IOException {
    String path = "src/test/java/com/pwdd/server/mocks/filesystem/battle.jpeg";
    File file = new File(path);
    Path filePath = Paths.get(path);
    byte[] expected = Files.readAllBytes(filePath);
    byte[] result = Helpers.responseByteArray(fileReader.body(file));
    assertArrayEquals("Body has same content as image", expected, result);
  }

  @Test
  public void bodyReadsAnyFileIntoBytes() throws IOException {
    String path = "src/test/java/com/pwdd/server/mocks/filesystem/monkey.gif";
    File file = new File(path);
    Path filePath = Paths.get(path);
    byte[] expected = Files.readAllBytes(filePath);
    byte[] result = Helpers.responseByteArray(fileReader.body(file));
    assertArrayEquals("Body has same content as file", expected, result);
  }

  @Test
  public void getExtensionPNGTest() {
    File file = new File("src/test/java/com/pwdd/server/mocks/filesystem/battle.png");
    assertEquals("Get extension of png", "png", fileReader.getExtension(file));
  }

  @Test
  public void getExtensionPDFTest() {
    File file = new File("src/test/java/com/pwdd/server/mocks/filesystem/readme.pdf");
    assertEquals("Get extension of pdf", "pdf", fileReader.getExtension(file));
  }
}
