/* $Id: BaseDerby.java 1227011 2012-01-04 01:02:59Z kwright $ */

/**
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements. See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License. You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.apache.manifoldcf.webcrawler_tests;

import org.apache.manifoldcf.core.interfaces.*;
import org.apache.manifoldcf.agents.interfaces.*;
import org.apache.manifoldcf.crawler.interfaces.*;
import org.apache.manifoldcf.crawler.system.ManifoldCF;

import java.io.*;
import java.util.*;
import org.junit.*;

/** Tests that run the "agents daemon" should be derived from this */
public class BaseDerby extends org.apache.manifoldcf.crawler.tests.BaseITDerby
{
  protected String[] getConnectorNames()
  {
    return new String[]{"Web Connector"};
  }
  
  protected String[] getConnectorClasses()
  {
    return new String[]{"org.apache.manifoldcf.crawler.connectors.webcrawler.WebcrawlerConnector"};
  }
  
  protected String[] getOutputNames()
  {
    return new String[]{"Null Output"};
  }
  
  protected String[] getOutputClasses()
  {
    return new String[]{"org.apache.manifoldcf.agents.output.nullconnector.NullConnector"};
  }
  
}
