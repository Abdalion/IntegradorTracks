package digitalhouse.android.basededatosywebbaseintegrado.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by andres on 22/06/16.
 */
public class TrackContainer {

    @SerializedName("albums")
    private List<Track> trackList;

    public List<Track> getResults() {
        return trackList;
    }

    public void setTrackList(List<Track> trackList) {
        this.trackList = trackList;
    }
}
