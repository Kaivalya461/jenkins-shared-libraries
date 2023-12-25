import groovy.json.JsonSlurper

def call(def authUrl, def USR, def PSW) {
    getAccessToken(authUrl, USR, PSW)
}

/*
 * This function applies Kubernetes Manifest using Portainer API
 */
def createStackUsingRepository(
    def accessToken,
    def stackName,
    def repoUrl,
    def repoRef,
    def manifestFile) {
    def myRequestBody = """
            {
                "method": "repository",
                "type": "kubernetes",
                "ComposeFormat": false,
                "Namespace": "",
                "StackName": "${stackName}",
                "TLSSkipVerify": false,
                "RepositoryURL": "${repoUrl}",
                "RepositoryReferenceName": "${repoRef}",
                "RepositoryAuthentication": false,
                "ManifestFile": "${manifestFile}",
                "AdditionalFiles": [],
                "AutoUpdate": null
            }
        """;
    
    def response = httpRequest consoleLogResponseBody: true, 
        contentType: 'APPLICATION_JSON', 
        httpMode: 'POST', 
        customHeaders:[[name:'Authorization', value:"Bearer ${accessToken}"]],
        requestBody: myRequestBody, 
        responseHandle: 'NONE', 
        url: 'https://portainer.kvhome.in/api/stacks/create/kubernetes/repository?endpointId=1', //What's this endpointId?
        wrapAsMultipart: false;

    def responseJson = new JsonSlurper().parseText(response.content);
    
    echo "createStackUsingRepository ----------> responseBody: ${responseJson}";
    return responseJson;
}

