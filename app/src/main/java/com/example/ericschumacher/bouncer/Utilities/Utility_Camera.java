package com.example.ericschumacher.bouncer.Utilities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.example.ericschumacher.bouncer.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class Utility_Camera {

    public static File getImageFileFront(Context context)  {
        // Create an image file name
        File file = new File (context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)+"/JPEG_MODEL_COLOR_FRONT.jpg");
        if (!file.isDirectory()) {
            try {
                file.createNewFile();
                Log.i("PATTHH", file.getPath());
                Log.i("PATTHH_A", file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
        /*String imageFileName = "JPEG_MODEL_COLOR_FRONT";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(
                    imageFileName,
                    ".jpg",
                    storageDir
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Save a file: path for use with ACTION_VIEW intents
        //currentPhotoPath = image.getAbsolutePath();
        return image;
    */
    }

    public static File getImageFileBack(Context context)  {
        File file = new File (context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)+"/JPEG_MODEL_COLOR_BACK.jpg");
        if (!file.isDirectory()) {
            try {
                file.createNewFile();
                Log.i("PATTHH", file.getPath());
                Log.i("PATTHH_A", file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
        /*
        // Create an image file name
        String imageFileName = "JPEG_MODEL_COLOR_BACK";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(
                    imageFileName,
                    ".jpg",
                    storageDir
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Save a file: path for use with ACTION_VIEW intents
        //currentPhotoPath = image.getAbsolutePath();
        return image;
        */
    }

    public static void deleteImageFileFront(Context context) {
        getImageFileFront(context).delete();
    }

    public static void deleteImageFileBack(Context context) {
        getImageFileBack(context).delete();
    }

    public static String getStringImage(File fImage) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(fImage.getAbsolutePath(), options);
        bitmap = rotate(bitmap, 90);
        bitmap = resize(bitmap);

        if (bitmap == null) {
            Log.i("EEERROr", "jj");
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        return encodedImage;
    }

    public static boolean hasPersmission(Context context) {
        return (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    public static void requestPermission(Context context) {
        Toast.makeText(context, context.getString(R.string.toast_request_permission_camera), Toast.LENGTH_LONG).show();
    }

    public static Bitmap rotate(Bitmap bitmap, int nAngle) {
        Matrix matrix = new Matrix();

        matrix.postRotate(nAngle);

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

        Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);

        return rotatedBitmap;
    }

    public static Bitmap resize (Bitmap bitmap) {
        int orginalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int scaleFactor = 15;
        int newWidth = orginalWidth/scaleFactor;
        int newHeight = originalHeight/scaleFactor;
        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, false);
    }

    public static int getPixel(Context context, int dp) {
        return Math.round(dp*(context.getResources().getDisplayMetrics().xdpi/DisplayMetrics.DENSITY_DEFAULT));
    }

    public static Bitmap resizeToGivenWidth(Context context, Bitmap bitmap, int nWidth) {
        double orginalWidth = bitmap.getWidth();
        double originalHeight = bitmap.getHeight();
        double newWidth = Utility_Camera.getPixel(context,nWidth);
        Log.i("new Widht", ""+newWidth);
        Log.i("old width", ""+orginalWidth);
        double scaleFactor = orginalWidth/newWidth;
        Log.i("scale Factor", ""+Double.toString(scaleFactor));
        Log.i("height", ""+bitmap.getHeight());
        double newHeight = originalHeight/scaleFactor;
        Log.i("newHeight", ""+newHeight);
        return Bitmap.createScaledBitmap(bitmap, (int)newWidth, (int)newHeight, false);
    }

}
