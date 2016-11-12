package digitalhouse.android.basededatosywebbaseintegrado.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import digitalhouse.android.basededatosywebbaseintegrado.R;
import digitalhouse.android.basededatosywebbaseintegrado.model.Track;

/**
 * Created by andres on 22/06/16.
 */
public class TrackAdapter extends RecyclerView.Adapter{

    private List<Track> trackList;
    private Context context;

    public TrackAdapter(Context context) {

        this.context = context;
        this.trackList = new ArrayList<>();
    }

    public void setTrackList(List<Track> trackList) {
        this.trackList = trackList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_detalle, parent, false);
        TrackHolder unItemHolder = new TrackHolder(itemView);
        return unItemHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Track track = trackList.get(position);
        TrackHolder unItemHolder = (TrackHolder) holder;
        unItemHolder.bindProduct(track, context);
    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }

    private static class TrackHolder extends RecyclerView.ViewHolder{

        private TextView textViewTitulo;
        private ImageView imageThumbnail;

        public TrackHolder(View itemView) {
            super(itemView);
            textViewTitulo = (TextView) itemView.findViewById(R.id.textViewTitulo);
            imageThumbnail = (ImageView) itemView.findViewById(R.id.imageViewThumbnail);
        }

        public void bindProduct(Track track, Context context){
            textViewTitulo.setText(track.getTitle());
            Glide
             .with(context)
             .load(track.getThumbnailUrl())
             .error(R.drawable.loading)
             .into(imageThumbnail);
        }
    }
}
