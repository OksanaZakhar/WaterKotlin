package com.ksusha.vel.waterkotlin.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ksusha.vel.waterkotlin.R
import com.ksusha.vel.waterkotlin.databinding.FragmentBasketBinding
import com.ksusha.vel.waterkotlin.localdata.WaterEntity
import com.ksusha.vel.waterkotlin.ui.adapter.WaterAdapterBasketFragment
import com.ksusha.vel.waterkotlin.ui.view.RecyclerviewOnClickListener
import com.ksusha.vel.waterkotlin.ui.viewmodel.MainActivityViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class BasketFragment : Fragment(), RecyclerviewOnClickListener {

    lateinit var waterAdapter: WaterAdapterBasketFragment
    var addresses = arrayOf("ksusha.vel.andrst@gmail.com")
    var subject = "Заказ воды"
    lateinit var waters: List<WaterEntity>
    lateinit var waterBasket: List<WaterEntity>
    lateinit var mainActivityViewModel: MainActivityViewModel
    lateinit var fragmentBasketBinding: FragmentBasketBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragmentBasketBinding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_basket, container, false)

        var view = fragmentBasketBinding.root

        mainActivityViewModel =
            ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]

        mainActivityViewModel.getWaterList().observe(
            viewLifecycleOwner
        ) { waterEntities ->
            waters = waterEntities as List<WaterEntity>
            waterBasket = ArrayList()
            var sumBasket = 0
            for (water in waters) {
                if (water.maskVisible == View.GONE) {
                    (waterBasket as ArrayList<WaterEntity>).add(water)
                    sumBasket += water.count * water.prise
                }
            }
            setWaterRecycler(waterBasket)
            fragmentBasketBinding.allSumBasket.text = "$sumBasket,00 грн"
            fragmentBasketBinding.orderDeal.isEnabled = sumBasket > 0
        }


        fragmentBasketBinding.orderDeal.setOnClickListener { sendOrder() }

        return view
    }

    private fun setWaterRecycler(waterList: List<WaterEntity>) {
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        fragmentBasketBinding.waterRecycler.layoutManager = layoutManager
        waterAdapter = WaterAdapterBasketFragment(this, waterList)
        fragmentBasketBinding.waterRecycler.adapter = waterAdapter
    }

    private fun sendOrder() {
        var sum = 0
        var order = ""
        order = order + "\n" + "\n"
        for (w in waterBasket) {
            order = order + " " +
                    w.description + " - " +
                    "Ціна - " + w.prise + " " +
                    "Кількість - " + w.count + " " +
                    "Сума - " + w.prise * w.count + ";" + "\n";
            sum += w.prise * w.count
        }
        order = order + "Загальна сума замовлення - " + sum + ",00 грн"

        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:") // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses)
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, order)
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(context, "Couldn't find an email app and account", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun recyclerviewClickDeleteMask(id: Long) {

        Observable.create<Any> {
            val waterEntity = mainActivityViewModel.getWaterEntity(id)
            waterEntity.maskClickable = false
            waterEntity.maskVisible = View.GONE
            mainActivityViewModel.updateWaterEntity(waterEntity)
        }.subscribeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    override fun recyclerviewClickAddMask(id: Long) {

        Observable.create<Any> {
            val waterEntity = mainActivityViewModel.getWaterEntity(id)
            waterEntity.maskClickable = true
            waterEntity.maskVisible = View.VISIBLE
            mainActivityViewModel.updateWaterEntity(waterEntity)
        }.subscribeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    override fun recyclerviewChangeCount(id: Long, count: Int) {

        Observable.create<Any> {
            val waterEntity = mainActivityViewModel.getWaterEntity(id)
            waterEntity.count = count
            mainActivityViewModel.updateWaterEntity(waterEntity)
        }.subscribeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

}



















