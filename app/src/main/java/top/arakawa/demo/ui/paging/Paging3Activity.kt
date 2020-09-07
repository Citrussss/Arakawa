package top.arakawa.demo.ui.paging

import androidx.activity.viewModels
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.LogUtils
import com.swkj.common.base.BaseActivity
import com.swkj.common.util.ktx.toJson
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import top.arakawa.demo.R
import top.arakawa.demo.adapter.ArticleAdapter
import top.arakawa.demo.adapter.LoadAdapter
import top.arakawa.demo.databinding.ActivityPaging3Binding
import top.arakawa.demo.net.bean.Article
import top.arakawa.demo.net.bean.Page
import top.arakawa.demo.net.bean.WanAndroidBaseBean
import top.arakawa.demo.viewmodel.paging.Paging3ViewModel
import top.arakawa.ktor.Http

/**
 * @author pisa
 * @date  2020/9/2
 * @version 1.0
 * @effect : wubba lubba dub dub
 */
@ExperimentalPagingApi
class Paging3Activity : BaseActivity<Nothing>() {

    private val binding by lazy { ActivityPaging3Binding.bind(contentView) }
    private val viewModel by viewModels<Paging3ViewModel>()
    private val adapter by lazy {  ArticleAdapter() }
    override fun getResLayout(): Int {
        return R.layout.activity_paging3
    }

    override fun onShowLoading() {
    }

    override fun onHideLoading() {
    }

    override fun initView() {
        super.initView()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter =  adapter.withLoadStateFooter(LoadAdapter())
        initListener()
    }

    override fun initListener() {
        super.initListener()
        adapter.addDataRefreshListener {
            LogUtils.dTag("pisa","刷新$it")
            if (!it){
                binding.smartRefreshLayout.finishRefresh()
            }
        }
        binding.smartRefreshLayout.setOnRefreshListener {
            LogUtils.dTag("pisa","开始刷新")
            adapter.refresh()
        }
        viewModel.getArticle().observe(this, Observer {
            adapter.submitData(this@Paging3Activity.lifecycle,it)
        })
    }
}