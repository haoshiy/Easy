package com.hao.easy.mvvm.android.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.alibaba.android.arouter.launcher.ARouter
import com.hao.easy.mvvm.android.model.Article
import com.hao.easy.mvvm.android.model.Type
import com.hao.easy.mvvm.android.repository.Api
import com.hao.easy.mvvm.base.Config
import com.hao.easy.mvvm.base.extensions.io_main
import com.hao.easy.mvvm.base.extensions.main
import com.hao.easy.mvvm.base.extensions.subscribeBy
import com.hao.easy.mvvm.base.viewmodel.BaseListViewModel

class AndroidViewModel : BaseListViewModel<Article>() {

    override fun pageSize() = 6

    val typeLiveData = MutableLiveData<ArrayList<Type>>()

    override fun loadData(page: Int, onResponse: (ArrayList<Article>?) -> Unit) {

        if (page == 1) {
            Api.getProjectType().io_main().subscribeBy({
                if (it != null && !it.isEmpty()) {
                    typeLiveData.value = it
                }
            }, {

            }).add()
        }

        Api.getNewArticles(page - 1).main().subscribeBy({
            onResponse(it?.datas)
        }, {
            onResponse(null)
        }).add()
    }

    fun collect(item: Article, position: Int) {
        if (!Config.instance().isLogin) {
            ARouter.getInstance().build("/user/LoginActivity").navigation()
            return
        }
        if (item.collect) {
            Api.cancelCollect(item.id).io_main().subscribeBy({
                item.collect = false
                notifyItem(position)
            }, {

            })
        } else {
            Api.collect(item.id).io_main().subscribeBy({
                item.collect = true
                notifyItem(position)
            }, {

            })
        }
    }
}