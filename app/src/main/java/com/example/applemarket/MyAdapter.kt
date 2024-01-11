package com.example.applemarket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.applemarket.databinding.ActivityMainBinding
import com.example.applemarket.databinding.ItemRecyclerviewBinding

class MyAdapter(val appleMarketItem: List<AppleMarketItem>):RecyclerView.Adapter<MyAdapter.Holder>() {

    interface ItemClick{
        fun onClick(view: View, position: Int)
    }
    var itemClick : ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.Holder {
        val binding = ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: MyAdapter.Holder, position: Int) {
        holder.itemView.setOnClickListener{
            itemClick?.onClick(it, position)
        }
        holder.iconImageView.setImageResource(appleMarketItem[position].icon)
        holder.itemName.text = appleMarketItem[position].imgName
        holder.mAddress.text = appleMarketItem[position].address
        holder.mMoney.text = appleMarketItem[position].Money
        holder.mchat.text = appleMarketItem[position].Chat
        holder.mlike.text = appleMarketItem[position].likes
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return appleMarketItem.size
    }

    inner class Holder(val binding: ItemRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root){
        val iconImageView = binding.mimgIcon
        val itemName = binding.mtvitemName
        val mAddress = binding.mtvaddres
        val mMoney = binding.mtvmoney
        val mchat = binding.mtvchat
        val mlike = binding.mtvlike
    }

}