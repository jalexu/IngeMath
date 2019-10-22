package com.estudiates.ingemath.modelo;

public class Estudiante {

    private String nombre;
    private String genenero;
    private String fechaNacimiento;
    private String numeroTelefonico;
    private String correo;

    public  Estudiante(){}

    public Estudiante(String nombre, String genenero, String fechaNacimiento, String numeroTelefonico, String correo) {
        this.nombre = nombre;
        this.genenero = genenero;
        this.fechaNacimiento = fechaNacimiento;
        this.numeroTelefonico = numeroTelefonico;
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenenero() {
        return genenero;
    }

    public void setGenenero(String genenero) {
        this.genenero = genenero;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNumeroTelefonico() {
        return numeroTelefonico;
    }

    public void setNumeroTelefonico(String numeroTelefonico) {
        this.numeroTelefonico = numeroTelefonico;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
