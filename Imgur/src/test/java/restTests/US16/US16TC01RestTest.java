package restTests.US16;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Reporter;
import org.testng.annotations.Test;
import rest.imgurClient.ImgurClient;

import java.io.IOException;

import static rest.responses.BasicResponseModel.getProperty;
import static rest.responses.GetImageResponse.getPropertyFromData;

public class US16TC01RestTest extends ImgurClient {

    private HttpResponse httpResponse;
    private String response;
    private String imageHash;
    private JSONObject testData;

    @Test
    public void sendRequest() throws IOException, ParseException, InterruptedException {

        prepareTestData();
        httpResponse = getImage(imageHash);
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
        action.assertEqualsAndReport(getPropertyFromData(response, "favorite"), "false",
                "Favorite value");

        httpResponse = postFavoriteImage(imageHash);
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
        action.assertEqualsAndReport(getProperty(response, "data"), "favorited",
                "Response \"data\" value");
        action.assertEqualsAndReport(getProperty(response, "success"), "true",
                "Response \"success\" value");

        client = HttpClientBuilder.create().build();

        httpResponse = getImage(imageHash);
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
        action.assertEqualsAndReport(getPropertyFromData(response, "favorite"), "true",
                "Favorite value");

        httpResponse = postFavoriteImage(imageHash);
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
        action.assertEqualsAndReport(getProperty(response, "data"), "unfavorited",
                "Response \"data\" value");
        action.assertEqualsAndReport(getProperty(response, "success"), "true",
                "Response \"success\" value");

        Reporter.log("<h3Favorite image test PASSED");
    }
    private void prepareTestData() throws ParseException {
        testData = manager.getTestCaseData("us16TestData");
        imageHash = testData.get("imageHash").toString();
    }
}
