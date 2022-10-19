let DS_SEARCH = (function () {
    const API_TYPES = {
        ESIGNATURE: 'esignature',
        MONITOR: 'monitor',
        CLICK: 'click',
        ROOMS: 'rooms',
        ADMIN: 'admin',
    };

    let processJSONData = function() {
        let json_raw = $("#api_json_data").text(),
            jsonData = json_raw ? JSON.parse(json_raw) : false;

        return jsonData;
    }

    function checkIfExampleMatches(example, matches) {
        const name = example.ExampleName;
        const description = example.ExampleDescription;
        const pathNames = example.LinksToAPIMethod.map(a => a.PathName);

        for (let i = 0; i < matches.length; i++) {
            if (name === matches[i].value || description === matches[i].value || pathNames.indexOf(matches[i].value) > -1) {
                return true;
            }
        }

        return false;
    }

    function clearNonMatchingExamples(examples, matches) {
        for (let i = examples.length - 1; i >= 0; i--) {
            if (!checkIfExampleMatches(examples[i], matches)) {
                examples.splice(i, 1);
            }
        }
    }

    function clearResultsAfterMatching(api, matches) {
        const groups = api.Groups;

        for (let i = groups.length - 1; i >= 0; i--) {
            const group = groups[i];
            clearNonMatchingExamples(group.Examples, matches);

            if (group.Examples.length === 0) {
                groups.splice(i, 1);
            }
        }
    }

    let findCodeExamplesByKeywords = function(json, pattern) {
        const options = {
            isCaseSensitive: false,
            minMatchCharLength: pattern.length,
            threshold: -0.0,
            includeMatches: true,
            ignoreLocation: true,
            useExtendedSearch: true,
            keys: [
                "Groups.Examples.ExampleName",
                "Groups.Examples.ExampleDescription",
                "Groups.Examples.LinksToAPIMethod.PathName"
            ]
        };

        const fuse = new Fuse(json, options);

        var searchResults =  fuse.search(JSON.stringify(pattern))

        searchResults.forEach(searchResult => clearResultsAfterMatching(searchResult.item, searchResult.matches));

        return searchResults;
    }

    let getExamplesByAPIType = function(apiType, codeExamples) {
        let codeExamplesByAPI = codeExamples.find(x => x.Name.toLowerCase() === apiType);

        if (codeExamplesByAPI != null) {
            return [codeExamplesByAPI];
        } else {
            return null;
        }
    }

    let getEnteredAPIType = function(inputValue) {
        const inputLength = inputValue.length;

        for (const key in API_TYPES) {
            if (Object.hasOwnProperty.call(API_TYPES, key)) {
                const apiType = API_TYPES[key];
                const comparedValue = apiType.substr(0, inputLength);

                if(inputValue === comparedValue){
                    return apiType;
                }
            }
        }

        return null;
    }

    function getLinkForApiType(apiName) {
        switch (apiName) {
            case API_TYPES.ADMIN:
                return "a";
            case API_TYPES.CLICK:
                return "c";
            case API_TYPES.ROOMS:
                return "r";
            case API_TYPES.MONITOR:
                return "m";
            case API_TYPES.ESIGNATURE:
                return "eg"
        }
    }

    let addCodeExampleToHomepage = function(codeExamples) {
        codeExamples.forEach(
            element => {
                let linkToCodeExample = getLinkForApiType(element.Name.toLowerCase());

                element.Groups.forEach(
                    group => {
                        $("#filtered_code_examples").append("<h2>" + group.Name + "</h2>");

                        group.Examples.forEach(
                            example => {
                                if (!example.SkipForLanguages || !example.SkipForLanguages.toLowerCase().includes("c#"))
                                {
                                    $("#filtered_code_examples").append(
                                        "<h4 id="
                                        + "example".concat("0".repeat(3 - example.ExampleNumber.toString().length)).concat(example.ExampleNumber) + ">"
                                        + "<a href = "
                                        + linkToCodeExample.concat("0".repeat(3 - example.ExampleNumber.toString().length)).concat(example.ExampleNumber)
                                        + " >"
                                        + example.ExampleName
                                        + "</a ></h4 >"
                                    );

                                    $("#filtered_code_examples").append("<p>" + example.ExampleDescription + "</p>");

                                    $("#filtered_code_examples").append("<p>");

                                    if (example.LinksToAPIMethod.length == 1)
                                    {
                                        $("#filtered_code_examples").append(processJSONData().SupportingTexts.APIMethodUsed);
                                    }
                                    else
                                    {
                                        $("#filtered_code_examples").append(processJSONData().SupportingTexts.APIMethodUsedPlural);
                                    }

                                    for (let index = 0; index < example.LinksToAPIMethod.length; index++) {
                                        $("#filtered_code_examples").append(
                                            " <a target='_blank' href='"
                                            + example.LinksToAPIMethod[index].Path
                                            + "'>"
                                            + example.LinksToAPIMethod[index].PathName
                                            + "</a>"
                                        );

                                        if (index + 1 === example.LinksToAPIMethod.length)
                                        {
                                            $("#filtered_code_examples").append("<span></span>");
                                        }
                                        else if (index + 1 === example.LinksToAPIMethod.length - 1)
                                        {
                                            $("#filtered_code_examples").append("<span> and </span>");
                                        }
                                        else
                                        {
                                            $("#filtered_code_examples").append("<span>, </span>");
                                        }

                                    }

                                    $("#filtered_code_examples").append("</p> ");
                                }
                            }
                        );
                    }
                );
            }
        );
    }

    let textCouldNotBeFound = function() {
        $("#filtered_code_examples").append(processJSONData().SupportingTexts.SearchFailed);
    }

    return {
        processJSONData: processJSONData,
        getEnteredAPIType: getEnteredAPIType,
        getExamplesByAPIType: getExamplesByAPIType,
        findCodeExamplesByKeywords: findCodeExamplesByKeywords,
        textCouldNotBeFound: textCouldNotBeFound,
        addCodeExampleToHomepage: addCodeExampleToHomepage
    }
})();

const input = document.getElementById('code_example_search');

input.addEventListener('input', updateValue);

function updateValue(esearchPattern) {
    document.getElementById('filtered_code_examples').innerHTML = "";

    const inputValue = esearchPattern.target.value.toLowerCase();
    const json = DS_SEARCH.processJSONData().APIs;
    let tempJson = json;

    if (inputValue === "")
    {
        DS_SEARCH.addCodeExampleToHomepage(tempJson);
    }
    else
    {
        const apiType = DS_SEARCH.getEnteredAPIType(inputValue);

        if (apiType !== null)
        {
            const adminExamples = DS_SEARCH.getExamplesByAPIType(apiType, tempJson);
            DS_SEARCH.addCodeExampleToHomepage(adminExamples);
        }
        else
        {
            const result = DS_SEARCH.findCodeExamplesByKeywords(tempJson, inputValue);
            if (result.length < 1) {
                DS_SEARCH.textCouldNotBeFound();
            } else {
                result.forEach(x => {
                    DS_SEARCH.addCodeExampleToHomepage([x.item]);
                });
            }
        }
    }
}
