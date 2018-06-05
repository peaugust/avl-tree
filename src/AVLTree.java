import java.util.ArrayList;

public class AVLTree {
    private AVLNode raiz;

    /**Método que inicia a inserção de um elemento na árvore
     *
     * @param novoElemento - Novo elemento a ser inserido na árvore
     */
    public void inserir(int novoElemento) {
        AVLNode aInserir = new AVLNode(novoElemento);
        inserirAVL(this.raiz, aInserir);
    }

    /**Método que realiza a inserção de um novo elemento na árvore
     *
     * @param aComparar - Elemento a ser comparado(raiz)
     * @param aInserir - Elemento a ser inserido
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

    /**Método responsável por verificar o balanceamento da árvore
     *
     * @param atual - Nó que se deseja saber se está balanceado
     */
    public void verificarBalanceamento(AVLNode atual) {
        //Chama o método responsável por calcular o balanceamento das subárvores desse nó
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

    /**Método que inicia a remoção de um nó da árvore
     *
     * @param aSerRemovido - Nó que desejamos remover
     */
    public void remover(int aSerRemovido) {
        removerAVL(this.raiz, aSerRemovido);
    }

    /**Método que realiza a busca do nó a ser removido da árvore
     *
     * @param atual - Nó de referência
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

    /**Método que remove o Nó após realizada a busca do nó a ser removido
     *
     * @param aRemover - Nó que será removido da aŕvore
     */
    public void removerAVLNodeEncontrado(AVLNode aRemover) {
        AVLNode r;

        if (aRemover.getEsquerda() == null || aRemover.getDireita() == null) {

            if (aRemover.getPai() == null) {
                this.raiz = null;
                aRemover = null;
                return;
            }
            r = aRemover;

        } else {
            r = sucessor(aRemover);
            aRemover.setChave(r.getChave());
        }

        AVLNode p;
        if (r.getEsquerda() != null) {
            p = r.getEsquerda();
        } else {
            p = r.getDireita();
        }

        if (p != null) {
            p.setPai(r.getPai());
        }

        if (r.getPai() == null) {
            this.raiz = p;
        } else {
            if (r == r.getPai().getEsquerda()) {
                r.getPai().setEsquerda(p);
            } else {
                r.getPai().setDireita(p);
            }
            verificarBalanceamento(r.getPai());
        }
        r = null;
    }

    /**Método que realiza rotações na árvore com base em um nó de referência
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

    /**Método que realiza rotação a direita
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
     *
     * @param inicial
     * @return
     */
    public AVLNode duplaRotacaoEsquerdaDireita(AVLNode inicial) {
        inicial.setEsquerda(rotacaoEsquerda(inicial.getEsquerda()));
        return rotacaoDireita(inicial);
    }

    /**
     *
     * @param inicial
     * @return
     */
    public AVLNode duplaRotacaoDireitaEsquerda(AVLNode inicial) {
        inicial.setDireita(rotacaoDireita(inicial.getDireita()));
        return rotacaoEsquerda(inicial);
    }

    /**
     *
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
     *
     * @param atual
     * @return
     */
    private int altura(AVLNode atual) {
        if (atual == null) {
            return -1;
        }

        if (atual.getEsquerda() == null && atual.getDireita() == null) {
            return 0;

        } else if (atual.getEsquerda() == null) {
            return 1 + altura(atual.getDireita());

        } else if (atual.getDireita() == null) {
            return 1 + altura(atual.getEsquerda());

        } else {
            return 1 + Math.max(altura(atual.getEsquerda()), altura(atual.getDireita()));
        }
    }

    /**
     *
     * @param AVLNode
     */
    private void setBalanceamento(AVLNode AVLNode) {
        AVLNode.setBalanceamento(altura(AVLNode.getDireita()) - altura(AVLNode.getEsquerda()));
    }

    /**
     *
     * @return
     */
    final protected ArrayList<AVLNode> inorder() {
        ArrayList<AVLNode> ret = new ArrayList<AVLNode>();
        inorder(raiz, ret);
        return ret;
    }

    /**
     *
     * @param AVLNode
     * @param lista
     */
    final protected void inorder(AVLNode AVLNode, ArrayList<AVLNode> lista) {
        if (AVLNode == null) {
            return;
        }
        inorder(AVLNode.getEsquerda(), lista);
        lista.add(AVLNode);
        inorder(AVLNode.getDireita(), lista);
    }
}
