package com.android.app.function.eventbus;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.app.function.R;
import com.android.app.function.eventbus.bus.MoveToFragmentEvent;
import com.android.app.function.eventbus.bus.StickyEvent;
import com.android.app.function.eventbus.bus.UpdateActionBarTitleEvent;
import com.android.app.function.eventbus.data.DataObject;
import com.android.app.function.eventbus.fragments.BaseFragment;
import com.android.app.function.eventbus.fragments.NoStickyFragment;
import com.android.app.function.eventbus.fragments.SecondFragment;
import com.android.app.function.eventbus.fragments.ThirdFragment;

import de.greenrobot.event.EventBus;

/**
 * Sample main {@link android.app.Activity}.
 * <p/>
 * Created by kevintanhongann.
 * <p/>
 * Update:
 * <p/>
 * 1. Added event-handler that updates title of {@link android.app.ActionBar}.
 * <p/>
 * 2. Added event-handler that controls showing {@link com.kevintan.eventbussample.fragments.SecondFragment}, {@link
 * com.kevintan.eventbussample.fragments.ThirdFragment}.
 * <p/>
 * 3. Remove {@link com.kevintan.eventbussample.bus.StickyEvent} in {@link EventBusActivity#onDestroy()}.
 *
 * @author Xinyue Zhao
 */
public class EventBusActivity extends Activity {
	private static final int LAYOUT = R.layout.eventbus_main;

	/**
	 * Handler for {@link com.kevintan.eventbussample.bus.UpdateActionBarTitleEvent}
	 *
	 * @param e
	 * 		Event {@link  com.kevintan.eventbussample.bus.UpdateActionBarTitleEvent}.
	 */
	public void onEvent(UpdateActionBarTitleEvent e) {
		getActionBar().setTitle(e.getTitle());
	}

	/**
	 * Handler for {@link com.kevintan.eventbussample.bus.MoveToFragmentEvent}
	 *
	 * @param e
	 * 		Event {@link com.kevintan.eventbussample.bus.MoveToFragmentEvent }.
	 */
	public void onEvent(MoveToFragmentEvent e) {
		if (e.getFragment() instanceof ThirdFragment) {
			getFragmentManager().beginTransaction().replace(R.id.container, e.getFragment()).addToBackStack(null)
					.commit();
		} else if (e.getFragment() instanceof SecondFragment) {
			DataObject object = new DataObject("kevin tan", "kevintan@kevintan.com");
			EventBus.getDefault().postSticky(new StickyEvent(object));
			getFragmentManager().beginTransaction().replace(R.id.container, e.getFragment()).addToBackStack(null)
					.commit();
		} else if (e.getFragment() instanceof NoStickyFragment) {
			DataObject object = new DataObject("Hello, world!", "www.github.com");
			EventBus.getDefault().postSticky(new StickyEvent(object));
			getFragmentManager().beginTransaction().replace(R.id.container, e.getFragment()).addToBackStack(null)
					.commit();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(LAYOUT);
		EventBus.getDefault().register(this);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment())
					.commit();
		}
	}

	@Override
	protected void onDestroy() {
		EventBus.getDefault().unregister(this);
		EventBus.getDefault().removeStickyEvent(StickyEvent.class);
		super.onDestroy();
	}


	/**
	 * A placeholder fragment containing a simple view.
	 * <p/>
	 * Update:
	 * <p/>
	 * Extends from {@link com.kevintan.eventbussample.fragments.BaseFragment} to demonstrate coexistence between {@link
	 * de.greenrobot.event.EventBus#registerSticky(Object)} and {@link de.greenrobot.event.EventBus#register(Object)}.
	 */
	public static class PlaceholderFragment extends BaseFragment {
		private static final int LAYOUT = R.layout.eventbus_fragment_main;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
		                         Bundle savedInstanceState) {
			/* Update fragment's title.*/
			EventBus.getDefault().post(new UpdateActionBarTitleEvent(getString(R.string.screen_1)));
			View rootView = inflater.inflate(LAYOUT, container, false);
			/* Move to fragment that can demonstrate sticky-event. */
			rootView.findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					EventBus.getDefault().post(new MoveToFragmentEvent(new SecondFragment()));
				}
			});
			/* Move to fragment that can not accept sticky-event. */
			rootView.findViewById(R.id.btn_no_sticky).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					EventBus.getDefault().post(new MoveToFragmentEvent(new NoStickyFragment()));
				}
			});

			return rootView;
		}
	}

}
