import java.util.ArrayList;

public class AVLTree {
    private AVLNode raiz;

    /**
     * Método que inicia a inserção de um elemento na árvore
     *
     * @param novoElemento - Novo elemento a ser inserido na árvore
     */
    public void inserir(int novoElemento) {
        AVLNode aInserir = new AVLNode(novoElemento);
        inserirAVL(this.raiz, aInserir);
    }

    /**
     * Método que realiza a inserção de um novo elemento na árvore
     *
     * @param aComparar - Elemento a ser comparado(raiz)
     * @param aInserir  - Elemento a ser inserido
     */
    private void inserirAVL(AVLNode aComparar, AVLNode aInserir) {
        //Verifica se a raiz está vazia
        if (aComparar == null) {
            this.raiz = aInserir;
            //Caso não esteja:
        } else {
            //Verifica se a chave inserida é menor que a chave já existente
            if (aInserir.getChave() < aComparar.getChave()) {
                //Verifica se a esquerda da chave existente está livre:
                if (aComparar.getEsquerda() == null) {
                    aComparar.setEsquerda(aInserir);
                    aInserir.setPai(aComparar);
                    //Verfica se o balanceamento permanece dentro do intervalo ± 1
                    verificarBalanceamento(aComparar);
                    //Caso a esquerda não esteja livre, chama recursivamente o método para avançar na árvore:
                } else {
                    inserirAVL(aComparar.getEsquerda(), aInserir);
                }
                //Verifica se a chave inserida é maior que a chave já existente
            } else if (aInserir.getChave() > aComparar.getChave()) {
                //Verfica se a direita da chave existente está livre:
                if (aComparar.getDireita() == null) {
                    aComparar.setDireita(aInserir);
                    aInserir.setPai(aComparar);
                    //Verfica se o balanceamento permanece dentro do intervalo ± 1
                    verificarBalanceamento(aComparar);
                    //Caso a direita não esteja livre, chama recursivamente o método para avançar na árvore:
                } else {
                    inserirAVL(aComparar.getDireita(), aInserir);
                }
                //Nesse caso já existe um nó com essa chave nessa árvore
            } else {
                // O nó já existe
            }
        }
    }

    /**
     * Método responsável por verificar o balanceamento da árvore
     *
     * @param atual - Nó que se deseja saber se está balanceado
     */
    public void verificarBalanceamento(AVLNode atual) {
        //Chama o método responsável por setar o calculo do balanceamento das subárvores desse nó
        setBalanceamento(atual);
        //Cria uma variável que assume o valor do Fator de Balanceamento desse nó
        int balanceamento = atual.getBalanceamento();
        //Se o balanceamento for -2 (Esquerda = 1 e Direita = -1)
        if (balanceamento == -2) {
            //Verifica se a altura da subárvore da esquerda e maior ou igual a subárvore da direita
            if (altura(atual.getEsquerda().getEsquerda()) >= altura(atual.getEsquerda().getDireita())) {
                //Faz uma rotação a direita
                atual = rotacaoDireita(atual);
                //Caso contrário, dá uma dupla rotação a esquerda
            } else {
                atual = duplaRotacaoEsquerdaDireita(atual);
            }

        } else if (balanceamento == 2) {

            if (altura(atual.getDireita().getDireita()) >= altura(atual.getDireita().getEsquerda())) {
                atual = rotacaoEsquerda(atual);

            } else {
                atual = duplaRotacaoDireitaEsquerda(atual);
            }
        }

        if (atual.getPai() != null) {
            verificarBalanceamento(atual.getPai());
        } else {
            this.raiz = atual;
        }
    }

    /**
     * Método que inicia a remoção de um nó da árvore
     *
     * @param aSerRemovido - Nó que desejamos remover
     */
    public void remover(int aSerRemovido) {
        removerAVL(this.raiz, aSerRemovido);
    }

