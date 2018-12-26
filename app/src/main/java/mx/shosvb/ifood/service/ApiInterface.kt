package mx.shosvb.ifood.service

import com.twitter.sdk.android.core.models.User

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface APIInterface {

    @GET("i/search/typeahead.json")
    fun findUser(@Query("count") count: Integer,
                 @Query("filters") filters:Boolean,
                 @Query( "q") q:String ): Call<List<User>>


    //https://twitter.com/i/search/typeahead.json?count=10&filters=true&lang=es&q=isabe%20&result_type=topics%2Cusers&src=SEARCH_BOX


    companion object {

        fun create(): APIInterface {



            //val oAuth = OAuth1aInterceptor( TwitterCore.getInstance().getSessionManager().getActiveSession(), TwitterCore.getInstance().getAuthConfig() );
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS

            val client = OkHttpClient().newBuilder().addInterceptor(loggingInterceptor)
                    .build()


            val retrofit = Retrofit.Builder().baseUrl("https://twitter.com/").
                    client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client( client ).build()

            return retrofit.create( APIInterface::class.java )
        }
    }


}