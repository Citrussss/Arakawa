package com.sw.common.net.flow

import kotlinx.coroutines.flow.AbstractFlow
import kotlinx.coroutines.flow.FlowCollector
import retrofit2.Call

/**
 * author pisa
 * date  2020/4/29
 * version 1.0
 * effect :
 */
class NetFlow : AbstractFlow<Call<Any>>() {

    override suspend fun collectSafely(collector: FlowCollector<Call<Any>>) {

    }
}