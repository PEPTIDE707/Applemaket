package com.example.applemarket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.applemarket.Value.itemId
import com.example.applemarket.databinding.ActivityDetailBinding
import com.example.applemarket.databinding.ActivityMainBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val items = ItemManager.getItem().sortedBy { it.icon }
        val dimg = binding.imageView
        val dName = binding.dtvName
        val dAddress = binding.dtvAddress
        val dItemName = binding.dtvItemName
        val dItemInfo = binding.dtvIteminfo
        val dMoney = binding.dtvMoney

        val itemIndex = intent.getIntExtra(itemId, -1)
        dimg.setImageResource(items.get(itemIndex).icon)
        dName.text = items[itemIndex].Names
        dAddress.text = items[itemIndex].address
        dItemName.text = items[itemIndex].imgName
        dItemInfo.text = items[itemIndex].iteminfo
        dMoney.text = items[itemIndex].Money


        val backbtn = binding.leftarrow

        backbtn.setOnClickListener {
            finish()
        }


    }
}