package com.ethioroot.mereja;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class HomeFragment extends Fragment {

    String url ="https://www.mereja360.com/api/getrecentvideos.php";


    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private List<Album> albumList;



    private void getofflineHomeData(){
        String stringfile="";
        try {
            FileManager fileManager = new FileManager();
            stringfile = fileManager.readFromFile("home.dat", getContext());
        }catch (Exception ex){
            Log.e("File error :",ex.getMessage());
            return;
        }

        JSONArray response = null;
        try {
            response = new JSONArray(stringfile);
        } catch (JSONException e) {
            Log.e("json Exception : ",e.getMessage());
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
return;

            }
            adapter.notifyDataSetChanged();


        }


    }
    private void getHomeData() {


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    Log.e("Response : ", String.valueOf(response));
                    FileManager fileManager = new FileManager();
                    fileManager.writeToFile("home.dat", String.valueOf(response), getContext());

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
                        albumList.add(movie);

                    } catch (JSONException e) {
                        Log.e("Json Exception : ", e.getMessage());
                        adapter.notifyDataSetChanged();
                            return;
                    }
                }
                adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getofflineHomeData();
                Log.e("Volley Error : ", error.toString());


            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache();
        requestQueue.add(jsonArrayRequest);
    }



    private AdView mAdView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.homefragment, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);

        mAdView = view.findViewById(R.id.adviewsmart1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

          mAdView = new AdView(getContext());
        mAdView.setAdSize(AdSize.SMART_BANNER);


        try {
            recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

            albumList = new ArrayList<>();
            adapter = new AlbumsAdapter(getContext(), albumList);

            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
            recyclerView.setLayoutManager(mLayoutManager);
            // recyclerView.addItemDecoration(new MainActivity.GridSpacingItemDecoration(2, dpToPx(10), true));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
            getofflineHomeData();

            getHomeData();
//_______________________________________________________________

        }catch (Exception ex){
            Log.e("Exception all :",ex.getMessage());
        }

        getHomeData();
    }

}