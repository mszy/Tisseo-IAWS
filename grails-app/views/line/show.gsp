


<%@ page import="tisseo.Line" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'line.label', default: 'Line')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
			</ul>
		</div>
		<div id="show-line" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list line">
			
				<g:if test="${lineInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="line.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${lineInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${lineInstance?.shortName}">
				<li class="fieldcontain">
					<span id="shortName-label" class="property-label"><g:message code="line.shortName.label" default="Short Name" /></span>
					
						<span class="property-value" aria-labelledby="shortName-label"><g:fieldValue bean="${lineInstance}" field="shortName"/></span>
					
				</li>
				</g:if>
			
				<li class="fieldcontain">
					<span id="likes-label" class="property-label"><g:message code="line.likes.label" default="Likes" /></span>
					
						<span class="property-value">${lineInstance.likesCount >= lineInstance.dislikesCount ? "+" : "-"}${Math.abs(lineInstance.likesCount - lineInstance.dislikesCount)}
						(Total des votes : ${lineInstance.likesCount + lineInstance.dislikesCount})</span>
					
				</li>
			
			</ol>
				
			<table>
				<thead>
					<tr>
						<g:sortableColumn property="name" title="${message(code: 'stopPoint.name.label', default: 'Stop point name')}" />
					
						<g:sortableColumn property="nextBus" title="${message(code: 'stopPoint.nextBus.label', default: 'Next Bus')}" />
					
					</tr>
				</thead>
				<tbody>
					<g:each in="${stopPoints}" status="i" var="stopPointInstance">
						<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						
							<td>${fieldValue(bean: stopPointInstance, field: "name")}</td>
						
							<td><g:formatDate format="HH:mm" date="${stopPointInstance.nextBus}"/></td>
						
						</tr>
					</g:each>
				</tbody>
			</table>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${lineInstance?.id}" />
					<g:actionSubmit class="like" action="like" value="${message(code: 'default.button.like.label', default: 'Like')}" />
					<g:actionSubmit class="dislike" action="dislike" value="${message(code: 'default.button.dislike.label', default: 'Dislike')}" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
