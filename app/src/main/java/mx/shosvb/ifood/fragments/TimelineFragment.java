package mx.shosvb.ifood.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import mx.shosvb.ifood.R;
import mx.shosvb.ifood.adapters.TweetAdapter;
import mx.shosvb.ifood.listener.EndlessScrollListener;
import mx.shosvb.ifood.models.Document;
import mx.shosvb.ifood.models.MyTweet;
import mx.shosvb.ifood.models.Sentiment;
import mx.shosvb.ifood.models.TweetMessageEvent;
import mx.shosvb.ifood.models.analyzed.AnalyzedDocument;
import mx.shosvb.ifood.service.GCNLInterface;
import retrofit2.Call;
import retrofit2.Response;


public class TimelineFragment extends ListFragment{
    private static final String TAG = "TimelineFragment";
    private TweetAdapter tweetAdapter;
    private OnReadyTweets onReadyTweets;

    public static TimelineFragment newInstance() {
        return new TimelineFragment();
    }

    GCNLInterface gcnlInterface;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        gcnlInterface = GCNLInterface.Companion.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onReadyTweets = (OnReadyTweets) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tweetui_timeline, container, false);
        tweetAdapter = new TweetAdapter( getContext() );

        setListAdapter(tweetAdapter);




        return view;
    }

    private static final String HAPPY_EMOJI="\ud83d\ude03";
    private static final String NEUTRAL_EMOJI="\ud83d\ude10";
    private static final String SAD_EMOJI="\ud83d\ude14";

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String text = ((MyTweet)l.getItemAtPosition(position)).text;
        Sentiment sentiment = new Sentiment();
        Document document = new Document();
        sentiment.setDocument(document);
        document.setContent(text);
        document.setType( Document.Type.HTML_TEXT.value );// "PLAIN_TEXT");
        sentiment.setEncodingType(Document.Encoding.UTF8.value );

        Call<AnalyzedDocument> analyzed = gcnlInterface.analyzeSentiment("AIzaSyC8enyanwMy-yS_wpPuqXCLI0h7ZIcFq8k", sentiment );
        analyzed.enqueue(new retrofit2.Callback<AnalyzedDocument>() {
            @Override
            public void onResponse(Call<AnalyzedDocument> call, Response<AnalyzedDocument> response) {
                Log.d( TAG, "<<<<<< input" );
                //Log.d( TAG, "::::" + new Gson().toJson(response.body() ) );
                if( response.body().getDocumentSentiment().getScore() == 0 ){
                    LinearLayout bg = v.findViewById(R.id.container_row);
                    ((MyTweet)l.getItemAtPosition(position)).setEmojiText( getString( R.string.neutral_tweet, NEUTRAL_EMOJI )  );
                    ((MyTweet)l.getItemAtPosition(position)).setSentimentColor( ResourcesCompat.getColor( getResources(), R.color.neutral_sentiment, null ) );
                    bg.setBackgroundColor( ResourcesCompat.getColor( getResources(), R.color.neutral_sentiment, null ) );

                    tweetAdapter.notifyDataSetChanged();

                    //Emoji neutral gray
                    return;
                }
                if( response.body().getDocumentSentiment().getScore() < 0 ){
                    LinearLayout bg = v.findViewById(R.id.container_row);
                    ((MyTweet)l.getItemAtPosition(position)).setSentimentColor( ResourcesCompat.getColor( getResources(), R.color.sad_sentiment, null ) );
                    ((MyTweet)l.getItemAtPosition(position)).setEmojiText( getString( R.string.sad_tweet, SAD_EMOJI )  );
                    bg.setBackgroundColor( ResourcesCompat.getColor( getResources(), R.color.sad_sentiment, null ) );

                    tweetAdapter.notifyDataSetChanged();


                    //Emoji sad blue
                    return;
                }
                if( response.body().getDocumentSentiment().getScore() > 0 ){
                    LinearLayout bg = v.findViewById(R.id.container_row);
                    bg.setBackgroundColor( ResourcesCompat.getColor( getResources(), R.color.happy_sentiment, null ) );
                    ((MyTweet)l.getItemAtPosition(position)).setEmojiText( getString( R.string.happy_tweet, HAPPY_EMOJI )  );
                    ((MyTweet)l.getItemAtPosition(position)).setSentimentColor( ResourcesCompat.getColor( getResources(), R.color.happy_sentiment, null ) );
                    tweetAdapter.notifyDataSetChanged();

                    //Emoji Happy yellow
                    return;
                }



            }

            @Override
            public void onFailure(Call<AnalyzedDocument> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(), R.string.sentiment_failure, Toast.LENGTH_SHORT ).show();

            }
        });


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(TweetMessageEvent event) {
        Log.d("MainActivity", "From Fragment search : " + event.id);
        tweetAdapter.clear();
        tweetAdapter.notifyDataSetChanged();
        Call<List<Tweet>> tweets = TwitterCore.getInstance().getApiClient().
                getStatusesService().userTimeline(
                event.id,
                null,
                30,
                null,
                null,
                false,
                true,
                null,
                null);

        tweets.enqueue(new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> result) {
                //for (Tweet t : result.data) {
                    //Log.d( TAG,t.text);
                //    Log.d(TAG, "id>>>>" + t.id + "");
                //}
                tweetAdapter.setData(result.data);
                tweetAdapter.notifyDataSetChanged();
                onReadyTweets.show();

                getListView().setOnScrollListener(new EndlessScrollListener() {
                    @Override
                    public boolean onLoadMore(int page, int totalItemsCount) {
                        Log.d(TAG, " load more... " + page + "---" + totalItemsCount);
                        return false;
                    }
                });
            }

            @Override
            public void failure(TwitterException exception) {

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public interface OnReadyTweets {
        void show();

    }

}