/**
 * Copyright (c) 2015-2015 EEFUNG Software Co.Ltd. All rights reserved.
 * 版权所有 (c) 2015-2015 湖南蚁坊软件有限公司。保留所有权利。
 */
package com.example.lenovo.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * ${Date}
 */
public class ThumbnailImageView extends ImageView {

    public ThumbnailImageView(Context context) {
        super(context);
    }

    public ThumbnailImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ThumbnailImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        BitmapDrawable bd = (BitmapDrawable) drawable;
        Bitmap bitmap = bd.getBitmap();
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Log.d("test", "w: "+ width+"h:"+height+"bitmap: "+bitmap);
        float scale = 1;
        float current = 200;
        Log.d("test", "w: "+ width+"h:"+height+"bitmap: "+bitmap+"current: "+current);
        if(width < current && height < current){
            scale = width < height ? current / width : current/height;

        }else if(width < current && height > current){
            scale = current / width;
        }else if(width > current && height < current){
            scale = current / height;
        }else if(width > current && height > current){
            scale = width < height ? width / current : height / current;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        Log.d("test", "w: " + width + "h:" + height + "bitmap: " + bitmap + "scale: " + scale);
        bitmap = Bitmap.createBitmap(bitmap, 0 ,0 ,width, height, matrix, true);
        BitmapDrawable drawable1 = new BitmapDrawable(bitmap);
        super.setImageDrawable(drawable1);
    }

    @Override
    public void setImageBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scale = 1;
        float current = 200;
        if(width < current && height < current){
            scale = width < height ? current / width : current/height;

        }else if(width < current && height > current){
            scale = current / width;
        }else if(width > current && height < current){
            scale = current / height;
        }else if(width > current && height > current){
            scale = width < height ? width / current : height / current;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        bitmap = Bitmap.createBitmap(bitmap, 0 ,0 ,width, height, matrix, true);
        super.setImageBitmap(bitmap);
    }
}