package com.shubhamkhuva.bottomnavigation

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.activity_main.*
// Shubham Khuva
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        BottomNavigationViewHelper.disableShiftMode(navigation);

        val bottomNavigationMenuView = navigation.getChildAt(0) as BottomNavigationMenuView
        val v = bottomNavigationMenuView.getChildAt(2)
        val itemView = v as BottomNavigationItemView
        val badge = LayoutInflater.from(this)
                .inflate(R.layout.notification_badge, bottomNavigationMenuView, false)
        itemView.addView(badge)
    }
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                message.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                message.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notification -> {
                message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_account -> {
                message.setText(R.string.title_myaccount)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
    object BottomNavigationViewHelper {
        @SuppressLint("RestrictedApi")
        fun disableShiftMode(view:BottomNavigationView) {
            val menuView = view.getChildAt(0) as BottomNavigationMenuView
            try
            {
                val shiftingMode = menuView.javaClass.getDeclaredField("mShiftingMode")
                shiftingMode.setAccessible(true)
                shiftingMode.setBoolean(menuView, false)
                shiftingMode.setAccessible(false)
                for (i in 0 until menuView.getChildCount())
                {
                    val item = menuView.getChildAt(i) as BottomNavigationItemView
                    item.setShiftingMode(false)
                    // set once again checked value, so view will be updated

                    item.setChecked(item.getItemData().isChecked())
                }
            }
            catch (e:NoSuchFieldException) {
                Log.e("BNVHelper", "Unable to get shift mode field", e)
            }
            catch (e:IllegalAccessException) {
                Log.e("BNVHelper", "Unable to change value of shift mode", e)
            }
        }
    }
}
