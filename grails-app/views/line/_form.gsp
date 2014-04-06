<%@ page import="tisseo.Line" %>



<div class="fieldcontain ${hasErrors(bean: lineInstance, field: 'dislikesCount', 'error')} required">
	<label for="dislikesCount">
		<g:message code="line.dislikesCount.label" default="Dislikes Count" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="dislikesCount" type="number" value="${lineInstance.dislikesCount}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: lineInstance, field: 'likesCount', 'error')} required">
	<label for="likesCount">
		<g:message code="line.likesCount.label" default="Likes Count" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="likesCount" type="number" value="${lineInstance.likesCount}" required=""/>
</div>

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

