
<%@ page import="velo.LineVelo" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'lineVelo.label', default: 'LineVelo')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-lineVelo" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-lineVelo" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="likesCount" title="${message(code: 'lineVelo.likesCount.label', default: 'Likes Count')}" />
					
						<g:sortableColumn property="dislikesCount" title="${message(code: 'lineVelo.dislikesCount.label', default: 'Dislikes Count')}" />
					
						<g:sortableColumn property="lineId" title="${message(code: 'lineVelo.lineId.label', default: 'Line Id')}" />
					
						<g:sortableColumn property="name" title="${message(code: 'lineVelo.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="shortName" title="${message(code: 'lineVelo.shortName.label', default: 'Short Name')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${lineVeloInstanceList}" status="i" var="lineVeloInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${lineVeloInstance.id}">${fieldValue(bean: lineVeloInstance, field: "likesCount")}</g:link></td>
					
						<td>${fieldValue(bean: lineVeloInstance, field: "dislikesCount")}</td>
					
						<td>${fieldValue(bean: lineVeloInstance, field: "lineId")}</td>
					
						<td>${fieldValue(bean: lineVeloInstance, field: "name")}</td>
					
						<td>${fieldValue(bean: lineVeloInstance, field: "shortName")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${lineVeloInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
