package com.duiafudao.app_pc.mine

import android.content.Context
import com.duiafudao.app_pc.center.WriteNameDataBindingActivity
import com.viewModel.BaseViewModel

/**
 * Desc: 我的
 * Author: Jooyer
 * Date: 2018-08-02
 * Time: 15:42
 */
class PersonViewModel(context: Context) : BaseViewModel(context){



    public fun gotoPersonalCenter() {
//        startActivity(PersonalCenterActivity::class.java)
        startActivity(WriteNameDataBindingActivity::class.java)
        println("==============>")
    }

}