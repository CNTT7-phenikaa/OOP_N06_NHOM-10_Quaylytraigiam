/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlytraigiam.entity;

/**
 *
 * @author Admin
 */
public class SpecialPerson {
    private String id;
    private String name;
    private String reason;
    private String duration;

    public SpecialPerson() {
    }

    public SpecialPerson(String id, String name, String reason, String duration) {
        this.id = id;
        this.name = name;
        this.reason = reason;
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getReason() {
        return reason;
    }

    public String getDuration() {
        return duration;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "SpecialPerson{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               ", reason='" + reason + '\'' +
               ", duration='" + duration + '\'' +
               '}';
    }
}