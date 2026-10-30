package modelo;

import utiles.Modo;

/** Ciudad de ejemplo para probar el planificador. */
public class CiudadDemo {

    public static Grafo crear() {
        Grafo g = new Grafo();

        // A pie y en bici (ambos sentidos)
        g.agregarDoble("Casa", "Plaza", 10, Modo.ANDANDO, "");
        g.agregarDoble("Casa", "Plaza", 4, Modo.BICI, "");
        g.agregarDoble("Casa", "Parque", 15, Modo.ANDANDO, "");
        g.agregarDoble("Casa", "Parque", 6, Modo.BICI, "");
        g.agregarDoble("Parque", "Universidad", 12, Modo.ANDANDO, "");
        g.agregarDoble("Parque", "Universidad", 5, Modo.BICI, "");
        g.agregarDoble("Plaza", "Estacion", 8, Modo.ANDANDO, "");
        g.agregarDoble("Plaza", "Centro", 22, Modo.ANDANDO, "");
        g.agregarDoble("Mercado", "Hospital", 12, Modo.ANDANDO, "");
        g.agregarDoble("Centro", "Hospital", 25, Modo.ANDANDO, "");

        // Metro M1 (ambos sentidos): Estacion - Universidad - Centro
        g.agregarDoble("Estacion", "Universidad", 5, Modo.METRO, "M1");
        g.agregarDoble("Universidad", "Centro", 4, Modo.METRO, "M1");

        // Bus L1 CIRCULAR (un solo sentido): Plaza -> Centro -> Mercado -> Plaza
        g.agregarDirigida("Plaza", "Centro", 6, Modo.BUS, "L1");
        g.agregarDirigida("Centro", "Mercado", 5, Modo.BUS, "L1");
        g.agregarDirigida("Mercado", "Plaza", 7, Modo.BUS, "L1");

        // Bus L2 (ambos sentidos)
        g.agregarDoble("Centro", "Hospital", 8, Modo.BUS, "L2");

        // Calle de sentido único para bicis: Centro -> Hospital
        g.agregarDirigida("Centro", "Hospital", 10, Modo.BICI, "");

        return g;
    }
}