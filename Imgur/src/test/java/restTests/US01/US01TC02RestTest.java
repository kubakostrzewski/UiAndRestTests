package restTests.US01;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Reporter;
import org.testng.annotations.Test;
import rest.imgurClient.ImgurClient;

import java.io.IOException;

import static rest.responses.GetAccessTokenResponse.getErrorMessage;

public class US01TC02RestTest extends ImgurClient{

    private HttpResponse httpResponse;
    private String response;
    private String refreshToken, clientId, clientSecret, grantType;
    private JSONObject testData;

    @Test
    public void sendFailRequest() throws IOException, ParseException {

        prepareTestData();
        refreshToken = testData.get("fail_refresh_token").toString();
        httpResponse = postGenerateAccessToken(refreshToken, clientId, clientSecret, grantType);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 400, "Status code");
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        action.assertEqualsAndReport(getErrorMessage(response), testData.get("fail_refresh_token_error"),
                "Fail refresh token response");
        Reporter.log("<h3>Fail refresh token Test PASSED</h3>");

        prepareTestData();
        clientId = testData.get("fail_client_ID").toString();
        httpResponse = postGenerateAccessToken(refreshToken, clientId, clientSecret, grantType);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 400, "Status code");
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        action.assertEqualsAndReport(getErrorMessage(response), testData.get("fail_client_credential_error"),
                "Fail client ID response");
        Reporter.log("<h3>Fail client ID Test PASSED</h3>");

        prepareTestData();
        clientSecret = testData.get("fail_client_secret").toString();
        httpResponse = postGenerateAccessToken(refreshToken, clientId, clientSecret, grantType);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 400, "Status code");
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        action.assertEqualsAndReport(getErrorMessage(response), testData.get("fail_client_credential_error"),
                "Fail client secret response");
        Reporter.log("<h3>Fail client secret Test PASSED</h3>");

        prepareTestData();
        grantType = testData.get("fail_grant_type").toString();
        httpResponse = postGenerateAccessToken(refreshToken, clientId, clientSecret, grantType);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 400, "Status code");
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        action.assertEqualsAndReport(getErrorMessage(response), testData.get("fail_grant_type_error"),
                "Fail grant type response");
        Reporter.log("<h3>Fail grant type Test PASSED</h3>");

    }
    public void prepareTestData() throws ParseException, IOException {
        testData = manager.getTestCaseData("us01TestData");
        refreshToken =  manager.getToken("refreshToken");
        clientId = manager.getClientData("clientId");
        clientSecret = manager.getClientData("clientSecret");
        grantType = manager.getClientData("grant_type");
    }
}
