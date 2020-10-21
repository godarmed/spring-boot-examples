package com.zzy.activiti.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @Classname Person
 * @Description TODO
 * @Date 2020/7/27 17:34
 * @Created by Zzy
 */

public class Person implements Serializable {

    private String name;

    private Integer id;

    private Date date;

    private String note;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        return Objects.equals(name, person.name) &&
                Objects.equals(id, person.id) &&
                Objects.equals(date, person.date) &&
                Objects.equals(note, person.note);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, id, date, note);
    }
}
