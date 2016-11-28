package com.pwdd.httpServer;

import org.junit.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class FileHandlerTest {

  @Test
  public void listFilenamesTest() {
    FileRouter fileHandler = new FileRouter("src/test/java/com/pwdd/httpServer/testFilesOne");
    List<String> expected = Arrays.asList("a.html", "b.html");
    assertTrue("Lists files inside a directory", expected.equals(fileHandler.listFilenames()));
  }

  @Test
  public void listFilenamesInEmptyDirTest() {
    FileRouter fileHandler = new FileRouter("src/test/java/com/pwdd/httpServer/testFilesTwo");
    List<String> expected = new ArrayList<>();
    assertTrue("Returns empty list if directory is empty", expected.equals(fileHandler.listFilenames()));
  }
}
