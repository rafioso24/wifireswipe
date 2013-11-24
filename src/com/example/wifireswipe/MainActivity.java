package com.example.wifireswipe;
import android.app.ActionBar;


import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.*;
import java.util.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationManager;
import android.net.http.AndroidHttpClient;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.wifireswipe.MainActivity.MyWebViewClient.MiddlePanelFragment;
import com.example.wifireswipe.MainActivity.MyWebViewClient.RightPanelFragment;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {
	static int mapOption = 0;
	
    AppSectionsPagerAdapter mAppSectionsPagerAdapter;
   //The {link ViewPager} that will display the three primary sections of the app, one at a time.
    ViewPager mViewPager;
    public void onCreate(Bundle savedInstanceState) {
        
      
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.
        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();

        // The Home/Up button should not be enabled, since there is no hierarchical parent.
        actionBar.setHomeButtonEnabled(false);

        // Specify that we will be displaying tabs in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Set up the ViewPager, attaching the adapter and setting up a listener for when the
        // user swipes between sections.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between different app sections, select the corresponding tab.
                // We can also use ActionBar.Tab#select() to do this if we have a reference to the Tab.
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by the adapter.
            // Also specify this Activity object, which implements the TabListener interface, as the listener for when this tab is selected.
            actionBar.addTab(actionBar.newTab().setText(mAppSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
        }
    }
    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }
    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
     * sections of the app.
     */
    public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {
        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) { //"i" is the page number
            switch (i) {
                case 0:
                    return new LeftPanelFragment();
                case 1:
                		Fragment fragment = new MiddlePanelFragment();
	                	Bundle args = new Bundle();
	                	args.putInt(MiddlePanelFragment.ARG_SECTION_NUMBER, i + 1);
	                	fragment.setArguments(args);
	                	return fragment;                	
                case 2:
                	Fragment fragment2 = new RightPanelFragment();
                	Bundle args2 = new Bundle();
                	args2.putInt(RightPanelFragment.ARG_SECTION_NUMBER, i + 1);
                	fragment2.setArguments(args2);
                	return fragment2;
                default:
                	return new LeftPanelFragment();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position == 0)
            	return "Survey";
            else if(position ==1)
            	return "Map";
            else
            	return "Settings";
        	//return "Section " + (position + 1);
        }
    }

    
    public static class LeftPanelFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
            
        	View rootView = inflater.inflate(R.layout.fragment_section_left, container, false);
            rootView.findViewById(R.id.manualSwitch).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Create intent to enter manual mode
                        	//Intent intent = new Intent(getActivity(), CollectionDemoActivity.class);
                            //startActivity(intent);
                        }
                    });
            rootView.findViewById(R.id.autoSwitch).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
							//Create an intent to enter automatic mode
                        	//Intent externalActivityIntent = new Intent(Intent.ACTION_PICK);
                        	//startActivity(externalActivityIntent);
                      }
                    });
            return rootView;
        }
    }
    
    public static class MyWebViewClient extends WebViewClient {        
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.endsWith(".mp4")) 
            {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(url), "video/*");

                view.getContext().startActivity(intent);
                return true;
            } 
            else {
                return super.shouldOverrideUrlLoading(view, url);
            }
        }
    
    public static class MiddlePanelFragment extends Fragment {
        public static final String ARG_SECTION_NUMBER = "section_number";
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        	View mainView = (View)inflater.inflate(R.layout.fragment_section_middle, container, false);
        	WebView myWebView = (WebView)mainView.findViewById(R.id.webview);
        	WebSettings webSettings = myWebView.getSettings();
        	webSettings.setJavaScriptEnabled(true);
        	myWebView.setWebViewClient(new MyWebViewClient());
        	myWebView.getSettings().setBuiltInZoomControls(false); 
            myWebView.getSettings().setSupportZoom(false);
            myWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);   
            myWebView.getSettings().setAllowFileAccess(true); 
            myWebView.getSettings().setDomStorageEnabled(true);
            if(mapOption == 0)	//heat map
            	myWebView.loadUrl("http://www.codegen.bugs3.com/results.php"); 
            else				//range map
            	myWebView.loadUrl("http://www.codegen.bugs3.com"); 
            return mainView;
        }
    }
    public static class RightPanelFragment extends Fragment {
        	public static final String ARG_SECTION_NUMBER = "section_number";
        @Override
	        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
	            View rootView = inflater.inflate(R.layout.fragment_section_right, container, false);
	           //((TextView) rootView.findViewById(android.R.id.text2)).setText(getString(R.string.right_panel_text, args.getInt(ARG_SECTION_NUMBER)));
	            Button button1 = (Button)rootView.findViewById(R.id.button1);
	            Button button2 = (Button)rootView.findViewById(R.id.button2);
	            
//	            public void changeToRange(View view){
//	        		mapOption = 1;
//	        	}
//	        	public void changeToHeat(View view){
//	        		mapOption =0;
//	        	}

	            return rootView;
	        }
        	
    	}
    }
}
