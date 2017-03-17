package com.bsstokes.bspix.api;

import okhttp3.HttpUrl;

public interface InstagramApi {

    String CLIENT_ID = "dc7dc3c3e1a64726b2adf148a3e03194";
    String SCOPE = "follower_list likes public_content";

    HttpUrl BASE_URL = HttpUrl.parse("https://api.instagram.com/");

    HttpUrl REDIRECT_URL = HttpUrl.parse("http://bspix.bsstokes.com/ig/authenticate");
    HttpUrl AUTHORIZE_URL = BASE_URL.newBuilder()
            .addPathSegments("oauth/authorize/")
            .addQueryParameter("client_id", CLIENT_ID)
            .addQueryParameter("redirect_uri", REDIRECT_URL.toString())
            .addQueryParameter("response_type", "token")
            .addQueryParameter("scope", SCOPE)
            .build();
}
