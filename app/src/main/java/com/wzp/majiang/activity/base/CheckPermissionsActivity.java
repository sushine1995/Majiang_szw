/**
 *
 */
package com.wzp.majiang.activity.base;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import com.wzp.majiang.R;
import com.wzp.majiang.constant.ProjectConstants;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CheckPermissionsActivity extends BaseActivity {

    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
//        Manifest.permission.ACCESS_COARSE_LOCATION,
//        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    };

    protected static final int PERMISSON_REQUEST_CODE = 0;

    @Override
    protected void onStart() {
        super.onStart();

        List<String> permissionList = checkPermissions(needPermissions);
        if (permissionList != null && !(permissionList.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                || permissionList.contains(Manifest.permission.READ_EXTERNAL_STORAGE))) {
            createDefaultFolder();
        }
    }

    /**
     * 检查是否具有设定的权限
     *
     * @param permissions
     * @return 权限是否均被接受
     */
    protected List<String> checkPermissions(String... permissions) {
        List<String> needRequestPermissonList = null;
        if (Build.VERSION.SDK_INT >= 23
                && getApplicationInfo().targetSdkVersion >= 23) {
            needRequestPermissonList = findDeniedPermissions(permissions);
            getPermission(needRequestPermissonList);
        }
        return needRequestPermissonList;
    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     * @since 2.5.0
     */
    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<>();
        try {
            for (String perm : permissions) {
                Method checkSelfMethod = getClass().getMethod("checkSelfPermission", String.class);
                Method shouldShowRequestPermissionRationaleMethod = getClass().getMethod("shouldShowRequestPermissionRationale",
                        String.class);
                if ((Integer) checkSelfMethod.invoke(this, perm) != PackageManager.PERMISSION_GRANTED
                        || (Boolean) shouldShowRequestPermissionRationaleMethod.invoke(this, perm)) {
                    needRequestPermissonList.add(perm);
                }
            }
        } catch (Throwable e) {
            Log.e(LOG_TAG, Log.getStackTraceString(e));
        }
        return needRequestPermissonList;
    }

    private void getPermission(List<String> needRequestPermissonList) {
        if (null != needRequestPermissonList
                && needRequestPermissonList.size() > 0) {
            try {
                // 存在被拒绝的权限，需要提醒用户获取相应的权限，方可正常使用定位功能
                String[] array = needRequestPermissonList.toArray(new String[needRequestPermissonList.size()]);
                Method method = getClass().getMethod("requestPermissions",
                        new Class[]{String[].class, int.class});

                method.invoke(this, array, PERMISSON_REQUEST_CODE);
            } catch (Exception e) {
                Log.e(LOG_TAG, Log.getStackTraceString(e));
            }
        }
    }

    /**
     * 检测是否所有的权限都已经授权
     *
     * @param grantResults
     * @return
     * @since 2.5.0
     */
    protected boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @TargetApi(23)
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] paramArrayOfInt) {
        if (requestCode == PERMISSON_REQUEST_CODE) {
            if (!verifyPermissions(paramArrayOfInt)) {
                showMissingPermissionDialog();
            }

            Map<String, Integer> map = new HashMap<>();
            for (int i = 0; i < permissions.length; i++) {
                map.put(permissions[i], paramArrayOfInt[i]);
            }
            if (map.containsKey(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    && map.get(Manifest.permission.WRITE_EXTERNAL_STORAGE).equals(PackageManager.PERMISSION_GRANTED)
                    && map.containsKey(Manifest.permission.READ_EXTERNAL_STORAGE)
                    && map.get(Manifest.permission.READ_EXTERNAL_STORAGE).equals(PackageManager.PERMISSION_GRANTED)) {
                createDefaultFolder();
            }
        }
    }

    /**
     * 显示提示信息
     *
     * @since 2.5.0
     */
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.notifyTitle);
        builder.setMessage(R.string.notifyMsg);

        // 拒绝, 退出应用
        builder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

        builder.setPositiveButton(R.string.setting,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                });

        builder.setCancelable(false);

        builder.show();
    }

    /**
     * 启动应用的设置
     *
     * @since 2.5.0
     */
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    private void createDefaultFolder() {
        File basePath = new File(ProjectConstants.BASE_FILE_PATH);
        if (!basePath.exists()) {
            basePath.mkdir();
        }

        File uncaughtExceptionLogPath = new File(ProjectConstants.UNCAUGHT_EXCEPTION_LOG_PATH);
        if (!uncaughtExceptionLogPath.exists()) {
            uncaughtExceptionLogPath.mkdirs();
        }
    }
}
