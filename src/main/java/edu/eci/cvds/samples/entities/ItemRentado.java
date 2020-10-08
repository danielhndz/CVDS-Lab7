/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.cvds.samples.entities;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author 2106913
 */
@SuppressWarnings("unused")
public class ItemRentado implements Serializable {

    private int id;
    private Item item;
    private Date fechaInicioRenta;
    private Date fechaFinRenta;

    public ItemRentado(int id, Item item, Date fechaInicioRenta, Date fechaFinRenta) {
        this.id = id;
        this.item = item;
        this.fechaInicioRenta = fechaInicioRenta;
        this.fechaFinRenta = fechaFinRenta;
    }

    public ItemRentado() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Date getFechaInicioRenta() {
        return fechaInicioRenta;
    }

    public void setFechaInicioRenta(Date fechaInicioRenta) {
        this.fechaInicioRenta = fechaInicioRenta;
    }

    public Date getFechaFinRenta() {
        return fechaFinRenta;
    }

    public void setFechaFinRenta(Date fechaFinRenta) {
        this.fechaFinRenta = fechaFinRenta;
    }

    @Override
    public String toString() {
        return "ItemRentado{" + "id=" + id + ", item=" + item + ", fechaInicioRenta=" + fechaInicioRenta + ", fechaFinRenta=" + fechaFinRenta + '}';
    }
}
