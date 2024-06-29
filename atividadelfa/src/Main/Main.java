package Main;
import Metodos.*;
import interfac.*;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
        
		 Set<Estado> estados = new HashSet<>(Arrays.asList(
		            new EstadoImpl("q0"),
		            new EstadoImpl("q1"),
		            new EstadoImpl("q2")
		        ));
		 Set<Character> alfabetoEntrada = new HashSet<>(Arrays.asList('a', 'b'));

		 Set<Character> alfabetoPilha = new HashSet<>(Arrays.asList('Z', 'A'));

		        // Definição do estado inicial
         Estado estadoInicial = new EstadoImpl("q0");

		        // Definição dos estados finais
		 Set<Estado> estadosFinais = new HashSet<>(Arrays.asList(
		            new EstadoImpl("q2")
		 ));

		 AutomatoPilha automato = new AutomatoPilhaImpl(estados, alfabetoEntrada, alfabetoPilha, estadoInicial, estadosFinais);

		        // Definição das transições
		 automato.adicionarTransicao(new TransicaoImpl(new EstadoImpl("q0"), 'a', 'Z', new EstadoImpl("q1"), new char[] {'A', 'Z'}));
		 automato.adicionarTransicao(new TransicaoImpl(new EstadoImpl("q1"), 'a', 'A', new EstadoImpl("q1"), new char[] {'A', 'A'}));
		 automato.adicionarTransicao(new TransicaoImpl(new EstadoImpl("q1"), 'b', 'A', new EstadoImpl("q2"), new char[] {'ε'}));
		 automato.adicionarTransicao(new TransicaoImpl(new EstadoImpl("q2"), 'b', 'A', new EstadoImpl("q2"), new char[] {'ε'}));
		 automato.adicionarTransicao(new TransicaoImpl(new EstadoImpl("q2"), (char) 0, 'Z', new EstadoImpl("q2"), new char[] {'Z'}));

		 String cadeia;
		 do {
			 System.out.println("Digite sua cadeia ou fim para finalizar o programa");
			 Scanner entrada = new Scanner(System.in);
			  cadeia = entrada.nextLine();
			 if (automato.processarCadeia(cadeia)) {
			     System.out.println("A cadeia foi aceita pelo autômato.");
			 } else {
			     System.out.println("A cadeia não foi aceita pelo autômato.");
			}
		 }while(!cadeia.equals("fim"));
    }
}
