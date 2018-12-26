/*
 * Copyright (C) 2015 Twitter, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package mx.shosvb.ifood;

import android.app.Application;
import android.os.StrictMode;
import android.support.text.emoji.EmojiCompat;
import android.support.text.emoji.bundled.BundledEmojiCompatConfig;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

import mx.shosvb.ifood.client.MyTwitterApiClient;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class IfooApplication extends Application {
    private static final String TAG = IfooApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);

        Log.d(TAG, "Setting up StrictMode policy checking.");
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());




        EmojiCompat.init(new BundledEmojiCompatConfig( this ));

        TwitterAuthConfig twitterAuthConfig = new TwitterAuthConfig("sabb2BhL8UX7mJv3lDsS4pdeZ","t21V0uish126TVjD0PZZUH7ch9gSpRhe6rCXTa1q1N6arU3tI9");

        TwitterConfig twitterConfig = new TwitterConfig.Builder(this).twitterAuthConfig(twitterAuthConfig).build();

        Twitter.initialize( twitterConfig );

        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        final OkHttpClient customClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor).build();

        final TwitterSession activeSession = TwitterCore.getInstance().getSessionManager().getActiveSession();

        final MyTwitterApiClient customApiClient;
        if (activeSession != null) {

            customApiClient = new MyTwitterApiClient(activeSession, customClient);
            TwitterCore.getInstance().addApiClient(activeSession, customApiClient);
        } else {
            customApiClient = new MyTwitterApiClient(customClient);
            TwitterCore.getInstance().addGuestApiClient(customApiClient);
        }


    }
}
