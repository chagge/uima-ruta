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

package org.apache.uima.cev.data.tree;

import org.apache.uima.cas.Type;

public class CEVTypeTreeNode extends CEVAbstractTreeNode {

  private Type type;

  public CEVTypeTreeNode(Type type) {
    this(null, type);
  }

  public CEVTypeTreeNode(ICEVTreeNode parent, Type type) {
    super(parent);
    this.type = type;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.apache.uima.cev.data.tree.ICEVTreeNode#getName()
   */
  public String getName() {
    return type.getName();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.apache.uima.cev.data.tree.ICEVTreeNode#getType()
   */
  public Type getType() {
    return type;
  }
}