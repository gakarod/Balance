package com.pointless.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pointless.AdHandler;
import com.pointless.FlappyImpact;

public class DesktopLauncher implements AdHandler {                // STARTER CLASS
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true");
		config.width = 480 ;
		config.height = 320 ;
		new LwjglApplication(new FlappyImpact(), config);
	}

	@Override
	public void showAds(boolean show) {

	}
}
