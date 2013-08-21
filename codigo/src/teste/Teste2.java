package teste;

import ia.AEstrela;
import java.util.ArrayList;
import puzzle.GenericPuzzle;
import uteis.Uteis;

public class Teste2 {

    public static void main(String[] args) {

        GenericPuzzle puzzle = new GenericPuzzle();
        ArrayList<Integer> arrayMeta;
        
        arrayMeta = Uteis.initArrayList(0, 1, null, 3, 4, 2, 6, 7, 5);
        puzzle.setPuzzleAtual(arrayMeta);
        puzzle.printActualPuzzle();
        System.out.println("distancia total: " + puzzle.getDistanciaTodasPecas());

        arrayMeta = Uteis.initArrayList(0, 1, 2, 3, 4, 5, 6, 7, null);
        puzzle.setPuzzleAtual(arrayMeta);
        puzzle.printActualPuzzle();
        System.out.println("distancia total: " + puzzle.getDistanciaTodasPecas());


    }
}
