package restTests.US06;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;
import rest.imgurClient.ImgurClient;

import java.io.IOException;
import java.util.List;

import static rest.responses.BasicResponseModel.getProperty;
import static rest.responses.DataResponse.getPropertyFromData;
import static rest.responses.GetAvailableAvatarsResponse.getAvatarNameList;

public class US06RestTest extends ImgurClient {

    private HttpResponse httpResponse;
    private String response, avatar;
    private List<String> avatarList;
    public String defaultAvatar;
    public JSONObject testData;

    @Test
    public void sendRequest() throws IOException, ParseException {

        prepareTestData();
        httpResponse = getAccountAvailableAvatars();
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
        avatarList = getAvatarNameList(response);
        avatar = avatarList.get(0);

        httpResponse = putChangeAccountSettings(null,null,null,null,null,
                null, null, avatar);
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
        action.assertEqualsAndReport(getProperty(response, "data"), "true",
                "Response \"data\" value");
        action.assertEqualsAndReport(getProperty(response, "success"), "true",
                "Response \"success\" value");

        httpResponse = getAccountAvatar();
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
        action.assertEqualsAndReport(getPropertyFromData(response, "avatar_name"), avatar,
                "Response \"avatar_name\" value");

        httpResponse = putChangeAccountSettings(null,null,null,null,null,
                null, null, defaultAvatar);
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
        action.assertEqualsAndReport(getProperty(response, "data"), "true",
                "Response \"data\" value");
        action.assertEqualsAndReport(getProperty(response, "success"), "true",
                "Response \"success\" value");

        httpResponse = getAccountAvatar();
        response = EntityUtils.toString(httpResponse.getEntity());
        action.logResponse(httpResponse, response);
        action.assertEqualsAndReport(httpResponse.getStatusLine().getStatusCode(), 200, "Status code");
        action.assertEqualsAndReport(getPropertyFromData(response, "avatar_name"), defaultAvatar,
                "Response \"avatar_name\" value");
    }
    public void prepareTestData() throws ParseException, IOException {
        testData = manager.getTestCaseData("us06TestData");
        defaultAvatar =  testData.get("defaultAvatar").toString();
    }

}
