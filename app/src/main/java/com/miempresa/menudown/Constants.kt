package com.miempresa.menudown

import android.Manifest

object Constants {
    const val TAG = "cameraX"
    const val  FILE_NAME_FORMAT = "yy-MM-dd-HH-mm-ss-SSS"
    const val REQUEST_CODE_PERMISSIONS =123
    const val CAMERA_PERM_CODE = 100
    val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)

}