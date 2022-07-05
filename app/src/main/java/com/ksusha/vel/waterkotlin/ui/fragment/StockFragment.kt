package com.ksusha.vel.waterkotlin.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ksusha.vel.waterkotlin.R
import com.ksusha.vel.waterkotlin.databinding.FragmentStockBinding
import com.ksusha.vel.waterkotlin.localdata.WaterEntity
import com.ksusha.vel.waterkotlin.ui.adapter.WaterAdapterStockFragment
import com.ksusha.vel.waterkotlin.ui.view.RecyclerviewOnClickListener
import com.ksusha.vel.waterkotlin.ui.viewmodel.MainActivityViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class StockFragment : Fragment(), RecyclerviewOnClickListener {
    lateinit var waterAdapter: WaterAdapterStockFragment
    lateinit var mainActivityViewModel: MainActivityViewModel
    lateinit var waters: List<WaterEntity>
    lateinit var fragmentStockBinding: FragmentStockBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentStockBinding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_stock, container, false)
        val view = fragmentStockBinding.root

        mainActivityViewModel =
            ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]

        mainActivityViewModel.getFilterList("sf").observe(
            viewLifecycleOwner
        ) { waterEntities ->
            waters = waterEntities as List<WaterEntity>
            setWaterRecycler(waters)
        }

        return view
    }

    private fun setWaterRecycler(waterList: List<WaterEntity>) {
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        fragmentStockBinding.waterRecycler.layoutManager = layoutManager
        waterAdapter = WaterAdapterStockFragment(this, waterList)
        fragmentStockBinding.waterRecycler.setAdapter(waterAdapter)
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