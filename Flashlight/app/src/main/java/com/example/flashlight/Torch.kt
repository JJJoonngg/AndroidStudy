package com.example.flashlight

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager

class Torch(context: Context) {
    private var cameraId: String? = null
    private var cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager

    init {
        cameraId = getCameraId()
    }

    fun flashOn() {
        cameraId?.let {
            cameraManager.setTorchMode(it, true)
        }
    }

    fun flashOff() {
        cameraId?.let {
            cameraManager.setTorchMode(it, false)
        }
    }

    private fun getCameraId(): String? {
        var cameraIds = cameraManager.cameraIdList
        for (id in cameraIds) {
            val info = cameraManager.getCameraCharacteristics(id)
            val flashAvailable = info.get(CameraCharacteristics.FLASH_INFO_AVAILABLE) // 플래시 가능 여부
            val lensFacing = info.get(CameraCharacteristics.LENS_FACING) //카메라 렌즈 방향
            if (flashAvailable != null
                && flashAvailable
                && lensFacing != null
                && lensFacing == CameraCharacteristics.LENS_FACING_BACK
            ) {
                return id
            }
        }
        return null
    }
}