<!DOCTYPE html>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<html>
    <head>
       <tiles:insertAttribute name="header" />
       <tiles:insertAttribute name="backbonetmpl" ignore="true"/>
       <tiles:insertAttribute name="jsimports" />
    </head>
    <body>
		<tiles:insertAttribute name="navbar" ignore="true" />
		<tiles:insertAttribute name="body" />
	    <tiles:insertAttribute name="footer" ignore="true"  />
    </body>
</html>