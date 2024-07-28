package Main;

import Metodos.*;
import Interface.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

public class Main {
    private static AutomatoPilha automato;
    static String cadeia;
    private static JTextArea logArea;

    public static void main(String[] args) {
    	criarInterfaceGrafica();
    	inicializarAutomato();
        
    }

    private static void inicializarAutomato() {
        Set<Estado> estados = new HashSet<>(Arrays.asList(
          //  new EstadoImpl("q0"),
            //new EstadoImpl("q1"),
            //new EstadoImpl("q2")
        ));

        Set<Character> alfabetoEntrada = new HashSet<>(Arrays.asList('a', 'b', 'c'));

        Set<Character> alfabetoPilha = new HashSet<>(Arrays.asList('Z', 'A'));

        Estado estadoInicial = new EstadoImpl("q0");

        Set<Estado> estadosFinais = new HashSet<>(Arrays.asList(
            //new EstadoImpl("q2")
        ));

        automato = new AutomatoPilhaImpl(estados, alfabetoEntrada, alfabetoPilha, estadoInicial, estadosFinais);

        configurarAutomato(automato);
    }

    private static void configurarAutomato(AutomatoPilha automato) {
       // automato.adicionarTransicao(new TransicaoImpl(new EstadoImpl("q0"), 'a', 'Z', new EstadoImpl("q0"), new char[] {'A', 'Z'}));
        //automato.adicionarTransicao(new TransicaoImpl(new EstadoImpl("q0"), 'a', 'A', new EstadoImpl("q0"), new char[] {'A', 'A'}));

        //automato.adicionarTransicao(new TransicaoImpl(new EstadoImpl("q0"), 'c', 'A', new EstadoImpl("q1"), new char[] {'A'}));

        //automato.adicionarTransicao(new TransicaoImpl(new EstadoImpl("q1"), 'b', 'A', new EstadoImpl("q1"), new char[] {}));
        //automato.adicionarTransicao(new TransicaoImpl(new EstadoImpl("q1"), 'ε', 'Z', new EstadoImpl("q2"), new char[] {'Z'}));
    	
    	// Transições a serem cadastradas via interface:
        // (q0, a, Z) -> (q0, AZ)
        // (q0, a, A) -> (q0, AA)
        // (q0, c, A) -> (q1, A)
        // (q1, b, A) -> (q1, ε)
        // (q1, ε, Z) -> (q2, Z)
        
        logArea.append("Por favor, cadastre as seguintes transições via interface:\n");
        //logArea.append("1. (q0, a, Z) -> (q0, AZ)\n");
        //logArea.append("2. (q0, a, A) -> (q0, AA)\n");
        //logArea.append("3. (q0, c, A) -> (q1, A)\n");
        //logArea.append("4. (q1, b, A) -> (q1, ε)\n");
        //logArea.append("5. (q1, ε, Z) -> (q2, Z)\n");
    }

