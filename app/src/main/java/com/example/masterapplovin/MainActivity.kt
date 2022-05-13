package com.example.masterapplovin

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.view.ViewGroup
import android.widget.FrameLayout
import com.applovin.mediation.*
import com.applovin.mediation.ads.MaxAdView
import com.applovin.mediation.ads.MaxInterstitialAd
import com.applovin.mediation.nativeAds.MaxNativeAdListener
import com.applovin.mediation.nativeAds.MaxNativeAdLoader
import com.applovin.mediation.nativeAds.MaxNativeAdView
import com.applovin.sdk.AppLovinSdk
import com.applovin.sdk.AppLovinSdkConfiguration
import com.applovin.sdk.AppLovinSdkUtils
import com.example.masterapplovin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAppLovinAds()

        binding.btnBanner.setOnClickListener {
            initAppLovinBanner()
        }

        binding.btnNative.setOnClickListener {
            initAppLovinNative()
        }

        binding.btnInterstitial.setOnClickListener {
            showAppLovinInterstitial()
        }
    }

    fun initAppLovinAds(){
        binding.txtLog.append("\n AppLovin Ads Init")
        AppLovinSdk.getInstance(this).mediationProvider = "max"
        AppLovinSdk.getInstance(this).initializeSdk {
            binding.txtLog.append("\n AppLovin Ads Initialized")
            initAppLovinInterstitial()
        }
    }

    fun initAppLovinBanner(){
        binding.lyBannerAds.removeAllViews()
        binding.txtLog.append("\n AppLovin banner init")
        val adView= MaxAdView("YOUR_AD_UNIT_ID", this)
        adView.setListener(object : MaxAdViewAdListener{
            override fun onAdLoaded(ad: MaxAd?) {
                binding.txtLog.append("\n AppLovin Banner onAdLoaded")
            }

            override fun onAdDisplayed(ad: MaxAd?) {
                binding.txtLog.append("\n AppLovin Banner onAdDisplayed")
            }

            override fun onAdHidden(ad: MaxAd?) {
                binding.txtLog.append("\n AppLovin Banner onAdHidden")
            }

            override fun onAdClicked(ad: MaxAd?) {
                binding.txtLog.append("\n AppLovin Banner onAdClicked")
            }

            override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) {
                binding.txtLog.append("\n AppLovin Banner onAdLoadFailed "+error?.message)
            }

            override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {
                binding.txtLog.append("\n AppLovin Banner onAdDisplayFailed "+error?.message)
            }

            override fun onAdExpanded(ad: MaxAd?) {
                binding.txtLog.append("\n AppLovin Banner onAdExpanded")
            }

            override fun onAdCollapsed(ad: MaxAd?) {
                binding.txtLog.append("\n AppLovin Banner onAdCollapsed")
            }

        })
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val heightDp = MaxAdFormat.BANNER.getAdaptiveSize(this).height
        val heightPx = AppLovinSdkUtils.dpToPx(this, heightDp)
        adView.layoutParams = FrameLayout.LayoutParams(width, heightPx)
        adView.setExtraParameter("adaptive_banner", "true")
        binding.lyBannerAds.addView(adView)
        adView.loadAd()
    }

    fun initAppLovinNative(){
        binding.lyBannerAds.removeAllViews()
        binding.txtLog.append("\n AppLovin native init")

        var nativeAdLoader= MaxNativeAdLoader( "YOUR_AD_UNIT_ID", this )
        nativeAdLoader.setNativeAdListener(object : MaxNativeAdListener() {

            override fun onNativeAdLoaded(nativeAdView: MaxNativeAdView?, ad: MaxAd)
            {
                binding.txtLog.append("\n AppLovin onNativeAdLoaded")
                binding.lyBannerAds.addView(nativeAdView)
            }

            override fun onNativeAdLoadFailed(adUnitId: String, error: MaxError)
            {
                binding.txtLog.append("\n AppLovin onNativeAdLoadFailed")
            }

            override fun onNativeAdClicked(ad: MaxAd)
            {
                binding.txtLog.append("\n AppLovin onNativeAdClicked")
            }
        })
        nativeAdLoader.loadAd()
    }

    private lateinit var interstitialAd: MaxInterstitialAd
    fun initAppLovinInterstitial(){
        interstitialAd = MaxInterstitialAd( "YOUR_AD_UNIT_ID", this )
        interstitialAd.setListener(object : MaxAdListener{
            override fun onAdLoaded(ad: MaxAd?) {
                binding.txtLog.append("\n AppLovin interstitial onAdLoaded")
            }

            override fun onAdDisplayed(ad: MaxAd?) {
                binding.txtLog.append("\n AppLovin interstitial onAdDisplayed")
            }

            override fun onAdHidden(ad: MaxAd?) {
                binding.txtLog.append("\n AppLovin interstitial onAdHidden")
                interstitialAd.loadAd()
            }

            override fun onAdClicked(ad: MaxAd?) {
                binding.txtLog.append("\n AppLovin interstitial onAdClicked")
            }

            override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) {
                binding.txtLog.append("\n AppLovin interstitial onAdLoadFailed "+error?.message)
            }

            override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {
                binding.txtLog.append("\n AppLovin interstitial onAdDisplayFailed"+error?.message)
            }
        })
        interstitialAd.loadAd()
    }

    fun showAppLovinInterstitial(){
        if (interstitialAd.isReady) {
            interstitialAd.showAd()
            binding.txtLog.append("\n Interstitial ads Show")
        }else binding.txtLog.append("\n Interstitial ads not ready")
    }
}