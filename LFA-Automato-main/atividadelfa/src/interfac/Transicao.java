package interfac;

public interface Transicao {
    Estado getEstadoOrigem();
    char getSimboloEntrada();
    char getSimboloPilha();
    Estado getEstadoDestino();
    char[] getSimbolosPilha();
}
