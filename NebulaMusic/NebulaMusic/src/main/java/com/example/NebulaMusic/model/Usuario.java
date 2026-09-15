package com.example.NebulaMusic.model;

public class Usuario {
    private String nombre;
    private String correo;
    private String contrasenia;
    private String psuedonimo;
    private String genero;
    private String suscripcion;
    private String fechaNacimiento;
    private String terminos;
    private String comentarios;

    public Usuario() {
    }

    public Usuario(String nombre, String correo, String contrasenia, String psuedonimo, String genero, String suscripcion, String fechaNacimiento, String terminos, String comentarios) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasenia = contrasenia;
        this.psuedonimo = psuedonimo;
        this.genero = genero;
        this.suscripcion = suscripcion;
        this.fechaNacimiento = fechaNacimiento;
        this.terminos = terminos;
        this.comentarios = comentarios;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getPsuedonimo() {
        return psuedonimo;
    }

    public void setPsuedonimo(String psuedonimo) {
        this.psuedonimo = psuedonimo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getSuscripcion() {
        return suscripcion;
    }

    public void setSuscripcion(String suscripcion) {
        this.suscripcion = suscripcion;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTerminos() {
        return terminos;
    }

    public void setTerminos(String terminos) {
        this.terminos = terminos;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", psuedonimo='" + psuedonimo + '\'' +
                ", genero='" + genero + '\'' +
                ", suscripcion='" + suscripcion + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", terminos='" + terminos + '\'' +
                ", comentarios='" + comentarios + '\'' +
                '}';
    }
}
