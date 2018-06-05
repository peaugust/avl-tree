public class Main {
    public static void main(String[] args){
        AVLTree testeArvore = new AVLTree();
        testeArvore.inserir(200);
        testeArvore.inserir(5);
        testeArvore.inserir(52);
        testeArvore.inserir(10);
        testeArvore.inserir(2);
        testeArvore.inserir(25);
        testeArvore.inserir(30);
        testeArvore.inserir(1);
        testeArvore.inserir(33);
        System.out.print(testeArvore.inorder());
    }
}
