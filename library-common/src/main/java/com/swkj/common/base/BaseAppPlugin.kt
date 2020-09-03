package com.swkj.common.base

import android.app.Application

/**
 * @author pisa
 * @date  2020/6/15
 * @version 1.0
 * @effect : Common
 */
interface BaseAppPlugin {
    fun onOnCreate(app: Application)
}