public class AVLNode {
    private AVLNode esquerda;
    private AVLNode direita;
    private AVLNode pai;
    private int chave;
    private int balanceamento;

    public AVLNode(int k) {
        setEsquerda(setDireita(setPai(null)));
        setBalanceamento(0);
        setChave(k);
    }

    /**
     *
     * @return
     */
    public String toString() {
        return Integer.toString(getChave());
    }

    /**
     *
     * @return
     */
    public int getChave() {
        return chave;
    }

    /**
     *
     * @param chave
     */
    public void setChave(int chave) {
        this.chave = chave;
    }

    /**
     *
     * @return
     */
    public int getBalanceamento() {
        return balanceamento;
    }

    /**
     *
     * @param balanceamento
     */
    public void setBalanceamento(int balanceamento) {
        this.balanceamento = balanceamento;
    }

    /**
     *
     * @return
     */
    public AVLNode getPai() {
        return pai;
    }

    /**
     *
     * @param pai
     * @return
     */
    public AVLNode setPai(AVLNode pai) {
        this.pai = pai;
        return pai;
    }

    /**
     *
     * @return
     */
    public AVLNode getDireita() {
        return direita;
    }

    /**
     *
     * @param direita
     * @return
     */
    public AVLNode setDireita(AVLNode direita) {
        this.direita = direita;
        return direita;
    }

    /**
     *
     * @return
     */
    public AVLNode getEsquerda() {
        return esquerda;
    }

    /**
     *
     * @param esquerda
     */
    public void setEsquerda(AVLNode esquerda) {
        this.esquerda = esquerda;
    }
}
