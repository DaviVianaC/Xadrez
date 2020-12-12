/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xadrez;

/**
 *
 * @author daviv
 */

public class Adversario {
    private int[] pecaEscolhida;
    private RegrasJogo jogo;
    
    Adversario(RegrasJogo jogo) {
        this.jogo = jogo;
    }
    
    public void jogar() {
        if(!jogo.getVez())
            return;
        
        int[] movimento = {0,0};
        while(true){
            int y = (int)(Math.random()*8);
            int x = (int)(Math.random()*8);
            char peca = jogo.getTabuleiro()[y][x];
            if(peca < 90 && peca != ' ') {
                int[] coordPeca = {y, x};
                for(int k=0; k< 8; k++) {
                    for(int l=0; l<8; l++){
                        movimento[0] = k - coordPeca[0];
                        movimento[1] = l - coordPeca[1];
                        if(jogo.movimentarPeca(coordPeca, movimento) != -1)
                            return;
                    }
                }
            }
        }
    }
    
}
