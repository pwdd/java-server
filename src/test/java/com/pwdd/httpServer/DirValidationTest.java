package com.pwdd.httpServer;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import static org.junit.Assert.*;

public class DirValidationTest {
  @Rule
  public final ExpectedSystemExit exit = ExpectedSystemExit.none();

  @Test
  public void exitsWithInvalidDirDirTest() {
    String[] args = new String[]{"blob"};
    exit.expectSystemExitWithStatus(0);
    DirValidation.exitInvalidDir(args);
  }

  @Test
  public void validDirAsEmptyStringTest() {
    String[] args = new String[]{""};
    assertEquals("foo", DirValidation.getDirectory(args));
  }

  @Test
  public void validDirAsExistingDirTest() {
    String[] args = new String[]{"foo"};
    assertEquals("foo", DirValidation.getDirectory(args));
  }
}
