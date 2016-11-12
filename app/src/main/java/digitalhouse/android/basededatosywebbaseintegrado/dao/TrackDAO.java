package digitalhouse.android.basededatosywebbaseintegrado.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import digitalhouse.android.basededatosywebbaseintegrado.model.Track;
import digitalhouse.android.basededatosywebbaseintegrado.model.TrackContainer;
import digitalhouse.android.basededatosywebbaseintegrado.util.HTTPConnectionManager;
import digitalhouse.android.basededatosywebbaseintegrado.util.ResultListener;


/**
 * Created by digitalhouse on 6/06/16.
 */
public class TrackDAO extends SQLiteOpenHelper {

    private static final String URL_A_PARSEAR = "https://api.myjson.com/bins/25hip";

    //CONSTANTES PARA LOS NOMBRES DE LA BD Y LOS CAMPOS
    private static final String DATABASENAME = "TrackDB";
    private static final Integer DATABASEVERSION = 1;

    //TABLA PERSONA CON SUS CAMPOS
    private static final String TABLEPOST = "Track";
    private static final String ID = "ID";
    private static final String TITLE = "title";
    private static final String URL = "Url";

    //El contexto lo necesitamos para poder crear una BD.
    private Context context;

    //Constructor que permite crear la BD
    public TrackDAO(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
        this.context = context;
    }

    //CREACION DE LA BASE DE DATOS POR PRIMERA VEZ
    @Override
    public void onCreate(SQLiteDatabase db) {

        //Creo la tabla que contendrá mi base de datos
        String createTable = "CREATE TABLE " + TABLEPOST + "("
                + ID + " INTEGER PRIMARY KEY, "
                + TITLE + " TEXT, "
                + URL + " TEXT " + ")";

        db.execSQL(createTable);
    }

    //SE EJECUTA CUANDO SE MODIFICA ALGO EN LA ESTRUCTURA DE LA TABLA
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    //METODO QUE ME PERMITE CHEQUEAR SI EXISTIA UN POST EN MI BASE DE DATOS
    private Boolean checkIfExist(String aPostID) {

        SQLiteDatabase database = getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLEPOST
                + " WHERE " + ID + "==" + aPostID;

        Cursor result = database.rawQuery(selectQuery, null);
        Integer count = result.getCount();

        Log.v("TrackDAO", "Track " + aPostID + " ya esta en la base");

        database.close();

        return count > 0;
    }

    private void addAllPosts(List<Track> trackList){
        if(trackList != null) {
            for (Track unTrack : trackList) {
                if (!checkIfExist(unTrack.getId())) {
                    addPost(unTrack);
                }
            }
        }
    }

    //METODO QUE ME PERMITE AGREGAR UN POST A MI MI BD
    public void addPost(Track post) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues row = new ContentValues();

        //Obtengo los datos y los cargo en el row
        row.put(ID, post.getId());
        row.put(TITLE, post.getTitle());
        row.put(URL, post.getThumbnailUrl());

        database.insert(TABLEPOST, null, row);

        database.close();
    }

    public List<Track> getAllPostsFromDatabase() {

        SQLiteDatabase database = getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLEPOST;
        Cursor cursor = database.rawQuery(selectQuery, null);

        List<Track> postList = new ArrayList<>();
        while(cursor.moveToNext()){

            //TOMO LOS DATOS DE CADA POST
            Track track = new Track();

            track.setId(cursor.getString(cursor.getColumnIndex(ID)));
            track.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
            track.setThumbnailUrl(cursor.getString(cursor.getColumnIndex(URL)));

            //AGREGO UN POST A LA LISTA
            postList.add(track);
        }

        return postList;
    }


    //ESTE METODO CHEQUEA SI TIENE CONEXION DE INTERNET, EN CASO AFIRMATIVO GENERAMOS EL ASYNC TASK Y PEDIMOS EL LISTADO A LA
    //URL, EN CASO NEGATIVO PEDIMOS EL CONTENIDO A LA BASE DE DATOS.
    public void getAllPostsFromWEB(final ResultListener<List<Track>> listener) {

        RetrievePostTask retrievePostTask = new RetrievePostTask(listener);
        retrievePostTask.execute();
    }


    //ESTA CLASE ES UNA CLASE QUE ME PERMITE GENERAR UNA TAREA ASINCRONICA. ES DECIR, ESTA TAREA SE EJECUTARA
// INDEPENDIENTEMENTE DE LO QUE ESTE HACIENDO COMO ACTIVIDAD PRINCIPAL
    class RetrievePostTask extends AsyncTask<String, Void, List<Track>> {

        private ResultListener<List<Track>> listener;

        public RetrievePostTask(ResultListener<List<Track>> listener) {
            this.listener = listener;
        }

        //Esto método se ejecuta mientras sigue corriendo la tarea principal. Aqui lo que haremos es conectarnos
        // al servicio y descargar la lista.
        @Override
        protected List<Track> doInBackground(String... params) {

            HTTPConnectionManager connectionManager = new HTTPConnectionManager();
            String input = null;

            try {
                input = connectionManager.getRequestString(URL_A_PARSEAR);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Gson gson = new Gson();
            TrackContainer trackContainer = gson.fromJson(input, TrackContainer.class);

            addAllPosts(trackContainer.getResults());

            return trackContainer.getResults();
        }

        //Una vez terminado el procesamiento, le avisamos al listener que ya tiene la lista disponible.
        @Override
        protected void onPostExecute(List<Track> postList) {

            this.listener.finish(postList);
        }
    }
}




















