package us.jasonh.spotifystreamer;

/**
 * Created by jason on 7/4/15.
 */
public class ArtistItem {
    private String mImageUrl;
    private String mName;

    public ArtistItem(String imageUrl, String name) {
        mImageUrl = imageUrl;
        mName = name;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getName() {
        return mName;
    }
}
