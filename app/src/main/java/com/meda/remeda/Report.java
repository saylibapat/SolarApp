package com.meda.remeda;

import android.app.Application;
//import java.util.Date;

/**
 * Created by anushree on 15/8/17.
 */


public class Report extends Application{
    public int ReportId;
    public String ModelNo;
    public String date;
    public String ModelName,Location,Manufacturer,Username;
    public String Barcode;
    public Double Latitude,Longitude;
    public byte[] Image;

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String Remarks;

    public Report(){
        ReportId=-1;
        Remarks=null;
        /*ModelNo=new String();
        ModelName=new String();
        Manufacturer=new String();
        Location=new String();
        date=new String();
        Username=new String();*/
        Username=null;
        ModelNo=null;
        date=null;
        ModelName=null;
        Location=null;
        Manufacturer=null;
        Barcode=null;
        Image=null;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public String getUsername()
    {
        return this.Username;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) { this.date = date;
    }

    public void setUsername(String username)
    {
        this.Username=username;

    }
    public int getReportId()
    {
        return this.ReportId;
    }
    public String getModelNo()
    {
        return this.ModelNo;
    }
    public String getModelName()
    {
        return ModelName;
    }
    public String getLocation()
    {
        return Location;
    }

    public void setReportId(int reportId) {
        ReportId = reportId;
    }

    public void setModelNo(String modelNo) {
        ModelNo = modelNo;
    }

    public void setModelName(String modelName) {
        ModelName = modelName;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public void setManufacturer(String manufacturer) {
        Manufacturer = manufacturer;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }

    public void setImage(byte[] image) {
        Image = image;
    }

    public String getManufacturer()

    {
        return Manufacturer;
    }
    public String getBarcode()
    {
        return Barcode;
    }
    public byte[] getImage()
    {
        return Image;
    }
}
