package com.pwdd.httpServer;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import static org.junit.Assert.*;

public class ArgumentsValidationTest {
  @Test
  public void getEmptyArgsTest() {
    String[] zeroArg = new String[0];
    assertEquals(ArgumentsValidation.defaultDir, ArgumentsValidation.getDirectory(zeroArg));
  }

  @Test
  public void getDirTest() {
    String[] onlyDir = new String[] {"-d", "blob"};
    assertEquals("blob", ArgumentsValidation.getDirectory(onlyDir));
    assertEquals(ArgumentsValidation.defaultPortNumber, ArgumentsValidation.getPortNumber(onlyDir));
  }

  @Test
  public void getPortTest() {
    String[] onlyPort = new String[] {"-p", "blob"};
    assertEquals("blob", ArgumentsValidation.getPortNumber(onlyPort));
    assertEquals(ArgumentsValidation.defaultDir, ArgumentsValidation.getDirectory(onlyPort));
  }

  @Test
  public void getArgsInAnyOrder() {
    String[] unordered = new String[]{"-p", "1234", "-d", "bar"};
    assertEquals("bar", ArgumentsValidation.getDirectory(unordered));
    assertEquals("1234", ArgumentsValidation.getPortNumber(unordered));
  }

  @Test
  public void zeroArgTest() {
    String[] zeroArg = new String[0];
    assertTrue("Is valid if there is no arg", ArgumentsValidation.isValidArgs(zeroArg));
  }

  @Test
  public void invalidArgTest() {
    String[] args = new String[]{"blob"};
    assertFalse("Does not accept random arguments", ArgumentsValidation.isValidArgs(args));
  }

  @Test
  public void noPortNorDirTest() {
    String[] noPortNorDir = new String[]{"foo", "bar"};
    assertFalse("Invalid if port and dir are not properly defined", ArgumentsValidation.isValidArgs(noPortNorDir));
  }

  @Test
  public void invalidOrderTest() {
    String[] invalidOrderPort = new String[]{"8080", "-p"};
    assertFalse("Invalid if port is in wrong order", ArgumentsValidation.isValidArgs(invalidOrderPort));

    String[] invalidOrderDir = new String[]{"8080", "-d"};
    assertFalse("Invalid if dir is in wrong order", ArgumentsValidation.isValidArgs(invalidOrderDir));

    String[] invalidOrderOne = new String[]{"8080", "-p", "-d", "foo"};
    assertFalse("Invalid if one arg is in wrong order", ArgumentsValidation.isValidArgs(invalidOrderOne));

    String[] invalidOrderTwo = new String[]{"-p", "8080", "foo", "foo"};
    assertFalse("Invalid if second arg is in wrong order", ArgumentsValidation.isValidArgs(invalidOrderTwo));

    String[] repeatedARgs = new String[]{"-d", "foo", "-d", "bar"};
    assertFalse("Invalid if it has repeated args", ArgumentsValidation.isValidArgs((repeatedARgs)));
  }

  @Test
  public void validOrderTest() {
    String[] validOrderPort = new String[]{"-p", "8080"};
    assertTrue("valid if port is in right order", ArgumentsValidation.isValidArgs(validOrderPort));

    String[] validOrderDir = new String[]{"-d", "foo"};
    assertTrue("Invalid if dir is in right order", ArgumentsValidation.isValidArgs(validOrderDir));

    String[] validOrderOne = new String[]{"-p", "12", "-d", "foo"};
    assertTrue("Invalid if one arg is in right order", ArgumentsValidation.isValidArgs(validOrderOne));
  }

  @Rule
  public final ExpectedSystemExit exit = ExpectedSystemExit.none();

  @Test
  public void exitsWithInvalidArgTest() {
    String[] args = new String[]{"blob"};
    exit.expectSystemExitWithStatus(0);
    ArgumentsValidation.exitOnInvalidArgs(args);
  }

  @Test
  public void exitsWithRepeatedArgsTest() {
    String[] args = new String[]{"-d", "foo", "-d", "bar"};
    exit.expectSystemExitWithStatus(0);
    ArgumentsValidation.exitOnInvalidArgs(args);
  }

  @Test
  public void validDirAsEmptyStringTest() {
    String[] args = new String[0];
    assertEquals(ArgumentsValidation.defaultDir, ArgumentsValidation.getDirectory(args));
  }

  @Test
  public void validDirAsExistingDirTest() {
    String[] args = new String[]{"foo"};
    assertEquals(ArgumentsValidation.defaultDir, ArgumentsValidation.getDirectory(args));
  }
}
