package com.jumbomob.invistoo.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author maiko.trindade
 * @since 22/07/2016
 */
public class StorageUtil {

    private static String TAG = StorageUtil.class.getSimpleName();
    public final static String DIRECTORY_NAME = "/Ecrew/";

    public static String saveImageToDeviceStorage(Bitmap image, Context context) {
        String fullPath;
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";


        if (isExternalStorageWritable()) {
            fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                    DIRECTORY_NAME;// + APP_THUMBNAIL_PATH_SD_CARD;
        } else {
            fullPath = Environment.getDataDirectory().getAbsolutePath() + DIRECTORY_NAME;
            // APP_THUMBNAIL_PATH_SD_CARD;
        }

        return saveImage(image, fullPath, imageFileName, context);
    }

    private static String saveImage(Bitmap bitmap, String fullPath, String fileName, Context
            context) {

        try {
            File dir = new File(fullPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            OutputStream fOut = null;
            File file = new File(fullPath, fileName + ".png");
            Log.i(TAG, "PATH: " + file.getAbsolutePath());

            file.createNewFile();
            fOut = context.openFileOutput(fileName + ".png", Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            return fileName + ".png";

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    public static Bitmap loadBitmapFromStorage(String fileName, Context context) {
        try {
            FileInputStream fileInputStream = context.openFileInput(fileName);
            Bitmap b = BitmapFactory.decodeStream(fileInputStream);
            return b;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
}
