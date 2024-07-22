package interfac;

import java.util.function.Consumer;

public interface AutomatoPilha {
    boolean processarCadeia(String cadeia, Consumer<String> logger);
    void adicionarTransicao(Transicao transicao);
}
