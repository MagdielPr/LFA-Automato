package Metodos;
import interfac.*;

public class TransicaoImpl implements Transicao {
    private final Estado estadoAtual;
    private final char simboloEntrada;
    private final char topoPilha;
    private final Estado proximoEstado;
    private final char[] novosSimbolos;

    public TransicaoImpl(Estado estadoAtual, char simboloEntrada, char topoPilha, Estado proximoEstado, char[] novosSimbolos) {
        this.estadoAtual = estadoAtual;
        this.simboloEntrada = simboloEntrada;
        this.topoPilha = topoPilha;
        this.proximoEstado = proximoEstado;
        this.novosSimbolos = novosSimbolos;
    }

    @Override
    public Estado getEstadoAtual() {
        return estadoAtual;
    }

    @Override
    public char getSimboloEntrada() {
        return simboloEntrada;
    }

    @Override
    public char getTopoPilha() {
        return topoPilha;
    }

    @Override
    public Estado getProximoEstado() {
        return proximoEstado;
    }

    @Override
    public char[] getNovosSimbolos() {
        return novosSimbolos;
    }
}