/* $Id: NewConnectionName.java 1449684 2013-02-25 12:47:13Z kwright $ */

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

/** Class defining the creation of a new Connection Name object.
*/
public class NewConnectionName implements NewOperation
{
  /** Parse and execute.  Parsing begins right after the "new" keyword and the operation name token.
  *@param sp is the script parser to use to help in the parsing.
  *@param currentStream is the current token stream.
  *@return the variable reference that got created.  Should never be null.
  */
  @Override
  public VariableReference parseAndCreate(ScriptParser sp, TokenStream currentStream)
    throws ScriptException
  {
    VariableReference connectionName = sp.evaluateExpression(currentStream);
    if (connectionName == null)
      sp.syntaxError(currentStream,"Need connection name expression");
    String connectionNameString = sp.resolveMustExist(currentStream,connectionName).getStringValue();
    return new VariableConnectionName(connectionNameString);
  }
  
  /** Parse and skip.  Parsing begins right after the "new" keyword and the operation name token.
  *@param sp is the script parser to use to help in the parsing.
  *@param currentStream is the current token stream.
  */
  @Override
  public void parseAndSkip(ScriptParser sp, TokenStream currentStream)
    throws ScriptException
  {
    sp.skipExpression(currentStream);
  }
  
}
