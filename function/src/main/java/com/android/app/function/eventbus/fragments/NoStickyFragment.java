package com.android.app.function.eventbus.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.android.app.function.R;
import com.android.app.function.eventbus.bus.NormalEvent;
import com.android.app.function.eventbus.bus.SimpleEvent;
import com.android.app.function.eventbus.bus.StickyEvent;
import com.android.app.function.eventbus.bus.UpdateActionBarTitleEvent;

import de.greenrobot.event.EventBus;

/**
 * {@link com.android.app.function.eventbus.fragments.NoStickyFragment} can not accept a sticky-event, but normal event is no problem.
 * <p/>
 * UI can't refresh although it implements handler that handles the StickyEvent.
 */
public final class NoStickyFragment extends BaseFragment {
	private static final int LAYOUT = R.layout.eventbus_fragment_no_sticky;

	public void onEvent(StickyEvent e) {
		TextView textView = (TextView) getView().findViewById(R.id.text_tv);
		textView.setText(e.getDataObject().toString());
	}

	public void onEvent(NormalEvent e) {
		TextView textView = (TextView) getView().findViewById(R.id.text_tv);
		textView.setText("Got a normal event.");

	}

	public void onSimpleEvent(SimpleEvent e) {
		Toast.makeText(getActivity(),"onSimpleEvent handling SimpleEvent", Toast.LENGTH_LONG).show();
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		/*Update ActionBar's title.*/
		EventBus.getDefault().post(new UpdateActionBarTitleEvent(getString(R.string.screen_4)));
		return inflater.inflate(LAYOUT, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		view.findViewById(R.id.normal_event_btn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				/*Can send a normal event and handle it.*/
				EventBus.getDefault().post(new NormalEvent());
			}
		});

		view.findViewById(R.id.simple_event_btn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EventBus.getDefault().post(new SimpleEvent());
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		EventBus.getDefault().register(this);
	}
}
