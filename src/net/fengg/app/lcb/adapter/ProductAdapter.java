package net.fengg.app.lcb.adapter;

import java.util.ArrayList;
import java.util.List;

import net.fengg.app.lcb.R;
import net.fengg.app.lcb.activity.MainActivity;
import net.fengg.app.lcb.model.Product;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class ProductAdapter extends BaseAdapter {

	private final List<Boolean> selected = new ArrayList<Boolean>();

	private LayoutInflater inflater;
	private List<Product> list;
	StoreAdapter adapter;
	int i;
	MainActivity context;

	public ProductAdapter(MainActivity context, List<Product> list, StoreAdapter adapter, int i) {
		this.inflater = LayoutInflater.from(context);
		this.list = list;
		this.adapter = adapter;
		this.i = i;
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
			holder.cbSelect = (CheckBox) convertView.findViewById(R.id.cbSelect);
			holder.tvContent = (TextView) convertView.findViewById(R.id.tvContent);
			holder.tvItemID = (TextView) convertView.findViewById(R.id.tvItemID);  
	        holder.btClick = (Button) convertView.findViewById(R.id.btClick);  
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final Product itemInfo = list.get(position);
		holder.tvContent.setText(itemInfo.getContent());
		holder.tvItemID.setText(itemInfo.getId() + "");
		
		
		holder.cbSelect.setChecked(selected.get(position));
		holder.cbSelect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("selected set position:" + position);
				selected.set(position, !selected.get(position));// 将CheckBox的选中状况记录下来
				
				if(selected.contains(false)) {
					adapter.getSelect().set(i, false);
				}else {
					adapter.getSelect().set(i, true);
				}
				
				if(adapter.getSelect().contains(false)) {
					context.checkAll(false);
				}else {
					context.checkAll(true);
				}
				
				adapter.notifyDataSetChanged();
			}
		});

		 holder.btClick.setOnClickListener(new OnClickListener() {  
			  
	            @Override  
	            public void onClick(View v) {  
	                String value = String.valueOf(position) + "|" + itemInfo.getId() + "|" + itemInfo.getContent();  
	                Toast.makeText(context, value, Toast.LENGTH_SHORT).show();  
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
		 public CheckBox cbSelect;  
	     public TextView tvContent;  
	     public TextView tvItemID;  
	     public Button btClick; 
	}
	
	public interface CheckAll {
		public void checkAll(boolean checked);
	}
}
