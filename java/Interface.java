import java.util.Scanner;
import java.io.IOException;

public class Interface {
    private RegrasJogo jogo = new RegrasJogo();
    private Scanner sc = new Scanner(System.in);
    private int erro = 0;
    
    public void iniciar() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        desenharTabuleiro();
        if(erro == -1) {
            System.out.println("\nMovimento invalido");
        }
        System.out.println("\ndigite a cordenada da peca (y,x)\n Ex: 61");
        System.out.print("> ");
        String p = sc.next();
        System.out.println("digite o movimento vertical \n Ex: -1");
        System.out.print("> ");
        int ym = sc.nextInt();
        System.out.println("digite o movimento horizontal\n Ex: 0");
        System.out.print("> ");
        int xm = sc.nextInt();
        
        int[] peca = {Integer.parseInt(p.substring(0,1)),Integer.parseInt(p.substring(1,2))};
        int[] movimento = {ym,xm};
        erro = jogo.movimentarPeca(peca, movimento);

        if(jogo.getPeaoPromovido()[0] != -1) {
            System.out.println("digite:\n\t1 para Rainha\n\t2 para Cavalo\n\t3 para Torre\n\t4 paraBispo\n\t0 para cancelar");
            System.out.print("> ");
            jogo.promocao(sc.nextInt());
        }
        
        iniciar(); // a interface não vai ser recursiva, é so um teste
    }
    public void desenharTabuleiro() {
        for(int i = 0; i < 8; i++){
            for(int j = 0; j< 8; j++){
                if(j==0)
                    System.out.print(i + "  ");
                System.out.print(jogo.getTabuleiro()[i][j]+ " ");
            }
            System.out.println();
        }
        System.out.println("\n   0 1 2 3 4 5 6 7");
    }
}