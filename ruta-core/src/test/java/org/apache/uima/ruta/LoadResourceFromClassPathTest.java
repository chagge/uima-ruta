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
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.ruta.engine.Ruta;
import org.apache.uima.ruta.engine.RutaTestUtils;
import org.apache.uima.ruta.engine.RutaTestUtils.TestFeature;
import org.junit.Test;

public class LoadResourceFromClassPathTest {

  @Test
  public void test() {
    String document = "Peter Kluegl: Ruta\nMarshall Schor: UIMA\nJoern Kottmann: CAS Editor\n";
    String script = "WORDTABLE table = '/org/apache/uima/ruta/action/table.csv';\n";
    script += "WORDLIST list = '/org/apache/uima/ruta/action/trie.mtwl';\n";
    script += "WORDLIST list2 = '/org/apache/uima/ruta/action/firstnames.txt';\n";
    script += "Document{->TRIE(\"FirstNames.txt\" = T1, \"LastNames.txt\" = T2, list, true, 4, false, 0, \".,-/\")};\n";
    script += "Document{-> MARKTABLE(Person, 1, table, true, 0, \"\", 0, \"firstname\" = 2, \"system\" = 3)};\n";
    script += "Document{-> MARKFAST(T3, list2)};\n";
    
    Map<String, String> complexTypes = new TreeMap<String, String>();
    String typeName = "org.apache.uima.Person";
    complexTypes.put(typeName, "uima.tcas.Annotation");
    
    Map<String, List<TestFeature>> features = new TreeMap<String, List<TestFeature>>();
    List<TestFeature> list = new ArrayList<RutaTestUtils.TestFeature>();
    features.put(typeName, list);
    String fn1 = "firstname";
    list.add(new TestFeature(fn1, "", "uima.cas.String"));
    String fn2 = "system";
    list.add(new TestFeature(fn2, "", "uima.cas.String"));
    
    CAS cas = null;
    try {
      cas = RutaTestUtils.getCAS(document, complexTypes, features);
      Ruta.apply(cas, script);
    } catch (Exception e) {
      e.printStackTrace();
    }

    Type t = null;
    AnnotationIndex<AnnotationFS> ai = null;
    FSIterator<AnnotationFS> iterator = null;

    t = RutaTestUtils.getTestType(cas, 1);
    ai = cas.getAnnotationIndex(t);
    assertEquals(3, ai.size());
    iterator = ai.iterator();
    assertEquals("Peter", iterator.next().getCoveredText());
    assertEquals("Marshall", iterator.next().getCoveredText());
    assertEquals("Joern", iterator.next().getCoveredText());

    t = RutaTestUtils.getTestType(cas, 2);
    ai = cas.getAnnotationIndex(t);
    assertEquals(3, ai.size());
    iterator = ai.iterator();
    assertEquals("Kluegl", iterator.next().getCoveredText());
    assertEquals("Schor", iterator.next().getCoveredText());
    assertEquals("Kottmann", iterator.next().getCoveredText());
    
    AnnotationFS next = null;
    String v1 = null;
    String v2 = null;
    t = cas.getTypeSystem().getType(typeName);
    Feature f1 = t.getFeatureByBaseName(fn1);
    Feature f2 = t.getFeatureByBaseName(fn2);
    ai = cas.getAnnotationIndex(t);
    assertEquals(3, ai.size());
    iterator = ai.iterator();
    next = iterator.next();
    v1 = next.getStringValue(f1);
    v2 = next.getStringValue(f2);
    assertEquals("Peter", v1);
    assertEquals("Ruta", v2);
    
    next = iterator.next();
    v1 = next.getStringValue(f1);
    v2 = next.getStringValue(f2);
    assertEquals("Marshall", v1);
    assertEquals("UIMA", v2);
    
    next = iterator.next();
    v1 = next.getStringValue(f1);
    v2 = next.getStringValue(f2);
    assertEquals("Joern", v1);
    assertEquals("CAS Editor", v2);
    
    t = RutaTestUtils.getTestType(cas, 3);
    ai = cas.getAnnotationIndex(t);
    assertEquals(3, ai.size());
    iterator = ai.iterator();
    assertEquals("Peter", iterator.next().getCoveredText());
    assertEquals("Marshall", iterator.next().getCoveredText());
    assertEquals("Joern", iterator.next().getCoveredText());

    
    if (cas != null) {
      cas.release();
    }

  }

