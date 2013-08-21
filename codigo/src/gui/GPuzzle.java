package gui;

import ia.AEstrela;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import puzzle.GenericPuzzle;
import uteis.Uteis;

/**
 * @author douglasjam
 */
public class GPuzzle extends JFrame {

    private JMenuBar mnBar;
    private JMenu mnArquivo;
    private JMenuItem mnNovo;
    private JMenuItem mnSair;
    private JMenu mnAjuda;
    private JMenuItem mnSobre;
    private JPanel pnlPuzzle;
    private JPanel pnlMiniatura;
    private JLabel lblMiniatura;
    private JPanel pnlRodape;
    private JLabel lblRodape;
    private JTextField txtOrdem;
    private JButton btnMisturar;
    private JButton btnResolver;
    private JButton btnCarregar;
    private JButton btnArquivoImagens;
    private JButton btnReproduzir;
    private JLabel lblJogadas;
    private JTextField txtJogadas;
    private JLabel lblTempo;
    private JTextField txtTempo;
    private JLabel lblNaoSei1;
    private JTextField txtNaoSei1;
    private JLabel lblNaoSei2;
    private JTextField txtNaoSei2;
    private JLabel lblNaoSei3;
    private JTextField txtNaoSei3;
    //
    GenericPuzzle puzzle;
    ArrayList<GPeca> gpecas = new ArrayList<GPeca>();
    String movimento;
    Timer cronometro;
    Integer tempoSegundos;
    //
    AEstrela ia;
    ArrayList<String> iaResultado;

    public GPuzzle() throws HeadlessException {

        configuraJanela();
        montaJanela();
        criaListeners();
        criaTimer();
        iniciaJogo();
    }

    public void configuraJanela() {

        setSize(new Dimension(546, 450));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //
        setLayout(null);
        //
        ImageIcon icon = new ImageIcon(getClass().getResource("/resources/icones/puzzle-icon.png"));
        setTitle("Quebra Cabeça 8 - by Hidenove");
        setIconImage(icon.getImage());
        //


    }

