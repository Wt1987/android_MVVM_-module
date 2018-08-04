package com.duiafudao.app_pc.center

import android.databinding.ViewDataBinding
import android.os.Bundle
import com.duiafudao.app_pc.R
import com.activity.BaseDataBindingActivity
import com.viewModel.BaseViewModel

/**
 *  填写姓名
 */
class WriteNameDataBindingActivity : BaseDataBindingActivity<ViewDataBinding, BaseViewModel>() {
    override fun initData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initVariableId(): Int {
        return 0
    }


    override fun getLayoutId(savedInstanceState: Bundle?): Int {
        return  R.layout.activity_write_name
    }

    override fun initViewModel(): BaseViewModel {
        return BaseViewModel(this)
    }


}

