package restTests.US19;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Reporter;
import org.testng.annotations.Test;
import rest.imgurClient.ImgurClient;

import java.io.IOException;

import static rest.responses.BasicResponseModel.getProperty;
import static rest.responses.GetImageResponse.getPropertyFromData;

public class US19TC01RestTest extends ImgurClient {

    private HttpResponse httpResponse;
    private String response;
    private String albumHash, title;
    private JSONObject testData;

    @Test
    public void sendRequest() throws IOException, ParseException {

        prepareTestData();
        httpResponse = postShareAlbumWithCommunity(albumHash, title, null, true, false,
                null);
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
        action.assertEqualsAndReport(getProperty(response, "data"), "true",
                "Response \"data\" value");
        action.assertEqualsAndReport(getProperty(response, "success"), "true",
                "Response \"success\" value");

        httpResponse = getGalleryAlbum(albumHash);
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
        action.assertEqualsAndReport(getPropertyFromData(response, "id"), albumHash,
                "Image ID");

        httpResponse = deleteRemoveFromGallery(albumHash);
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
        action.assertEqualsAndReport(getProperty(response, "data"), "true",
                "Response \"data\" value");
        action.assertEqualsAndReport(getProperty(response, "success"), "true",
                "Response \"success\" value");

        httpResponse = getGalleryAlbum(albumHash);
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 404, "Status code");
        Reporter.log("<h3>Sharing and deleting image from Gallery Test PASSED</h3>");

    }
    private void prepareTestData() throws ParseException {
        testData = manager.getTestCaseData("us19TestData");
        albumHash = testData.get("albumHash").toString();
        title = testData.get("title").toString();
    }
}
