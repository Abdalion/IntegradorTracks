package digitalhouse.android.integradortracks.model;

/**
 * Created by digitalhouse on 6/06/16.
 */
public class Track {

    //Solo incluimos aca las variables del JSON que queremos leer. Solo me interesa el ID, el titulo,el precio y la imagen.. Lo demás lo ignoramos.

    private String albumId;

    private String id;

    private String title;

    private String thumbnailUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }
}
