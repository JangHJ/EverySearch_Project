package com.example.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.everysearch1.R
import kotlinx.android.synthetic.main.searchresultitemfix.view.*

class SearchAdapter(private var items: ArrayList<Search>) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    companion object {
        var callNum: String = "0"
    }

    lateinit var ctx: Context

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val listener = View.OnClickListener { it ->
        }
        holder.apply {
            bind(listener, item)
            itemView.tag = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.searchresultitemfix, parent, false)
        ctx = parent.context
        return ViewHolder(inflatedView)
    }

    fun setItems(list: ArrayList<Search>) {
        this.items = list
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        private var callIcon: Button = v.callIcon

        init {
            // 전화 아이콘 클릭 이벤트 처리
            callIcon.setOnClickListener {
                val phoneNumber = if (view.number.text.startsWith("02")) {
                    view.number.text.toString()
                } else {
                    "02-970-${view.number.text}"
                }
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
                ctx.startActivity(intent)
            }
        }

        fun bind(listener: View.OnClickListener, item: Search) {
            view.agency.text = item.agency
            view.department.text = item.department
            view.name.text = item.name
            view.task.text = item.task
            if (view.number.text.startsWith("02")) {
                view.number.text = item.number
            } else {
                view.number.text = "02-970-${item.number}"
            }
            view.setOnClickListener(listener)
        }
    }
}
