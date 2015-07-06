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

public class TrackAdapter extends ArrayAdapter<TrackItem> {

    public TrackAdapter(Activity context, List<TrackItem> trackItems) {
        super(context, 0, trackItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TrackItem trackItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_track, parent, false);
        }

        String imageUrl = trackItem.getImageUrl();
        if (imageUrl != null && imageUrl.length() > 0) {
            ImageView imageView = (ImageView) convertView.findViewById(R.id.list_item_track_image);
            Picasso.with(getContext())
                    .load(imageUrl)
                    .into(imageView);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.list_item_track_name);
        textView.setText(trackItem.getTrackName());

        TextView textView2 = (TextView) convertView.findViewById(R.id.list_item_album);
        textView2.setText(trackItem.getAlbum());

        return convertView;
    }
}
