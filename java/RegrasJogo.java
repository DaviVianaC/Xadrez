public class RegrasJogo {

    private char[][] tabuleiro = {{'T', 'C', 'B', 'A', 'R', 'B', 'C', 'T'},
                                  {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
                                  {' ', ' ', ' ', ' ',' ', ' ', ' ', ' '},
                                  {' ', ' ', ' ', ' ',' ', ' ', ' ', ' '},
                                  {' ', ' ', ' ', ' ',' ', ' ', ' ', ' '},
                                  {' ', ' ', ' ', ' ',' ', ' ', ' ', ' '},
                                  {'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'},
                                  {'t', 'c', 'b', 'a', 'r', 'b', 'c', 't'}};

    public char[][] getTabuleiro(){
        return tabuleiro;
    }

    public int movimentarPeca(int[] peca, int[] movimento){
        if(tabuleiro[peca[0]][peca[1]] == 'p') {
            int[] resultante = {peca[0] + movimento[0], peca[1] + movimento[1]};

            if(resultante[0] > 7 || resultante[0] < 0 || resultante[1] > 7 || resultante[1] < 0 ) // fora dos limites
                return -1;
            if(resultante[0] > peca[0]) // andou para tras
                return -1;
            if(movimento[0] == 0) // não andou verticalmente
                return -1;
            if(resultante[1] != peca[1] && tabuleiro[resultante[0]][resultante[1]] == ' ') // andou para o lado sem peca para matar
                return -1;
            if(movimento[1] > 1 || movimento[1] < -1) // andou mais que uma casa para o lado
                return -1;
            if(movimento[0] == -2 && peca[0] != 6) // andou duas casas para frente após a primeira jogada
                return -1;
            if(movimento[0] == -2 && movimento[1] != 0) // andou duas casas para frente e tambem uma para o lado
                return -1;
            if(movimento[0] < -2) // andou mais que duas casas para frente
                return -1;
            if(movimento[1] == 0 && tabuleiro[resultante[0]][resultante[1]] != ' ') // andou apenas para frente caindo numa casa ocupada
                return -1;
            if(movimento[1] == 0 && movimento[0] == -2 && tabuleiro[resultante[0]+1][resultante[1]] != ' ') // andou duas casas para frente com a casa intermediaria ocupada
                return -1;

            tabuleiro[resultante[0]][resultante[1]] = 'p';
            tabuleiro[peca[0]][peca[1]] = ' ';
            return 0;
        }else if(tabuleiro[peca[0]][peca[1]] == 'P') {
            int[] resultante = {peca[0] + movimento[0], peca[1] + movimento[1]};

            if(resultante[0] > 7 || resultante[0] < 0 || resultante[1] > 7 || resultante[1] < 0 ) // fora dos limites
                return -1;
            if(resultante[0] < peca[0]) // andou para tras
                return -1;
            if(movimento[0] == 0) // não andou verticalmente
                return -1;
            if(resultante[1] != peca[1] && tabuleiro[resultante[0]][resultante[1]] == ' ') // andou para o lado sem peca para matar
                return -1;
            if(movimento[1] > 1 || movimento[1] < -1) // andou mais que uma casa para o lado
                return -1;
            if(movimento[0] == 2 && peca[0] != 1) // andou duas casas para frente após a primeira jogada
                return -1;
            if(movimento[0] == 2 && movimento[1] != 0) // andou duas casas para frente e tambem uma para o lado
                return -1;
            if(movimento[0] > 2) // andou mais que duas casas para frente
                return -1;
            if(movimento[1] == 0 && tabuleiro[resultante[0]][resultante[1]] != ' ') // andou apenas para frente caindo numa casa ocupada
                return -1;
            if(movimento[1] == 0 && movimento[0] == 2 && tabuleiro[resultante[0]-1][resultante[1]] != ' ') // andou duas casas para frente com a casa intermediaria ocupada
                return -1;

            tabuleiro[resultante[0]][resultante[1]] = 'P';
            tabuleiro[peca[0]][peca[1]] = ' ';
        }
        return -1;
    }





}