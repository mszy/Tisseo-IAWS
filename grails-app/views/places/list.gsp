
<%@ page import="tisseo.Places" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'places.label', default: 'Places')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-places" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-places" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${placesInstanceList}" status="i" var="placesInstance">
					
					
						<g:each in="${placesInstance.places}" status="j" var="place">
							<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
								<td>
									<g:link action="show" id="${place.id}">
								
										${fieldValue(bean: place, field: "shortName")}
									</g:link>
								</td>
							</tr>
						</g:each>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${placesInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
