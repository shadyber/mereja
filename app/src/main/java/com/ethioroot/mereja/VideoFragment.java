package com.ethioroot.mereja;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TabHost;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class  VideoFragment extends Fragment {

    TabHost tabHost;


    String url ="https://www.mereja360.com/api/getmusic.php";

    String urldrama ="https://www.mereja360.com/api/getdrama.php";

    String urlmovie ="https://www.mereja360.com/api/getmovies.php";

    String urltv ="https://www.mereja360.com/api/gettv.php";

    String urlmisc ="https://www.mereja360.com/api/getmisc.php";

    String urllive ="https://www.mereja360.com/api/getlive.php";

    private RecyclerView recyclerView,recyclerViewdrama,recyclerViewtv,recyclerViewmovies,recyclerViewmisc,recyclerViewlive;
    private AlbumsAdapter adapter,dramaAdapter,filmAdapter,tvAdapter,miscAdapter,liveAdapter;
    private List<Album> albumList,dramaList,filmList,tvList,miscList,liveList;
    public Animation animBounce;





//----------------- Drama Recicle View
    private void getofflineDramaData() {
        String stringfile="";
        try {
            FileManager fileManager = new FileManager();
            stringfile = fileManager.readFromFile("drama.dat", getContext());
        }catch (Exception ex){
            Log.e("File Exception : ",ex.getMessage());
            return;
        }


        JSONArray response = null;
        try {
            response = new JSONArray(stringfile);
        } catch (JSONException e) {
            Log.e("Json Exception : ",e.getMessage());
            return;
        }

        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                Album movie = new Album();
                movie.setName(jsonObject.getString("title"));
                movie.setGeners(jsonObject.getString("category"));
                movie.setThumbnail(jsonObject.getString("thumb_small"));
                movie.setShortDescription(jsonObject.getString("detail"));
                movie.setLongDescription(jsonObject.getString("detail"));


                movie.setVideo(jsonObject.getString("vidId"));
                dramaList.add(movie);
                dramaAdapter.notifyDataSetChanged();


            } catch (JSONException e) {
                Log.e("Json Exception : ", e.getMessage());
return;

            }
            dramaAdapter.notifyDataSetChanged();

        }


    }
    private void getDramaData() {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urldrama, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

            try {
                FileManager fileManager = new FileManager();
                fileManager.writeToFile("drama.dat", String.valueOf(response), getContext());
            }catch (Exception fx){
                Log.e("File Exception : ",fx.getMessage());
            return;
            }

            for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        Album movie = new Album();
                        movie.setName(jsonObject.getString("title"));
                        movie.setGeners(jsonObject.getString("category"));
                        movie.setThumbnail(jsonObject.getString("thumb_small"));
                        movie.setShortDescription(jsonObject.getString("detail"));
                        movie.setLongDescription(jsonObject.getString("detail"));


                        movie.setVideo(jsonObject.getString("vidId"));
                        dramaList.add(movie);
                        dramaAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        Log.e("Json Exception : ", e.getMessage());
                        return;


                    }
                }
                dramaAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getofflineDramaData();
                Log.e("Volley Error : ", error.toString());



            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache();
        requestQueue.add(jsonArrayRequest);
    }

