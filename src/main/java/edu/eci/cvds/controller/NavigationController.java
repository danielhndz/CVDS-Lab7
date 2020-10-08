package edu.eci.cvds.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;

@SuppressWarnings("deprecation")
@ManagedBean(name = "navigationController", eager = true)
@RequestScoped
public class NavigationController implements Serializable {

    private static final long serialVersionUID = 1L;

    public String viewIndex() {
        return "index";
    }

    public String viewRegistroAlquiler() {
        System.out.println("a");
        return "registro-alquiler";
    }

    public String viewRegistroCliente() {
        return "registro-cliente";
    }
}
