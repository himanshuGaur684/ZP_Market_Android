package com.gaur.zpmarket

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.gaur.zpmarket.databinding.ActivityContainerBinding
import com.gaur.zpmarket.utils.CustomerConstants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ContainerActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences


    private var _binding: ActivityContainerBinding? = null
    val binding: ActivityContainerBinding
        get() = _binding!!

    lateinit var navController: NavController
    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_container)


        navController = findNavController(R.id.customer_fragment_container)


        drawerLayout = binding.drawerLayout


        binding.customersBottomNavigationView.setupWithNavController(navController)


        val navBuilder = NavOptions.Builder().setEnterAnim(R.anim.fragment_animation)
            .setExitAnim(R.anim.fragment_exit_animation).setPopEnterAnim(R.anim.fade_in)
            .setExitAnim(android.R.anim.fade_in).build()


        binding.customerNavigationView.setNavigationItemSelectedListener {
            when (it.itemId) {

                R.id.customerReviewFragment -> {
                    navController.navigate(
                        R.id.customerReviewFragment,
                        bundleOf(
                            "customer_id" to sharedPreferences.getString(
                                CustomerConstants.CUSTOMER_ID,
                                ""
                            ).toString()
                        ),
                        navBuilder
                    )
                }

                R.id.profileFragment -> {

                }


            }
            drawerLayout.closeDrawers()
            return@setNavigationItemSelectedListener false
        }

    }


    fun openNavigationDrawer(item: MenuItem) {
        if (!drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            drawerLayout.openDrawer(Gravity.RIGHT)
        } else {
            drawerLayout.closeDrawer(Gravity.RIGHT)
        }
    }

}