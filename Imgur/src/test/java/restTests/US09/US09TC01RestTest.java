package restTests.US09;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import rest.imgurClient.ImgurClient;

import static rest.responses.BasicResponseModel.getProperty;

import java.io.IOException;

public class US09TC01RestTest extends ImgurClient {

    private JSONObject testData;
    private HttpResponse httpResponse;
    private String response;
    private String success;
    private String accountName;
    private int data;

    @Test
    public void sendRequest() throws ParseException, IOException {

        prepareTestData();

        httpResponse = getAccountImagesCount(accountName);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        success = getProperty(response, "success");
        data = Integer.parseInt(getProperty(response, "data"));
        boolean notNegative = (data >= 0) ? true : false;

        action.assertEqualsAndReport(success, "true", "Success field");
        Assert.assertTrue(notNegative, "Data is negative [" + data + "]\n" );
        Reporter.log("Data image counter is a positive number: [" + data + "]\n");
        Reporter.log("<h3>Account image counter test PASSED</h3>");
    }

    private void prepareTestData() throws ParseException {
        testData = manager.getTestCaseData("us09TestData");
        accountName = testData.get("account_username").toString();
    }
}
