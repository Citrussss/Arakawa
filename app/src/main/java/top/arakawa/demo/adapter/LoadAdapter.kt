package top.arakawa.demo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import top.arakawa.demo.databinding.ItemLoadstateLodingBinding

/**
 * @author pisa
 * @date  2020/9/2
 * @version 1.0
 * @effect : wubba lubba dub dub
 */
class LoadAdapter : LoadStateAdapter<BaseViewHolder>() {
    override fun onBindViewHolder(holder: BaseViewHolder, loadState: LoadState) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): BaseViewHolder {
        LogUtils.dTag("pisa","刷新$loadState")
        return when (loadState) {
            is LoadState.Loading ->
                ItemLoadstateLodingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            is LoadState.Error ->
                ItemLoadstateLodingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            is LoadState.NotLoading ->
                ItemLoadstateLodingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
        }.let { BaseViewHolder(it.root) }
    }
}
