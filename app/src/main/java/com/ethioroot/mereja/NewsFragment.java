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

import android.support.v4.app.Fragment;
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

public class NewsFragment extends Fragment {
    TabHost tabHost;


    String url ="https://www.mereja360.com/api/getrecentnews.php";

    String urlethiopia ="https://www.mereja360.com/api/getnewsbycat.php?cat=ethio";

    String urlafrica ="https://www.mereja360.com/api/getnewsbycat.php?cat=africa";

    String urlworld ="https://www.mereja360.com/api/getnewsbycat.php?cat=world";


    private RecyclerView recyclerView,recyclerViewethio,recyclerViewafrica,recyclerViewworld;
    private NewsAdapter adapter,ethioAdapter,afroAdapter,worldAdapter;
    private List<News> newsList,ethioList,afroList,worldList;


    //----------------- Drama Recicle View
    private void getofflinenewsData() throws JSONException {
        FileManager fileManager = new FileManager();
        String stringfile = fileManager.readFromFile("newsrecent.dat", getContext());
        JSONArray response = new JSONArray(stringfile);

        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                News news = new News();
                news.setName(jsonObject.getString("title"));
                news.setGeners(jsonObject.getString("category"));
                news.setThumbnail(jsonObject.getString("thumb_small"));
                news.setShortDescription(jsonObject.getString("detail"));
                news.setLongDescription(jsonObject.getString("detail"));


                newsList.add(news);
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                Log.e("Json Exception : ", e.getMessage());
                e.printStackTrace();

            }
            adapter.notifyDataSetChanged();


        }


    }
    private void getNewsData() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
         JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("Response : ", String.valueOf(response));
                FileManager fileManager = new FileManager();
                fileManager.writeToFile("newsrecent.dat", String.valueOf(response), getContext());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        News news = new News();
                        news.setName(jsonObject.getString("title"));
                        news.setGeners(jsonObject.getString("category"));
                        news.setThumbnail(jsonObject.getString("thumb_small"));

                      //  movie.setThumbnail(jsonObject.getString("thumb_big"));
                        news.setShortDescription(jsonObject.getString("detail"));
                        news.setLongDescription(jsonObject.getString("detail"));

                        newsList.add(news);
                        adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        Log.e("Json Exception : ", e.getMessage());
                        e.printStackTrace();

                    }
                }
                adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    getofflinenewsData();
                } catch (JSONException e) {
                    //e.printStackTrace();
                }
                Log.e("Volley Error : ", error.toString());



            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache();
        requestQueue.add(jsonArrayRequest);
    }

    //----------------- Drama Recicle View
    private void getoffliethionenewsData() throws JSONException {
        FileManager fileManager = new FileManager();
        String stringfile = fileManager.readFromFile("ethionews.dat", getContext());
        JSONArray response = new JSONArray(stringfile);

        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                News news = new News();
                news.setName(jsonObject.getString("title"));
                news.setGeners(jsonObject.getString("category"));
                news.setThumbnail(jsonObject.getString("thumb_small"));
                news.setShortDescription(jsonObject.getString("detail"));
                news.setLongDescription(jsonObject.getString("detail"));


                ethioList.add(news);
                ethioAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                Log.e("Json Exception : ", e.getMessage());
                e.printStackTrace();

            }
            ethioAdapter.notifyDataSetChanged();


        }


    }
    private void getethioNewsData() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlethiopia, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("Response : ", String.valueOf(response));
                FileManager fileManager = new FileManager();
                fileManager.writeToFile("ethionews.dat", String.valueOf(response), getContext());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        News news = new News();
                        news.setName(jsonObject.getString("title"));
                        news.setGeners(jsonObject.getString("category"));
                        news.setThumbnail(jsonObject.getString("thumb_small"));

                        //  movie.setThumbnail(jsonObject.getString("thumb_big"));
                        news.setShortDescription(jsonObject.getString("detail"));
                        news.setLongDescription(jsonObject.getString("detail"));

                        ethioList.add(news);
                        ethioAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        Log.e("Json Exception : ", e.getMessage());
                        e.printStackTrace();

                    }
                }
                ethioAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    getoffliethionenewsData();
                } catch (JSONException e) {
                    //e.printStackTrace();
                }
                Log.e("Volley Error : ", error.toString());



            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache();
        requestQueue.add(jsonArrayRequest);
    }



    //----------------- Drama Recicle View
    private void getoffliafronenewsData() throws JSONException {
        FileManager fileManager = new FileManager();
        String stringfile = fileManager.readFromFile("afronews.dat", getContext());
        JSONArray response = new JSONArray(stringfile);

        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                News news = new News();
                news.setName(jsonObject.getString("title"));
                news.setGeners(jsonObject.getString("category"));
                news.setThumbnail(jsonObject.getString("thumb_small"));
                news.setShortDescription(jsonObject.getString("detail"));
                news.setLongDescription(jsonObject.getString("detail"));


                afroList.add(news);
                afroAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                Log.e("Json Exception : ", e.getMessage());
                e.printStackTrace();

            }
            afroAdapter.notifyDataSetChanged();


        }


    }
    private void getafroNewsData() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlafrica, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("Response : ", String.valueOf(response));
                FileManager fileManager = new FileManager();
                fileManager.writeToFile("afronews.dat", String.valueOf(response), getContext());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        News news = new News();
                        news.setName(jsonObject.getString("title"));
                        news.setGeners(jsonObject.getString("category"));
                        news.setThumbnail(jsonObject.getString("thumb_small"));

                        //  movie.setThumbnail(jsonObject.getString("thumb_big"));
                        news.setShortDescription(jsonObject.getString("detail"));
                        news.setLongDescription(jsonObject.getString("detail"));

                        afroList.add(news);
                        afroAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        Log.e("Json Exception : ", e.getMessage());
                        e.printStackTrace();

                    }
                }
                afroAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    getoffliafronenewsData();
                } catch (JSONException e) {
                    //e.printStackTrace();
                }
                Log.e("Volley Error : ", error.toString());



            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache();
        requestQueue.add(jsonArrayRequest);
    }


    //----------------- Drama Recicle View
    private void getofflineworldnenewsData() throws JSONException {
        FileManager fileManager = new FileManager();
        String stringfile = fileManager.readFromFile("worldnews.dat", getContext());
        JSONArray response = new JSONArray(stringfile);

        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                News news = new News();
                news.setName(jsonObject.getString("title"));
                news.setGeners(jsonObject.getString("category"));
                news.setThumbnail(jsonObject.getString("thumb_small"));
                news.setShortDescription(jsonObject.getString("detail"));
                news.setLongDescription(jsonObject.getString("detail"));


                worldList.add(news);
                worldAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                Log.e("Json Exception : ", e.getMessage());
                e.printStackTrace();

            }
            worldAdapter.notifyDataSetChanged();


        }


    }
    private void getworldNewsData() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlworld, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("Response : ", String.valueOf(response));
                FileManager fileManager = new FileManager();
                fileManager.writeToFile("worldnews.dat", String.valueOf(response), getContext());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        News news = new News();
                        news.setName(jsonObject.getString("title"));
                        news.setGeners(jsonObject.getString("category"));
                        news.setThumbnail(jsonObject.getString("thumb_small"));

                        //  movie.setThumbnail(jsonObject.getString("thumb_big"));
                        news.setShortDescription(jsonObject.getString("detail"));
                        news.setLongDescription(jsonObject.getString("detail"));

                        worldList.add(news);
                        worldAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        Log.e("Json Exception : ", e.getMessage());
                        e.printStackTrace();

                    }
                }
                worldAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    getofflineworldnenewsData();
                } catch (JSONException e) {
                    //e.printStackTrace();
                }
                Log.e("Volley Error : ", error.toString());



            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache();
        requestQueue.add(jsonArrayRequest);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.newsfragment, parent, false);
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
    TabHost.TabSpec spec = host.newTabSpec("Recent");
    spec.setContent(R.id.tab1);

    spec.setIndicator("Recent");
    host.addTab(spec);

    //Tab 2
    spec = host.newTabSpec("Ethiopia");
    spec.setContent(R.id.tab2);
    spec.setIndicator("Ethiopia");
    host.addTab(spec);

    //Tab 3
    spec = host.newTabSpec("Africa");
    spec.setContent(R.id.tab3);
    spec.setIndicator("Africa");
    host.addTab(spec);

    //Tab 3
    spec = host.newTabSpec("World");
    spec.setContent(R.id.tab3);
    spec.setIndicator("World");
    host.addTab(spec);


    //____________________________________________


    recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

    newsList = new ArrayList<>();
    adapter = new NewsAdapter(getContext(), newsList);

    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
    recyclerView.setLayoutManager(mLayoutManager);
    // recyclerView.addItemDecoration(new MainActivity.GridSpacingItemDecoration(2, dpToPx(10), true));
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.setAdapter(adapter);
    try {
        getofflinenewsData();
    } catch (JSONException e) {

        Log.e("JsonException : ", e.getMessage());
    }

    getNewsData();
