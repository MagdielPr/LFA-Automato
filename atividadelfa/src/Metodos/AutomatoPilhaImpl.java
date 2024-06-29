package Metodos;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Collections;

import interfac.*;

public class AutomatoPilhaImpl implements AutomatoPilha {
    private final Set<Estado> estados;
    private final Set<Character> alfabetoEntrada;
    private final Set<Character> alfabetoPilha;
    private final Map<Estado, Map<Character, Map<Character, List<Transicao>>>> funcaoTransicao;
    private final Estado estadoInicial;
    private final Set<Estado> estadosFinais;
    private Stack<Character> pilha;

    public AutomatoPilhaImpl(Set<Estado> estados, Set<Character> alfabetoEntrada, Set<Character> alfabetoPilha, Estado estadoInicial, Set<Estado> estadosFinais) {
        this.estados = estados;
        this.alfabetoEntrada = alfabetoEntrada;
        this.alfabetoPilha = alfabetoPilha;
        this.estadoInicial = estadoInicial;
        this.estadosFinais = estadosFinais;
        this.funcaoTransicao = new HashMap<>();
        this.pilha = new Stack<>();
    }

    @Override
    public void adicionarTransicao(Transicao transicao) {
        funcaoTransicao
            .computeIfAbsent(transicao.getEstadoAtual(), k -> new HashMap<>())
            .computeIfAbsent(transicao.getSimboloEntrada(), k -> new HashMap<>())
            .computeIfAbsent(transicao.getTopoPilha(), k -> new ArrayList<>())
            .add(transicao);
    }

    @Override
    public boolean processarCadeia(String cadeia) {
        pilha.clear();
        pilha.push('Z'); 
        return processarCadeia(estadoInicial, cadeia, 0);
    }

    private boolean processarCadeia(Estado estadoAtual, String cadeia, int pos) {
        if (pos == cadeia.length()) {
            return estadosFinais.contains(estadoAtual) && pilha.size() == 1 && pilha.peek() == 'Z';
        }
        
        char simboloEntrada = pos < cadeia.length() ? cadeia.charAt(pos) : 0;
        char topoPilha = pilha.isEmpty() ? 0 : pilha.pop();

        List<Transicao> transicoes = funcaoTransicao
            .getOrDefault(estadoAtual, Collections.emptyMap())
            .getOrDefault(simboloEntrada, Collections.emptyMap())
            .getOrDefault(topoPilha, Collections.emptyList());

        for (Transicao transicao : transicoes) {
            Stack<Character> backupPilha = (Stack<Character>) pilha.clone();
            for (int i = transicao.getNovosSimbolos().length - 1; i >= 0; i--) {
                if (transicao.getNovosSimbolos()[i] != 'ε') {
                    pilha.push(transicao.getNovosSimbolos()[i]);
                }
            }
            if (processarCadeia(transicao.getProximoEstado(), cadeia, pos + 1)) {
                return true;
            }
            pilha = backupPilha;
        }

        if (topoPilha != 0) {
            pilha.push(topoPilha);
        }

        return false;
    }
}
