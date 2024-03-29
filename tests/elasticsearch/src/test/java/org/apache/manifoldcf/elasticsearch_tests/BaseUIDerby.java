/* $Id: BaseUIDerby.java 1244653 2012-02-15 19:14:36Z piergiorgio $ */

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
package org.apache.manifoldcf.elasticsearch_tests;

/** Tests that run the "agents daemon" should be derived from this */
public class BaseUIDerby extends org.apache.manifoldcf.crawler.tests.ConnectorBaseUIDerby
{
  protected String[] getConnectorNames()
  {
    return new String[]{"CMIS Connector"};
  }
  
  protected String[] getConnectorClasses()
  {
    return new String[]{"org.apache.manifoldcf.crawler.connectors.cmis.CmisRepositoryConnector"};
  }
  
  protected String[] getOutputNames()
  {
    return new String[]{"ElasticSearch"};
  }
  
  protected String[] getOutputClasses()
  {
    return new String[]{"org.apache.manifoldcf.agents.output.elasticsearch.ElasticSearchConnector"};
  }

  protected String[] getAuthorityClasses()
  {
    return new String[]{"org.apache.manifoldcf.crawler.connectors.cmis.CmisAuthorityConnector"};
  }
  
  protected String[] getAuthorityNames()
  {
    return new String[]{"CMIS authority"};
  }

}
