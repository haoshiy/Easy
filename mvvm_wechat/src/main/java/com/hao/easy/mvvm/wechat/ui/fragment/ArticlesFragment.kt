package com.hao.easy.mvvm.wechat.ui.fragment

import android.os.Bundle
import android.view.View
import com.hao.easy.mvvm.base.App
import com.hao.easy.mvvm.base.ui.BaseListFragment
import com.hao.easy.mvvm.base.ui.WebActivity
import com.hao.easy.mvvm.inject.component.DaggerFragmentComponent
import com.hao.easy.mvvm.inject.module.FragmentCommonModule
import com.hao.easy.mvvm.inject.module.FragmentModule
import com.hao.easy.mvvm.wechat.R
import com.hao.easy.mvvm.wechat.model.Article
import com.hao.easy.mvvm.wechat.ui.adapter.ArticlesAdapter
import com.hao.easy.mvvm.wechat.viewmodel.ArticlesViewModel
import com.socks.library.KLog
import javax.inject.Inject

/**
 * @author Yang Shihao
 * @date 2018/11/18
 */
class ArticlesFragment : BaseListFragment<Article>() {

    companion object {
        private const val TAG = "ArticlesFragment"
        private const val AUTHOR_ID = "AUTHOR_ID"
        fun instance(authorId: Int): ArticlesFragment {
            val fragment = ArticlesFragment()
            val bundle = Bundle()
            bundle.putInt(AUTHOR_ID, authorId)
            fragment.arguments = bundle
            return fragment
        }
    }

    @Inject
    lateinit var adapter: ArticlesAdapter

    @Inject
    lateinit var viewModel: ArticlesViewModel

    override fun getLayoutId() = R.layout.fragment_wechat_articles

    override fun initInject() {
        DaggerFragmentComponent.builder()
                .appComponent(App.instance.appComponent)
                .fragmentCommonModule(FragmentCommonModule(this))
                .fragmentModule(FragmentModule())
                .build()
                .inject(this)
    }

    override fun initData() {
        KLog.d(TAG, "initData")
        arguments?.apply {
            viewModel.authorId = getInt(AUTHOR_ID, 409)
        }
        super.initData()
    }

    override fun dataViewModel() = viewModel

    override fun adapter() = adapter

    override fun itemClicked(view: View, item: Article, position: Int) {
       context?.apply { WebActivity.start(this, item.title, item.link) }
    }

    override fun refreshFinished(success: Boolean?) {
        super.refreshFinished(success)
        var weChatFragment = parentFragment as WechatFragment
        weChatFragment.refreshFinished()
    }

    fun refresh() {
        viewModel.invalidate()
    }
}