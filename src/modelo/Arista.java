package modelo;

import utiles.Modo;

//Las aristas son clases que guardan informacion. Ya tienen creados los getters, setters y constructores.
public record Arista(String destino, int minutos, Modo modo, String linea) {
}
