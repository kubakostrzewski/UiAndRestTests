package restTests.US05;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Reporter;
import org.testng.annotations.Test;
import rest.imgurClient.ImgurClient;

import java.io.IOException;

import static rest.responses.BasicResponseModel.*;

public class US05TC01RestTest extends ImgurClient {

    private JSONObject testData;
    private HttpResponse httpResponse;
    private String response;
    private String imageID;
    private String success;
    private String galleryHash;
    private String commentSort;



    @Test
    public void sendRequest() throws ParseException, IOException {

        prepareTestData();

        httpResponse = getGalleryCommentsSorted(galleryHash, commentSort);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        imageID = getPropertyFromDataArray(response, "image_id");
        success = getProperty(response, "success");

        action.assertEqualsAndReport(imageID, testData.get("galleryHash"), "Image ID");
        action.assertEqualsAndReport(success, "true", "Success field");

        Reporter.log("<h3>Sort comments by new first test PASSED</h3>");
    }

    private void prepareTestData() throws ParseException {
        testData = manager.getTestCaseData("us05TestData");
        galleryHash = testData.get("galleryHash").toString();
        commentSort = testData.get("commentSort").toString();

    }
}