    public void montaJanela() {

        // menu
        mnBar = new JMenuBar();
        //
        mnArquivo = new JMenu("Arquivo");
        mnNovo = new JMenuItem("Novo");
        mnSair = new JMenuItem("Sair");
        //
        mnAjuda = new JMenu("Ajuda");
        mnSobre = new JMenuItem("Sobre");

        this.add(mnBar);
        mnBar.setBounds(0, 0, 640, 24);

        mnBar.add(mnArquivo);
        mnArquivo.add(mnNovo);
        mnArquivo.add(mnSair);
        mnBar.add(mnAjuda);
        mnAjuda.add(mnSobre);

        // pnlPuzzle
        pnlPuzzle = new JPanel();
        pnlPuzzle.setLayout(null);
        this.add(pnlPuzzle);
        pnlPuzzle.setBackground(new Color(210, 210, 210));
        pnlPuzzle.setBounds(10, 34, 350, 350);

        // pnlMiniatura
        pnlMiniatura = new JPanel();
        pnlMiniatura.setLayout(null);
        pnlMiniatura.setBounds(370, 234, 150, 150);
        pnlMiniatura.setBackground(new Color(210, 210, 210));
        this.add(pnlMiniatura);

        lblMiniatura = new JLabel();
        lblMiniatura.setBounds(0, 0, 150, 150);
        pnlMiniatura.add(lblMiniatura);

        // pnlRodape
        pnlRodape = new JPanel();
        pnlRodape.setBounds(0, 390, 640, 24);
        this.add(pnlRodape);

        lblRodape = new JLabel();
        lblRodape.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        pnlRodape.add(lblRodape);

        //

        txtOrdem = new JTextField();
        txtOrdem.setBounds(373, 34, 147, 24);
        this.add(txtOrdem);

        //--------------------------
        // botões
        //--------------------------

        btnCarregar = new JButton(new ImageIcon(getClass().getResource("/resources/icones/carregar.png")));
        btnCarregar.setBounds(373, 63, 24, 24);
        btnCarregar.setToolTipText("Carregar imagem do disco");
        this.add(btnCarregar);

        //
        btnArquivoImagens = new JButton(new ImageIcon(getClass().getResource("/resources/icones/imagens.png")));
        btnArquivoImagens.setBounds(403, 63, 24, 24);
        btnArquivoImagens.setToolTipText("Selecionar imagem padrão");
        this.add(btnArquivoImagens);

        //
        btnMisturar = new JButton(new ImageIcon(getClass().getResource("/resources/icones/misturar.png")));
        btnMisturar.setBounds(433, 63, 24, 24);
        btnMisturar.setToolTipText("Misturar peças");
        this.add(btnMisturar);

        //
        btnResolver = new JButton(new ImageIcon(getClass().getResource("/resources/icones/resolver.png")));
        btnResolver.setBounds(463, 63, 24, 24);
        btnResolver.setToolTipText("Resolver com o algoritmo A*");
        this.add(btnResolver);

        //
        btnReproduzir = new JButton(new ImageIcon(getClass().getResource("/resources/icones/play.png")));
        btnReproduzir.setBounds(495, 63, 24, 24);
        btnReproduzir.setToolTipText("Reproduzir movimentos");
        this.add(btnReproduzir);

        //

        lblJogadas = new JLabel("Jogadas");
        lblJogadas.setBounds(373, 92, 60, 24);
        this.add(lblJogadas);

        txtJogadas = new JTextField("0");
        txtJogadas.setBounds(443, 92, 77, 24);
        this.add(txtJogadas);

        //

        lblTempo = new JLabel("Tempo");
        lblTempo.setBounds(373, 119, 60, 24);
        this.add(lblTempo);

        txtTempo = new JTextField("00:00");
        txtTempo.setBounds(443, 119, 77, 24);
        this.add(txtTempo);

        //

        lblNaoSei1 = new JLabel("Medidor 1");
        lblNaoSei1.setBounds(373, 148, 60, 24);
        this.add(lblNaoSei1);

        txtNaoSei1 = new JTextField("");
        txtNaoSei1.setBounds(443, 148, 77, 24);
        this.add(txtNaoSei1);

        //

        lblNaoSei2 = new JLabel("Medidor 2");
        lblNaoSei2.setBounds(373, 176, 60, 24);
        this.add(lblNaoSei2);

        txtNaoSei2 = new JTextField("");
        txtNaoSei2.setBounds(443, 176, 77, 24);
        this.add(txtNaoSei2);

        //

        lblNaoSei3 = new JLabel("Medidor 3");
        lblNaoSei3.setBounds(373, 205, 60, 24);
        this.add(lblNaoSei3);

        txtNaoSei3 = new JTextField("");
        txtNaoSei3.setBounds(443, 205, 77, 24);
        this.add(txtNaoSei3);
    }

    public void iniciaJogo() {

        puzzle = new GenericPuzzle();
        puzzle.setLabelLog(lblRodape);
        puzzle.misturar();
        resetaJogadas();
        atualizaPecas();
        txtOrdem.setText(puzzle.getActualPuzzleSeparatedByComma());

    }

    public void atualizaPecas() {

        gpecas = new ArrayList<GPeca>();
        pnlPuzzle.removeAll();

        int lblSize = pnlPuzzle.getWidth() / puzzle.getOrdem();

        for (int i = 0; i < puzzle.getOrdem(); i++) {
            for (int j = 0; j < puzzle.getOrdem(); j++) {
                GPeca gpeca = new GPeca();
                gpeca.setIcon(puzzle.getImageIconScaledAt(j, i, lblSize, lblSize));
                pnlPuzzle.add(gpeca);
                gpeca.setBounds(i * lblSize, j * lblSize, 120, 120);
                gpecas.add(gpeca);
            }
        }

        lblMiniatura.setIcon(puzzle.getCompleteScaledImage(lblMiniatura.getWidth(), lblMiniatura.getHeight()));
        criaListenerPecas();

    }

