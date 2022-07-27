<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../partials/head.jsp"/>

<c:if test="${locals.user == null}">
  <!-- IF not signed in -->
  <div>
  <div class="jumbotron jumbotron-fluid"> <table>
    <tbody>
    <tr>
    <td>
      <h1 class="display-4">Java Launcher</h1>
    <p class="Xlead">Run and explore eSignature REST API code examples with Authorization Code Grant or JWT Grant authentication</p>
    </td>
    <td>
        <img src="/assets/banner-code.png" />
    </td>
  </tr>
  </tbody>
  </table>
</div>
</c:if>


<div class="container" style="margin-top: 40px" id="index-page">
    <c:if test="${showDoc == true}">
        <p><a target='_blank' href='${documentation}'>Documentation</a> on using OAuth Authorization Code Grant from a Java application.</p>
    </c:if>

  <h2>Basic examples</h2>

  <h4 id="example001"><a href="eg001">Use embedded signing</a></h4>
  <p>This example sends an envelope, and then uses embedded signing for the first signer.
    With embedded signing, the DocuSign signing is initiated from your website.
  </p>
  <p>API methods used:
    <a target ='_blank' rel="noopener noreferrer" href="https://developers.docusign.com/docs/esign-rest-api/reference/envelopes/envelopes/create/">Envelopes::create</a> and
    <a target ='_blank' rel="noopener noreferrer" href="https://developers.docusign.com/docs/esign-rest-api/reference/envelopes/envelopeviews/createrecipient/">EnvelopeViews::createRecipient</a>.
  </p>

  <h4 id="example002"><a href="eg002">Send an envelope with a remote (email) signer and cc recipient</a></h4>
  <p>The envelope includes a pdf, Word, and HTML document. Anchor text
    (<a target ='_blank' rel="noopener noreferrer" href="https://developers.docusign.com/docs/esign-rest-api/esign101/concepts/tabs/auto-place/">AutoPlace</a>)
    is used to position the signing fields in the documents.
  </p>
  <p>API method used:
    <a target ='_blank' rel="noopener noreferrer" href="https://developers.docusign.com/docs/esign-rest-api/reference/envelopes/envelopes/create/">Envelopes::create</a>.
  </p>

  <h4 id="example003"><a href="eg003">List envelopes in the user's account</a></h4>
  <p>List the envelopes created in the last 30 days.
  </p>
  <p>API method used:
    <a target ='_blank' rel="noopener noreferrer" href="https://developers.docusign.com/docs/esign-rest-api/reference/envelopes/envelopes/liststatuschanges/">Envelopes::listStatusChanges</a>.
  </p>

  <h4 id="example004"><a href="eg004">Get an envelope's basic information and status</a></h4>
  <p>List the basic information about an envelope, including its overall status.
    Additional API/SDK methods may be used to get additional information about the
    envelope, its documents, recipients, etc.
  </p>
  <p>API method used:
    <a target ='_blank' rel="noopener noreferrer" href="https://developers.docusign.com/docs/esign-rest-api/reference/envelopes/envelopes/get/">Envelopes::get</a>.
  </p>

  <h4 id="example005"><a href="eg005">List an envelope's recipients and their status</a></h4>
  <p>List the envelope's recipients, including their current status.
  </p>
  <p>API method used:
    <a target ='_blank' rel="noopener noreferrer" href="https://developers.docusign.com/docs/esign-rest-api/reference/envelopes/enveloperecipients/list/">EnvelopeRecipients::list</a>.
  </p>

  <h4 id="example006"><a href="eg006">List an envelope's documents</a></h4>
  <p>List the envelope's documents. A <em>Certificate of Completion</em> document
    is also associated with every envelope.
  </p>
  <p>API method used:
    <a target ='_blank' rel="noopener noreferrer" href="https://developers.docusign.com/docs/esign-rest-api/reference/envelopes/envelopedocuments/list/">EnvelopeDocuments::list</a>.
  </p>

  <h4 id="example007"><a href="eg007">Download a document from an envelope</a></h4>
  <p>An envelope's documents can be downloaded one by one or as a complete set.
  </p>
  <p>API method used:
    <a target ='_blank' rel="noopener noreferrer" href="https://developers.docusign.com/docs/esign-rest-api/reference/envelopes/envelopedocuments/get/">EnvelopeDocuments::get</a>.
  </p>

  <h4 id="example008"><a href="eg008">Create a template</a></h4>
  <p>Create a template with two roles, <strong>signer</strong> and <strong>cc</strong>.</p>
  <p>API methods used:
    <a target='_blank' rel="noopener noreferrer" href="https://developers.docusign.com/docs/esign-rest-api/reference/templates/templates/list/">Templates::list</a>,
    <a target='_blank' rel="noopener noreferrer" href="https://developers.docusign.com/docs/esign-rest-api/reference/templates/templates/create/">Templates::create</a>.
  </p>

  <h4 id="example009"><a href="eg009">Send an envelope using a template</a></h4>
  <p>The envelope is defined by the template.
    The signer and cc recipient name and email are used to fill in the template's <em>roles</em>.</p>
  <p>API method used:
    <a target ='_blank' rel="noopener noreferrer" href="https://developers.docusign.com/docs/esign-rest-api/reference/envelopes/envelopes/create/">Envelopes::create</a>.
  </p>

  <h4 id="example010"><a href="eg010">Send an envelope using binary document transfer</a></h4>
  <p>The envelope includes a pdf, Word, and HTML document.</p>
  <p>Multipart data transfer is used to send the documents in binary format to DocuSign.
    Binary transfer is 33% more efficient than base64 encoding and is recommended for documents over 15M Bytes.
    Binary transfer is not yet supported by the SDK.
  </p>
  <p>API method used:
    <a target ='_blank' rel="noopener noreferrer" href="https://developers.docusign.com/docs/esign-rest-api/reference/envelopes/envelopes/create/">Envelopes::create</a>.
  </p>

  <h4 id="example011"><a href="eg011">Use embedded sending</a></h4>
  <p>An envelope will be created in draft mode. The DocuSign
    web tool (NDSE) will then be shown, enabling further updates
    to the envelope before it is sent.
  </p>
  <p>API methods used:
    <a target='_blank' rel="noopener noreferrer" href="https://developers.docusign.com/docs/esign-rest-api/reference/envelopes/envelopes/create/">Envelopes::create</a>,
    <a target='_blank' rel="noopener noreferrer" href="https://developers.docusign.com/docs/esign-rest-api/reference/envelopes/envelopeviews/createsender/">EnvelopeViews::createSender</a>.

  <h4 id="example012"><a href="eg012">Embedded DocuSign web tool</a></h4>
  <p>Redirect the user to the DocuSign web tool.</p>
  <p>API method used:
    <a target ='_blank' rel="noopener noreferrer" href="https://developers.docusign.com/docs/esign-rest-api/reference/envelopes/envelopeviews/createconsole/">EnvelopeViews::createConsole</a>.
  </p>

  <h4 id="example013"><a href="eg013">Use embedded signing from a template with an added document</a></h4>
  <p>This example sends an envelope based on a template.</p>
  <p>In addition to the template's document(s), the example adds an
    additional document to the envelope by using the
    <a target='_blank' rel="noopener noreferrer" href='https://developers.docusign.com/docs/esign-rest-api/esign101/concepts/templates/composite/'>Composite Templates</a>
    feature.</p>
  <p>API methods used:
    <a target ='_blank' rel="noopener noreferrer" href="https://developers.docusign.com/docs/esign-rest-api/reference/envelopes/envelopes/create/">Envelopes::create</a> and
    <a target ='_blank' rel="noopener noreferrer" href="https://developers.docusign.com/docs/esign-rest-api/reference/envelopes/envelopeviews/createrecipient/">EnvelopeViews::createRecipient</a>.
  </p>
  <h4 id="example039"><a href="eg039">Send an envelope to an In Person Signer</a></h4>
  <p>
    Demonstrates how to host an In Person Signing session with embedded signing.
  <p>
  <p>
    API method used:
    <a target="_blank" href="https://developers.docusign.com/docs/esign-rest-api/reference/envelopes/envelopes/create/">Envelopes::create</a>.
  </p>

  <h2>Payments</h2>

  <h4 id="example014"><a href="eg014">Send an envelope with an order form and payment field</a></h4>
  <p>Anchor text
    (<a target ='_blank' rel="noopener noreferrer" href="https://developers.docusign.com/docs/esign-rest-api/esign101/concepts/tabs/auto-place/">AutoPlace</a>)
    is used to position the fields in the documents.
  </p>
  <p>API method used:
    <a target ='_blank' rel="noopener noreferrer" href="https://developers.docusign.com/docs/esign-rest-api/reference/envelopes/envelopes/create/">Envelopes::create</a>.
  </p>

  <h2>Tabs</h2>

  <h4 id="example015"><a href="eg015">Get the tab data from an envelope</a></h4>
  <p>This example retrieves the <strong>tab</strong> (<a target="_blank" href="https://developers.docusign.com/docs/esign-rest-api/esign101/concepts/tabs/">field</a>) values from an envelope.</p>
  <p>
    API method used:
    <a target='_blank' href="https://developers.docusign.com/docs/esign-rest-api/reference/envelopes/envelopeformdata/get/">EnvelopeFormData::get</a>.
  </p>

  <h4 id="example016"><a href="eg016">Set tab values for an envelope</a></h4>
  <p>This example sets the tab (field) values for an envelope including tabs that can and cannot be changed by the signer.</p>
  <p>
    API method used:
    <a target='_blank' href="https://developers.docusign.com/docs/esign-rest-api/reference/envelopes/envelopes/create/">Envelopes::create</a> and
    <a target ='_blank' href="https://developers.docusign.com/docs/esign-rest-api/reference/envelopes/envelopeviews/createrecipient/">EnvelopeViews::createRecipient</a>.
  </p>

  <h4 id="example017"><a href="eg017">Set template tab values</a></h4>
  <p>This example sets the tab (field) values for a template being used by an envelope.</p>
  <p>
    API method used:
    <a target='_blank' href="https://developers.docusign.com/docs/esign-rest-api/reference/envelopes/envelopes/create/">Envelopes::create</a> and
    <a target ='_blank' href="https://developers.docusign.com/docs/esign-rest-api/reference/envelopes/envelopeviews/createrecipient/">EnvelopeViews::createRecipient</a>.
  </p>

  <h4 id="example018"><a href="eg018">List envelope custom metadata field values</a></h4>
  <p>This example lists the envelope's custom metadata field values.</p>
  <p>
    API method used:
    <a target='_blank' href="https://developers.docusign.com/docs/esign-rest-api/reference/envelopes/envelopecustomfields/list/">EnvelopeCustomFields::list</a>.
  </p>

  <h2>Recipient authentication</h2>
  
  <h4 id="example019"><a href="eg019">Require access code authentication for a recipient</a></h4>
  <p>Sends an envelope that requires entering an access code for the purpose of multifactor authentication.</p>
  <p>API method used:
    <a target ='_blank' rel="noopener noreferrer" href="https://developers.docusign.com/docs/esign-rest-api/reference/envelopes/envelopes/create/">Envelopes::create</a>.
  </p>

  <h4 id="example020"><a href="eg020">Require phone authentication for a recipient</a></h4>
  <p>Sends an envelope that requires entering a six-digit code from a text message or phone call for the purpose of multifactor authentication.</p>
  <p>API method used:
    <a target ='_blank' rel="noopener noreferrer" href="https://developers.docusign.com/docs/esign-rest-api/reference/envelopes/envelopes/create/">Envelopes::create</a>.
  </p>

  <h4 id="example022"><a href="eg022">Require knowledge-based authentication (KBA) for a recipient</a></h4>
  <p>Sends an envelope that requires passing a public records check to validate identity for the purpose of multifactor authentication.</p>
  <p>API method used:
    <a target ='_blank' rel="noopener noreferrer" href="https://developers.docusign.com/docs/esign-rest-api/reference/envelopes/envelopes/create/">Envelopes::create</a>.
  </p>

  <h4 id="example023"><a href="eg023">Require ID verification (IDV) for a recipient</a></h4>
  <p>Sends an envelope that requires the recipient to upload a government-issued ID for the purpose of multifactor authentication.</p>
  <p>
    API method used:
    <a target ='_blank' href="https://developers.docusign.com/docs/esign-rest-api/reference/envelopes/envelopes/create/">Envelopes::create</a>.
  </p>

  <h2>Permissions</h2>

  <h4 id="example024"><a href="eg024">Create a new permission profile</a></h4>
  <p>
    API method used:
    <a target='_blank' href="https://developers.docusign.com/docs/esign-rest-api/reference/accounts/accountpermissionprofiles/create/">AccountPermissionProfiles::create</a>.
  </p>
 
  <h4 id="example025"><a href="eg025">Setting a permission profile</a></h4>
  <p>This example allows you to set a permission profile on an existing user group.</p>
  <p>API method used:
    <a target='_blank' rel="noopener noreferrer" href="https://developers.docusign.com/docs/esign-rest-api/reference/usergroups/groups/update/">Groups::update</a>.
  </p>

  <h4 id="example026"><a href="eg026">Updating individual permission profile settings</a></h4>
  <p>
    This example will update individual permission settings for a given account.
  </p>
  <p>
    API method used:
    <a target='_blank' href="https://developers.docusign.com/docs/esign-rest-api/reference/accounts/accountpermissionprofiles/update/">AccountPermissionProfiles::update</a>.
  </p>

  <h4 id="example027"><a href="eg027">Deleting a permission profile</a></h4>
  <p>This example lists all available permissions profiles and allows you to delete any without associated users. Please note that you cannot remove "Everyone" nor "Administrator" permission profiles.</p>
  <p>API method used:
    <a target='_blank' rel="noopener noreferrer" href="https://developers.docusign.com/docs/esign-rest-api/reference/accounts/accountpermissionprofiles/delete/">AccountPermissionProfiles::create</a>.
  </p>
  
  <h2>Brands</h2>
  
  <h4 id="example028"><a href="eg028">Create a brand</a></h4>
  <p>This example will create an account brand that can be used to apply customization to your envelopes such as your own logo, colors, and text elements.</p>
  <p>API method used:
    <a target ='_blank' rel="noopener noreferrer" href="https://developers.docusign.com/docs/esign-rest-api/reference/accounts/accountbrands/create/">AccountBrands::create</a>.
  </p>
  
  <h4 id="example029"><a href="eg029">Applying a brand to an envelope</a></h4>
  <p>This example will show you how to create an envelope using any of the created brands on your account.</p>
  <p>API method used:
    <a target='_blank' rel="noopener noreferrer" href="https://developers.docusign.com/docs/esign-rest-api/reference/envelopes/envelopes/create/">Envelopes::create</a>.
  </p>

  <h4 id="example030"><a href="eg030">Applying a brand to a template</a></h4>
  <p>This example will show you how to create an envelope using a brand </p>
  <p>API method used:
    <a target='_blank' rel="noopener noreferrer" href="https://developers.docusign.com/docs/esign-rest-api/reference/envelopes/envelopes/create/">Envelopes::create</a>.
  </p>

  <h2>Bulk operations</h2>

  <h4 id="example031"><a href="eg031">Bulk send envelopes</a></h4>

  <p>
    Demonstrates how to send envelopes in bulk to multiple recipients. First, this example
    creates a bulk-send recipients list, then creates an envelope. After that, it initiates bulk
    envelope sending.
  </p>
  <p>
    API methods used:
    <a target="_blank" href="https://developers.docusign.com/docs/esign-rest-api/reference/bulkenvelopes/bulksend/createbulksendlist/">BulkSend::createBulkSendList</a>,
    <a target="_blank" href="https://developers.docusign.com/docs/esign-rest-api/reference/envelopes/envelopes/create/">Envelopes::create</a>,
    <a target="_blank" href="https://developers.docusign.com/docs/esign-rest-api/reference/envelopes/envelopecustomfields/create/">EnvelopeCustomFields::create</a>,
    <a target="_blank" href="https://developers.docusign.com/docs/esign-rest-api/reference/bulkenvelopes/bulksend/createbulksendrequest/">BulkSend::createBulkSendRequest</a>,
    <a target="_blank" href="https://developers.docusign.com/docs/esign-rest-api/reference/bulkenvelopes/bulksend/getbulksendbatchstatus/">BulkSend::getBulkSendBatchStatus</a>.
  </p>

  <h2>Advanced recipient routing</h2>

  <h4 id="example032"><a href="eg032">Pause a signature workflow</a></h4>

  <p>
    This topic demonstrates how to create an envelope where the workflow is paused before the
    envelope is sent to a second recipient. For information on resuming a workflow see
    <a href="https://developers.docusign.com/docs/esign-rest-api/how-to/unpause-workflow">
      How to unpause a signature workflow
    </a>.
  </p>
  <p>
    API methods used:
    <a target="_blank" href="https://developers.docusign.com/docs/esign-rest-api/reference/envelopes/envelopes/create">Envelopes::create</a>.
  </p>

  <h4 id="example033"><a href="eg033">Unpause a signature workflow</a></h4>

  <p>
    This topic demonstrates how to resume an envelope workflow that has been paused.
    For information on creating an envelope with a paused workflow, see
    <a href="https://developers.docusign.com/docs/esign-rest-api/how-to/pause-workflow">
      How to pause a signature workflow
    </a>.
  </p>
  <p>
    API methods used:
    <a target="_blank" href="https://developers.docusign.com/docs/esign-rest-api/reference/envelopes/envelopes/update">Envelopes::update</a>.
  </p>

  <h4 id="example034"><a href="eg034">Use conditional recipients</a></h4>

  <p>
    This topic demonstrates how to create an envelope where the workflow is routed to
    different recipients based on the value of a transaction.
  </p>
  <p>
    API methods used:
    <a target="_blank" href="https://developers.docusign.com/docs/esign-rest-api/reference/envelopes/envelopes/create">Envelopes::create</a>.
  </p>

  <h4 id="example035"><a href="eg035">Schedule an envelope</a></h4>
  <p>
      Demonstrates how to schedule an envelope using the scheduled sending feature.
  </p>
  <p>
      API method used:
      <a target='_blank'
         href="https://developers.docusign.com/docs/esign-rest-api/reference/envelopes/envelopes/create/">Envelopes::create</a>.
  </p>

  <h4 id="example036"><a href="eg036">Send an envelope with delayed routing</a></h4>
  <p>
      Demonstrates how to delay an envelope's delivery between recipients using the delayed routing feature.
  </p>
  <p>
      API method used:
      <a target='_blank'
         href="https://developers.docusign.com/docs/esign-rest-api/reference/envelopes/envelopes/create/">Envelopes::create</a>.
  </p>

  <h2>Premium features</h2>
  <h4 id="example037"><a href="eg037">Request a signature by SMS delivery</a></h4>
  <p>
      Sends a signature requet via an SMS message.
  </p>
  <p>
      API method used:
      <a target='_blank'
         href="https://developers.docusign.com/docs/esign-rest-api/reference/envelopes/envelopes/create/">Envelopes::create</a>.
  </p>

  <h4 id="example038"><a href="eg038">Create a signable HTML document</a></h4>
  <p>
    Demonstrates how to create an HTML document for responsive signing.
  <p>
    API methods used:
    <a target="_blank" href="https://developers.docusign.com/docs/esign-rest-api/reference/envelopes/envelopes/create/">Envelopes::create</a>,
    <a target='_blank'
       href="https://developers.docusign.com/docs/esign-rest-api/reference/envelopes/envelopeviews/createrecipient/">EnvelopeViews::createRecipient</a>.
  </p>

  <h4 id="example039"><a href="eg039">Send an envelope to an In Person Signer</a></h4>
  <p>
    Demonstrates how to host an In Person Signing session with embedded signing.
  <p>
  <p>
    API method used:
    <a target="_blank" href="https://developers.docusign.com/docs/esign-rest-api/reference/envelopes/envelopes/create/">Envelopes::create</a>.
  </p>

  <h4 id="example040"><a href="eg040">Set document visibility for envelope recipients</a></h4>
  <p>
    Demonstrates how to set document visibility for envelope recipients.
  <p>
  <p>
    API method used:
    <a target="_blank" href="https://developers.docusign.com/docs/esign-rest-api/reference/envelopes/envelopes/create/">Envelopes::create</a>.
  </p>

</div>


<!-- anchor-js is only for the index page -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/anchor-js/4.1.1/anchor.min.js"></script>
<script>anchors.options.placement = 'left'; anchors.add('h4')</script>

<jsp:include page="../../partials/foot.jsp"/>
