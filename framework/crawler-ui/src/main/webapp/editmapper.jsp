<%@ include file="adminHeaders.jsp" %>

<%

/* $Id: editmapper.jsp 1551924 2013-12-18 12:43:40Z kwright $ */

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
%>

<%
    // The contract of this edit page is as follows.  It is either called directly, in which case it is expected to be creating
    // a connection or beginning the process of editing an existing connection, or it is called via redirection from execute.jsp, in which case
    // the connection object being edited will be placed in the thread context under the name "ConnectionObject".
    try
    {
	// Get the connection manager handle
	IMappingConnectionManager connMgr = MappingConnectionManagerFactory.make(threadContext);
	// Also get the list of available connectors
	IMappingConnectorManager connectorManager = MappingConnectorManagerFactory.make(threadContext);

	// Figure out what the current tab name is.
	String tabName = variableContext.getParameter("tabname");
	if (tabName == null || tabName.length() == 0)
		tabName = Messages.getString(pageContext.getRequest().getLocale(),"editmapper.Name");

	String connectionName = null;
	IMappingConnection connection = (IMappingConnection)threadContext.get("ConnectionObject");
	if (connection == null)
	{
		// We did not go through execute.jsp
		// We might have received an argument specifying the connection name.
		connectionName = variableContext.getParameter("connname");
		// If the connectionname is not null, load the connection description and prepopulate everything with what comes from it.
		if (connectionName != null && connectionName.length() > 0)
		{
			connection = connMgr.load(connectionName);
		}
	}

	// Setup default fields
	boolean isNew = true;
	String description = "";
	String className = "";
	int maxConnections = 10;
	ConfigParams parameters = new ConfigParams();
	String prereq = null;

	if (connection != null)
	{
		// Set up values
		isNew = connection.getIsNew();
		connectionName = connection.getName();
		description = connection.getDescription();
		className = connection.getClassName();
		parameters = connection.getConfigParams();
		maxConnections = connection.getMaxConnections();
		prereq = connection.getPrerequisiteMapping();
	}
	else
		connectionName = null;

	if (connectionName == null)
		connectionName = "";

	// Initialize tabs array
	ArrayList tabsArray = new ArrayList();

	// Set up the predefined tabs
	tabsArray.add(Messages.getString(pageContext.getRequest().getLocale(),"editmapper.Name"));
	tabsArray.add(Messages.getString(pageContext.getRequest().getLocale(),"editmapper.Type"));
	if (className.length() > 0)
	{
		tabsArray.add(Messages.getString(pageContext.getRequest().getLocale(),"editmapper.Prerequisites"));
		tabsArray.add(Messages.getString(pageContext.getRequest().getLocale(),"editmapper.Throttling"));
	}

%>

<?xml version="1.0" encoding="utf-8"?>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="StyleSheet" href="style.css" type="text/css" media="screen"/>
	<title>
		<%=Messages.getBodyString(pageContext.getRequest().getLocale(),"editmapper.ApacheManifoldCFEditMapping")%>
	</title>

	<script type="text/javascript">
	<!--
	// Use this method to repost the form and pick a new tab
	function SelectTab(newtab)
	{
		if (checkForm())
		{
			document.editconnection.tabname.value = newtab;
			document.editconnection.submit();
		}
	}

	// Use this method to repost the form,
	// and set the anchor request.
	function postFormSetAnchor(anchorValue)
	{
		if (checkForm())
		{
			if (anchorValue != "")
				document.editconnection.action = document.editconnection.action + "#" + anchorValue;
			document.editconnection.submit();
		}
	}

	// Use this method to repost the form
	function postForm()
	{
		if (checkForm())
		{
			document.editconnection.submit();
		}
	}

	function Save()
	{
		if (checkForm())
		{
			// Can't submit until all required fields have been set.
			// Some of these don't live on the current tab, so don't set
			// focus.

			// Check our part of the form, for save
			if (editconnection.connname.value == "")
			{
				alert("<%=Messages.getBodyJavascriptString(pageContext.getRequest().getLocale(),"editmapper.ConnectionMustHaveAName")%>");
				SelectTab("<%=Messages.getBodyJavascriptString(pageContext.getRequest().getLocale(),"editmapper.Name")%>");
				document.editconnection.connname.focus();
				return;
			}
			if (window.checkConfigForSave)
			{
				if (!checkConfigForSave())
					return;
			}
			document.editconnection.op.value="Save";
			document.editconnection.submit();
		}
	}

	function Continue()
	{
		document.editconnection.op.value="Continue";
		postForm();
	}

	function Cancel()
	{
		document.editconnection.op.value="Cancel";
		document.editconnection.submit();
	}

	function checkForm()
	{
		if (!checkConnectionCount())
			return false;
		if (window.checkConfig)
			return checkConfig();
		return true;
	}

	function checkConnectionCount()
	{
		if (!isInteger(editconnection.maxconnections.value))
		{
			alert("<%=Messages.getBodyJavascriptString(pageContext.getRequest().getLocale(),"editmapper.TheMaximumNumberOfConnectionsMustBeAValidInteger")%>");
			editconnection.maxconnections.focus();
			return false;
		}
		return true;
	}

	function isRegularExpression(value)
	{
		try
		{
			var foo = "teststring";
                        foo.search(value.replace(/\(\?i\)/,""));
			return true;
		}
		catch (e)
		{
			return false;
		}

	}

	function isInteger(value)
	{
		var anum=/(^\d+$)/;
		return anum.test(value);
	}

	//-->
	</script>
<%
	MappingConnectorFactory.outputConfigurationHeader(threadContext,className,new org.apache.manifoldcf.ui.jsp.JspWrapper(out,adminprofile),pageContext.getRequest().getLocale(),parameters,tabsArray);

	// Get connectors, since this will be needed to determine what to display.
	IResultSet set = connectorManager.getConnectors();

%>

</head>

<body class="standardbody">

    <table class="page">
      <tr><td colspan="2" class="banner"><jsp:include page="banner.jsp" flush="true"/></td></tr>
      <tr><td class="navigation"><jsp:include page="navigation.jsp" flush="true"/></td>
       <td class="darkwindow">


<%
	if (set.getRowCount() == 0)
	{
%>
	<p class="windowtitle"><%=Messages.getBodyString(pageContext.getRequest().getLocale(),"editmapper.EditMappingConnection")%></p>
	<table class="displaytable"><tr><td class="message"><%=Messages.getBodyString(pageContext.getRequest().getLocale(),"editmapper.NoMappingConnectorsRegistered")%></td></tr></table>
<%
	}
	else
	{
%>
	<form class="standardform" name="editconnection" action="execute.jsp" method="POST" enctype="multipart/form-data">
	  <input type="hidden" name="op" value="Continue"/>
	  <input type="hidden" name="type" value="mapper"/>
	  <input type="hidden" name="tabname" value='<%=org.apache.manifoldcf.ui.util.Encoder.attributeEscape(tabName)%>'/>
	  <input type="hidden" name="isnewconnection" value='<%=(isNew?"true":"false")%>'/>
	    <table class="tabtable">
	      <tr class="tabrow">
<%
	  int tabNum = 0;
	  while (tabNum < tabsArray.size())
	  {
		String tab = (String)tabsArray.get(tabNum++);
		if (tab.equals(tabName))
		{
%>
		      <td class="activetab"><nobr><%=org.apache.manifoldcf.ui.util.Encoder.bodyEscape(tab)%></nobr></td>
<%
		}
		else
		{
%>
		      <td class="passivetab"><nobr><a href="javascript:void(0);" alt='<%=org.apache.manifoldcf.ui.util.Encoder.attributeEscape(tab)+" "+Messages.getAttributeString(pageContext.getRequest().getLocale(),"editmapper.tab")%>' onclick='<%="javascript:SelectTab(\""+tab+"\");return false;"%>'><%=org.apache.manifoldcf.ui.util.Encoder.bodyEscape(tab)%></a></nobr></td>
<%
		}
	  }
%>
		      <td class="remaindertab">
<%
	  if (description.length() > 0)
	  {
%>
			  <nobr><%=Messages.getBodyString(pageContext.getRequest().getLocale(),"editmapper.EditMapping")%> '<%=org.apache.manifoldcf.ui.util.Encoder.bodyEscape(description)%>'</nobr>
<%
	  }
	  else
	  {
%>
		          <nobr><%=Messages.getBodyString(pageContext.getRequest().getLocale(),"editmapper.EditAMapping")%></nobr>
<%
	  }
%>
		      </td>
	      </tr>
	      <tr class="tabbodyrow">
		<td class="tabbody" colspan='<%=Integer.toString(tabsArray.size()+1)%>'>

<%

	  // Name tab
	  if (tabName.equals(Messages.getString(pageContext.getRequest().getLocale(),"editmapper.Name")))
	  {
%>
		    <table class="displaytable">
			<tr><td class="separator" colspan="5"><hr/></td></tr>
			<tr>
				<td class="description"><nobr><%=Messages.getBodyString(pageContext.getRequest().getLocale(),"editmapper.NameColon")%></nobr></td>
				<td class="value" colspan="4">
<%
	    // If the connection doesn't exist yet, we are allowed to change the name.
	    if (isNew)
	    {
%>
					<input type="text" size="32" name="connname" value='<%=org.apache.manifoldcf.ui.util.Encoder.attributeEscape(connectionName)%>'/>
<%
	    }
	    else
	    {
%>
					<%=org.apache.manifoldcf.ui.util.Encoder.bodyEscape(connectionName)%>
					<input type="hidden" name="connname" value='<%=org.apache.manifoldcf.ui.util.Encoder.attributeEscape(connectionName)%>'/>
<%
	    }
%>
				</td>
			</tr>
			<tr>
				<td class="description"><nobr><%=Messages.getBodyString(pageContext.getRequest().getLocale(),"editmapper.DescriptionColon")%></nobr></td>
				<td class="value" colspan="4">
					<input type="text" size="50" name="description" value='<%=org.apache.manifoldcf.ui.util.Encoder.attributeEscape(description)%>'/>
				</td>
			</tr>
		    </table>
<%
	  }
	  else
	  {
		// Hiddens for the Name tab
%>
		    <input type="hidden" name="connname" value='<%=org.apache.manifoldcf.ui.util.Encoder.attributeEscape(connectionName)%>'/>
		    <input type="hidden" name="description" value='<%=org.apache.manifoldcf.ui.util.Encoder.attributeEscape(description)%>'/>
<%
	  }


	  // "Type" tab
	  if (tabName.equals(Messages.getString(pageContext.getRequest().getLocale(),"editmapper.Type")))
	  {
%>
		    <table class="displaytable">
			<tr><td class="separator" colspan="5"><hr/></td></tr>
			<tr>
				<td class="description"><nobr><%=Messages.getBodyString(pageContext.getRequest().getLocale(),"editmapper.ConnectionTypeColon")%></nobr></td><td class="value" colspan="4">
<%
	    if (className.length() > 0)
	    {
		String value = connectorManager.getDescription(className);
		if (value == null)
		{
%>
					<nobr><%=Messages.getBodyString(pageContext.getRequest().getLocale(),"editmapper.UNREGISTERED")%> <%=org.apache.manifoldcf.ui.util.Encoder.bodyEscape(className)%></nobr>
<%
		}
		else
		{
%>
					<%=org.apache.manifoldcf.ui.util.Encoder.bodyEscape(value)%>
<%
		}
%>
					<input type="hidden" name="classname" value='<%=org.apache.manifoldcf.ui.util.Encoder.attributeEscape(className)%>'/>
<%
	    }
	    else
	    {
		int i = 0;
%>
					<select name="classname" size="1">
<%
		while (i < set.getRowCount())
		{
			IResultRow row = set.getRow(i++);
			String thisClassName = row.getValue("classname").toString();
			String thisDescription = row.getValue("description").toString();
%>
						<option value='<%=org.apache.manifoldcf.ui.util.Encoder.attributeEscape(thisClassName)%>'
							<%=className.equals(thisClassName)?"selected=\"selected\"":""%>><%=org.apache.manifoldcf.ui.util.Encoder.bodyEscape(thisDescription)%></option>
<%
		}
%>
					</select>
<%
	    }
%>
				</td>
			</tr>
		    </table>
<%
	  }
	  else
	  {
		// Hiddens for the "Type" tab
%>
		    <input type="hidden" name="classname" value='<%=org.apache.manifoldcf.ui.util.Encoder.attributeEscape(className)%>'/>
<%
	  }

	  // The "Prerequisites" tab
	  IMappingConnection[] mappingConnections = connMgr.getAllNonLoopingConnections((connection==null)?null:connection.getName());
	  if (tabName.equals(Messages.getString(pageContext.getRequest().getLocale(),"editmapper.Prerequisites")))
	  {
%>
		    <table class="displaytable">
			<tr><td class="separator" colspan="5"><hr/></td></tr>
			<tr>
				<td class="description"><nobr><%=Messages.getBodyString(pageContext.getRequest().getLocale(),"editmapper.PrerequisiteUserMappingColon")%></nobr></td>
				<td class="value" colspan="4">
					<input type="hidden" name="prerequisites_present" value="true"/>
<%
	    if (prereq == null)
	    {
%>
					<input type="radio" name="prerequisites" value="" checked="true"/>&nbsp;<%=Messages.getBodyString(pageContext.getRequest().getLocale(),"editmapper.NoPrerequisites")%><br/>
<%
	    }
	    else
	    {
%>
					<input type="radio" name="prerequisites" value=""/>&nbsp;<%=Messages.getBodyString(pageContext.getRequest().getLocale(),"editmapper.NoPrerequisites")%><br/>
<%
	    }

	    for (IMappingConnection mappingConnection : mappingConnections)
	    {
		String mappingName = mappingConnection.getName();
		String mappingDescription = mappingName;
		if (mappingConnection.getDescription() != null && mappingConnection.getDescription().length() > 0)
			mappingDescription += " (" + mappingConnection.getDescription()+")";
		if (prereq != null && prereq.equals(mappingName))
		{
%>
					<input type="radio" name="prerequisites" value='<%=org.apache.manifoldcf.ui.util.Encoder.attributeEscape(mappingName)%>' checked="true"/>&nbsp;<%=org.apache.manifoldcf.ui.util.Encoder.bodyEscape(mappingDescription)%><br/>
<%
		}
		else
		{
%>
					<input type="radio" name="prerequisites" value='<%=org.apache.manifoldcf.ui.util.Encoder.attributeEscape(mappingName)%>'/>&nbsp;<%=org.apache.manifoldcf.ui.util.Encoder.bodyEscape(mappingDescription)%><br/>
<%
		}
	    }
%>
				</td>
			</tr>
		    </table>
<%
	  }
	  else
	  {
		// Hiddens for Prerequisites tab
%>
		    <input type="hidden" name="prerequisites_present" value="true"/>
<%
		if (prereq != null)
		{
%>
		    <input type="hidden" name="prerequisites" value='<%=org.apache.manifoldcf.ui.util.Encoder.attributeEscape(prereq)%>'/>
<%
		}
	  }

	  // The "Throttling" tab
	  if (tabName.equals(Messages.getString(pageContext.getRequest().getLocale(),"editmapper.Throttling")))
	  {
%>
		    <table class="displaytable">
			<tr><td class="separator" colspan="5"><hr/></td></tr>
			<tr>
				<td class="description"><nobr><%=Messages.getBodyString(pageContext.getRequest().getLocale(),"editmapper.MaxConnectionsColon")%></nobr></td>
				<td class="value" colspan="4"><input type="text" size="6" name="maxconnections" value='<%=Integer.toString(maxConnections)%>'/></td>
			</tr>
		    </table>
<%
	  }
	  else
	  {
		// Hiddens for "Throttling" tab
%>
		    <input type="hidden" name="maxconnections" value='<%=Integer.toString(maxConnections)%>'/>
<%
	  }

	  if (className.length() > 0)
		MappingConnectorFactory.outputConfigurationBody(threadContext,className,new org.apache.manifoldcf.ui.jsp.JspWrapper(out,adminprofile),pageContext.getRequest().getLocale(),parameters,tabName);
%>
		    <table class="displaytable">
			<tr><td class="separator" colspan="4"><hr/></td></tr>
			<tr><td class="message" colspan="4"><nobr>
<%
	  if (className.length() > 0)
	  {
%>
			    <input type="button" value="<%=Messages.getAttributeString(pageContext.getRequest().getLocale(),"editmapper.Save")%>" onClick="javascript:Save()" alt="<%=Messages.getAttributeString(pageContext.getRequest().getLocale(),"editmapper.SaveThisMappingConnection")%>"/>
<%
	  }
	  else
	  {
		if (tabName.equals(Messages.getString(pageContext.getRequest().getLocale(),"editmapper.Type")))
		{
%>
			    <input type="button" value="<%=Messages.getAttributeString(pageContext.getRequest().getLocale(),"editmapper.Continue")%>" onClick="javascript:Continue()" alt="<%=Messages.getAttributeString(pageContext.getRequest().getLocale(),"editmapper.ContinueToNextPage")%>"/>
<%
		}
	  }
%>
			    &nbsp;<input type="button" value="<%=Messages.getAttributeString(pageContext.getRequest().getLocale(),"editmapper.Cancel")%>" onClick="javascript:Cancel()" alt="<%=Messages.getAttributeString(pageContext.getRequest().getLocale(),"editmapper.CancelMappingEditing")%>"/></nobr></td>
			</tr>
		    </table>
		</td>
	      </tr>
	    </table>
	</form>
<%
	}
%>
       </td>
      </tr>
    </table>

</body>

</html>

<%
    }
    catch (ManifoldCFException e)
    {
	e.printStackTrace();
	variableContext.setParameter("text",e.getMessage());
	variableContext.setParameter("target","listmappers.jsp");
%>
	<jsp:forward page="error.jsp"/>
<%
    }
%>

