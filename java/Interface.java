import java.util.Scanner;
import java.io.IOException;

public class Interface {
    private RegrasJogo jogo = new RegrasJogo();
    private Scanner sc = new Scanner(System.in);
    private int erro = 0;
    private int[] gambiarraPraTeste = {0,0};

    public void iniciar() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        desenharTabuleiro(false, gambiarraPraTeste);
        if(erro == -1) {
            System.out.println("\nMovimento invalido");
        }
        if(erro == -3) {
            System.out.println("\nvez das peças pretas");
        }
        if(erro == -4) {
            System.out.println("\nvez das peças brancas");
        }
        System.out.println("\ndigite a cordenada da peca (y,x)\n Ex: 61");
        System.out.print("> ");
        String p = sc.next();
        int[] peca = {Integer.parseInt(p.substring(0,1)),Integer.parseInt(p.substring(1,2))};
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        desenharTabuleiro(true, peca);
        System.out.println("digite o movimento vertical \n Ex: -1");
        System.out.print("> ");
        int ym = sc.nextInt();
        System.out.println("digite o movimento horizontal\n Ex: 0");
        System.out.print("> ");
        int xm = sc.nextInt();
        
        
        int[] movimento = {ym,xm};
        erro = jogo.movimentarPeca(peca, movimento);

        if(jogo.getPeaoPromovido()[0] != -1) {
            System.out.println("digite:\n\t1 para Rainha\n\t2 para Cavalo\n\t3 para Torre\n\t4 paraBispo\n\t0 para cancelar");
            System.out.print("> ");
            jogo.promocao(sc.nextInt());
        }

        if(jogo.getFimDeJogo() == 2) {
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println("empate por afogamento");
            return;
        }
        if(jogo.getFimDeJogo() == 3) {
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println("empate por tripla repetição");
            return;
        }
        if(jogo.getFimDeJogo() == 4) {
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println("empate por material insuficiente");
            return;
        }
        if(jogo.getFimDeJogo() == 5) {
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println("empate por cinquenta lançes");
            return;
        }
        if(jogo.getFimDeJogo() == 0) {
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println("vitoria das brancas");
            return;
        } 
        if(jogo.getFimDeJogo() == 1) {
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println("vitoria das pretas");
            return;
        }       
        iniciar(); // a interface não vai ser recursiva, é so um teste
    }
    public void desenharTabuleiro(boolean desenharMovimentos, int[] peca) {
        int[] movimento = {0, 0};
        for(int i = 0; i < 8; i++){
            for(int j = 0; j< 8; j++){
                if(j==0)
                    System.out.print(i + "  ");

                if(peca[0] > i)
                    movimento[0] = i - peca[0];
                else
                    movimento[0] = i - peca[0];

                if(peca[1] > i)
                    movimento[1] = j - peca[1];
                else
                    movimento[1] = j - peca[1];
                
                //System.out.print(movimento[0] + " "+movimento[1]);
                if(desenharMovimentos && jogo.getTabuleiro()[i][j] == ' ' && jogo.movimentoPermitido(peca, movimento, false))
                    System.out.print("* ");
                else
                    System.out.print(jogo.getTabuleiro()[i][j]+ " ");
                
            }
            System.out.println();
        }
        System.out.println("\n   0 1 2 3 4 5 6 7");
    }
}