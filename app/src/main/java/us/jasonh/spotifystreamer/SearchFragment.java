package us.jasonh.spotifystreamer;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;

public class SearchFragment extends Fragment {

    private ListView mListView;

    public SearchFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        mListView = (ListView) rootView.findViewById(R.id.listview_artists);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent intent = new Intent(getActivity(), TopTracksActivity.class);
                String artistId = ((ArtistItem) mListView.getAdapter().getItem(position)).getId();
                intent.putExtra(SearchActivity.EXTRA_MESSAGE, artistId);
                startActivity(intent);
            }
        });

        final EditText editText = (EditText) rootView.findViewById(R.id.edit_text);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event == null || event.getAction() != KeyEvent.ACTION_DOWN) {
                    return false;
                }
                if (actionId != EditorInfo.IME_ACTION_NEXT && actionId != EditorInfo.IME_NULL) {
                    return false;
                }
                SearchSpotifyTask task = new SearchSpotifyTask(editText.getText().toString());
                task.execute();

                return true;
            }
        });

        return rootView;
    }

    public class SearchSpotifyTask extends AsyncTask<Void, String, Void> {
        private String mSearchTerm;
        private ArtistAdapter mArtistAdapter;

        public SearchSpotifyTask(String searchTerm) {
            super();
            mSearchTerm = searchTerm;
        }

        @Override
        protected Void doInBackground(Void... unused) {
            SpotifyApi api = new SpotifyApi();
            SpotifyService service = api.getService();

            ArtistsPager results = service.searchArtists(mSearchTerm);
            List<Artist> artists = results.artists.items;
            List<ArtistItem> artistItems = new ArrayList<>();
            for (int i = 0; i < artists.size() && i < 10; i++) {
                Artist artist = artists.get(i);
                String imageUrl = "";
                if (artist.images.size() > 0) {
                    imageUrl = artist.images.get(0).url;
                }
                ArtistItem artistItem = new ArtistItem(artist.id, imageUrl, artist.name);
                artistItems.add(artistItem);
            }
            if (artists.size() == 0) {
                Util.showToast(getActivity(), "No results for \"" + mSearchTerm + "\"");
            }
            mArtistAdapter = new ArtistAdapter(getActivity(), artistItems);

            publishProgress();

            return null;
        }

        @Override
        protected void onProgressUpdate(String... item) {
            mListView.setAdapter(mArtistAdapter);
        }
    }
}
