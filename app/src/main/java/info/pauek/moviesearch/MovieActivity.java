package info.pauek.moviesearch;

import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MovieActivity extends AppCompatActivity {

    private Movie movie; // MovieActivity conté una referencia a Movie (no conté Movie)
    private Gson gson;
    private TextView titleView;
    private TextView yearView;
    private TextView ratedView;
    private TextView runtimeView;
    private TextView genreView;
    private TextView directorView;
    private TextView writerView;
    private TextView actorsView;
    private TextView plotView;
    private ImageView posterView;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        gson = new Gson();

        // LLIBRERIA VOLLEY: per fer una petició a una web
        queue = Volley.newRequestQueue(this);

        // findViewById és una crida costosa (és molt lent)
        titleView = findViewById(R.id.titleview);
        yearView = findViewById(R.id.yearview);
        ratedView = findViewById(R.id.ratedview);
        runtimeView = findViewById(R.id.runtimeview);
        genreView = findViewById(R.id.genreview);
        directorView = findViewById(R.id.directorview);
        writerView = findViewById(R.id.writerview);
        actorsView = findViewById(R.id.actorsview);
        plotView = findViewById(R.id.plotview);
        posterView = findViewById(R.id.posterview);

        // URL: i=blabla (paràmetre 1) & i=blabla (paràmetre 2)
        StringRequest request = new StringRequest(Request.Method.GET, "http://www.omdbapi.com/?i=tt3896198&apikey=b746a0d5",
                new Response.Listener<String>() { // Objecte 1

            // Classe que hereda de Response.Listener
            @Override // Sobrecarrega el mètode onResponse, que ja existia
            public void onResponse(String response) {

                // La resposta de la web és un json
                movie = gson.fromJson(response, Movie.class);
                updateMovie();

                // LLIBRERIA GLIDE: per carregar la imatge
                Glide.with(MovieActivity.this) // Retorna un objecte
                        .load(movie.getPoster()) // Crido load en l'objecte
                        .into(posterView);
            }
        }, new Response.ErrorListener() { // Objecte 2

            // Classe que hereda de Response.ErrorListener
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MovieActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }

    private void updateMovie() {

        titleView.setText(movie.getTitle());
        yearView.setText(movie.getYear());
        ratedView.setText(movie.getRated());
        runtimeView.setText(movie.getRuntime());
        genreView.setText(movie.getGenre());
        directorView.setText(movie.getDirector());
        writerView.setText(movie.getWriter());
        actorsView.setText(movie.getActors().replace(", ", "\n"));
        plotView.setText(movie.getPlot());
    }
}