//---------------------Music Recicle View


    private void getofflineMusicData() {

        String stringfile="";
        try {

            FileManager fileManager = new FileManager();
            stringfile = fileManager.readFromFile("music.dat", getContext());

        }catch (Exception fx){

            Log.e("File Exception : ",fx.getMessage());
            return;
        }
         JSONArray response = null;
        try {
            response = new JSONArray(stringfile);
        } catch (JSONException e) {
            Log.e("Json Exception",e.getMessage());
            return;
        }

        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                Album movie = new Album();
                movie.setName(jsonObject.getString("title"));
                movie.setGeners(jsonObject.getString("category"));
                movie.setThumbnail(jsonObject.getString("thumb_small"));
                movie.setShortDescription(jsonObject.getString("detail"));
                movie.setLongDescription(jsonObject.getString("detail"));


                movie.setVideo(jsonObject.getString("vidId"));
                albumList.add(movie);
                adapter.notifyDataSetChanged();

            } catch (JSONException e) {
                Log.e("Json Exception : ", e.getMessage());

            }
            adapter.notifyDataSetChanged();


        }


    }
    private void getMusicData() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("Response : ", String.valueOf(response));
              try {
                  FileManager fileManager = new FileManager();
                  fileManager.writeToFile("music.dat", String.valueOf(response), getContext());
              }catch (Exception ex){
                  Log.e("file Error : ",ex.getMessage());
              }


              for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        Album movie = new Album();
                        movie.setName(jsonObject.getString("title"));
                        movie.setGeners(jsonObject.getString("category"));
                        movie.setThumbnail(jsonObject.getString("thumb_small"));
                        movie.setShortDescription(jsonObject.getString("detail"));
                        movie.setLongDescription(jsonObject.getString("detail"));


                        movie.setVideo(jsonObject.getString("vidId"));
                        albumList.add(movie);

                    } catch (JSONException e) {
                        Log.e("Json Exception : ", e.getMessage());

                        adapter.notifyDataSetChanged();

                    }
                }
                adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getofflineMusicData();
                Log.e("Volley Error : ", error.toString());



            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache();
        requestQueue.add(jsonArrayRequest);
    }



    //----------------- Drama Recicle View
    private void getofflineFilmData() {
        String stringfile="";
        try {
            FileManager fileManager = new FileManager();
            stringfile = fileManager.readFromFile("film.dat", getContext());
     }catch (Exception fx){
            Log.e("File Exception : ",fx.getMessage());
            return;
        }
         JSONArray response = null;
        try {
            response = new JSONArray(stringfile);
        } catch (JSONException e) {
            Log.e("Json Exception : ",e.getMessage());
            return;
        }

        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                Album movie = new Album();
                movie.setName(jsonObject.getString("title"));
                movie.setGeners(jsonObject.getString("category"));
                movie.setThumbnail(jsonObject.getString("thumb_small"));
                movie.setShortDescription(jsonObject.getString("detail"));
                movie.setLongDescription(jsonObject.getString("detail"));


                movie.setVideo(jsonObject.getString("vidId"));
               filmList.add(movie);
             } catch (JSONException e) {
                Log.e("Json Exception : ", e.getMessage());

                filmAdapter.notifyDataSetChanged();

            }
            filmAdapter.notifyDataSetChanged();


        }


    }
    private void getFilmData() {


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlmovie, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("Response : ", String.valueOf(response));
              try {
                  FileManager fileManager = new FileManager();
                  fileManager.writeToFile("film.dat", String.valueOf(response), getContext());

              }catch (Exception fx){

                  Log.e("File Exception : ",fx.getMessage());
              }
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        Album movie = new Album();
                        movie.setName(jsonObject.getString("title"));
                        movie.setGeners(jsonObject.getString("category"));
                        movie.setThumbnail(jsonObject.getString("thumb_small"));
                        movie.setShortDescription(jsonObject.getString("detail"));
                        movie.setLongDescription(jsonObject.getString("detail"));


                        movie.setVideo(jsonObject.getString("vidId"));
                        filmList.add(movie);
                        filmAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        Log.e("Json Exception : ", e.getMessage());
                        return;
     }
                }
                dramaAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getofflineFilmData();
                Log.e("Volley Error : ", error.toString());

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache();
        requestQueue.add(jsonArrayRequest);
    }

//---------------------Music Recicle View

    private void getofflinetvData() throws JSONException {

        String stringfile="";
        try {
            FileManager fileManager = new FileManager();
            stringfile = fileManager.readFromFile("tv.dat", getContext());
        }catch (Exception fx){

            Log.e("File Exception : ",fx.getMessage());
            return;
        }


        JSONArray response = new JSONArray(stringfile);

        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                Album movie = new Album();
                movie.setName(jsonObject.getString("title"));
                movie.setGeners(jsonObject.getString("category"));
                movie.setThumbnail(jsonObject.getString("thumb_small"));
                movie.setShortDescription(jsonObject.getString("detail"));
                movie.setLongDescription(jsonObject.getString("detail"));


                movie.setVideo(jsonObject.getString("vidId"));
                tvList.add(movie);
                tvAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                Log.e("Json Exception : ", e.getMessage());
            return;

            }
            tvAdapter.notifyDataSetChanged();


        }


    }
    private void gettvData() {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urltv, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("Response : ", String.valueOf(response));
              try {
                  FileManager fileManager = new FileManager();
                  fileManager.writeToFile("tv.dat", String.valueOf(response), getContext());
              }catch (Exception ex){
                  Log.e("File Exception : ",ex.getMessage());
              }
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        Album movie = new Album();
                        movie.setName(jsonObject.getString("title"));
                        movie.setGeners(jsonObject.getString("category"));
                        movie.setThumbnail(jsonObject.getString("thumb_small"));
                        movie.setShortDescription(jsonObject.getString("detail"));
                        movie.setLongDescription(jsonObject.getString("detail"));


                        movie.setVideo(jsonObject.getString("vidId"));
                        tvList.add(movie);
                        tvAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        Log.e("Json Exception : ", e.getMessage());

return;
                    }
                }
                tvAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    getofflinetvData();
                } catch (JSONException e) {
                   return;
                }
                Log.e("Volley Error : ", error.toString());


            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache();
        requestQueue.add(jsonArrayRequest);
    }

