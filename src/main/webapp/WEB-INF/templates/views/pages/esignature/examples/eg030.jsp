<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../../../partials/head.jsp" />

<h4>${example.getExampleName()}</h4>
<p>${example.getExampleDescription()}</p>
<c:if test="${showDoc}">
    <p>
        <a target='_blank' href='${documentation}'>Documentation</a> about this example.
    </p>
</c:if>


<jsp:include page="../../links_to_api_methods.jsp" />
<p>
    ${viewSourceFile}
</p>

<c:choose>
    <c:when test="${templateOk and not empty listBrands}">
        <p>The template you created via Create a template and brands created via
            Create a brand will be used.</p>
        <form class="eg" action="" method="post" data-busy="form">
            <div class="form-group">
                <label for="signerEmail">Signer Email</label> <input type="email"
                    class="form-control" id="signerEmail" name="signerEmail"
                    aria-describedby="emailHelp" placeholder="pat@example.com" required
                    value="${locals.dsConfig.signerEmail}"> <small
                    id="emailHelp" class="form-text text-muted">We'll never
                    share your email with anyone else.</small>
            </div>
            <div class="form-group">
                <label for="signerName">Signer Name</label> <input type="text"
                    class="form-control" id="signerName" placeholder="Pat Johnson"
                    name="signerName" value="${locals.dsConfig.signerName}" required>
            </div>
            <div class="form-group">
                <label for="ccEmail">CC Email</label> <input type="email"
                    class="form-control" id="ccEmail" name="ccEmail"
                    aria-describedby="emailHelp" placeholder="pat@example.com" required>
                <small id="emailHelp" class="form-text text-muted">The email
                    and/or name for the cc recipient must be different from the signer.</small>
            </div>
            <div class="form-group">
                <label for="ccName">CC Name</label> <input type="text"
                    class="form-control" id="ccName" placeholder="Pat Johnson"
                    name="ccName" required>
            </div>
            <div class="form-group">
                <label for="brandId">Brand</label>
                <select id="brandId" name="brandId" class="form-control">
                    <c:forEach items="${listBrands}" var="brand">
                        <option value="${brand.brandId}">${brand.brandName}</option>
                    </c:forEach>
                </select>
            </div>
            <input type="hidden" name="_csrf" value="${csrfToken}">
            <button type="submit" class="btn btn-docu">${launcherTexts.getSubmitButton()}</button>
        </form>
    </c:when>
    <c:otherwise>
        <c:if test="${not templateOk}">
            <p>Problem: please create the template using <a href="eg008">Create a template.</a></p>
            <form class="eg" action="eg008" method="get">
                <button type="submit" class="btn btn-docu">${launcherTexts.getContinueButton()}</button>
            </form>
        </c:if>
        <c:if test="${empty listBrands}">
            <p>Problem: please create the brand using <a href="eg028">Create a brand.</a></p>
            <form class="eg" action="eg028" method="get">
                <button type="submit" class="btn btn-docu">${launcherTexts.getContinueButton()}</button>
            </form>
        </c:if>
    </c:otherwise>
</c:choose>

<jsp:include page="../../../partials/foot.jsp" />
