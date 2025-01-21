package com.example.admin.augscan;

import android.system.Os;

public class Items {
    private String assetImg1;
    private String itemname;
    private String itemcategory;
    private String phone_num;
    private String itembarcode;
    private String dateValue,assign,Device_id, Processor,System_Type, OS_version, RAM, AssetImg;
    private String con, company, model;

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public void setphone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public void setItembarcode(String itembarcode) {
        this.itembarcode = itembarcode;
    }

    public void setDateValue(String dateValue) {
        this.dateValue = dateValue;
    }

    public void setAssign(String assign) {
        this.assign = assign;
    }

    public void setDevice_id(String device_id) {
        Device_id = device_id;
    }

    public void setProcessor(String processor) {
        Processor = processor;
    }

    public void setSysType(String sysType) {
        System_Type = sysType;
    }

    public void setOS_version(String OS_version) {
        this.OS_version = OS_version;
    }

    public void setInstalled_ram(String installed_ram) {
        RAM = installed_ram;
    }

    public void setCon(String con) {
        this.con = con;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setItemcategory(String itemcategory) {
        this.itemcategory = itemcategory;
    }

    public void setAssetImg(String AssetImg){this.AssetImg=AssetImg;}


    public Items() {

    }

    public Items(String itemname,String itemcategory,String phone_num,String itembarcode,String datevalue,String assign,String Processor,String Device_id,String System_Type,String OS_version,String RAM,String AssetImg)
    {
        this.Processor=Processor;
        this.Device_id=Device_id;
        this.System_Type=System_Type;
        this.OS_version=OS_version;
        this.RAM=RAM;
        this.itemname=itemname;
        this.itemcategory=itemcategory;
        this.phone_num=phone_num;
        this.itembarcode= itembarcode;
        this.dateValue=datevalue;
        this.assign=assign;
        this.AssetImg = AssetImg;

    }
    public Items(String itemname,String itemcategory,String phone_num,String itembarcode,String datevalue,String assign,String con,String company,String model,String assetImg1)
    {
        this.itemname=itemname;
        this.itemcategory=itemcategory;
        this.phone_num=phone_num;
        this.itembarcode= itembarcode;
        this.dateValue=datevalue;
        this.assign=assign;
        this.con=con;
        this.company=company;
        this.model=model;
        this.assetImg1=assetImg1;
    }
    public String getProcessor() {
        return Processor;
    }

    public String getDevice_id() {
        return Device_id;
    }
    public String getSysType() {
        return RAM;
    }
    public String getOS_version() {
        return OS_version;
    }
    public String getInstalled_ram() {
        return System_Type;
    }

    public String getItemcategory() {
        return itemcategory;
    }

    //  public String getItemprice() {
    //      return phone_num;
    //  }

    public String getItembarcode() {
        return itembarcode;
    }
    public String getDateValue() {
        return dateValue;
    }

    public String getAssign(){return assign;}
    public String getItemname() {
        return itemname;
    }
    public String getCon(){return con;}
    public String getCompany(){return company;}
    public String getModel(){return  model;}
    public String getAssetImg(){return AssetImg;}
    public String getAssetImg1(){return assetImg1;}
    public String getPhone_num(){return phone_num;}
}