//---------------------Music Recicle View

    private void getofflinemiscData() {

        String stringfile="";
        try {

            FileManager fileManager = new FileManager();
            stringfile = fileManager.readFromFile("misc.dat", getContext());

        }catch (Exception fx){
            Log.e("File Exception : ",fx.getMessage());
            return;
        }
         JSONArray response = null;
        try {
            response = new JSONArray(stringfile);
        } catch (JSONException e) {
            Log.e("Json Exception : ",e.getMessage());
            return;
        }

        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                Album movie = new Album();
                movie.setName(jsonObject.getString("title"));
                movie.setGeners(jsonObject.getString("category"));
                movie.setThumbnail(jsonObject.getString("thumb_small"));
                movie.setShortDescription(jsonObject.getString("detail"));
                movie.setLongDescription(jsonObject.getString("detail"));


                movie.setVideo(jsonObject.getString("vidId"));
                miscList.add(movie);
              } catch (JSONException e) {
                Log.e("Json Exception : ", e.getMessage());
                miscAdapter.notifyDataSetChanged();

            }
            miscAdapter.notifyDataSetChanged();


        }


    }
    private void getmiscData() {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlmisc, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    FileManager fileManager = new FileManager();
                    fileManager.writeToFile("misc.dat", String.valueOf(response), getContext());
                }catch (Exception ex){
                    Log.e("File Error : ",ex.getMessage());
                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        Album movie = new Album();
                        movie.setName(jsonObject.getString("title"));
                        movie.setGeners(jsonObject.getString("category"));
                        movie.setThumbnail(jsonObject.getString("thumb_small"));
                        movie.setShortDescription(jsonObject.getString("detail"));
                        movie.setLongDescription(jsonObject.getString("detail"));


                        movie.setVideo(jsonObject.getString("vidId"));
                        miscList.add(movie);
                        miscAdapter.notifyDataSetChanged();


                    } catch (JSONException e) {
                        Log.e("Json Exception : ", e.getMessage());

                       return;
                    }
                }
                miscAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getofflinemiscData();
                Log.e("Volley Error : ", error.toString());




            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache();
        requestQueue.add(jsonArrayRequest);
    }

//---------------------Music Recicle View

    private void getofflineliveData()  {
        String stringfile ="";
        try {
           FileManager fileManager = new FileManager();
          stringfile = fileManager.readFromFile("live.dat", getContext());
       }catch (Exception fx){

Log.e("FIle Exception :",fx.getMessage());
return;
       }

        JSONArray response = null;
        try {
            response = new JSONArray(stringfile);
        } catch (JSONException e) {
            Log.e("Json Exception : ",e.getMessage());
            return;
        }

        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                Album movie = new Album();
                movie.setName(jsonObject.getString("title"));
                movie.setGeners(jsonObject.getString("category"));
                movie.setThumbnail(jsonObject.getString("thumb_small"));
                movie.setShortDescription(jsonObject.getString("detail"));
                movie.setLongDescription(jsonObject.getString("detail"));


                movie.setVideo(jsonObject.getString("vidId"));
                liveList.add(movie);
                liveAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                Log.e("Json Exception : ", e.getMessage());

            return;
            }
            liveAdapter.notifyDataSetChanged();


        }


    }
    private void getliveData() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urllive, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("Response : ", String.valueOf(response));
                try {
                    FileManager fileManager = new FileManager();
                    fileManager.writeToFile("live.dat", String.valueOf(response), getContext());
                }catch (Exception fx){
                    Log.e("File Exception : ",fx.getMessage());
                }
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        Album movie = new Album();
                        movie.setName(jsonObject.getString("title"));
                        movie.setGeners(jsonObject.getString("category"));
                        movie.setThumbnail(jsonObject.getString("thumb_small"));
                        movie.setShortDescription(jsonObject.getString("detail"));
                        movie.setLongDescription(jsonObject.getString("detail"));


                        movie.setVideo(jsonObject.getString("vidId"));
                        liveList.add(movie);
                        liveAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        Log.e("Json Exception : ", e.getMessage());

                        return;

                    }
                }
                liveAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getofflineliveData();
                Log.e("Volley Error : ", error.toString());
 }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache();
        requestQueue.add(jsonArrayRequest);
    }

