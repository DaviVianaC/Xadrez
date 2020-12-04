import java.util.Arrays;
public class RegrasJogo {

    private char[][] tabuleiro = {{'T', 'C', 'B', 'A', 'R', 'B', 'C', 'T'},
                                  {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
                                  {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                                  {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                                  {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                                  {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                                  {'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'},
                                  {'t', 'c', 'b', 'a', 'r', 'b', 'c', 't'}};

    private int[] indiceDoReiBranco = {7, 4};
    private int[] indiceDoReiPreto = {0, 4};
    private boolean reiBrancoMovido = false;
    private boolean reiPretoMovido = false;
    private boolean torreBranca1Movido = false;
    private boolean torreBranca2Movido = false;
    private boolean torrePreta1Movido = false;
    private boolean torrePreta2Movido = false;
    private int[] peaoPromovido = {-1, -1};
    private int[] indicePeao = new int[2];
    private int[] peaoEnPassant = {-1,-1};
    private boolean enPassantFlag = false;
    private int roqueFlag = -1;

    public char[][] getTabuleiro() {
        return tabuleiro;
    }

    public int[] getPeaoPromovido() {
        return peaoPromovido;
    }

    public int movimentarPeca(int[] peca, int[] movimento) {
        // posição em que a peça irá se encontrar apos o movimento
        int[] resultante = {peca[0] + movimento[0], peca[1] + movimento[1]};
        
        if(peaoPromovido[0] != -1) // tem um peão para ser promovido
            return -1;

        enPassantFlag = false;
        roqueFlag = -1;
        if(movimentoPermitido(peca, movimento, false)) {
            // salvando coordenada do rei e verificando movimento para possibilidade de roque
            if(tabuleiro[peca[0]][peca[1]] == 'r') {
                indiceDoReiBranco = resultante;
                reiBrancoMovido = true;
            }
            if(tabuleiro[peca[0]][peca[1]] == 'R') {
                indiceDoReiPreto = resultante;
                reiPretoMovido = true;
            }

            // verificando movimento de torres para possibilidade de roque
            if(tabuleiro[7][0] != 't')
                torreBranca1Movido = true;
            if(tabuleiro[7][7] != 't')
                torreBranca2Movido = true;
            if(tabuleiro[0][0] != 'T')
                torrePreta1Movido = true;
            if(tabuleiro[0][7] != 'T')
                torrePreta2Movido = true;

            // En passant so é valido se efetuado imediatamente apos o movimento do peao inimigo
            if(enPassantFlag)
                    tabuleiro[peaoEnPassant[0]][peaoEnPassant[1]] = ' ';
            peaoEnPassant[0] = -1; 
            peaoEnPassant[1] = -1;    

            if((movimento[0] == 2 || movimento[0] == -2) && (tabuleiro[peca[0]][peca[1]] == 'p' || tabuleiro[peca[0]][peca[1]] == 'P')) // peão en passant
                peaoEnPassant = resultante;

            // peão promovido
            if((tabuleiro[peca[0]][peca[1]] == 'p' || tabuleiro[peca[0]][peca[1]] == 'P') && (resultante[0] == 0 || resultante[0] == 7)) {
                indicePeao[0] = peca[0];
                indicePeao[1] = peca[1];
                peaoPromovido[0] = resultante[0];
                peaoPromovido[1] = resultante[1];
            }else {
                // mudando matriz
                if(roqueFlag == 1) {
                    tabuleiro[peca[0]][peca[1]+1] = tabuleiro[peca[0]][peca[1]+3];
                    tabuleiro[peca[0]][peca[1]+3] = ' ';
                }else if(roqueFlag == 0) {
                    tabuleiro[peca[0]][peca[1]-1] = tabuleiro[peca[0]][peca[1]-4];
                    tabuleiro[peca[0]][peca[1]-4] = ' ';
                }

                tabuleiro[resultante[0]][resultante[1]] = tabuleiro[peca[0]][peca[1]];
                tabuleiro[peca[0]][peca[1]] = ' '; 
            }
            return 0;
        }

        return -1;
    }


    public boolean movimentoPermitido(int[] peca, int[] movimento, boolean ignorar) {
        int[] resultante = {peca[0] + movimento[0], peca[1] + movimento[1]};

        // regras iguais para todas as peças
        if(tabuleiro[peca[0]][peca[1]] == ' ') // não é uma peça
            return false;
        if(movimento[0] == 0 && movimento[1] == 0) // não andou
            return false;
        if(resultante[0] > 7 || resultante[0] < 0 || resultante[1] > 7 || resultante[1] < 0 ) // fora dos limites
            return false;
        if(tabuleiro[peca[0]][peca[1]] > 90) {
            if(tabuleiro[resultante[0]][resultante[1]] > 90) // tentou matar um aliado branco
                return false;
        }else {                                             // tentou matar um aliado preto
            if(tabuleiro[resultante[0]][resultante[1]] < 97 && tabuleiro[resultante[0]][resultante[1]] != 32) 
                return false;
        }

        // teste de xeque após movimento
        if(!ignorar && !Arrays.equals(peca, indiceDoReiBranco) && !Arrays.equals(peca, indiceDoReiPreto)) {  
            char pecaMovimentada = tabuleiro[peca[0]][peca[1]];         // salvando peças para teste
            char possivelPeca = tabuleiro[resultante[0]][resultante[1]];

            tabuleiro[resultante[0]][resultante[1]] = pecaMovimentada;
            tabuleiro[peca[0]][peca[1]] = ' ';

            boolean resultado = reiEmXeque(pecaMovimentada);

            tabuleiro[resultante[0]][resultante[1]] = possivelPeca;
            tabuleiro[peca[0]][peca[1]] = pecaMovimentada;

            if(resultado){
                return false;
            }
        }   

        // regras do peão
        if(tabuleiro[peca[0]][peca[1]] == 'p' || tabuleiro[peca[0]][peca[1]] == 'P') {
            // regras iguais para ambos os lados
            if(movimento[0] == 0) // não andou verticalmente
                return false;                                                                 // andou para o lado sem peca para matar
            if(resultante[1] != peca[1] && tabuleiro[resultante[0]][resultante[1]] == ' ' && !enPassantValido(peca, resultante))
                return false;
            if(movimento[1] > 1 || movimento[1] < -1) // andou mais que uma casa para o lado
                return false;
            if(movimento[1] == 0 && tabuleiro[resultante[0]][resultante[1]] != ' ') // andou apenas para frente caindo numa casa ocupada
                return false;

            // regras diferentes para cada lado
            if(tabuleiro[peca[0]][peca[1]] == 'p') {
                if(resultante[0] > peca[0]) // andou para tras
                    return false;
                if(movimento[0] == -2 && peca[0] != 6) // andou duas casas para frente após a primeira jogada
                    return false;
                if(movimento[0] == -2 && movimento[1] != 0) // andou duas casas para frente e tambem uma para o lado
                    return false;
                if(movimento[0] < -2) // andou mais que duas casas para frente
                    return false;                                                             // andou duas casas para frente com a casa intermediaria ocupada
                if(movimento[1] == 0 && movimento[0] == -2 && tabuleiro[resultante[0]+1][resultante[1]] != ' ')
                    return false;
                
            }else if(tabuleiro[peca[0]][peca[1]] == 'P') {
                if(resultante[0] < peca[0]) // andou para tras
                    return false;
                if(movimento[0] == 2 && peca[0] != 1) // andou duas casas para frente após a primeira jogada
                    return false;
                if(movimento[0] == 2 && movimento[1] != 0) // andou duas casas para frente e tambem uma para o lado
                    return false;
                if(movimento[0] > 2) // andou mais que duas casas para frente
                    return false;                                                             // andou duas casas para frente com a casa intermediaria ocupada
                if(movimento[1] == 0 && movimento[0] == 2 && tabuleiro[resultante[0]-1][resultante[1]] != ' ') 
                    return false;
            }

            return true;
        }

        // regras do bispo
        if(tabuleiro[peca[0]][peca[1]] == 'b' || tabuleiro[peca[0]][peca[1]] == 'B') {
            if(Math.abs(movimento[0]) != Math.abs(movimento[1])) // não andou diagonalmente
                return false;
            
            int x = movimento[1];
            int y = movimento[0];
            for(int i=1; i < Math.abs(movimento[0]); i++) {  
                if(x > 0)
                    x--;
                else
                    x++;
                if(y > 0)
                    y--;
                else
                    y++;
                if(tabuleiro[peca[0] + y][peca[1] + x] != ' ') // passou por cima de alguma peça
                    return false;
            }
            
            return true;
        }
        
        // regras da torre
        if(tabuleiro[peca[0]][peca[1]] == 't' || tabuleiro[peca[0]][peca[1]] == 'T') {
            if(movimento[0] != 0 && movimento[1] != 0) // não andou em linha reta
                return false;

            int x = movimento[1];
            int y = movimento[0];
            int eixo;
            if(movimento[0] != 0)
                eixo = 0;
            else
                eixo = 1;
            for(int i=1; i < Math.abs(movimento[eixo]); i++) {  
                if(x > 0)
                    x--;
                if(x < 0)
                    x++;
                if(y > 0)
                    y--;
                if(y < 0)
                    y++;
                if(tabuleiro[peca[0] + y][peca[1] + x] != ' ') // passou por cima de alguma peça
                    return false;
            }

            return true;
        }

        // regras da rainha
        if(tabuleiro[peca[0]][peca[1]] == 'a' || tabuleiro[peca[0]][peca[1]] == 'A') {
            if(Math.abs(movimento[0]) == Math.abs(movimento[1])) {  // andou diagonalmente
                int x = movimento[1];
                int y = movimento[0];
                for(int i=1; i < Math.abs(movimento[0]); i++) {  
                    if(x > 0)
                        x--;
                    else
                        x++;
                    if(y > 0)
                        y--;
                    else
                        y++;
                    if(tabuleiro[peca[0] + y][peca[1] + x] != ' ') // passou por cima de alguma peça
                        return false;
                }
            }else if(movimento[0] == 0 || movimento[1] == 0) {  // andou em linha ou coluna
                int x = movimento[1];
                int y = movimento[0];
                int eixo;
                if(movimento[0] != 0)
                    eixo = 0;
                else
                    eixo = 1;
                for(int i=1; i < Math.abs(movimento[eixo]); i++) {  
                    if(x > 0)
                        x--;
                    if(x < 0)
                        x++;
                    if(y > 0)
                        y--;
                    if(y < 0)
                        y++;
                    if(tabuleiro[peca[0] + y][peca[1] + x] != ' ') // passou por cima de alguma peça
                        return false;
                }
            }else {
                return false;
            }

            return true;
        }

        //regras do cavalo
        if(tabuleiro[peca[0]][peca[1]] == 'c' || tabuleiro[peca[0]][peca[1]] == 'C') {
            // todos movimentos possiveis do cavalo
            if(resultante[0]==peca[0]+2 && resultante[1]==peca[1]+1)
                return true;
            if(resultante[0]==peca[0]+2 && resultante[1]==peca[1]-1)
                return true;
            if(resultante[0]==peca[0]-2 && resultante[1]==peca[1]+1)
                return true;
            if(resultante[0]==peca[0]-2 && resultante[1]==peca[1]-1)
                return true;
            if(resultante[0]==peca[0]+1 && resultante[1]==peca[1]+2)
                return true;
            if(resultante[0]==peca[0]+1 && resultante[1]==peca[1]-2)
                return true; 
            if(resultante[0]==peca[0]-1 && resultante[1]==peca[1]+2)
                return true;
            if(resultante[0]==peca[0]-1 && resultante[1]==peca[1]-2)
                return true;

            return false;
        }
            
        //regras do rei
        if(tabuleiro[peca[0]][peca[1]] == 'r' || tabuleiro[peca[0]][peca[1]] == 'R') {
            if(roquePermitido(peca, movimento)) {
                if(movimento[1] > 0){ // pequeno roque
                    roqueFlag = 1;
                }else{  // grande roque
                    roqueFlag = 0;
                }
            }else if(movimento[1] > 1 || movimento[1] < -1) // andou mais que uma casa horizontalmente sem possibilida de roque
                return false;    
                                        
            if(movimento[0] > 1 || movimento[0] < -1)  // andou mais que uma casa verticalmente
                return false;

            if(!ignorar) // caso esteja em teste de movimento suicida não entra aqui
                if(movimentoSuicida(peca, movimento))  // tentou se matar
                    return false;

            return true;
        }
        
        return false;
    }


    private boolean movimentoSuicida(int[] peca, int[] movimento) {
        int[] resultante = {peca[0] + movimento[0], peca[1] + movimento[1]};
        
        char pecaMovimentada = tabuleiro[peca[0]][peca[1]];
        char possivelPeca = tabuleiro[resultante[0]][resultante[1]];
        char pecaTeste = 'E';
        if(pecaMovimentada > 90)
            pecaTeste = 'e';

        for(int i=0; i < 8; i++) {
            for(int j=0; j < 8; j++) {
                int[] p = {i, j};
                int[] m = {resultante[0] - i, resultante[1] - j};

                tabuleiro[peca[0]][peca[1]] = ' '; // rei removido para teste
                tabuleiro[resultante[0]][resultante[1]] = pecaTeste; // peça colocada para teste 
                if(movimentoPermitido(p, m, true)) { // testando possivel ataque de todas as peças inimigas
                    tabuleiro[resultante[0]][resultante[1]] = possivelPeca; // removendo peça de teste
                    tabuleiro[peca[0]][peca[1]] = pecaMovimentada; // rei retornado
                    return true;
                }

                tabuleiro[resultante[0]][resultante[1]] = possivelPeca; // removendo peça de teste
            }
        }

        tabuleiro[peca[0]][peca[1]] = pecaMovimentada; // rei retornado
        
        return false; 
    }

    private boolean reiEmXeque(char peca) {
        int[] movimentoZero = {0, 0};
        boolean reiEmXeque = false;

        if(peca > 90) {
            if(movimentoSuicida(indiceDoReiBranco, movimentoZero))
                reiEmXeque = true;
        }else {
            if(movimentoSuicida(indiceDoReiPreto, movimentoZero))
                reiEmXeque = true;
        }
        
        return reiEmXeque;     
    }

    private boolean roquePermitido(int[] peca, int[] movimento) {
        if(reiEmXeque(tabuleiro[peca[0]][peca[1]]))
            return false;

        if(movimento[1] != -2 && movimento[1] != 2 || movimento[0] != 0)
            return false;

        if(tabuleiro[peca[0]][peca[1]] == 'r' && !reiBrancoMovido) {
            if(movimento[1] == -2 && torreBranca1Movido || movimento[1] == -2 && tabuleiro[peca[0]][peca[1]-3] != ' ') 
                return false;
            if(movimento[1] == 2 && torreBranca2Movido) 
                return false;
        }else if(tabuleiro[peca[0]][peca[1]] == 'R' && !reiPretoMovido) {
            if(movimento[1] == -2 && torrePreta1Movido || movimento[1] == -2 && tabuleiro[peca[0]][peca[1]-3] != ' ') 
                return false;
            if(movimento[1] == 2 && torrePreta2Movido) 
                return false;
        }else {
            return false;
        }

        int[] movimentoDeTeste = {0, 0};
        for(int i = movimento[1]; i!=0;) {
            if(tabuleiro[peca[0]][peca[1]+i] != ' ')
                return false;

            movimentoDeTeste[1] = i;
            if(movimentoSuicida(peca, movimentoDeTeste))
                return false;

            i = (i>0) ? i-1 : i+1;
        }

        return true;
    }

    public void promocao(int pecaEscolhida) {
        if(pecaEscolhida > 4 || pecaEscolhida < 0) // parametro invalido
            return;

        switch(pecaEscolhida) {
            case 1: // rainha
                tabuleiro[peaoPromovido[0]][peaoPromovido[1]] = (peaoPromovido[0] == 0) ? 'a' : 'A';
                break;
            case 2: // cavalo
                tabuleiro[peaoPromovido[0]][peaoPromovido[1]] = (peaoPromovido[0] == 0) ? 'c' : 'C';
                break;
            case 3: // torre
                tabuleiro[peaoPromovido[0]][peaoPromovido[1]] = (peaoPromovido[0] == 0) ? 't' : 'T';
                break;
            case 4: // bispo
                tabuleiro[peaoPromovido[0]][peaoPromovido[1]] = (peaoPromovido[0] == 0) ? 'b' : 'B';
                break;
        }

        if(pecaEscolhida != 0)  // caso pecaEscolhida == 0 significa movimento cancelado
            tabuleiro[indicePeao[0]][indicePeao[1]] = ' ';

        // indicando que não há peão para ser promovido
        peaoPromovido[0] = -1;
        peaoPromovido[1] = -1;
    }

    private boolean enPassantValido(int[] peca, int[] resultante) {
        if(peaoEnPassant[0] == -1)
            return false;

        if(tabuleiro[peca[0]][peca[1]] == 'p') {
            if(peca[0] == 3 && tabuleiro[peaoEnPassant[0]][peaoEnPassant[1]] == 'P' && resultante[0] == peaoEnPassant[0]-1 && resultante[1] == peaoEnPassant[1]) {
                enPassantFlag = true;
                return true;
            }else {  
                return false;
            }
        }else if(tabuleiro[peca[0]][peca[1]] == 'P') {
            if(peca[0] == 4 && tabuleiro[peaoEnPassant[0]][peaoEnPassant[1]] == 'p' && resultante[0] == peaoEnPassant[0]+1  && resultante[1] == peaoEnPassant[1]) {
                enPassantFlag = true;
                return true;
            }else {
                return false;
            }
        }

        return false;
    }
}