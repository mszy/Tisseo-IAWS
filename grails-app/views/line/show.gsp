


<%@ page import="tisseo.Line" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'line.label', default: 'Line')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-line" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
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
					
						<span class="property-value" aria-labelledby="likes-label">${lineInstance.likesCount >= lineInstance.dislikesCount ? "+" : "-"}${Math.abs(lineInstance.likesCount - lineInstance.dislikesCount)}</span>
					
				</li>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${lineInstance?.id}" />
					<g:link class="edit" action="edit" id="${lineInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
					<g:actionSubmit class="like" action="like" value="${message(code: 'default.button.like.label', default: 'Like')}" />
					<g:actionSubmit class="dislike" action="dislike" value="${message(code: 'default.button.dislike.label', default: 'Dislike')}" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
