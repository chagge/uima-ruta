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

package org.apache.uima.ruta;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.ruta.engine.RutaEngine;
import org.apache.uima.ruta.engine.RutaTestUtils;
import org.apache.uima.ruta.engine.RutaTestUtils.TestFeature;
import org.junit.Test;

public class FeatureMatch1Test {

  @Test
  public void test() {
    String name = this.getClass().getSimpleName();
    String namespace = this.getClass().getPackage().getName().replaceAll("\\.", "/");
    CAS cas = null;
    Map<String, String> complexTypes = new HashMap<String, String>();
    Map<String, List<TestFeature>> features = new TreeMap<String, List<TestFeature>>();
    String typeNameA = "org.apache.uima.ruta.FeatureMatchTest.A";
    String typeNameB = "org.apache.uima.ruta.FeatureMatchTest.B";
    String typeNameC = "org.apache.uima.ruta.FeatureMatchTest.C";
    String typeNameD = "org.apache.uima.ruta.FeatureMatchTest.D";
    complexTypes.put(typeNameA, "uima.tcas.Annotation");
    complexTypes.put(typeNameB, typeNameD);
    complexTypes.put(typeNameC, typeNameD);
    complexTypes.put(typeNameD, "uima.tcas.Annotation");
    List<TestFeature> listA = new ArrayList<RutaTestUtils.TestFeature>();
    features.put(typeNameA, listA);
    String fnab = "ab";
    listA.add(new TestFeature(fnab, "", typeNameB));
    String fnac = "ac";
    listA.add(new TestFeature(fnac, "", typeNameC));
    List<TestFeature> listB = new ArrayList<RutaTestUtils.TestFeature>();
    features.put(typeNameB, listB);
    String fnbc = "bc";
    listB.add(new TestFeature(fnbc, "", typeNameC));
    List<TestFeature> listC = new ArrayList<RutaTestUtils.TestFeature>();
    features.put(typeNameC, listC);
    String fnci = "ci";
    listC.add(new TestFeature(fnci, "", "uima.cas.Integer"));
    String fncb = "cb";
    listC.add(new TestFeature(fncb, "", "uima.cas.Boolean"));
    List<TestFeature> listD = new ArrayList<RutaTestUtils.TestFeature>();
    features.put(typeNameD, listD);
    String fnds = "ds";
    listD.add(new TestFeature(fnds, "", "uima.cas.String"));

    try {
      cas = RutaTestUtils.process(namespace + "/" + name + RutaEngine.SCRIPT_FILE_EXTENSION,
              namespace + "/" + name + ".txt", 50, false, false, complexTypes, features, null);
    } catch (Exception e) {
      e.printStackTrace();
      assert (false);
    }
    Type t = null;
    AnnotationIndex<AnnotationFS> ai = null;
    FSIterator<AnnotationFS> iterator = null;

    t = RutaTestUtils.getTestType(cas, 1);
    ai = cas.getAnnotationIndex(t);
    assertEquals(1, ai.size());
    iterator = ai.iterator();
    assertEquals("Marshall", iterator.next().getCoveredText());

    t = RutaTestUtils.getTestType(cas, 2);
    ai = cas.getAnnotationIndex(t);
    assertEquals(1, ai.size());
    iterator = ai.iterator();
    assertEquals("Marshall", iterator.next().getCoveredText());
    
    t = RutaTestUtils.getTestType(cas, 3);
    ai = cas.getAnnotationIndex(t);
    assertEquals(1, ai.size());
    iterator = ai.iterator();
    assertEquals("Marshall", iterator.next().getCoveredText());
    
    t = RutaTestUtils.getTestType(cas, 4);
    ai = cas.getAnnotationIndex(t);
    assertEquals(1, ai.size());
    iterator = ai.iterator();
    assertEquals("Marshall", iterator.next().getCoveredText());
    
    t = RutaTestUtils.getTestType(cas, 5);
    ai = cas.getAnnotationIndex(t);
    iterator = ai.iterator();
    assertEquals(3, ai.size());
    assertEquals("Peter", iterator.next().getCoveredText());
    assertEquals("Joern", iterator.next().getCoveredText());
    assertEquals("Marshall", iterator.next().getCoveredText());
    
    t = RutaTestUtils.getTestType(cas, 6);
    ai = cas.getAnnotationIndex(t);
    iterator = ai.iterator();
    assertEquals(3, ai.size());
    assertEquals("Kluegl", iterator.next().getCoveredText());
    assertEquals("Kottmann", iterator.next().getCoveredText());
    assertEquals("Schor", iterator.next().getCoveredText());
    
    t = RutaTestUtils.getTestType(cas, 7);
    ai = cas.getAnnotationIndex(t);
    iterator = ai.iterator();
    assertEquals(3, ai.size());
    assertEquals("Kluegl", iterator.next().getCoveredText());
    assertEquals("Kottmann", iterator.next().getCoveredText());
    assertEquals("Schor", iterator.next().getCoveredText());
    
    t = RutaTestUtils.getTestType(cas, 8);
    ai = cas.getAnnotationIndex(t);
    iterator = ai.iterator();
    assertEquals(3, ai.size());
    assertEquals("Kluegl", iterator.next().getCoveredText());
    assertEquals("Kottmann", iterator.next().getCoveredText());
    assertEquals("Schor", iterator.next().getCoveredText());
    
    t = RutaTestUtils.getTestType(cas, 9);
    ai = cas.getAnnotationIndex(t);
    iterator = ai.iterator();
    assertEquals(3, ai.size());
    assertEquals("Kluegl", iterator.next().getCoveredText());
    assertEquals("Kottmann", iterator.next().getCoveredText());
    assertEquals("Schor", iterator.next().getCoveredText());

    t = RutaTestUtils.getTestType(cas, 10);
    ai = cas.getAnnotationIndex(t);
    assertEquals(1, ai.size());
    iterator = ai.iterator();
    assertEquals("Marshall", iterator.next().getCoveredText());
    
    t = RutaTestUtils.getTestType(cas, 11);
    ai = cas.getAnnotationIndex(t);
    assertEquals(1, ai.size());
    iterator = ai.iterator();
    assertEquals("Marshall", iterator.next().getCoveredText());
    
    t = RutaTestUtils.getTestType(cas, 12);
    ai = cas.getAnnotationIndex(t);
    assertEquals(1, ai.size());
    iterator = ai.iterator();
    assertEquals("Marshall", iterator.next().getCoveredText());

    
    t = RutaTestUtils.getTestType(cas, 13);
    ai = cas.getAnnotationIndex(t);
    iterator = ai.iterator();
    assertEquals(3, ai.size());
    assertEquals("Peter", iterator.next().getCoveredText());
    assertEquals("Joern", iterator.next().getCoveredText());
    assertEquals("Marshall", iterator.next().getCoveredText());
    
    t = RutaTestUtils.getTestType(cas, 14);
    ai = cas.getAnnotationIndex(t);
    iterator = ai.iterator();
    assertEquals(3, ai.size());
    assertEquals("Kluegl", iterator.next().getCoveredText());
    assertEquals("Kottmann", iterator.next().getCoveredText());
    assertEquals("Schor", iterator.next().getCoveredText());
    
    t = RutaTestUtils.getTestType(cas, 15);
    ai = cas.getAnnotationIndex(t);
    iterator = ai.iterator();
    assertEquals(3, ai.size());
    assertEquals("Peter", iterator.next().getCoveredText());
    assertEquals("Joern", iterator.next().getCoveredText());
    assertEquals("Marshall", iterator.next().getCoveredText());
    
    
    t = RutaTestUtils.getTestType(cas, 16);
    ai = cas.getAnnotationIndex(t);
    assertEquals(1, ai.size());
    iterator = ai.iterator();
    assertEquals("Peter Kluegl, Joern Kottmann, Marshall", iterator.next().getCoveredText());
    
    t = RutaTestUtils.getTestType(cas, 17);
    ai = cas.getAnnotationIndex(t);
    assertEquals(1, ai.size());
    iterator = ai.iterator();
    assertEquals("Peter Kluegl, Joern Kottmann, Marshall Schor", iterator.next().getCoveredText());

    t = RutaTestUtils.getTestType(cas, 18);
    ai = cas.getAnnotationIndex(t);
    assertEquals(1, ai.size());
    iterator = ai.iterator();
    assertEquals("Peter Kluegl, Joern Kottmann, Marshall Schor", iterator.next().getCoveredText());

    if (cas != null) {
      cas.release();
    }

  }
}
