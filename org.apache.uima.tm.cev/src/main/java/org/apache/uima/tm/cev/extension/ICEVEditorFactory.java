package org.apache.uima.tm.cev.extension;

import org.apache.uima.tm.cev.data.CEVDocument;
import org.apache.uima.tm.cev.editor.CEVViewer;

public interface ICEVEditorFactory {

  ICEVEditor createEditor(CEVViewer viewer, CEVDocument cevDocument, int index);

  Class<?> getAdapterInterface();
}