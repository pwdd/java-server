package com.pwdd.server.protocol;

import com.pwdd.server.responders.GET.*;
import com.pwdd.server.responders.IResponder;

import java.io.File;

public class GET extends ResponseBuilder implements Protocol {
  private final File rootDirectory;
  private final IResponder[] responders;

  public GET(File _rootDirectory) {
    this.rootDirectory = _rootDirectory;
    this.responders = responders();
  }

  public IResponder[] responders() {
    return new IResponder[] {
        new FormResponder(),
        new DownloadableResponder(),
        new HelloWorldResponder(),
        new ImageResponder(),
        new IndexResponder(rootDirectory)};
  }
}
