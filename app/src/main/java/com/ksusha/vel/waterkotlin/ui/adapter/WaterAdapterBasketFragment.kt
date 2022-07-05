package com.ksusha.vel.waterkotlin.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ksusha.vel.waterkotlin.R
import com.ksusha.vel.waterkotlin.databinding.WaterItemBasketBinding
import com.ksusha.vel.waterkotlin.localdata.WaterEntity
import com.ksusha.vel.waterkotlin.ui.view.RecyclerviewOnClickListener

class WaterAdapterBasketFragment(
    private val listener: RecyclerviewOnClickListener,
    private val waters: List<WaterEntity>
) :
    RecyclerView.Adapter<WaterAdapterBasketFragment.WaterViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WaterViewHolder {
        context = parent.context
        val waterItemBinding: WaterItemBasketBinding = DataBindingUtil
            .inflate(
                LayoutInflater.from(context),
                R.layout.water_item_basket, parent, false
            )

        return WaterViewHolder(waterItemBinding)
    }

    override fun onBindViewHolder(holder: WaterViewHolder, position: Int) {
        val imageId = context.resources.getIdentifier(
            waters[position].img,
            "drawable",
            context.packageName
        )
        holder.waterItemBinding.waterImage.setImageResource(imageId)

        holder.waterItemBinding.waterDesc.text = waters[position].description
        holder.waterItemBinding.waterPrise.text =
            String.format("Ціна: %s,00грн", waters[position].prise)
        holder.waterItemBinding.waterCount.text = waters[position].count.toString()
        holder.waterItemBinding.gradientEllipse.isClickable = waters[position].maskClickable
        holder.waterItemBinding.gradientEllipse.visibility = waters[position].maskVisible
        holder.waterItemBinding.gradientBasket.isClickable = waters[position].maskClickable
        holder.waterItemBinding.gradientBasket.visibility = waters[position].maskVisible

        holder.waterItemBinding.gradientEllipse.setOnClickListener {
            listener.recyclerviewClickDeleteMask(
                waters[position].id
            )
        }

        holder.waterItemBinding.waterButtonPlus.setOnClickListener {
            listener.recyclerviewChangeCount(
                waters[position].id,
                waters[position].count + 1
            )
        }

        holder.waterItemBinding.waterButtonMinus.setOnClickListener {
            if (waters[position].count - 1 >= waters[position].startCount) {
                listener.recyclerviewChangeCount(
                    waters[position].id,
                    waters[position].count - 1
                )
            }
            if (waters[position].count == waters[position].startCount) {
                listener.recyclerviewClickAddMask(waters[position].id)
            }
        }

        holder.waterItemBinding.waterDelete.setOnClickListener {
            listener.recyclerviewClickAddMask(waters[position].id)
            listener.recyclerviewChangeCount(
                waters[position].id,
                waters[position].startCount
            )
        }
    }

    override fun getItemCount(): Int {
        return waters.size
    }


    class WaterViewHolder(waterItemBinding: WaterItemBasketBinding) :
        RecyclerView.ViewHolder(waterItemBinding.root) {
        var waterItemBinding: WaterItemBasketBinding

        init {
            this.waterItemBinding = waterItemBinding
        }
    }


}