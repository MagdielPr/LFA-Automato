package Metodos;

import interfac.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.function.Consumer;

public class AutomatoPilhaImpl implements AutomatoPilha {
    private Set<Estado> estados;
    private Set<Character> alfabetoEntrada;
    private Set<Character> alfabetoPilha;
    private Estado estadoInicial;
    private Set<Estado> estadosFinais;
    private Set<Transicao> transicoes;

    public AutomatoPilhaImpl(Set<Estado> estados, Set<Character> alfabetoEntrada, Set<Character> alfabetoPilha, Estado estadoInicial, Set<Estado> estadosFinais) {
        this.estados = estados;
        this.alfabetoEntrada = alfabetoEntrada;
        this.alfabetoPilha = alfabetoPilha;
        this.estadoInicial = estadoInicial;
        this.estadosFinais = estadosFinais;
        this.transicoes = new HashSet<>();
    }

    @Override
    public void adicionarTransicao(Transicao transicao) {
        transicoes.add(transicao);
    }

    @Override
    public boolean processarCadeia(String cadeia, Consumer<String> logger) {
        Stack<Character> pilha = new Stack<>();
        pilha.push('Z');
        Estado estadoAtual = estadoInicial;
        int pos = 0;

        logger.accept(gerarRepresentacao(estadoAtual, pilha));

        while (pos <= cadeia.length()) {
            char simboloEntrada = pos < cadeia.length() ? cadeia.charAt(pos) : 'ε';
            boolean transicaoEncontrada = false;

            for (Transicao transicao : transicoes) {
                if (transicao.getEstadoOrigem().equals(estadoAtual) &&
                    transicao.getSimboloEntrada() == simboloEntrada &&
                    transicao.getSimboloPilha() == pilha.peek()) {
                    
                    estadoAtual = transicao.getEstadoDestino();
                    pilha.pop();
                    for (int i = transicao.getSimbolosPilha().length - 1; i >= 0; i--) {
                        pilha.push(transicao.getSimbolosPilha()[i]);
                    }

                    logger.accept(gerarRepresentacao(estadoAtual, pilha) + " - Transição: (" +
                            transicao.getEstadoOrigem().getNome() + ", " + simboloEntrada + ", " +
                            transicao.getSimboloPilha() + ") -> (" + estadoAtual.getNome() + ")");
                    
                    transicaoEncontrada = true;
                    if (simboloEntrada != 'ε') pos++;
                    break;
                }
            }

            if (!transicaoEncontrada) {
                logger.accept("Nenhuma transição encontrada para (" + estadoAtual.getNome() + ", " + simboloEntrada + ", " + pilha.peek() + ")");
                break;
            }
        }

        boolean aceita = estadosFinais.contains(estadoAtual) && pilha.peek() == 'Z' && cadeia.contains("a") && cadeia.contains("b");
        if(aceita) {
	        logger.accept(gerarRepresentacao(estadoAtual, pilha) + " - Cadeia foi aceita");
	        return aceita;
	    }
        else {
        	logger.accept(gerarRepresentacao(estadoAtual, pilha) + " - Cadeia não foi aceita");
	        return aceita;
        }
    }

    private String gerarRepresentacao(Estado estadoAtual, Stack<Character> pilha) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n+------------------------+\n");
        sb.append("| Estado Atual: ").append(estadoAtual.getNome()).append(" |\n");
        sb.append("+------------------------+\n");
        sb.append("| Pilha:                 |\n");
        sb.append("+------------------------+\n");
        for (Character c : pilha) {
            sb.append("| ").append(c).append("                      |\n");
        }
        sb.append("+------------------------+\n");
        return sb.toString();
    }
}
