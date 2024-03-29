/* $Id: DecodingByteReceiver.java 1443879 2013-02-08 08:33:41Z kwright $ */

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
package org.apache.manifoldcf.core.fuzzyml;

import org.apache.manifoldcf.core.interfaces.*;
import java.io.*;

/** This class represents a ByteReceiver that passes
* decoded characters on to a supplied CharacterReceiver.
*/
public class DecodingByteReceiver extends ByteReceiver
{
  protected final CharacterReceiver charReceiver;
  protected final String charSet;
  
  public DecodingByteReceiver(int chunkSize, String charSet, CharacterReceiver charReceiver)
  {
    super();
    this.charSet = charSet;
    this.charReceiver = charReceiver;
  }
  
  /** Read a byte stream and process bytes.
  *@return true if abort signalled, false if end of stream reached.
  */
  @Override
  public final boolean dealWithBytes(InputStream is)
    throws IOException, ManifoldCFException
  {
    // Create a reader based on the encoding and the input stream
    Reader reader = new InputStreamReader(is,charSet);
    return charReceiver.dealWithCharacters(reader);
  }
  
  /** Finish up all processing.
  */
  @Override
  public void finishUp()
    throws ManifoldCFException
  {
    super.finishUp();
    charReceiver.finishUp();
  }

}
