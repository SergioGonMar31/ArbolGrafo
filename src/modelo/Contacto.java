package modelo;

public class Contacto {
    private String nombre;
    private String apellidos;
    private String telefono;
    private String email;            // opcional: cadena vacía si no hay

    public Contacto(String nombre, String apellidos, String telefono, String email) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.email = (email == null) ? "" : email;
    }

    public String getNombre() { return nombre; }
    public String getApellidos() { return apellidos; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return apellidos + ", " + nombre + " | " + telefono + (email.isEmpty() ? "" : " | " + email);
    }
}