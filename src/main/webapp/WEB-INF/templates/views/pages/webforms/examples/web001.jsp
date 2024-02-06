<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../../partials/head.jsp"/>

<h4>${example.getExampleName()}</h4>
<p>${example.getExampleDescription()}</p>

<p>
    API methods used:
    <a target='_blank' href="https://developers.docusign.com/docs/esign-rest-api/reference/templates/templates/list/">Templates::list</a>, and
    <a target='_blank' href="https://developers.docusign.com/docs/esign-rest-api/reference/templates/templates/create/">Templates::create</a>.
</p>

<jsp:include page="../../links_to_api_methods.jsp" />
<p>
    ${viewSourceFile}
</p>


<form class="eg" action="" method="post" data-busy="form">
    <input type="hidden" name="_csrf" value="${csrfToken}">
    <button type="submit" class="btn btn-docu">${supportingTexts.getSubmitButton()}</button>
</form>


<jsp:include page="../../../partials/foot.jsp"/>