    public void criaTimer() {

        tempoSegundos = 0;

        cronometro = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tempoSegundos++;
                txtTempo.setText(Uteis.MinSecIntegerToMinSecString(tempoSegundos));
            }
        });


    }

    public void carregaImagem() {

        BufferedImage image;

        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Todos os arquivos de imagem", "bmp", "dib", "jpeg", "jpg", "jfif", "gif", "tiff", "tif", "png", "ico"));

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                image = ImageIO.read(new File(fileChooser.getSelectedFile().getPath()));
                puzzle.setImage(image);
                atualizaPecas();
            } catch (IOException ex) {
                Logger.getLogger(GPuzzle.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void selecionaImagemPadrao() {

        // criar jscroll + jlist com listagem das imagens do pacote /resources/tela
        // onde exibe miniatura da imagem a direita
        // com botao de ok e cancelar
        puzzle.setImage(GenericPuzzle.imagemPadrao);
        atualizaPecas();
        System.out.println("a fazer...");

    }

    public void exibeGameCompletado() {
        Object[] options = {"Sim", "Não"};
        int retorno;
        retorno = JOptionPane.showOptionDialog(null, "Parabéns, você venceu o jogo, deseja reiniciar?", "VITÓRIA", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (retorno == JOptionPane.YES_OPTION) {
            resetaJogo();
        }
    }

    public String getMovimento(Point ponto) {
        int angulo = 0;
        int dx = (int) (ponto.getX() - 60);
        int dy = (int) -(ponto.getY() - 60);

        angulo = (int) Math.toDegrees(Math.atan2(dy, dx));

        // atencao aqui eu inverto, pois a pessoa 
        // deve achar que meche a peca preenchida e não
        // a lacuna
        if (angulo > 45 && angulo < 135) {
            return "C";
        } else if (angulo > -45 && angulo < 45) {
            return "D";
        } else if (angulo > -135 && angulo < -45) {
            return "B";
        } else {
            return "E";
        }
    }

    public void mostraSobre() {

        final JDialog dialogSobre = new JDialog(this, "Digite o texto", true);
        dialogSobre.setSize(320, 310);
        dialogSobre.setLayout(null);

        //

        JLabel lblFaculdade = new JLabel("Faculdade");
        lblFaculdade.setBounds(10, 10, 100, 24);
        dialogSobre.add(lblFaculdade);

        JTextField txtFaculdade = new JTextField("Universidade de Itaúna");
        txtFaculdade.setBounds(120, 10, 170, 24);
        txtFaculdade.setEditable(false);
        dialogSobre.add(txtFaculdade);

        //

        JLabel lblCurso = new JLabel("Curso");
        lblCurso.setBounds(10, 39, 100, 24);
        dialogSobre.add(lblCurso);

        JTextField txtCurso = new JTextField("Ciência da Computação");
        txtCurso.setBounds(120, 39, 170, 24);
        txtCurso.setEditable(false);
        dialogSobre.add(txtCurso);

        //

        JLabel lblAutores = new JLabel("Autores");
        lblAutores.setBounds(10, 68, 100, 24);
        dialogSobre.add(lblAutores);

        JTextArea txaAutores = new JTextArea("Douglas Antunes\nLeandro César");
        txaAutores.setEditable(false);

        JScrollPane srlAutores = new JScrollPane(txaAutores);
        srlAutores.setBounds(120, 68, 170, 45);
        dialogSobre.add(srlAutores);

        //

        JLabel lblDataCriacao = new JLabel("Data Criação");
        lblDataCriacao.setBounds(10, 117, 100, 24);
        dialogSobre.add(lblDataCriacao);

        JTextField txtDataCriacao = new JTextField("01/11/2012 23:50");
        txtDataCriacao.setBounds(120, 117, 170, 24);
        txtDataCriacao.setEditable(false);
        dialogSobre.add(txtDataCriacao);

        //

        JLabel lblHelp = new JLabel("Sobre");
        lblHelp.setBounds(10, 146, 100, 24);
        dialogSobre.add(lblHelp);

        JTextArea txaHelp = new JTextArea(loadSobre());
        txaHelp.setEditable(false);

        JScrollPane srlHelp = new JScrollPane(txaHelp);
        srlHelp.setBounds(120, 146, 170, 87);
        dialogSobre.add(srlHelp);

        //

        JButton btnFechar = new JButton("Fechar");
        btnFechar.setBounds(10, 240, 285, 24);
        dialogSobre.add(btnFechar);

        btnFechar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialogSobre.dispose();
            }
        });

        dialogSobre.setLocationRelativeTo(null);
        dialogSobre.setVisible(true);

    }

    public String loadSobre() {

        String retorno = "";
        byte[] buffer = new byte[1024];
        int len;

        InputStream in = getClass().getResourceAsStream("/resources/sobre.txt");
        retorno = Uteis.inputStreamToString(in);

        return retorno;
    }

    public void resetaJogo() {
        puzzle.misturar();
        resetaJogadas();
        txtOrdem.setText(puzzle.getActualPuzzleSeparatedByComma());
        atualizaPecas();
    }

    public void criaListeners() {

        mnNovo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               resetaJogo();
            }
        });
        
        
        mnSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                System.exit(0);
            }
        });
        
        mnSobre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostraSobre();
            }
        });

        btnCarregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregaImagem();
            }
        });


        btnArquivoImagens.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selecionaImagemPadrao();
            }
        });

        btnMisturar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetaJogo();
            }
        });

        btnResolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnResolver.setIcon(new ImageIcon(getClass().getResource("/resources/icones/spinner.gif")));
                lblRodape.setText("Encontrando solucao para o jogo, aguarde.");
                System.out.println("vai resolver...");
                ia = new AEstrela(puzzle.getOrdem(), puzzle.getPuzzleAtual());
                System.out.println("resolvendo...");
                iaResultado = ia.resolveQuebraCabeca();
                System.out.println("resolvido");
                btnResolver.setIcon(new ImageIcon(getClass().getResource("/resources/icones/resolver.gif")));
                lblRodape.setText("Solução encontrada, clique em play para ve-la");
            }
        });

        btnReproduzir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reproduzirResultado();
            }
        });




        txtOrdem.addKeyListener(new KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {

                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (!puzzle.setPuzzleSeparatedByComma(txtOrdem.getText())) {
                        JOptionPane.showMessageDialog(null, "Erro ao alterar a ordem, verifique se a mesma obedece os padrões", "Erro", JOptionPane.ERROR_MESSAGE);
                        txtOrdem.setText(puzzle.getActualPuzzleSeparatedByComma());
                    } else {
                        atualizaPecas();
                        puzzle.printActualPuzzle();
                    }
                }
            }
        });

        criaListenerPecas();



    }

    public void criaListenerPecas() {
        for (GPeca peca : gpecas) {

            peca.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    super.mouseMoved(e);
                    String movimento = getMovimento(e.getPoint());
                    setCursor(getCursor(movimento));
                }
            });

            peca.addMouseListener(new MouseAdapter() {
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    setCursor(getCursor("MOVE"));
                }

                public void mouseReleased(java.awt.event.MouseEvent evt) {
                    String movimento = getMovimento(evt.getPoint());
                    super.mouseReleased(evt);
                    puzzle.move(inverteMovimento(movimento));
                    incrementaJogada();
                    atualizaPecas();
                    setCursor(getCursor("DEFAULT"));

                    if (puzzle.isPuzzleCorrect()) {
                        exibeGameCompletado();
                    }
                }
            });

        }

    }

    public void incrementaJogada() {
        Integer jogadas = Integer.valueOf(txtJogadas.getText());
        jogadas++;
        txtJogadas.setText(String.valueOf(jogadas));
        if (jogadas > 0 && !cronometro.isRunning()) {
            cronometro.start();
        }
    }

    public void resetaJogadas() {
        txtJogadas.setText("0");
        txtTempo.setText("00:00");
        tempoSegundos = 0;
        if (cronometro.isRunning()) {
            cronometro.stop();
        }
    }

    public String inverteMovimento(String movimento) {
        if (movimento.equals("C")) {
            return "B";
        } else if (movimento.equals("B")) {
            return "C";
        } else if (movimento.equals("E")) {
            return "D";
        } else if (movimento.equals("D")) {
            return "E";
        } else {
            return null;
        }
    }

    public Cursor getCursor(String tipo) {

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = null;
        String cursorPath;

        if (tipo.equals("C")) {
            cursorPath = "/resources/icones/cima.png";
        } else if (tipo.equals("E")) {
            cursorPath = "/resources/icones/esquerda.png";
        } else if (tipo.equals("D")) {
            cursorPath = "/resources/icones/direita.png";
        } else if (tipo.equals("B")) {
            cursorPath = "/resources/icones/baixo.png";
        } else if (tipo.equals("MOVE")) {
            cursorPath = "/resources/icones/move.png";
        } else {
            return Cursor.getDefaultCursor();
        }

        image = new javax.swing.ImageIcon(getClass().getResource(cursorPath)).getImage();
        Point hotSpot = new Point(16, 16);

        return toolkit.createCustomCursor(image, hotSpot, "QUADRADO");
    }

    public void reproduzirResultado() {
        if (iaResultado != null && iaResultado.size() > 0) {
            for (String movimento : iaResultado) {
                try {
                    puzzle.move(movimento);
                    Thread.sleep(250);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GPuzzle.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            lblRodape.setText("Não existem movimentos na memória");
        }
    }
}