  @Test
  public void testWithClasspathPrefix() {
    String document = "Peter Kluegl: Ruta\nMarshall Schor: UIMA\nJoern Kottmann: CAS Editor\n";
    String script = "WORDTABLE table = 'classpath:/org/apache/uima/ruta/action/table.csv';\n";
    script += "WORDLIST list = 'classpath:/org/apache/uima/ruta/action/trie.mtwl';\n";
    script += "WORDLIST list2 = 'classpath:/org/apache/uima/ruta/action/firstnames.txt';\n";
    script += "Document{->TRIE(\"FirstNames.txt\" = T1, \"LastNames.txt\" = T2, list, true, 4, false, 0, \".,-/\")};\n";
    script += "Document{-> MARKTABLE(Person, 1, table, true, 0, \"\", 0, \"firstname\" = 2, \"system\" = 3)};\n";
    script += "Document{-> MARKFAST(T3, list2)};\n";

    Map<String, String> complexTypes = new TreeMap<String, String>();
    String typeName = "org.apache.uima.Person";
    complexTypes.put(typeName, "uima.tcas.Annotation");

    Map<String, List<TestFeature>> features = new TreeMap<String, List<TestFeature>>();
    List<TestFeature> list = new ArrayList<RutaTestUtils.TestFeature>();
    features.put(typeName, list);
    String fn1 = "firstname";
    list.add(new TestFeature(fn1, "", "uima.cas.String"));
    String fn2 = "system";
    list.add(new TestFeature(fn2, "", "uima.cas.String"));

    CAS cas = null;
    try {
      cas = RutaTestUtils.getCAS(document, complexTypes, features);
      Ruta.apply(cas, script);
    } catch (Exception e) {
      e.printStackTrace();
    }

    Type t = null;
    AnnotationIndex<AnnotationFS> ai = null;
    FSIterator<AnnotationFS> iterator = null;

    t = RutaTestUtils.getTestType(cas, 1);
    ai = cas.getAnnotationIndex(t);
    assertEquals(3, ai.size());
    iterator = ai.iterator();
    assertEquals("Peter", iterator.next().getCoveredText());
    assertEquals("Marshall", iterator.next().getCoveredText());
    assertEquals("Joern", iterator.next().getCoveredText());

    t = RutaTestUtils.getTestType(cas, 2);
    ai = cas.getAnnotationIndex(t);
    assertEquals(3, ai.size());
    iterator = ai.iterator();
    assertEquals("Kluegl", iterator.next().getCoveredText());
    assertEquals("Schor", iterator.next().getCoveredText());
    assertEquals("Kottmann", iterator.next().getCoveredText());

    AnnotationFS next = null;
    String v1 = null;
    String v2 = null;
    t = cas.getTypeSystem().getType(typeName);
    Feature f1 = t.getFeatureByBaseName(fn1);
    Feature f2 = t.getFeatureByBaseName(fn2);
    ai = cas.getAnnotationIndex(t);
    assertEquals(3, ai.size());
    iterator = ai.iterator();
    next = iterator.next();
    v1 = next.getStringValue(f1);
    v2 = next.getStringValue(f2);
    assertEquals("Peter", v1);
    assertEquals("Ruta", v2);

    next = iterator.next();
    v1 = next.getStringValue(f1);
    v2 = next.getStringValue(f2);
    assertEquals("Marshall", v1);
    assertEquals("UIMA", v2);

    next = iterator.next();
    v1 = next.getStringValue(f1);
    v2 = next.getStringValue(f2);
    assertEquals("Joern", v1);
    assertEquals("CAS Editor", v2);

    t = RutaTestUtils.getTestType(cas, 3);
    ai = cas.getAnnotationIndex(t);
    assertEquals(3, ai.size());
    iterator = ai.iterator();
    assertEquals("Peter", iterator.next().getCoveredText());
    assertEquals("Marshall", iterator.next().getCoveredText());
    assertEquals("Joern", iterator.next().getCoveredText());


    if (cas != null) {
      cas.release();
    }

  }
}