    private static void criarInterfaceGrafica() {
        JFrame frame = new JFrame("Autômato de Pilha");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        JLabel label = new JLabel("Insira a cadeia para ser processada:");
        JTextField textField = new JTextField(20);
        JButton button = new JButton("Processar");

        inputPanel.add(label);
        inputPanel.add(textField);
        inputPanel.add(button);

        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadeia = textField.getText();
                StringBuilder log = new StringBuilder();
                boolean aceita = automato.processarCadeia(cadeia, log);
                logArea.setText(log.toString());
            }
        });

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(3, 1));

        JPanel transitionPanel = new JPanel();
        transitionPanel.setLayout(new GridLayout(7, 2));
        transitionPanel.setBorder(BorderFactory.createTitledBorder("Adicionar Transição"));

        JLabel origemLabel = new JLabel("Estado de Origem:");
        JTextField origemField = new JTextField(5);

        JLabel entradaLabel = new JLabel("Símbolo de Entrada:");
        JTextField entradaField = new JTextField(1);

        JLabel pilhaLabel = new JLabel("Símbolo do topo da Pilha:");
        JTextField pilhaField = new JTextField(1);

        JLabel destinoLabel = new JLabel("Estado de Destino:");
        JTextField destinoField = new JTextField(5);

        JLabel novosSimbolosLabel = new JLabel("Novos Símbolos da Pilha:");
        JTextField novosSimbolosField = new JTextField(5);

        JButton adicionarTransicaoButton = new JButton("Adicionar Transição");

        transitionPanel.add(origemLabel);
        transitionPanel.add(origemField);
        transitionPanel.add(entradaLabel);
        transitionPanel.add(entradaField);
        transitionPanel.add(pilhaLabel);
        transitionPanel.add(pilhaField);
        transitionPanel.add(destinoLabel);
        transitionPanel.add(destinoField);
        transitionPanel.add(novosSimbolosLabel);
        transitionPanel.add(novosSimbolosField);
        transitionPanel.add(new JLabel()); // Empty space
        transitionPanel.add(adicionarTransicaoButton);

        adicionarTransicaoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (origemField.getText().isEmpty() || pilhaField.getText().isEmpty() || destinoField.getText().isEmpty()) {
                    logArea.append("Erro: Os campos de origem, pilha e destino devem ser preenchidos.\n");
                    return;
                }

                Estado origem = new EstadoImpl(origemField.getText());
                char entrada = entradaField.getText().isEmpty() ? 'ε' : entradaField.getText().charAt(0);
                char pilha = pilhaField.getText().charAt(0);
                Estado destino = new EstadoImpl(destinoField.getText());
                char[] novosSimbolos;

                if (novosSimbolosField.getText().isEmpty() || novosSimbolosField.getText().equals("ε")) {
                    novosSimbolos = new char[0]; // Desempilha sem empilhar nada
                } else {
                    novosSimbolos = novosSimbolosField.getText().toCharArray();
                }

                Transicao transicao = new TransicaoImpl(origem, entrada, pilha, destino, novosSimbolos);
                automato.adicionarTransicao(transicao);

                logArea.append("Transição adicionada: (" + origem.getNome() + ", " + 
                               (entrada == 'ε' ? "ε" : entrada) + ", " + pilha + ") -> (" + 
                               destino.getNome() + ", " + 
                               (novosSimbolos.length == 0 ? "ε" : new String(novosSimbolos)) + ")\n");
            }
        });

        JPanel statePanel = new JPanel();
        statePanel.setLayout(new GridLayout(3, 2));
        statePanel.setBorder(BorderFactory.createTitledBorder("Adicionar Estado"));

        JLabel estadoLabel = new JLabel("Nome do Estado:");
        JTextField estadoField = new JTextField(10);

        JButton adicionarEstadoButton = new JButton("Adicionar Estado");

        statePanel.add(estadoLabel);
        statePanel.add(estadoField);
        statePanel.add(new JLabel()); // Empty space
        statePanel.add(adicionarEstadoButton);

        adicionarEstadoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Estado novoEstado = new EstadoImpl(estadoField.getText());
                automato.adicionarEstado(novoEstado);

                logArea.append("Estado adicionado: " + novoEstado.getNome() + "\n");
            }
        });

        JPanel finalStatePanel = new JPanel();
        finalStatePanel.setLayout(new GridLayout(3, 2));
        finalStatePanel.setBorder(BorderFactory.createTitledBorder("Adicionar Estado Final"));

        JLabel estadoFinalLabel = new JLabel("Nome do Estado Final:");
        JTextField estadoFinalField = new JTextField(10);

        JButton adicionarEstadoFinalButton = new JButton("Adicionar Estado Final");

        finalStatePanel.add(estadoFinalLabel);
        finalStatePanel.add(estadoFinalField);
        finalStatePanel.add(new JLabel()); // Empty space
        finalStatePanel.add(adicionarEstadoFinalButton);

        adicionarEstadoFinalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Estado estadoFinal = new EstadoImpl(estadoFinalField.getText());
                automato.adicionarEstadoFinal(estadoFinal);

                logArea.append("Estado final adicionado: " + estadoFinal.getNome() + "\n");
            }
        });

        controlPanel.add(transitionPanel);
        controlPanel.add(statePanel);
        controlPanel.add(finalStatePanel);

        frame.add(controlPanel, BorderLayout.EAST);

        frame.setVisible(true);
    }
}
