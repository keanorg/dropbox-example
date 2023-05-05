package com.mycompany.dropbox;

import com.dropbox.core.*;
import com.dropbox.core.oauth.DbxCredential;

import com.dropbox.core.v2.DbxClientV2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

public class DropboxAuth{

    private static final String APP_KEY = "your_app_key";
    private static final String APP_SECRET = "your_app_secret";

    public static void main(String[] args) throws IOException, DbxException {
        DbxRequestConfig requestConfig = DbxRequestConfig.newBuilder("dropbox/java-auth-example")
                .withUserLocaleFrom(Locale.getDefault())
                .build();
        DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);
        DbxWebAuth webAuth = new DbxWebAuth(requestConfig, appInfo);
        
        // Have the user sign in and authorize your app
        String authorizeUrl = webAuth.authorize(DbxWebAuth.newRequestBuilder().withNoRedirect().build());
        System.out.println("1. Go to " + authorizeUrl);
        System.out.println("2. Click 'Allow' (you might have to log in first)");
        System.out.println("3. Copy the authorization code.");
        String code = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
        DbxAuthFinish authFinish = webAuth.finishFromCode(code);
        String accessToken = authFinish.getAccessToken();
        System.out.println("Access Token: " + accessToken);
        String refreshToken = authFinish.getRefreshToken();
        System.out.println("Refresh Token: " + refreshToken);
    }
}
