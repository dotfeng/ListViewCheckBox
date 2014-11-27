package net.fengg.app.lcb.adapter;

import java.util.ArrayList;
import java.util.List;

import net.fengg.app.lcb.R;
import net.fengg.app.lcb.activity.MainActivity;
import net.fengg.app.lcb.model.Product;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class ProductAdapter extends BaseAdapter {

	private final List<Boolean> selected = new ArrayList<Boolean>();

	private LayoutInflater inflater;
	private List<Product> list;
	StoreAdapter adapter;
	int storePosition;
	MainActivity context;

	public ProductAdapter(MainActivity context, List<Product> list, StoreAdapter adapter, int storePosition) {
		this.inflater = LayoutInflater.from(context);
		this.list = list;
		this.adapter = adapter;
		this.storePosition = storePosition;
		this.context = context;
		for (int j = 0; j < list.size(); j++) {
			getSelect().add(false);
		}
	}

	public List<Boolean> getSelect() {
		return selected;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.product_item, null);

			holder = new ViewHolder();
			holder.cb_select = (CheckBox) convertView.findViewById(R.id.cb_select);
			holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
			holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);  
			holder.btn_decrease = (Button) convertView.findViewById(R.id.btn_decrease);
			holder.btn_increase = (Button) convertView.findViewById(R.id.btn_increase);
			holder.et_quantity = (EditText) convertView.findViewById(R.id.et_quantity);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final Product product = list.get(position);
		holder.tv_content.setText(product.getContent());
		holder.tv_price.setText(product.getPrice() + "");
		holder.et_quantity.setText(product.getQuantity() + "");
//		holder.et_quantity.setKeyListener(null);
		holder.cb_select.setChecked(selected.get(position));
		holder.cb_select.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("selected set position:" + position);
				selected.set(position, !selected.get(position));// 将CheckBox的选中状况记录下来
				
				if(selected.contains(false)) {
					adapter.getSelect().set(storePosition, false);
				}else {
					adapter.getSelect().set(storePosition, true);
				}
				
				if(adapter.getSelect().contains(false)) {
					context.checkAll(false);
				}else {
					context.checkAll(true);
				}
				
				context.updateAmount();
				adapter.notifyDataSetChanged();
			}
		});

		final EditText quantity = holder.et_quantity;
		
		holder.btn_decrease.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!(TextUtils.isEmpty(quantity.getText().toString())
						|| "1".equals(quantity.getText().toString()))) {			
					quantity.setText(Integer.parseInt(quantity.getText().toString()) - 1 + "");
					product.setQuantity(Integer.parseInt(quantity.getText().toString()));
					context.updateAmount();
				}
			}
		});
		holder.btn_increase.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				quantity.setText(Integer.parseInt(quantity.getText().toString()) + 1 + "");
				product.setQuantity(Integer.parseInt(quantity.getText().toString()));
				context.updateAmount();
			}
		});
		
		return convertView;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public class ViewHolder {
		CheckBox cb_select;
		TextView tv_content;
		TextView tv_price;
		EditText et_quantity;
		Button btn_decrease;
		Button btn_increase;
	}
}
