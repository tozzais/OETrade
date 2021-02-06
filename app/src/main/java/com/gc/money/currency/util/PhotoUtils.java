package com.gc.money.currency.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import com.gc.money.currency.R;
import com.gc.money.currency.global.Constant;

import java.io.File;

public class PhotoUtils {

    private static volatile PhotoUtils singleton;

    private PhotoUtils() {}

    public static PhotoUtils getInstance() {
        if (singleton == null) {
            synchronized (PhotoUtils.class) {
                if (singleton == null) {
                    singleton = new PhotoUtils();
                }
            }
        }
        return singleton;
    }


    public static final int PHOTOZOOM = 0;
    public static final int PHOTOTAKE = 1;
    public static final int IMAGE_COMPLETE = 2; // 结果
    public static final String PATH = Constant.PATH; // 结果
    private String photoSaveName;

    public static String[] needPermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    public void selectPic(Context context) {
        new MySelfSheetDialog(context).builder().addSheetItem(context.getResources().getString(R.string.TakingPictures), MySelfSheetDialog.SheetItemColor.Black, new MySelfSheetDialog.OnSheetItemClickListener() {

            @Override
            public void onClick(int which) {
                takePhoto(context);
            }
        }).addSheetItem(context.getResources().getString(R.string.PhotoAlbum), MySelfSheetDialog.SheetItemColor.Black, new MySelfSheetDialog.OnSheetItemClickListener() {

            @Override
            public void onClick(int which) {
                Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                openAlbumIntent.setType("image/*");
                ((Activity)context).startActivityForResult(openAlbumIntent, PHOTOZOOM);
            }
        }).show();
    }


    private void takePhoto(Context context){
        //这代码有问题 就是打开拍照没问题，然后在打开录制视频 在小米9上就闪退
        photoSaveName = PATH + System.currentTimeMillis() + ".jpg";
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(photoSaveName);
//        file.getParentFile().mkdirs();
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(
                    context, context.getApplicationContext().getPackageName() + ".provider",
                    file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(file);
        }
//        Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);
//        intent.putExtra("android.intent.extras.CAMERA_FACING", 2);
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        ((Activity)context).startActivityForResult(intent, PHOTOTAKE);

//        photoSaveName = PATH + System.currentTimeMillis() + ".jpg";
//        File file = new File(photoSaveName);
//        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//        Uri uri;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            uri = FileProvider.getUriForFile(
//                    context, context.getApplicationContext().getPackageName() + ".provider",
//                    file);
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//        } else {
//            uri = Uri.fromFile(file);
//        }
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//        ((Activity)context).startActivityForResult(intent, PHOTOTAKE);

    }

    public void takeFrontPhoto(Context context){
        photoSaveName = PATH + System.currentTimeMillis() + ".jpg";
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(photoSaveName);
        file.getParentFile().mkdirs();
        Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);
        //添加权限
        intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        ((Activity)context).startActivityForResult(intent, PHOTOTAKE);
    }

    public String getPath(Context context, int requestCode, Intent data){
        String path = "";
        switch (requestCode) {
            case PhotoUtils.PHOTOZOOM:// 相册
                if (data == null) {
                    return "";
                }
                path= BitmapUtils.getInstance().getPath(context, data.getData());
                break;
            case PHOTOTAKE:// 拍照
                path = ImageUtils.amendRotatePhoto(photoSaveName, context);;
                break;

        }
        return path;
    }
}
