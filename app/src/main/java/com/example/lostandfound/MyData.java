package com.example.lostandfound;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "myTable")
public class MyData {
    //primary key
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo (name = "postType")
    public String postType;
    @ColumnInfo (name = "itemName")
    public String itemName;
    @ColumnInfo (name = "phone")
    public String phone;
    @ColumnInfo (name = "description")
    public String description;

    @ColumnInfo (name = "itemDate")
    public String itemDate;

    @ColumnInfo (name = "location")
    public String location;

    @ColumnInfo (name = "itemLat")
    public Double itemLat;

    @ColumnInfo (name = "itemLng")
    public Double itemLng;


    public MyData(String postType, String itemName, String phone, String description, String itemDate, String location, Double itemLat, Double itemLng){
        this.postType = postType;
        this.itemName = itemName;
        this.phone = phone;
        this.description = description;
        this.itemDate = itemDate;
        this.location = location;
        this.itemLat = itemLat;
        this.itemLng = itemLng;
    }

    //Latlng could change back to single value
    public int getId() {
        return id;
    }

    public double getLat(){return itemLat;}
    public double getLng(){return itemLng;}

    public String getListTitle(){return this.postType + " " +this.itemName;}

    public String getItemListing(){
        return this.postType + " " + this.itemName + "\n" + this.itemDate + "\n" + this.location + "\n" + this.description + "\n" + this.phone;
    }

}