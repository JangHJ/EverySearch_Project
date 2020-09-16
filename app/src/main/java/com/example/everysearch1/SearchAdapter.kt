package com.example.main


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.telecom.Call
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.everysearch1.R
import com.example.everysearch1.searchResult
import kotlinx.android.synthetic.main.searchresultitemfix.view.*


class SearchAdapter(private var items: ArrayList<Search>): RecyclerView.Adapter<SearchAdapter.ViewHolder>(){
companion object{
    var callNum:String="0"
}

        override fun getItemCount() = items.size
    lateinit var ctx:Context



        override fun onBindViewHolder(holder: SearchAdapter.ViewHolder, position: Int) {
            val item = items[position]
            val listener = View.OnClickListener {it ->
            }
            holder.apply {
                bind(listener, item)
                itemView.tag = item
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
                SearchAdapter.ViewHolder {
            val inflatedView = LayoutInflater.from(parent.context)
                .inflate(R.layout.searchresultitemfix, parent, false)
            ctx = parent.getContext()
            return SearchAdapter.ViewHolder(inflatedView)
        }

    fun setItems(list: ArrayList<Search>) {
        this.items=list
    }
    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView),View.OnClickListener{

        protected  var callIcon:Button

        init{
            callIcon=itemView.findViewById(R.id.callIcon) as Button

           // callIcon.setTag(R.string.callIcon,itemView)
            callIcon.setOnClickListener(this)
        }

        override fun onClick(v: View?) {//Intent(Intent.ACTION_DIAL, Uri.parse(callNum))

            val intent = Intent(Intent.ACTION_DIAL,Uri.parse(callNum))
            //startactivity(ctx,intent) 왜 안되냐..
        }

    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            private var view: View = v

        fun bind(listener: View.OnClickListener, item: Search) {
                view.agency.text=item.agency
                view.department.text=item.department
                view.name.text=item.name
                view.task.text=item.task
                if(view.number.text.startsWith("02")){
                    view.number.text=item.number
                    callNum=item.number


                }else{
                    view.number.text="02-970"+item.number
                    callNum="02970"+item.number

                }

                view.setOnClickListener(listener)

            }

        }

}
