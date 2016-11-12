package digitalhouse.android.basededatosywebbaseintegrado.controller;

import android.content.Context;
import android.widget.Toast;

import java.util.List;

import digitalhouse.android.basededatosywebbaseintegrado.dao.TrackDAO;
import digitalhouse.android.basededatosywebbaseintegrado.model.Track;
import digitalhouse.android.basededatosywebbaseintegrado.util.HTTPConnectionManager;
import digitalhouse.android.basededatosywebbaseintegrado.util.ResultListener;

/**
 * Created by digitalhouse on 6/06/16.
 */
public class TrackController {

    private List<Track> dameLosPostsDeLaBaseDeDatos(Context context){
        TrackDAO postDAO = new TrackDAO(context);
        List<Track> postsList = postDAO.getAllPostsFromDatabase();
        return postsList;
    }

    private void dameLosPostsDeLaWeb(final ResultListener<List<Track>>listenerView, Context context){
        TrackDAO trackDAO = new TrackDAO(context);
        trackDAO.getAllPostsFromWEB(new ResultListener<List<Track>>() {
            @Override
            public void finish(List<Track> resultado) {
                listenerView.finish(resultado);
            }
        });


    }

    public void dameLosPostDeAlgunLado(ResultListener<List<Track>>listenerView, Context context){
        Boolean hayInternet = HTTPConnectionManager.isNetworkingOnline(context);

        if(hayInternet){
            dameLosPostsDeLaWeb(listenerView,context);
            Toast.makeText(context, "Descargando posts...", Toast.LENGTH_SHORT).show();
        }else{
            List<Track> postList = dameLosPostsDeLaBaseDeDatos(context);
            Toast.makeText(context, "Sin conección a internet", Toast.LENGTH_SHORT).show();
            listenerView.finish(postList);
        }

    }

}























































