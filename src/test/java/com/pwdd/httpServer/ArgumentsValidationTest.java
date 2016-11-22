package com.pwdd.httpServer;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import static org.junit.Assert.*;

public class ArgumentsValidationTest {
  @Rule
  public final ExpectedSystemExit exit = ExpectedSystemExit.none();

  @Test
  public void exitsWithInvalidDirDirTest() {
    String[] args = new String[]{"blob"};
    exit.expectSystemExitWithStatus(0);
    ArgumentsValidation.exitInvalidDir(args);
  }

  @Test
  public void validDirAsEmptyStringTest() {
    String[] args = new String[]{""};
    assertEquals("foo", ArgumentsValidation.getDirectory(args));
  }

  @Test
  public void validDirAsExistingDirTest() {
    String[] args = new String[]{"foo"};
    assertEquals("foo", ArgumentsValidation.getDirectory(args));
  }
}
