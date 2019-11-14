package com.wavenet.ding.qpps.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UrlBitmapUtils {
//    public static String url = "http://222.66.154.70:2069/FTPHZSW/20060701/0010/站次7/0010.jpg";
    public static String url = "http://116.236.251.18:9090/FileList/SubScriptionImg/icon_df.png";
    public static void main(String[] args) {
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    URL imageurlx = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection) imageurlx.openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        System.out.println("qingpu bitmap"+bitmap);
                    }catch (Exception e){
                        System.out.println("qingpu exception "+e.getClass());
                        e.printStackTrace();
                    }
                    is.close();
                }catch (IOException e){
                    System.out.println("qingpu exception2 "+e.getClass());
                    e.printStackTrace();
                }
            }
        }).start();*/

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            final int index = i;
            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(index);
//                        Thread.sleep(2000);

                        URL imageurlx = new URL(url);
                        HttpURLConnection conn = (HttpURLConnection) imageurlx.openConnection();
//                        conn.setConnectTimeout(5000);
                        conn.setDoInput(true);
                        conn.connect();
                        InputStream is = conn.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        System.out.println("qingpu bitmap"+bitmap);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        }

    }
    public static Bitmap getBitMap(String ImageUrl){
        URL imageurlx = null;
        Bitmap bitmap = null;
        try {
            imageurlx = new URL(ImageUrl);
            HttpURLConnection conn = (HttpURLConnection) imageurlx.openConnection();
//                        conn.setConnectTimeout(5000);
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
