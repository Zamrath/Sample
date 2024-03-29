/* $Id: ReprioritizationTrackerFactory.java 1549594 2013-12-09 15:09:02Z kwright $ */

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
package org.apache.manifoldcf.crawler.interfaces;

import org.apache.manifoldcf.core.interfaces.*;
import org.apache.manifoldcf.crawler.system.*;

/** Factory class for IReprioritizationTracker.
*/
public class ReprioritizationTrackerFactory
{
  public static final String _rcsid = "@(#)$Id: ReprioritizationTrackerFactory.java 1549594 2013-12-09 15:09:02Z kwright $";

  // Name
  protected final static String reprioritizationTrackerName = "_ReprioritizationTracker_";

  private ReprioritizationTrackerFactory()
  {
  }

  /** Create a reprioritization tracker handle.
  *@param threadContext is the thread context.
  *@return the handle.
  */
  public static IReprioritizationTracker make(IThreadContext threadContext)
    throws ManifoldCFException
  {
    Object o = threadContext.get(reprioritizationTrackerName);
    if (o == null || !(o instanceof IReprioritizationTracker))
    {
      o = new org.apache.manifoldcf.crawler.reprioritizationtracker.ReprioritizationTracker(threadContext);
      threadContext.save(reprioritizationTrackerName,o);
    }
    return (IReprioritizationTracker)o;
  }

}