//---------------------Music Recicle View



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.videofragment, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
try {
    TabHost host = (TabHost) view.findViewById(R.id.tabHost);
    host.setup();

    //Tab 1
    TabHost.TabSpec spec = host.newTabSpec("Music");
    spec.setContent(R.id.tab1);

    spec.setIndicator("Music");
    host.addTab(spec);

    //Tab 2
    spec = host.newTabSpec("Drama");
    spec.setContent(R.id.tab2);
    spec.setIndicator("Drama");
    host.addTab(spec);

    //Tab 3
    spec = host.newTabSpec("Movies");
    spec.setContent(R.id.tab3);
    spec.setIndicator("Movies");
    host.addTab(spec);


    //Tab 4
    spec = host.newTabSpec("TV-Show");
    spec.setContent(R.id.tab4);
    spec.setIndicator("TV-Show");
    host.addTab(spec);


    //Tab 5
    spec = host.newTabSpec("MISC");
    spec.setContent(R.id.tab5);
    spec.setIndicator("MISC");
    host.addTab(spec);


    //Tab 6
    spec = host.newTabSpec("LIVE");
    spec.setContent(R.id.tab6);
    spec.setIndicator("LIVE");
    host.addTab(spec);


    //____________________________________________


    recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

    albumList = new ArrayList<>();
    adapter = new AlbumsAdapter(getContext(), albumList);

    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
    recyclerView.setLayoutManager(mLayoutManager);
    // recyclerView.addItemDecoration(new MainActivity.GridSpacingItemDecoration(2, dpToPx(10), true));
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.setAdapter(adapter);

    getMusicData();

//_______________________________________________________________


    recyclerViewdrama = (RecyclerView) view.findViewById(R.id.recycler_view2);

    dramaList = new ArrayList<>();
    dramaAdapter = new AlbumsAdapter(getContext(), dramaList);

    mLayoutManager = new GridLayoutManager(getContext(), 2);
    recyclerViewdrama.setLayoutManager(mLayoutManager);
    // recyclerView.addItemDecoration(new MainActivity.GridSpacingItemDecoration(2, dpToPx(10), true));
    recyclerViewdrama.setItemAnimator(new DefaultItemAnimator());
    recyclerViewdrama.setAdapter(dramaAdapter);
getofflineDramaData();
    getDramaData();


//_______________________________________________________________


    recyclerViewmovies = (RecyclerView) view.findViewById(R.id.recycler_view3);

    filmList = new ArrayList<>();
    filmAdapter = new AlbumsAdapter(getContext(), filmList);

    mLayoutManager = new GridLayoutManager(getContext(), 2);
    recyclerViewmovies.setLayoutManager(mLayoutManager);
    // recyclerView.addItemDecoration(new MainActivity.GridSpacingItemDecoration(2, dpToPx(10), true));
    recyclerViewmovies.setItemAnimator(new DefaultItemAnimator());
    recyclerViewmovies.setAdapter(filmAdapter);

    getFilmData();


//_______________________________________________________________


    recyclerViewtv = (RecyclerView) view.findViewById(R.id.recycler_view4);

    tvList = new ArrayList<>();
    tvAdapter = new AlbumsAdapter(getContext(), tvList);

    mLayoutManager = new GridLayoutManager(getContext(), 2);
    recyclerViewtv.setLayoutManager(mLayoutManager);
    // recyclerView.addItemDecoration(new MainActivity.GridSpacingItemDecoration(2, dpToPx(10), true));
    recyclerViewtv.setItemAnimator(new DefaultItemAnimator());
    recyclerViewtv.setAdapter(tvAdapter);
    try {
        getofflinetvData();
    } catch (JSONException e) {

        Log.e("JsonException : ", e.getMessage());

    }
    gettvData();


//_______________________________________________________________


    recyclerViewmisc = (RecyclerView) view.findViewById(R.id.recycler_view5);

    miscList = new ArrayList<>();
    miscAdapter = new AlbumsAdapter(getContext(), miscList);

    mLayoutManager = new GridLayoutManager(getContext(), 2);
    recyclerViewmisc.setLayoutManager(mLayoutManager);
    // recyclerView.addItemDecoration(new MainActivity.GridSpacingItemDecoration(2, dpToPx(10), true));
    recyclerViewmisc.setItemAnimator(new DefaultItemAnimator());
    recyclerViewmisc.setAdapter(miscAdapter);
getofflineMusicData();
    getmiscData();


//_______________________________________________________________


    recyclerViewlive = (RecyclerView) view.findViewById(R.id.recycler_view6);

    liveList = new ArrayList<>();
    liveAdapter = new AlbumsAdapter(getContext(), liveList);

    mLayoutManager = new GridLayoutManager(getContext(), 2);
    recyclerViewlive.setLayoutManager(mLayoutManager);
    // recyclerView.addItemDecoration(new MainActivity.GridSpacingItemDecoration(2, dpToPx(10), true));
    recyclerViewlive.setItemAnimator(new DefaultItemAnimator());
    recyclerViewlive.setAdapter(liveAdapter);
getofflineliveData();
    getliveData();


}catch (Exception ex){
    Log.e("General Exception : ",ex.getMessage());
}

    }


}
