package com.swkj.common.util.ktx

import android.text.TextUtils
import com.blankj.utilcode.util.LogUtils
import com.luck.picture.lib.PictureSelectionModel
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.swkj.common.util.GlideEngine

/**
 * author pisa
 * date  2020/4/10
 * version 1.0
 * effect :
 */
/**
 * 快速构造 拍照
 */
fun PictureSelector.buildCameraPic(): PictureSelectionModel {
    return this
        .openCamera(PictureMimeType.ofImage())
        .maxSelectNum(1)
        .minSelectNum(1)
        .imageSpanCount(3)
        .selectionMode(PictureConfig.SINGLE)
        .isPreviewVideo(true)
        .isCamera(true) //图片加载引擎，必传项
        .imageEngine(GlideEngine.getInstance())
        .isCompress(true)
}

/**
 * 快速构造 选图
 */
fun PictureSelector.buildMediaPic(chooseMode: Int = PictureMimeType.ofImage()): PictureSelectionModel {
    return this
        .openGallery(chooseMode)
        .maxSelectNum(1)
        .minSelectNum(1)
        .imageSpanCount(3)
        .selectionMode(PictureConfig.SINGLE)
        .isPreviewImage(true)
        .isPreviewVideo(true)
        .isEnableCrop(true)
        .freeStyleCropEnabled(true)
        .isCamera(false) //图片加载引擎，必传项
        .imageEngine(GlideEngine.getInstance())
        .isCompress(true)
}

/**
 * 显示
 */
fun PictureSelectionModel.show(listener: OnResultCallbackListener<LocalMedia>) {
    this.forResult(listener)
}

//fun PictureSelectionModel.show(result: (localMedia: LocalMedia?, uri: Uri?) -> Unit) {
//    this.forResult(SimpleResultCallbackListener(Consumer {
//        val localMedia = it[0]
//        result.invoke(localMedia,
//                localMedia.let {
//                    if (it?.isCut) {
//                        Uri.fromFile(File(it?.cutPath))
//                    } else if (it?.isCompressed) {
//                        Uri.fromFile(File(it.compressPath))
//                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                        Uri.fromFile(File(it.androidQToPath))
//                    } else {
//                        val file = File(it.path)
//                        if (file.exists()) {
//                            Uri.fromFile(file)
//                        } else {
//                            Uri.EMPTY
//                        }
//                    }
//                }
//        )
//    }))
//}
open class SimpleResultCallbackListener(//    public abstract void onSuccess(List<LocalMedia> result);
    private val onSuccess: Function1<String?, Unit>
) :
    OnResultCallbackListener<LocalMedia> {


    override fun onCancel() {
        LogUtils.i("用户取消了选择")
    }

    override fun onResult(result: MutableList<LocalMedia>?) {
        if (result != null && !result.isEmpty()) {
            result.singleOrNull()?.let {
                if (!TextUtils.isEmpty(it.cutPath)) {
                    it.cutPath
                } else if (!TextUtils.isEmpty(it.compressPath)) {
                    it.compressPath
                } else if (!TextUtils.isEmpty(it.androidQToPath)) {
                    it.androidQToPath
                } else {
                    it.path
                }
            }?.run(onSuccess)
        } else {
            LogUtils.i("数据为空：%1s", result)
        }
    }
}