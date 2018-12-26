package mx.shosvb.ifood.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.internal.UserUtils;
import com.twitter.sdk.android.core.models.User;

import java.util.ArrayList;
import java.util.List;

import mx.shosvb.ifood.R;

public class SearchUserAdapter extends ArrayAdapter<User>{
    private final Picasso imageLoader;
    private List<User> mlistData;
    private final LayoutInflater mInflater;
    public SearchUserAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        imageLoader = Picasso.with( context );
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mlistData = new ArrayList<>();
    }



    public void setData(List<User> list) {
        mlistData.clear();
        mlistData.addAll(list);
    }

    @Override
    public int getCount() {
        return mlistData.size();
    }

    @Nullable
    @Override
    public User getItem(int position) {
        return mlistData.get(position);
    }

    /**
     * Used to Return the full object directly from adapter.
     *
     * @param position
     * @return
     */
    public User getObject(int position) {
        return mlistData.get(position);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        SearchUserAdapter.ViewHolder holder = null;


        final User item = getItem(position);
        if (convertView == null) {
            holder = new SearchUserAdapter.ViewHolder();
            convertView = mInflater.inflate(R.layout.search_row, parent, false);
            holder.userText = convertView.findViewById( R.id.user_text );
            holder.userImage = convertView.findViewById( R.id.user_image );
            convertView.setTag(holder);
        }else {
            holder = (SearchUserAdapter.ViewHolder)convertView.getTag();
        }

        holder.userText.setText( item.name );
        final String url;
        url = UserUtils.getProfileImageUrlHttps(item, UserUtils.AvatarSize.REASONABLY_SMALL);

        ColorDrawable avatarMediaBg = new ColorDrawable( Color.BLACK );
        imageLoader.load(url).placeholder(avatarMediaBg).into(holder.userImage);


        return convertView;
    }

    public static class ViewHolder {
        public ImageView userImage;
        public TextView userText;

    }

}
