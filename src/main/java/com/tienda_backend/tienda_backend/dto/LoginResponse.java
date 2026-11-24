
package com.tienda_backend.tienda_backend.dto;

public class LoginResponse {

    private String token;
    private String tipo;
    private String id;
    private String nombre;
    private String email;
    private String rol;

    public LoginResponse(
            String token,
            String id,
            String nombre,
            String email,
            String rol) {

        this.token = token;
        this.tipo = "Bearer";
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
    }

    public String getToken() {
        return token;
    }

    public String getTipo() {
        return tipo;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getRol() {
        return rol;
    }
}
