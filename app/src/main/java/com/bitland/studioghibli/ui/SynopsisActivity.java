package com.bitland.studioghibli.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.bitland.studioghibli.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SynopsisActivity extends AppCompatActivity {

    @BindView(R.id.synopsisTextView)TextView mSynopsisTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synopsis);
        ButterKnife.bind(this);

        showSynopsis();
    }

    private void showSynopsis() {
        Intent intent = getIntent();

        Resources resources = getResources();
        String key = resources.getString(R.string.key);

        String synopsis = intent.getStringExtra(key);

        mSynopsisTextView.setText(synopsis);
    }
}
