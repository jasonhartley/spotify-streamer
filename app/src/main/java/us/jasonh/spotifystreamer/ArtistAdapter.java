package us.jasonh.spotifystreamer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ArtistAdapter extends ArrayAdapter<ArtistItem> {

    public ArtistAdapter(Activity context, List<ArtistItem> artistItems) {
        super(context, 0, artistItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ArtistItem artistItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_artist, parent, false);
        }

        String imageUrl = artistItem.getImageUrl();
        if (imageUrl != null && imageUrl.length() > 0) {
            ImageView imageView = (ImageView) convertView.findViewById(R.id.list_item_artist_image);
            Picasso.with(getContext())
                    .load(imageUrl)
                    .into(imageView);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.list_item_artist_name);
        textView.setText(artistItem.getName());

        return convertView;
    }
}
