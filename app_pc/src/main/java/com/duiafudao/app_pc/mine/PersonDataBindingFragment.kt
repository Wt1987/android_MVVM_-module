package com.duiafudao.app_pc.mine

import android.os.Bundle
import com.duiafudao.app_pc.BR
import com.duiafudao.app_pc.R
import com.duiafudao.app_pc.databinding.FragmentPersonBinding
import com.fragment.BaseDataBindingFragment

/** https://www.jianshu.com/p/ba4982be30f8
 *  https://www.jianshu.com/p/53925ccb900e
 *  绑定监听 --> https://blog.csdn.net/crazyman2010/article/details/53590007
 * Desc: 我的界面
 * Author: Jooyer
 * Date: 2018-08-02
 * Time: 13:41
 */
class PersonDataBindingFragment : BaseDataBindingFragment<FragmentPersonBinding, PersonViewModel>() {
    override fun initContentView(): Int {
        return R.layout.fragment_person
}

    companion object {
        val TAG = PersonDataBindingFragment::class.java.simpleName
        public fun newInstance(param: String): PersonDataBindingFragment {
            val personFragment = PersonDataBindingFragment()
            val bundle = Bundle()
            bundle.putString(TAG, param)
            personFragment.arguments = bundle
            return personFragment
        }
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initViewModel(): PersonViewModel {
        return PersonViewModel(context!!)
    }


}