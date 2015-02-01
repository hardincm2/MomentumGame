package com.brassbeluga.momentum.desktop;

import java.io.File;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import com.brassbeluga.momentum.Momentum;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 600;
		
		/** FOR DEVELOPMENT: Pack textures before each run
		 *  add gdx-tools to root gradle-build to use TexturePacker
		 */
		Settings settings = new Settings();
        settings.maxWidth = 512;
        settings.maxHeight = 512;
        File androidAssets = new File("");
        String assetPath = androidAssets.getAbsolutePath().replace("desktop", "android\\assets");
		TexturePacker.process(settings, assetPath + "/prepack", assetPath, "momentum");

		
		new LwjglApplication(new Momentum(), config);
	}
}
