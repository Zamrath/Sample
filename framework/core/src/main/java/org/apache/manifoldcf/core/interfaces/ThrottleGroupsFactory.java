/* $Id: ThrottleGroupsFactory.java 1551707 2013-12-17 21:12:42Z kwright $ */

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

/** Thread-local IThrottleGroups factory.
*/
public class ThrottleGroupsFactory
{
  public static final String _rcsid = "@(#)$Id: ThrottleGroupsFactory.java 1551707 2013-12-17 21:12:42Z kwright $";

  // name to use in thread context pool of objects
  private final static String objectName = "_ThrottleGroups_";

  private ThrottleGroupsFactory()
  {
  }

  /** Make a connection throttle handle.
  *@param tc is the thread context.
  *@return the handle.
  */
  public static IThrottleGroups make(IThreadContext tc)
    throws ManifoldCFException
  {
    Object o = tc.get(objectName);
    if (o == null || !(o instanceof IThrottleGroups))
    {
      o = new org.apache.manifoldcf.core.throttler.ThrottleGroups(tc);
      tc.save(objectName,o);
    }
    return (IThrottleGroups)o;
  }

}
