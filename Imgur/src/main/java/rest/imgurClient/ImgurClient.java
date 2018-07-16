package rest.imgurClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.*;
import rest.imgurManager.ImgurManager;
import rest.utils.Action;

import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Listeners({org.uncommons.reportng.HTMLReporter.class})
public class ImgurClient {

    protected HttpClient client;
    private HttpGet getRequest;
    private HttpPost postRequest;
    private HttpDelete deleteRequest;
    private HttpPut putRequest;
    private HttpResponse httpResponse;
    protected ImgurManager manager = new ImgurManager();
    private String accesTokenResponse;
    private JSONParser parser = new JSONParser();
    private JSONObject jsonGetAccessTokenResponse;
    private JSONObject jsonExpiryDate;
    private JSONObject requestBody = new JSONObject();
    private long expireDate;
    private StringBuilder builder;
    protected Action action = new Action();

    @BeforeClass
    public void setUp() throws IOException, ParseException {
        System.setProperty("org.uncommons.reportng.escape-output", "false");
        client = HttpClientBuilder.create().build();
        checkAccessTokenExpiry();
    }

    private void checkAccessTokenExpiry() throws ParseException {
        try (FileReader reader = new FileReader("src/main/resources/token.json")) {

            jsonExpiryDate = (JSONObject) parser.parse(reader);
            expireDate = (long) jsonExpiryDate.get("expireDate");

            Date date = new Date();
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");

            if (expireDate < Long.parseLong(dateFormatter.format(date))) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, 30);
                long newExpireDate = Long.parseLong(dateFormatter.format(calendar.getTime()));
                httpResponse = newAccessToken();
                accesTokenResponse = EntityUtils.toString(httpResponse.getEntity());
                System.out.println("LLLLL");
                action.logResponse(httpResponse, accesTokenResponse);
                jsonGetAccessTokenResponse = (JSONObject) parser.parse(accesTokenResponse);
                JSONObject object = new JSONObject();
                object.put("accessToken", jsonGetAccessTokenResponse.get("access_token"));
                object.put("expireDate", newExpireDate);
                object.put("refreshToken", jsonGetAccessTokenResponse.get("refresh_token"));
                try (FileWriter writer = new FileWriter("src/main/resources/token.json")) {
                    writer.write(object.toString());
                } catch (IOException e) {
                    System.out.println("IOException " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("IOException " + e.getMessage());
        }
    }

    public HttpResponse postGenerateAccessToken(String refreshToken, String clientId, String clientSecret, String grantType)
            throws ParseException, IOException {
        postRequest = new HttpPost("https://api.imgur.com/oauth2/token");
        postRequest.addHeader("content-type","application/json");
        postRequest.addHeader("cookie", "accesstoken="
                + manager.getToken("accessToken").toString() + ";");
        requestBody.put("refresh_token", refreshToken);
        requestBody.put("client_id", clientId);
        requestBody.put("client_secret", clientSecret);
        requestBody.put("grant_type", grantType);
        postRequest.setEntity(new StringEntity(requestBody.toString()));
        action.logRequest(postRequest, "POST");
        return client.execute(postRequest);
    }

    public HttpResponse getGallerySearch(String sort, String window, String page, String searchQuery)
            throws IOException, ParseException {
        builder = new StringBuilder("https://api.imgur.com/3/gallery/search");
        if (sort != null) {
            builder.append("/" + sort);
        }
        if (window != null) {
            builder.append("/" + window);
        }
        if ((page != null)) {
            try {
                Integer.parseInt(page);
            } catch (NumberFormatException nfe){
                System.out.println("Number of page is not an integer type " + nfe.getStackTrace());
                Assert.fail("Number of page is not an integer type " + nfe.getStackTrace());
            }
            builder.append("/" + page);
        }
        builder.append("?q=title:" + searchQuery);
        getRequest = new HttpGet(builder.toString());
        getRequest.addHeader("Authorization", "Client-ID " + manager.getClientData("clientId"));
        getRequest.addHeader(new BasicHeader("cookie", "accesstoken="
                + manager.getToken("accessToken").toString() + ";"));
        action.logRequest(getRequest, "GET");
        return client.execute(getRequest);

    }

    public HttpResponse postShareImageWithCommunity(String imageHash, String title, String topic, boolean acceptTerms,
                                               boolean mature, List<String> tagList) throws IOException, ParseException {
        postRequest = new HttpPost("https://api.imgur.com/3/gallery/image/" + imageHash);
        postRequest.addHeader("Authorization", "Bearer " + manager.getToken("accessToken").toString());
        postRequest.addHeader("cookie", "accesstoken="
                + manager.getToken("accessToken").toString() + ";");
        postRequest.addHeader("content-type","application/json");
        if (title != null) requestBody.put("title", title);
        if (topic != null) requestBody.put("topic", topic);
        if (acceptTerms) {
            requestBody.put("terms", "1");
        } else {
            requestBody.put("terms", "0");
        }
        if (mature) {
            requestBody.put("mature", "1");
        } else {
            requestBody.put("mature", "0");
        }
        if (tagList != null && tagList.size() != 0) {
            StringBuilder builder = new StringBuilder();
            for (String tag : tagList) {
                builder.append(tag);
                if (tagList.indexOf(tag) != tagList.size() - 1) {
                    builder.append(",");
                }
            }
            requestBody.put("tags", builder.toString());
        }
        postRequest.setEntity(new StringEntity(requestBody.toString()));
        action.logRequest(postRequest, "POST");
        return client.execute(postRequest);
    }
    public HttpResponse postShareAlbumWithCommunity(String albumHash, String title, String topic, boolean acceptTerms,
                                                    boolean mature, List<String> tagList) throws IOException, ParseException {
        postRequest = new HttpPost("https://api.imgur.com/3/gallery/album/" + albumHash);
        postRequest.addHeader("Authorization", "Bearer " + manager.getToken("accessToken").toString());
        postRequest.addHeader("cookie", "accesstoken="
                + manager.getToken("accessToken").toString() + ";");
        postRequest.addHeader("content-type","application/json");
        if (title != null) requestBody.put("title", title);
        if (topic != null) requestBody.put("topic", topic);
        if (acceptTerms) {
            requestBody.put("terms", "1");
        } else {
            requestBody.put("terms", "0");
        }
        if (mature) {
            requestBody.put("mature", "1");
        } else {
            requestBody.put("mature", "0");
        }
        if (tagList != null && tagList.size() != 0) {
            StringBuilder builder = new StringBuilder();
            for (String tag : tagList) {
                builder.append(tag);
                if (tagList.indexOf(tag) != tagList.size() - 1) {
                    builder.append(",");
                }
            }
            requestBody.put("tags", builder.toString());
        }
        postRequest.setEntity(new StringEntity(requestBody.toString()));
        action.logRequest(postRequest, "POST");
        return client.execute(postRequest);
    }

    public HttpResponse deleteRemoveFromGallery(String imageHash) throws IOException, ParseException {
        deleteRequest = new HttpDelete("https://api.imgur.com/3/gallery/" + imageHash);
        deleteRequest.addHeader("Authorization", "Bearer "
                + manager.getToken("accessToken").toString());
        deleteRequest.addHeader("cookie", "accesstoken="
                + manager.getToken("accessToken").toString() + ";");
        action.logRequest(deleteRequest, "DELETE");
        return client.execute(deleteRequest);
    }

    public HttpResponse getGalleryImage(String imageHash) throws ParseException, IOException {
        getRequest = new HttpGet("https://api.imgur.com/3/gallery/image/" + imageHash);
        getRequest.addHeader("Authorization", "Client-ID " + manager.getClientData("clientId"));
        getRequest.addHeader("cookie", "accesstoken="
                + manager.getToken("accessToken").toString() + ";");
        action.logRequest(getRequest, "GET");
        return client.execute(getRequest);
    }
    public HttpResponse getImage(String imageHash) throws ParseException, IOException {
        getRequest = new HttpGet("https://api.imgur.com/3/image/" + imageHash);
        getRequest.addHeader("Authorization", "Client-ID " + manager.getClientData("clientId"));
        getRequest.addHeader("cookie", "accesstoken="
                + manager.getToken("accessToken").toString() + ";");
        action.logRequest(getRequest, "GET");
        return client.execute(getRequest);
    }

    public HttpResponse postFavoriteImage(String imageHash) throws IOException, ParseException {
        postRequest = new HttpPost("https://api.imgur.com/3/image/" + imageHash + "/favorite");
        postRequest.addHeader("Authorization", "Bearer " + manager.getToken("accessToken"));
        postRequest.addHeader("cookie", "accesstoken="
                + manager.getToken("accessToken").toString() + ";");
        action.logRequest(postRequest, "POST");
        return client.execute(postRequest);
    }
    public HttpResponse postFavoritealbum(String albumHash) throws IOException, ParseException {
        postRequest = new HttpPost("https://api.imgur.com/3/image/" + albumHash + "/favorite");
        postRequest.addHeader("Authorization", "Bearer " + manager.getToken("accessToken"));
        postRequest.addHeader("cookie", "accesstoken="
                + manager.getToken("accessToken").toString() + ";");
        action.logRequest(postRequest, "POST");
        return client.execute(postRequest);
    }

    public HttpResponse getAlbum(String imageHash) throws ParseException, IOException {
        getRequest = new HttpGet("https://api.imgur.com/3/album/" + imageHash);
        getRequest.addHeader("Authorization", "Client-ID " + manager.getClientData("clientId"));
        getRequest.addHeader("cookie", "accesstoken="
                + manager.getToken("accessToken").toString() + ";");
        action.logRequest(getRequest, "GET");
        return client.execute(getRequest);
    }
    public HttpResponse getGalleryAlbum(String albumHash) throws ParseException, IOException {
        getRequest = new HttpGet("https://api.imgur.com/3/gallery/album/" + albumHash);
        getRequest.addHeader("Authorization", "Client-ID " + manager.getClientData("clientId"));
        getRequest.addHeader("cookie", "accesstoken="
                + manager.getToken("accessToken").toString() + ";");
        action.logRequest(getRequest, "GET");
        return client.execute(getRequest);
    }

    public HttpResponse getGalleryCommentsSorted(String galleryHash, String commentSort) throws IOException, ParseException {
        getRequest = new HttpGet("https://api.imgur.com/3/gallery/" + galleryHash + "/comments/" + commentSort);
        getRequest.addHeader("Authorization", "Client-ID " + manager.getClientData("clientId"));
        getRequest.addHeader("cookie", "accesstoken="
                + manager.getToken("accessToken").toString() + ";");
        action.logRequest(getRequest, "GET");
        return client.execute(getRequest);
    }

    public HttpResponse getGalleryCommentByID(String galleryHash, String commentID) throws IOException, ParseException {
        getRequest = new HttpGet("https://api.imgur.com/3/gallery/" + galleryHash + "/comment/" + commentID);
        getRequest.addHeader("Authorization", "Client-ID " + manager.getClientData("clientId"));
        getRequest.addHeader("cookie", "accesstoken="
                + manager.getToken("accessToken").toString() + ";");
        action.logRequest(getRequest, "GET");
        return client.execute(getRequest);
    }

    public HttpResponse getAccountImagesCount(String accountName) throws IOException, ParseException {
        getRequest = new HttpGet("https://api.imgur.com/3/account/" + accountName + "/images/count");
        getRequest.addHeader("Authorization", "Bearer " + manager.getToken("accessToken"));
        getRequest.addHeader("cookie", "accesstoken="
                + manager.getToken("accessToken").toString() + ";");
        action.logRequest(getRequest, "GET");
        return client.execute(getRequest);
    }

    public HttpResponse getAccountComments(String accountName, String commentSort) throws IOException, ParseException {
        getRequest = new HttpGet("https://api.imgur.com/3/account/" + accountName + "/comments/" + commentSort);
        getRequest.addHeader("Authorization", "Bearer " + manager.getToken("accessToken"));
        getRequest.addHeader("cookie", "accesstoken="
                + manager.getToken("accessToken").toString() + ";");
        action.logRequest(getRequest, "GET");
        return client.execute(getRequest);
    }

    public HttpResponse postReplyCreation(String imageHash, String commentID, String text) throws IOException, ParseException {
        postRequest = new HttpPost("https://api.imgur.com/3/comment/" + commentID);
        postRequest.addHeader("Authorization", "Bearer " + manager.getToken("accessToken"));
        postRequest.addHeader("cookie", "accesstoken="
                + manager.getToken("accessToken").toString() + ";");
        postRequest.addHeader("content-type","application/json");
        requestBody.put("image_id", imageHash);
        requestBody.put("comment", text);
        postRequest.setEntity(new StringEntity(requestBody.toString()));
        action.logRequest(postRequest, "POST");
        return client.execute(postRequest);
    }

    public HttpResponse deleteCommentDeletion(String accountName, String commentID) throws IOException, ParseException {
        deleteRequest = new HttpDelete("https://api.imgur.com/3/account/" + accountName + "/comment/" + commentID);
        deleteRequest.addHeader("Authorization", "Bearer " + manager.getToken("accessToken"));
        deleteRequest.addHeader("cookie", "accesstoken="
                + manager.getToken("accessToken").toString() + ";");
        action.logRequest(deleteRequest, "DELETE");
        return client.execute(deleteRequest);
    }

    public HttpResponse getComment(String commentID) throws IOException, ParseException {
        getRequest = new HttpGet("https://api.imgur.com/3/comment/" + commentID);
        getRequest.addHeader("Authorization", "Client-ID " + manager.getClientData("clientId"));
        getRequest.addHeader("cookie", "accesstoken="
                + manager.getToken("accessToken").toString() + ";");
        action.logRequest(getRequest, "GET");
        return client.execute(getRequest);
    }

    public HttpResponse postCommentCreation(String imageHash, String comment) throws IOException, ParseException {
        postRequest = new HttpPost("https://api.imgur.com/3/comment");
        postRequest.addHeader("Authorization", "Bearer " + manager.getToken("accessToken"));
        postRequest.addHeader("cookie", "accesstoken="
                + manager.getToken("accessToken").toString() + ";");
        postRequest.addHeader("content-type","application/json");
        requestBody.put("image_id", imageHash);
        requestBody.put("comment", comment);
        postRequest.setEntity(new StringEntity(requestBody.toString()));
        action.logRequest(postRequest, "POST");
        return client.execute(postRequest);
    }

    public HttpResponse getAlbumCount(String accountName) throws IOException, ParseException {
        getRequest = new HttpGet("https://api.imgur.com/3/account/" + accountName + "/albums/count");
        getRequest.addHeader("Authorization", "Bearer " + manager.getToken("accessToken"));
        getRequest.addHeader("cookie", "accesstoken="
                + manager.getToken("accessToken").toString() + ";");
        action.logRequest(getRequest, "GET");
        return client.execute(getRequest);
    }

    public HttpResponse deleteAlbumDeletion(String accountName, String albumHash) throws IOException, ParseException {
        deleteRequest = new HttpDelete("https://api.imgur.com/3/account/" + accountName + "/album/" + albumHash);
        deleteRequest.addHeader("Authorization", "Bearer " + manager.getToken("accessToken"));
        deleteRequest.addHeader("cookie", "accesstoken="
                + manager.getToken("accessToken").toString() + ";");
        action.logRequest(deleteRequest, "DELETE");
        return client.execute(deleteRequest);
    }

    public HttpResponse postAlbumCreation(String albumTitle, List<String> ids) throws IOException, ParseException {
        postRequest = new HttpPost("https://api.imgur.com/3/album");
        postRequest.addHeader("Authorization", "Bearer " + manager.getToken("accessToken"));
        postRequest.addHeader("cookie", "accesstoken="
                + manager.getToken("accessToken").toString() + ";");
        postRequest.addHeader("content-type","application/json");
        requestBody.put("title", albumTitle);
        for(String id : ids) {
            requestBody.put("ids[]", id);
        }
        postRequest.setEntity(new StringEntity(requestBody.toString()));
        action.logRequest(postRequest, "POST");
        return client.execute(postRequest);
    }

    public HttpResponse getAlbums(String accountName) throws IOException, ParseException {
        getRequest = new HttpGet("https://api.imgur.com/3/account/" + accountName + "/albums/0");
        getRequest.addHeader("Authorization", "Bearer " + manager.getToken("accessToken"));
        getRequest.addHeader("cookie", "accesstoken="
                + manager.getToken("accessToken").toString() + ";");
        action.logRequest(getRequest, "GET");
        return client.execute(getRequest);
    }

    public HttpResponse getAlbumImages(String albumHash) throws IOException, ParseException {
        getRequest = new HttpGet("https://api.imgur.com/3/album/" + albumHash + "/images");
        getRequest.addHeader("Authorization", "Client-ID " + manager.getClientData("clientId"));
        getRequest.addHeader("cookie", "accesstoken="
                + manager.getToken("accessToken").toString() + ";");
        action.logRequest(getRequest, "GET");
        return client.execute(getRequest);
    }


    public HttpResponse postUpdateGalleryItemTags(String imageHash, List<String> tagList) throws IOException, ParseException {
        postRequest = new HttpPost("https://api.imgur.com/3/gallery/tags/" + imageHash);
        postRequest.addHeader("Authorization", "Bearer " + manager.getToken("accessToken").toString());
        postRequest.addHeader("cookie", "accesstoken="
                + manager.getToken("accessToken").toString() + ";");
        postRequest.addHeader("content-type","application/json");
        StringBuilder builder = new StringBuilder();
        for (String tag : tagList) {
            builder.append(tag);
            if (tagList.indexOf(tag) != tagList.size() - 1) {
                builder.append(",");
            }
        }
        requestBody.put("tags", builder.toString());
        postRequest.setEntity(new StringEntity(requestBody.toString()));
        action.logRequest(postRequest, "POST");
        return client.execute(postRequest);
    }

    public HttpResponse postUpdateImageInformation(String imageHash, String title, String description)
            throws IOException, ParseException {
        postRequest = new HttpPost("https://api.imgur.com/3/image/" + imageHash);
        postRequest.addHeader("Authorization", "Bearer " + manager.getToken("accessToken").toString());
        postRequest.addHeader("cookie", "accesstoken="
                + manager.getToken("accessToken").toString() + ";");
        postRequest.addHeader("content-type","application/json");
        if (title != null) requestBody.put("title", title);
        if (description != null) requestBody.put("description", description);
        postRequest.setEntity(new StringEntity(requestBody.toString()));
        action.logRequest(postRequest, "POST");
        return client.execute(postRequest);
    }

    public HttpResponse getAccountAvailableAvatars() throws IOException, ParseException {
        getRequest = new HttpGet("https://api.imgur.com/3/account/" + manager.getClientData("username") + "/available_avatars");
        getRequest.addHeader("Client-ID", manager.getClientData("clientId"));
        getRequest.addHeader("Authorization", "Bearer " + manager.getToken("accessToken"));
        getRequest.addHeader("cookie", "accesstoken="
                + manager.getToken("accessToken").toString() + ";");
        action.logRequest(getRequest, "GET");
        return client.execute(getRequest);
    }

    public HttpResponse putChangeAccountSettings(String username, String publicImages, String messaging,
                                                 String albumPrivacy, String terms, String mature, String newsletter,
                                                 String avatar)
            throws ParseException, IOException {
        putRequest = new HttpPut("https://api.imgur.com/3/account/me/settings");
        putRequest.addHeader("Authorization", "Bearer " + manager.getToken("accessToken").toString());
        putRequest.addHeader("cookie", "accesstoken="
                + manager.getToken("accessToken").toString() + ";");
        putRequest.addHeader("content-type","application/json");
        if (publicImages != null) requestBody.put("public_images", publicImages);
        if (messaging != null) requestBody.put("messaging_enabled", messaging);
        if (albumPrivacy != null) requestBody.put("album_privacy", albumPrivacy);
        if (terms != null) requestBody.put("accepted_gallery_terms", terms);
        if (username != null) requestBody.put("username", username);
        if (mature != null) requestBody.put("show_mature", mature);
        if (newsletter != null) requestBody.put("newsletter_subscribed", newsletter);
        if (avatar != null) requestBody.put("avatar", avatar);
        putRequest.setEntity(new StringEntity(requestBody.toString()));
        action.logRequest(putRequest, "PUT");
        return client.execute(putRequest);
    }

    public HttpResponse getAccountAvatar() throws IOException, ParseException {
        getRequest = new HttpGet("https://api.imgur.com/3/account/" + manager.getClientData("username") +
                "/avatar");
        getRequest.addHeader("Authorization", "Bearer " + manager.getToken("accessToken").toString());
        action.logRequest(getRequest, "GET");
        return client.execute(getRequest);
    }

    private HttpResponse newAccessToken() throws IOException, ParseException {
        postRequest = new HttpPost("https://api.imgur.com/oauth2/token");
        requestBody.put("refresh_token", manager.getToken("refreshToken"));
        requestBody.put("client_id", manager.getClientData("clientId"));
        requestBody.put("client_secret", manager.getClientData("clientSecret"));
        requestBody.put("grant_type", manager.getClientData("grant_type"));
        postRequest.setEntity(new StringEntity(requestBody.toString()));
        action.logRequest(postRequest, "POST");
        return client.execute(postRequest);
    }

    public HttpResponse putChangeAccountSettings(String username, String publicImages, String messaging,
                                                 String albumPrivacy, String terms, String mature, String newsletter)
            throws ParseException, IOException {
        putRequest = new HttpPut("https://api.imgur.com/3/account/me/settings");
        putRequest.addHeader("Authorization", "Bearer " + manager.getToken("accessToken").toString());
        putRequest.addHeader("cookie", "accesstoken="
                + manager.getToken("accessToken").toString() + ";");
        putRequest.addHeader("content-type","application/json");
        if(publicImages!=null)requestBody.put("public_images", publicImages);
        if(messaging!=null)requestBody.put("messaging_enabled", messaging);
        if(albumPrivacy!=null)requestBody.put("album_privacy", albumPrivacy);
        if(terms!=null)requestBody.put("accepted_gallery_terms", terms);
        if(username!=null)requestBody.put("username", username);
        if(mature!=null)requestBody.put("show_mature", mature);
        if(newsletter!=null)requestBody.put("newsletter_subscribed", newsletter);
        putRequest.setEntity(new StringEntity(requestBody.toString()));
        action.logRequest(putRequest, "PUT");
        return client.execute(putRequest);
        }

    public HttpResponse getAccountSettings(String name) throws IOException, ParseException {
        builder = new StringBuilder("https://api.imgur.com/3/account/");
        builder.append(name);
        builder.append("/settings");
        getRequest = new HttpGet(builder.toString());
        getRequest.addHeader("Authorization", "Bearer " + manager.getToken("accessToken").toString());
        getRequest.addHeader("cookie", "accesstoken="
                + manager.getToken("accessToken").toString() + ";");
        action.logRequest(getRequest, "GET");
        return client.execute(getRequest);
    }

    public HttpResponse getGalleryTagInfo(String tagName) throws IOException, ParseException {
        getRequest = new HttpGet("https://api.imgur.com/3/gallery/tag_info/" + tagName);
        getRequest.addHeader("Authorization", "Client-ID " + manager.getClientData("clientId"));
        getRequest.addHeader("cookie", "accesstoken="
                + manager.getToken("accessToken").toString() + ";");
        action.logRequest(getRequest, "GET");
        return client.execute(getRequest);
    }
    public HttpResponse getAlbumImage(String albumHash, String imageHash) throws IOException, ParseException {
        getRequest = new HttpGet("https://api.imgur.com/3/album/" + albumHash + "/image/" + imageHash);
        getRequest.addHeader("Authorization", "Client-ID " + manager.getClientData("clientId"));
        getRequest.addHeader("cookie", "accesstoken="
                + manager.getToken("accessToken").toString() + ";");
        action.logRequest(getRequest, "GET");
        return client.execute(getRequest);
    }
    @AfterSuite
    public void showReport() throws IOException {
        File file = new File("test-output/html/index.html");
        Desktop.getDesktop().browse(file.toURI());
    }
}
