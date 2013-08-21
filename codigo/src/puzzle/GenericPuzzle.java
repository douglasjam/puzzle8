package puzzle;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class GenericPuzzle {

    public static final String imagemPadrao = "resources/telas/cutecat.jpg";
    private static final Integer ordemPadrao = 3;
    //
    private Integer ordemMatriz;
    private BufferedImage imagemPuzzle;
    //
    private ArrayList<Integer> puzzleOriginal;
    private ArrayList<Integer> puzzleAtual;
//
    private ArrayList<String> logErros;
    private JLabel lblLog;

    public GenericPuzzle() {
        this(GenericPuzzle.imagemPadrao, GenericPuzzle.ordemPadrao);
    }

    public GenericPuzzle(String imagemPath, Integer ordemMatriz) {
        this.ordemMatriz = ordemMatriz;
        setImage(imagemPath);
        constroiPuzzle();

    }

    public void setImage(String imagemPath) {
        try {
            URL url = getClass().getClassLoader().getResource(imagemPath);
            imagemPuzzle = ImageIO.read(url);
        } catch (IOException ex) {
            Logger.getLogger(GenericPuzzle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setImage(BufferedImage imagem) {
        this.imagemPuzzle = imagem;
    }

    public ArrayList<Integer> getPuzzleOriginal() {
        return puzzleOriginal;
    }

    public void setPuzzleOriginal(ArrayList<Integer> puzzleOriginal) {
        this.puzzleOriginal = puzzleOriginal;
    }

    private void setOrdem(Integer ordem) {
        this.ordemMatriz = ordem;

    }

    private void constroiPuzzle() {

        int qtdeItens = ordemMatriz * ordemMatriz;

        puzzleOriginal = new ArrayList<Integer>();
        puzzleAtual = new ArrayList<Integer>();
        logErros = new ArrayList<String>();

        for (int i = 0; i < qtdeItens; i++) {
            puzzleOriginal.add(i);
            puzzleAtual.add(i);
        }

        puzzleOriginal.set(puzzleOriginal.size() - 1, null);
        puzzleAtual.set(puzzleAtual.size() - 1, null);

    }

    public ImageIcon getImageIconAt(int lin, int col) {

        int width = imagemPuzzle.getWidth() / ordemMatriz;
        int height = imagemPuzzle.getHeight() / ordemMatriz;
        int x = lin * width;
        int y = lin * height;

        BufferedImage retorno = imagemPuzzle.getSubimage(x, y, width, height);
        return new ImageIcon(retorno);
    }

    // aqui devemos retornar no puzzle atual, a posicao da imagem
    public ImageIcon getImageIconScaledAt(int lin, int col, int width, int height) {

        int elemento;
        
        // se achou o vazio entao retorna nenhuma imagem
        if (puzzleAtual.get(lin * ordemMatriz + col) == null) {
            return null;
        }

        elemento = puzzleAtual.get(lin * ordemMatriz + col);

        int w = imagemPuzzle.getWidth() / ordemMatriz;
        int h = imagemPuzzle.getHeight() / ordemMatriz;
        int x = (elemento / ordemMatriz) * w;
        int y = (elemento % ordemMatriz) * h;

        return new ImageIcon(imagemPuzzle.getSubimage(x, y, w, h).getScaledInstance(width, height, Image.SCALE_DEFAULT));
    }

    public ImageIcon getCompleteImage() {
        return new ImageIcon(imagemPuzzle);
    }

    public ImageIcon getCompleteScaledImage(int width, int height) {
        return new ImageIcon(imagemPuzzle.getScaledInstance(width, height, Image.SCALE_DEFAULT));
    }

    public void misturar() {

        long seed = System.currentTimeMillis();
        Collections.shuffle(puzzleAtual, new Random(seed));

    }

    public void setPuzzleAtual(ArrayList<Integer> puzzleAtual) {
        this.puzzleAtual = (ArrayList<Integer>) puzzleAtual.clone();
    }

    public ArrayList<Integer> getPuzzleAtual() {
        return puzzleAtual;
    }

    public void printPuzzle(ArrayList<Integer> puzzle) {
        Integer peca;

        for (int i = 0; i < ordemMatriz; i++) {
            for (int j = 0; j < ordemMatriz; j++) {
                peca = puzzle.get(i * ordemMatriz + j);
                System.out.print(peca == null ? "  " : peca + " ");
            }
            System.out.println("");
        }
    }

    public void printActualPuzzle() {
        Integer peca;

        for (int i = 0; i < ordemMatriz; i++) {
            for (int j = 0; j < ordemMatriz; j++) {
                peca = puzzleAtual.get(i * ordemMatriz + j);
                System.out.print(peca == null ? "  " : peca + " ");
            }
            System.out.println("");
        }
    }

    // TODO: a implementar
    public Boolean setPuzzleSeparatedByComma(String puzzle) {

        String[] novoPuzzle = puzzle.split(",");
        ArrayList<Integer> puzzleTemp = new ArrayList<Integer>();
        ArrayList<Integer> newPuzzle = new ArrayList<Integer>();

        if (novoPuzzle.length != 9) {
            return false;
        }

        for (int i = 0; i < novoPuzzle.length; i++) {

            novoPuzzle[i] = novoPuzzle[i].trim();

            if (novoPuzzle[i].toLowerCase().equals("x") && !puzzleTemp.contains(null)) {
                puzzleTemp.add(null);
            } else if (Integer.valueOf(novoPuzzle[i]) == null) {
                return false;
            } else if (puzzleTemp.contains(Integer.valueOf(novoPuzzle[i]))) {
                return false;
            } else if (Integer.valueOf(novoPuzzle[i]) < 0 || Integer.valueOf(novoPuzzle[i]) > 7) {
                return false;
            } else {
                puzzleTemp.add(Integer.valueOf(novoPuzzle[i]));
            }
        }

        for (int i = 0; i < ordemMatriz; i++) {
            for (int j = 0; j < ordemMatriz; j++) {
                newPuzzle.add(puzzleTemp.get(j * ordemMatriz + i));
            }
        }

        setPuzzleAtual(puzzleTemp);
        return true;
    }

    public String getActualPuzzleSeparatedByComma() {
        String retorno = "";
        Integer peca;

        for (int i = 0; i < ordemMatriz; i++) {
            for (int j = 0; j < ordemMatriz; j++) {
                peca = puzzleAtual.get(i * ordemMatriz + j);
                retorno += peca == null ? "x, " : peca + ", ";
                System.out.print(peca == null ? "x " : peca + ", ");
            }
            System.out.println("");
        }

        return retorno;
    }

    public Boolean isPecaCorreta(Integer x, Integer y) {
        int posArray = x * ordemMatriz + y;
        if (puzzleAtual.get(posArray) == posArray) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean isPuzzleCorrect() {
        if (puzzleOriginal == puzzleAtual) {
            return true;
        } else {
            return false;
        }
    }

    // posicao = {C [Cima], B [Baixo], E [Esquerda], D [Direita]}
    public Boolean move(String posicao) {
        return move(whereIsVazio(), posicao);
    }

    // posicao = {C [Cima], B [Baixo], E [Esquerda], D [Direita]}
    public Boolean move(Point a, String posicao) {
        return move((int) a.getX(), (int) a.getY(), posicao);
    }

    // posicao = {C [Cima], B [Baixo], E [Esquerda], D [Direita]}
    public Boolean move(Integer x, Integer y, String posicao) {

        Integer xf = x;
        Integer yf = y;

        // so permite os caracteres de posicao definidos
        if (posicao.equals("C") || posicao.equals("B") || posicao.equals("E") || posicao.equals("D")) {

            if (posicao.equals("C")) {
                xf -= 1;
                System.out.println("Move: CIMA");
            } else if (posicao.equals("B")) {
                xf += 1;
                System.out.println("Move: BAIXO");
            } else if (posicao.equals("E")) {
                yf -= 1;
                System.out.println("Move: ESQUERDA");
            } else {
                yf += 1;
                System.out.println("Move: DIREITA");
            }

            if (xf < 0 || yf < 0 || xf >= ordemMatriz || yf >= ordemMatriz) {
                addLog("Erro: não pode-se movimentar uma peça do extremo para fora");
                return false;
            }

            return move(x, y, xf, yf);

        } else {
            addLog("Erro: comando para troca \"" + posicao + "\" desconhecido");
            return false;
        }
    }

    public Boolean move(Point a, Point b) {
        return move((int) a.getX(), (int) a.getY(), (int) b.getX(), (int) b.getY());
    }

    public Boolean move(Integer xi, Integer yi, Integer xf, Integer yf) {

        Integer pecaA;
        Integer pecaB;


        try {
            pecaA = puzzleAtual.get(xi * ordemMatriz + yi);
            pecaB = puzzleAtual.get(xf * ordemMatriz + yf);
        } catch (IndexOutOfBoundsException ex) {
            addLog("Erro: movimento não permitido, ultrapassou o limite do vetor");
            return false;
        }

        if (pecaA == null && pecaB == null) {
            addLog("Erro: não pode-se trocar uma peça com ela própria");
            return false;

        } else {
            // troca as pecas de lugar
            puzzleAtual.set(xi * ordemMatriz + yi, pecaB);
            puzzleAtual.set(xf * ordemMatriz + yf, pecaA);
            addLog("Trocado: " + "(" + xi + "," + yi + ") => (" + xf + "," + yf + ")");
            return true;
        }
    }

    public Point whereIsVazio() {

        for (int i = 0; i < puzzleAtual.size(); i++) {
            if (puzzleAtual.get(i) == null) {
                return new Point(i / ordemMatriz, i % ordemMatriz);
            }
        }

        return null;
    }

    public Integer getOrdem() {
        return ordemMatriz;
    }

    public Integer getDistanciaPeca(ArrayList<Integer> puzzleOriginal, ArrayList<Integer> puzzleAlterado, Integer xPuzzleAlterado, Integer yPuzzleAlterado) {

        Integer posArray = xPuzzleAlterado * ordemMatriz + yPuzzleAlterado;
        Integer pecaAEncontrar;
        Integer posArrayOriginal;
        Integer distanciaX;
        Integer distanciaY;
        Integer retorno = 0;

        if (puzzleOriginal.get(posArray) != puzzleAlterado.get(posArray)) {

            pecaAEncontrar = puzzleAlterado.get(posArray);
            posArrayOriginal = puzzleOriginal.indexOf(pecaAEncontrar);
            distanciaX = ((posArrayOriginal / ordemMatriz) - (posArray / ordemMatriz));

            if (distanciaX < 0) {
                distanciaX *= -1;
            }
            distanciaY = ((posArrayOriginal % ordemMatriz) - (posArray % ordemMatriz));

            if (distanciaY < 0) {
                distanciaY *= -1;
            }

            retorno = distanciaX + distanciaY;
        }

        return retorno;
    }

    public Integer getDistanciaPeca(ArrayList<Integer> puzzleAlterado, Integer xPuzzleAlterado, Integer yPuzzleAlterado) {
        return getDistanciaPeca(this.puzzleOriginal, puzzleAlterado, xPuzzleAlterado, yPuzzleAlterado);
    }

    public Integer getDistanciaPeca(Integer xPuzzleAlterado, Integer yPuzzleAlterado) {
        return getDistanciaPeca(this.puzzleOriginal, this.puzzleAtual, xPuzzleAlterado, yPuzzleAlterado);
    }

    public Integer getDistanciaTodasPecas(ArrayList<Integer> puzzleOriginal, ArrayList<Integer> puzzleAlterado) {

        Integer retorno = 0;
        Integer x = 0;
        Integer y = 0;

        for (int i = 0; i < puzzleOriginal.size(); i++) {

            x = i / ordemMatriz;
            y = i % ordemMatriz;

            retorno += getDistanciaPeca(puzzleOriginal, puzzleAlterado, x, y);
        }

        return retorno;

    }

    public Integer getDistanciaTodasPecas(ArrayList<Integer> puzzleAlterado) {
        return getDistanciaTodasPecas(this.puzzleOriginal, puzzleAlterado);
    }

    public Integer getDistanciaTodasPecas() {
        return getDistanciaTodasPecas(this.puzzleOriginal, this.puzzleAtual);
    }

    public Integer heuristicaNumeroPecas() {

        Integer resultado = 0;
        for (int i = 0; i < puzzleOriginal.size(); i++) {
            if (puzzleOriginal.get(i) != puzzleAtual.get(i)) {
                resultado++;
            }
        }
        return resultado;
    }

    public void setLabelLog(JLabel lblLog) {
        this.lblLog = lblLog;
    }

    public void addLog(String erro) {
        System.out.println(erro);
        logErros.add(erro);
        if (lblLog != null) {
            lblLog.setText(erro);
        }
    }

    public String getLastError() {
        return logErros.get(logErros.size() - 1);
    }

    public Integer getQtdeErros() {
        return logErros.size();
    }
}
