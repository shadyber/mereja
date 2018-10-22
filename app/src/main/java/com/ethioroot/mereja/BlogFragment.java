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

public class BlogFragment extends Fragment {
    String url ="https://www.mereja360.com/api/getrecentblog.php";


    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private List<News> newsList;
    //----------------- Drama Recicle View
    private void getofflinenewsData() throws JSONException {
        FileManager fileManager = new FileManager();
        String stringfile = fileManager.readFromFile("blogrecent.dat", getContext());
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
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("Response : ", String.valueOf(response));
                FileManager fileManager = new FileManager();
                fileManager.writeToFile("blogrecent.dat", String.valueOf(response), getContext());
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
                        progressDialog.dismiss();
                    }
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
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
                progressDialog.dismiss();



            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache();
        requestQueue.add(jsonArrayRequest);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.blogfragment, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);




        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);

        newsList = new ArrayList<>();
        adapter = new NewsAdapter(getContext(), newsList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        // recyclerView.addItemDecoration(new MainActivity.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        try {
            getofflinenewsData();
        } catch (JSONException e) {

            Log.e("JsonException : ",e.getMessage());
        }

        getNewsData();
//_______________________________________________________________



    }

}
