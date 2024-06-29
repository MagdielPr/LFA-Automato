package interfac;

public interface Transicao {
	Estado getEstadoAtual();
	char getSimboloEntrada();
	char getTopoPilha();
    Estado getProximoEstado();
    char[] getNovosSimbolos();
}
