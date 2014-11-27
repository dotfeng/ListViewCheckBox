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
import android.widget.TextView;


public class MainActivity extends Activity 
	implements OnClickListener, OnItemClickListener {

	private List<Store> list;
	private StoreAdapter adapter;
	private ListView lv_store;
	private CheckBox cb_select_all ;
	private TextView tv_amount;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		cb_select_all = (CheckBox) findViewById(R.id.cb_select_all);
		lv_store = (ListView) findViewById(R.id.lv_store);
		tv_amount = (TextView) findViewById(R.id.tv_amount);

		list = new ArrayList<Store>();
		initData();
		adapter = new StoreAdapter(this, list);
		
		lv_store.setAdapter(adapter);
		lv_store.setOnItemClickListener(this);
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
				info.setPrice(j+1);
				info.setContent("µêÆÌÄÚÉÌÆ·" + i+j);
				info.setQuantity(1);
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
		case R.id.btn_delete:	
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
			updateAmount();
			adapter.notifyDataSetChanged();
			cb_select_all.setChecked(false);
			break;
		case R.id.cb_select_all:
			boolean flag = cb_select_all.isChecked();
			for (int i = 0; i < adapter.getSelect().size(); i++) {
				adapter.getSelect().set(i, flag);
				for(int j=0;j<adapter.getPAdapter(i).getSelect().size();j++) {
					adapter.getPAdapter(i).getSelect().set(j, flag);
				}
			}
			updateAmount();
			adapter.notifyDataSetChanged();
			break;

		default:
			break;
		}
		
	}

	public void checkAll(boolean checked) {
		cb_select_all.setChecked(checked);
	}
	
	public void updateAmount() {
		double amount = 0;
		
		for (int i = 0; i < list.size(); i++) {
			for(int j=0; j < list.get(i).getProducts().size(); j++) {
				if(adapter.getPAdapter(i).getSelect().get(j)) {
					amount += list.get(i).getProducts().get(j).getPrice()
							*list.get(i).getProducts().get(j).getQuantity();
				}
			}
		}
		
		tv_amount.setText(amount + "");
	}
	
}
