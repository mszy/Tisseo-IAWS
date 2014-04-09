
<%@ page import="velo.LineVelo" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'lineVelo.label', default: 'LineVelo')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-lineVelo" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-lineVelo" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list lineVelo">
			
				<g:if test="${lineVeloInstance?.likesCount}">
				<li class="fieldcontain">
					<span id="likesCount-label" class="property-label"><g:message code="lineVelo.likesCount.label" default="Likes Count" /></span>
					
						<span class="property-value" aria-labelledby="likesCount-label"><g:fieldValue bean="${lineVeloInstance}" field="likesCount"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${lineVeloInstance?.dislikesCount}">
				<li class="fieldcontain">
					<span id="dislikesCount-label" class="property-label"><g:message code="lineVelo.dislikesCount.label" default="Dislikes Count" /></span>
					
						<span class="property-value" aria-labelledby="dislikesCount-label"><g:fieldValue bean="${lineVeloInstance}" field="dislikesCount"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${lineVeloInstance?.lineId}">
				<li class="fieldcontain">
					<span id="lineId-label" class="property-label"><g:message code="lineVelo.lineId.label" default="Line Id" /></span>
					
						<span class="property-value" aria-labelledby="lineId-label"><g:fieldValue bean="${lineVeloInstance}" field="lineId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${lineVeloInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="lineVelo.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${lineVeloInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${lineVeloInstance?.shortName}">
				<li class="fieldcontain">
					<span id="shortName-label" class="property-label"><g:message code="lineVelo.shortName.label" default="Short Name" /></span>
					
						<span class="property-value" aria-labelledby="shortName-label"><g:fieldValue bean="${lineVeloInstance}" field="shortName"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${lineVeloInstance?.id}" />
					<g:link class="edit" action="edit" id="${lineVeloInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
