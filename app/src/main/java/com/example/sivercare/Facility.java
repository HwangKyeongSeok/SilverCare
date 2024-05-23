package com.example.sivercare;

import java.util.List;

public class Facility {
    private String city;
    private String district;
    private String subdistrict;
    private String name;
    private Admin admin;
    private List<Staff> staffs;
    private int id;

    public Facility(String city, String district, String subdistrict, String name, Admin admin, List<Staff> staffs, int id) {
        this.city = city;
        this.district = district;
        this.subdistrict = subdistrict;
        this.name = name;
        this.admin = admin;
        this.staffs = staffs;
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public String getSubdistrict() {
        return subdistrict;
    }

    public String getName() {
        return name;
    }

    public Admin getAdmin() {
        return admin;
    }

    public List<Staff> getStaffs() {
        return staffs;
    }

    public int getId() {
        return id;
    }
}
