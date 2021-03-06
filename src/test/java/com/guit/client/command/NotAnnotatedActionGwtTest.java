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
package com.guit.client.command;

import com.guit.client.async.AbstractAsyncCallback;
import com.guit.client.command.action.NotAnnotatedAction;
import com.guit.client.command.action.NotAnnotatedAction.NotAnnotatedTestResponse;

public class NotAnnotatedActionGwtTest extends BaseCommandGwtTest {

  public void testNotAnnotatedAction() {
    commandService.execute(new NotAnnotatedAction(),
        new AbstractAsyncCallback<NotAnnotatedTestResponse>() {
          @Override
          public void success(NotAnnotatedTestResponse result) {
            fail();
          }

          @Override
          public void failure(Throwable caught) {
            finishTest();
          }
        });
    delayTestFinish(1000);
  }
}