    /**
     * Método que realiza a busca do nó a ser removido da árvore
     *
     * @param atual        - Nó de referência
     * @param aSerRemovido - Nó a ser removido
     */
    private void removerAVL(AVLNode atual, int aSerRemovido) {
        if (atual == null) {
            return;

        } else {

            if (atual.getChave() > aSerRemovido) {
                removerAVL(atual.getEsquerda(), aSerRemovido);

            } else if (atual.getChave() < aSerRemovido) {
                removerAVL(atual.getDireita(), aSerRemovido);

            } else if (atual.getChave() == aSerRemovido) {
                removerAVLNodeEncontrado(atual);
            }
        }
    }

    /**
     * Método que remove o Nó após realizada a busca do nó a ser removido
     *
     * @param aRemover - Nó que será removido da aŕvore
     */
    public void removerAVLNodeEncontrado(AVLNode aRemover) {
        //Crio uma varíavel na memória do tipo AVLNode que servirá de apoio
        AVLNode r;

        //Se o Nó a remover não tiver filhos a esquerda e a direta dele
        if (aRemover.getEsquerda() == null || aRemover.getDireita() == null) {
            //Verifico se o Nó a remover possui não possui pai
            if (aRemover.getPai() == null) {
                //Caso não possua, significa que ele é a raiz da árvore, portanto mudo o apontamento do atributo raiz
                this.raiz = null;
                //Limpo o apontamento da variável do parâmetro
                aRemover = null;
                //Termino o método
                return;
            }
            //Caso não passe no if, a variável de apoio recebe o Nó dado via parâmetro
            r = aRemover;
        } else {
            //Então transformo o r no sucessor do nó dado como parâmetro
            r = sucessor(aRemover);
            //Dou um set na chave do nó a ser removido usando a chave do sucessor
            aRemover.setChave(r.getChave());
        }

        //Crio outra variável de apoio
        AVLNode p;
        //Caso a esquerda de r não estiver vazia
        if (r.getEsquerda() != null) {
            //p ganha um apontamento para a subávore a esquerda
            p = r.getEsquerda();
        //Caso contrário ele ganha um apontamento para a subárvore a direita
        } else {
            p = r.getDireita();
        }
        //Se p não estiver vazio
        if (p != null) {
            //p recebe como pai o pai de r
            p.setPai(r.getPai());
        }
        //Caso o pai de r seja null, p vira a raiz da árvore toda
        if (r.getPai() == null) {
            this.raiz = p;
        //Caso contrário
        } else {
            //Se r for igual ao pai da esquerda
            if (r == r.getPai().getEsquerda()) {
                //r chama o pai dele e seta a p como subárvore a esquerda dele
                r.getPai().setEsquerda(p);
            //Caso contrário
            } else {
                //r chama o pai dele e seta p como subárvore a direita dele
                r.getPai().setDireita(p);
            }
            //Verfico o balanceamento passando o pai de r como parâmetro
            verificarBalanceamento(r.getPai());
        }
        //apago o apontamento de r
        r = null;
    }

    /**
     * Método que realiza rotações na árvore com base em um nó de referência
     *
     * @param inicial - Nó inicial
     * @return
     */
    private AVLNode rotacaoEsquerda(AVLNode inicial) {

        AVLNode direita = inicial.getDireita();
        direita.setPai(inicial.getPai());

        inicial.setDireita(direita.getEsquerda());

        if (inicial.getDireita() != null) {
            inicial.getDireita().setPai(inicial);
        }

        direita.setEsquerda(inicial);
        inicial.setPai(direita);

        if (direita.getPai() != null) {

            if (direita.getPai().getDireita() == inicial) {
                direita.getPai().setDireita(direita);

            } else if (direita.getPai().getEsquerda() == inicial) {
                direita.getPai().setEsquerda(direita);
            }
        }

        setBalanceamento(inicial);
        setBalanceamento(direita);

        return direita;
    }

    /**
     * Método que realiza rotação a direita
     *
     * @param inicial
     * @return
     */
    public AVLNode rotacaoDireita(AVLNode inicial) {

        AVLNode esquerda = inicial.getEsquerda();
        esquerda.setPai(inicial.getPai());

        inicial.setEsquerda(esquerda.getDireita());

        if (inicial.getEsquerda() != null) {
            inicial.getEsquerda().setPai(inicial);
        }

        esquerda.setDireita(inicial);
        inicial.setPai(esquerda);

        if (esquerda.getPai() != null) {

            if (esquerda.getPai().getDireita() == inicial) {
                esquerda.getPai().setDireita(esquerda);

            } else if (esquerda.getPai().getEsquerda() == inicial) {
                esquerda.getPai().setEsquerda(esquerda);
            }
        }

        setBalanceamento(inicial);
        setBalanceamento(esquerda);

        return esquerda;
    }

