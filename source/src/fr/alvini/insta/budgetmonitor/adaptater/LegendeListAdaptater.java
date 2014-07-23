package fr.alvini.insta.budgetmonitor.adaptater;

import java.util.List;
import fr.alvini.insta.budgetmonitor.R;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LegendeListAdaptater extends ArrayAdapter<String>{
	private final Activity context;
	private final List<String> text;
	private final List<String> ColorId;
	public LegendeListAdaptater(Activity context, List<String> text, List<String> colorId) {
		super(context, R.layout.legenge_list, text);
		this.context = context;
		this.text = text;
		this.ColorId = colorId;
	}
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView= inflater.inflate(R.layout.legenge_list, null, true);
		TextView txtTitle = (TextView) rowView.findViewById(R.id.itxt);
		LinearLayout imageView = (LinearLayout) rowView.findViewById(R.id.icolor);
		txtTitle.setText(text.get(position));
		imageView.setBackgroundColor(Color.parseColor(ColorId.get(position)));
		return rowView;
	}
}