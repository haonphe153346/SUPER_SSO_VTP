package com.example.SUPER_SSO_VTP.entity;


import net.minidev.json.JSONObject;

import java.io.Serializable;

public class UserEntity implements Serializable {
    JSONObject infos;

    public UserEntity() {
    }

    public UserEntity(JSONObject infos) {
        this.infos = infos;
    }

    public JSONObject getInfos() {
        return infos;
    }

    public void setInfos(JSONObject infos) {
        this.infos = infos;
    }

    public boolean verify() {
        long exp = infos.containsKey("exp") ? infos.getAsNumber("exp").longValue() : 0;
        return exp == 0 || exp > System.currentTimeMillis();
    }

    public Long getUserId() {
        return infos.getAsNumber("userid").longValue();
    }

    public String getPost() {
        return infos.getAsString("ma_buucuc");
    }

    public String getOrg() {
        return infos.getAsString("don_vi");
    }

    public String getName() {
        return infos.getAsString("name");
    }

    public String getPhone() {
        return infos.getAsString("phone");
    }

    public String getMaChucDanh() {
        return infos.getAsString("ma_chucdanh");
    }

    public String getUsername() {
        return infos.getAsString("username");
    }

    public String getFirstname() {
        return infos.getAsString("firstname");
    }

    public String getLastname() {
        return infos.getAsString("lastname");
    }

    public String getEmail() {
        return infos.getAsString("email");
    }

    public String getManhanvien() {
        return infos.getAsString("manhanvien");
    }

    public String getDn_userid() {
        return infos.getAsString("dn_userid");
    }

    public String getMa_buucuc() {
        return infos.getAsString("ma_buucuc");
    }

    public String getChi_nhanh() {
        return infos.getAsString("don_vi");
    }

    public String getMa_chucdanh() {
        return infos.getAsString("ma_chucdanh");
    }

    public String getEmployeeGroupId() {
        return infos.getAsString("employee_group_id");
    }

    public Integer getSource() {
        String sourceTmp = infos.getAsString("source");
//        if (Utils.isNullOrEmpty(sourceTmp) || !StringUtils.isNumeric(sourceTmp)) {
//        if (Utils.isNullOrEmpty(sourceTmp)) {
//            return -2;
//        }
        return Integer.valueOf(sourceTmp);
    }
}
