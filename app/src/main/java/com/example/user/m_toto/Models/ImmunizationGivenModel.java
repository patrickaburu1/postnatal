package com.example.user.m_toto.Models;

/**
 * Created by user on 5/28/2017.
 */

public class ImmunizationGivenModel {
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public  String age;
    public String getImmunization_name() {
        return immunization_name;
    }

    public void setImmunization_name(String immunization_name) {
        this.immunization_name = immunization_name;
    }

    public String getDate_given() {
        return date_given;
    }

    public void setDate_given(String date_given) {
        this.date_given = date_given;
    }

    public  String immunization_name,date_given;
}
