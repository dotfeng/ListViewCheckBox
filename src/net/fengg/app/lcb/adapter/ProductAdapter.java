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
import android.widget.LinearLayout;
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
			holder.ll_quantity = (LinearLayout) convertView.findViewById(R.id.ll_quantity);  
			holder.tv_quantity = (TextView) convertView.findViewById(R.id.tv_quantity);
			holder.btn_edit = (Button) convertView.findViewById(R.id.btn_edit);
			holder.ll_edit_quantity = (LinearLayout) convertView.findViewById(R.id.ll_edit_quantity);
			holder.btn_decrease = (Button) convertView.findViewById(R.id.btn_decrease);
			holder.btn_increase = (Button) convertView.findViewById(R.id.btn_increase);
			holder.et_quantity = (EditText) convertView.findViewById(R.id.et_quantity);
			holder.btn_done = (Button) convertView.findViewById(R.id.btn_done);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final EditText et_quantity = holder.et_quantity;
		final TextView tv_quantity = holder.tv_quantity;
		final LinearLayout l_quantity = holder.ll_quantity;
		final LinearLayout le_quantity = holder.ll_edit_quantity;

		final Product product = list.get(position);
		holder.tv_content.setText(product.getContent());
		holder.tv_price.setText(product.getPrice() + "");
		
		
		holder.btn_edit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				l_quantity.setVisibility(View.GONE);
				le_quantity.setVisibility(View.VISIBLE);
			}
		});
		holder.btn_done.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				l_quantity.setVisibility(View.VISIBLE);
				le_quantity.setVisibility(View.GONE);
				product.setQuantity(Integer.parseInt(et_quantity.getText().toString()));
				tv_quantity.setText(et_quantity.getText().toString());
				context.updateAmount();
			}
		});
		
		holder.et_quantity.setText(product.getQuantity() + "");
		holder.tv_quantity.setText(product.getQuantity() + "");
		holder.cb_select.setChecked(selected.get(position));
		holder.cb_select.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("selected set position:" + position);
				selected.set(position, !selected.get(position));
				
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

		holder.btn_decrease.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!(TextUtils.isEmpty(et_quantity.getText().toString())
						|| "1".equals(et_quantity.getText().toString()))) {			
					et_quantity.setText(Integer.parseInt(et_quantity.getText().toString()) - 1 + "");
				}
			}
		});
		holder.btn_increase.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				et_quantity.setText(Integer.parseInt(et_quantity.getText().toString()) + 1 + "");
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
		LinearLayout ll_quantity;
		TextView tv_quantity;
		Button btn_edit;
		LinearLayout ll_edit_quantity;
		EditText et_quantity;
		Button btn_decrease;
		Button btn_increase;
		Button btn_done;
	}
}
