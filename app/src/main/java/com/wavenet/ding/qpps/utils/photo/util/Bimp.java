package com.wavenet.ding.qpps.utils.photo.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Bimp {
    public static int max = 0;
    public static int maxvideo = 0;

    public static ArrayList<ImageItem> tempSelectBitmap = new ArrayList<ImageItem>();   //选择的图片的临时列表

    public static Bitmap revitionImageSize(String path) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(
                new File(path)));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);
        in.close();
        int i = 0;
        Bitmap bitmap = null;
        while (true) {
            if ((options.outWidth >> i <= 1000)
                    && (options.outHeight >> i <= 1000)) {
                in = new BufferedInputStream(
                        new FileInputStream(new File(path)));
                options.inSampleSize = (int) Math.pow(2.0D, i);
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeStream(in, null, options);
                break;
            }
            i += 1;
        }

        return bitmap;
    }

    /**
     * 网络图片转换为Bitmap
     */

    public static Bitmap netPicToBmp(final String urlpath) {
        final Bitmap[] myBitmap = {null};
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    URL url = new URL(urlpath);
                    URLConnection conn = url.openConnection();
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    myBitmap[0] = BitmapFactory.decodeStream(is);

//			//设置固定大小
//			//需要的大小
//			float newWidth = 200f;
//			float newHeigth = 200f;
//
//			//图片大小
//			int width = myBitmap.getWidth();
//			int height = myBitmap.getHeight();
//
//			//缩放比例
//			float scaleWidth = newWidth / width;
//			float scaleHeigth = newHeigth / height;
//			Matrix matrix = new Matrix();
//			matrix.postScale(scaleWidth, scaleHeigth);
//
//			Bitmap bitmap = Bitmap.createBitmap(myBitmap, 0, 0, width, height, matrix, true);

                } catch (IOException e) {
                    // Log exception

                }
            }
        });
        thread.start();
        return myBitmap[0];
    }
}
