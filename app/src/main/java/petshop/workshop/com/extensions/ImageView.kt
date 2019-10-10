package petshop.workshop.com.extensions

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.net.URL
import java.util.concurrent.Callable

@SuppressLint("CheckResult")
fun ImageView.loadFromUrl(link: String) {
    val bitmapCallback = Callable<Bitmap> {
        val url = URL(link)
        return@Callable BitmapFactory.decodeStream(url.openConnection().getInputStream())
    }

    Single.fromCallable(bitmapCallback)
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            this.setImageBitmap(it)
        }, {
            Timber.e("Load image failed")
            Timber.e(it)
        })
}