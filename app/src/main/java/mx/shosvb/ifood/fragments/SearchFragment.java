package mx.shosvb.ifood.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.User;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import mx.shosvb.ifood.R;
import mx.shosvb.ifood.adapters.SearchUserAdapter;
import mx.shosvb.ifood.client.MyTwitterApiClient;
import mx.shosvb.ifood.models.TweetMessageEvent;
import retrofit2.Call;

public class SearchFragment extends ListFragment {
    private static final String TAG="SearchFragment";
    public static SearchFragment newInstance() {
        return new SearchFragment();
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }




    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        long idUser = ((User)l.getItemAtPosition(position)).id;
        EventBus.getDefault().post( new TweetMessageEvent( idUser ) );
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.search_fragment, container, false);

        EditText textSearch = view.findViewById( R.id.text_search );
        SearchUserAdapter searchUserAdapter = new SearchUserAdapter(getContext(),R.layout.search_row);
        setListAdapter( searchUserAdapter );


        view.findViewById( R.id.action_search ).setOnClickListener( v->{
            if (!TextUtils.isEmpty(textSearch.getText())) {

                MyTwitterApiClient myTwitterApiClient = (MyTwitterApiClient) TwitterCore.getInstance().getApiClient();
                Call<List<User>> users = myTwitterApiClient.getCustomService().findUser(textSearch.getText().toString(), 10);
                users.enqueue(new Callback<List<User>>() {
                    @Override
                    public void success(Result<List<User>> result) {
                        ((SearchUserAdapter)getListView().getAdapter()).setData( result.data );
                        ((SearchUserAdapter)getListView().getAdapter()).notifyDataSetChanged();
                    }
                    @Override
                    public void failure(TwitterException exception) {
                        Toast.makeText(getContext(), R.string.search_user_failure, Toast.LENGTH_SHORT ).show();
                    }
                });
            }

        });

        return view;
    }

}