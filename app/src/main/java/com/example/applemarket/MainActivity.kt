package com.example.applemarket

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore.Audio
import android.provider.Settings
import android.util.Log
import android.view.View
import android.window.OnBackInvokedCallback
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.applemarket.Value.itemId
import com.example.applemarket.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private var backPressCallback:OnBackPressedCallback? = null

    private fun setBackCallback(){
        backPressCallback =
            this.onBackPressedDispatcher.addCallback(this){
                var builder = AlertDialog.Builder(this@MainActivity)
                builder.setTitle("종료")
                builder.setMessage("종료하시겠습니까?")
                builder.setIcon(R.drawable.chat)

                val listener = object: DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        when(p1){
                            DialogInterface.BUTTON_POSITIVE ->
                                finish()
                            DialogInterface.BUTTON_NEGATIVE ->
                                handleOnBackCancelled()

                        }
                    }
                }
                builder.setPositiveButton("확인",listener)
                builder.setNegativeButton("취소",listener)

                builder.show()
            }

    }

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
//    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(backPressCallback == null){
            setBackCallback()
        }
        val appleMarketItem = ItemManager.getItem().sortedBy { it.icon }
//        binding.mre1.adapter = MyAdapter(items)

        val adapter = MyAdapter(appleMarketItem)
        binding.mre1.adapter = adapter
        binding.mre1.layoutManager = LinearLayoutManager(this)

        adapter!!.itemClick = object : MyAdapter.ItemClick{
            override fun onClick(view: View, position: Int) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java).apply {
                    putExtra(itemId, position)
                }
                startActivity(intent)
            }
        }

        binding.mimgBell.setOnClickListener {
            clickBell()
        }
    }


    fun clickBell(){
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val builder: NotificationCompat.Builder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) {
                // 알림 권한이 없다면, 사용자에게 권한 요청
                val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                    putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                }
                startActivity(intent)
            }
            val channelId = "one-channel"
            val channelName = "My Channel One"
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "My Channel One Description"
                setShowBadge(true)
                val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val audioAttributes = AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build()
                setSound(uri, audioAttributes)
                enableVibration(true)
            }
            manager.createNotificationChannel(channel)

            builder = NotificationCompat.Builder(this, channelId)
        }else{
            builder = NotificationCompat.Builder(this)
        }
        builder.run {
            setSmallIcon(R.drawable.bell)
            setWhen(System.currentTimeMillis())
            setContentTitle("사과 마켓")
            setContentText("키워드 알림")
            setStyle(NotificationCompat.BigTextStyle().bigText("설정한 키워드에 대한 알림이 도착했습니다!!"))
        }

        manager.notify(11, builder.build())
    }
}
