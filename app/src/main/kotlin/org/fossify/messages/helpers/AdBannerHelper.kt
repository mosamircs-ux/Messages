package org.fossify.messages.helpers

import android.app.Activity
import android.view.View
import android.widget.FrameLayout
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import org.fossify.messages.BuildConfig

class AdBannerHelper(
    private val activity: Activity,
    private val wrapperView: View,
    private val container: FrameLayout
) {
    private var adView: AdView? = null
    private var hasLoaded = false

    fun load() {
        if (hasLoaded) return
        hasLoaded = true
        container.post {
            if (activity.isFinishing || activity.isDestroyed) return@post
            val density = activity.resources.displayMetrics.density
            val adWidthPixels = container.width.takeIf { it > 0 }
                ?: activity.resources.displayMetrics.widthPixels
            val adWidthDp = (adWidthPixels / density).toInt().coerceAtLeast(320)
            val adSize = AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidthDp)

            val banner = AdView(activity).apply {
                adUnitId = BuildConfig.ADMOB_BANNER_UNIT_ID
                setAdSize(adSize)
                adListener = object : AdListener() {
                    override fun onAdLoaded() {
                        wrapperView.visibility = View.VISIBLE
                    }

                    override fun onAdFailedToLoad(error: LoadAdError) {
                        wrapperView.visibility = View.GONE
                    }
                }
            }

            container.removeAllViews()
            container.addView(banner)
            adView = banner
            banner.loadAd(AdRequest.Builder().build())
        }
    }

    fun onResume() {
        adView?.resume()
    }

    fun onPause() {
        adView?.pause()
    }

    fun onDestroy() {
        adView?.destroy()
        adView = null
        container.removeAllViews()
    }
}
