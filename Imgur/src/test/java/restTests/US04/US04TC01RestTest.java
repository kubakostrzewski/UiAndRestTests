package restTests.US04;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Reporter;
import org.testng.annotations.Test;
import rest.imgurClient.ImgurClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static rest.responses.BasicResponseModel.getProperty;
import static rest.responses.GetImageResponse.getTagList;

public class US04TC01RestTest extends ImgurClient {

    private HttpResponse httpResponse;
    private String response;
    private String imageHash, title, tag, description;
    private JSONObject testData;
    private List<String> tagList;
    @Test
    public void sendRequest() throws ParseException, IOException {

        prepareTestData();
        httpResponse = postShareImageWithCommunity(imageHash, title, null, true, false,
                null);
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
        action.assertEqualsAndReport(getProperty(response, "data"), "true",
                "Response \"data\" value");
        action.assertEqualsAndReport(getProperty(response, "success"), "true",
                "Response \"success\" value");

        httpResponse = postUpdateGalleryItemTags(imageHash, tagList);
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
        action.assertEqualsAndReport(getProperty(response, "data"), "true",
                "Response \"data\" value");
        action.assertEqualsAndReport(getProperty(response, "success"), "true",
                "Response \"success\" value");

        httpResponse = postUpdateImageInformation(imageHash, null, description);
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
        action.assertEqualsAndReport(getProperty(response, "data"), "true",
                "Response \"data\" value");
        action.assertEqualsAndReport(getProperty(response, "success"), "true",
                "Response \"success\" value");

        httpResponse = getGalleryImage(imageHash);
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
        action.assertEqualsAndReport(getTagList(response).get(0), tag.toLowerCase(),
                "tag");

        httpResponse = deleteRemoveFromGallery(imageHash);
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
        action.assertEqualsAndReport(getProperty(response, "data"), "true",
                "Response \"data\" value");
        action.assertEqualsAndReport(getProperty(response, "success"), "true",
                "Response \"success\" value");

        httpResponse = getGalleryImage(imageHash);
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 404, "Status code");
        Reporter.log("<h3>Updating image information and tags Test PASSED</h3>");

    }
    private void prepareTestData() throws ParseException {
        testData = manager.getTestCaseData("us04TestData");
        imageHash = testData.get("imageHash").toString();
        title = testData.get("title").toString();
        tag = testData.get("tag").toString();
        tagList = new ArrayList<>();
        tagList.add(tag);
        description = testData.get("description").toString();
    }
}
