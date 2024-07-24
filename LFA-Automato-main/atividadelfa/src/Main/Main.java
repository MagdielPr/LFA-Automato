package Main;

import Metodos.*;
import interfac.*;
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
        inicializarAutomato();
        criarInterfaceGrafica();
    }

    private static void inicializarAutomato() {
        Set<Estado> estados = new HashSet<>(Arrays.asList(
            new EstadoImpl("q0"),
            new EstadoImpl("q1"),
            new EstadoImpl("q2")
        ));

        Set<Character> alfabetoEntrada = new HashSet<>(Arrays.asList('a', 'b', 'c'));

        Set<Character> alfabetoPilha = new HashSet<>(Arrays.asList('Z', 'A'));

        Estado estadoInicial = new EstadoImpl("q0");

        Set<Estado> estadosFinais = new HashSet<>(Arrays.asList(
            new EstadoImpl("q2")
        ));

        automato = new AutomatoPilhaImpl(estados, alfabetoEntrada, alfabetoPilha, estadoInicial, estadosFinais);

        configurarAutomato(automato);
    }

    private static void configurarAutomato(AutomatoPilha automato) {
        // Transições para a linguagem (a^n) c (b^n) onde n > 0
        automato.adicionarTransicao(new TransicaoImpl(new EstadoImpl("q0"), 'a', 'Z', new EstadoImpl("q0"), new char[] {'A', 'Z'}));
        automato.adicionarTransicao(new TransicaoImpl(new EstadoImpl("q0"), 'a', 'A', new EstadoImpl("q0"), new char[] {'A', 'A'}));

        automato.adicionarTransicao(new TransicaoImpl(new EstadoImpl("q0"), 'c', 'A', new EstadoImpl("q1"), new char[] {'A'}));

        automato.adicionarTransicao(new TransicaoImpl(new EstadoImpl("q1"), 'b', 'A', new EstadoImpl("q1"), new char[] {}));
        automato.adicionarTransicao(new TransicaoImpl(new EstadoImpl("q1"), 'ε', 'Z', new EstadoImpl("q2"), new char[] {'Z'}));
    }

    private static void criarInterfaceGrafica() {
        JFrame frame = new JFrame("Autômato de Pilha");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
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
                processarCadeia(cadeia);
            }
        });
        
        JPanel configPainel = new JPanel();
        configPainel.setLayout(new GridLayout(7, 2));
        
        JLabel configLabel = new JLabel("Estado de Origem:");
        JTextField configField = new JTextField(5);
        
        JButton aconfigPainelButton = new JButton("Adicionar Transição");

        configPainel.add(configLabel);
        configPainel.add(configField);
        

        JPanel transitionPanel = new JPanel();
        transitionPanel.setLayout(new GridLayout(7, 2));

        JLabel origemLabel = new JLabel("Estado de Origem:");
        JTextField origemField = new JTextField(5);

        JLabel entradaLabel = new JLabel("Símbolo de Entrada:");
        JTextField entradaField = new JTextField(1);

        JLabel pilhaLabel = new JLabel("Símbolo da Pilha:");
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
        transitionPanel.add(new JLabel()); // placeholder
        transitionPanel.add(adicionarTransicaoButton);

        frame.add(transitionPanel, BorderLayout.SOUTH);

        adicionarTransicaoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String origem = origemField.getText();
                char entrada = entradaField.getText().charAt(0);
                char pilha = pilhaField.getText().charAt(0);
                String destino = destinoField.getText();
                char[] novosSimbolos = novosSimbolosField.getText().toCharArray();

                Transicao novaTransicao = new TransicaoImpl(new EstadoImpl(origem), entrada, pilha, new EstadoImpl(destino), novosSimbolos);
                automato.adicionarTransicao(novaTransicao);
                logArea.append("Transição adicionada: (" + origem + ", " + entrada + ", " + pilha + ") -> (" + destino + ", " + Arrays.toString(novosSimbolos) + ")\n");
            }
        });

        frame.setVisible(true);
    }

    private static void processarCadeia(String cadeia) {
        logArea.setText("");
        automato.processarCadeia(cadeia, Main::adicionarLog);
    }

    public static void adicionarLog(String mensagem) {
        logArea.append(mensagem + "\n");
    }
}
