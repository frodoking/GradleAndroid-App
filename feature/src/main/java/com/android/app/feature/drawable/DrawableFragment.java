package com.android.app.feature.drawable;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.io.IOException;

/**
 * Created by frodo on 2016/6/29.
 */

public class DrawableFragment extends Fragment {
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return new LinearLayout(getContext());
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		try {
			Drawable d =  Drawable.createFromStream(getContext().getAssets().open("explore.svg"), null);
			view.setBackground(d);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
