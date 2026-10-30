package modelo;

//Record con la informacion de cada paso dado
public record Paso(String desde, Arista tramo, int minutos, boolean subida) {
}