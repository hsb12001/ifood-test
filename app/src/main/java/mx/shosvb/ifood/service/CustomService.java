package mx.shosvb.ifood.service;

import com.twitter.sdk.android.core.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CustomService {
    @GET("/1.1/users/search.json")
    Call<List<User>> findUser(@Query("q") String username, @Query("count") int count );
}