package com.example.admin.mvpinitialprojectsetupkotlin.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.example.admin.mvpinitialprojectsetupkotlin.R
import com.example.admin.mvpinitialprojectsetupkotlin.data.response.DataItem

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife


class NameListAdapter(private val context: Context) : RecyclerView.Adapter<NameListAdapter.ViewHolder>() {


    private var nameList: List<DataItem>? = null

    init {
        nameList = ArrayList()

    }

    fun setNameList(itemList: List<DataItem>?) {
        if (itemList == null) {
            return
        }

        nameList = itemList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_name_list, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindDataToView(nameList!![position])
    }

    override fun getItemCount(): Int {

        return nameList!!.size
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        @BindView(R.id.user_image)
        lateinit var userImage: ImageView
        @BindView(R.id.firstname)
        lateinit var firstname: TextView
        @BindView(R.id.lastname)
        lateinit var lastname: TextView

        init {
            ButterKnife.bind(this, v)
        }

        fun bindDataToView(item: DataItem) {
            firstname.text = item.firstName
            lastname.text = item.lastName
        }

    }
}