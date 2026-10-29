package utiles;

public enum Modo{
/** Forma de recorrer un tramo. */
    ANDANDO("a pie"),
    BICI("en bici"),
    BUS("en bus"),
    METRO("en metro");

    private final String texto;

    Modo(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

    /** Bus y metro son transporte público: tienen línea, espera y cuentan para transbordos. */
    public boolean esTransporte() {
        return this == BUS || this == METRO;
    }
}
