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

package org.apache.uima.textmarker.rule;

import java.util.List;

import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.textmarker.TextMarkerStream;

public abstract class AbstractRuleMatch<T extends AbstractRule> {

  protected boolean matched = true;

  protected final T rule;

  public AbstractRuleMatch(T rule) {
    super();
    this.rule = rule;
  }

  public T getRule() {
    return rule;
  }

  public boolean matched() {
    return matched;
  }

  public boolean matchedCompletely() {
    return true;
  }

//  public abstract List<AnnotationFS> getMatchedAnnotations(TextMarkerStream stream,
//          List<Integer> indexes, RuleElementContainer container);

  public abstract List<AnnotationFS> getMatchedAnnotationsOfRoot(TextMarkerStream stream);
  
 

}
