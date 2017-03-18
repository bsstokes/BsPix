package com.bsstokes.bspix.api;

import okhttp3.HttpUrl;
import retrofit2.Response;
import retrofit2.http.GET;
import rx.Observable;

public interface InstagramApi {

    String CLIENT_ID = "dc7dc3c3e1a64726b2adf148a3e03194";
    String SCOPE = "follower_list likes public_content";

    HttpUrl BASE_URL = HttpUrl.parse("https://api.instagram.com/");
    HttpUrl BASE_API_URL = HttpUrl.parse("https://api.instagram.com/v1/");

    HttpUrl REDIRECT_URL = HttpUrl.parse("http://bspix.bsstokes.com/ig/authenticate");
    HttpUrl AUTHORIZE_URL = BASE_URL.newBuilder()
            .addPathSegments("oauth/authorize/")
            .addQueryParameter("client_id", CLIENT_ID)
            .addQueryParameter("redirect_uri", REDIRECT_URL.toString())
            .addQueryParameter("response_type", "token")
            .addQueryParameter("scope", SCOPE)
            .build();

    @GET("users/self/")
    Observable<Response<InstagramResponse<User>>> getSelf();

    class InstagramResponse<T> {
        public T data;
    }

    class User {
        public String id;
        public String username;
        public String full_name;
        public String profile_picture;
        public String bio;
        public String website;
        public Counts counts;

        public static class Counts {
            public int media;
            public int follows;
            public int followed_by;
        }
    }
}
