package restTests.US15;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;
import rest.imgurClient.ImgurClient;

import java.io.IOException;

import static rest.responses.BasicResponseModel.getProperty;
import static rest.responses.GetImageResponse.getNullValuePropertyFromData;

public class US15TC1RestTest extends ImgurClient {

    private HttpResponse httpResponse;
    private String response;
    private String imageHash, albumHash, title, description;
    private JSONObject testData;

    @Test
    public void sendRequest() throws IOException, ParseException {

        prepareTestData();
        httpResponse = getAlbumImage(albumHash, imageHash);
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
        action.assertEqualsAndReport(getNullValuePropertyFromData(response, "title"), null,
                "Album Image Title");
        action.assertEqualsAndReport(getNullValuePropertyFromData(response, "description"), null,
                "Album Image Description");

        httpResponse = postUpdateImageInformation(imageHash, title, description);
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
        action.assertEqualsAndReport(getProperty(response, "data"), "true",
                "Response \"data\" value");
        action.assertEqualsAndReport(getProperty(response, "success"), "true",
                "Response \"success\" value");

        httpResponse = getAlbumImage(albumHash, imageHash);
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
        action.assertEqualsAndReport(getNullValuePropertyFromData(response, "title"), title,
                "Album Image Title");
        action.assertEqualsAndReport(getNullValuePropertyFromData(response, "description"), description,
                "Album Image Description");

        httpResponse = postUpdateImageInformation(imageHash, "", "");
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
        action.assertEqualsAndReport(getProperty(response, "data"), "true",
                "Response \"data\" value");
        action.assertEqualsAndReport(getProperty(response, "success"), "true",
                "Response \"success\" value");

        httpResponse = getAlbumImage(albumHash, imageHash);
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
        action.assertEqualsAndReport(getNullValuePropertyFromData(response, "title"), null,
                "Album Image Title");
        action.assertEqualsAndReport(getNullValuePropertyFromData(response, "description"), null,
                "Album Image Description");
    }
    private void prepareTestData() throws ParseException {
        testData = manager.getTestCaseData("us15TestData");
        albumHash = testData.get("albumHash").toString();
        imageHash = testData.get("imageHash").toString();
        title = testData.get("title").toString();
        description = testData.get("description").toString();
    }
}
