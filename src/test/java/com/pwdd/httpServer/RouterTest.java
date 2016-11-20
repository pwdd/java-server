package com.pwdd.httpServer;

import org.junit.*;
import static org.junit.Assert.*;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

public class RouterTest {
  @Rule
  public final ExpectedSystemExit exit = ExpectedSystemExit.none();

  @Test
  public void invalidDirTest() {
    String[] args = new String[]{"blob"};
    exit.expectSystemExitWithStatus(0);
    Router.exitInvalidDir(args);
  }

  @Test
  public void validDirAsEmptyStringTest() {
    String[] args = new String[]{""};
    assertEquals("foo", Router.getDirectory(args));
  }

  @Test
  public void validDirAsExistingDirTest() {
    String[] args = new String[]{"foo"};
    assertEquals("foo", Router.getDirectory(args));
  }
}

