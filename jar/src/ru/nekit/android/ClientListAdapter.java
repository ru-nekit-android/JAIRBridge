package ru.nekit.android;

import java.util.List;

import ru.nekit.android.model.ClientProxy;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ClientListAdapter extends ArrayAdapter<ClientProxy> 
{

	Activity context;
	List<ClientProxy> dataSource;
	LayoutInflater inflater;

	static class ViewHolder
	{
		public TextView clientName;
		public TextView peerID;
		public TextView groupID;
	}

	public ClientListAdapter(Activity context, List<ClientProxy> objects) {
		super(context, R.layout.client_list_item, objects);
		this.dataSource = objects;
		this.context 	= context;
		this.inflater 	= context.getLayoutInflater();
	}

	@Override
	public View getView(int index, View convertView, ViewGroup parent) {
		ViewHolder holder;
		View rowView = convertView;
		if (rowView == null) {
			rowView = inflater.inflate(R.layout.client_list_item, null, true);
			holder = new ViewHolder();
			holder.clientName = (TextView) rowView.findViewWithTag("clientName");
			holder.peerID = (TextView) rowView.findViewWithTag("peerID");
			holder.groupID = (TextView) rowView.findViewWithTag("groupID");
			rowView.setTag(holder);
		} else {
			holder = (ViewHolder) rowView.getTag();
		}
		ClientProxy item = dataSource.get(index);
		holder.clientName.setText("name: " + item.getValue().clientName);
		holder.peerID.setText("peerID: " + item.getValue().peerID);
		holder.groupID.setText("groupID: " + item.getValue().groupID);
		return rowView;
	}
}