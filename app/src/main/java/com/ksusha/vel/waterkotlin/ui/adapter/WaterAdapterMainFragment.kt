package com.ksusha.vel.waterkotlin.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ksusha.vel.waterkotlin.R
import com.ksusha.vel.waterkotlin.databinding.WaterItemBinding
import com.ksusha.vel.waterkotlin.localdata.WaterEntity
import com.ksusha.vel.waterkotlin.ui.view.MainWaterPage
import com.ksusha.vel.waterkotlin.ui.view.RecyclerviewOnClickListener

class WaterAdapterMainFragment(
    private val listener: RecyclerviewOnClickListener,
    private val waters: List<WaterEntity>
) :
    RecyclerView.Adapter<WaterAdapterMainFragment.WaterViewHolder>() {

    lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WaterViewHolder {
        context = parent.context
        val waterItemBinding: WaterItemBinding = DataBindingUtil
            .inflate(
                LayoutInflater.from(context),
                R.layout.water_item, parent, false
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
            String.format("Ціна: %s,00грн", waters[position].prise.toString())
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
        holder.waterItemBinding.waterImage.setOnClickListener {
            val intent = Intent(context, MainWaterPage::class.java)
            intent.putExtra("waterImage", imageId)
            intent.putExtra("waterDesc", waters[position].description)
            intent.putExtra(
                "waterPrice", String.format(
                    "Ціна: %s,00грн", waters[position].prise
                )
            )
            intent.putExtra("waterCount", waters[position].count)
            intent.putExtra("waterStartCount", waters[position].startCount)
            intent.putExtra("waterId", waters[position].id)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return waters.size
    }


    class WaterViewHolder(waterItemBinding: WaterItemBinding) :
        RecyclerView.ViewHolder(waterItemBinding.root) {
        var waterItemBinding: WaterItemBinding

        init {
            this.waterItemBinding = waterItemBinding
        }
    }

}