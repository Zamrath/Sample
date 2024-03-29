/* $Id: UnRegisterMapper.java 1498128 2013-06-30 14:15:49Z kwright $ */

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
package org.apache.manifoldcf.authorities;

import java.io.*;
import org.apache.manifoldcf.core.interfaces.*;
import org.apache.manifoldcf.authorities.interfaces.*;
import org.apache.manifoldcf.authorities.system.*;

public class UnRegisterMapper extends BaseMappersInitializationCommand
{
  public static final String _rcsid = "@(#)$Id: UnRegisterMapper.java 1498128 2013-06-30 14:15:49Z kwright $";

  private final String className;

  public UnRegisterMapper(String className)
  {
    this.className = className;
  }

  protected void doExecute(IMappingConnectorManager mgr) throws ManifoldCFException
  {
    mgr.unregisterConnector(className);
    Logging.root.info("Successfully unregistered connector '"+className+"'");
  }

  public static void main(String[] args)
  {
    if (args.length != 1)
    {
      System.err.println("Usage: UnRegisterMapper <classname>");
      System.exit(1);
    }

    String className = args[0];

    try
    {
      UnRegisterMapper unRegisterMapper = new UnRegisterMapper(className);
      unRegisterMapper.execute();
      System.err.println("Successfully unregistered connector '"+className+"'");
    }
    catch (ManifoldCFException e)
    {
      e.printStackTrace();
      System.exit(1);
    }
  }
}
