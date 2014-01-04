/* $Id: RewindableTokenStream.java 1160012 2011-08-21 17:10:20Z kwright $ */

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

package org.apache.manifoldcf.scriptengine;

import java.util.*;

/** Allows a TokenStream to be repeated as long as a sequence start is kept.
*/
public class RewindableTokenStream implements TokenStream
{
  protected TokenStream parent;
  protected List<Token> history = new ArrayList<Token>();
  protected int currentIndex = 0;
  protected int committedSize = 0;
  
  public RewindableTokenStream(TokenStream parent)
  {
    this.parent = parent;
  }
  
  public Token peek()
    throws ScriptException
  {
    if (history.size() > currentIndex)
      return history.get(currentIndex);
    Token rval = parent.peek();
    history.add(rval);
    return rval;
  }
  
  public void skip()
  {
    currentIndex++;
    if (currentIndex > committedSize)
    {
      parent.skip();
      committedSize++;
    }
    if (currentIndex > committedSize)
      throw new RuntimeException("Two skips in a row!");
  }
  
  public void reset()
  {
    currentIndex = 0;
  }
  
}
