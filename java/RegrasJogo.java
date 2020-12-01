public class RegrasJogo {

    private char[][] tabuleiro = {{'T', 'C', 'B', 'A', 'R', 'B', 'C', 'T'},
                                  {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
                                  {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                                  {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                                  {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                                  {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                                  {'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'},
                                  {'t', 'c', 'b', 'a', 'r', 'b', 'c', 't'}};

    public char[][] getTabuleiro() {
        return tabuleiro;
    }

    public int movimentarPeca(int[] peca, int[] movimento) {
        // posição em que a peça irá se encontrar apos o movimento
        int[] resultante = {peca[0] + movimento[0], peca[1] + movimento[1]};

        if(movimentoPermitido(peca, movimento)) {
            tabuleiro[resultante[0]][resultante[1]] = tabuleiro[peca[0]][peca[1]];
            tabuleiro[peca[0]][peca[1]] = ' ';
            return 0;
        }

        return -1;
    }


    public boolean movimentoPermitido(int[] peca, int[] movimento) {
        int[] resultante = {peca[0] + movimento[0], peca[1] + movimento[1]};

        // regras iguais para todas as peças
        if(movimento[0] == 0 && movimento[1] == 0) // não andou
            return false;
        if(resultante[0] > 7 || resultante[0] < 0 || resultante[1] > 7 || resultante[1] < 0 ) // fora dos limites
            return false;
        if(tabuleiro[peca[0]][peca[1]] > 90) { // tentou matar um aliado branco
            if(tabuleiro[resultante[0]][resultante[1]] > 90)
                return false;
        }else {                                 // tentou matar um aliado preto
            if(tabuleiro[resultante[0]][resultante[1]] < 97 && tabuleiro[resultante[0]][resultante[1]] != 32)
                return false;
        }
        
        // regras do peão
        if(tabuleiro[peca[0]][peca[1]] == 'p' || tabuleiro[peca[0]][peca[1]] == 'P') {
            // regras iguais para ambos os lados
            if(movimento[0] == 0) // não andou verticalmente
                return false;
            if(resultante[1] != peca[1] && tabuleiro[resultante[0]][resultante[1]] == ' ') // andou para o lado sem peca para matar
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
                    return false;
                if(movimento[1] == 0 && movimento[0] == -2 && tabuleiro[resultante[0]+1][resultante[1]] != ' ') // andou duas casas para frente com a casa intermediaria ocupada
                    return false;

            }else if(tabuleiro[peca[0]][peca[1]] == 'P') {
                if(resultante[0] < peca[0]) // andou para tras
                    return false;
                if(movimento[0] == 2 && peca[0] != 1) // andou duas casas para frente após a primeira jogada
                    return false;
                if(movimento[0] == 2 && movimento[1] != 0) // andou duas casas para frente e tambem uma para o lado
                    return false;
                if(movimento[0] > 2) // andou mais que duas casas para frente
                    return false;
                if(movimento[1] == 0 && movimento[0] == 2 && tabuleiro[resultante[0]-1][resultante[1]] != ' ') // andou duas casas para frente com a casa intermediaria ocupada
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
            if(movimento[0] != 0 && movimento[1] != 0) // não andou horizontalmente ou verticalmente
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
            }else if(movimento[0] == 0 || movimento[1] == 0) {  // andou horizontalmente ou verticalmente
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
		if(tabuleiro[peca[0]][peca[1]] == 'c' || tabuleiro[peca[0]][peca[1]] == 'C'){
                // todos movimentos possiveis do cavalo
			if(resultante[0]==peca[0]+2 && resultante[1]==peca[1]+1)
                return true;
            if(resultante[0]==peca[0]+2 && resultante[1]==peca[1]-1)
                return true;
            if(resultante[0]==peca[0]-2 && resultante[1]==peca[1]+1)
                return true;
            if(resultante[0]==peca[0]-2 && resultante[1]==peca[1]-1)
                return true;
            if(resultante[0]==peca[0]-1 && resultante[1]==peca[1]+2)
                return true;
            if(resultante[0]==peca[0]+1 && resultante[1]==peca[1]-2)
                return true;
            if(resultante[0]==peca[0]+1 && resultante[1]==peca[1]+2)
                return true;
            if(resultante[0]==peca[0]-1 && resultante[1]==peca[1]-2)
                return true;	

			return false;				
		}
	
        
        return false;
    }


}