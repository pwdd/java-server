package com.pwdd.httpServer.utils;

import org.junit.*;
import static org.junit.Assert.*;

public class URICleanerTest {
  @Test
  public void cleanRootTest() {
    assertEquals("Return '/' for root", "/", URICleaner.cleanUp("/"));
  }

  @Test
  public void cleanTrailingSlashTest() {
    assertEquals("Trims out trailing slash", "foo", URICleaner.cleanUp("foo/"));
  }

  @Test
  public void cleanLeadingSlashTest() {
    assertEquals("Trims out leading slash", "foo", URICleaner.cleanUp("/foo"));
  }

  @Test
  public void cleanStartAndEndSlashTest() {
    assertEquals("Trims out leading and trailing slashes", "foo", URICleaner.cleanUp("/foo/"));
  }

  @Test
  public void cleanNestedURITest() {
    assertEquals("Trims out only leading and trailing slashes", "foo/bar", URICleaner.cleanUp("/foo/bar"));
  }
}
