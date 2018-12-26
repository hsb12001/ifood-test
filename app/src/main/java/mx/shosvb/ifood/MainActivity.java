package mx.shosvb.ifood;

import android.annotation.TargetApi;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.text.emoji.EmojiCompat;
import android.support.text.emoji.FontRequestEmojiCompatConfig;
import android.support.text.emoji.bundled.BundledEmojiCompatConfig;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.provider.FontRequest;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Locale;

import mx.shosvb.ifood.adapters.FoodPagerAdapter;
import mx.shosvb.ifood.fragments.TimelineFragment;


public class MainActivity extends AppCompatActivity implements TimelineFragment.OnReadyTweets{
    final Locale deviceLocale = Locale.getDefault();
    private static final String TAG = "SearchTimelineActivity";
    private  ViewPager viewPager;

    private static final boolean USE_BUNDLED_EMOJI = true;


    @Override
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d( TAG, "Initialize emoji...");
        initEmojiCompat();
        setContentView( R.layout.activity_main );
        final FragmentManager fm = getSupportFragmentManager();
        final FragmentPagerAdapter pagerAdapter = new FoodPagerAdapter( fm, getResources());
        viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);

        final TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);


    }



    private void initEmojiCompat() {
        final EmojiCompat.Config config;
        if (USE_BUNDLED_EMOJI) {
            // Use the bundled font for EmojiCompat
            config = new BundledEmojiCompatConfig(getApplicationContext());
        } else {
            // Use a downloadable font for EmojiCompat
            final FontRequest fontRequest = new FontRequest(
                    "com.google.android.gms.fonts",
                    "com.google.android.gms",
                    "Noto Color Emoji Compat",
                    R.array.com_google_android_gms_fonts_certs);
            config = new FontRequestEmojiCompatConfig(getApplicationContext(), fontRequest);
        }

        config.setReplaceAll(true)
                .registerInitCallback(new EmojiCompat.InitCallback() {
                    @Override
                    public void onInitialized() {
                        Log.i(TAG, "EmojiCompat initialized");
                    }

                    @Override
                    public void onFailed(@Nullable Throwable throwable) {
                        Log.e(TAG, "EmojiCompat initialization failed", throwable);
                    }
                });

        EmojiCompat.init(config);
    }


    @Override
    public void show() {
        viewPager.setCurrentItem(1);
    }





    @Override
    public void onResume() {
        super.onResume();

        final Locale locale = new Locale("us");
        Locale.setDefault(locale);
        final Configuration config = getResources().getConfiguration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    @Override
    public void onPause() {
        super.onPause();

        Locale.setDefault(deviceLocale);
        final Configuration config = getResources().getConfiguration();
        config.locale = deviceLocale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }



}
