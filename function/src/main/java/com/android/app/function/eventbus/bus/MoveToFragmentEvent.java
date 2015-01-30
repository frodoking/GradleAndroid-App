package com.android.app.function.eventbus.bus;

import android.app.Fragment;

/**
 * Event for moving to a fragment.
 *
 * @author Xinyue Zhao
 */
public final class MoveToFragmentEvent {
	/**
	 * {@link android.app.Fragment} to switch.
	 */
	private Fragment mFragment;

	/**
	 * Constructor of {@link com.android.app.function.eventbus.bus.MoveToFragmentEvent}
	 *
	 * @param _fragment
	 * 		{@link android.app.Fragment} to switch.
	 */
	public MoveToFragmentEvent(Fragment _fragment) {
		mFragment = _fragment;
	}

	/**
	 * Get the {@link android.app.Fragment} to switch.
	 *
	 * @return {@link android.app.Fragment} to switch.
	 */
	public Fragment getFragment() {
		return mFragment;
	}
}
