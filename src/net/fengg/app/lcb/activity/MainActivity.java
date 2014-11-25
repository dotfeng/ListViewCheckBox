package net.fengg.app.lcb.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import net.fengg.app.lcb.R;
import net.fengg.app.lcb.adapter.ProductAdapter;
import net.fengg.app.lcb.adapter.StoreAdapter;
import net.fengg.app.lcb.model.Product;
import net.fengg.app.lcb.model.Store;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.ListView;


public class MainActivity extends Activity 
	implements OnClickListener, OnItemClickListener, 
	net.fengg.app.lcb.adapter.StoreAdapter.CheckAll, net.fengg.app.lcb.adapter.ProductAdapter.CheckAll{

	private List<Store> list;
	private StoreAdapter adapter;
	private ListView actualListView;
	private CheckBox cbSelectAll ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		cbSelectAll = (CheckBox) findViewById(R.id.cbSelectAll);
		actualListView = (ListView) findViewById(R.id.lv_store);

		list = new ArrayList<Store>();
		initData();
		adapter = new StoreAdapter(this, list);
		
		actualListView.setAdapter(adapter);
		actualListView.setOnItemClickListener(this);
	}

	private void initData() {
		for (int i = 0; i < 5; i++) {
			Store store = new Store();
			store.setId(i);
			store.setName("µêÆÌ" + i);
			
			List<Product> plist = new ArrayList<Product>();
			
			for (int j = 0; j < 5; j++) {
				Product info = new Product();
				info.setId(j);
				info.setContent("µêÆÌÄÚÉÌÆ·" + i+j);
				plist.add(info);
			}
			store.setProducts(plist);
			
			list.add(store);
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btDelete:
//			for (int i = 0; i < adapter.getSelect().size(); i++) {
//				System.out.println(i+"|" + adapter.getSelect().get(i));
//				if(adapter.getSelect().get(i)){
//					for(int j = 0; j < adapter.getPAdapter(i).getSelect().size();j++) {
//						adapter.getPAdapter(i).getSelect().remove(j);
//						list.get(i).getProducts().remove(j);
//					}
//					
//					adapter.getSelect().remove(i);
//					list.remove(i);
//				} else {
//					for(int j = 0; j < adapter.getPAdapter(i).getSelect().size();j++) {
//						if(adapter.getPAdapter(i).getSelect().get(j)) {							
//							adapter.getPAdapter(i).getSelect().remove(j);
//							list.get(i).getProducts().remove(j);
//						}
//					}
//				}
//			}
			
			ListIterator<Boolean> storeB = adapter.getSelect().listIterator();
			ListIterator<Store> storeL = list.listIterator();
			ListIterator<ProductAdapter> adapterL = adapter.getPAdapterList().listIterator();
			while(storeB.hasNext()) {
				boolean it = storeB.next();
				Store store = storeL.next();
				ProductAdapter pAdapter = adapterL.next();
				ListIterator<Boolean> productB = pAdapter.getSelect().listIterator();
				ListIterator<Product> productL = store.getProducts().listIterator();
				if(it) {
					while(productB.hasNext()) {
						productB.next();productB.remove();
						productL.next();productL.remove();
					}
					storeB.remove();
					storeL.remove();
					adapterL.remove();
				}else {
					while(productB.hasNext()) {
						productL.next();
						if(productB.next()) {
							productB.remove();productL.remove();
						}
					}
				}
			}
			
			adapter.notifyDataSetChanged();
//			adapter = new StoreAdapter(this, list);
//			actualListView.setAdapter(adapter);
			cbSelectAll.setChecked(false);
			break;
		case R.id.cbSelectAll:
			boolean flag = cbSelectAll.isChecked();
			for (int i = 0; i < adapter.getSelect().size(); i++) {
				adapter.getSelect().set(i, flag);
				for(int j=0;j<adapter.getPAdapter(i).getSelect().size();j++) {
					adapter.getPAdapter(i).getSelect().set(j, flag);
				}
			}
			adapter.notifyDataSetChanged();
			break;

		default:
			break;
		}
		
	}

	@Override
	public void checkAll(boolean checked) {
		cbSelectAll.setChecked(checked);
	}
}
