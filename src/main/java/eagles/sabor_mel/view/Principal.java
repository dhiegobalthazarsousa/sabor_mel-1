package eagles.sabor_mel.view;

import eagles.sabor_mel.control.ControllerEstado;
import eagles.sabor_mel.control.ControllerFuncionario;
import eagles.sabor_mel.control.ControllerPessoa;
import eagles.sabor_mel.control.ControllerProduto;
import eagles.sabor_mel.control.ControllerVendas;
import eagles.sabor_mel.control.Validacao;
import eagles.sabor_mel.model.Acesso;
import eagles.sabor_mel.model.Sexo;
import eagles.sabor_mel.model.TipoDocumento;
import eagles.sabor_mel.model.TipoTelefone;
import eagles.sabor_mel.model.TipoVenda;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.persistence.NoResultException;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Tiago Lima Villalobos
 */
public class Principal extends javax.swing.JFrame {
    /**
     * Creates new form Principal
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.io.UnsupportedEncodingException
     */
    public Principal() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        try{
            Map <String, String> funcionario =
                    ControllerFuncionario.searchFuncionario(Login.login.getText(), Login.senha.getText());

            initComponents();
            
            if(funcionario.get("acesso").equals("Vendedor")){
                btnCompra.setEnabled(false);
                btnRelatorio.setEnabled(false);
                btnFornecedor.setEnabled(false);
                btnUsuario.setEnabled(false);
            }
            
            carregaComboEstados();
            logado.setText(funcionario.get("nome"));

            this.setExtendedState(this.MAXIMIZED_BOTH);   
        }
        catch(NullPointerException e){
            JOptionPane.showMessageDialog(null, "Permissão Negada...");
            this.dispose();
            new Login().setVisible(true);
        }
        
        
    }

    public void carregaComboEstados() {
        List<Map<String, String>> estados = ControllerEstado.listEstados();
        
        for(int i = 0; i < estados.size(); i++){
            estadoUsuario.addItem(estados.get(i).get("uf"));
            estadoCliente.addItem(estados.get(i).get("uf"));
            estadoFornecedor.addItem(estados.get(i).get("uf"));
        }
    }
    
    public void estiloPadrao() {
        /*Background para os combobox*/
        acessoUsuario.setBackground(Color.white);
        estadoUsuario.setBackground(Color.white);
        sexoUsuario.setBackground(Color.white);
        vendaParcela.setBackground(Color.white);
        
        /*Usuários*/
        checkSenha.setEnabled(false);
        deleteUsuario.setEnabled(false);
        confirmUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/checked (1).png")));
        labelTelefoneUsuario2.setEnabled(false);
        dddUsuario2.setEnabled(false);
        telefoneUsuario2.setEnabled(false);
        
        /*Cliente*/
        deleteCliente.setEnabled(false);
        confirmCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/checked (1).png")));
        labelTelefoneCliente2.setEnabled(false);
        dddCliente2.setEnabled(false);
        telefoneCliente2.setEnabled(false);
        
        /*Fornecedor*/
        deleteFornecedor.setEnabled(false);
        confirmFornecedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/checked (1).png")));
        labelTelefoneFornecedor2.setEnabled(false);
        dddFornecedor2.setEnabled(false);
        telefoneFornecedor2.setEnabled(false);
        
        /*Produtos*/
        deleteProduto.setEnabled(false);
        
        /*Venda Parcelada*/
        labelParcelasVenda.setEnabled(false);
        vendaParcela.setEnabled(false);
        labelDataVencimentoParcela.setEnabled(false);
        dataVencimentoParcela.setEnabled(false);
        
        /*Venda a Vista*/
        labelPagoVenda.setEnabled(false);
        labelTotalVenda.setEnabled(false);
        labelTrocoVenda.setEnabled(false);
        valorPagoVenda.setEnabled(false);
        valorTotalVenda.setEnabled(false);
        valorTrocoVenda.setEnabled(false);
        calculaTrocoVenda.setEnabled(false);
    }

    public void carregaTabela(String menu) {
        List<Map<String, String>> lista = null;
        
        if(menu.equals("usuario")){
            lista = ControllerFuncionario.listFuncionarios();
            ((DefaultTableModel)tabelaUsuario.getModel()).setNumRows(0);
            
            for(int i = 0; i < lista.size(); i++){
                
                ((DefaultTableModel)tabelaUsuario.getModel()).addRow(new String[]{
                    lista.get(i).get("id"),
                    lista.get(i).get("nome"),
                    lista.get(i).get("usuario"), 
                    lista.get(i).get("acesso")
                });

                confirmUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/checked (1).png")));
                deleteUsuario.setEnabled(false);
            }
        }
        else{
            if(menu.equals("cliente")){
                lista = ControllerPessoa.listClientes();
                
                ((DefaultTableModel)tabelaCliente.getModel()).setNumRows(0);
                
                for(int i = 0; i < lista.size(); i++){
                    
                    ((DefaultTableModel)tabelaCliente.getModel()).addRow(new String[]{
                        lista.get(i).get("idPessoa"),
                        lista.get(i).get("nome")
                    });
                   
                    confirmCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/checked (1).png")));
                    deleteCliente.setEnabled(false);
                }
            }
            else{
                if(menu.equals("fornecedor")){
                   lista = ControllerPessoa.listFornecedores();
                
                    ((DefaultTableModel)tabelaFornecedor.getModel()).setNumRows(0);

                    for(int i = 0; i < lista.size(); i++){

                        ((DefaultTableModel)tabelaFornecedor.getModel()).addRow(new String[]{
                            lista.get(i).get("id"),
                            lista.get(i).get("nome")
                        });

                        confirmFornecedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/checked (1).png")));
                        deleteFornecedor.setEnabled(false);
                    }
                }
                else{
                    if(menu.equals("produto")){
                        lista = ControllerProduto.listProdutos();
                
                        ((DefaultTableModel)tabelaProduto.getModel()).setNumRows(0);

                        for(int i = 0; i < lista.size(); i++){

                            ((DefaultTableModel)tabelaProduto.getModel()).addRow(new String[]{
                                lista.get(i).get("id"),
                                lista.get(i).get("descricao"),
                                lista.get(i).get("valorUnitario"),
                                lista.get(i).get("quantidade"),
                                lista.get(i).get("total")
                            });

                            confirmProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/checked (1).png")));
                            deleteProduto.setEnabled(false);
                        }
                    }
                }
            }
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        opcoesPagamento = new javax.swing.ButtonGroup();
        logo = new javax.swing.JLabel();
        eagles = new javax.swing.JLabel();
        mainPanel = new javax.swing.JPanel();
        vendas = new javax.swing.JPanel();
        fecharVenda = new javax.swing.JButton();
        panelVendaMetodoPagamento = new javax.swing.JPanel();
        radioVendaVista = new javax.swing.JRadioButton();
        radioVendaParcelado = new javax.swing.JRadioButton();
        panelVendaPagamentoVista = new javax.swing.JPanel();
        labelPagoVenda = new javax.swing.JLabel();
        valorPagoVenda = new javax.swing.JTextField();
        valorTotalVenda = new javax.swing.JTextField();
        labelTotalVenda = new javax.swing.JLabel();
        labelTrocoVenda = new javax.swing.JLabel();
        valorTrocoVenda = new javax.swing.JTextField();
        calculaTrocoVenda = new javax.swing.JButton();
        panelVendaPagamentoParcelado = new javax.swing.JPanel();
        labelParcelasVenda = new javax.swing.JLabel();
        vendaParcela = new javax.swing.JComboBox<>();
        dataVencimentoParcela = new javax.swing.JFormattedTextField();
        labelDataVencimentoParcela = new javax.swing.JLabel();
        panelVendaCliente = new javax.swing.JPanel();
        checkVendaClienteCadastrado = new javax.swing.JCheckBox();
        btnBuscaVendaCliente = new javax.swing.JButton();
        vendaDocumentoCliente = new javax.swing.JFormattedTextField();
        vendaNomeCliente = new javax.swing.JTextField();
        panelVendaProduto = new javax.swing.JPanel();
        vendaIdProduto = new javax.swing.JTextField();
        btnVendaBuscaProduto = new javax.swing.JButton();
        btnVendaDeleteProduto = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tabelaVendaProduto = new javax.swing.JTable();
        labelValorTotalVenda = new javax.swing.JLabel();
        totalVendaProduto = new javax.swing.JTextField();
        labelVendaDesconto = new javax.swing.JLabel();
        descontoVenda = new javax.swing.JSpinner();
        labelPorcetagemVenda = new javax.swing.JLabel();
        labelQuantidadeVendaProduto = new javax.swing.JLabel();
        quantidadeVendaProduto = new javax.swing.JTextField();
        avisoVenda1 = new javax.swing.JLabel();
        avisoVenda2 = new javax.swing.JLabel();
        clientes = new javax.swing.JPanel();
        refreshCliente = new javax.swing.JButton();
        deleteCliente = new javax.swing.JButton();
        confirmCliente = new javax.swing.JButton();
        panelCliente = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelaCliente = new javax.swing.JTable();
        buscaCliente = new javax.swing.JTextField();
        btnBuscaCliente = new javax.swing.JButton();
        labelNomeBuscaCliente = new javax.swing.JLabel();
        panelClienteDadoPessoal = new javax.swing.JPanel();
        labelNomeCliente = new javax.swing.JLabel();
        nomeCliente = new javax.swing.JTextField();
        labelEmailCliente = new javax.swing.JLabel();
        emailCliente = new javax.swing.JTextField();
        labelDataNascimentoCliente = new javax.swing.JLabel();
        dataNascimentoCliente = new javax.swing.JTextField();
        try{ 
            javax.swing.text.MaskFormatter mask = new javax.swing.text.MaskFormatter("##/##/####");
            dataNascimentoCliente = new javax.swing.JFormattedTextField(mask);
        }
        catch (Exception e){
        }
        labelDocumentoCliente = new javax.swing.JLabel();
        documentoCliente = new javax.swing.JTextField();
        try{ 
            javax.swing.text.MaskFormatter mask = new javax.swing.text.MaskFormatter("###.###.###-##");
            documentoCliente = new javax.swing.JFormattedTextField(mask);
        }
        catch (Exception e){
        }
        labelSexoCliente = new javax.swing.JLabel();
        sexoCliente = new javax.swing.JComboBox<>();
        labelTelefoneCliente = new javax.swing.JLabel();
        dddCliente = new javax.swing.JTextField();
        try{ 
            javax.swing.text.MaskFormatter mask = new javax.swing.text.MaskFormatter("(##)");
            dddCliente = new javax.swing.JFormattedTextField(mask);
        }
        catch (Exception e){
        }
        telefoneCliente = new javax.swing.JTextField();
        labelTelefoneCliente2 = new javax.swing.JLabel();
        dddCliente2 = new javax.swing.JTextField();
        try{ 
            javax.swing.text.MaskFormatter mask = new javax.swing.text.MaskFormatter("(##)");
            dddCliente2 = new javax.swing.JFormattedTextField(mask);
        }
        catch (Exception e){
        }
        telefoneCliente2 = new javax.swing.JTextField();
        panelClienteEndereco = new javax.swing.JPanel();
        labelCepCliente = new javax.swing.JLabel();
        cepCliente = new javax.swing.JTextField();
        try{ 
            javax.swing.text.MaskFormatter mask = new javax.swing.text.MaskFormatter("#####-###");
            cepCliente = new javax.swing.JFormattedTextField(mask);
        }
        catch (Exception e){
        }
        labelLogradouroCliente = new javax.swing.JLabel();
        logradouroCliente = new javax.swing.JTextField();
        labelNumeroCliente = new javax.swing.JLabel();
        numeroCliente = new javax.swing.JTextField();
        labelBairroCliente = new javax.swing.JLabel();
        bairroCliente = new javax.swing.JTextField();
        labelCidadeCliente = new javax.swing.JLabel();
        cidadeCliente = new javax.swing.JTextField();
        labelEstadoCliente = new javax.swing.JLabel();
        estadoCliente = new javax.swing.JComboBox<>();
        produtos = new javax.swing.JPanel();
        deleteProduto = new javax.swing.JButton();
        confirmProduto = new javax.swing.JButton();
        refreshProduto = new javax.swing.JButton();
        panelProduto = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelaProduto = new javax.swing.JTable();
        descricaoProdutoBusca = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        btnBuscaProduto = new javax.swing.JButton();
        panelProdutoCadastro = new javax.swing.JPanel();
        imagemProduto = new javax.swing.JLabel();
        labelDescricaoProduto = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        descricaoProduto = new javax.swing.JTextArea();
        labelQuantidadeProduto = new javax.swing.JLabel();
        quantidadeProduto = new javax.swing.JSpinner();
        labelPrecoProduto = new javax.swing.JLabel();
        precoProduto = new javax.swing.JTextField();
        avisoProduto1 = new javax.swing.JLabel();
        avisoProduto2 = new javax.swing.JLabel();
        labelNomeArquivo = new javax.swing.JLabel();
        labelCaminhoArquivo = new javax.swing.JLabel();
        compras = new javax.swing.JPanel();
        labelCompraFornecedor = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        documentoFornecedorBusca = new javax.swing.JFormattedTextField();
        btnBuscaFornecedor = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        panelCompraProduto = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tabelaCompra = new javax.swing.JTable();
        btnInsereProduto = new javax.swing.JButton();
        labelPorcetagemLucro = new javax.swing.JLabel();
        porcentagemLucro = new javax.swing.JSpinner();
        labelPorcentagemLucro2 = new javax.swing.JLabel();
        avisoCompra1 = new javax.swing.JLabel();
        avisoCompra2 = new javax.swing.JLabel();
        panelConfirmaCompra = new javax.swing.JPanel();
        labelCompraQuantidadeTotal = new javax.swing.JLabel();
        compraQuantidadeTotal = new javax.swing.JTextField();
        labelCompraValorTotal = new javax.swing.JLabel();
        compraValorTotal = new javax.swing.JTextField();
        finalizaCompra = new javax.swing.JButton();
        relatorios = new javax.swing.JPanel();
        panelRelatorio = new javax.swing.JPanel();
        panelRelatorioLista = new javax.swing.JPanel();
        btnRelatorio4 = new javax.swing.JButton();
        btnRelatorio5 = new javax.swing.JButton();
        btnRelatorio6 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        panelRelatorioCrediario = new javax.swing.JPanel();
        btnRelatorio7 = new javax.swing.JButton();
        btnRelatorio8 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        panelRelatorioGerencial = new javax.swing.JPanel();
        btnRelatorio9 = new javax.swing.JButton();
        btnRelatorio10 = new javax.swing.JButton();
        btnRelatorio11 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        panelRelatorioVenda = new javax.swing.JPanel();
        btnRelatorio1 = new javax.swing.JButton();
        btnRelatorio2 = new javax.swing.JButton();
        btnRelatorio3 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        fornecedores = new javax.swing.JPanel();
        refreshFornecedor = new javax.swing.JButton();
        deleteFornecedor = new javax.swing.JButton();
        confirmFornecedor = new javax.swing.JButton();
        panelFornecedor = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelaFornecedor = new javax.swing.JTable();
        labelNomeFornecedorBusca = new javax.swing.JLabel();
        nomeBuscaFornecedor = new javax.swing.JTextField();
        btnBuscaNomeFornecedor = new javax.swing.JButton();
        panelDadosFornecedor = new javax.swing.JPanel();
        labelNomeFornecedor = new javax.swing.JLabel();
        labelDocumentoFornecedor = new javax.swing.JLabel();
        documentoFornecedor = new javax.swing.JTextField();
        try{ 
            javax.swing.text.MaskFormatter mask = new javax.swing.text.MaskFormatter("##.###.###/####-##");
            documentoFornecedor = new javax.swing.JFormattedTextField(mask);
        }
        catch (Exception e){
        }
        nomeFornecedor = new javax.swing.JTextField();
        labelEmailFornecedor = new javax.swing.JLabel();
        emailFornecedor = new javax.swing.JTextField();
        labelTelefoneFornecedor = new javax.swing.JLabel();
        labelTelefoneFornecedor2 = new javax.swing.JLabel();
        dddFornecedor2 = new javax.swing.JTextField();
        try{ 
            javax.swing.text.MaskFormatter mask = new javax.swing.text.MaskFormatter("(##)");
            dddFornecedor2 = new javax.swing.JFormattedTextField(mask);
        }
        catch (Exception e){
        }
        dddFornecedor = new javax.swing.JTextField();
        try{ 
            javax.swing.text.MaskFormatter mask = new javax.swing.text.MaskFormatter("(##)");
            dddFornecedor = new javax.swing.JFormattedTextField(mask);
        }
        catch (Exception e){
        }
        telefoneFornecedor = new javax.swing.JTextField();
        telefoneFornecedor2 = new javax.swing.JTextField();
        panelEnderecoFornecedor = new javax.swing.JPanel();
        labelCepFornecedor = new javax.swing.JLabel();
        cepFornecedor = new javax.swing.JTextField();
        try{ 
            javax.swing.text.MaskFormatter mask = new javax.swing.text.MaskFormatter("#####-###");
            cepFornecedor = new javax.swing.JFormattedTextField(mask);
        }
        catch (Exception e){
        }
        labelLogradouroFornecedor = new javax.swing.JLabel();
        logradouroFornecedor = new javax.swing.JTextField();
        labelNumeroFornecedor = new javax.swing.JLabel();
        numeroFornecedor = new javax.swing.JTextField();
        labelBairroFornecedor = new javax.swing.JLabel();
        bairroFornecedor = new javax.swing.JTextField();
        labelCidadeFornecedor = new javax.swing.JLabel();
        cidadeFornecedor = new javax.swing.JTextField();
        labelEstadoFornecedor = new javax.swing.JLabel();
        estadoFornecedor = new javax.swing.JComboBox<>();
        usuarios = new javax.swing.JPanel();
        deleteUsuario = new javax.swing.JButton();
        confirmUsuario = new javax.swing.JButton();
        refreshUsuario = new javax.swing.JButton();
        panelUsuarios = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaUsuario = new javax.swing.JTable();
        labelNomeUsuarioBusca = new javax.swing.JLabel();
        nomeBuscaUsuario = new javax.swing.JTextField();
        btnBuscaUsuario = new javax.swing.JButton();
        panelUsuarioDadoPessoal = new javax.swing.JPanel();
        labelNomeUsuario = new javax.swing.JLabel();
        nomeUsuario = new javax.swing.JTextField();
        emailUsuario = new javax.swing.JTextField();
        labelEmailUsuario = new javax.swing.JLabel();
        sexoUsuario = new javax.swing.JComboBox<>();
        labelSexoUsuario = new javax.swing.JLabel();
        documentoUsuario = new javax.swing.JTextField();
        try{ 
            javax.swing.text.MaskFormatter mask = new javax.swing.text.MaskFormatter("###.###.###-##");
            documentoUsuario = new javax.swing.JFormattedTextField(mask);
        }
        catch (Exception e){
        }
        dataNascimentoUsuario = new javax.swing.JTextField();
        try{ 
            javax.swing.text.MaskFormatter mask = new javax.swing.text.MaskFormatter("##/##/####");
            dataNascimentoUsuario = new javax.swing.JFormattedTextField(mask);
        }
        catch (Exception e){
        }
        labelDataNascimentoUsuario = new javax.swing.JLabel();
        labelTelefoneUsuario = new javax.swing.JLabel();
        labelTelefoneUsuario2 = new javax.swing.JLabel();
        dddUsuario2 = new javax.swing.JTextField();
        try{ 
            javax.swing.text.MaskFormatter mask = new javax.swing.text.MaskFormatter("(##)");
            dddUsuario2 = new javax.swing.JFormattedTextField(mask);
        }
        catch (Exception e){
        }
        dddUsuario = new javax.swing.JTextField();
        try{ 
            javax.swing.text.MaskFormatter mask = new javax.swing.text.MaskFormatter("(##)");
            dddUsuario = new javax.swing.JFormattedTextField(mask);
        }
        catch (Exception e){
        }
        telefoneUsuario = new javax.swing.JTextField();
        telefoneUsuario2 = new javax.swing.JTextField();
        labelDocumentoUsuario = new javax.swing.JLabel();
        panelUsuarioEndereco = new javax.swing.JPanel();
        labelCepUsuario = new javax.swing.JLabel();
        cepUsuario = new javax.swing.JTextField();
        try{ 
            javax.swing.text.MaskFormatter mask = new javax.swing.text.MaskFormatter("#####-###");
            cepUsuario = new javax.swing.JFormattedTextField(mask);
        }
        catch (Exception e){
        }
        labelLogradouroUsuario = new javax.swing.JLabel();
        logradouroUsuario = new javax.swing.JTextField();
        labelNumeroUsuario = new javax.swing.JLabel();
        numeroUsuario = new javax.swing.JTextField();
        estadoUsuario = new javax.swing.JComboBox<>();
        labelEstadoUsuario = new javax.swing.JLabel();
        cidadeUsuario = new javax.swing.JTextField();
        labelCidadeUsuario = new javax.swing.JLabel();
        bairroUsuario = new javax.swing.JTextField();
        labelBairroUsuario = new javax.swing.JLabel();
        panelUsuarioAcesso = new javax.swing.JPanel();
        labelLoginUsuario = new javax.swing.JLabel();
        loginUsuario = new javax.swing.JTextField();
        labelSenhaUsuario = new javax.swing.JLabel();
        checkSenha = new javax.swing.JCheckBox();
        senhaUsuario = new javax.swing.JPasswordField();
        labelAcessoUsuario = new javax.swing.JLabel();
        acessoUsuario = new javax.swing.JComboBox<>();
        panelUsuario = new javax.swing.JPanel();
        logado = new javax.swing.JLabel();
        ferramentas = new javax.swing.JToolBar();
        btnVenda = new javax.swing.JButton();
        btnCliente = new javax.swing.JButton();
        btnProduto = new javax.swing.JButton();
        btnCompra = new javax.swing.JButton();
        btnRelatorio = new javax.swing.JButton();
        btnFornecedor = new javax.swing.JButton();
        btnUsuario = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        panelDataHora = new javax.swing.JPanel();
        menuPrincipal = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/new_logo.png"))); // NOI18N

        eagles.setText("Eagle's Alliance © 2016");

        mainPanel.setLayout(new java.awt.CardLayout());

        fecharVenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/checked (1).png"))); // NOI18N
        fecharVenda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fecharVendaMouseClicked(evt);
            }
        });

        panelVendaMetodoPagamento.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Método de Pagamento", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        opcoesPagamento.add(radioVendaVista);
        radioVendaVista.setText("À Vista");
        radioVendaVista.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                radioVendaVistaMouseClicked(evt);
            }
        });

        opcoesPagamento.add(radioVendaParcelado);
        radioVendaParcelado.setText("Parcelado");
        radioVendaParcelado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                radioVendaParceladoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelVendaMetodoPagamentoLayout = new javax.swing.GroupLayout(panelVendaMetodoPagamento);
        panelVendaMetodoPagamento.setLayout(panelVendaMetodoPagamentoLayout);
        panelVendaMetodoPagamentoLayout.setHorizontalGroup(
            panelVendaMetodoPagamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVendaMetodoPagamentoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelVendaMetodoPagamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radioVendaVista)
                    .addComponent(radioVendaParcelado))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelVendaMetodoPagamentoLayout.setVerticalGroup(
            panelVendaMetodoPagamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVendaMetodoPagamentoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(radioVendaVista)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioVendaParcelado)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelVendaPagamentoVista.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Pagamento à Vista"));

        labelPagoVenda.setText("PAGO");

        valorPagoVenda.setEditable(false);

        valorTotalVenda.setEditable(false);

        labelTotalVenda.setText("TOTAL");

        labelTrocoVenda.setText("TROCO");

        valorTrocoVenda.setEditable(false);

        calculaTrocoVenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/equal.png"))); // NOI18N
        calculaTrocoVenda.setEnabled(false);
        calculaTrocoVenda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                calculaTrocoVendaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelVendaPagamentoVistaLayout = new javax.swing.GroupLayout(panelVendaPagamentoVista);
        panelVendaPagamentoVista.setLayout(panelVendaPagamentoVistaLayout);
        panelVendaPagamentoVistaLayout.setHorizontalGroup(
            panelVendaPagamentoVistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVendaPagamentoVistaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelVendaPagamentoVistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelPagoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(valorPagoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelVendaPagamentoVistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelTotalVenda)
                    .addComponent(valorTotalVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelVendaPagamentoVistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labelTrocoVenda)
                    .addComponent(valorTrocoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(calculaTrocoVenda)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelVendaPagamentoVistaLayout.setVerticalGroup(
            panelVendaPagamentoVistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVendaPagamentoVistaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelVendaPagamentoVistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(calculaTrocoVenda)
                    .addGroup(panelVendaPagamentoVistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelVendaPagamentoVistaLayout.createSequentialGroup()
                            .addComponent(labelPagoVenda)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(valorPagoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelVendaPagamentoVistaLayout.createSequentialGroup()
                            .addComponent(labelTotalVenda)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(valorTotalVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelVendaPagamentoVistaLayout.createSequentialGroup()
                            .addComponent(labelTrocoVenda)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(valorTrocoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        panelVendaPagamentoParcelado.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Pagamento Parcelado"));

        labelParcelasVenda.setText("PARCELAS");

        dataVencimentoParcela.setEditable(false);
        try {
            dataVencimentoParcela.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        labelDataVencimentoParcela.setText("VENCIMENTO");

        javax.swing.GroupLayout panelVendaPagamentoParceladoLayout = new javax.swing.GroupLayout(panelVendaPagamentoParcelado);
        panelVendaPagamentoParcelado.setLayout(panelVendaPagamentoParceladoLayout);
        panelVendaPagamentoParceladoLayout.setHorizontalGroup(
            panelVendaPagamentoParceladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVendaPagamentoParceladoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelVendaPagamentoParceladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(vendaParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelParcelasVenda))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelVendaPagamentoParceladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelDataVencimentoParcela)
                    .addComponent(dataVencimentoParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        panelVendaPagamentoParceladoLayout.setVerticalGroup(
            panelVendaPagamentoParceladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVendaPagamentoParceladoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelVendaPagamentoParceladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelParcelasVenda)
                    .addComponent(labelDataVencimentoParcela))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelVendaPagamentoParceladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(vendaParcela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dataVencimentoParcela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelVendaCliente.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        checkVendaClienteCadastrado.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        checkVendaClienteCadastrado.setText("Cliente não cadastrado");
        checkVendaClienteCadastrado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                checkVendaClienteCadastradoMouseClicked(evt);
            }
        });

        btnBuscaVendaCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/search.png"))); // NOI18N
        btnBuscaVendaCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBuscaVendaClienteMouseClicked(evt);
            }
        });

        try {
            vendaDocumentoCliente.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        vendaNomeCliente.setEditable(false);

        javax.swing.GroupLayout panelVendaClienteLayout = new javax.swing.GroupLayout(panelVendaCliente);
        panelVendaCliente.setLayout(panelVendaClienteLayout);
        panelVendaClienteLayout.setHorizontalGroup(
            panelVendaClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVendaClienteLayout.createSequentialGroup()
                .addGroup(panelVendaClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelVendaClienteLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(checkVendaClienteCadastrado))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelVendaClienteLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(panelVendaClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelVendaClienteLayout.createSequentialGroup()
                                .addComponent(vendaDocumentoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBuscaVendaCliente))
                            .addComponent(vendaNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelVendaClienteLayout.setVerticalGroup(
            panelVendaClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelVendaClienteLayout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addComponent(checkVendaClienteCadastrado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelVendaClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnBuscaVendaCliente)
                    .addComponent(vendaDocumentoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(vendaNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelVendaProduto.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Produtos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        btnVendaBuscaProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/search.png"))); // NOI18N
        btnVendaBuscaProduto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnVendaBuscaProdutoMouseClicked(evt);
            }
        });

        btnVendaDeleteProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete_icon.png"))); // NOI18N
        btnVendaDeleteProduto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnVendaDeleteProdutoMouseClicked(evt);
            }
        });

        tabelaVendaProduto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Produto", "Valor Unitario", "Quantidade", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaVendaProduto.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane6.setViewportView(tabelaVendaProduto);

        labelValorTotalVenda.setText("TOTAL");

        totalVendaProduto.setEditable(false);

        labelVendaDesconto.setText("DESCONTO");

        descontoVenda.setModel(new javax.swing.SpinnerNumberModel(0, 0, 100, 1));

        labelPorcetagemVenda.setText("%");

        labelQuantidadeVendaProduto.setText("QUANTIDADE");

        quantidadeVendaProduto.setEditable(false);

        avisoVenda1.setFont(new java.awt.Font("Dialog", 2, 11)); // NOI18N
        avisoVenda1.setText("* O desconto somente será aplicado a compras à vista.");

        avisoVenda2.setFont(new java.awt.Font("Dialog", 2, 11)); // NOI18N
        avisoVenda2.setText("* Atualize o desconto clicando novamente na opção À Vista, em Método de Pagamento.");

        javax.swing.GroupLayout panelVendaProdutoLayout = new javax.swing.GroupLayout(panelVendaProduto);
        panelVendaProduto.setLayout(panelVendaProdutoLayout);
        panelVendaProdutoLayout.setHorizontalGroup(
            panelVendaProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVendaProdutoLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(panelVendaProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelVendaProdutoLayout.createSequentialGroup()
                        .addComponent(vendaIdProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnVendaBuscaProduto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnVendaDeleteProduto))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(avisoVenda1)
                    .addComponent(avisoVenda2)
                    .addGroup(panelVendaProdutoLayout.createSequentialGroup()
                        .addGroup(panelVendaProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelVendaProdutoLayout.createSequentialGroup()
                                .addComponent(labelValorTotalVenda)
                                .addGap(28, 28, 28)
                                .addComponent(totalVendaProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelVendaProdutoLayout.createSequentialGroup()
                                .addComponent(labelVendaDesconto)
                                .addGap(6, 6, 6)
                                .addComponent(descontoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelPorcetagemVenda)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelQuantidadeVendaProduto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(quantidadeVendaProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelVendaProdutoLayout.setVerticalGroup(
            panelVendaProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVendaProdutoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelVendaProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnVendaDeleteProduto)
                    .addGroup(panelVendaProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(vendaIdProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnVendaBuscaProduto)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelVendaProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelValorTotalVenda)
                    .addComponent(totalVendaProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelQuantidadeVendaProduto)
                    .addComponent(quantidadeVendaProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelVendaProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelVendaProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelVendaDesconto)
                        .addComponent(descontoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(labelPorcetagemVenda))
                .addGap(18, 18, 18)
                .addComponent(avisoVenda1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(avisoVenda2)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout vendasLayout = new javax.swing.GroupLayout(vendas);
        vendas.setLayout(vendasLayout);
        vendasLayout.setHorizontalGroup(
            vendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vendasLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(vendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelVendaProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelVendaCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(vendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(vendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(vendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panelVendaPagamentoVista, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelVendaMetodoPagamento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(fecharVenda))
                    .addComponent(panelVendaPagamentoParcelado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        vendasLayout.setVerticalGroup(
            vendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vendasLayout.createSequentialGroup()
                .addComponent(panelVendaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(vendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelVendaProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(vendasLayout.createSequentialGroup()
                        .addComponent(panelVendaMetodoPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelVendaPagamentoVista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelVendaPagamentoParcelado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(fecharVenda)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainPanel.add(vendas, "vendas");

        refreshCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/refresh-page-option.png"))); // NOI18N
        refreshCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refreshClienteMouseClicked(evt);
            }
        });

        deleteCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete.png"))); // NOI18N
        deleteCliente.setEnabled(false);
        deleteCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteClienteMouseClicked(evt);
            }
        });

        confirmCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/checked (1).png"))); // NOI18N
        confirmCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                confirmClienteMouseClicked(evt);
            }
        });

        panelCliente.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Clientes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        tabelaCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaClienteMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelaCliente);

        btnBuscaCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/search.png"))); // NOI18N
        btnBuscaCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBuscaClienteMouseClicked(evt);
            }
        });

        labelNomeBuscaCliente.setText("Nome");

        javax.swing.GroupLayout panelClienteLayout = new javax.swing.GroupLayout(panelCliente);
        panelCliente.setLayout(panelClienteLayout);
        panelClienteLayout.setHorizontalGroup(
            panelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelClienteLayout.createSequentialGroup()
                        .addComponent(buscaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscaCliente))
                    .addComponent(labelNomeBuscaCliente)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelClienteLayout.setVerticalGroup(
            panelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelClienteLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(labelNomeBuscaCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buscaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscaCliente))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelClienteDadoPessoal.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Dados Pessoais", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        labelNomeCliente.setText("Nome");

        labelEmailCliente.setText("E-Mail");

        labelDataNascimentoCliente.setText("Data de Nascimento");

        labelDocumentoCliente.setText("CPF");

        labelSexoCliente.setText("Sexo");

        sexoCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Feminino" }));

        labelTelefoneCliente.setText("Telefone");

        labelTelefoneCliente2.setText("Telefone 2");

        javax.swing.GroupLayout panelClienteDadoPessoalLayout = new javax.swing.GroupLayout(panelClienteDadoPessoal);
        panelClienteDadoPessoal.setLayout(panelClienteDadoPessoalLayout);
        panelClienteDadoPessoalLayout.setHorizontalGroup(
            panelClienteDadoPessoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClienteDadoPessoalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelClienteDadoPessoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelClienteDadoPessoalLayout.createSequentialGroup()
                        .addGroup(panelClienteDadoPessoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelClienteDadoPessoalLayout.createSequentialGroup()
                                .addComponent(labelNomeCliente)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelClienteDadoPessoalLayout.createSequentialGroup()
                                .addComponent(labelEmailCliente)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(emailCliente)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addGroup(panelClienteDadoPessoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelDataNascimentoCliente, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelClienteDadoPessoalLayout.createSequentialGroup()
                                .addComponent(labelDocumentoCliente)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(documentoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelSexoCliente)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelClienteDadoPessoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(sexoCliente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dataNascimentoCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE))
                        .addContainerGap(17, Short.MAX_VALUE))
                    .addGroup(panelClienteDadoPessoalLayout.createSequentialGroup()
                        .addGroup(panelClienteDadoPessoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelClienteDadoPessoalLayout.createSequentialGroup()
                                .addComponent(labelTelefoneCliente2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dddCliente2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(telefoneCliente2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelClienteDadoPessoalLayout.createSequentialGroup()
                                .addComponent(labelTelefoneCliente)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(dddCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(telefoneCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(316, Short.MAX_VALUE))))
        );
        panelClienteDadoPessoalLayout.setVerticalGroup(
            panelClienteDadoPessoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClienteDadoPessoalLayout.createSequentialGroup()
                .addGroup(panelClienteDadoPessoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelNomeCliente)
                    .addComponent(labelDocumentoCliente)
                    .addComponent(documentoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelSexoCliente)
                    .addComponent(sexoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelClienteDadoPessoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelClienteDadoPessoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelEmailCliente)
                        .addComponent(emailCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelClienteDadoPessoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelDataNascimentoCliente)
                        .addComponent(dataNascimentoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(23, 23, 23)
                .addGroup(panelClienteDadoPessoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTelefoneCliente)
                    .addComponent(dddCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(telefoneCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(panelClienteDadoPessoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dddCliente2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(telefoneCliente2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelTelefoneCliente2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelClienteEndereco.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Endereço", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        labelCepCliente.setText("CEP");

        labelLogradouroCliente.setText("Logradouro");

        labelNumeroCliente.setText("N°");

        labelBairroCliente.setText("Bairro");

        labelCidadeCliente.setText("Cidade");

        labelEstadoCliente.setText("Estado");

        javax.swing.GroupLayout panelClienteEnderecoLayout = new javax.swing.GroupLayout(panelClienteEndereco);
        panelClienteEndereco.setLayout(panelClienteEnderecoLayout);
        panelClienteEnderecoLayout.setHorizontalGroup(
            panelClienteEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClienteEnderecoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelClienteEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelLogradouroCliente)
                    .addComponent(labelBairroCliente)
                    .addComponent(labelCepCliente))
                .addGap(18, 18, 18)
                .addGroup(panelClienteEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cepCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelClienteEnderecoLayout.createSequentialGroup()
                        .addGroup(panelClienteEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelClienteEnderecoLayout.createSequentialGroup()
                                .addComponent(bairroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelCidadeCliente)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cidadeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(logradouroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelClienteEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelNumeroCliente)
                            .addComponent(labelEstadoCliente))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(panelClienteEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(numeroCliente)
                    .addComponent(estadoCliente, 0, 61, Short.MAX_VALUE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        panelClienteEnderecoLayout.setVerticalGroup(
            panelClienteEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClienteEnderecoLayout.createSequentialGroup()
                .addGroup(panelClienteEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCepCliente)
                    .addComponent(cepCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelClienteEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelLogradouroCliente)
                    .addComponent(logradouroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelNumeroCliente)
                    .addComponent(numeroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelClienteEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelBairroCliente)
                    .addComponent(bairroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelCidadeCliente)
                    .addComponent(cidadeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelEstadoCliente)
                    .addComponent(estadoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout clientesLayout = new javax.swing.GroupLayout(clientes);
        clientes.setLayout(clientesLayout);
        clientesLayout.setHorizontalGroup(
            clientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(clientesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(clientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelClienteEndereco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelClienteDadoPessoal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(clientesLayout.createSequentialGroup()
                        .addComponent(refreshCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(confirmCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteCliente)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        clientesLayout.setVerticalGroup(
            clientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(clientesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(clientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(clientesLayout.createSequentialGroup()
                        .addComponent(panelClienteDadoPessoal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelClienteEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(clientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(refreshCliente, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(confirmCliente, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(deleteCliente, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainPanel.add(clientes, "clientes");

        deleteProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete.png"))); // NOI18N
        deleteProduto.setEnabled(false);
        deleteProduto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteProdutoMouseClicked(evt);
            }
        });

        confirmProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/checked (1).png"))); // NOI18N
        confirmProduto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                confirmProdutoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                confirmProdutoMouseEntered(evt);
            }
        });

        refreshProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/refresh-page-option.png"))); // NOI18N
        refreshProduto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refreshProdutoMouseClicked(evt);
            }
        });

        panelProduto.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Produtos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        tabelaProduto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Produto", "R$", "QTD", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaProduto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaProdutoMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tabelaProduto);

        jLabel8.setText("Descrição do Produto");

        btnBuscaProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/search.png"))); // NOI18N
        btnBuscaProduto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBuscaProdutoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelProdutoLayout = new javax.swing.GroupLayout(panelProduto);
        panelProduto.setLayout(panelProdutoLayout);
        panelProdutoLayout.setHorizontalGroup(
            panelProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProdutoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelProdutoLayout.createSequentialGroup()
                        .addComponent(descricaoProdutoBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscaProduto))
                    .addComponent(jLabel8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelProdutoLayout.setVerticalGroup(
            panelProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelProdutoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(descricaoProdutoBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscaProduto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(129, 129, 129))
        );

        panelProdutoCadastro.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cadastro de Produto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        imagemProduto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imagemProduto.setText("IMAGEM");
        imagemProduto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        imagemProduto.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        imagemProduto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        imagemProduto.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        imagemProduto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                imagemProdutoMouseClicked(evt);
            }
        });

        labelDescricaoProduto.setText("Descrição");

        descricaoProduto.setColumns(20);
        descricaoProduto.setRows(5);
        jScrollPane5.setViewportView(descricaoProduto);

        labelQuantidadeProduto.setText("Quantidade");

        quantidadeProduto.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        labelPrecoProduto.setText("Preço");

        avisoProduto1.setFont(new java.awt.Font("Dialog", 2, 11)); // NOI18N
        avisoProduto1.setText("* Apenas imagens no formato png ou jpg são permitidas.");

        avisoProduto2.setFont(new java.awt.Font("Dialog", 2, 11)); // NOI18N
        avisoProduto2.setText("* Tamanho recomendado 200x200 px.");

        javax.swing.GroupLayout panelProdutoCadastroLayout = new javax.swing.GroupLayout(panelProdutoCadastro);
        panelProdutoCadastro.setLayout(panelProdutoCadastroLayout);
        panelProdutoCadastroLayout.setHorizontalGroup(
            panelProdutoCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProdutoCadastroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelProdutoCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelProdutoCadastroLayout.createSequentialGroup()
                        .addComponent(imagemProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelProdutoCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(avisoProduto1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(panelProdutoCadastroLayout.createSequentialGroup()
                                .addGroup(panelProdutoCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(avisoProduto2)
                                    .addComponent(labelNomeArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(labelCaminhoArquivo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(labelDescricaoProduto)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 476, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelProdutoCadastroLayout.createSequentialGroup()
                        .addComponent(labelQuantidadeProduto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(quantidadeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelPrecoProduto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(precoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelProdutoCadastroLayout.setVerticalGroup(
            panelProdutoCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProdutoCadastroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelProdutoCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelProdutoCadastroLayout.createSequentialGroup()
                        .addComponent(avisoProduto1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(avisoProduto2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelNomeArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelCaminhoArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(imagemProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelDescricaoProduto)
                .addGap(8, 8, 8)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelProdutoCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelQuantidadeProduto)
                    .addComponent(quantidadeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelPrecoProduto)
                    .addComponent(precoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout produtosLayout = new javax.swing.GroupLayout(produtos);
        produtos.setLayout(produtosLayout);
        produtosLayout.setHorizontalGroup(
            produtosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(produtosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(produtosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelProdutoCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(produtosLayout.createSequentialGroup()
                        .addComponent(refreshProduto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(confirmProduto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteProduto)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        produtosLayout.setVerticalGroup(
            produtosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(produtosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(produtosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelProduto, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(produtosLayout.createSequentialGroup()
                        .addComponent(panelProdutoCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(produtosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(refreshProduto)
                            .addComponent(deleteProduto)
                            .addComponent(confirmProduto))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainPanel.add(produtos, "produtos");

        labelCompraFornecedor.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Fornecedor", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        jLabel1.setText("CNPJ");

        btnBuscaFornecedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/search.png"))); // NOI18N

        jTextField2.setEditable(false);

        javax.swing.GroupLayout labelCompraFornecedorLayout = new javax.swing.GroupLayout(labelCompraFornecedor);
        labelCompraFornecedor.setLayout(labelCompraFornecedorLayout);
        labelCompraFornecedorLayout.setHorizontalGroup(
            labelCompraFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(labelCompraFornecedorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(labelCompraFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(labelCompraFornecedorLayout.createSequentialGroup()
                        .addGroup(labelCompraFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(labelCompraFornecedorLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(documentoFornecedorBusca))
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscaFornecedor)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        labelCompraFornecedorLayout.setVerticalGroup(
            labelCompraFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(labelCompraFornecedorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(labelCompraFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnBuscaFornecedor)
                    .addGroup(labelCompraFornecedorLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(documentoFornecedorBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelCompraProduto.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Produtos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        tabelaCompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "Descrição", "Quantidade", "Valor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane7.setViewportView(tabelaCompra);

        btnInsereProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add.png"))); // NOI18N

        labelPorcetagemLucro.setText("Percentagem de Lucro");

        porcentagemLucro.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        labelPorcentagemLucro2.setText("%");

        avisoCompra1.setFont(new java.awt.Font("Dialog", 2, 11)); // NOI18N
        avisoCompra1.setText("<html>* Atenção à descrição do produto, produtos com mesma descrição terão sua quantidade<br/>aumentada. Não será inserido novo produto.</html>");

        avisoCompra2.setFont(new java.awt.Font("Dialog", 2, 11)); // NOI18N
        avisoCompra2.setText("<html>* Produtos cadastrados durante a compra  terão imagem padrão.<br/>Necessário cadastrar imagem pelo menu Produtos.</html>");

        javax.swing.GroupLayout panelCompraProdutoLayout = new javax.swing.GroupLayout(panelCompraProduto);
        panelCompraProduto.setLayout(panelCompraProdutoLayout);
        panelCompraProdutoLayout.setHorizontalGroup(
            panelCompraProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCompraProdutoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCompraProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCompraProdutoLayout.createSequentialGroup()
                        .addComponent(labelPorcetagemLucro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(porcentagemLucro, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelPorcentagemLucro2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnInsereProduto))
                    .addGroup(panelCompraProdutoLayout.createSequentialGroup()
                        .addGroup(panelCompraProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(avisoCompra1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(avisoCompra2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelCompraProdutoLayout.setVerticalGroup(
            panelCompraProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCompraProdutoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCompraProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnInsereProduto)
                    .addGroup(panelCompraProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelPorcetagemLucro)
                        .addComponent(porcentagemLucro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelPorcentagemLucro2)))
                .addGap(18, 18, 18)
                .addComponent(avisoCompra1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(avisoCompra2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelConfirmaCompra.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Confirmação da Compra", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        labelCompraQuantidadeTotal.setText("Quantidade Total de Produtos");

        compraQuantidadeTotal.setEditable(false);

        labelCompraValorTotal.setText("Valor Total");

        compraValorTotal.setEditable(false);

        finalizaCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/checked (1).png"))); // NOI18N

        javax.swing.GroupLayout panelConfirmaCompraLayout = new javax.swing.GroupLayout(panelConfirmaCompra);
        panelConfirmaCompra.setLayout(panelConfirmaCompraLayout);
        panelConfirmaCompraLayout.setHorizontalGroup(
            panelConfirmaCompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfirmaCompraLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfirmaCompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelCompraQuantidadeTotal)
                    .addComponent(compraQuantidadeTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelCompraValorTotal)
                    .addComponent(compraValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(248, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelConfirmaCompraLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(finalizaCompra)
                .addContainerGap())
        );
        panelConfirmaCompraLayout.setVerticalGroup(
            panelConfirmaCompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfirmaCompraLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelCompraQuantidadeTotal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(compraQuantidadeTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelCompraValorTotal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(compraValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(finalizaCompra)
                .addContainerGap())
        );

        javax.swing.GroupLayout comprasLayout = new javax.swing.GroupLayout(compras);
        compras.setLayout(comprasLayout);
        comprasLayout.setHorizontalGroup(
            comprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(comprasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(comprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelCompraProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelCompraFornecedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelConfirmaCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        comprasLayout.setVerticalGroup(
            comprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(comprasLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(comprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(comprasLayout.createSequentialGroup()
                        .addComponent(labelCompraFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelCompraProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelConfirmaCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainPanel.add(compras, "compras");

        panelRelatorio.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Relatorios", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        panelRelatorioLista.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Listas"));

        btnRelatorio4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/usuario.png"))); // NOI18N

        btnRelatorio5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fornecedor.png"))); // NOI18N

        btnRelatorio6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/produto.png"))); // NOI18N

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/funcionario.png"))); // NOI18N

        jLabel2.setText("Lista de Clientes");

        jLabel3.setText("Lista de Fornecedores");

        jLabel4.setText("Lista de Produtos");

        jLabel5.setText("Lista de Funcionários");

        javax.swing.GroupLayout panelRelatorioListaLayout = new javax.swing.GroupLayout(panelRelatorioLista);
        panelRelatorioLista.setLayout(panelRelatorioListaLayout);
        panelRelatorioListaLayout.setHorizontalGroup(
            panelRelatorioListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRelatorioListaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRelatorioListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRelatorioListaLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnRelatorio4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addGap(144, 144, 144))
                    .addGroup(panelRelatorioListaLayout.createSequentialGroup()
                        .addGroup(panelRelatorioListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelRelatorioListaLayout.createSequentialGroup()
                                .addComponent(btnRelatorio5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3))
                            .addGroup(panelRelatorioListaLayout.createSequentialGroup()
                                .addComponent(btnRelatorio6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4))
                            .addGroup(panelRelatorioListaLayout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        panelRelatorioListaLayout.setVerticalGroup(
            panelRelatorioListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRelatorioListaLayout.createSequentialGroup()
                .addGroup(panelRelatorioListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelRelatorioListaLayout.createSequentialGroup()
                        .addGroup(panelRelatorioListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelRelatorioListaLayout.createSequentialGroup()
                                .addGroup(panelRelatorioListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnRelatorio4)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnRelatorio5))
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRelatorio6))
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelRelatorioListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1)
                    .addComponent(jLabel5))
                .addGap(0, 35, Short.MAX_VALUE))
        );

        panelRelatorioCrediario.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Crediário"));

        btnRelatorio7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/credito.png"))); // NOI18N
        btnRelatorio7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnRelatorio7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        btnRelatorio8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/divida.png"))); // NOI18N

        jLabel10.setText("Crediários em Aberto");

        jLabel11.setText("Inadimplência");

        javax.swing.GroupLayout panelRelatorioCrediarioLayout = new javax.swing.GroupLayout(panelRelatorioCrediario);
        panelRelatorioCrediario.setLayout(panelRelatorioCrediarioLayout);
        panelRelatorioCrediarioLayout.setHorizontalGroup(
            panelRelatorioCrediarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRelatorioCrediarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRelatorioCrediarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRelatorioCrediarioLayout.createSequentialGroup()
                        .addComponent(btnRelatorio7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10))
                    .addGroup(panelRelatorioCrediarioLayout.createSequentialGroup()
                        .addComponent(btnRelatorio8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelRelatorioCrediarioLayout.setVerticalGroup(
            panelRelatorioCrediarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRelatorioCrediarioLayout.createSequentialGroup()
                .addGroup(panelRelatorioCrediarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnRelatorio7)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelRelatorioCrediarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnRelatorio8)
                    .addComponent(jLabel11))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panelRelatorioGerencial.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Gerencial"));

        btnRelatorio9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/stock.png"))); // NOI18N
        btnRelatorio9.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnRelatorio9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        btnRelatorio10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/grafico.png"))); // NOI18N
        btnRelatorio10.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnRelatorio10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        btnRelatorio11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ranking.png"))); // NOI18N
        btnRelatorio11.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnRelatorio11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel6.setText("Relatório Geral de Estoque");

        jLabel7.setText("Valor Médio das Vendas");

        jLabel9.setText("Ranking de Vendas");

        javax.swing.GroupLayout panelRelatorioGerencialLayout = new javax.swing.GroupLayout(panelRelatorioGerencial);
        panelRelatorioGerencial.setLayout(panelRelatorioGerencialLayout);
        panelRelatorioGerencialLayout.setHorizontalGroup(
            panelRelatorioGerencialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRelatorioGerencialLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRelatorioGerencialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRelatorioGerencialLayout.createSequentialGroup()
                        .addComponent(btnRelatorio9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6))
                    .addGroup(panelRelatorioGerencialLayout.createSequentialGroup()
                        .addComponent(btnRelatorio10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7))
                    .addGroup(panelRelatorioGerencialLayout.createSequentialGroup()
                        .addComponent(btnRelatorio11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelRelatorioGerencialLayout.setVerticalGroup(
            panelRelatorioGerencialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRelatorioGerencialLayout.createSequentialGroup()
                .addGroup(panelRelatorioGerencialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelRelatorioGerencialLayout.createSequentialGroup()
                        .addGroup(panelRelatorioGerencialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnRelatorio9)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRelatorio10))
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelRelatorioGerencialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnRelatorio11)
                    .addComponent(jLabel9))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelRelatorioVenda.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Vendas"));

        btnRelatorio1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/venda_funcionario.png"))); // NOI18N
        btnRelatorio1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnRelatorio1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        btnRelatorio2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/venda_cliente.png"))); // NOI18N
        btnRelatorio2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnRelatorio2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        btnRelatorio3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/venda_periodo.png"))); // NOI18N
        btnRelatorio3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnRelatorio3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel12.setText("Vendas por Funcionário");

        jLabel13.setText("Vendas por Cliente");

        jLabel14.setText("Vendas por Período");

        javax.swing.GroupLayout panelRelatorioVendaLayout = new javax.swing.GroupLayout(panelRelatorioVenda);
        panelRelatorioVenda.setLayout(panelRelatorioVendaLayout);
        panelRelatorioVendaLayout.setHorizontalGroup(
            panelRelatorioVendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRelatorioVendaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRelatorioVendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRelatorioVendaLayout.createSequentialGroup()
                        .addGroup(panelRelatorioVendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnRelatorio1)
                            .addComponent(btnRelatorio2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelRelatorioVendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel12)))
                    .addGroup(panelRelatorioVendaLayout.createSequentialGroup()
                        .addComponent(btnRelatorio3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelRelatorioVendaLayout.setVerticalGroup(
            panelRelatorioVendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRelatorioVendaLayout.createSequentialGroup()
                .addGroup(panelRelatorioVendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelRelatorioVendaLayout.createSequentialGroup()
                        .addGroup(panelRelatorioVendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnRelatorio1)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRelatorio2))
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelRelatorioVendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnRelatorio3)
                    .addComponent(jLabel14))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelRelatorioLayout = new javax.swing.GroupLayout(panelRelatorio);
        panelRelatorio.setLayout(panelRelatorioLayout);
        panelRelatorioLayout.setHorizontalGroup(
            panelRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRelatorioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelRelatorioCrediario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelRelatorioLista, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelRelatorioVenda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelRelatorioGerencial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelRelatorioLayout.setVerticalGroup(
            panelRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRelatorioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelRelatorioLista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelRelatorioVenda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelRelatorioGerencial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelRelatorioCrediario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout relatoriosLayout = new javax.swing.GroupLayout(relatorios);
        relatorios.setLayout(relatoriosLayout);
        relatoriosLayout.setHorizontalGroup(
            relatoriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(relatoriosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        relatoriosLayout.setVerticalGroup(
            relatoriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(relatoriosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainPanel.add(relatorios, "relatorios");

        refreshFornecedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/refresh-page-option.png"))); // NOI18N
        refreshFornecedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refreshFornecedorMouseClicked(evt);
            }
        });

        deleteFornecedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete.png"))); // NOI18N
        deleteFornecedor.setEnabled(false);
        deleteFornecedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteFornecedorMouseClicked(evt);
            }
        });

        confirmFornecedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/checked (1).png"))); // NOI18N
        confirmFornecedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                confirmFornecedorMouseClicked(evt);
            }
        });

        panelFornecedor.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Fornecedores", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        tabelaFornecedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaFornecedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaFornecedorMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tabelaFornecedor);

        labelNomeFornecedorBusca.setText("Nome");

        btnBuscaNomeFornecedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/search.png"))); // NOI18N
        btnBuscaNomeFornecedor.setToolTipText("BUscar Fornecedor por Nome");
        btnBuscaNomeFornecedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBuscaNomeFornecedorMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelFornecedorLayout = new javax.swing.GroupLayout(panelFornecedor);
        panelFornecedor.setLayout(panelFornecedorLayout);
        panelFornecedorLayout.setHorizontalGroup(
            panelFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFornecedorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelFornecedorLayout.createSequentialGroup()
                        .addGroup(panelFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelNomeFornecedorBusca)
                            .addComponent(nomeBuscaFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscaNomeFornecedor)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelFornecedorLayout.setVerticalGroup(
            panelFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFornecedorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelFornecedorLayout.createSequentialGroup()
                        .addComponent(labelNomeFornecedorBusca)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nomeBuscaFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnBuscaNomeFornecedor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelDadosFornecedor.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Dados do Fornecedor", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        labelNomeFornecedor.setText("Nome Fantasia");

        labelDocumentoFornecedor.setText("CNPJ");

        labelEmailFornecedor.setText("E-mail");

        labelTelefoneFornecedor.setText("Telefone");

        labelTelefoneFornecedor2.setText("Telefone 2");

        javax.swing.GroupLayout panelDadosFornecedorLayout = new javax.swing.GroupLayout(panelDadosFornecedor);
        panelDadosFornecedor.setLayout(panelDadosFornecedorLayout);
        panelDadosFornecedorLayout.setHorizontalGroup(
            panelDadosFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDadosFornecedorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDadosFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDadosFornecedorLayout.createSequentialGroup()
                        .addGroup(panelDadosFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelNomeFornecedor)
                            .addComponent(labelDocumentoFornecedor))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDadosFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(documentoFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nomeFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelEmailFornecedor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(emailFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelDadosFornecedorLayout.createSequentialGroup()
                        .addGroup(panelDadosFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(labelTelefoneFornecedor2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelTelefoneFornecedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(32, 32, 32)
                        .addGroup(panelDadosFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dddFornecedor2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dddFornecedor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDadosFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(telefoneFornecedor2)
                            .addComponent(telefoneFornecedor))
                        .addGap(323, 323, 323)))
                .addContainerGap())
        );
        panelDadosFornecedorLayout.setVerticalGroup(
            panelDadosFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDadosFornecedorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDadosFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNomeFornecedor)
                    .addComponent(nomeFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelEmailFornecedor)
                    .addComponent(emailFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDadosFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelDocumentoFornecedor)
                    .addComponent(documentoFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelDadosFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTelefoneFornecedor)
                    .addComponent(dddFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(telefoneFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDadosFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTelefoneFornecedor2)
                    .addComponent(dddFornecedor2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(telefoneFornecedor2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        panelEnderecoFornecedor.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Endereço", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        labelCepFornecedor.setText("CEP");

        labelLogradouroFornecedor.setText("Logradouro");

        labelNumeroFornecedor.setText("Nº");

        labelBairroFornecedor.setText("Bairro");

        labelCidadeFornecedor.setText("Cidade");

        labelEstadoFornecedor.setText("Estado");

        javax.swing.GroupLayout panelEnderecoFornecedorLayout = new javax.swing.GroupLayout(panelEnderecoFornecedor);
        panelEnderecoFornecedor.setLayout(panelEnderecoFornecedorLayout);
        panelEnderecoFornecedorLayout.setHorizontalGroup(
            panelEnderecoFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEnderecoFornecedorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelEnderecoFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelLogradouroFornecedor)
                    .addComponent(labelCepFornecedor)
                    .addComponent(labelBairroFornecedor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEnderecoFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelEnderecoFornecedorLayout.createSequentialGroup()
                        .addComponent(bairroFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelCidadeFornecedor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cidadeFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cepFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(logradouroFornecedor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelEnderecoFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelEstadoFornecedor)
                    .addComponent(labelNumeroFornecedor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEnderecoFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(numeroFornecedor)
                    .addComponent(estadoFornecedor, 0, 69, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelEnderecoFornecedorLayout.setVerticalGroup(
            panelEnderecoFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEnderecoFornecedorLayout.createSequentialGroup()
                .addGroup(panelEnderecoFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCepFornecedor)
                    .addComponent(cepFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEnderecoFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelLogradouroFornecedor)
                    .addComponent(logradouroFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelNumeroFornecedor)
                    .addComponent(numeroFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEnderecoFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelBairroFornecedor)
                    .addComponent(bairroFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelCidadeFornecedor)
                    .addComponent(cidadeFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelEstadoFornecedor)
                    .addComponent(estadoFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout fornecedoresLayout = new javax.swing.GroupLayout(fornecedores);
        fornecedores.setLayout(fornecedoresLayout);
        fornecedoresLayout.setHorizontalGroup(
            fornecedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fornecedoresLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(fornecedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(fornecedoresLayout.createSequentialGroup()
                        .addComponent(refreshFornecedor)
                        .addGap(423, 423, 423)
                        .addComponent(confirmFornecedor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteFornecedor))
                    .addGroup(fornecedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(panelDadosFornecedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelEnderecoFornecedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        fornecedoresLayout.setVerticalGroup(
            fornecedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fornecedoresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(fornecedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(fornecedoresLayout.createSequentialGroup()
                        .addComponent(panelDadosFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(panelEnderecoFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(fornecedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(refreshFornecedor, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(confirmFornecedor, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(deleteFornecedor, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainPanel.add(fornecedores, "fornecedores");

        deleteUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete.png"))); // NOI18N
        deleteUsuario.setEnabled(false);
        deleteUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteUsuarioMouseClicked(evt);
            }
        });

        confirmUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/checked (1).png"))); // NOI18N
        confirmUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                confirmUsuarioMouseClicked(evt);
            }
        });

        refreshUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/refresh-page-option.png"))); // NOI18N
        refreshUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refreshUsuarioMouseClicked(evt);
            }
        });

        panelUsuarios.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Usuários", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        tabelaUsuario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome", "Usuário", "Acesso"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaUsuarioMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaUsuario);

        labelNomeUsuarioBusca.setText("Nome");

        btnBuscaUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/search.png"))); // NOI18N
        btnBuscaUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBuscaUsuarioMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelUsuariosLayout = new javax.swing.GroupLayout(panelUsuarios);
        panelUsuarios.setLayout(panelUsuariosLayout);
        panelUsuariosLayout.setHorizontalGroup(
            panelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUsuariosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelNomeUsuarioBusca)
                    .addComponent(nomeBuscaUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscaUsuario)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelUsuariosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelUsuariosLayout.setVerticalGroup(
            panelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelUsuariosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelUsuariosLayout.createSequentialGroup()
                        .addComponent(labelNomeUsuarioBusca)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nomeBuscaUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnBuscaUsuario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelUsuarioDadoPessoal.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Dados Pessoais", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        labelNomeUsuario.setText("Nome");

        labelEmailUsuario.setText("E-Mail");

        sexoUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Feminino" }));

        labelSexoUsuario.setText("Sexo");

        labelDataNascimentoUsuario.setText("Data de Nascimento");

        labelTelefoneUsuario.setText("Telefone");

        labelTelefoneUsuario2.setText("Telefone 2");

        labelDocumentoUsuario.setText("CPF");

        javax.swing.GroupLayout panelUsuarioDadoPessoalLayout = new javax.swing.GroupLayout(panelUsuarioDadoPessoal);
        panelUsuarioDadoPessoal.setLayout(panelUsuarioDadoPessoalLayout);
        panelUsuarioDadoPessoalLayout.setHorizontalGroup(
            panelUsuarioDadoPessoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUsuarioDadoPessoalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelUsuarioDadoPessoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelUsuarioDadoPessoalLayout.createSequentialGroup()
                        .addGroup(panelUsuarioDadoPessoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(labelTelefoneUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelTelefoneUsuario2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelUsuarioDadoPessoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelUsuarioDadoPessoalLayout.createSequentialGroup()
                                .addComponent(dddUsuario2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(telefoneUsuario2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelUsuarioDadoPessoalLayout.createSequentialGroup()
                                .addComponent(dddUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(telefoneUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panelUsuarioDadoPessoalLayout.createSequentialGroup()
                        .addGroup(panelUsuarioDadoPessoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelUsuarioDadoPessoalLayout.createSequentialGroup()
                                .addComponent(labelNomeUsuario)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nomeUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelUsuarioDadoPessoalLayout.createSequentialGroup()
                                .addComponent(labelEmailUsuario)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(emailUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelUsuarioDadoPessoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelUsuarioDadoPessoalLayout.createSequentialGroup()
                                .addComponent(labelDataNascimentoUsuario)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(panelUsuarioDadoPessoalLayout.createSequentialGroup()
                                .addComponent(labelDocumentoUsuario)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(documentoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelSexoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22)))
                        .addGroup(panelUsuarioDadoPessoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(sexoUsuario, 0, 82, Short.MAX_VALUE)
                            .addComponent(dataNascimentoUsuario))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelUsuarioDadoPessoalLayout.setVerticalGroup(
            panelUsuarioDadoPessoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUsuarioDadoPessoalLayout.createSequentialGroup()
                .addGroup(panelUsuarioDadoPessoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNomeUsuario)
                    .addComponent(nomeUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(documentoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelDocumentoUsuario)
                    .addComponent(labelSexoUsuario)
                    .addComponent(sexoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelUsuarioDadoPessoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelEmailUsuario)
                    .addComponent(emailUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelDataNascimentoUsuario)
                    .addComponent(dataNascimentoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(panelUsuarioDadoPessoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTelefoneUsuario)
                    .addComponent(dddUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(telefoneUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(panelUsuarioDadoPessoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTelefoneUsuario2)
                    .addComponent(dddUsuario2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(telefoneUsuario2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelUsuarioEndereco.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Endereço", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        labelCepUsuario.setText("CEP");

        labelLogradouroUsuario.setText("Logradouro");

        labelNumeroUsuario.setText("Nº");

        labelEstadoUsuario.setText("Estado");

        labelCidadeUsuario.setText("Cidade");

        labelBairroUsuario.setText("Bairro");

        javax.swing.GroupLayout panelUsuarioEnderecoLayout = new javax.swing.GroupLayout(panelUsuarioEndereco);
        panelUsuarioEndereco.setLayout(panelUsuarioEnderecoLayout);
        panelUsuarioEnderecoLayout.setHorizontalGroup(
            panelUsuarioEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUsuarioEnderecoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelUsuarioEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelUsuarioEnderecoLayout.createSequentialGroup()
                        .addComponent(labelCepUsuario)
                        .addGap(49, 49, 49)
                        .addComponent(cepUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelUsuarioEnderecoLayout.createSequentialGroup()
                        .addGroup(panelUsuarioEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelUsuarioEnderecoLayout.createSequentialGroup()
                                .addComponent(labelBairroUsuario)
                                .addGap(39, 39, 39)
                                .addComponent(bairroUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelCidadeUsuario)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cidadeUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelUsuarioEnderecoLayout.createSequentialGroup()
                                .addComponent(labelLogradouroUsuario)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(logradouroUsuario)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelUsuarioEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelEstadoUsuario)
                            .addComponent(labelNumeroUsuario))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelUsuarioEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(estadoUsuario, 0, 65, Short.MAX_VALUE)
                            .addComponent(numeroUsuario))))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        panelUsuarioEnderecoLayout.setVerticalGroup(
            panelUsuarioEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUsuarioEnderecoLayout.createSequentialGroup()
                .addGroup(panelUsuarioEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCepUsuario)
                    .addComponent(cepUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelUsuarioEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelLogradouroUsuario)
                    .addComponent(logradouroUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelNumeroUsuario)
                    .addComponent(numeroUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelUsuarioEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelUsuarioEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelEstadoUsuario)
                        .addComponent(estadoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelUsuarioEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelCidadeUsuario)
                        .addComponent(cidadeUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelUsuarioEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelBairroUsuario)
                        .addComponent(bairroUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelUsuarioAcesso.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Dados de Acesso", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        labelLoginUsuario.setText("Usuário");

        labelSenhaUsuario.setText("Senha");

        checkSenha.setText("Cadastrar Nova Senha");
        checkSenha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                checkSenhaMouseClicked(evt);
            }
        });

        senhaUsuario.setEnabled(false);

        labelAcessoUsuario.setText("Acesso");

        acessoUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vendedor", "Administrador" }));

        javax.swing.GroupLayout panelUsuarioAcessoLayout = new javax.swing.GroupLayout(panelUsuarioAcesso);
        panelUsuarioAcesso.setLayout(panelUsuarioAcessoLayout);
        panelUsuarioAcessoLayout.setHorizontalGroup(
            panelUsuarioAcessoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUsuarioAcessoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelUsuarioAcessoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelUsuarioAcessoLayout.createSequentialGroup()
                        .addComponent(labelLoginUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(loginUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelSenhaUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelUsuarioAcessoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(checkSenha)
                            .addComponent(senhaUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelUsuarioAcessoLayout.createSequentialGroup()
                        .addComponent(labelAcessoUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(acessoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelUsuarioAcessoLayout.setVerticalGroup(
            panelUsuarioAcessoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUsuarioAcessoLayout.createSequentialGroup()
                .addComponent(checkSenha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelUsuarioAcessoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelLoginUsuario)
                    .addComponent(loginUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelSenhaUsuario)
                    .addComponent(senhaUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelUsuarioAcessoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelAcessoUsuario)
                    .addComponent(acessoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout usuariosLayout = new javax.swing.GroupLayout(usuarios);
        usuarios.setLayout(usuariosLayout);
        usuariosLayout.setHorizontalGroup(
            usuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(usuariosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(usuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(usuariosLayout.createSequentialGroup()
                        .addComponent(refreshUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(confirmUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteUsuario))
                    .addComponent(panelUsuarioDadoPessoal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelUsuarioEndereco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelUsuarioAcesso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        usuariosLayout.setVerticalGroup(
            usuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(usuariosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(usuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panelUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(usuariosLayout.createSequentialGroup()
                        .addComponent(panelUsuarioDadoPessoal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(panelUsuarioEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addComponent(panelUsuarioAcesso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addGroup(usuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(refreshUsuario)
                            .addGroup(usuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(deleteUsuario, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(confirmUsuario, javax.swing.GroupLayout.Alignment.TRAILING)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainPanel.add(usuarios, "usuarios");

        panelUsuario.setBorder(javax.swing.BorderFactory.createTitledBorder("Usuário"));

        logado.setText("usuário");

        javax.swing.GroupLayout panelUsuarioLayout = new javax.swing.GroupLayout(panelUsuario);
        panelUsuario.setLayout(panelUsuarioLayout);
        panelUsuarioLayout.setHorizontalGroup(
            panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logado)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelUsuarioLayout.setVerticalGroup(
            panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logado)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ferramentas.setFloatable(false);
        ferramentas.setRollover(true);

        btnVenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cart.png"))); // NOI18N
        btnVenda.setText("Vendas");
        btnVenda.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVendaActionPerformed(evt);
            }
        });
        ferramentas.add(btnVenda);

        btnCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cliente.png"))); // NOI18N
        btnCliente.setText("Clientes");
        btnCliente.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClienteActionPerformed(evt);
            }
        });
        ferramentas.add(btnCliente);

        btnProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/clothes.png"))); // NOI18N
        btnProduto.setText("Produtos");
        btnProduto.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProdutoActionPerformed(evt);
            }
        });
        ferramentas.add(btnProduto);

        btnCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buying.png"))); // NOI18N
        btnCompra.setText("Compras");
        btnCompra.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompraActionPerformed(evt);
            }
        });
        ferramentas.add(btnCompra);

        btnRelatorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/report.png"))); // NOI18N
        btnRelatorio.setText("Relatórios");
        btnRelatorio.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnRelatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRelatorioActionPerformed(evt);
            }
        });
        ferramentas.add(btnRelatorio);

        btnFornecedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/truck.png"))); // NOI18N
        btnFornecedor.setText("Fornecedores");
        btnFornecedor.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFornecedorActionPerformed(evt);
            }
        });
        ferramentas.add(btnFornecedor);

        btnUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/users.png"))); // NOI18N
        btnUsuario.setText("Usuários");
        btnUsuario.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuarioActionPerformed(evt);
            }
        });
        ferramentas.add(btnUsuario);

        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logout.png"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSair.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSairMouseClicked(evt);
            }
        });
        ferramentas.add(btnSair);

        panelDataHora.setBorder(javax.swing.BorderFactory.createTitledBorder("Data & Hora"));

        javax.swing.GroupLayout panelDataHoraLayout = new javax.swing.GroupLayout(panelDataHora);
        panelDataHora.setLayout(panelDataHoraLayout);
        panelDataHoraLayout.setHorizontalGroup(
            panelDataHoraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelDataHoraLayout.setVerticalGroup(
            panelDataHoraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 66, Short.MAX_VALUE)
        );

        jMenu1.setText("File");
        menuPrincipal.add(jMenu1);

        jMenu2.setText("Edit");
        menuPrincipal.add(jMenu2);

        setJMenuBar(menuPrincipal);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(eagles))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(logo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelDataHora, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ferramentas, javax.swing.GroupLayout.PREFERRED_SIZE, 844, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 121, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                                .addContainerGap())))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ferramentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(logo))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelDataHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(eagles)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVendaActionPerformed
        CardLayout card = (CardLayout)mainPanel.getLayout();
        card.show(mainPanel, "vendas");
        limpaCampos("venda");
    }//GEN-LAST:event_btnVendaActionPerformed

    private void btnClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClienteActionPerformed
        CardLayout card = (CardLayout)mainPanel.getLayout();
        card.show(mainPanel, "clientes");
        limpaCampos("cliente");
        carregaTabela("cliente");
    }//GEN-LAST:event_btnClienteActionPerformed

    private void btnProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProdutoActionPerformed
        CardLayout card = (CardLayout)mainPanel.getLayout();
        card.show(mainPanel, "produtos");
        limpaCampos("produto");
        carregaTabela("produto");
    }//GEN-LAST:event_btnProdutoActionPerformed

    private void btnCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompraActionPerformed
        if(btnCompra.isEnabled()){
            CardLayout card = (CardLayout)mainPanel.getLayout();
            card.show(mainPanel, "compras");
            limpaCampos("compra");
        }
        else{
            JOptionPane.showMessageDialog(null, "Acesso Restrito!");
        }
    }//GEN-LAST:event_btnCompraActionPerformed

    private void btnRelatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRelatorioActionPerformed
        if(btnRelatorio.isEnabled()){
            CardLayout card = (CardLayout)mainPanel.getLayout();
            card.show(mainPanel, "relatorios");
            limpaCampos("relatorio");
        }
        else{
            JOptionPane.showMessageDialog(null, "Acesso Restrito!");
        }
    }//GEN-LAST:event_btnRelatorioActionPerformed

    private void btnFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFornecedorActionPerformed
        if(btnFornecedor.isEnabled()){
            CardLayout card = (CardLayout)mainPanel.getLayout();
            card.show(mainPanel, "fornecedores");
            limpaCampos("fornecedore");
            carregaTabela("fornecedor");
        }
        else{
            JOptionPane.showMessageDialog(null, "Acesso Restrito!");
        }
    }//GEN-LAST:event_btnFornecedorActionPerformed

    private void btnUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuarioActionPerformed
        if(btnUsuario.isEnabled()){
            CardLayout card = (CardLayout)mainPanel.getLayout();
            card.show(mainPanel, "usuarios");
            limpaCampos("usuario");
            carregaTabela("usuario");
        }
        else{
            JOptionPane.showMessageDialog(null, "Acesso Restrito!");
        }
    }//GEN-LAST:event_btnUsuarioActionPerformed

   
    public void limpaCampos(String menu) {
        if(menu.equals("usuario")){
            /*Dados Pessoais*/
            nomeUsuario.setText(null);
            documentoUsuario.setText(null);
            sexoUsuario.setSelectedIndex(0);
            emailUsuario.setText(null);
            dataNascimentoUsuario.setText(null);
            dddUsuario.setText(null);
            telefoneUsuario.setText(null);
            dddUsuario2.setText(null);
            telefoneUsuario2.setText(null);
            
            /*Endereço*/
            cepUsuario.setText(null);
            logradouroUsuario.setText(null);
            numeroUsuario.setText(null);
            bairroUsuario.setText(null);
            cidadeUsuario.setText(null);
            estadoUsuario.setSelectedItem("SP");
            
            /*Dados de Acesso*/
            loginUsuario.setText(null);
            senhaUsuario.setText(null);
            acessoUsuario.setSelectedIndex(0);
            
            /*Tabela*/
            tabelaUsuario.clearSelection();
            
            /*Alteração de Senha*/
            checkSenha.setEnabled(false);
            senhaUsuario.setEnabled(true);
            senhaUsuario.setEditable(true);
        }
        else{
            if(menu.equals("cliente")){
                /*Dados Pessoais*/
                nomeCliente.setText(null);
                documentoCliente.setText(null);
                sexoCliente.setSelectedIndex(0);
                emailCliente.setText(null);
                dataNascimentoCliente.setText(null);
                dddCliente.setText(null);
                telefoneCliente.setText(null);
                dddCliente2.setText(null);
                telefoneCliente2.setText(null);

                /*Endereço*/
                cepCliente.setText(null);
                logradouroCliente.setText(null);
                numeroCliente.setText(null);
                bairroCliente.setText(null);
                cidadeCliente.setText(null);
                estadoCliente.setSelectedItem("SP");
                
                /*Tabela*/
                tabelaCliente.clearSelection();
            }
            else{
                if(menu.equals("fornecedor")){
                    /*Dados Pessoais*/
                    nomeFornecedor.setText(null);
                    documentoFornecedor.setText(null);
                    emailFornecedor.setText(null);
                    dddFornecedor.setText(null);
                    telefoneFornecedor.setText(null);
                    dddFornecedor2.setText(null);
                    telefoneFornecedor2.setText(null);

                    /*Endereço*/
                    cepFornecedor.setText(null);
                    logradouroFornecedor.setText(null);
                    numeroFornecedor.setText(null);
                    bairroFornecedor.setText(null);
                    cidadeFornecedor.setText(null);
                    estadoFornecedor.setSelectedIndex(0);

                    /*Tabela*/
                    tabelaFornecedor.clearSelection();
                }
                else{
                    if(menu.equals("produto")){
                        /*Cadastro Produto*/
                        descricaoProduto.setText(null);
                        imagemProduto.setIcon(null);
                        imagemProduto.setText("IMAGEM");
                        quantidadeProduto.setValue(1);
                        precoProduto.setText(null);
                        labelNomeArquivo.setText(null);
                        labelCaminhoArquivo.setText(null);
                        
                        /*Tabela*/
                        descricaoProdutoBusca.setText(null);
                        tabelaProduto.clearSelection();
                    }
                    else{
                        if(menu.equals("venda")){
                            /*Cliente*/
                            vendaDocumentoCliente.setText(null);
                            vendaNomeCliente.setText(null);
                            checkVendaClienteCadastrado.setSelected(false);
                            
                            /*Produtos*/
                            vendaIdProduto.setText(null);
                            ((DefaultTableModel)tabelaVendaProduto.getModel()).setNumRows(0);
                            totalVendaProduto.setText(null);
                            quantidadeVendaProduto.setText(null);
                            descontoVenda.setValue(0);
                            
                            /*Método de Pagamento*/
                            radioVendaVista.setSelected(false);
                            radioVendaParcelado.setSelected(false);
                            
                            /*Pagamento a Vista*/
                            valorPagoVenda.setText(null);
                            valorTotalVenda.setText(null);
                            valorTrocoVenda.setText(null);
                            
                            /*Pagamento Parcelado*/
                            dataVencimentoParcela.setText(null);
                            vendaParcela.removeAllItems();  
                        }
                    }
                }
            }
        }
    }

    public void preencheFormulario(String menu) throws NumberFormatException {
       if(menu.equals("venda")){
           
       }
       else{
           if(menu.equals("cliente")){
               
               Long id = Long.parseLong((String) tabelaCliente.getValueAt(tabelaCliente.getSelectedRow(), 0));
               Map<String, String> pessoa;
               pessoa = ControllerPessoa.searchPessoa(id);
               
               
                /*Dados Pessoais*/
                nomeCliente.setText(pessoa.get("nome"));
                documentoCliente.setText(pessoa.get("documento"));
                sexoCliente.setSelectedItem(pessoa.get("sexo"));
                emailCliente.setText(pessoa.get("email"));
                dataNascimentoCliente.setText(pessoa.get("dataNascimento"));
                
                
                List<Map<String, String>> telefones;
                telefones = ControllerPessoa.procuraTelefones(Long.parseLong(pessoa.get("idPessoa")));
                
                if(telefones.size() == 1){
                    dddCliente.setText(telefones.get(0).get("ddd"));
                    telefoneCliente.setText(telefones.get(0).get("numero"));
                }
                else{
                    dddCliente.setText(telefones.get(0).get("ddd"));
                    telefoneCliente.setText(telefones.get(0).get("numero"));
                    
                    dddCliente2.setText(telefones.get(1).get("ddd"));
                    telefoneCliente2.setText(telefones.get(1).get("numero"));
                }
                
                

                /*Endereço*/
                cepCliente.setText(pessoa.get("cep"));
                logradouroCliente.setText(pessoa.get("logradouro"));
                numeroCliente.setText(pessoa.get("numero"));
                bairroCliente.setText(pessoa.get("bairro"));
                cidadeCliente.setText(pessoa.get("cidade"));
                estadoCliente.setSelectedItem(pessoa.get("estado"));
                
                /*Edição*/
                deleteCliente.setEnabled(true);
                confirmCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit.png")));
           }
           else{
               if(menu.equals("produto")){
                   Long id = Long.parseLong((String) tabelaProduto.getValueAt(tabelaProduto.getSelectedRow(), 0));
                   Map<String, String> produto;
                   produto = ControllerProduto.searchProduto(id);
                   
                   /*Cadastro Produto*/
                   descricaoProduto.setText(produto.get("descricao"));
                   quantidadeProduto.setValue(Integer.parseInt(produto.get("quantidade")));
                   precoProduto.setText(produto.get("valorUnitario"));
                   
                   /*Imagem do Produto*/
                   URL resource = Principal.class.getResource("/produtos/");
                   
                   imagemProduto.setIcon(new javax.swing.ImageIcon(resource.getPath()+produto.get("imagem")));
                   
                   /*Edição*/
                    deleteProduto.setEnabled(true);
                    confirmProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit.png")));
               }
               else{
                   if(menu.equals("fornecedor")){
                        Long id = Long.parseLong((String) tabelaFornecedor.getValueAt(tabelaFornecedor.getSelectedRow(), 0));
                        Map<String, String> pessoa;
                        pessoa = ControllerPessoa.searchPessoa(id);

                        /*Dados Pessoais*/
                        nomeFornecedor.setText(pessoa.get("nome"));
                        documentoFornecedor.setText(pessoa.get("documento"));
                        emailFornecedor.setText(pessoa.get("email"));
                       
                        List<Map<String, String>> telefones;
                        telefones = ControllerPessoa.procuraTelefones(Long.parseLong(pessoa.get("idPessoa")));

                        if(telefones.size() == 1){
                            dddFornecedor.setText(telefones.get(0).get("ddd"));
                            telefoneFornecedor.setText(telefones.get(0).get("numero"));
                        }
                        else{
                            dddFornecedor.setText(telefones.get(0).get("ddd"));
                            telefoneFornecedor.setText(telefones.get(0).get("numero"));

                            dddFornecedor.setText(telefones.get(1).get("ddd"));
                            telefoneFornecedor.setText(telefones.get(1).get("numero"));
                        }

                        /*Endereço*/
                        cepFornecedor.setText(pessoa.get("cep"));
                        logradouroFornecedor.setText(pessoa.get("logradouro"));
                        numeroFornecedor.setText(pessoa.get("numero"));
                        bairroFornecedor.setText(pessoa.get("bairro"));
                        cidadeFornecedor.setText(pessoa.get("cidade"));
                        estadoFornecedor.setSelectedItem(pessoa.get("estado"));
                        
                        /*Edição*/
                        deleteFornecedor.setEnabled(true);
                        confirmFornecedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit.png")));

                   }
                   else{
                       if(menu.equals("usuario")){
                            Long id = Long.parseLong((String) tabelaUsuario.getValueAt(tabelaUsuario.getSelectedRow(), 0));
                            Map<String, String> funcionario;
                            funcionario = ControllerFuncionario.searchFuncionario(id);

                            /*Dados Pessoais*/
                            nomeUsuario.setText(funcionario.get("nome"));
                            documentoUsuario.setText(funcionario.get("documento"));
                            sexoUsuario.setSelectedItem(funcionario.get("sexo"));
                            emailUsuario.setText(funcionario.get("email"));
                            dataNascimentoUsuario.setText(funcionario.get("dataNascimento"));

                            List<Map<String, String>> telefones;
                            telefones = ControllerPessoa.procuraTelefones(Long.parseLong(funcionario.get("idPessoa")));

                            if(telefones.size() == 1){
                                dddUsuario.setText(telefones.get(0).get("ddd"));
                                telefoneUsuario.setText(telefones.get(0).get("numero"));
                            }
                            else{
                                dddUsuario.setText(telefones.get(0).get("ddd"));
                                telefoneUsuario.setText(telefones.get(0).get("numero"));

                                dddUsuario2.setText(telefones.get(1).get("ddd"));
                                telefoneUsuario2.setText(telefones.get(1).get("numero"));
                            }

                            /*Endereço*/
                            cepUsuario.setText(funcionario.get("cep"));
                            logradouroUsuario.setText(funcionario.get("logradouro"));
                            numeroUsuario.setText(funcionario.get("numero"));
                            bairroUsuario.setText(funcionario.get("bairro"));
                            cidadeUsuario.setText(funcionario.get("cidade"));
                            estadoUsuario.setSelectedItem(funcionario.get("estado"));

                            /*Dados de Acesso*/
                            loginUsuario.setText(funcionario.get("usuario"));
                            acessoUsuario.setSelectedItem(funcionario.get("acesso"));

                            /*Edição*/
                            deleteUsuario.setEnabled(true);
                            confirmUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit.png")));
                            checkSenha.setEnabled(true);
                            senhaUsuario.setEnabled(false);
                       }
                   }
               }
           }
       }
    }

    private void btnSairMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSairMouseClicked
        int resp = JOptionPane.showConfirmDialog(
            null, 
            "As operações não concretizadas serão perdidas\nDeseja mesmo sair?", 
            "Sair", 
            JOptionPane.YES_NO_OPTION
        );
        
        if (resp == JOptionPane.YES_OPTION) {
            this.dispose();
            new Login().setVisible(true);
        }
    }//GEN-LAST:event_btnSairMouseClicked

    private void checkSenhaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_checkSenhaMouseClicked
        if(checkSenha.isEnabled()){
            if(checkSenha.isSelected()){
                senhaUsuario.setEnabled(true);
                senhaUsuario.setEditable(true);
            }
            else{
                senhaUsuario.setEnabled(false);
                senhaUsuario.setEditable(false);
            }
        }
    }//GEN-LAST:event_checkSenhaMouseClicked

    private void refreshUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refreshUsuarioMouseClicked
        limpaCampos("usuario");
        deleteUsuario.setEnabled(false);
        confirmUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/checked (1).png")));
    }//GEN-LAST:event_refreshUsuarioMouseClicked

    private void confirmUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_confirmUsuarioMouseClicked

        Validacao valida = new Validacao();

        if(valida.validaNome(nomeUsuario.getText())){
            if(valida.validaEmail(emailUsuario.getText())){
                if(valida.validaDataNascimento(dataNascimentoUsuario.getText())){
                    if(valida.validaCpf(documentoUsuario.getText())){
                        if(valida.validaDdd(dddUsuario.getText())){
                            if(valida.validaTelefone(telefoneUsuario.getText())){
                                if(valida.validaCep(cepUsuario.getText())){
                                    if(valida.validaEndereco(logradouroUsuario.getText())){
                                        if(valida.validaEndereco(bairroUsuario.getText())){
                                            if(valida.validaEndereco(cidadeUsuario.getText())){
                                                if(valida.validaNumero(numeroUsuario.getText())){
                                                    if(valida.validaUsuario(loginUsuario.getText())){
                                                        if(valida.validaCombo(acessoUsuario.getSelectedIndex())){
                                                            /*Persist Pessoa*/
                                                            Calendar c = Calendar.getInstance();
                                                            int day   = Integer.parseInt(dataNascimentoUsuario.getText().substring(0, 2));
                                                            int month = Integer.parseInt(dataNascimentoUsuario.getText().substring(3, 5));
                                                            int year  = Integer.parseInt(dataNascimentoUsuario.getText().substring(6, 10));
                                                            c.set(year, (month-1), day);

                                                            /*Define qunatidade de Telefones*/
                                                            String[] numerosTel;
                                                            String[] dddsTel;
                                                            TipoTelefone[] tiposTel;

                                                            int tam = 0;
                                                            if(valida.validaTelefone(telefoneUsuario2.getText()) && valida.validaDdd(dddUsuario2.getText())){
                                                                tam = 2;
                                                                tiposTel = new TipoTelefone[tam];

                                                                if(telefoneUsuario.getText().length() == 8){
                                                                    tiposTel[0] = TipoTelefone.Fixo;
                                                                }
                                                                else{
                                                                    tiposTel[0] = TipoTelefone.Celular;
                                                                }

                                                                if(telefoneUsuario2.getText().length() == 8){
                                                                    tiposTel[1] = TipoTelefone.Fixo;
                                                                }
                                                                else{
                                                                    tiposTel[1] = TipoTelefone.Celular;
                                                                }
                                                            }
                                                            else{
                                                                tam = 1;
                                                                tiposTel = new TipoTelefone[tam];

                                                                if(telefoneUsuario.getText().length() == 8){
                                                                    tiposTel[0] = TipoTelefone.Fixo;
                                                                }
                                                                else{
                                                                    tiposTel[0] = TipoTelefone.Celular;
                                                                }
                                                            }

                                                            numerosTel = new String[tam];
                                                            dddsTel = new String[tam];


                                                            for(int i = 0; i < tam; i++){
                                                                numerosTel[i] = telefoneUsuario.getText();
                                                                dddsTel[i] = dddUsuario.getText();
                                                            }

                                                            if(!deleteUsuario.isEnabled()){
                                                                if(valida.validaSenha(senhaUsuario.getText())){
                                                                    try {
                                                                        ControllerFuncionario.cadastrar(nomeUsuario.getText(), emailUsuario.getText(),
                                                                            c, Sexo.valueOf(sexoUsuario.getSelectedItem().toString()), numerosTel,
                                                                            dddsTel, tiposTel, estadoUsuario.getSelectedItem().toString(),
                                                                            cidadeUsuario.getText(), bairroUsuario.getText(), logradouroUsuario.getText(), numeroUsuario.getText(),
                                                                            cepUsuario.getText(), documentoUsuario.getText(), TipoDocumento.CPF,
                                                                            loginUsuario.getText(), senhaUsuario.getText(), Acesso.valueOf(acessoUsuario.getSelectedItem().toString())
                                                                        );

                                                                        limpaCampos("usuario");

                                                                        carregaTabela("usuario");
                                                                    } 
                                                                    catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                                                                        JOptionPane.showMessageDialog(null, "Falha no Algoritmo\n Entre em contato com o Administrador.");
                                                                    }
                                                                }
                                                                else{
                                                                    JOptionPane.showMessageDialog(null, "Senha requerida");
                                                                    senhaUsuario.requestFocus();
                                                                }

                                                            }
                                                            else{
                                                                /*Atualiza os Dados*/
                                                                Long id = Long.parseLong(
                                                                    (String) tabelaUsuario.getValueAt(tabelaUsuario.getSelectedRow(), 0)
                                                                );
                                                                
                                                                if(checkSenha.isSelected()){
                                                                    try{
                                                                        if(valida.validaSenha(senhaUsuario.getText())){
                                                                            ControllerFuncionario.editarFuncionario(
                                                                                id, nomeUsuario.getText(), emailUsuario.getText(),
                                                                                c, Sexo.valueOf(sexoUsuario.getSelectedItem().toString()), numerosTel,
                                                                                dddsTel, tiposTel, estadoUsuario.getSelectedItem().toString(),
                                                                                cidadeUsuario.getText(), bairroUsuario.getText(), logradouroUsuario.getText(), numeroUsuario.getText(),
                                                                                cepUsuario.getText(), documentoUsuario.getText(),
                                                                                loginUsuario.getText(),Acesso.valueOf(acessoUsuario.getSelectedItem().toString())
                                                                            );
                                                                            
                                                                            ControllerFuncionario.novaSenha(id, senhaUsuario.getText());
                                                                            
                                                                            limpaCampos("usuario");
                                                                            carregaTabela("usuario");
                                                                        }
                                                                        else{
                                                                            JOptionPane.showMessageDialog(null, "Informe uma nova senha válida");
                                                                            senhaUsuario.requestFocus();
                                                                        }
                                                                    }
                                                                    catch(NoSuchAlgorithmException | UnsupportedEncodingException ex){
                                                                        JOptionPane.showMessageDialog(null, "Falha no Algoritmo\n Entre em contato com o Administrador.");
                                                                    }
                                                                }
                                                                else{
                                                                    ControllerFuncionario.editarFuncionario(
                                                                        id, nomeUsuario.getText(), emailUsuario.getText(),
                                                                        c, Sexo.valueOf(sexoUsuario.getSelectedItem().toString()), numerosTel,
                                                                        dddsTel, tiposTel, estadoUsuario.getSelectedItem().toString(),
                                                                        cidadeUsuario.getText(), bairroUsuario.getText(), logradouroUsuario.getText(), numeroUsuario.getText(),
                                                                        cepUsuario.getText(), documentoUsuario.getText(),
                                                                        loginUsuario.getText(),Acesso.valueOf(acessoUsuario.getSelectedItem().toString())
                                                                    );
                                                                            
                                                                    limpaCampos("usuario");
                                                                    carregaTabela("usuario");
                                                                }
                                                            }
                                                        }
                                                        else{
                                                            JOptionPane.showMessageDialog(null, "Selecione o tipo de acesso");
                                                            acessoUsuario.requestFocus();
                                                        }
                                                    }
                                                    else{
                                                        JOptionPane.showMessageDialog(null, "Usuário Requerido");
                                                        loginUsuario.requestFocus();
                                                    }
                                                }
                                                else{
                                                    JOptionPane.showMessageDialog(null, "Número Requerido");
                                                    numeroUsuario.requestFocus();
                                                }
                                            }
                                            else{
                                                JOptionPane.showMessageDialog(null, "Cidade Requerido");
                                                cidadeUsuario.requestFocus();
                                            }
                                        }
                                        else{
                                            JOptionPane.showMessageDialog(null, "Bairro Requerido");
                                            bairroUsuario.requestFocus();
                                        }
                                    }
                                    else{
                                        JOptionPane.showMessageDialog(null, "Logradouro Requerido");
                                        logradouroUsuario.requestFocus();
                                    }
                                }
                                else{
                                    JOptionPane.showMessageDialog(null, "Cep Requerido");
                                    cepUsuario.requestFocus();
                                }
                            }
                            else{
                                JOptionPane.showMessageDialog(null, "Telefone Requerido");
                                telefoneUsuario.requestFocus();
                            }
                        }else{
                            JOptionPane.showMessageDialog(null, "DDD Requerido");
                            dddUsuario.requestFocus();
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "CPF Requerido");
                        documentoUsuario.requestFocus();
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Data de Nascimento Requerido");
                    dataNascimentoUsuario.requestFocus();
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "E-mail Requerido");
                emailUsuario.requestFocus();
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Nome Requerido");
            nomeUsuario.requestFocus();
        }
    }//GEN-LAST:event_confirmUsuarioMouseClicked

    private void deleteUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteUsuarioMouseClicked
        if(deleteUsuario.isEnabled()){
            Long id = Long.parseLong(
                (String) tabelaUsuario.getValueAt(tabelaUsuario.getSelectedRow(), 0)
            );

            ControllerFuncionario.deleteFuncionario(id);
            
            limpaCampos("usuario");
            carregaTabela("usuario");
        }
    }//GEN-LAST:event_deleteUsuarioMouseClicked

    private void tabelaUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaUsuarioMouseClicked
        preencheFormulario("usuario");
    }//GEN-LAST:event_tabelaUsuarioMouseClicked

    private void refreshClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refreshClienteMouseClicked
        limpaCampos("cliente");
        carregaTabela("cliente");
        deleteCliente.setEnabled(false);
        confirmCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/checked (1).png")));
    }//GEN-LAST:event_refreshClienteMouseClicked

    private void confirmClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_confirmClienteMouseClicked
        Validacao valida = new Validacao();
        
        if(valida.validaNome(nomeCliente.getText())){
            if(valida.validaEmail(emailCliente.getText())){
                if(valida.validaDataNascimento(dataNascimentoCliente.getText())){
                    if(valida.validaCpf(documentoCliente.getText())){
                        if(valida.validaDdd(dddCliente.getText())){
                            if(valida.validaTelefone(telefoneCliente.getText())){
                                if(valida.validaCep(cepCliente.getText())){
                                    if(valida.validaEndereco(logradouroCliente.getText())){
                                        if(valida.validaNumero(numeroCliente.getText())){
                                            if(valida.validaEndereco(bairroCliente.getText())){
                                                if(valida.validaEndereco(cidadeCliente.getText())){
                                                    
                                                    /*Persist Pessoa*/
                                                    Calendar c = Calendar.getInstance();
                                                    int day   = Integer.parseInt(dataNascimentoCliente.getText().substring(0, 2));
                                                    int month = Integer.parseInt(dataNascimentoCliente.getText().substring(3, 5));
                                                    int year  = Integer.parseInt(dataNascimentoCliente.getText().substring(6, 10));
                                                    c.set(year, (month-1), day);
                                                    
                                                    String[] numerosTel;
                                                    String[] dddsTel;
                                                    TipoTelefone[] tiposTel;
                                                    
                                                    
                                                    /*Define qunatidade de Telefones*/
                                                    int tam = 0;
                                                    if(valida.validaTelefone(telefoneCliente2.getText()) && valida.validaDdd(dddCliente2.getText())){
                                                        tam = 2;
                                                        tiposTel = new TipoTelefone[tam];
                                                        numerosTel = new String[tam];
                                                        dddsTel = new String[tam];
                                                        
                                                        if(telefoneCliente.getText().length() == 8){
                                                            numerosTel[0] = telefoneCliente.getText();
                                                            dddsTel[0] = dddCliente.getText();
                                                            tiposTel[0] = TipoTelefone.Fixo;
                                                        }
                                                        else{
                                                            numerosTel[0] = telefoneCliente.getText();
                                                            dddsTel[0] = dddCliente.getText();
                                                            tiposTel[0] = TipoTelefone.Celular;
                                                        }
                                                        
                                                        if(telefoneCliente2.getText().length() == 8){
                                                            numerosTel[1] = telefoneCliente2.getText();
                                                            dddsTel[1] = dddCliente2.getText();
                                                            tiposTel[1] = TipoTelefone.Fixo;
                                                        }
                                                        else{
                                                            numerosTel[1] = telefoneCliente2.getText();
                                                            dddsTel[1] = dddCliente2.getText();
                                                            tiposTel[1] = TipoTelefone.Celular;
                                                        }
                                                    }
                                                    else{
                                                        tam = 1;
                                                        tiposTel = new TipoTelefone[tam];
                                                        numerosTel = new String[tam];
                                                        dddsTel = new String[tam];
                                                        
                                                        
                                                        if(telefoneCliente.getText().length() == 8){
                                                            numerosTel[0] = telefoneCliente.getText();
                                                            dddsTel[0] = dddCliente.getText();
                                                            tiposTel[0] = TipoTelefone.Fixo;
                                                        }
                                                        else{
                                                            numerosTel[0] = telefoneCliente.getText();
                                                            dddsTel[0] = dddCliente.getText();
                                                            tiposTel[0] = TipoTelefone.Celular;
                                                        }
                                                    }
                                                    
                                                    if(!deleteCliente.isEnabled()){
                                                        
                                                        ControllerPessoa.cadastrar(nomeCliente.getText(), emailCliente.getText(),
                                                            c, Sexo.valueOf(sexoCliente.getSelectedItem().toString()), numerosTel, 
                                                            dddsTel, tiposTel, estadoCliente.getSelectedItem().toString(),
                                                            cidadeCliente.getText(), bairroCliente.getText(), logradouroCliente.getText(), numeroCliente.getText(),
                                                            cepCliente.getText(), documentoCliente.getText(), TipoDocumento.CPF
                                                        );
                                                        
                                                        JOptionPane.showMessageDialog(null, "Cliente Inserido!");
                                                        
                                                        limpaCampos("cliente");

                                                        carregaTabela("cliente");
                                                    
                                                    }
                                                    else{
                                                        /*Atualiza os Dados*/
                                                        Long id = Long.parseLong((String) tabelaCliente.getValueAt(tabelaCliente.getSelectedRow(), 0));
                                                        
                                                        ControllerPessoa.editarPessoa(
                                                            id, nomeCliente.getText(), emailCliente.getText(), c, Sexo.valueOf(sexoCliente.getSelectedItem().toString()), 
                                                            numerosTel, dddsTel, tiposTel, estadoCliente.getSelectedItem().toString(),
                                                            cidadeCliente.getText(), bairroCliente.getText(), logradouroCliente.getText(), numeroCliente.getText(),
                                                            cepCliente.getText(), documentoCliente.getText()
                                                        );

                                                        JOptionPane.showMessageDialog(null, "Dados Alterados");
                                                        
                                                        limpaCampos("cliente");
                                                        carregaTabela("cliente");
                                                    }
                                                }
                                                else{
                                                    JOptionPane.showMessageDialog(null,"Cidade requerida!");
                                                    cidadeCliente.requestFocus();
                                                }
                                            }
                                            else{
                                                JOptionPane.showMessageDialog(null,"Bairro requerido!");
                                                bairroCliente.requestFocus();
                                            }
                                        }
                                        else{
                                            JOptionPane.showMessageDialog(null,"Número requerido!");
                                            numeroCliente.requestFocus();
                                        }
                                    }
                                    else{
                                        JOptionPane.showMessageDialog(null,"Logradouro requerido!");
                                        logradouroCliente.requestFocus();
                                    }
                                }
                                else{
                                    JOptionPane.showMessageDialog(null,"CEP requerido!");
                                    cepCliente.requestFocus();
                                }              
                            }
                            else{
                                JOptionPane.showMessageDialog(null,"Telefone requerido!");
                                telefoneCliente.requestFocus();
                            }
                        }
                        else{
                            JOptionPane.showMessageDialog(null,"DDD requerido!");
                            dddCliente.requestFocus();
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"CPF requerido!");
                        documentoCliente.requestFocus();
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null,"Data de Nascimento Requerida!");
                    dataNascimentoCliente.requestFocus();
                }
            }
            else{
                JOptionPane.showMessageDialog(null,"E-Mail requerido!");
                emailCliente.requestFocus();
            }
        }
        else{
            JOptionPane.showMessageDialog(null,"Nome requerido!");
            nomeCliente.requestFocus();
        }
    }//GEN-LAST:event_confirmClienteMouseClicked

    private void tabelaClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaClienteMouseClicked
        preencheFormulario("cliente");
    }//GEN-LAST:event_tabelaClienteMouseClicked

    private void deleteClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteClienteMouseClicked
        if(deleteCliente.isEnabled()){
            Long id = Long.parseLong(
                (String) tabelaCliente.getValueAt(tabelaCliente.getSelectedRow(), 0)
            );

            ControllerPessoa.deletePessoa(id);
            JOptionPane.showMessageDialog(null, "Cliente Excluído!");
            carregaTabela("cliente");
        }
    }//GEN-LAST:event_deleteClienteMouseClicked

    private void refreshFornecedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refreshFornecedorMouseClicked
        limpaCampos("fornecedor");
        deleteFornecedor.setEnabled(false);
        confirmFornecedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/checked (1).png")));
    }//GEN-LAST:event_refreshFornecedorMouseClicked

    private void confirmFornecedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_confirmFornecedorMouseClicked
        Validacao valida = new Validacao();
        
        if(valida.validaNome(nomeFornecedor.getText())){
            if(valida.validaEmail(emailFornecedor.getText())){
                if(valida.validaCnpj(documentoFornecedor.getText())){
                    if(valida.validaDdd(dddFornecedor.getText())){
                        if(valida.validaTelefone(telefoneFornecedor.getText())){
                            if(valida.validaCep(cepFornecedor.getText())){
                                if(valida.validaEndereco(logradouroFornecedor.getText())){
                                    if(valida.validaNumero(numeroFornecedor.getText())){
                                        if(valida.validaEndereco(bairroFornecedor.getText())){
                                            if(valida.validaEndereco(cidadeFornecedor.getText())){
                                                
                                                /*Persist Pessoa*/
                                                String[] numerosTel;
                                                String[] dddsTel;
                                                TipoTelefone[] tiposTel;


                                                /*Define qunatidade de Telefones*/
                                                int tam = 0;
                                                if(valida.validaTelefone(telefoneFornecedor2.getText()) && valida.validaDdd(dddFornecedor2.getText())){
                                                    tam = 2;
                                                    tiposTel = new TipoTelefone[tam];

                                                    if(telefoneFornecedor.getText().length() == 8){
                                                        tiposTel[0] = TipoTelefone.Fixo;
                                                    }
                                                    else{
                                                        tiposTel[0] = TipoTelefone.Celular;
                                                    }

                                                    if(telefoneFornecedor2.getText().length() == 8){
                                                        tiposTel[1] = TipoTelefone.Fixo;
                                                    }
                                                    else{
                                                        tiposTel[1] = TipoTelefone.Celular;
                                                    }
                                                }
                                                else{
                                                    tam = 1;
                                                    tiposTel = new TipoTelefone[tam];

                                                    if(telefoneFornecedor.getText().length() == 8){
                                                        tiposTel[0] = TipoTelefone.Fixo;
                                                    }
                                                    else{
                                                        tiposTel[0] = TipoTelefone.Celular;
                                                    }
                                                }

                                                numerosTel = new String[tam];
                                                dddsTel = new String[tam];


                                                for(int i = 0; i < tam; i++){
                                                    numerosTel[i] = telefoneFornecedor.getText();
                                                    dddsTel[i] = dddFornecedor.getText();
                                                }

                                                Calendar c = Calendar.getInstance();
                                                c.set(0, 0, 0);

                                                if(!deleteFornecedor.isEnabled()){
                                                    /*Persistence With Hibernate - Good Luck For Us!!!!*/

                                                    ControllerPessoa.cadastrar(nomeFornecedor.getText(), emailFornecedor.getText(),
                                                        c, Sexo.Não_Definido, numerosTel, dddsTel, tiposTel, estadoFornecedor.getSelectedItem().toString(),
                                                        cidadeFornecedor.getText(), bairroFornecedor.getText(), logradouroFornecedor.getText(), 
                                                        numeroFornecedor.getText(),cepFornecedor.getText(), documentoFornecedor.getText(), TipoDocumento.CNPJ
                                                    );
                                                    
                                                    limpaCampos("fornecedor");

                                                    carregaTabela("fornecedor");

                                                }
                                                else{
                                                    /*Atualiza os Dados*/
                                                    Long id = Long.parseLong(
                                                       (String) tabelaFornecedor.getValueAt(tabelaFornecedor.getSelectedRow(), 0)
                                                    );
                                                    
                                                    ControllerPessoa.editarPessoa(
                                                        id, nomeFornecedor.getText(), emailFornecedor.getText(), c, Sexo.Não_Definido, 
                                                        numerosTel, dddsTel, tiposTel, estadoFornecedor.getSelectedItem().toString(),
                                                        cidadeFornecedor.getText(), bairroFornecedor.getText(), logradouroFornecedor.getText(), numeroFornecedor.getText(),
                                                        cepFornecedor.getText(), documentoFornecedor.getText()
                                                    );
                                                    
                                                    
                                                    limpaCampos("fornecedor");
                                                    carregaTabela("fornecedor");
                                                }
                                                
                                                
                                            }
                                            else{
                                                JOptionPane.showMessageDialog(null, "Cidade Requerida!");
                                                cidadeFornecedor.requestFocus();
                                            }
                                        }
                                        else{
                                            JOptionPane.showMessageDialog(null, "Bairro Requerido!");
                                            bairroFornecedor.requestFocus();
                                        }
                                    }
                                    else{
                                        JOptionPane.showMessageDialog(null, "Número Requerido!");
                                        numeroFornecedor.requestFocus();
                                    }
                                }
                                else{
                                    JOptionPane.showMessageDialog(null, "Logradouro Requerido!");
                                    logradouroFornecedor.requestFocus();
                                }
                            }
                            else{
                                JOptionPane.showMessageDialog(null, "CEP Requerido!");
                                cepFornecedor.requestFocus();
                            }
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Telefone Requerido!");
                            telefoneFornecedor.requestFocus();
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "DDD Requerido!");
                        dddFornecedor.requestFocus();
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "CNPJ Requerido!");
                    documentoFornecedor.requestFocus();
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "E-Mail Requerido!");
                emailFornecedor.requestFocus();
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Nome Fantasia Requerido!");
            nomeFornecedor.requestFocus();
        }
        
    }//GEN-LAST:event_confirmFornecedorMouseClicked

    private void tabelaFornecedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaFornecedorMouseClicked
        preencheFormulario("fornecedor");
    }//GEN-LAST:event_tabelaFornecedorMouseClicked

    private void deleteFornecedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteFornecedorMouseClicked
        if(deleteFornecedor.isEnabled()){
            Long id = Long.parseLong(
                (String) tabelaFornecedor.getValueAt(tabelaFornecedor.getSelectedRow(), 0)
            );

            ControllerPessoa.deletePessoa(id);

            limpaCampos("fornecedor");
            carregaTabela("fornecedor");
        }
    }//GEN-LAST:event_deleteFornecedorMouseClicked

    private void imagemProdutoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imagemProdutoMouseClicked
        JFileChooser arquivo = new JFileChooser();

        int retorno = arquivo.showOpenDialog(arquivo);
        

        if (retorno == JFileChooser.APPROVE_OPTION) {
            String caminhoArquivo = arquivo.getSelectedFile().getAbsolutePath();
            String nomeArquivo = arquivo.getSelectedFile().getName();
            File selecionado = arquivo.getSelectedFile();
            
            String extensao = getExtensaoArquivo(selecionado);
            
            /*Verifica o sistema operacional utilizado*/
            String sistema = System.getProperty("os.name");
            if(sistema.equals("Linux")){
                extensao = "jpg";
            }
           
            if(extensao.toLowerCase().equals("jpg") || extensao.toLowerCase().equals("png")){
                try {
                    BufferedImage original = ImageIO.read(new File(caminhoArquivo));

                    imagemProduto.setText(null);
                    labelNomeArquivo.setText(nomeArquivo);
                    labelCaminhoArquivo.setText(caminhoArquivo);
                    imagemProduto.setIcon(new javax.swing.ImageIcon(original));
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Arquivo não suportado.");
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Extensão não permitida.\nApenas jpg ou png");
            }
        }
    }//GEN-LAST:event_imagemProdutoMouseClicked

    private void refreshProdutoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refreshProdutoMouseClicked
        limpaCampos("produto");
        deleteProduto.setEnabled(false);
        confirmProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/checked (1).png")));
    }//GEN-LAST:event_refreshProdutoMouseClicked

    private void confirmProdutoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_confirmProdutoMouseClicked
          Validacao valida = new Validacao();
          
          if(valida.validaImagem(imagemProduto)){
                  if(valida.validaPreco(precoProduto.getText())){
                        if(valida.validaTexto(descricaoProduto.getText())){
                            
                            if(!deleteProduto.isEnabled()){
                                FileInputStream origem;
                                FileOutputStream destino;
                                FileChannel fcOrigem;
                                FileChannel fcDestino;

                                /*Persistindo Produto*/
                                try {

                                   URL resource = Principal.class.getResource("/produtos/");
                                   
                                   origem  = new FileInputStream(labelCaminhoArquivo.getText());
                                   destino = new FileOutputStream(Paths.get(resource.toURI()).toFile()+ "/" +labelNomeArquivo.getText());

                                   fcOrigem = origem.getChannel();
                                   fcDestino = destino.getChannel();

                                   fcOrigem.transferTo(0, fcOrigem.size(), fcDestino);

                                   origem.close();
                                   destino.close();
                                } 
                                 catch (FileNotFoundException ex) {
                                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                                } 
                                 catch (IOException ex) {
                                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                                } 
                                 catch (URISyntaxException ex) {
                                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                ControllerProduto.cadastrar(
                                    descricaoProduto.getText(),
                                    Integer.parseInt(quantidadeProduto.getValue().toString()),
                                    Double.parseDouble(precoProduto.getText().replace(",",".")), 
                                    labelNomeArquivo.getText()
                                );
                                
                                limpaCampos("produto");

                                carregaTabela("produto");
                            }
                            else{
                                /*Atualiza os Dados*/
                                String imagem = "";
                                
                                Long id = Long.parseLong(
                                    (String) tabelaProduto.getValueAt(tabelaProduto.getSelectedRow(), 0)
                                );

                                Map<String, String> produto = ControllerProduto.searchProduto(id);
                                if(labelNomeArquivo.getText() != null){
                                    
                                    imagem = labelNomeArquivo.getText();
                                    
                                    FileInputStream origem;
                                    FileOutputStream destino;
                                    FileChannel fcOrigem;
                                    FileChannel fcDestino;
                                    
                                    File imagemAntiga = new File(produto.get("imagem"));
                                    imagemAntiga.delete();
                                    
                                    try {
                                        URL resource = Principal.class.getResource("/produtos/");
                                        origem = new FileInputStream(labelCaminhoArquivo.getText());
                                        
                                        destino = new FileOutputStream(Paths.get(resource.toURI()).toFile()+ "/" +labelNomeArquivo.getText());

                                        fcOrigem = origem.getChannel();
                                        fcDestino = destino.getChannel();

                                        fcOrigem.transferTo(0, fcOrigem.size(), fcDestino);

                                        origem.close();
                                        destino.close();
                                    } 
                                    catch (FileNotFoundException ex) {
                                        Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                                    } 
                                    catch (URISyntaxException ex) {
                                        Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                                    } 
                                    catch (IOException ex) {
                                        Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                else{
                                    imagem = produto.get("imagem");
                                }
                                
                                ControllerProduto.alterProduto(
                                    id, 
                                    descricaoProduto.getText(), 
                                    Double.parseDouble(precoProduto.getText().replace(",", ".")),
                                    Integer.parseInt(quantidadeProduto.getValue().toString()), 
                                    imagem
                                );
                                
                                limpaCampos("produto");
                                carregaTabela("produto");
                            }

                        }
                        else{
                              JOptionPane.showMessageDialog(null,"Descrição requerida!");
                              descricaoProduto.requestFocus();
                        }
                  }
                  else{
                        JOptionPane.showMessageDialog(null,"Valor de compra requerido!");
                        precoProduto.requestFocus();
                  }
              
          }
          else{
              JOptionPane.showMessageDialog(null,"Selecione uma imagem!");
              imagemProduto.requestFocus();
          }
    }//GEN-LAST:event_confirmProdutoMouseClicked

    private void tabelaProdutoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaProdutoMouseClicked
        preencheFormulario("produto");
    }//GEN-LAST:event_tabelaProdutoMouseClicked

    private void deleteProdutoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteProdutoMouseClicked
        if(deleteProduto.isEnabled()){
            Long id = Long.parseLong(
                (String) tabelaProduto.getValueAt(tabelaProduto.getSelectedRow(), 0)
            );
            Map<String, String> produto = ControllerProduto.searchProduto(id);

            URL resource = Principal.class.getResource("/produtos/");
            File imagemAntiga = new File(resource.getPath()+produto.get("imagem"));
            imagemAntiga.delete();

            ControllerProduto.deleteProduto(id);

            JOptionPane.showMessageDialog(null, "Produto Excluido.");

            limpaCampos("produto");
            carregaTabela("produto");
        }
    }//GEN-LAST:event_deleteProdutoMouseClicked

    private void calculaTrocoVendaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_calculaTrocoVendaMouseClicked
        DecimalFormat df = new DecimalFormat("0.00");
        if(calculaTrocoVenda.isEnabled()){
            if(!valorPagoVenda.getText().equals("")){
                if(Double.parseDouble(valorPagoVenda.getText().replace(",", ".")) > 0.0 &&
                    Double.parseDouble(valorPagoVenda.getText().replace(",", ".")) >=
                    Double.parseDouble(valorTotalVenda.getText().replace(",", "."))){

                    Double troco = Double.parseDouble(valorPagoVenda.getText().replace(",", "."))
                    - Double.parseDouble(valorTotalVenda.getText().replace(",", "."));

                    valorTrocoVenda.setText(df.format(troco));
                }
                else{
                    JOptionPane.showMessageDialog(null, "Valor informado incorreto, favor verificar.");
                    valorPagoVenda.requestFocus();
                }
            }
        }
    }//GEN-LAST:event_calculaTrocoVendaMouseClicked

    private void radioVendaParceladoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_radioVendaParceladoMouseClicked
        if(!quantidadeVendaProduto.getText().equals("")){
            DecimalFormat df = new DecimalFormat("0.00");
            for(int i = 2; i <= 10; i++){
                Double valor = Double.parseDouble(totalVendaProduto.getText().replace(",", "."));
                vendaParcela.addItem(i+"X "+df.format((valor/i)));
            }
            if(Integer.parseInt(quantidadeVendaProduto.getText()) > 0){
                /**/

                labelParcelasVenda.setEnabled(true);
                vendaParcela.setEnabled(true);
                labelDataVencimentoParcela.setEnabled(true);
                dataVencimentoParcela.setEnabled(true);
                dataVencimentoParcela.setEditable(true);

                labelPagoVenda.setEnabled(false);
                labelTotalVenda.setEnabled(false);
                labelTrocoVenda.setEnabled(false);

                valorPagoVenda.setEnabled(false);
                valorTotalVenda.setEnabled(false);
                valorTrocoVenda.setEnabled(false);

                calculaTrocoVenda.setEnabled(false);

                /**/
            }
            else{
                JOptionPane.showMessageDialog(null, "Não existem produtos na compra!");
                opcoesPagamento.clearSelection();
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Não existem produtos na compra!");
            opcoesPagamento.clearSelection();
        }
    }//GEN-LAST:event_radioVendaParceladoMouseClicked

    private void radioVendaVistaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_radioVendaVistaMouseClicked
        if(!quantidadeVendaProduto.getText().equals("")){
            if(Integer.parseInt(quantidadeVendaProduto.getText()) > 0){
                /**/
                DecimalFormat df = new DecimalFormat("0.00");
                Double desconto = Double.parseDouble(descontoVenda.getValue().toString());
                desconto = Double.parseDouble(totalVendaProduto.getText().replace(",",".")) -
                Double.parseDouble(totalVendaProduto.getText().replace(",","."))*(desconto/100);

                valorTotalVenda.setText(df.format(desconto));

                labelParcelasVenda.setEnabled(false);
                vendaParcela.setEnabled(false);
                labelDataVencimentoParcela.setEnabled(false);
                dataVencimentoParcela.setEnabled(false);

                labelPagoVenda.setEnabled(true);
                labelTotalVenda.setEnabled(true);
                labelTrocoVenda.setEnabled(true);

                valorPagoVenda.setEnabled(true);
                valorPagoVenda.setEditable(true);
                valorTotalVenda.setEnabled(true);
                valorTrocoVenda.setEnabled(true);

                calculaTrocoVenda.setEnabled(true);

                /**/
            }
            else{
                JOptionPane.showMessageDialog(null, "Não existem produtos na compra!");
                opcoesPagamento.clearSelection();
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Não existem produtos na compra!");
            opcoesPagamento.clearSelection();
        }
    }//GEN-LAST:event_radioVendaVistaMouseClicked

    private void checkVendaClienteCadastradoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_checkVendaClienteCadastradoMouseClicked
        if(checkVendaClienteCadastrado.isSelected()){
            vendaDocumentoCliente.setEnabled(false);
            btnBuscaVendaCliente.setEnabled(false);
            
            vendaNomeCliente.setText("Cliente");
            vendaDocumentoCliente.setText("111.111.111-11");
            vendaNomeCliente.setEnabled(false);
        }
        else{
            vendaDocumentoCliente.setText(null);
            vendaDocumentoCliente.setEnabled(true);
            btnBuscaVendaCliente.setEnabled(true);
            vendaNomeCliente.setText(null);
            vendaNomeCliente.setEnabled(true);
        }
    }//GEN-LAST:event_checkVendaClienteCadastradoMouseClicked

    private void btnBuscaVendaClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscaVendaClienteMouseClicked
        if(btnBuscaVendaCliente.isEnabled()){
            try{
                Map<String, String> pessoa = ControllerPessoa.searchPessoa(vendaDocumentoCliente.getText());
                vendaNomeCliente.setText(pessoa.get("nome"));
            }
            catch(NoResultException e){
                JOptionPane.showMessageDialog(null,"Cliente não encontrado");

                vendaNomeCliente.setText(null);
                vendaDocumentoCliente.setText(null);
                vendaDocumentoCliente.requestFocus();
            }
        }
    }//GEN-LAST:event_btnBuscaVendaClienteMouseClicked

    private void fecharVendaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fecharVendaMouseClicked
        Validacao valida = new Validacao();
        if(!vendaNomeCliente.getText().equals("")){
            if(tabelaVendaProduto.getRowCount() > 0){
                if(radioVendaVista.isSelected() || radioVendaParcelado.isSelected()){
                    if(radioVendaVista.isSelected()){
                        if(!valorTrocoVenda.getText().equals("")){
                            /*Vista*/
                            int [] quantidades = new int[tabelaVendaProduto.getRowCount()];

                            Long[] produtosVenda = new Long[tabelaVendaProduto.getRowCount()];

                            for(int i = 0; i < tabelaVendaProduto.getRowCount(); i++){
                                Long id = Long.parseLong(tabelaVendaProduto.getValueAt(i, 0).toString());
                                int quantidade = Integer.parseInt(tabelaVendaProduto.getValueAt(i, 3).toString());

                                quantidades[i] = quantidade;
                                produtosVenda[i] = id;
                            }

                            Map<String, String> mapFuncionario = ControllerFuncionario.searchFuncionario(logado.getText());
                            Map<String, String> mapPessoa = ControllerPessoa.searchPessoa(vendaDocumentoCliente.getText());

                            ControllerVendas.vender(
                                Long.valueOf(mapPessoa.get("idPessoa")),
                                Long.valueOf(mapFuncionario.get("idFuncionario")),
                                TipoVenda.Vista,
                                produtosVenda,
                                quantidades,
                                Double.parseDouble(descontoVenda.getValue().toString())
                            );

                            
                            limpaCampos("venda");

                            JOptionPane.showMessageDialog(null, "Venda Realizada com Sucesso!");
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Calcule o troco para prosseguir.");
                        }
                    }
                    else{
                        /*Crediario*/
                        if(valida.validaDataNascimento(dataVencimentoParcela.getText())){
                            Calendar dataAtual = Calendar.getInstance();
                            Calendar novaData  = Calendar.getInstance();
                            
                            int dia   = Integer.parseInt(dataVencimentoParcela.getText().substring(0, 2));
                            int mes   = Integer.parseInt(dataVencimentoParcela.getText().substring(3, 5));
                            int ano   = Integer.parseInt(dataVencimentoParcela.getText().substring(6, 10));

                            novaData.set(ano, (mes-1), dia);
                            
                            if(novaData.after(dataAtual)){
                            
                                int [] quantidades = new int[tabelaVendaProduto.getRowCount()];

                                int quantidadeParcelas =
                                Integer.parseInt(vendaParcela.getSelectedItem().toString().substring(0, 1));

                                Long[] produtosVenda = new Long[tabelaVendaProduto.getRowCount()];

                                for(int i = 0; i < tabelaVendaProduto.getRowCount(); i++){
                                    Long id = Long.parseLong(tabelaVendaProduto.getValueAt(i, 0).toString());
                                    int quantidade = Integer.parseInt(tabelaVendaProduto.getValueAt(i, 3).toString());

                                    quantidades[i] = quantidade;
                                    produtosVenda[i] = id;

                                }

                                Map<String, String> mapFuncionario = ControllerFuncionario.searchFuncionario(logado.getText());
                                Map<String, String> mapPessoa = ControllerPessoa.searchPessoa(vendaDocumentoCliente.getText());
                                ControllerVendas.vender(
                                    Long.valueOf(mapPessoa.get("idPessoa")),
                                    Long.valueOf(mapFuncionario.get("idFuncionario")),
                                    TipoVenda.Parcelado,
                                    produtosVenda,
                                    quantidades,
                                    Double.parseDouble(descontoVenda.getValue().toString()),
                                    quantidadeParcelas,
                                    dia,
                                    mes,
                                    ano
                                );

                                limpaCampos("venda");

                                JOptionPane.showMessageDialog(null, "Venda Realizada com Sucesso!");
                            }
                            else{
                                JOptionPane.showMessageDialog(null, "Data Inválida!");
                            }
                        }
                        else{
                            JOptionPane.showMessageDialog(null,"Informe a data de vencimento da primeira parcela.");
                        }

                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Não foi selecionada opção de pagamento");
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Não existem produtos na compra!");
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Cliente não selecionado!");
        }
    }//GEN-LAST:event_fecharVendaMouseClicked

    private void btnVendaDeleteProdutoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVendaDeleteProdutoMouseClicked
        DecimalFormat df = new DecimalFormat("0.00");
        if (tabelaVendaProduto.getSelectedRow() >= 0){
            if(Integer.parseInt(tabelaVendaProduto.getValueAt(tabelaVendaProduto.getSelectedRow(), 3).toString()) > 1){
                Integer tempQuantidade = Integer.parseInt(tabelaVendaProduto.getValueAt(tabelaVendaProduto.getSelectedRow(), 3).toString());
                Double tempValor = Double.parseDouble(tabelaVendaProduto.getValueAt(tabelaVendaProduto.getSelectedRow(), 2).toString().replace(",", "."));
                ((DefaultTableModel)tabelaVendaProduto.getModel()).setValueAt(tempQuantidade-1, tabelaVendaProduto.getSelectedRow(), 3);
                ((DefaultTableModel)tabelaVendaProduto.getModel()).setValueAt(df.format(tempValor * (tempQuantidade-1)),tabelaVendaProduto.getSelectedRow(), 4);
            }
            else{
                ((DefaultTableModel)tabelaVendaProduto.getModel()).removeRow(tabelaVendaProduto.getSelectedRow());
            }

            setValorQuantidadeVenda();
        }
        else{
            JOptionPane.showMessageDialog(null, "Selecione um produto na lista para remover da compra.");
        }

        if(quantidadeVendaProduto.getText().equals("0")){
            estiloPadrao();
        }
    }//GEN-LAST:event_btnVendaDeleteProdutoMouseClicked

    private void btnVendaBuscaProdutoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVendaBuscaProdutoMouseClicked
        Long id = Long.parseLong(vendaIdProduto.getText());

        try{
            Map mapProduto = ControllerProduto.searchProduto(id);

            DecimalFormat df = new DecimalFormat("0.00");
            boolean existe = false;

            Integer quantidade = 1;

            if(Integer.parseInt(mapProduto.get("quantidade").toString()) > 0){

                /*Verificar se produto esta na tabela*/
                int linhas = tabelaVendaProduto.getRowCount();

                if(linhas > 0){
                    for(int i = 0; i < linhas; i++){
                        if(tabelaVendaProduto.getValueAt(i, 0).equals(vendaIdProduto.getText())){
                            quantidade = Integer.valueOf(tabelaVendaProduto.getValueAt(i, 3).toString());
                            if((quantidade+1) <= Integer.parseInt(mapProduto.get("quantidade").toString())){
                                ((DefaultTableModel)tabelaVendaProduto.getModel()).setValueAt(quantidade+1,i, 3);
                                ((DefaultTableModel)tabelaVendaProduto.getModel()).setValueAt(
                                    df.format(
                                        Double.parseDouble(
                                            mapProduto.get("valorUnitario").toString()
                                        ) * (quantidade+1)
                                    ),i, 4);
                                    existe = true;
                                    break;
                                }
                                else{
                                    JOptionPane.showMessageDialog(null, "Estoque insuficiente.");
                                    existe = true;
                                }
                            }
                        }

                        if(!existe){
                            ((DefaultTableModel)tabelaVendaProduto.getModel()).addRow(new String[]{
                                mapProduto.get("idProduto").toString(),
                                String.valueOf(mapProduto.get("descricao")),
                                df.format(Double.parseDouble(mapProduto.get("valorUnitario").toString())),
                                quantidade.toString(),
                                df.format(Double.parseDouble(mapProduto.get("valorUnitario").toString()) * quantidade)
                            });
                        }
                    }
                    else{
                        ((DefaultTableModel)tabelaVendaProduto.getModel()).addRow(new String[]{
                            mapProduto.get("idProduto").toString(),
                            String.valueOf(mapProduto.get("descricao")),
                            df.format(Double.parseDouble(mapProduto.get("valorUnitario").toString())),
                            quantidade.toString(),
                            df.format(Double.parseDouble(mapProduto.get("valorUnitario").toString()) * quantidade)
                        });
                    }

                    setValorQuantidadeVenda();
                }
                else{
                    JOptionPane.showMessageDialog(null, "Produto Fora de Estoque!");
                    vendaIdProduto.setText(null);
                }
            }
            catch(NullPointerException e){
                JOptionPane.showMessageDialog(null, "Produto não encontrado");
                vendaIdProduto.setText(null);
            }
    }//GEN-LAST:event_btnVendaBuscaProdutoMouseClicked

    private void btnBuscaClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscaClienteMouseClicked
        if(!buscaCliente.getText().equals("")){
            try{
                filtraTabela("cliente");
            }
            catch(NoResultException e){
                JOptionPane.showMessageDialog(null, "Nenhum resultado para a busca");
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Informe o nome do cliente");
            buscaCliente.requestFocus();
        }
        buscaCliente.setText(null);
    }//GEN-LAST:event_btnBuscaClienteMouseClicked

    private void btnBuscaProdutoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscaProdutoMouseClicked
        if(!descricaoProdutoBusca.getText().equals("")){
            try{
                filtraTabela("produto");
            }
            catch(NoResultException e){
                JOptionPane.showMessageDialog(null, "Nenhum resultado para a busca");
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Informe o nome do produto");
            descricaoProdutoBusca.requestFocus();
        }
        descricaoProdutoBusca.setText(null);
    }//GEN-LAST:event_btnBuscaProdutoMouseClicked

    private void confirmProdutoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_confirmProdutoMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_confirmProdutoMouseEntered

    private void btnBuscaNomeFornecedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscaNomeFornecedorMouseClicked
        if(!nomeBuscaFornecedor.getText().equals("")){
            try{
                filtraTabela("fornecedor");
            }
            catch(NoResultException e){
                JOptionPane.showMessageDialog(null, "Nenhum resultado para a busca");
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Informe o nome do fornecedor");
            nomeBuscaFornecedor.requestFocus();
        }
        nomeBuscaFornecedor.setText(null);
    }//GEN-LAST:event_btnBuscaNomeFornecedorMouseClicked

    private void btnBuscaUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscaUsuarioMouseClicked
        if(!nomeBuscaUsuario.getText().equals("")){
            try{
                filtraTabela("usuario");
            }
            catch(NoResultException e){
                JOptionPane.showMessageDialog(null, "Nenhum resultado para a busca");
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Informe o nome do usuário");
            nomeBuscaUsuario.requestFocus();
        }
        nomeBuscaUsuario.setText(null);
    }//GEN-LAST:event_btnBuscaUsuarioMouseClicked

    public void filtraTabela(String menu) {
        List<Map<String, String>> lista;
        
        if(menu.equals("cliente")){
            lista = ControllerPessoa.searchPessoaNome(buscaCliente.getText());
            ((DefaultTableModel)tabelaCliente.getModel()).setNumRows(0);
            
            for(int i = 0; i < lista.size(); i++){
                
                ((DefaultTableModel)tabelaCliente.getModel()).addRow(new String[]{
                    lista.get(i).get("idPessoa"),
                    lista.get(i).get("nome")
                });
                
                confirmCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/checked (1).png")));
                deleteCliente.setEnabled(false);
            }
        }
        else{
            if(menu.equals("produto")){
                lista = ControllerProduto.searchProduto(descricaoProdutoBusca.getText());
                ((DefaultTableModel)tabelaProduto.getModel()).setNumRows(0);

                for(int i = 0; i < lista.size(); i++){

                    ((DefaultTableModel)tabelaProduto.getModel()).addRow(new String[]{
                        lista.get(i).get("id"),
                        lista.get(i).get("descricao"),
                        lista.get(i).get("valorUnitario"),
                        lista.get(i).get("quantidade"),
                        lista.get(i).get("total")
                    });

                    confirmProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/checked (1).png")));
                    deleteProduto.setEnabled(false);
                }
            }
            else{
                if(menu.equals("fornecedor")){
                    lista = ControllerPessoa.searchFornecedor(nomeBuscaFornecedor.getText());
                    ((DefaultTableModel)tabelaFornecedor.getModel()).setNumRows(0);

                    for(int i = 0; i < lista.size(); i++){

                        ((DefaultTableModel)tabelaFornecedor.getModel()).addRow(new String[]{
                            lista.get(i).get("id"),
                            lista.get(i).get("nome")
                        });

                        confirmFornecedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/checked (1).png")));
                        deleteFornecedor.setEnabled(false);
                    }
                }
                else{
                    if(menu.equals("usuario")){
                        lista = ControllerFuncionario.searchUsuario(nomeBuscaUsuario.getText());
                        ((DefaultTableModel)tabelaUsuario.getModel()).setNumRows(0);

                        for(int i = 0; i < lista.size(); i++){

                            ((DefaultTableModel)tabelaUsuario.getModel()).addRow(new String[]{
                                lista.get(i).get("id"),
                                lista.get(i).get("nome")
                            });

                            confirmUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/checked (1).png")));
                            deleteUsuario.setEnabled(false);
                        }
                    }
                }
            }
        }
    }

    public void setValorQuantidadeVenda() throws NumberFormatException {
        DecimalFormat df = new DecimalFormat("0.00");
        int linhas;
        Integer quantidadeGeral = 0;
        Double valorGeral = 0.0;
        
        /*Setar quantidade geral e valor geral*/
        linhas = tabelaVendaProduto.getRowCount();
        for(int i = 0; i < linhas; i++){
            quantidadeGeral += Integer.parseInt(tabelaVendaProduto.getValueAt(i, 3).toString());
            valorGeral += Double.parseDouble(tabelaVendaProduto.getValueAt(i, 4).toString().replace(",", "."));
        }
        totalVendaProduto.setText(df.format(valorGeral));
        quantidadeVendaProduto.setText(quantidadeGeral.toString());
        vendaIdProduto.setText(null);
    }
    
    private static BufferedImage resizeImage(BufferedImage originalImage, int type){
	BufferedImage resizedImage = new BufferedImage(200, 200, type);
	Graphics2D g = resizedImage.createGraphics();
	g.drawImage(originalImage, 0, 0, 200, 200, null);
	g.dispose();

	return resizedImage;
    }
    
    private static String getExtensaoArquivo(File file){
        String nomeArquivo = file.getName();
        if(nomeArquivo.lastIndexOf(".") != -1 && nomeArquivo.lastIndexOf(".") != 0){
            return nomeArquivo.substring(nomeArquivo.lastIndexOf(".")+1);
        }
        else {
            return "";
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Principal().setVisible(true);
                } 
                catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao carregar o software\nEntre em contato com o administrador");
                } 
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> acessoUsuario;
    private javax.swing.JLabel avisoCompra1;
    private javax.swing.JLabel avisoCompra2;
    private javax.swing.JLabel avisoProduto1;
    private javax.swing.JLabel avisoProduto2;
    private javax.swing.JLabel avisoVenda1;
    private javax.swing.JLabel avisoVenda2;
    private javax.swing.JTextField bairroCliente;
    private javax.swing.JTextField bairroFornecedor;
    private javax.swing.JTextField bairroUsuario;
    private javax.swing.JButton btnBuscaCliente;
    private javax.swing.JButton btnBuscaFornecedor;
    private javax.swing.JButton btnBuscaNomeFornecedor;
    private javax.swing.JButton btnBuscaProduto;
    private javax.swing.JButton btnBuscaUsuario;
    private javax.swing.JButton btnBuscaVendaCliente;
    private javax.swing.JButton btnCliente;
    private javax.swing.JButton btnCompra;
    private javax.swing.JButton btnFornecedor;
    private javax.swing.JButton btnInsereProduto;
    private javax.swing.JButton btnProduto;
    private javax.swing.JButton btnRelatorio;
    private javax.swing.JButton btnRelatorio1;
    private javax.swing.JButton btnRelatorio10;
    private javax.swing.JButton btnRelatorio11;
    private javax.swing.JButton btnRelatorio2;
    private javax.swing.JButton btnRelatorio3;
    private javax.swing.JButton btnRelatorio4;
    private javax.swing.JButton btnRelatorio5;
    private javax.swing.JButton btnRelatorio6;
    private javax.swing.JButton btnRelatorio7;
    private javax.swing.JButton btnRelatorio8;
    private javax.swing.JButton btnRelatorio9;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnUsuario;
    private javax.swing.JButton btnVenda;
    private javax.swing.JButton btnVendaBuscaProduto;
    private javax.swing.JButton btnVendaDeleteProduto;
    private javax.swing.JTextField buscaCliente;
    private javax.swing.JButton calculaTrocoVenda;
    private javax.swing.JTextField cepCliente;
    private javax.swing.JTextField cepFornecedor;
    private javax.swing.JTextField cepUsuario;
    private javax.swing.JCheckBox checkSenha;
    private javax.swing.JCheckBox checkVendaClienteCadastrado;
    private javax.swing.JTextField cidadeCliente;
    private javax.swing.JTextField cidadeFornecedor;
    private javax.swing.JTextField cidadeUsuario;
    private javax.swing.JPanel clientes;
    private javax.swing.JTextField compraQuantidadeTotal;
    private javax.swing.JTextField compraValorTotal;
    private javax.swing.JPanel compras;
    private javax.swing.JButton confirmCliente;
    private javax.swing.JButton confirmFornecedor;
    private javax.swing.JButton confirmProduto;
    private javax.swing.JButton confirmUsuario;
    private javax.swing.JTextField dataNascimentoCliente;
    private javax.swing.JTextField dataNascimentoUsuario;
    private javax.swing.JFormattedTextField dataVencimentoParcela;
    private javax.swing.JTextField dddCliente;
    private javax.swing.JTextField dddCliente2;
    private javax.swing.JTextField dddFornecedor;
    private javax.swing.JTextField dddFornecedor2;
    private javax.swing.JTextField dddUsuario;
    private javax.swing.JTextField dddUsuario2;
    private javax.swing.JButton deleteCliente;
    private javax.swing.JButton deleteFornecedor;
    private javax.swing.JButton deleteProduto;
    private javax.swing.JButton deleteUsuario;
    private javax.swing.JSpinner descontoVenda;
    private javax.swing.JTextArea descricaoProduto;
    private javax.swing.JTextField descricaoProdutoBusca;
    private javax.swing.JTextField documentoCliente;
    private javax.swing.JTextField documentoFornecedor;
    private javax.swing.JFormattedTextField documentoFornecedorBusca;
    private javax.swing.JTextField documentoUsuario;
    private javax.swing.JLabel eagles;
    private javax.swing.JTextField emailCliente;
    private javax.swing.JTextField emailFornecedor;
    private javax.swing.JTextField emailUsuario;
    private javax.swing.JComboBox<String> estadoCliente;
    private javax.swing.JComboBox<String> estadoFornecedor;
    private javax.swing.JComboBox<String> estadoUsuario;
    private javax.swing.JButton fecharVenda;
    private javax.swing.JToolBar ferramentas;
    private javax.swing.JButton finalizaCompra;
    private javax.swing.JPanel fornecedores;
    private javax.swing.JLabel imagemProduto;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JLabel labelAcessoUsuario;
    private javax.swing.JLabel labelBairroCliente;
    private javax.swing.JLabel labelBairroFornecedor;
    private javax.swing.JLabel labelBairroUsuario;
    private javax.swing.JLabel labelCaminhoArquivo;
    private javax.swing.JLabel labelCepCliente;
    private javax.swing.JLabel labelCepFornecedor;
    private javax.swing.JLabel labelCepUsuario;
    private javax.swing.JLabel labelCidadeCliente;
    private javax.swing.JLabel labelCidadeFornecedor;
    private javax.swing.JLabel labelCidadeUsuario;
    private javax.swing.JPanel labelCompraFornecedor;
    private javax.swing.JLabel labelCompraQuantidadeTotal;
    private javax.swing.JLabel labelCompraValorTotal;
    private javax.swing.JLabel labelDataNascimentoCliente;
    private javax.swing.JLabel labelDataNascimentoUsuario;
    private javax.swing.JLabel labelDataVencimentoParcela;
    private javax.swing.JLabel labelDescricaoProduto;
    private javax.swing.JLabel labelDocumentoCliente;
    private javax.swing.JLabel labelDocumentoFornecedor;
    private javax.swing.JLabel labelDocumentoUsuario;
    private javax.swing.JLabel labelEmailCliente;
    private javax.swing.JLabel labelEmailFornecedor;
    private javax.swing.JLabel labelEmailUsuario;
    private javax.swing.JLabel labelEstadoCliente;
    private javax.swing.JLabel labelEstadoFornecedor;
    private javax.swing.JLabel labelEstadoUsuario;
    private javax.swing.JLabel labelLoginUsuario;
    private javax.swing.JLabel labelLogradouroCliente;
    private javax.swing.JLabel labelLogradouroFornecedor;
    private javax.swing.JLabel labelLogradouroUsuario;
    private javax.swing.JLabel labelNomeArquivo;
    private javax.swing.JLabel labelNomeBuscaCliente;
    private javax.swing.JLabel labelNomeCliente;
    private javax.swing.JLabel labelNomeFornecedor;
    private javax.swing.JLabel labelNomeFornecedorBusca;
    private javax.swing.JLabel labelNomeUsuario;
    private javax.swing.JLabel labelNomeUsuarioBusca;
    private javax.swing.JLabel labelNumeroCliente;
    private javax.swing.JLabel labelNumeroFornecedor;
    private javax.swing.JLabel labelNumeroUsuario;
    private javax.swing.JLabel labelPagoVenda;
    private javax.swing.JLabel labelParcelasVenda;
    private javax.swing.JLabel labelPorcentagemLucro2;
    private javax.swing.JLabel labelPorcetagemLucro;
    private javax.swing.JLabel labelPorcetagemVenda;
    private javax.swing.JLabel labelPrecoProduto;
    private javax.swing.JLabel labelQuantidadeProduto;
    private javax.swing.JLabel labelQuantidadeVendaProduto;
    private javax.swing.JLabel labelSenhaUsuario;
    private javax.swing.JLabel labelSexoCliente;
    private javax.swing.JLabel labelSexoUsuario;
    private javax.swing.JLabel labelTelefoneCliente;
    private javax.swing.JLabel labelTelefoneCliente2;
    private javax.swing.JLabel labelTelefoneFornecedor;
    private javax.swing.JLabel labelTelefoneFornecedor2;
    private javax.swing.JLabel labelTelefoneUsuario;
    private javax.swing.JLabel labelTelefoneUsuario2;
    private javax.swing.JLabel labelTotalVenda;
    private javax.swing.JLabel labelTrocoVenda;
    private javax.swing.JLabel labelValorTotalVenda;
    private javax.swing.JLabel labelVendaDesconto;
    private javax.swing.JLabel logado;
    private javax.swing.JTextField loginUsuario;
    private javax.swing.JLabel logo;
    private javax.swing.JTextField logradouroCliente;
    private javax.swing.JTextField logradouroFornecedor;
    private javax.swing.JTextField logradouroUsuario;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuPrincipal;
    private javax.swing.JTextField nomeBuscaFornecedor;
    private javax.swing.JTextField nomeBuscaUsuario;
    private javax.swing.JTextField nomeCliente;
    private javax.swing.JTextField nomeFornecedor;
    private javax.swing.JTextField nomeUsuario;
    private javax.swing.JTextField numeroCliente;
    private javax.swing.JTextField numeroFornecedor;
    private javax.swing.JTextField numeroUsuario;
    private javax.swing.ButtonGroup opcoesPagamento;
    private javax.swing.JPanel panelCliente;
    private javax.swing.JPanel panelClienteDadoPessoal;
    private javax.swing.JPanel panelClienteEndereco;
    private javax.swing.JPanel panelCompraProduto;
    private javax.swing.JPanel panelConfirmaCompra;
    private javax.swing.JPanel panelDadosFornecedor;
    private javax.swing.JPanel panelDataHora;
    private javax.swing.JPanel panelEnderecoFornecedor;
    private javax.swing.JPanel panelFornecedor;
    private javax.swing.JPanel panelProduto;
    private javax.swing.JPanel panelProdutoCadastro;
    private javax.swing.JPanel panelRelatorio;
    private javax.swing.JPanel panelRelatorioCrediario;
    private javax.swing.JPanel panelRelatorioGerencial;
    private javax.swing.JPanel panelRelatorioLista;
    private javax.swing.JPanel panelRelatorioVenda;
    private javax.swing.JPanel panelUsuario;
    private javax.swing.JPanel panelUsuarioAcesso;
    private javax.swing.JPanel panelUsuarioDadoPessoal;
    private javax.swing.JPanel panelUsuarioEndereco;
    private javax.swing.JPanel panelUsuarios;
    private javax.swing.JPanel panelVendaCliente;
    private javax.swing.JPanel panelVendaMetodoPagamento;
    private javax.swing.JPanel panelVendaPagamentoParcelado;
    private javax.swing.JPanel panelVendaPagamentoVista;
    private javax.swing.JPanel panelVendaProduto;
    private javax.swing.JSpinner porcentagemLucro;
    private javax.swing.JTextField precoProduto;
    private javax.swing.JPanel produtos;
    private javax.swing.JSpinner quantidadeProduto;
    private javax.swing.JTextField quantidadeVendaProduto;
    private javax.swing.JRadioButton radioVendaParcelado;
    private javax.swing.JRadioButton radioVendaVista;
    private javax.swing.JButton refreshCliente;
    private javax.swing.JButton refreshFornecedor;
    private javax.swing.JButton refreshProduto;
    private javax.swing.JButton refreshUsuario;
    private javax.swing.JPanel relatorios;
    private javax.swing.JPasswordField senhaUsuario;
    private javax.swing.JComboBox<String> sexoCliente;
    private javax.swing.JComboBox<String> sexoUsuario;
    private javax.swing.JTable tabelaCliente;
    private javax.swing.JTable tabelaCompra;
    private javax.swing.JTable tabelaFornecedor;
    private javax.swing.JTable tabelaProduto;
    private javax.swing.JTable tabelaUsuario;
    private javax.swing.JTable tabelaVendaProduto;
    private javax.swing.JTextField telefoneCliente;
    private javax.swing.JTextField telefoneCliente2;
    private javax.swing.JTextField telefoneFornecedor;
    private javax.swing.JTextField telefoneFornecedor2;
    private javax.swing.JTextField telefoneUsuario;
    private javax.swing.JTextField telefoneUsuario2;
    private javax.swing.JTextField totalVendaProduto;
    private javax.swing.JPanel usuarios;
    private javax.swing.JTextField valorPagoVenda;
    private javax.swing.JTextField valorTotalVenda;
    private javax.swing.JTextField valorTrocoVenda;
    private javax.swing.JFormattedTextField vendaDocumentoCliente;
    private javax.swing.JTextField vendaIdProduto;
    private javax.swing.JTextField vendaNomeCliente;
    private javax.swing.JComboBox<String> vendaParcela;
    private javax.swing.JPanel vendas;
    // End of variables declaration//GEN-END:variables
}
