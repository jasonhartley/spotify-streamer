package us.jasonh.spotifystreamer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;

public class TopTracksFragment extends Fragment {

    private ListView mListView;

    public TopTracksFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_top_tracks, container, false);
        mListView = (ListView) rootView.findViewById(R.id.listview_tracks);

        String artistId = getArguments().getString(SearchActivity.EXTRA_MESSAGE, "default");
        Log.d("jch", "artistId: " + artistId);

        SearchSpotifyTask task = new SearchSpotifyTask(artistId);
        task.execute();

        return rootView;
    }

    public class SearchSpotifyTask extends AsyncTask<Void, String, Void> {
        private String mArtistId;
        private List<TrackItem> mTracks;
        private TrackAdapter mTrackAdapter;

        public SearchSpotifyTask(String artistId) {
            super();
            mArtistId = artistId;
        }

        @Override
        protected Void doInBackground(Void... unused) {
            SpotifyApi api = new SpotifyApi();
            SpotifyService service = api.getService();

            mTracks = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            map.put("country", Locale.getDefault().getCountry());
            Tracks results = service.getArtistTopTrack(mArtistId, map);
            List<Track> tracks = results.tracks;
            for (int i = 0; i < tracks.size() && i < 10; i++) {
                Track track = tracks.get(i);
                String imageUrl = "";
                if (track.album.images.size() > 0) {
                    imageUrl = track.album.images.get(0).url;
                }
                TrackItem trackItem = new TrackItem(track.name, track.album.name, imageUrl);
                mTracks.add(trackItem);
            }
            mTrackAdapter = new TrackAdapter(getActivity(), mTracks);

            publishProgress();

            return null;
        }

        @Override
        protected void onProgressUpdate(String... item) {
            mListView.setAdapter(mTrackAdapter);
        }
    }

}
