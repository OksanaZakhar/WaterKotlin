package com.ksusha.vel.waterkotlin.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ksusha.vel.waterkotlin.R
import com.ksusha.vel.waterkotlin.databinding.BottomSheetDialogLayoutBinding
import com.ksusha.vel.waterkotlin.databinding.TopRecyclerItemBinding
import com.ksusha.vel.waterkotlin.ui.model.TopRecycler

class TopRecyclerAdapter(private val topRecyclers: List<TopRecycler>) :
    RecyclerView.Adapter<TopRecyclerAdapter.TopRecyclerViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRecyclerViewHolder {
        context = parent.context
        val topRecyclerItemBinding: TopRecyclerItemBinding = DataBindingUtil
            .inflate(
                LayoutInflater.from(context),
                R.layout.top_recycler_item, parent, false
            )
        return TopRecyclerViewHolder(topRecyclerItemBinding)

    }

    override fun onBindViewHolder(holder: TopRecyclerViewHolder, position: Int) {
        val actualPosition = position % topRecyclers.size
        val imageId = context.resources.getIdentifier(
            topRecyclers[actualPosition].topCardImage,
            "drawable",
            context.packageName
        )
        holder.topRecyclerItemBinding.topCardImage.setImageResource(imageId)


        holder.topRecyclerItemBinding.topCardImage.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(context)
            val bottomSheetDialogLayoutBinding: BottomSheetDialogLayoutBinding = DataBindingUtil
                .inflate(
                    LayoutInflater.from(context),
                    R.layout.bottom_sheet_dialog_layout, null, false
                )
            val view: View = bottomSheetDialogLayoutBinding.root
            val imageId = context.resources.getIdentifier(
                topRecyclers[actualPosition].topCardImage,
                "drawable",
                context.packageName
            )
            bottomSheetDialogLayoutBinding.imageBottomSheet.setImageResource(imageId)
            bottomSheetDialogLayoutBinding.textBottomTopic.text = topRecyclers[actualPosition].topic
            bottomSheetDialogLayoutBinding.textBottomDescription.text =
                topRecyclers[actualPosition].description
            bottomSheetDialog.setContentView(view)
            bottomSheetDialog.show()
        }


        holder.topRecyclerItemBinding.executePendingBindings();

    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    class TopRecyclerViewHolder(topRecyclerItemBinding: TopRecyclerItemBinding) :
        RecyclerView.ViewHolder(topRecyclerItemBinding.root) {
        var topRecyclerItemBinding: TopRecyclerItemBinding

        init {
            this.topRecyclerItemBinding = topRecyclerItemBinding
        }
    }


}