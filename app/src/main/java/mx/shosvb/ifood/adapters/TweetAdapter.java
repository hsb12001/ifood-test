package mx.shosvb.ifood.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

import mx.shosvb.ifood.R;
import mx.shosvb.ifood.models.MyTweet;


public class TweetAdapter extends ArrayAdapter<MyTweet> {
    private final LayoutInflater mInflater;

    public TweetAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_2);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(List<Tweet> data) {
        clear();
        if (data != null) {
            for (Tweet appEntry : data) {
                add(new MyTweet(appEntry.text));
            }
        }
        notifyDataSetChanged();
    }

    Drawable backupColor;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder holder = null;


        final MyTweet item = getItem(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.tweet_row, parent, false);
            holder.tweetText = convertView.findViewById(R.id.tweet_text);
            holder.bg = convertView.findViewById(R.id.container_row);
            holder.emojiSentiment = convertView.findViewById( R.id.emoji_sentiment );
            if (backupColor == null)
                backupColor = holder.bg.getBackground();
            convertView.setTag(holder);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tweetText.setText(item.text);
        if (item.getSentimentColor() != null) {
            holder.emojiSentiment.setText( item.getEmojiText() );
            holder.emojiSentiment.setVisibility( View.VISIBLE );
            holder.bg.setBackgroundColor(item.getSentimentColor());
        } else {
            holder.emojiSentiment.setText( null );
            holder.emojiSentiment.setVisibility( View.GONE );
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                holder.bg.setBackground(backupColor);
            } else {
                holder.bg.setBackgroundDrawable(backupColor);
            }
        }


        return convertView;
    }

    public static class ViewHolder {
        public LinearLayout bg;
        public TextView tweetText;
        public TextView emojiSentiment;

    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }


}