package com.ksusha.vel.waterkotlin.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.ksusha.vel.waterkotlin.R
import com.ksusha.vel.waterkotlin.data.DataForFragment
import com.ksusha.vel.waterkotlin.databinding.FragmentMainBinding
import com.ksusha.vel.waterkotlin.localdata.WaterEntity
import com.ksusha.vel.waterkotlin.ui.adapter.TopRecyclerAdapter
import com.ksusha.vel.waterkotlin.ui.adapter.WaterAdapterMainFragment
import com.ksusha.vel.waterkotlin.ui.model.TopRecycler
import com.ksusha.vel.waterkotlin.ui.view.Authentication
import com.ksusha.vel.waterkotlin.ui.view.RecyclerviewOnClickListener
import com.ksusha.vel.waterkotlin.ui.view.ScaleLayoutManager
import com.ksusha.vel.waterkotlin.ui.viewmodel.MainActivityViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

class MainFragment : Fragment(), RecyclerviewOnClickListener {

    lateinit var waterAdapter: WaterAdapterMainFragment
    lateinit var topRecyclerAdapter: TopRecyclerAdapter
    lateinit var waters: List<WaterEntity>
    var topPosition = 0
    lateinit var layoutManager: ScaleLayoutManager
    lateinit var mainActivityViewModel: MainActivityViewModel
    lateinit var fragmentMainBinding: FragmentMainBinding
    var timer: Timer? = null
    var timerTask: TimerTask? = null
    var position = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragmentMainBinding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_main, container, false)
        val view = fragmentMainBinding.root

        mainActivityViewModel =
            ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]

        mainActivityViewModel.getFilterList("mf").observe(
            viewLifecycleOwner
        ) { waterEntities ->
            waters = waterEntities as MutableList<WaterEntity>
            setWaterRecycler(waters)
            fragmentMainBinding.waterRecycler.scrollToPosition(topPosition)
        }

        fragmentMainBinding.imageButtonWater.setOnClickListener {
            fragmentMainBinding.waterRecycler.scrollToPosition(0)
            changeButtonWater()
        }
        fragmentMainBinding.imageButtonAxes.setOnClickListener {
            fragmentMainBinding.waterRecycler.scrollToPosition(3)
            changeButtonAxes()
        }
        fragmentMainBinding.imageButtonCool.setOnClickListener {
            fragmentMainBinding.waterRecycler.scrollToPosition(6)
            changeButtonCool()
        }

        setTopRecycler(DataForFragment().topRecyclerList)

        fragmentMainBinding.imageAuthentication.setOnClickListener {
            val intentAuth = Intent(context, Authentication::class.java)
            startActivity(intentAuth)
        }

        return view
    }


    private fun setWaterRecycler(waterList: List<WaterEntity>) {
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        fragmentMainBinding.waterRecycler.layoutManager = layoutManager
        waterAdapter = WaterAdapterMainFragment(this, waterList)
        fragmentMainBinding.waterRecycler.adapter = waterAdapter
        fragmentMainBinding.waterRecycler.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lm = fragmentMainBinding.waterRecycler.layoutManager as LinearLayoutManager
                if (lm.findFirstVisibleItemPosition() == 0) {
                    changeButtonWater()
                }
                if (lm.findFirstVisibleItemPosition() == 3) {
                    changeButtonAxes()
                }
                if (lm.findFirstVisibleItemPosition() == 6) {
                    changeButtonCool()
                }
            }
        })
    }

    private fun changeButtonWater() {
        fragmentMainBinding.imageButtonWater.background =
            requireContext().resources.getDrawable(
                R.drawable.ic_category_blue,
                requireContext().theme
            )
        fragmentMainBinding.imageButtonAxes.background =
            requireContext().resources.getDrawable(R.drawable.ic_category, requireContext().theme)
        fragmentMainBinding.imageButtonCool.background =
            requireContext().resources.getDrawable(R.drawable.ic_category, requireContext().theme)
    }

    private fun changeButtonAxes() {
        fragmentMainBinding.imageButtonWater.background =
            requireContext().resources.getDrawable(R.drawable.ic_category, requireContext().theme)
        fragmentMainBinding.imageButtonAxes.background =
            requireContext().resources.getDrawable(
                R.drawable.ic_category_blue,
                requireContext().theme
            )
        fragmentMainBinding.imageButtonCool.background =
            requireContext().resources.getDrawable(R.drawable.ic_category, requireContext().theme)
    }

    private fun changeButtonCool() {
        fragmentMainBinding.imageButtonWater.background =
            requireContext().resources.getDrawable(R.drawable.ic_category, requireContext().theme)
        fragmentMainBinding.imageButtonAxes.background =
            requireContext().resources.getDrawable(R.drawable.ic_category, requireContext().theme)
        fragmentMainBinding.imageButtonCool.background =
            requireContext().resources.getDrawable(
                R.drawable.ic_category_blue,
                requireContext().theme
            )
    }

    override fun recyclerviewClickDeleteMask(id: Long) {
        topPosition =
            (fragmentMainBinding.waterRecycler.layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()

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
        topPosition =
            (fragmentMainBinding.waterRecycler.layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()

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
        topPosition =
            (fragmentMainBinding.waterRecycler.layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()

        Observable.create<Any> {
            val waterEntity = mainActivityViewModel.getWaterEntity(id)
            waterEntity.count = count
            mainActivityViewModel.updateWaterEntity(waterEntity)
        }.subscribeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    private fun setTopRecycler(topRecyclerList: List<TopRecycler>) {
        layoutManager = ScaleLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        fragmentMainBinding.topRecycler.layoutManager = layoutManager
        topRecyclerAdapter = TopRecyclerAdapter(topRecyclerList)
        fragmentMainBinding.topRecycler.adapter = topRecyclerAdapter
        if (topRecyclerList != null) {
            fragmentMainBinding.topRecycler.scrollToPosition(Int.MAX_VALUE / 2)
        }
        val helper: SnapHelper = PagerSnapHelper()
        helper.attachToRecyclerView(fragmentMainBinding.topRecycler)
        fragmentMainBinding.topRecycler.smoothScrollBy(1000, 0)
        fragmentMainBinding.topRecycler.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == 1) {
                    stopAutoScrollTopRecycler()
                } else if (newState == 0) {
                    position = layoutManager.findFirstCompletelyVisibleItemPosition()
                    runAutoScrollTopRecycler()
                }
            }
        })
    }

    private fun runAutoScrollTopRecycler() {
        if (timer == null && timerTask == null) {
            timer = Timer()
            timerTask = object : TimerTask() {
                override fun run() {
                    if (position != Int.MAX_VALUE) {
                        position++
                        fragmentMainBinding.topRecycler.smoothScrollToPosition(position)
                    } else {
                        fragmentMainBinding.topRecycler.scrollToPosition(Int.MAX_VALUE / 2)
                        fragmentMainBinding.topRecycler.smoothScrollBy(1000, 0)
                    }
                }
            }
            timer!!.schedule(timerTask, 4000, 4000)
        }
    }


    private fun stopAutoScrollTopRecycler() {
        if (timer != null && timerTask != null) {
            timerTask!!.cancel()
            timer!!.cancel()
            timer = null
            timerTask = null
            position = layoutManager.findFirstCompletelyVisibleItemPosition()
        }
    }

    override fun onResume() {
        super.onResume()
        runAutoScrollTopRecycler()
    }

    override fun onPause() {
        super.onPause()
        stopAutoScrollTopRecycler()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

}