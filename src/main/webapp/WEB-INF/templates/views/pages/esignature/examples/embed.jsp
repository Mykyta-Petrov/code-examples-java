<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../../partials/head.jsp"/>

<br />
<h2>The document has been embedded with focused view.</h2>
<br />

<!--
//ds-snippet-start:eSign44Step6
-->

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>Signing</title>
    <style>
        html,
        body {
            margin-top: 30px;
            padding: 0;
            font: 13px Helvetica, Arial, sans-serif;
        }

        .docusign-agreement {
            width: 75%;
            height: 800px;
        }
    </style>
</head>
<body>
<div class="docusign-agreement" id="agreement"></div>
</body>
</html>

<p><a href="/">Continue</a></p>

<script src='http://js.docusign.com/bundle.js'></script>
<script>
    window.DocuSign.loadDocuSign('${integrationKey}')
        .then((docusign) => {
            const signing = docusign.signing({
                url: '${url}',
                displayFormat: 'focused',
                style: {
                    /** High-level variables that mirror our existing branding APIs. Reusing the branding name here for familiarity. */
                    branding: {
                        primaryButton: {
                            /** Background color of primary button */
                            backgroundColor: '#333',
                            /** Text color of primary button */
                            color: '#fff',
                        }
                    },

                    /** High-level components we allow specific overrides for */
                    signingNavigationButton: {
                        finishText: 'You have finished the document! Hooray!',
                        position: 'bottom-center'
                    }
                }
            });

            signing.on('ready', (event) => {
                console.log('UI is rendered');
            });

            signing.on('sessionEnd', (event) => {
                /** The event here denotes what caused the sessionEnd to trigger, such as signing_complete, ttl_expired etc../ **/
                console.log('sessionend', event);
            });

            signing.mount('#agreement');
        })
        .catch((ex) => {
            // Any configuration or API limits will be caught here
        });
</script>

<!--
//ds-snippet-end:eSign44Step6
-->

<jsp:include page="../../../partials/foot.jsp"/>
