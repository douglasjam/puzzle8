package teste;

import ia.AEstrela;
import java.util.ArrayList;
import uteis.Uteis;

public class Douglas {

    public static void main(String[] args) {

//        GenericPuzzle puzzle = new GenericPuzzle();

//        puzzle.randomize();
//        puzzle.printActualPuzzle();
//        puzzle.move("B");
//        puzzle.printActualPuzzle();
        ArrayList<Integer> arrayMeta = new ArrayList<Integer>();
        
        arrayMeta.add(0);
        arrayMeta.add(1);
        arrayMeta.add(2);
        arrayMeta.add(3);        
        arrayMeta.add(4);
        arrayMeta.add(5); 
        arrayMeta.add(6);
        arrayMeta.add(7);
        arrayMeta.add(null);        
        
        AEstrela ia = new AEstrela(3, arrayMeta);
        ArrayList<String> resultado = ia.resolveQuebraCabeca();
        if (resultado != null){
            for (int i = 0; i < resultado.size(); i++) {
                System.out.println(resultado.get(i) + "  ");
            }
        }else
            System.out.println("Array ordenado");

    }
}