//_______________________________________________________________


    recyclerViewethio = (RecyclerView) view.findViewById(R.id.recycler_view2);

    ethioList = new ArrayList<>();
    ethioAdapter = new NewsAdapter(getContext(), ethioList);

    mLayoutManager = new GridLayoutManager(getContext(), 2);
    recyclerViewethio.setLayoutManager(mLayoutManager);
    // recyclerView.addItemDecoration(new MainActivity.GridSpacingItemDecoration(2, dpToPx(10), true));
    recyclerViewethio.setItemAnimator(new DefaultItemAnimator());
    recyclerViewethio.setAdapter(ethioAdapter);
    try {
        getoffliethionenewsData();
    } catch (JSONException e) {

        Log.e("JsonException : ", e.getMessage());
    }

    getethioNewsData();
//_______________________________________________________________


    recyclerViewafrica = (RecyclerView) view.findViewById(R.id.recycler_view3);

    afroList = new ArrayList<>();
    afroAdapter = new NewsAdapter(getContext(), afroList);

    mLayoutManager = new GridLayoutManager(getContext(), 2);
    recyclerViewafrica.setLayoutManager(mLayoutManager);
    // recyclerView.addItemDecoration(new MainActivity.GridSpacingItemDecoration(2, dpToPx(10), true));
    recyclerViewafrica.setItemAnimator(new DefaultItemAnimator());
    recyclerViewafrica.setAdapter(afroAdapter);
    try {
        getoffliafronenewsData();
    } catch (JSONException e) {

        Log.e("JsonException : ", e.getMessage());
    }

    getafroNewsData();
//_______________________________________________________________


    recyclerViewworld = (RecyclerView) view.findViewById(R.id.recycler_view4);

    worldList = new ArrayList<>();
    worldAdapter = new NewsAdapter(getContext(), worldList);

    mLayoutManager = new GridLayoutManager(getContext(), 2);
    recyclerViewworld.setLayoutManager(mLayoutManager);
    // recyclerView.addItemDecoration(new MainActivity.GridSpacingItemDecoration(2, dpToPx(10), true));
    recyclerViewworld.setItemAnimator(new DefaultItemAnimator());
    recyclerViewworld.setAdapter(worldAdapter);
    try {
        getofflineworldnenewsData();
    } catch (JSONException e) {

        Log.e("JsonException : ", e.getMessage());
    }

    getworldNewsData();
//_______________________________________________________________


}catch (Exception ex){

    Log.e("exeption :",ex.getMessage());
}




    }

}
