package com.luminet.mobile.luminetandroid.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.luminet.mobile.luminetandroid.R;

/**
 * Created by chris on 2017/10/26.
 */

public class TermsConditionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view_layout);
        WebView webView = (WebView)findViewById(R.id.webview);
        String terms = "<html>\n" +
                "\t<body>\n" +
                "\t\t<div style=\"padding-right:5%\">\n" +
                "\t\t\t<h3 style=\"font-weight:bold;\">SOFTWARE LICENSE AGREEMENT FOR LUMINET WORLD APPLICATION</h3>\n" +
                "\t\t\t<span style=\"font-weight:bold;\">Date last amended: December 2013</span>\n" +
                "\t\t\n" +
                "\t\t\t<p>IMPORTANT INFORMATION: READ BEFORE YOU DOWNLOAD OR USE THE LUMINET WORLD APPLICATION (“Luminet App”)</p>\n" +
                "\t\t\t<p>Luminet World is a communications platform between yourself and organisations. By downloading and registering on the Luminet App, you agree to be bound to the general terms and conditions of Luminet World, which are contained on <a style=\"color: blue; text-decoration: underline;\" onClick=\"window.open('http://www.luminetworld.com', '_system')\">www.luminetworld.com</a> (“T’s & C’s”), as well as this software license agreement (“Agreement”). It is your responsibility, after signing up, to periodically check the T’s & C’s as well as the terms of this Agreement. Any access by you to the Luminet App is deemed to be acceptance of both.</p>\n" +
                "\t\t\t<p>Do not download or access the Luminet App if you do not agree to be bound to the T’s & C’s and this Agreement.</p>\n" +
                "\t\t\t<p>Luminet grants you (in your personal capacity) the right to install and use the Luminet App on multiple mobile devices that you control for the sole purpose of connecting with multiple organisations.</p>\n" +
                "\t\t\t<p>The right granted to you is revocable, which means Luminet can take back the right it has given to you. You may not transfer the right.</p>\n" +
                "\t\t\t<p>By accessing the Luminet App, you grant consent to Luminet to provide any captured data to organisations which provide information, products or services in the lifestyle sectors or interest categories (as defined in the T’s & C’s) which you have indicated on the Luminet App are of relevance to you. You thereby provide those organisations with consent to connect with you through the Luminet App. You may at any stage amend your lifestyle or interest data, block an organisation from connecting with you or delete the Luminet App from your phone in its entirety.</p>\n" +
                "\t\t\t<p>The following activities are prohibited in terms of this Agreement:</p>\n" +
                "\t\t\t<p>\n" +
                "\t\t\t\t<ul>\n" +
                "\t\t\t\t\t<li>You may not make a copy the Luminet App except for your own reasonable back up purposes</li>\n" +
                "\t\t\t\t\t<li>You may not reproduce, modify, reverse engineer or use the source code of the Luminet App</li>\n" +
                "\t\t\t\t\t<li>You may not make any derivative work from the Luminet App</li>\n" +
                "\t\t\t\t\t<li>You may not decrypt the Luminet App or override any security features</li>\n" +
                "\t\t\t\t\t<li>You may not remove, hide or alter any proprietary notice on or associated with the Luminet App and this includes any copyright notices or trademarks or license agreements</li>\n" +
                "\t\t\t\t\t<li>You may not use the services for any purpose they were not intended or designed</li>\n" +
                "\t\t\t\t</ul>\n" +
                "\t\t\t</p>\n" +
                "\t\t\t<p>If you modify or attempt to modify your device in any way or use any software that is designed to modify your device, override, or disable any security features on your device or its operating system, you may expose yourself to risk. This includes but is not limited to the following: jail breaking or rooting your phone. Modifying your phone or its operating system may also result in the Luminet App not working properly or at all and Luminet makes no warranties in this regard.</p>\n" +
                "\t\t\t<p>Luminet makes no warranties with regards to any organisation displayed to you on the Luminet World App. Validity of displayed messages or organisations and or the accuracy of the content or delivery time. The Luminet App is a delivery channel for messages from the organisation to you. For any queries on the content displayed you are required to contact the relevant organisation. Any reliance placed on any messages received by you on Luminet World is entirely at your own risk.</p>\n" +
                "\t\t\t<p>Any access to your handset capabilities including amongst others the camera, vibration, photo gallery, accelerometer or microphone is required purely for the purpose of making current or future functionality available to you.</p>\n" +
                "\t\t\t<p>While we will use our best efforts to ensure that the Luminet App operates as it was designed by us, we cannot warrant that the services are compatible with, or will operate with your mobile device or any software/hardware that you have on your mobile device.</p>\n" +
                "\t\t\t<p>Luminet may at any time discontinue or disable certain parts of the services available via the Luminet App.</p>\n" +
                "\t\t\t<p>No fee is payable for downloading the Luminet App, but Luminet may amend this in its sole and absolute discretion.</p>\n" +
                "\t\t\t\n" +
                "\t\t</div>\n" +
                "\t</body>\n" +
                "</html>";
        webView.loadData(terms, "text/html", null);

    }
}
