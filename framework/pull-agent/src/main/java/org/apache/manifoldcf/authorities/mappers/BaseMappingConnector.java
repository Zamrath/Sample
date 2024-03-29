/* $Id: BaseMappingConnector.java 1495814 2013-06-23 13:10:02Z kwright $ */

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
package org.apache.manifoldcf.authorities.mappers;

import org.apache.manifoldcf.core.interfaces.*;
import org.apache.manifoldcf.authorities.interfaces.*;

/** A mapping connector massages a UserRecord to augment the user identification information within.
*
* An instance of this interface provides this functionality.  Mapping connector instances are pooled, so that session
* setup does not need to be done repeatedly.  The pool is segregated by specific sets of configuration parameters.
*/
public abstract class BaseMappingConnector extends org.apache.manifoldcf.core.connector.BaseConnector implements IMappingConnector
{
  public static final String _rcsid = "@(#)$Id: BaseMappingConnector.java 1495814 2013-06-23 13:10:02Z kwright $";

  // This class is provided for future backwards compatibility reasons, so it is wise to
  // extend it.

}
