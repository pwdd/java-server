package com.pwdd.server.protocol;

import com.pwdd.server.responders.IResponder;
import com.pwdd.server.responders.POST.ProcessFormResponder;

public class POST extends ResponseBuilder implements Protocol {
  private String requestBody;

  public POST(String _requestBody) {
    this.requestBody = _requestBody;
  }

  public IResponder[] responders() {
    return new IResponder[]{
        new ProcessFormResponder(requestBody)
    };
  }

  @Override
  public String errorMessage() {
    String invalidRequest = Protocol.statusCodes.get("400");
    return Protocol.version + " " + invalidRequest + IResponder.CRLF +
        "Date: " + dateInUTC0() + IResponder.CRLF +
        IResponder.CRLF +
        invalidRequest;
  }
}
