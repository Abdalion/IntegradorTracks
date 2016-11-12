package digitalhouse.android.integradortracks.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import digitalhouse.android.integradortracks.R;
import digitalhouse.android.integradortracks.controller.TrackController;
import digitalhouse.android.integradortracks.model.Track;
import digitalhouse.android.integradortracks.util.ResultListener;

public class MainActivity extends AppCompatActivity {

    //Declaro todas estas variables como globales ya que las voy a usar a lo largo de la clase.
    private RecyclerView recyclerView;
    private TrackAdapter adapterPost;
    private TrackController postController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Seteo el contexto y obtengo el recyclerView y el SwipeRefreshLayout
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        //Seteo el adapter y los elementos del recyclerView
        adapterPost = new TrackAdapter(getApplicationContext());
        recyclerView.setAdapter(adapterPost);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);


        //Instancio el TrackController que me va a devolver todos los posts
        postController = new TrackController();

        //Este metodo va a pedirle al postcontroller que busque los posts y tambien actualizar el adaptador del recyclerView
        update();
    }

    //Aca es donde hacemos el pedido de posts y actualizamos el recyclerView
    private void update() {

        postController.dameLosPostDeAlgunLado(new ResultListener<List<Track>>() {
            @Override
            public void finish(List<Track> resultado) {

                adapterPost.setTrackList(resultado);

                adapterPost.notifyDataSetChanged();

            }
        },MainActivity.this);



    }}










