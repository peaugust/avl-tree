import java.util.Random;

public class Main {
    public static void main(String[] args){
        AVLTree testeArvore = new AVLTree();
        Random gerador = new Random();
        int for1 = 0;
        for(int i = 1; i < 101; i++ ){
            testeArvore.inserir(i);
            for1++;
        }
        System.out.println(for1);
        System.out.println(testeArvore.inorder());
        System.out.println(testeArvore.inorder().size());
        for(int i = 0; i < 50; i++ ){
            testeArvore.remover((i*2)+1);
        }
        System.out.println(testeArvore.inorder());
        System.out.println(testeArvore.inorder().size());
    }
}
