package top.arakawa.demo.adapter

import androidx.databinding.DataBindingUtil
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.swkj.common.base.paging.BasePageListAdapter
import top.arakawa.demo.R
import top.arakawa.demo.databinding.ItemArticleBinding
import top.arakawa.demo.net.bean.Article

/**
 * @author pisa
 * @date  2020/9/2
 * @version 1.0
 * @effect : wubba lubba dub dub
 */
class ArticleAdapter : BasePageListAdapter<Article, BaseViewHolder>(R.layout.item_article) {

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        DataBindingUtil.bind<ItemArticleBinding>(holder.itemView)?.apply {
//            LogUtils.dTag("pisa","填充数据"+GsonUtils.toJson(getItem(position)))
            tvTitle.text = getItem(position)?.title
        }
    }
}