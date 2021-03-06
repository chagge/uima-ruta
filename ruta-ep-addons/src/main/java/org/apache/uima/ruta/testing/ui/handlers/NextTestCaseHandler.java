/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
*/

package org.apache.uima.ruta.testing.ui.handlers;

import org.apache.uima.ruta.testing.ui.views.TestPageBookView;
import org.apache.uima.ruta.testing.ui.views.TestViewPage;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.ui.handlers.HandlerUtil;


public class NextTestCaseHandler implements IHandler {

  public void addHandlerListener(IHandlerListener handlerListener) {
    // TODO Auto-generated method stub

  }

  public void dispose() {
    // TODO Auto-generated method stub

  }

  public Object execute(ExecutionEvent event) throws ExecutionException {
    TestPageBookView debugView = (TestPageBookView) HandlerUtil.getActivePart(event);
    TestViewPage page = (TestViewPage) debugView.getCurrentPage();
    page.nextState();
    return null;
  }

  public boolean isEnabled() {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean isHandled() {
    // TODO Auto-generated method stub
    return true;
  }

  public void removeHandlerListener(IHandlerListener handlerListener) {
    // TODO Auto-generated method stub

  }

}
