
<%@ page import="tisseo.Line" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'line.label', default: 'Line')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-line" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
		<!--  
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
			</ul>
		-->
		</div>
		<div id="list-line" class="content scaffold-list" role="main">
			<h1>Lignes de bus<!-- <g:message code="default.list.label" args="[entityName]" /> --></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
						<g:sortableColumn property="name" title="${message(code: 'line.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="shortName" title="${message(code: 'line.shortName.label', default: 'Short Name')}" />
						
						<g:sortableColumn property="transportMode" title="${message(code: 'line.votes.label', default: 'Votes')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${Line.findAllByTransportMode( "bus" )}" status="i" var="lineInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${lineInstance.id}">${fieldValue(bean: lineInstance, field: "name")}</g:link></td>
					
						<td>${fieldValue(bean: lineInstance, field: "shortName")}</td>
						
						<td>${lineInstance.likesCount >= lineInstance.dislikesCount ? "+" : "-"}${Math.abs(lineInstance.likesCount - lineInstance.dislikesCount)}
						(${lineInstance.likesCount + lineInstance.dislikesCount})</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${lineInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
