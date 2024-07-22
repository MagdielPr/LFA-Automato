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
import java.util.Scanner;

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

        automato.adicionarTransicao(new TransicaoImpl(new EstadoImpl("q0"), 'b', 'A', new EstadoImpl("q1"), new char[] {'A'}));

        automato.adicionarTransicao(new TransicaoImpl(new EstadoImpl("q1"), 'a', 'A', new EstadoImpl("q1"), new char[] {}));
        automato.adicionarTransicao(new TransicaoImpl(new EstadoImpl("q1"), 'ε', 'Z', new EstadoImpl("q2"), new char[] {'Z'}));
    }

    private static void criarInterfaceGrafica() {
        JFrame frame = new JFrame("Autômato de Pilha");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
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
