package com.pwdd.httpServer.responders;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.*;

public class IndexResponderTest {

  @Test
  public void listFilenamesTest() {
    IndexResponder fileHandler = new IndexResponder("src/test/java/com/pwdd/httpServer/mocks/testFilesOne");
    List<String> expected = Arrays.asList("nest", "a.html", "b.html");
    Collections.sort(expected);
    List<String> actual = fileHandler.listFilenames();
    Collections.sort(actual);
    assertEquals("Lists files inside a directory", expected, actual);
  }

  @Test
  public void listFilenamesInEmptyDirTest() {
    IndexResponder fileHandler = new IndexResponder("src/test/java/com/pwdd/httpServer/mocks/testFilesTwo");
    List<String> expected = new ArrayList<>();
    assertTrue("Returns empty list if directory is empty", expected.equals(fileHandler.listFilenames()));
  }
}
