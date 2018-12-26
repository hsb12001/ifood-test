package mx.shosvb.ifood.service

import mx.shosvb.ifood.models.Sentiment
import mx.shosvb.ifood.models.analyzed.AnalyzedDocument
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query


interface GCNLInterface {

    @Headers("Accept: application/json", "Content-Type: application/json")

    @POST("v1/documents:analyzeSentiment")
    fun analyzeSentiment(@Query( "key") key: String,
                 @Body sentiment:Sentiment): Call<AnalyzedDocument>


    //https://twitter.com/i/search/typeahead.json?count=10&filters=true&lang=es&q=isabe%20&result_type=topics%2Cusers&src=SEARCH_BOX


    companion object {

        fun create(): GCNLInterface {

            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY;

            val client = OkHttpClient().newBuilder().addInterceptor(loggingInterceptor)
                    .build()


            val retrofit = Retrofit.Builder().baseUrl("https://language.googleapis.com/").
                    client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client( client ).build()

            return retrofit.create( GCNLInterface::class.java )
        }
    }


}