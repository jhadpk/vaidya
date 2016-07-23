package com.inmobi.app.reports;


import android.graphics.drawable.Drawable;
import android.net.Uri;

/**
 * Created by krishna.tiwari on 08/08/15.
 */
public class ReportData {
    private String reportName;
    private Uri reportImage;
    private Drawable preview;
    private String Date;

    public ReportData(String reportName, Drawable preview, String date) {
        this.reportName = reportName;
        this.reportImage = reportImage;
        this.preview = preview;
        Date = date;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public Uri getReportImage() {
        return reportImage;
    }

    public void setReportImage(Uri reportImage) {
        this.reportImage = reportImage;
    }

    public Drawable getPreview() {
        return preview;
    }

    public void setPreview(Drawable preview) {
        this.preview = preview;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
