package com.android.app.function.eventbus.bus;

/**
 * Update title of ActionBar.
 *
 * @author Xinyue Zhao
 */
public final class UpdateActionBarTitleEvent {
	/**
	 * Title for {@link android.app.ActionBar}.
	 */
	private String mTitle;

	/**
	 * Constructor of {@link com.android.app.function.eventbus.bus.UpdateActionBarTitleEvent}.
	 *
	 * @param _title
	 * 		Title for {@link android.app.ActionBar}.
	 */
	public UpdateActionBarTitleEvent(String _title) {
		mTitle = _title;
	}

	/**
	 * Get title for {@link android.app.ActionBar}.
	 *
	 * @return Title for {@link android.app.ActionBar}.
	 */
	public String getTitle() {
		return mTitle;
	}
}
