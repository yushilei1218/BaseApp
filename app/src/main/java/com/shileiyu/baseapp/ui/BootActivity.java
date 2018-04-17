package com.shileiyu.baseapp.ui;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.shileiyu.baseapp.R;
import com.shileiyu.baseapp.common.base.BaseActivity;
import com.shileiyu.baseapp.common.util.Constant;
import com.shileiyu.baseapp.ui.calendar.CalendarActivity;
import com.shileiyu.baseapp.ui.glide.GlideActivity;
import com.shileiyu.baseapp.ui.greendao.DbUpgradeActivity;
import com.shileiyu.baseapp.ui.greendao.GreenDaoActivity;
import com.shileiyu.baseapp.ui.lagou.LaGouActivity;
import com.shileiyu.baseapp.ui.ocr.OcrActivity;
import com.shileiyu.baseapp.ui.viewmodel.ViewModelActivity;
import com.shileiyu.baseapp.ui.waterfall.WaterfallActivity;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;

public class BootActivity extends BaseActivity {

    @BindView(R.id.boot_grid)
    GridView mBootGrid;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_boot;
    }

    @Override
    protected void initView() {
//2.1.0版本
        List<Bean> data = new ArrayList<>();
        data.add(new Bean(Constant.GREEN_DAO));
        data.add(new Bean(Constant.DAO_UPGRADE));
        data.add(new Bean(Constant.WATER_FALL));
        data.add(new Bean(Constant.GLIDE));
        data.add(new Bean(Constant.CALENDAR));
        data.add(new Bean(Constant.LAGOU));
        data.add(new Bean(Constant.VIEWMODEL));
        data.add(new Bean(Constant.OCR));
        data.add(new Bean(Constant.PICK_IMG));

        mBootGrid.setAdapter(new Adapter(data));

        mBootGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getAdapter().getItem(position);
                if (item instanceof Bean) {
                    openActivity(((Bean) item));
                }
            }
        });
    }

    private void openActivity(Bean item) {
        Intent intent = null;
        switch (item.name) {
            case Constant.GREEN_DAO:
                intent = new Intent(this, GreenDaoActivity.class);
                break;
            case Constant.WATER_FALL:
                intent = new Intent(this, WaterfallActivity.class);
                break;
            case Constant.GLIDE:
                intent = new Intent(this, GlideActivity.class);
                break;
            case Constant.CALENDAR:
                intent = new Intent(this, CalendarActivity.class);
                break;
            case Constant.LAGOU:
                intent = new Intent(this, LaGouActivity.class);
                break;
            case Constant.VIEWMODEL:
                intent = new Intent(this, ViewModelActivity.class);
                break;
            case Constant.DAO_UPGRADE:
                intent = new Intent(this, DbUpgradeActivity.class);
                break;
            case Constant.OCR:
                intent = new Intent(this, OcrActivity.class);
                break;
            case Constant.PICK_IMG:
                pickFromGallery();
                return;
            default:
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }

    }

    private static final class Bean {
        public final String name;

        public Bean(String name) {
            this.name = name;
        }
    }

    private static class Adapter extends BaseAdapter {
        List<Bean> data;

        Adapter(List<Bean> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bean, parent, false);
                convertView.setTag(new VH(convertView));
            }
            VH tag = (VH) convertView.getTag();
            tag.tv.setText(data.get(position).name);
            return convertView;
        }

        static final class VH {
            TextView tv;

            VH(View view) {
                tv = (TextView) view.findViewById(R.id.item_bean_tv);
            }
        }
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void pickFromGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT)
                .setType("image/*")
                .addCategory(Intent.CATEGORY_OPENABLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            String[] mimeTypes = {"image/jpeg", "image/png"};
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        }

        startActivityForResult(Intent.createChooser(intent, "测试选择图片"), 0x01);
    }

    @OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
    void showRationaleForPermission(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("再次申请权限")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .show();
    }

    @OnPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE)
    void showDeniedForPermission() {
        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(Manifest.permission.READ_EXTERNAL_STORAGE)
    void showNeverAskForPermission() {
        Toast.makeText(this, "Never Ask again", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 0x01) {
                final Uri selectedUri = data.getData();
                if (selectedUri != null) {
                    startCrop(selectedUri);
                } else {
                    Toast.makeText(this, "未选择图片", Toast.LENGTH_SHORT).show();
                }
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void startCrop(Uri selectedUri) {

        UCrop uCrop = UCrop.of(selectedUri, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "crop.jpeg")));
        uCrop.start(this);
    }
}

