package com.ksusha.vel.waterkotlin.ui.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.navigation.NavigationView
import com.ksusha.vel.waterkotlin.R
import com.ksusha.vel.waterkotlin.data.DataForFragment
import com.ksusha.vel.waterkotlin.databinding.ActivityMainBinding
import com.ksusha.vel.waterkotlin.localdata.WaterEntity
import com.ksusha.vel.waterkotlin.ui.fragment.BasketFragment
import com.ksusha.vel.waterkotlin.ui.fragment.MainFragment
import com.ksusha.vel.waterkotlin.ui.fragment.StockFragment
import com.ksusha.vel.waterkotlin.ui.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity(), View.OnClickListener,
    NavigationView.OnNavigationItemSelectedListener {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var mainActivityViewModel: MainActivityViewModel
    lateinit var waters: List<WaterEntity>
    var toBasketGo: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(activityMainBinding.root)

        mainActivityViewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(MainActivityViewModel::class.java)


        mainActivityViewModel.getWaterList().observe(
            this
        ) { waterEntities ->
            if (waterEntities.size == 0) {
                Thread {
                    for (w in DataForFragment().waterListFromAPI) {
                        mainActivityViewModel.addWaterEntityDB(
                            WaterEntity(
                                0,
                                w.id,
                                w.img,
                                w.description,
                                w.prise,
                                w.count,
                                w.startCount,
                                w.maskClicable,
                                w.maskVisible
                            )
                        )
                    }
                }.start()
            }
        }

        // это мы из данных апи переписываем в БД ВСЕ!!!!

        activityMainBinding.buttonBasket.setColorFilter(Color.parseColor("#686868"))
        activityMainBinding.txtBasket.setTextColor(Color.parseColor("#686868"))
        activityMainBinding.buttonStock.setColorFilter(Color.parseColor("#686868"))
        activityMainBinding.txtStock.setTextColor(Color.parseColor("#686868"))
        activityMainBinding.buttonMain.setColorFilter(Color.parseColor("#005080"))
        activityMainBinding.txtMain.setTextColor(Color.parseColor("#005080"))


        if (MainWaterPage().toBasketGo) {
            val fragment: Fragment = BasketFragment()
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction().setReorderingAllowed(true)
            fragmentTransaction.replace(R.id.fragMain, fragment)
            fragmentTransaction.commit()
        }

        activityMainBinding.navigationView.setNavigationItemSelectedListener(this)

        activityMainBinding.buttonOpen.setOnClickListener {
            if (activityMainBinding.drawerLayout.isDrawerOpen(GravityCompat.END)) {
                activityMainBinding.drawerLayout.closeDrawer(GravityCompat.END)
            }
            activityMainBinding.drawerLayout.openDrawer(GravityCompat.END)
        }

        mainActivityViewModel.getWaterList().observe(
            this
        ) { waterEntities ->
            waters = waterEntities as List<WaterEntity>
            val waterBasket: MutableList<WaterEntity> =
                ArrayList()
            var count = 0
            for (water in waters) {
                if (water.maskVisible == View.GONE) {
                    waterBasket.add(water)
                    count += water.count
                }
            }
            if (count > 0) {
                activityMainBinding.allCountBasket.text = count.toString()
                activityMainBinding.circleAllCountBasket.visibility = View.VISIBLE
            } else {
                activityMainBinding.allCountBasket.text = ""
                activityMainBinding.circleAllCountBasket.visibility = View.GONE
            }
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.oneMenu -> {
                val intentAuth = Intent(this@MainActivity, Authentication::class.java)
                startActivity(intentAuth)
            }
            R.id.twoMenu -> {
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    "Check out the App at: https://play.google.com/store/apps/details?id=$packageName"
                )
                sendIntent.type = "text/plain"
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }
            R.id.threeMenu -> Toast.makeText(this@MainActivity, "Інформація", Toast.LENGTH_SHORT)
                .show()
            R.id.fourMenu -> Toast.makeText(
                this@MainActivity,
                "Зв’яжіться з нами",
                Toast.LENGTH_SHORT
            ).show()
            R.id.fiveMenu -> Toast.makeText(this@MainActivity, "Оцініть нас", Toast.LENGTH_SHORT)
                .show()
            R.id.sixMenu -> Toast.makeText(
                this@MainActivity,
                "Скарга директору",
                Toast.LENGTH_SHORT
            ).show()
            else -> Toast.makeText(this@MainActivity, "No", Toast.LENGTH_SHORT).show()
        }
        return false
    }

    fun change(view: View) {
        var fragment: Fragment? = null
        when (view.id) {
            R.id.buttonBasket -> {
                fragment = BasketFragment()
                activityMainBinding.buttonBasket.setColorFilter(Color.parseColor("#005080"))
                activityMainBinding.txtBasket.setTextColor(Color.parseColor("#005080"))
                activityMainBinding.buttonStock.setColorFilter(Color.parseColor("#686868"))
                activityMainBinding.txtStock.setTextColor(Color.parseColor("#686868"))
                activityMainBinding.buttonMain.setColorFilter(Color.parseColor("#686868"))
                activityMainBinding.txtMain.setTextColor(Color.parseColor("#686868"))
            }
            R.id.buttonStock -> {
                fragment = StockFragment()
                activityMainBinding.buttonBasket.setColorFilter(Color.parseColor("#686868"))
                activityMainBinding.txtBasket.setTextColor(Color.parseColor("#686868"))
                activityMainBinding.buttonStock.setColorFilter(Color.parseColor("#005080"))
                activityMainBinding.txtStock.setTextColor(Color.parseColor("#005080"))
                activityMainBinding.buttonMain.setColorFilter(Color.parseColor("#686868"))
                activityMainBinding.txtMain.setTextColor(Color.parseColor("#686868"))
            }
            R.id.buttonMain -> {
                fragment = MainFragment()
                activityMainBinding.buttonBasket.setColorFilter(Color.parseColor("#686868"))
                activityMainBinding.txtBasket.setTextColor(Color.parseColor("#686868"))
                activityMainBinding.buttonStock.setColorFilter(Color.parseColor("#686868"))
                activityMainBinding.txtStock.setTextColor(Color.parseColor("#686868"))
                activityMainBinding.buttonMain.setColorFilter(Color.parseColor("#005080"))
                activityMainBinding.txtMain.setTextColor(Color.parseColor("#005080"))
            }
            else -> {}
        }
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragMain, fragment!!)
        fragmentTransaction.commit()
    }

    fun onClickHistory(view: View) {
        when (view.id) {
            R.id.buttonHistory -> {
                val intent0 = Intent(this, HistoryActivity::class.java)
                startActivity(intent0)
            }
            else -> {}
        }
    }

    override fun onClick(view: View) {

    }

}















