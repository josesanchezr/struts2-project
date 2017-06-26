<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjdt" uri="/struts-jquery-datatables-tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>List of institutions</title>
<sj:head jquerytheme="start" jqueryui="true" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>
	<div class="container">
		<h3>List of institutions</h3>

		<s:url var="remoteurl" action="listrequest" namespace="/datatables" />
		<sjdt:datatables ajax="{url:'%{remoteurl}',dataSrc:'listRequest'}"
			datatablesTheme="jqueryui" responsive="true" style="width:100%;"
			processing="true" dom="Blfrtip" buttons="true"
			columns="[
            {data:'headquarter',title:'Headquarter'},
            {data:'acronym',title:'Acronym'},
            {data:'name',title:'Name'},
            {data:'city',title:'City'},
            {data:'country',title:'Country'}
]">
			<caption class="ui-widget-header">List of institutions</caption>
		</sjdt:datatables>
		<a class="btn btn-info" href="request.jsp" role="button"> <span
			class="glyphicon glyphicon-plus" aria-hidden="true"></span> Add
			Institution
		</a>
	</div>
</body>
</html>