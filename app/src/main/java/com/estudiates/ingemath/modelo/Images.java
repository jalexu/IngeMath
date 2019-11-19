package com.estudiates.ingemath.modelo;

public class Images {

    private Integer imagen;
    private Integer puntaje;

    public Images() {
    }

    public Images(Integer imagen, Integer puntaje) {
        this.imagen = imagen;
        this.puntaje = puntaje;
    }

    public Integer getImagen() {
        return imagen;
    }

    public void setImagen(Integer imagen) {
        this.imagen = imagen;
    }

    public Integer getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(Integer puntaje) {
        this.puntaje = puntaje;
    }
}
