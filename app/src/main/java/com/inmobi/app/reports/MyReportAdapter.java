package com.inmobi.app.reports;

/**
 * Created by krishna.tiwari on 08/08/15.
 */

import android.content.Context;
import android.renderscript.Sampler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.inmobi.app.bloodbank.R;

import java.util.List;

public class MyReportAdapter extends ArrayAdapter<ReportData> {
    private final Context context;
    private final List<ReportData> values;

//    public MyReportAdapter(Context context, String[] values) {
//        super(context, R.layout.list_reports, values);
//        this.context = context;
//        this.values = values;
//    }

    public MyReportAdapter(Context context, List<ReportData> values) {
        super(context, R.layout.list_reports, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_reports, parent, false);
         TextView textView = (TextView) rowView.findViewById(R.id.label);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
           TextView dateView =(TextView) rowView.findViewById(R.id.date);

        textView.setText(values.get(position).getReportName());
         imageView.setImageDrawable(values.get(position).getPreview());
     //   imageView.setImageResource(R.drawable.ic_launcher);
           dateView.setText(values.get(position).getDate());
        return rowView;
    }
}