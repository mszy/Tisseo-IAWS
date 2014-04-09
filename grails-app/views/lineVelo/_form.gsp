<%@ page import="velo.LineVelo" %>



<div class="fieldcontain ${hasErrors(bean: lineVeloInstance, field: 'likesCount', 'error')} required">
	<label for="likesCount">
		<g:message code="lineVelo.likesCount.label" default="Likes Count" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="likesCount" type="number" min="0" value="${lineVeloInstance.likesCount}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: lineVeloInstance, field: 'dislikesCount', 'error')} required">
	<label for="dislikesCount">
		<g:message code="lineVelo.dislikesCount.label" default="Dislikes Count" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="dislikesCount" type="number" min="0" value="${lineVeloInstance.dislikesCount}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: lineVeloInstance, field: 'lineId', 'error')} ">
	<label for="lineId">
		<g:message code="lineVelo.lineId.label" default="Line Id" />
		
	</label>
	<g:textField name="lineId" value="${lineVeloInstance?.lineId}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: lineVeloInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="lineVelo.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${lineVeloInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: lineVeloInstance, field: 'shortName', 'error')} ">
	<label for="shortName">
		<g:message code="lineVelo.shortName.label" default="Short Name" />
		
	</label>
	<g:textField name="shortName" value="${lineVeloInstance?.shortName}"/>
</div>

