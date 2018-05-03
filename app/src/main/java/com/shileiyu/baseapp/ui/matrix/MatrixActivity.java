package com.shileiyu.baseapp.ui.matrix;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.View;

import com.shileiyu.baseapp.R;
import com.shileiyu.baseapp.common.base.BaseActivity;
import com.shileiyu.baseapp.common.widget.TabView;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MatrixActivity extends BaseActivity {


    @BindView(R.id.matrix)
    TabView mMatrix;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_matrix;
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.matrix, R.id.matrix_2, R.id.find_img})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.matrix:
                break;
            case R.id.matrix_2:
                break;
            case R.id.find_img:
                checkImgPermission();
                break;
            default:
                break;
        }
    }

    final int VALUE_PICK_PICTURE = 0x003;
    final int VALUE_CROP_PICTURE = 0x004;

    private void checkImgPermission() {
        final int code = 0x002;

        AndPermission.with(this).requestCode(code).permission(Permission.CAMERA, Permission.STORAGE).callback(new PermissionListener() {

            @Override
            public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, VALUE_PICK_PICTURE);
            }

            @Override
            public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                showToast("");
            }
        }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && VALUE_PICK_PICTURE == requestCode) {
            switch (requestCode) {
                case VALUE_PICK_PICTURE:
                    File target = new File(Environment.getExternalStorageDirectory(), "img.jpg");
                    Uri data1 = data.getData();
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(data1, "image/*");
                    // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
                    intent.putExtra("crop", "true");
                    //该参数可以不设定用来规定裁剪区的宽高比
                    intent.putExtra("aspectX", 2);
                    intent.putExtra("aspectY", 1);
                    //该参数设定为你的imageView的大小
                    intent.putExtra("outputX", 600);
                    intent.putExtra("outputY", 300);
                    intent.putExtra("scale", true);
                    //是否返回bitmap对象
                    intent.putExtra("return-data", false);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(target));
                    intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());//输出图片的格式
                    intent.putExtra("noFaceDetection", true); // 头像识别
                    startActivityForResult(intent, VALUE_CROP_PICTURE);
                    break;
                case VALUE_CROP_PICTURE:
                    showToast("OK");
                    break;
            }

        }
    }
}
