/* $Id: ClauseDescription.java 1195471 2011-10-31 14:26:33Z kwright $ */

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
package org.apache.manifoldcf.core.interfaces;

import java.util.*;

public interface ClauseDescription
{
  public static final String _rcsid = "@(#)$Id: ClauseDescription.java 1195471 2011-10-31 14:26:33Z kwright $";

  /** Get the column name */
  public String getColumnName();
  
  /** Get the operation string */
  public String getOperation();
  
  /** Get the individual values */
  public List getValues();
  
  /** Get the join column name */
  public String getJoinColumnName();
  
}
