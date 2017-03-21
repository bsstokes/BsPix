package com.bsstokes.bspix.api;

import java.util.List;

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

    @GET("users/self/media/recent/")
    Observable<Response<InstagramResponse<List<Media>>>> getRecentMedia();

    @GET("users/self/follows")
    Observable<Response<InstagramResponse<List<FollowedUser>>>> getFollows();

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
        public Counts counts = new Counts();

        public static class Counts {
            public int media;
            public int follows;
            public int followed_by;
        }
    }

    class FollowedUser {
        public String id;
        public String username;
        public String profile_picture;
        public String full_name;
    }

    class Media {
        public String id;
        public Caption caption;
        public User user; // username, profile_picture, id
        public Images images;
        public String type;
        public List<String> tags;
        public Location location;

        public static class Caption {
            public String text;
        }

        public static class Images {
            public Image low_resolution;
            public Image thumbnail;
            public Image standard_resolution;
        }

        public static class Image {
            public String url;
            public int width;
            public int height;
        }

        public static class Location {
            public double latitude;
            public double longitude;
            public String id;
            public String street_address;
            public String name;
        }
    }
}
