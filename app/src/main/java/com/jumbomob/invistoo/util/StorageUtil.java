package com.jumbomob.invistoo.util;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.jumbomob.invistoo.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author maiko.trindade
 * @since 18/10/2016
 */
public class StorageUtil {

    private static String TAG = StorageUtil.class.getSimpleName();

    public final static String DIRECTORY_NAME = "/" + InvistooApplication.getInstance().getBaseContext
            ().getString(R.string.app_name) + "/";
    public final static int REQUEST_EXTERNAL_STORAGE = 1;

    public static String saveImageToStorage(Bitmap image, Context context) {
        String fullPath;
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        String imageFileName = "_" + timeStamp + "_";

        if (isExternalStorageWritable()) {
            fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                    DIRECTORY_NAME;
        } else {
            fullPath = Environment.getDataDirectory().getAbsolutePath() + DIRECTORY_NAME;
        }
        return saveImage(image, fullPath, imageFileName, context);
    }

    private static String saveImage(Bitmap bitmap, String fullPath, String fileName, Context context) {
        try {
            File dir = new File(fullPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            OutputStream outputStream = null;
            File file = new File(fullPath, fileName + ".png");
            Log.i(TAG, "PATH: " + file.getAbsolutePath());

            file.createNewFile();
            outputStream = context.openFileOutput(fileName + ".png", Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            return fileName + ".png";

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    public static Bitmap getBitmap(String fileName, Context context) {
        try {
            final FileInputStream fileInputStream = context.openFileInput(fileName);
            final Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
            return bitmap;
        } catch (FileNotFoundException e) {
            Log.e(TAG, "FileNotFoundException");
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

    public static Bitmap getThumbnail(Uri uri, ContentResolver resolver, double thumbnailSize) throws FileNotFoundException, IOException {

        InputStream input = resolver.openInputStream(uri);

        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();

        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1))
            return null;

        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;

        double ratio = (originalSize > thumbnailSize) ? (originalSize / thumbnailSize) : 1.0;

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither = true;
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
        input = resolver.openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }

    private static int getPowerOfTwoForSampleRatio(double ratio) {
        int k = Integer.highestOneBit((int) Math.floor(ratio));
        if (k == 0) return 1;
        else return k;
    }

}
