/* $Id: DeleteStartupResetManager.java 1545624 2013-11-26 11:45:53Z kwright $ */

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
package org.apache.manifoldcf.crawler.system;

import org.apache.manifoldcf.core.interfaces.*;
import org.apache.manifoldcf.agents.interfaces.*;
import org.apache.manifoldcf.crawler.interfaces.*;
import org.apache.manifoldcf.crawler.system.Logging;
import java.util.*;
import java.lang.reflect.*;

/** Class which handles reset for seeding thread pool (of which there's
* typically only one member).  The reset action here
* is to move the status of jobs back from "seeding" to normal.
*/
public class DeleteStartupResetManager extends ResetManager
{

  /** Constructor. */
  public DeleteStartupResetManager(String processID)
  {
    super(processID);
  }

  /** Reset */
  @Override
  protected void performResetLogic(IThreadContext tc, String processID)
    throws ManifoldCFException
  {
    IJobManager jobManager = JobManagerFactory.make(tc);
    jobManager.resetDeleteStartupWorkerStatus(processID);
  }
    
  /** Do the wakeup logic.
  */
  @Override
  protected void performWakeupLogic()
  {
  }

}
