package com.brassbeluga.momentum.desktop;

import java.io.File;
import java.io.IOException;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import com.brassbeluga.momentum.Momentum;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = (int) 1280;
		config.height = (int) 720;
		
		/** FOR DEVELOPMENT: Pack textures before each run
		 *  add gdx-tools to root gradle-build to use TexturePacker
		* pass java command line argument -packTextures to use
		*/
		
		if (arg.length > 0 && arg[0].equals("-packTextures")) {
			Settings settings = new Settings();
	        settings.maxWidth = 1024;
	        settings.maxHeight = 1024; 
	        File androidAssets = new File("");
	        String assetPath = androidAssets.getAbsolutePath().replace("MomentumGame\\desktop", "Resources");
	        String outputPath = androidAssets.getAbsolutePath().replace("\\desktop", "\\android\\assets");
	        File pngFile = new File(assetPath + "/momentum.png");
        	try {
				pngFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			TexturePacker.process(settings, assetPath + "/prepack", outputPath, "momentum");
		}
		
		new LwjglApplication(new Momentum(), config);
		
	}
}