    /**
     * @param inicial
     * @return
     */
    public AVLNode duplaRotacaoEsquerdaDireita(AVLNode inicial) {
        inicial.setEsquerda(rotacaoEsquerda(inicial.getEsquerda()));
        return rotacaoDireita(inicial);
    }

    /**
     * @param inicial
     * @return
     */
    public AVLNode duplaRotacaoDireitaEsquerda(AVLNode inicial) {
        inicial.setDireita(rotacaoDireita(inicial.getDireita()));
        return rotacaoEsquerda(inicial);
    }

    /**
     * @param q
     * @return
     */
    public AVLNode sucessor(AVLNode q) {
        if (q.getDireita() != null) {
            AVLNode r = q.getDireita();
            while (r.getEsquerda() != null) {
                r = r.getEsquerda();
            }
            return r;
        } else {
            AVLNode p = q.getPai();
            while (p != null && q == p.getDireita()) {
                q = p;
                p = q.getPai();
            }
            return p;
        }
    }

    /**
     * @param atual
     * @return
     */
    private int altura(AVLNode atual) {
        //se o atual for um nó null
        if (atual == null) {
            //Altura é igual a -1
            return -1;
        }
        //se as subárvores estiverem vazias
        if (atual.getEsquerda() == null && atual.getDireita() == null) {
        //Altura é igual a 0
            return 0;
        //No entanto se apenas a subárvore a esquerda estiver vazia
        } else if (atual.getEsquerda() == null) {
            //Altura é igual a 1 + a altura da subárvore à direita
            return 1 + altura(atual.getDireita());
        //No entanto se apenas a subárvore a esquerda estiver vazia
        } else if (atual.getDireita() == null) {
            //Altura é igual a 1 + a altura da subárvore à esquerda
            return 1 + altura(atual.getEsquerda());
        //Por fim, sem ambas as subárvores possuirem filhos, realizo a comparação de qual delas é a maior e verifico
        //altura dela e somo com 1
        } else {
            return 1 + Math.max(altura(atual.getEsquerda()), altura(atual.getDireita()));
        }
    }

    /**Método que seta o fator de balanceamento em um nó dado por parâmetro
     *
     * @param AVLNode Nó que receberá o Fator de Balanceamento
     */
    private void setBalanceamento(AVLNode AVLNode) {
        //Dou um set no Nó passado por parâmetro usando o método que verifca a altura
        AVLNode.setBalanceamento(altura(AVLNode.getDireita()) - altura(AVLNode.getEsquerda()));
    }

    /**Método que inicia a caminhada in-order na árvore
     *
     * @return - ArrayList que receberá os dados da árvore
     */
    final protected ArrayList<AVLNode> inorder() {
        // Crio um ArrayList para armazenar os dados da minha árvore
        ArrayList<AVLNode> ret = new ArrayList<>();
        // Chamo o método que realiza a caminhada pela árvore
        inorder(raiz, ret);
        //Retorno o ArrayList
        return ret;
    }

    /**Método que realiza a caminhada in-order na árvore
     *
     * @param AVLNode Raiz de referência para o ínicio da caminhada in-order
     * @param lista - ArrayList que irá receber os dados da árvore
     */
    final protected void inorder(AVLNode AVLNode, ArrayList<AVLNode> lista) {
        //Se a raiz é nula, não faço nada
        if (AVLNode == null) {
            return;
        }
        //Pego a árvore a esquerda da minha raiz e chamo o método recursivamente até chegar em uma folha
        inorder(AVLNode.getEsquerda(), lista);
        //Ao chegar na folha(nível mais profundo) Adiciono a lista o nó que foi passado por parâmetro
        lista.add(AVLNode);
        //Logo em seguida, pego a árvore a direita do nó e chamo o método recursivamente até chegar em uma folha
        inorder(AVLNode.getDireita(), lista);
    }
}
