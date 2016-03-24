package com.fashionhack.trace;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fashionhack.trace.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ClothingArticleActivity extends AppCompatActivity {

    @Bind(R.id.clothing_img) ImageView clothingImage;
    @Bind(R.id.clothing_name) TextView clothingName;
    @Bind(R.id.clothing_price) TextView clothingPrice;
    @Bind(R.id.clothing_img1) ImageView clothingImage1;
    @Bind(R.id.clothing_img2) ImageView clothingImage2;
    @Bind(R.id.clothing_img3) ImageView clothingImage3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothing_article);
        ButterKnife.bind(this);

        Picasso.with(this).load("https://s-media-cache-ak0.pinimg.com/564x/0e/c4/5d/0ec45d86c6b36a255861057121bce7a4.jpg")
                .into(clothingImage);
        clothingName.setText("Fall Sweater in brown");
        clothingPrice.setText("$50");

        Picasso.with(this).load("https://s-media-cache-ak0.pinimg.com/564x/6e/49/d4/6e49d40f0532ff72ac98356625708518.jpg").into(
                clothingImage1);
        Picasso.with(this).load("https://s-media-cache-ak0.pinimg.com/564x/86/2c/50/862c50e03b40d179a724ca9f20d62615.jpg").into(
                clothingImage2);
        Picasso.with(this).load("https://s-media-cache-ak0.pinimg.com/564x/6b/9d/2f/6b9d2f97559b5c7a02aa058527b8e79c.jpg").into(clothingImage3);
    }

}
