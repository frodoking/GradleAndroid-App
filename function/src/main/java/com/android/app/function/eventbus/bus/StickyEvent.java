package com.android.app.function.eventbus.bus;


import com.android.app.function.eventbus.data.DataObject;

/**
 * Created by kevintanhongann on 11/27/13.
 * <p/>
 * <p/>
 * Update to move to package {@code com.kevintan.eventbussample.bus} and renamed.
 * <p/>
 *
 * @author Xinyue Zhao
 */
public class StickyEvent {
	private DataObject mDataObject;

	public StickyEvent(DataObject _dataObject) {
		mDataObject = _dataObject;
	}

	public DataObject getDataObject() {
		return mDataObject;
	}
}
