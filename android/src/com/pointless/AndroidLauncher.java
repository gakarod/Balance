package com.pointless;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.scenes.scene2d.actions.RelativeTemporalAction;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.pointless.FlappyImpact;

import java.io.IOException;
import java.util.UUID;

public class AndroidLauncher extends AndroidApplication implements AdHandler {        // STARTER CLASS
	private static final String TAG = "AndroidLauncher";

	private final int SHOW_ADS = 1;

	private final int HIDE_ADS = 0;

	protected AdView adView;

	AdvertisingIdClient.Info idInfo = null;



	Handler handler = new Handler(){



		@Override

		public void handleMessage(Message msg) {

		}

	};



	@Override

	protected void onCreate (Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);



		RelativeLayout layout = new RelativeLayout(this);



		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		View gameView = initializeForView(new FlappyImpact(), config);


		layout.addView(gameView);



		adView = new AdView(this);
		adView.setAdListener(new AdListener() {

			@Override

			public void onAdLoaded() {

				int visibility = adView.getVisibility();

				adView.setVisibility(AdView.GONE);

				adView.setVisibility(visibility);

				Log.i(TAG, "Ad Loaded...");

			}

		});

		adView.setAdSize(AdSize.SMART_BANNER);

		//http://www.google.com/admob

		adView.setAdUnitId("ca-app-pub-6306276344364522/8102443196");



		AdRequest.Builder builder = new AdRequest.Builder();

		//run once before uncommenting the following line. Get TEST device ID from the logcat logs.


		//builder.addTestDevice(AdId);

		RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(

				RelativeLayout.LayoutParams.WRAP_CONTENT,

				RelativeLayout.LayoutParams.WRAP_CONTENT

		);



		layout.addView(adView, adParams);

		adView.loadAd(builder.build());



		setContentView(layout);

	}



	@Override

	public void showAds(boolean show) {

		handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);

	}

}

