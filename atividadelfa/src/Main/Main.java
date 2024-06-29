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
		 Set<Character> alfabetoEntrada = new HashSet<>(Arrays.asList('0', '1'));

		 Set<Character> alfabetoPilha = new HashSet<>(Arrays.asList('Z', 'A'));

         Estado estadoInicial = new EstadoImpl("q0");

		 Set<Estado> estadosFinais = new HashSet<>(Arrays.asList(
		            new EstadoImpl("q2")
		 ));

		 AutomatoPilha automato = new AutomatoPilhaImpl(estados, alfabetoEntrada, alfabetoPilha, estadoInicial, estadosFinais);

		 automato.adicionarTransicao(new TransicaoImpl(new EstadoImpl("q0"), '0', 'Z', new EstadoImpl("q1"), new char[] {'A', 'Z'}));
		 automato.adicionarTransicao(new TransicaoImpl(new EstadoImpl("q0"), '1', 'Z', new EstadoImpl("q1"), new char[] {'A', 'Z'}));
		 
		 automato.adicionarTransicao(new TransicaoImpl(new EstadoImpl("q1"), '1', 'A', new EstadoImpl("q1"), new char[] {'A', 'A'}));
		 automato.adicionarTransicao(new TransicaoImpl(new EstadoImpl("q1"), '0', 'A', new EstadoImpl("q1"), new char[] {'A', 'A'}));
		 
		 automato.adicionarTransicao(new TransicaoImpl(new EstadoImpl("q1"), '1', 'A', new EstadoImpl("q2"), new char[] {'ε'}));
		 automato.adicionarTransicao(new TransicaoImpl(new EstadoImpl("q1"), '0', 'A', new EstadoImpl("q2"), new char[] {'ε'}));
		 automato.adicionarTransicao(new TransicaoImpl(new EstadoImpl("q2"), '1', 'A', new EstadoImpl("q2"), new char[] {'ε'}));
		 automato.adicionarTransicao(new TransicaoImpl(new EstadoImpl("q2"), '0', 'A', new EstadoImpl("q2"), new char[] {'ε'}));
		 
		 automato.adicionarTransicao(new TransicaoImpl(new EstadoImpl("q2"), (char) 0, 'Z', new EstadoImpl("q2"), new char[] {'Z'}));
		 automato.adicionarTransicao(new TransicaoImpl(new EstadoImpl("q1"), '0', 'Z', new EstadoImpl("q5"), new char[] {'ε'}));
		 automato.adicionarTransicao(new TransicaoImpl(new EstadoImpl("q2"), '0', 'Z', new EstadoImpl("q5"), new char[] {'ε'}));

		 System.out.println("Digite sua cadeia ou fim para finalizar o programa");
		 String cadeia;
		 do {
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
