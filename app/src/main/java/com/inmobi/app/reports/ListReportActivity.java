package com.inmobi.app.reports;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import com.inmobi.app.bloodbank.R;
import com.inmobi.app.reports.MyReportAdapter;
import com.inmobi.app.utils.MyReportsContentProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ListReportActivity extends ListActivity {

    private static final int TAKE_PICTURE = 1;
    private Uri imageUri;
    static final String[] MY_REPORTS =
            new String[]{"Fortis", "Apollo", "Dr Batra", "Dr Chandra"};

    private String reportName;
    private String date;


    private ListView listView;

    private TextView addReport;
    private File photofile, file;
    File newDir;
    private int TAKENPHOTO = 0;

    static final List<ReportData> my_reports = new ArrayList<ReportData>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_report);
        listView = this.getListView();

        View header = (View) getLayoutInflater().inflate(R.layout.reportheader, null);
        listView.addHeaderView(header);

        MyReportAdapter adapter = new MyReportAdapter(this, my_reports);
        Date today = new Date();
        ReportData dummyData = new ReportData("Dr Batra ", this.getResources().getDrawable(R.drawable.ic_launcher), today.toString());
        //   my_reports.add(dummyData);
        listView.setAdapter(adapter);
        //clean();
        onLoad();
        addReport = (TextView) findViewById(R.id.addreports);
        addReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reportName = showInputDialog(view);
                System.out.println("Repot name returned in onclick " + reportName);
            }
        });

   setListAdapter(new MyReportAdapter(this, my_reports));

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        //get selected items
        if (position > 0) {
           // String selection = l.getItemAtPosition(position);
            ReportData reportData =  (ReportData) l.getItemAtPosition(position);
            // String selectedValue = (String) getListAdapter().getItem(position);
            Intent myIntent = new Intent(Intent.ACTION_VIEW);
            Log.d("Krishna", "Open " + newDir.getAbsolutePath()+"/"+reportData.getReportName());
            //File f = new File(newDir.getAbsolutePath()+"/"+reportData.getReportName());

            String root = Environment.getExternalStorageDirectory().toString();
            File photostore = new File(root + "/Vaidya/MyReports");
            Log.d("Krishna", "photostorage" + photostore);
            System.out.println("Photostorage is " + photostore);
            System.out.println("Photostorage is "+photostore.toString());
            System.out.println("Photostorage is "+photostore.toURI());


            photofile = new File(photostore, reportData.getReportName());

            myIntent.setData(Uri.fromFile(photofile));
            Intent j = Intent.createChooser(myIntent, "TODO : Display the report");
            startActivity(j);
            Toast.makeText(this, reportData.getReportName(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Welcome Dude", Toast.LENGTH_SHORT).show();
        }
    }

    public void takePhoto(View view, String reportName) {

        Date today = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        this.date = dateFormat.format(today);
        Random rn = new Random();

        this.reportName = reportName;//+rn.nextInt();
        Log.d("Krishna", "reportName got it " +reportName);

        //File photostorage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String root = Environment.getExternalStorageDirectory().toString();
        File photostorage = new File(root + "/Vaidya/MyReports");
        Log.d("Krishna", "photostorage" + photostorage);

        photofile = new File(photostorage, reportName +".jpg");
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //intent to start camera
        i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photofile));
        startActivityForResult(i, TAKENPHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKENPHOTO){
            Bitmap photo2;
            try{
                Log.d("Krishna", "1");
                photo2 = (Bitmap) data.getExtras().get("data");
            }
            catch(NullPointerException ex){
                Log.d("Krishna", "2");
                BitmapFactory.Options options2 = new BitmapFactory.Options();
                //options2.inSampleSize = 4;
                photo2 = BitmapFactory.decodeFile(newDir.getAbsolutePath(),options2);

                //photo = BitmapFactory.decodeFile(photofile.getAbsolutePath(),options);
            }

            String root = Environment.getExternalStorageDirectory().toString();
            newDir = new File(root + "/Vaidya/MyReports");
            if(!newDir.exists()) {
                Log.d("Krishna", "3");
                newDir.mkdirs();
            } else {
                Log.d("Krishna", "4");
            }
            Date today = new Date();
            File file = new File (newDir, reportName+".jpg");
//            if (file.exists ()) {
//                file.delete();
//                Log.d("Krishna", "5");
//            }else{
//                Log.d("Krishna", "6" + file.getAbsolutePath());
//            }

            try {
                Log.d("Krishna", "7");
                FileOutputStream out = new FileOutputStream(file);
                photo2.compress(Bitmap.CompressFormat.JPEG, 90, out);
                Log.d("Krishna", "8");
                out.flush();

                out.close();
                Log.d("Krishna", "File is " + file.getAbsolutePath());
                Toast.makeText(getApplicationContext(), "Saved file name "+file.getAbsolutePath(), Toast.LENGTH_SHORT ).show();
                Log.d("Krishna", "Bitmap is "  +photo2.getHeight());
                
                Drawable drawable = new BitmapDrawable(getResources(), photo2);



                ReportData reportData = new ReportData(reportName, getResources().getDrawable(R.drawable.medreport),date);
                my_reports.add(reportData);
            } catch (Exception e) {

            }
        }
    }



    protected String showInputDialog(final View view) {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(ListReportActivity.this);
        View promptView = layoutInflater.inflate(R.layout.report_name, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ListReportActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Upload Record", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        takePhoto(view, editText.getText().toString());
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
        return editText.getText().toString();
    }


    private void clean(){
        String root = Environment.getExternalStorageDirectory().toString();
        newDir = new File(root + "/Vaidya/MyReports");
        String[]entries = newDir.list();
        for(String s: entries){
            File currentFile = new File(newDir.getPath(),s);
            currentFile.delete();
        }
    }
    private void onLoad(){
        String root = Environment.getExternalStorageDirectory().toString();
        newDir = new File(root + "/Vaidya/MyReports");
        if(!newDir.exists()) {
            Log.d("Krishna", "11");
            newDir.mkdirs();
            my_reports.clear();
        } else {
            Log.d("Krishna", "12");
            Log.d("Krishna",newDir + " ");
            String records[]= newDir.list();
            for(String s: records ){
                Log.d("Krishna",s+"");
            }
            for(String record : records){
                Log.d("Krishna", "File name is "+ newDir.getAbsolutePath()+"/"+record);
                String data[] =record.split("-");
                String name = data[0];
                Date date = new Date();
                Log.d("Krishna", "Dir name is "+ newDir.getAbsolutePath());
                BitmapFactory.Options options = new BitmapFactory.Options();
                //options.inSampleSize = 4;
                Bitmap photo1 = BitmapFactory.decodeFile(newDir.getAbsolutePath(),options);
                Drawable d =getResources().getDrawable(R.drawable.medreport);// new BitmapDrawable(getResources(), photo1);
                //Drawable d = Drawable.createFromPath(newDir.getAbsolutePath()+"/"+record);
                boolean flag =false;
                for(ReportData reportData: my_reports){
                    if(reportData.getReportName().replace(".jpg","").equals(name.replace(".jpg", ""))){
                       flag=true;
                        Log.d("Krishna","flag is true");
                    }
                }
                if(!flag) {
                    Log.d("Krishna","flag is 0");
                    my_reports.add(new ReportData(name.replace(".jpg", ""), d, date.toString()));
                }
            }
        }
    }
}