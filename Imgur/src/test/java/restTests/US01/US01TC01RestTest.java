package restTests.US01;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Reporter;
import org.testng.annotations.Test;
import rest.imgurClient.ImgurClient;
import java.io.IOException;

import static rest.responses.GetAccessTokenResponse.*;

public class US01TC01RestTest extends ImgurClient{

    private HttpResponse httpResponse;
    private String response;
    public String refreshToken, clientId, clientSecret, grantType;
    public JSONObject testData;

    @Test
    public void sendRequest() throws IOException, ParseException {

        prepareTestData();
        httpResponse = postGenerateAccessToken(refreshToken, clientId, clientSecret, grantType);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        action.assertEqualsAndReport(getTokenType(response), testData.get("token_type"), "Token type");
        action.assertEqualsAndReport(getAccountId(response), testData.get("account_id"), "Account ID");
        action.assertEqualsAndReport(getAccountUsername(response), testData.get("account_username"),
                "Account username");
        Reporter.log("<h3>Generate Access Token Test PASSED</h3>");
    }

    public void prepareTestData() throws ParseException, IOException {
        testData = manager.getTestCaseData("us01TestData");
        refreshToken =  manager.getToken("refreshToken");
        clientId = manager.getClientData("clientId");
        clientSecret = manager.getClientData("clientSecret");
        grantType = manager.getClientData("grant_type");
    }
}
