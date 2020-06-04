package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.Random;

//把Volley封装成Singleton(单例)
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private String url1;
    private String url2;
    private ImageView imageView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        imageView = findViewById(R.id.imageView);
        url1 = "https://pixabay.com/get/55e0d340485aa814f6da8c7dda79367a1338d9e553566c4870287dd3924cc05bbe_1280.jpg";
        url2 = "https://pixabay.com/get/55e1d4404953a414f6da8c7dda79367a1338d9e553566c4870287dd3924cc05bbe_1280.jpg";
        RequestQueue queue = Volley.newRequestQueue(this);
        //StringRequest;JsonRequest
        /*StringRequest stringRequest = new StringRequest(
                StringRequest.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        textView.setText(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TAG, "onErrorResponse: "+error);
                    }
                }
        );
        queue.add(stringRequest);*/

        /*ImageLoader imageLoader = new ImageLoader(queue, new ImageLoader.ImageCache() {
            private LruCache<String, Bitmap> cache = new LruCache<>(50); //least reacently used

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });//不写也行*/

       /* //使用NetworkImageView不用下面的
        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                imageView.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "onErrorResponse: " + error);
            }
        });*/
        /*//使用NetworkImageView
        imageView.setImageUrl(url, imageLoader);*/

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadImage();
            }
        });
    }

    private void loadImage() {
        Random random = new Random();
        String url = random.nextBoolean() ? url1 : url2;
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.ic_launcher_background)
                .centerCrop()
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        if (swipeRefreshLayout.isRefreshing()){
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if (swipeRefreshLayout.isRefreshing()){
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        return false;
                    }
                })
                .into(imageView);
    }
}
