package com.alwin.applapangan.service

import android.app.NotificationChannel
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.os.Build
import androidx.core.app.NotificationCompat
import com.alwin.applapangan.R


/**
 * Created by Dimas Aprizawandi on 04/03/2020
 * Email : animatorist@gmail.com
 * Mobile App Developer
 */
class NotificationManager(private val mCtx: Context) {
    fun displayNotification(
        title: String?,
        body: String?,
        // image: Uri?,
        id: String?,
        type: String?
    ) {
//        val soundUri: Uri =
//            Uri.parse("android.resource://" + mCtx.packageName + "/" + R.raw.sirene)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val mNotificationManager =
                mCtx.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
            val importance = android.app.NotificationManager.IMPORTANCE_DEFAULT
            val mChannel =
                NotificationChannel("Constant.CHANNEL_ID", "Constant.CHANNEL_NAME", importance)
            mChannel.description = "Constant.CHANNEL_DESCRIPTION"
            mChannel.enableLights(true)
            mChannel.lightColor = Color.RED
            mChannel.setShowBadge(true)
            mChannel.setLockscreenVisibility(NotificationCompat.PRIORITY_HIGH)
            mChannel.enableVibration(true)
            mChannel.vibrationPattern = longArrayOf(2000, 2000, 2000, 2000)

            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()
            //mChannel.setSound(soundUri, audioAttributes)
            mNotificationManager.createNotificationChannel(mChannel)

        }

        val mBuilder =
            NotificationCompat
                .Builder(mCtx, "Constant.CHANNEL_ID")
                .setSmallIcon(R.drawable.icon_splash)
                .setContentTitle(title)

//            .setStyle(
//                NotificationCompat.BigPictureStyle()
//                    .bigPicture(Picasso.get().load(image).get())
//                    .setSummaryText(body)
//            )
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
               // .setSound(soundUri)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setOngoing(true)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
           // mBuilder.setSound(soundUri);
        }

        /*
         *  Clicking on the notification will take us to this intent
         *  Right now we are using the MainActivity as this is the only activity we have in our application
         *  But for your project you can customize it as you want
         * */
        //val resultIntent = Intent(mCtx, MenungguPembayaranActivity::class.java)
        var resultIntent: Intent
        //resultIntent = Intent(mCtx, MainActivity::class.java)

        //resultIntent.putExtra("orderId", url)
        /*
         *  Now we will create a pending intent
         *  The method getActivity is taking 4 parameters
         *  All paramters are describing themselves
         *  0 is the request code (the second parameter)
         *  We can detect this code in the activity that will open by this we can get
         *  Which notification opened the activity
         * */
     //   val pendingIntent = PendingIntent.getActivity(
            //mCtx, 0, resultIntent,
       //     PendingIntent.FLAG_UPDATE_CURRENT
      //  )
        /*
         *  Setting the pending intent to notification builder
         * */
       // mBuilder.setContentIntent(pendingIntent)
        val mNotifyMgr =
            mCtx.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
        /*
         * The first parameter is the notification id
         * better don't give a literal here (right now we are giving a int literal)
         * because using this id we can modify it later
         * */
        mNotifyMgr?.notify(0, mBuilder.build())
        //playNotificationSound()
    }

    fun playNotificationSound() {
//        try {
//            val alarmSound = Uri.parse("android.resource://" + mCtx.packageName + "/" + R.raw.sirene)
//            val r = RingtoneManager.getRingtone(mCtx, alarmSound)
//            r.play()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
    }

    fun displayNotificationNullImage(
        title: String?,
        body: String?,
        id: String?,
        type: String?
    ) {

//        val soundUri: Uri =
//            Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + mCtx.packageName + "/" + R.raw.sirene)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val mNotificationManager =
                mCtx.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
            val importance = android.app.NotificationManager.IMPORTANCE_HIGH
            val mChannel =
                NotificationChannel("Constant.CHANNEL_ID", "Constant.CHANNEL_NAME", importance)
            mChannel.description = "Constant.CHANNEL_DESCRIPTION"
            mChannel.enableLights(true)
            mChannel.lightColor = Color.RED
            mChannel.enableVibration(true)
            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()
          //  mChannel.setSound(soundUri, audioAttributes)

            mNotificationManager.createNotificationChannel(mChannel)

        }

        val mBuilder =
            NotificationCompat
                .Builder(mCtx, "Constant.CHANNEL_ID")
                .setSmallIcon(R.drawable.icon_splash)
                .setContentTitle(title)

//            .setStyle(
//                NotificationCompat.BigPictureStyle()
//                    .bigPicture(Picasso.get().load(image).get())
//                    .setSummaryText(body)
//            )
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
             //   .setSound(soundUri)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setOngoing(true)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
           // mBuilder.setSound(soundUri);
        }


        /*
         *  Clicking on the notification will take us to this intent
         *  Right now we are using the MainActivity as this is the only activity we have in our application
         *  But for your project you can customize it as you want
         * */
        //val resultIntent = Intent(mCtx, MenungguPembayaranActivity::class.java)
//        val resultIntent: Intent
//        resultIntent = Intent(mCtx, HomeActivity::class.java)
//        when (type) {
////            TYPE.ORDER -> {
////                //resultIntent = MenungguPembayaranActivity.getIntent(mCtx, id, null, null, true)
////            }
////
////            TYPE.BILLING -> {
////            //    resultIntent = Intent(mCtx, TagihanActivity::class.java)
////            }
////            else -> {
////                resultIntent = Intent(mCtx, SplashActivity::class.java)
////            }
//        }
        //resultIntent.putExtra("orderId", url)
        /*
         *  Now we will create a pending intent
         *  The method getActivity is taking 4 parameters
         *  All paramters are describing themselves
         *  0 is the request code (the second parameter)
         *  We can detect this code in the activity that will open by this we can get
         *  Which notification opened the activity
         * */
//        val pendingIntent = PendingIntent.getActivity(
//            mCtx, 0, resultIntent,
//            PendingIntent.FLAG_UPDATE_CURRENT
//        )
        /*
         *  Setting the pending intent to notification builder
         * */
       // mBuilder.setContentIntent(pendingIntent)
        val mNotifyMgr =
            mCtx.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
        /*
         * The first parameter is the notification id
         * better don't give a literal here (right now we are giving a int literal)
         * because using this id we can modify it later
         * */
        mNotifyMgr?.notify(0, mBuilder.build())
    }

    companion object {
        private var mInstance: NotificationManager? = null

        @Synchronized
        fun getInstance(context: Context): NotificationManager? {
            if (mInstance == null) {
                mInstance =
                    NotificationManager(context)
            }
            return mInstance
        }
    }

}