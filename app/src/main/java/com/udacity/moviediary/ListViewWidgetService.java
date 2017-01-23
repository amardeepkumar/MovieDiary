package com.udacity.moviediary;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Amardeep Kumar on 1/22/2017.
 */
public class ListViewWidgetService extends RemoteViewsService {

    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {

        return new ListViewRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}

