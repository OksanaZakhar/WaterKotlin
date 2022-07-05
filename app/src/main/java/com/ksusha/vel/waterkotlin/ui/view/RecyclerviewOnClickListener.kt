package com.ksusha.vel.waterkotlin.ui.view

interface RecyclerviewOnClickListener {

    fun recyclerviewClickDeleteMask(id: Long)

    fun recyclerviewClickAddMask(id: Long)

    fun recyclerviewChangeCount(id: Long, count: Int)
}