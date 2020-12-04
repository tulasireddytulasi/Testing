package com.example.doctorapp.ui.maps;

public class MapDataModel {
    String doctorname;
    String address;
    String pic;
    double lat;
    double lag;
    public MapDataModel(String pic, String doctorname, String address, double lat, double lag) {
        this.pic = pic;
        this.doctorname = doctorname;
        this.address = address;
        this.lat = lat;
        this.lag = lag;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDoctorname() {
        return doctorname;
    }

    public void setDoctorname(String doctorname) {
        this.doctorname = doctorname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    public double getLag() {
        return lag;
    }

    public void setLag(int lag) {
        this.lag = lag;
    }
}
