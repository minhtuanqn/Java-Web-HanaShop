/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuanlm.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author MINH TUAN
 */
public class GoogleUtils {
    // mo trong net bean á
    // cai trang login dau mo len tui coi voi
//    public static String GOOGLE_CLIENT_ID = "613234871797-oj1q5fpves90ain4lfarpviobq5bcrht.apps.googleusercontent.com";
    public static String GOOGLE_CLIENT_ID = "891672189666-dc3srsif66268npgrb76hvmbmn5h0kvl.apps.googleusercontent.com";
//    public static String GOOGLE_CLIENT_SERCRET = "E5BwkVq72slje2jCRbPHRHeg";
    public static String GOOGLE_CLIENT_SERCRET = "pGh3O_556P4ScryZwevZirqS";
    public static String GOOGLE_REDIRECT_LINK = "http://localhost:8080/Assignment1_LeMinhTuan/DispatchServlet?btnAction=GoogleLogin";
    public static String GOOGLE_GET_TOKEN = "https://oauth2.googleapis.com/token";
    public static String GOOGLE_LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";
    public static String GOOGLE_GRANT_TYPE = "authorization_code";
      
    public static String getToken(final String code) throws MalformedURLException, IOException, JSONException {
        String urlParameters = "code=" 
                + code
                + "&client_id=" + GOOGLE_CLIENT_ID
                + "&client_secret=" + GOOGLE_CLIENT_SERCRET
                + "&redirect_uri=" + GOOGLE_REDIRECT_LINK
                + "&grant_type=" + GOOGLE_GRANT_TYPE;
        
        URL url = new URL(GOOGLE_GET_TOKEN);
        URLConnection urlCnn = url.openConnection();
        urlCnn.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(urlCnn.getOutputStream());
        writer.write(urlParameters);
        writer.flush();
        BufferedReader br = new BufferedReader(new InputStreamReader(urlCnn.getInputStream()));
        JSONTokener jt = new JSONTokener(br);
        JSONObject jsonObject = new JSONObject(jt);
        String accessToken = (String) jsonObject.get("access_token");
        return accessToken;
    }  
    
    public static String getUserInfor(final String accessToken) throws MalformedURLException, IOException, JSONException {
        String link = GOOGLE_LINK_GET_USER_INFO + accessToken;
        URL url = new URL(link);
        URLConnection urlCnn = url.openConnection();
        urlCnn.setDoOutput(true);
        BufferedReader br = new BufferedReader(new InputStreamReader(urlCnn.getInputStream()));
        JSONTokener jt = new JSONTokener(br);
        JSONObject jsonObject = new JSONObject(jt);
        return (String) jsonObject.get("email");
    }
}
