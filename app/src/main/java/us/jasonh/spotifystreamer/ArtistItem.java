package us.jasonh.spotifystreamer;

public class ArtistItem {
    private String mId;
    private String mImageUrl;
    private String mName;

    public ArtistItem(String id, String imageUrl, String name) {
        mId = id;
        mImageUrl = imageUrl;
        mName = name;
    }

    public String getId() {
        return mId;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getName() {
        return mName;
    }
}
