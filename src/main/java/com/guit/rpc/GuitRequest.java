/*
 * Copyright 2010 Gal Dolber.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.guit.rpc;

import com.google.gwt.http.client.Header;
import com.google.gwt.http.client.Response;

public class GuitRequest extends Response {

  private final String result;

  int status = Response.SC_OK;

  public GuitRequest(String result) {
    this.result = result;
  }

  public GuitRequest() {
    status = Response.SC_NOT_FOUND;
    result = "";
  }

  @Override
  public String getHeader(String header) {
    throw new IllegalStateException();
  }

  @Override
  public Header[] getHeaders() {
    throw new IllegalStateException();
  }

  @Override
  public String getHeadersAsString() {
    throw new IllegalStateException();
  }

  @Override
  public int getStatusCode() {
    return status;
  }

  @Override
  public String getStatusText() {
    return "SC_OK";
  }

  @Override
  public String getText() {
    return result;
  }

}
