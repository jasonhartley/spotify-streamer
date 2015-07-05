package us.jasonh.spotifystreamer;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;

/**
 * A placeholder fragment containing a simple view.
 */
public class SearchActivityFragment extends Fragment {

    private View mRootView;
    private ListView mListView;

    public SearchActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_search, container, false);
        mListView = (ListView) mRootView.findViewById(R.id.listview_artists);

        final EditText editText = (EditText) mRootView.findViewById(R.id.edit_text);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event == null || event.getAction() != KeyEvent.ACTION_DOWN) {
                    return false;
                }
                Log.d("jch", "v: " + v + ", actionId: " + actionId + ", event: " + event.getAction());
                if (actionId != EditorInfo.IME_ACTION_NEXT && actionId != EditorInfo.IME_NULL) {
                    return false;
                }
                SearchSpotifyTask task = new SearchSpotifyTask(editText.getText().toString());
                task.execute();

                return true;
            }
        });

        return mRootView;
    }

    public class SearchSpotifyTask extends AsyncTask<Void, String, Void> {
        private String mSearchTerm;
        private List<ArtistItem> mArtists;
        private ArtistAdapter mArtistAdapter;

        public SearchSpotifyTask(String searchTerm) {
            super();
            mSearchTerm = searchTerm;
        }

        @Override
        protected Void doInBackground(Void... unused) {
            SpotifyApi api = new SpotifyApi();
            SpotifyService service = api.getService();

            mArtists = new ArrayList<>();
            ArtistsPager results = service.searchArtists(mSearchTerm);
            List<Artist> artists = results.artists.items;
            for (int i = 0; i < artists.size(); i++) {
                Artist artist = artists.get(i);
                String imageUrl = "https://placeholdit.imgix.net/~text?txtsize=6&txt=50%C3%9750&w=50&h=50";
                if (artist.images.size() > 0) {
                    imageUrl = artist.images.get(0).url;
                }
                ArtistItem artistItem = new ArtistItem(imageUrl, artist.name);
                mArtists.add(artistItem);
            }
            mArtistAdapter = new ArtistAdapter(getActivity(), mArtists);

            publishProgress();

            return null;
        }

        @Override
        protected void onProgressUpdate(String... item) {
            mListView.setAdapter(mArtistAdapter);
        }
    }


}
