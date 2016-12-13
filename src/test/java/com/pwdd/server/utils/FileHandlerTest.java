package com.pwdd.server.utils;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FileHandlerTest {
  @Test
  public void isImageTrueTest() {
    File[] files = new File[] {
        new File("/foo.jpg"),
        new File("/foo.jpeg"),
        new File("/foo.png") };
    assertTrue("Is true for images formats", checkFilesAreImage(files));
  }

  @Test
  public void isImageOtherFileTest() {
    File file = new File("/foo.html");
    assertFalse("Is false for non images", FileHandler.isImage(file));
  }

  @Test
  public void isImageOtherTest() {
    File file = new File("/foo.gif");
    assertFalse("Is false for non images", FileHandler.isImage(file));
  }

  @Test
  public void isPdfTrueTest() {
    File file = new File("/foo.pdf");
    assertTrue("Is true for .pdf", FileHandler.isPdf(file));
  }

  @Test
  public void isPdfFalseTest() {
    File file = new File("/foo.png");
    assertFalse("Is false for non .pdf", FileHandler.isPdf(file));
  }

  private boolean checkFilesAreImage(File[] files) {
    for (File file : files) {
      if (!FileHandler.isImage(file)) {
        return false;
      }
    }
    return true;
  }
}
