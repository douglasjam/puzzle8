/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ia;

import java.util.ArrayList;

/**
 *
 * @author Leandro Cesar
 */
public class AEstrelaNo {
    private int custoG = 0;
    private int custoH = 999;
    private AEstrelaNo pai = null;
    private ArrayList<Integer> valor = null;
    private String direcao = null;

    public AEstrelaNo(){
        
    }
    
    public AEstrelaNo(ArrayList<Integer> valor) {
        this.valor = valor;
    }
    
    public String getDirecao() {
        return direcao;
    }

    public void setDirecao(String direcao) {
        this.direcao = direcao;
    }
    
    
    public int getCustoF() {
        return custoG + custoH;
    }

    public int getCustoG() {
        return custoG;
    }

    public void setCustoG(int custoG) {
        this.custoG = custoG;
    }

    public int getCustoH() {
        return custoH;
    }

    public void setCustoH(int custoH) {
        this.custoH = custoH;
    }

    public AEstrelaNo getPai() {
        return pai;
    }

    public void setPai(AEstrelaNo pai) {
        this.pai = pai;
    }

    public ArrayList<Integer> getValor() {
        return valor;
    }

    public void setValor(ArrayList<Integer> valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AEstrelaNo other = (AEstrelaNo) obj;
        if (this.valor != other.valor && (this.valor == null || !this.valor.equals(other.valor))) {
            return false;
        }
        return true;
    }
    
}
