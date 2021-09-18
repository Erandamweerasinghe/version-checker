package com.gmt.cap.plugin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.Settings;
import android.widget.Toast;

import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@NativePlugin
public class VersionChecker extends Plugin {

    @PluginMethod
    public void isUpdateAvailable(PluginCall call) {
		String customerbundleId = call.getString("bundleId"); // bundleId --> com.example.name
		String playStoreVersion = null;

			GetVersion version = new GetVersion(customerbundleId);
			try{
				playStoreVersion = version.execute().get();
			} catch (InterruptedException | ExecutionException ex) {
				ex.printStackTrace();
			}
			JSObject ret = new JSObject();
			ret.put("liveVersion", playStoreVersion);
			call.success(ret);
    }
}

 class GetVersion extends AsyncTask<String, String, String> {

	private String newVersion = null;
	private String bundleId;

	GetVersion(String bundleId) {
		this.bundleId = bundleId;
	}
	@Override
	protected String doInBackground(String... strings) {

		try {
			Document document = Jsoup.connect("https://play.google.com/store/apps/details?id=" + bundleId + "&hl=en")
				.timeout(30000)
				.userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
				.referrer("http://www.google.com")
				.get();

			if (document != null) {
				Elements element = document.getElementsContainingOwnText("Current Version");
				for (Element ele : element) {
					if (ele.siblingElements() != null) {
						Elements sibElements = ele.siblingElements();
						for (Element sibElement : sibElements) {
							newVersion = sibElement.text();
						}
					}
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return newVersion;
	}
 }
