/*******************************************************************************
 * Copyright (c) 2011 Sonatype, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.m2e.core.ui.internal.editing;

import org.apache.maven.model.Dependency;
import org.eclipse.m2e.core.embedder.ArtifactKey;
import static org.eclipse.m2e.core.ui.internal.editing.PomEdits.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class AddExclusionOperation implements Operation {

  private Dependency dependency;

  private ArtifactKey exclusion;

  public AddExclusionOperation(Dependency dependency, ArtifactKey exclusion) {
    this.dependency = dependency;
    this.exclusion = exclusion;
  }

  /* (non-Javadoc)
   * @see org.eclipse.m2e.core.ui.internal.editing.PomEdits.Operation#process(org.w3c.dom.Document)
   */
  public void process(Document document) {
    Element depElement = PomHelper.findDependency(document, dependency);

    if(depElement == null) {
      //TODO we shall not throw exceptions from operations..
      throw new IllegalArgumentException("Dependency does not exist in this pom");
    }
    Element exclusionsElement = getChild(depElement, EXCLUSIONS);

    Element exclusionElement = createElement(exclusionsElement, EXCLUSION);

    createElementWithText(exclusionElement, ARTIFACT_ID, exclusion.getArtifactId());
    createElementWithText(exclusionElement, GROUP_ID, exclusion.getGroupId());
    //TODO mkleint: are there really exclusion versions??
    createElementWithText(exclusionElement, VERSION, exclusion.getVersion());
  }
}
