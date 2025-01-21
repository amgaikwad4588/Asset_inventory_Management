package com.example.admin.augscan;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        closeContextMenu();
        Element adsElement = new Element();
        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setDescription("This app was developed as the final year project by the students of Computer Enggineering ,Government Polytechnic Nagpur.\n" +
                        "Team Members\n" +
                        "1.Vaishnavi Asare(2013005)\n"
                        +"2.Aditya Gaikwad(2013019)\n"
                        +"3.Mrunmayi Ambhaikar(2013043)\n"+
                        "4.Anjali Rambhad(2013052)\n"+
                        "5.Adwait Shesh(2013059)\n"+
                        "\n\n\tNEED HELP?"+
                        "\n\nHOW TO ADD AN ASSET TO THE INVENTORY?\n\n" +
                        "      1. Create a new user by entering email and password and other details and log in to the app upon the logout activity. Log in directly if the user already exists.\n" +
                        "      2. For adding an item into the inventory simply click on the 'Add Asset' and select item category as well as fill other details of that asset. Then scan the Barcode/QR code stuck on the asset to process further.\n" +
                        "      3. Based on the type of asset you've chosen you will be navigated to another screen which will ask you to fill some more details about the asset including asset's realtime image.\n" +
                        "      4. Wait till the image gets uploaded and you are good to go!\n" +
                        "\n\n" +
                        "        HOW TO DELETE AN ASSET FROM THE INVENTORY?\n" +
                        "\n" +
                        "     1. Create a new user by entering email and password and other details and log in to the app upon the logout activity. Log in directly if the user already exists.\n" +
                        "     2. For deleting an asset simply scan the Barcode/QR code stuck on the asset and click on 'Delete Item'.\n" +
                        "\n\n" +
                        "        HOW TO SEARCH FOR AN ASSET IN THE INVENTORY?\n" +
                        "\n" +
                        "     1. Create a new user by entering email and password and other details and log in to the app upon the logout activity. Log in directly if the user already exists.\n" +
                        "     2. In the Search bar manually enter the Barcode number and click on the search button.\n" +
                        "     3. Another way is to directly scan the Barcode/QR code stuck on the asset.\n" +
                        "     4. A list of assets matching your search will display on the screen.\n" +
                        "\n\n" +
                        "        HOW TO VIEW THE ENTIRE INVENTORY?\n" +
                        "\n" +
                        "    1. Create a new user by entering email and password and other details and log in to the app upon the logout activity. Log in directly if the user already exists.\n" +
                        "    2. To view the entire inventory click on the 'View Inventory' and select the asset category you want to view.\n" +
                        "    3. A list containing of the same category assets will be displayed.\n")

                .addGroup("CONNECT WITH US!")
                .addEmail("verifyItContact@gmail.com")
                .addWebsite("https://forms.gle/a4d8CWREbLavm77s8","Give us a Feedback!")
                .addItem(createCopyright())
                .addItem(new Element().setTitle("Version 1.0"))
                .create();
        setContentView(aboutPage);
    }
        private Element createCopyright()
        {
            Element copyright = new Element();
            @SuppressLint
                    ("DefaultLocale") final String copyrightString = String.format("Copyright %d by Students of Computer Government Polytechnic Nagpur.\n ",
                    Calendar.getInstance().get(Calendar.YEAR));
            copyright.setTitle(copyrightString);
            // copyright.setIcon(R.mipmap.ic_launcher);
            copyright.setGravity(Gravity.CENTER);
            copyright.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(AboutUs.this,copyrightString,Toast.LENGTH_SHORT).show();
                }
            });
            return copyright;
    }
    }
    /**/
