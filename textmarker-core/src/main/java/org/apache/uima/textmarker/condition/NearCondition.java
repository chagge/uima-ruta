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

package org.apache.uima.textmarker.condition;

import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.textmarker.TextMarkerStream;
import org.apache.uima.textmarker.expression.bool.BooleanExpression;
import org.apache.uima.textmarker.expression.bool.SimpleBooleanExpression;
import org.apache.uima.textmarker.expression.number.NumberExpression;
import org.apache.uima.textmarker.expression.number.SimpleNumberExpression;
import org.apache.uima.textmarker.expression.type.TypeExpression;
import org.apache.uima.textmarker.rule.EvaluatedCondition;
import org.apache.uima.textmarker.rule.RuleElement;
import org.apache.uima.textmarker.type.TextMarkerBasic;
import org.apache.uima.textmarker.visitor.InferenceCrowd;

public class NearCondition extends TypeSentiveCondition {

  private final NumberExpression min;

  private final NumberExpression max;

  private final BooleanExpression forward;

  private final BooleanExpression filtered;

  public NearCondition(TypeExpression type, NumberExpression min, NumberExpression max,
          BooleanExpression forward, BooleanExpression filtered) {
    super(type);
    this.min = min == null ? new SimpleNumberExpression(1) : min;
    this.max = max == null ? new SimpleNumberExpression(1) : max;
    this.forward = forward == null ? new SimpleBooleanExpression(true) : forward;
    this.filtered = filtered == null ? new SimpleBooleanExpression(false) : filtered;
  }

  @Override
  public EvaluatedCondition eval(AnnotationFS annotation, RuleElement element,
          TextMarkerStream stream, InferenceCrowd crowd) {
    int maxValue = max.getIntegerValue(element.getParent());
    int minValue = min.getIntegerValue(element.getParent());
    boolean forwardValue = forward.getBooleanValue(element.getParent());
    
    FSIterator<AnnotationFS> it = filtered.getBooleanValue(element.getParent()) ? stream : stream
            .getUnfilteredBasicIterator();
    AnnotationFS pointer = null;
    if(forwardValue) {
      pointer = stream.getEndAnchor(annotation.getEnd());
    } else {
      pointer = stream.getBeginAnchor(annotation.getBegin());
    }
    it.moveTo(pointer);
    int count = 0;
    while (count <= maxValue) {
      if (count >= minValue && it.isValid()) {
        FeatureStructure featureStructure = it.get();
        if (featureStructure instanceof TextMarkerBasic) {
          TextMarkerBasic each = (TextMarkerBasic) featureStructure;
          if (each.isPartOf(type.getType(element.getParent()))) {
            return new EvaluatedCondition(this, true);
          }
        }
      }
      if (forwardValue) {
        it.moveToNext();
      } else {
        it.moveToPrevious();
      }
      count++;
    }
    return new EvaluatedCondition(this, false);
  }

  public NumberExpression getMin() {
    return min;
  }

  public NumberExpression getMax() {
    return max;
  }

  public BooleanExpression getForward() {
    return forward;
  }

  public BooleanExpression getFiltered() {
    return filtered;
  }
}