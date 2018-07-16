package restTests.US02;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Reporter;
import org.testng.annotations.Test;
import rest.imgurClient.ImgurClient;
import java.io.IOException;

import static rest.responses.ErrorResponse.*;

public class US02TC02RestTest extends ImgurClient {

    private HttpResponse httpResponse;
    private String response;
    private  String invalidImageHash, title;
    private  JSONObject testData;

    @Test
    public void sendRequest() throws IOException,ParseException, InterruptedException{

        prepareTestData();
        httpResponse = postShareImageWithCommunity(invalidImageHash, title, null, true, false,
                null);
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 400, "Status code");
        action.assertEqualsAndReport(getPropertyFromData(response, "error"), "InvalidHash",
                "Response error message");
        action.assertEqualsAndReport(getProperty(response, "success"), "false",
                "Response \"success\" value");

        httpResponse = deleteRemoveFromGallery(invalidImageHash);
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
        action.assertEqualsAndReport(getProperty(response, "data"), "false",
                "Response \"data\" value");
        action.assertEqualsAndReport(getProperty(response, "success"), "true",
                "Response \"success\" value");
        Reporter.log("<h3>Adding and removing Invalid Hash image Test PASSED</h3>");
    }

    private void prepareTestData() throws ParseException {
        testData = manager.getTestCaseData("us02TestData");
        title = testData.get("title").toString();
        invalidImageHash = testData.get("invalidImageHash").toString();
    }
}
