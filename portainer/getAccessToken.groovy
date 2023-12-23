import groovy.json.JsonSlurper

/*
 * This function get the Access Token using the Login Service
 */
def getAccessToken(def authUrl, def USR, def PSW) {
    def myResquestBody = """
            {
                "username": "${USR}",
                "password": "${PSW}"
            }
        """;
        
    def response = httpRequest consoleLogResponseBody: true, 
        contentType: 'APPLICATION_JSON', 
        httpMode: 'POST', 
        requestBody: myResquestBody,
        responseHandle: 'NONE', 
        url: "${authUrl}", 
        wrapAsMultipart: false

    return new JsonSlurper().parseText(response.content)
}
