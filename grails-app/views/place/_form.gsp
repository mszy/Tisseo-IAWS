<%@ page import="tisseo.Place" %>



<div class="fieldcontain ${hasErrors(bean: placeInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="place.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${placeInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: placeInstance, field: 'shortName', 'error')} ">
	<label for="shortName">
		<g:message code="place.shortName.label" default="Short Name" />
		
	</label>
	<g:textField name="shortName" value="${placeInstance?.shortName}"/>
</div>

