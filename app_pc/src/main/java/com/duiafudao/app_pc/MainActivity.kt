package com.duiafudao.app_pc

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.duiafudao.app_pc.mine.PersonDataBindingFragment

/**
 * https://blog.csdn.net/qq_20330595/article/details/79358570
 *  Kotlin 使用 DataBinding
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
                .add(R.id.fl_container, PersonDataBindingFragment.newInstance("Test"))
                .commit()
    }
}
