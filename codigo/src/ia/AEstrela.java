/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ia;

import java.util.ArrayList;
import java.util.HashMap;
import puzzle.GenericPuzzle;

/**
 *
 * @author Leandro Cesar
 */
public class AEstrela {

    private int ordemMatriz;
    private ArrayList<AEstrelaNo> listaAberta;
    private ArrayList<AEstrelaNo> listaFechada;
    private AEstrelaNo origem;
    private AEstrelaNo estadoMeta;
    private ArrayList<String> resultado;
    private GenericPuzzle puzzle;

    public AEstrela(int ordemMatriz, ArrayList<Integer> origemArray) {
        this.origem = new AEstrelaNo();
        listaAberta = new ArrayList<AEstrelaNo>();
        listaFechada = new ArrayList<AEstrelaNo>();
        resultado = new ArrayList<String>();
        puzzle = new GenericPuzzle();
        estadoMeta = new AEstrelaNo(puzzle.getPuzzleOriginal());
        this.ordemMatriz = ordemMatriz;
        origem.setValor(origemArray);
    }

    public ArrayList<String> resolveQuebraCabeca() {
        listaAberta.add(getOrigem());
        if (proximoMovimento()) {
            return resultado;
        }
        return null;
    }

    ;
    
    public boolean proximoMovimento() {

        AEstrelaNo corrente = listaAberta.get(0);

        while (!corrente.equals(estadoMeta)) {

            corrente = listaAberta.get(0);

            //Procura o nó com menor custo
            for (int i = 0; i < listaAberta.size(); i++) {
                if (listaAberta.get(i).getCustoF() < corrente.getCustoF()) {
                    corrente = listaAberta.get(i);
                }
            }

            //Adiciona o nó corrente na lista de verificados e remove da lista que será percorrida
            listaFechada.add(corrente);
            listaAberta.remove(corrente);

            puzzle.setPuzzleAtual(corrente.getValor());
            resultado.add(corrente.getDirecao());

            //puzzle.printActualPuzzle();

            // posicao = {C [Cima], B [Baixo], E [Esquerda], D [Direita]}
            String direcao = "DECB";
            for (int i = 0; i < 4; i++) {
                //Percorre as adjacências a procura de movimentos válidos
                if (puzzle.move(direcao.substring(i, i + 1))) {
                    AEstrelaNo direcaoArray = new AEstrelaNo(puzzle.getPuzzleAtual());
                    if (!listaFechada.contains(direcaoArray)) {
                        int custoG = corrente.getCustoG() + 1;
                        //Método de Manhattan
                        int custoH = puzzle.getDistanciaTodasPecas();
                        //Número de peças corretas
                        //int custoH = puzzle.heuristicaNumeroPecas();
                        
                        direcaoArray.setPai(corrente);
                        direcaoArray.setDirecao(direcao.substring(i, i + 1));
                        
                        //Insere o nó na lista aberta, se ainda não estiver. Caso contrário atualiza os valores.
                        if (!listaAberta.contains(direcaoArray)) {
                            direcaoArray.setCustoG(custoG);
                            direcaoArray.setCustoH(custoH);
                            listaAberta.add(direcaoArray);
                        } else {
                            if (direcaoArray.getCustoH() > custoH) {
                                listaAberta.remove(direcaoArray);
                                direcaoArray.setCustoG(custoG);
                                direcaoArray.setCustoH(custoH);
                                listaAberta.add(direcaoArray);
                            }
                        }
                    }
                }
                puzzle.setPuzzleAtual(corrente.getValor());
            }
            //Se a lista aberta está vazia não é possível encontrar uma solução
            if (listaAberta.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public AEstrelaNo getOrigem() {
        return origem;
    }

    public void setOrigem(AEstrelaNo origem) {
        this.origem = origem;
    }

    public ArrayList<AEstrelaNo> getListaAberta() {
        return listaAberta;
    }

    public void setListaAberta(ArrayList<AEstrelaNo> listaAberta) {
        this.listaAberta = listaAberta;
    }

    public ArrayList<AEstrelaNo> getListaFechada() {
        return listaFechada;
    }

    public void setListaFechada(ArrayList<AEstrelaNo> listaFechada) {
        this.listaFechada = listaFechada;
    }

    public int getOrdemMatriz() {
        return ordemMatriz;
    }

    public void setOrdemMatriz(int ordemMatriz) {
        this.ordemMatriz = ordemMatriz;
    }
}
