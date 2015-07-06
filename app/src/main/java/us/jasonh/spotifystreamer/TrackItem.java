package us.jasonh.spotifystreamer;

public class TrackItem {
    private String mTrackName, mAlbum, mImageUrl;

    public TrackItem(String trackName, String album, String imageUrl) {
        mTrackName = trackName;
        mAlbum = album;
        mImageUrl = imageUrl;
    }

    public String getTrackName() {
        return mTrackName;
    }

    public String getAlbum() {
        return mAlbum;
    }

    public String getImageUrl() {
        return mImageUrl;
    }
}
