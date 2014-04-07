<%@ page import="tisseo.Line" %>

<div class="fieldcontain ${hasErrors(bean: lineInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="line.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${lineInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: lineInstance, field: 'shortName', 'error')} ">
	<label for="shortName">
		<g:message code="line.shortName.label" default="Short Name" />
		
	</label>
	<g:textField name="shortName" value="${lineInstance?.shortName}"/>
</div>

