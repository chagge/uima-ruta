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

package org.apache.uima.textmarker;

import java.util.List;

import org.apache.uima.textmarker.rule.TextMarkerRule;

public abstract class TextMarkerBlock extends TextMarkerStatement {

  protected final String name;

  protected TextMarkerEnvironment environment;

  protected TextMarkerRule rule;

  protected List<TextMarkerStatement> elements;

  private String namespace;

  private TextMarkerModule script;

  public TextMarkerBlock(String name, TextMarkerRule rule, List<TextMarkerStatement> elements,
          TextMarkerBlock parent, String defaultNamespace) {
    super(parent);
    this.name = name;
    this.rule = rule;
    this.elements = elements;
    this.environment = new TextMarkerEnvironment(this);
    this.namespace = defaultNamespace;
  }

  public TextMarkerRule getRule() {
    return rule;
  }

  public void setRule(TextMarkerRule rule) {
    this.rule = rule;
  }

  @Override
  public TextMarkerEnvironment getEnvironment() {
    return environment;
  }

  public List<TextMarkerStatement> getElements() {
    return elements;
  }

  public void setElements(List<TextMarkerStatement> elements) {
    this.elements = elements;
  }

  public TextMarkerModule getScript() {
    if (script != null) {
      return script;
    } else if (getParent() != null) {
      return getParent().getScript();
    } else {
      // may not happen!
      assert (false);
      return null;

    }
  }

  public void setScript(TextMarkerModule script) {
    this.script = script;
  }

  public String getNamespace() {
    return namespace;
  }

  public String getName() {
    return name;
  }

}
