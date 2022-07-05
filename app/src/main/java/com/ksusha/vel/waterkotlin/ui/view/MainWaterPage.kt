package com.ksusha.vel.waterkotlin.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ksusha.vel.waterkotlin.R
import com.ksusha.vel.waterkotlin.databinding.ActivityWaterPageBinding
import com.ksusha.vel.waterkotlin.localdata.WaterEntity
import com.ksusha.vel.waterkotlin.ui.viewmodel.MainActivityViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainWaterPage : AppCompatActivity() {

    lateinit var mainActivityViewModel: MainActivityViewModel
    lateinit var activityWaterPageBinding: ActivityWaterPageBinding
    var toBasketGo = false
    var id: Long = 0
    var count = 0
    var startCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityWaterPageBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_water_page)

        mainActivityViewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(MainActivityViewModel::class.java)

        toBasketGo = false

        id = intent.getLongExtra("waterId", 0)
        activityWaterPageBinding.waterPageImage.setImageResource(
            intent.getIntExtra(
                "waterImage",
                0
            )
        )
        activityWaterPageBinding.waterDescWaterPage.text = intent.getStringExtra("waterDesc")
        activityWaterPageBinding.priceWaterPage.text = intent.getStringExtra("waterPrice")
        count = intent.getIntExtra("waterCount", 0)
        startCount = intent.getIntExtra("waterStartCount", 0)
        activityWaterPageBinding.countWaterPage.text = "" + count

        activityWaterPageBinding.waterButtonMinus.setOnClickListener {
            if (count - 1 >= startCount) {
                Observable.create<Any> {
                    val waterEntity: WaterEntity = mainActivityViewModel.getWaterEntity(id)
                    waterEntity.count = waterEntity.count - 1
                    mainActivityViewModel.updateWaterEntity(waterEntity)
                }.subscribeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe()
                count--
                activityWaterPageBinding.countWaterPage.text = count.toString()
            }
            if (count == startCount) {
                Observable.create<Any> {
                    val waterEntity: WaterEntity = mainActivityViewModel.getWaterEntity(id)
                    waterEntity.maskClickable = true
                    waterEntity.maskVisible = View.VISIBLE
                    mainActivityViewModel.updateWaterEntity(waterEntity)
                }.subscribeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            }
        }

        activityWaterPageBinding.waterButtonPlus.setOnClickListener {
            Observable.create<Any> {
                val waterEntity = mainActivityViewModel.getWaterEntity(id)
                waterEntity.count = waterEntity.count + 1
                mainActivityViewModel.updateWaterEntity(waterEntity)
            }.subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe()
            count++
            activityWaterPageBinding.countWaterPage.text = count.toString()

        }

        activityWaterPageBinding.buttonHomeWaterPage.setOnClickListener { view -> goToHome(view) }
        activityWaterPageBinding.buttonBasketWaterPage.setOnClickListener { view -> goToBasket(view) }


    }

    fun goToHome(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun goToBasket(view: View) {
        MainActivity().toBasketGo = true
        val intent1 = Intent(this, MainActivity::class.java)
        startActivity(intent1)
    }

}















