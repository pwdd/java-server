package com.pwdd.server.protocol;

import com.pwdd.server.responders.GET.*;
import com.pwdd.server.responders.GET.FileReader;
import com.pwdd.server.responders.IResponder;

import java.io.*;
import java.util.HashMap;

public class GET implements Protocol {
  private final File rootDirectory;
  private final FileReader fileReader;
  private final ResponseBuilder responseBuilder;

  public GET(File _rootDirectory, FileReader _fileReader, ResponseBuilder _responseBuilder) {
    this.rootDirectory = _rootDirectory;
    this.fileReader = _fileReader;
    this.responseBuilder = _responseBuilder;
  }

  public InputStream processResponse(HashMap<String, String> request, File rootDirectory, IResponder[] responders)
      throws IOException {
    return responseBuilder.processResponse(request, rootDirectory, responders, this);
  }

  public IResponder[] responders() {
    return new IResponder[] {
        new FormResponder(),
        new DownloadableResponder(fileReader),
        new HelloWorldResponder(),
        new ImageResponder(fileReader),
        new IndexResponder(rootDirectory)};
  }
}
