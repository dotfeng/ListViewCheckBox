package net.fengg.app.lcb.util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class Util {
	public static void setListViewHeight(ListView listView) {   
		 
        ListAdapter listAdapter = listView.getAdapter();    
 
        if (listAdapter == null) {   
 
            return;   
 
        }   
 
        int totalHeight = 0;   
 
        for (int i = 0; i < listAdapter.getCount(); i++) {   
 
            View listItem = listAdapter.getView(i, null, listView);   
 
            listItem.measure(0, 0);   
 
            totalHeight += listItem.getMeasuredHeight();   
 
        }   
 
        ViewGroup.LayoutParams params = listView.getLayoutParams();   
 
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));   
 
        listView.setLayoutParams(params);  
 
    }
}
