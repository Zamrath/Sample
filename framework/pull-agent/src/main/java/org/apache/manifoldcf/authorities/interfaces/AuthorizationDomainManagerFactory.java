/* $Id: AuthorizationDomainManagerFactory.java 1535983 2013-10-26 12:46:18Z kwright $ */

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
package org.apache.manifoldcf.authorities.interfaces;

import org.apache.manifoldcf.core.interfaces.*;
import java.util.*;
import org.apache.manifoldcf.authorities.system.ManifoldCF;

/** This class is the factory for the Authorization Domain Manager.
*/
public class AuthorizationDomainManagerFactory
{
  protected static final String connMgr = "_AuthorizationDomainManager_";

  private AuthorizationDomainManagerFactory()
  {
  }

  /** Construct a connector manager.
  *@param tc is the thread context.
  *@return the connector manager handle.
  */
  public static IAuthorizationDomainManager make(IThreadContext tc)
    throws ManifoldCFException
  {
    Object o = tc.get(connMgr);
    if (o == null || !(o instanceof IAuthorizationDomainManager))
    {

      IDBInterface database = DBInterfaceFactory.make(tc,
        ManifoldCF.getMasterDatabaseName(),
        ManifoldCF.getMasterDatabaseUsername(),
        ManifoldCF.getMasterDatabasePassword());

      o = new org.apache.manifoldcf.authorities.authdomains.AuthorizationDomainManager(tc,database);
      tc.save(connMgr,o);
    }
    return (IAuthorizationDomainManager)o;
  }


}
