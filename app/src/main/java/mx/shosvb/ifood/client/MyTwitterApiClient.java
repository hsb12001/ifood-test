package mx.shosvb.ifood.client;

import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;

import mx.shosvb.ifood.service.CustomService;
import okhttp3.OkHttpClient;

public class MyTwitterApiClient extends TwitterApiClient {

    public MyTwitterApiClient(TwitterSession session, OkHttpClient client) {
        super( session, client);
    }



    public MyTwitterApiClient(OkHttpClient client) {
        super(client);
    }

    /**
     * Provide CustomService with defined endpoints
     */
    public CustomService getCustomService() {
        return getService(CustomService.class);
    }
}

