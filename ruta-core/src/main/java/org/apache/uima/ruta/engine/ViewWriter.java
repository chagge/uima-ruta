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

package org.apache.uima.ruta.engine;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasMultiplier_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.AbstractCas;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.XmiCasSerializer;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.CasCopier;
import org.apache.uima.util.XMLSerializer;

public class ViewWriter extends JCasMultiplier_ImplBase {

  public static final String OUTPUT = "output";
  public static final String INPUT_VIEW = "inputView";
  public static final String OUTPUT_VIEW = "outputView";
  
  private String outputLocation;
  private String inputView;
  private String outputView;
  private CAS outView;

  @Override
  public void initialize(UimaContext aContext) throws ResourceInitializationException {
    super.initialize(aContext);
    outputLocation = (String) aContext.getConfigParameterValue(OUTPUT);
    inputView= (String) aContext.getConfigParameterValue(INPUT_VIEW);
    outputView= (String) aContext.getConfigParameterValue(OUTPUT_VIEW);
  }

  private static void writeXmi(CAS aCas, File name) throws Exception {
    FileOutputStream out = null;

    try {
      // write XMI
      out = new FileOutputStream(name);
      XmiCasSerializer ser = new XmiCasSerializer(aCas.getTypeSystem());
      XMLSerializer xmlSer = new XMLSerializer(out, false);
      ser.serialize(aCas, xmlSer.getContentHandler());
    } catch (Exception e) {
      throw e;
    } finally {
      if (out != null) {
        out.close();
      }
    }
  }

  @Override
  public void process(JCas jcas) throws AnalysisEngineProcessException {
    CAS cas = jcas.getCas();
    Type sdiType = cas.getTypeSystem().getType(RutaEngine.SOURCE_DOCUMENT_INFORMATION);

    File file = new File(outputLocation);
    if(file.isDirectory()) {
      String filename = "output.xmi";
      file = new File(outputLocation, filename);
    }
    if (sdiType != null) {
      FSIterator<AnnotationFS> sdiit = cas.getAnnotationIndex(sdiType).iterator();
      if (sdiit.isValid()) {
        AnnotationFS annotationFS = sdiit.get();
        Feature uriFeature = sdiType.getFeatureByBaseName("uri");
        String stringValue = annotationFS.getStringValue(uriFeature);
        File f = new File(stringValue);
        String name = f.getName();
        if (!name.endsWith(".xmi")) {
          name = name + ".xmi";
        }
        String parent = f.getParent().endsWith("/") ? f.getParent() : f.getParent() + "/";
        file = new File(parent + outputLocation, name);
        file.getParentFile().mkdirs();
      }

    }
    CAS inView = cas.getView(inputView);
    if(outView == null) {
      outView = getContext().getEmptyCas(CAS.class);
    }
    outView.reset();
    CasCopier cc = new CasCopier(inView, outView, true);
    cc.copyCasView(inView, outputView, true);
    try {
      writeXmi(outView, file);
    } catch (Exception e) {
      throw new AnalysisEngineProcessException(e);
    }
  }
  
  @Override
  public void collectionProcessComplete() {
    if(outView != null) {
      outView.release();
    }
    outView = null;
  }
  
  public boolean hasNext() throws AnalysisEngineProcessException {
    return false;
  }

  public AbstractCas next() throws AnalysisEngineProcessException {
    return null;
  }
}
