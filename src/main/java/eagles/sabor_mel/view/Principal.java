package eagles.sabor_mel.view;

import eagles.sabor_mel.control.ControllerFuncionario;
import eagles.sabor_mel.control.ControllerPessoa;
import eagles.sabor_mel.control.ControllerProduto;
import eagles.sabor_mel.control.ControllerVendas;
import eagles.sabor_mel.control.HashSha;
import eagles.sabor_mel.control.Mensagem;
import eagles.sabor_mel.control.Validacao;
import eagles.sabor_mel.dao.BairroDAO;
import eagles.sabor_mel.dao.CidadeDAO;
import eagles.sabor_mel.dao.DocumentoDAO;
import eagles.sabor_mel.dao.EnderecoDAO;
import eagles.sabor_mel.dao.EstadoDAO;
import eagles.sabor_mel.dao.FuncionarioDAO;
import eagles.sabor_mel.dao.PessoaDAO;
import eagles.sabor_mel.dao.ProdutoDAO;
import eagles.sabor_mel.dao.TelefoneDAO;
import eagles.sabor_mel.model.Acesso;
import eagles.sabor_mel.model.Bairro;
import eagles.sabor_mel.model.Cidade;
import eagles.sabor_mel.model.Documento;
import eagles.sabor_mel.model.Endereco;
import eagles.sabor_mel.model.Estado;
import eagles.sabor_mel.model.Funcionario;
import eagles.sabor_mel.model.Pessoa;
import eagles.sabor_mel.model.Produto;
import eagles.sabor_mel.model.Sexo;
import eagles.sabor_mel.model.Telefone;
import eagles.sabor_mel.model.TipoDocumento;
import eagles.sabor_mel.model.TipoTelefone;
import eagles.sabor_mel.model.TipoVenda;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

/**
 *
 * @author etivideo
 */
public class Principal extends javax.swing.JFrame {
    /*Variavel global para definir as ações do operador*/
    public static String acao = "";
    
    /*Variavel para definir a opção selecionada do menu*/
    public static String menu = "";
    
    /*Variavel para definir o caminho da imagem*/
    public static String caminhoArquivo = "";
    public static String nomeArquivo = "";
    
    /*Variavel para definir valor total da venda*/
    public static Double totalParcialVenda = 0.0;
    
    /**
     * Creates new form Principal
     */
    public Principal() {
        //Login.permitir = true;

        if(Login.permitir){
            initComponents();
            if(Login.nivelAcesso.equals("Vendedor")){
                btnCompra.setEnabled(false);
                btnRelatorio.setEnabled(false);
                btnFornecedor.setEnabled(false);
                btnUsuario.setEnabled(false);
            }
            logado.setText("Usuário: "+Login.nome);
            this.setExtendedState(this.MAXIMIZED_BOTH); 
            carregaComboEstados();
            //carregaComboFornecedores();
            carregaDados();
            redefineEstilo();
        }
        else{
            this.dispose();
            JOptionPane.showMessageDialog(null, "Permissão Negada.... ");
            new Login().setVisible(true);
            //System.exit(0);
        }
        
        
    }

    public void carregaComboEstados() {
        EstadoDAO estadoDAO = new EstadoDAO();
        List<Estado> listDaoEstados = estadoDAO.findAll();
        
        for(int i = 0; i < listDaoEstados.size(); i++){
            estados.addItem(listDaoEstados.get(i).getUf());
            estadosCliente.addItem(listDaoEstados.get(i).getUf());
            estadosFornecedor.addItem(listDaoEstados.get(i).getUf());
        }
    }
    
    public void redefineEstilo() {
        acessos.setBackground(Color.white);
        estados.setBackground(Color.white);
        sexo.setBackground(Color.white);
        parcelas.setBackground(Color.white);
        
        
        checkSenha.setEnabled(false);
        delete.setEnabled(false);
        confirm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/checked (1).png")));
        labelTelefone2.setEnabled(false);
        ddd2.setEnabled(false);
        telefone2.setEnabled(false);
        delTel2.setEnabled(false);
        labelTelefone3.setEnabled(false);
        ddd3.setEnabled(false);
        telefone3.setEnabled(false);
        delTel3.setEnabled(false);
        
        /*Cliente*/
        deleteCliente.setEnabled(false);
        confirmCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/checked (1).png")));
        labelTelefone2Cliente.setEnabled(false);
        ddd2Cliente.setEnabled(false);
        telefone2Cliente.setEnabled(false);
        delTel2Cliente.setEnabled(false);
        labelTelefone3Cliente.setEnabled(false);
        ddd3Cliente.setEnabled(false);
        telefone3Cliente.setEnabled(false);
        delTel3Cliente.setEnabled(false);
        
        /*Fornecedor*/
        deleteFornecedor.setEnabled(false);
        confirmFornecedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/checked (1).png")));
        labelTelefone2Fornecedor.setEnabled(false);
        ddd2Fornecedor.setEnabled(false);
        telefone2Fornecedor.setEnabled(false);
        delTel2Fornecedor.setEnabled(false);
        labelTelefone3Fornecedor.setEnabled(false);
        ddd3Fornecedor.setEnabled(false);
        telefone3Fornecedor.setEnabled(false);
        delTel3Fornecedor.setEnabled(false);
        
        /*Produtos*/
        deleteProduto.setEnabled(false);
        
        /*Vendas*/
        opcoesPagamento.clearSelection();
        /**/
        
        
        labelParcelasVenda.setEnabled(false);
        parcelas.setEnabled(false);
        labelDataVencimentoParcela.setEnabled(false);
        dataVencimentoParcela.setEnabled(false);
        
        /**/
        
        
        labelPagoVenda.setEnabled(false);
        labelTotalVenda.setEnabled(false);
        labelTrocoVenda.setEnabled(false);
        valorPagoVenda.setEnabled(false);
        valorTotalVenda.setEnabled(false);
        valorTrocoVenda.setEnabled(false);
        calculaTrocoVenda.setEnabled(false);
    }

    public void carregaDados() {
        
        /*Funcionarios*/
        FuncionarioDAO dao = new FuncionarioDAO();
        List<Funcionario> funcionarios = dao.findAll();
        ((DefaultTableModel)tabelaUsuario.getModel()).setNumRows(0);
        for(int i = 0; i < funcionarios.size(); i++){
            String acesso = funcionarios.get(i).getAcesso().toString();
            
            ((DefaultTableModel)tabelaUsuario.getModel()).addRow(new String[]{
                funcionarios.get(i).getIdPessoa().toString(),
                funcionarios.get(i).getNome(),
                funcionarios.get(i).getUsuario(), 
                acesso
            });
            
            confirm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/checked (1).png")));
            delete.setEnabled(false);
        }
        
        /*Clientes*/
        PessoaDAO pesDAO = new PessoaDAO();
        List<Pessoa> listClientes = pesDAO.findAll();
        ((DefaultTableModel)tabelaCliente.getModel()).setNumRows(0);
        for(int i = 0; i < listClientes.size(); i++){
            if(listClientes.get(i).getDocumento().getTipo().toString().equals("CPF")){
                ((DefaultTableModel)tabelaCliente.getModel()).addRow(new String[]{
                    listClientes.get(i).getIdPessoa().toString(),
                    listClientes.get(i).getNome()
                });
            }
            
            confirmCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/checked (1).png")));
            deleteCliente.setEnabled(false);
        }
        
        /*Fornecedores*/
        PessoaDAO fornecedorDAO = new PessoaDAO();
        List<Pessoa> listFornecedores = fornecedorDAO.findAll();
        ((DefaultTableModel)tabelaFornecedor.getModel()).setNumRows(0);
        for(int i = 0; i < listFornecedores.size(); i++){
            if(listFornecedores.get(i).getDocumento().getTipo().toString().equals("CNPJ")){
                ((DefaultTableModel)tabelaFornecedor.getModel()).addRow(new String[]{
                    listClientes.get(i).getIdPessoa().toString(),
                    listClientes.get(i).getNome()
                });
            }
            
            confirmFornecedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/checked (1).png")));
            deleteFornecedor.setEnabled(false);
        }
        
        /*Produtos*/
        ProdutoDAO produtoDAO = new ProdutoDAO();
        List<Produto> listProdutos = produtoDAO.findAll();
        ((DefaultTableModel)tabelaProduto.getModel()).setNumRows(0);
        for(int i = 0; i < listProdutos.size(); i++){
            Double total = listProdutos.get(i).getQuantidade() * listProdutos.get(i).getValorUnitario();
            ((DefaultTableModel)tabelaProduto.getModel()).addRow(new String[]{
                listProdutos.get(i).getIdProduto().toString(),
                listProdutos.get(i).getDescricao(),
                listProdutos.get(i).getValorUnitario().toString(),
                listProdutos.get(i).getQuantidade().toString(),
                total.toString() 
            });
            
            confirmProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/checked (1).png")));
            deleteProduto.setEnabled(false);
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
        jPanel1 = new javax.swing.JPanel();
        radioVendaVista = new javax.swing.JRadioButton();
        radioVendaParcelado = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        labelPagoVenda = new javax.swing.JLabel();
        valorPagoVenda = new javax.swing.JTextField();
        valorTotalVenda = new javax.swing.JTextField();
        labelTotalVenda = new javax.swing.JLabel();
        labelTrocoVenda = new javax.swing.JLabel();
        valorTrocoVenda = new javax.swing.JTextField();
        calculaTrocoVenda = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        labelParcelasVenda = new javax.swing.JLabel();
        parcelas = new javax.swing.JComboBox<>();
        dataVencimentoParcela = new javax.swing.JFormattedTextField();
        labelDataVencimentoParcela = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        checkClienteCadastrado = new javax.swing.JCheckBox();
        btnBuscaDocumentoCliente = new javax.swing.JButton();
        buscaDocumentoCliente = new javax.swing.JFormattedTextField();
        nomeBuscaCliente = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        codigoProduto = new javax.swing.JTextField();
        procuraProduto = new javax.swing.JButton();
        deleteProdutoVenda = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tabelaVendaProduto = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        totalVendaProduto = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        descontoVenda = new javax.swing.JSpinner();
        jLabel60 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        quantidadeVendaProduto = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        clientes = new javax.swing.JPanel();
        refreshCliente = new javax.swing.JButton();
        deleteCliente = new javax.swing.JButton();
        confirmCliente = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelaCliente = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        nomeCliente = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        emailCliente = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        dataNascimentoCliente = new javax.swing.JTextField();
        try{ 
            javax.swing.text.MaskFormatter mask = new javax.swing.text.MaskFormatter("##/##/####");
            dataNascimentoCliente = new javax.swing.JFormattedTextField(mask);
        }
        catch (Exception e){
        }
        jLabel31 = new javax.swing.JLabel();
        documentoCliente = new javax.swing.JTextField();
        try{ 
            javax.swing.text.MaskFormatter mask = new javax.swing.text.MaskFormatter("###.###.###-##");
            documentoCliente = new javax.swing.JFormattedTextField(mask);
        }
        catch (Exception e){
        }
        jLabel35 = new javax.swing.JLabel();
        sexoCliente = new javax.swing.JComboBox<>();
        jLabel32 = new javax.swing.JLabel();
        dddCliente = new javax.swing.JTextField();
        try{ 
            javax.swing.text.MaskFormatter mask = new javax.swing.text.MaskFormatter("(##)");
            dddCliente = new javax.swing.JFormattedTextField(mask);
        }
        catch (Exception e){
        }
        telefoneCliente = new javax.swing.JTextField();
        addTelCliente = new javax.swing.JButton();
        labelTelefone2Cliente = new javax.swing.JLabel();
        ddd2Cliente = new javax.swing.JTextField();
        try{ 
            javax.swing.text.MaskFormatter mask = new javax.swing.text.MaskFormatter("(##)");
            ddd2Cliente = new javax.swing.JFormattedTextField(mask);
        }
        catch (Exception e){
        }
        telefone2Cliente = new javax.swing.JTextField();
        delTel2Cliente = new javax.swing.JButton();
        labelTelefone3Cliente = new javax.swing.JLabel();
        ddd3Cliente = new javax.swing.JTextField();
        try{ 
            javax.swing.text.MaskFormatter mask = new javax.swing.text.MaskFormatter("(##)");
            ddd3Cliente = new javax.swing.JFormattedTextField(mask);
        }
        catch (Exception e){
        }
        telefone3Cliente = new javax.swing.JTextField();
        delTel3Cliente = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        cepCliente = new javax.swing.JTextField();
        try{ 
            javax.swing.text.MaskFormatter mask = new javax.swing.text.MaskFormatter("#####-###");
            cepCliente = new javax.swing.JFormattedTextField(mask);
        }
        catch (Exception e){
        }
        jLabel38 = new javax.swing.JLabel();
        logradouroCliente = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        numeroCliente = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        bairroCliente = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        cidadeCliente = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        estadosCliente = new javax.swing.JComboBox<>();
        produtos = new javax.swing.JPanel();
        deleteProduto = new javax.swing.JButton();
        confirmProduto = new javax.swing.JButton();
        refreshProduto = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelaProduto = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        imagemProduto = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        descricaoProduto = new javax.swing.JTextArea();
        jLabel54 = new javax.swing.JLabel();
        quantidadeProduto = new javax.swing.JSpinner();
        jLabel55 = new javax.swing.JLabel();
        precoProduto = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        compras = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        relatorios = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        fornecedores = new javax.swing.JPanel();
        refreshFornecedor = new javax.swing.JButton();
        deleteFornecedor = new javax.swing.JButton();
        confirmFornecedor = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelaFornecedor = new javax.swing.JTable();
        jPanel13 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        documentoFornecedor = new javax.swing.JTextField();
        try{ 
            javax.swing.text.MaskFormatter mask = new javax.swing.text.MaskFormatter("##.###.###/####-##");
            documentoFornecedor = new javax.swing.JFormattedTextField(mask);
        }
        catch (Exception e){
        }
        nomeFornecedor = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        emailFornecedor = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        labelTelefone2Fornecedor = new javax.swing.JLabel();
        labelTelefone3Fornecedor = new javax.swing.JLabel();
        ddd3Fornecedor = new javax.swing.JTextField();
        try{ 
            javax.swing.text.MaskFormatter mask = new javax.swing.text.MaskFormatter("(##)");
            ddd3Fornecedor = new javax.swing.JFormattedTextField(mask);
        }
        catch (Exception e){
        }
        ddd2Fornecedor = new javax.swing.JTextField();
        try{ 
            javax.swing.text.MaskFormatter mask = new javax.swing.text.MaskFormatter("(##)");
            ddd2Fornecedor = new javax.swing.JFormattedTextField(mask);
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
        telefone2Fornecedor = new javax.swing.JTextField();
        telefone3Fornecedor = new javax.swing.JTextField();
        delTel3Fornecedor = new javax.swing.JButton();
        delTel2Fornecedor = new javax.swing.JButton();
        addTelefoneFornecedor = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        cepFornecedor = new javax.swing.JTextField();
        try{ 
            javax.swing.text.MaskFormatter mask = new javax.swing.text.MaskFormatter("#####-###");
            cepFornecedor = new javax.swing.JFormattedTextField(mask);
        }
        catch (Exception e){
        }
        jLabel49 = new javax.swing.JLabel();
        logradouroFornecedor = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        numeroFornecedor = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        bairroFornecedor = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        cidadeFornecedor = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        estadosFornecedor = new javax.swing.JComboBox<>();
        usuarios = new javax.swing.JPanel();
        delete = new javax.swing.JButton();
        confirm = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaUsuario = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        nome = new javax.swing.JTextField();
        email = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        sexo = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        documento = new javax.swing.JTextField();
        try{ 
            javax.swing.text.MaskFormatter mask = new javax.swing.text.MaskFormatter("###.###.###-##");
            documento = new javax.swing.JFormattedTextField(mask);
        }
        catch (Exception e){
        }
        dataNascimento = new javax.swing.JTextField();
        try{ 
            javax.swing.text.MaskFormatter mask = new javax.swing.text.MaskFormatter("##/##/####");
            dataNascimento = new javax.swing.JFormattedTextField(mask);
        }
        catch (Exception e){
        }
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        labelTelefone2 = new javax.swing.JLabel();
        labelTelefone3 = new javax.swing.JLabel();
        ddd3 = new javax.swing.JTextField();
        try{ 
            javax.swing.text.MaskFormatter mask = new javax.swing.text.MaskFormatter("(##)");
            ddd3 = new javax.swing.JFormattedTextField(mask);
        }
        catch (Exception e){
        }
        ddd2 = new javax.swing.JTextField();
        try{ 
            javax.swing.text.MaskFormatter mask = new javax.swing.text.MaskFormatter("(##)");
            ddd2 = new javax.swing.JFormattedTextField(mask);
        }
        catch (Exception e){
        }
        ddd = new javax.swing.JTextField();
        try{ 
            javax.swing.text.MaskFormatter mask = new javax.swing.text.MaskFormatter("(##)");
            ddd = new javax.swing.JFormattedTextField(mask);
        }
        catch (Exception e){
        }
        telefone = new javax.swing.JTextField();
        addTelefone = new javax.swing.JButton();
        delTel2 = new javax.swing.JButton();
        delTel3 = new javax.swing.JButton();
        telefone3 = new javax.swing.JTextField();
        telefone2 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        cep = new javax.swing.JTextField();
        try{ 
            javax.swing.text.MaskFormatter mask = new javax.swing.text.MaskFormatter("#####-###");
            cep = new javax.swing.JFormattedTextField(mask);
        }
        catch (Exception e){
        }
        jLabel16 = new javax.swing.JLabel();
        logradouro = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        numero = new javax.swing.JTextField();
        estados = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        cidade = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        bairro = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        usuario = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        checkSenha = new javax.swing.JCheckBox();
        senha = new javax.swing.JPasswordField();
        jLabel24 = new javax.swing.JLabel();
        acessos = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        logado = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        btnVenda = new javax.swing.JButton();
        btnCliente = new javax.swing.JButton();
        btnProduto = new javax.swing.JButton();
        btnCompra = new javax.swing.JButton();
        btnRelatorio = new javax.swing.JButton();
        btnFornecedor = new javax.swing.JButton();
        btnUsuario = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Método de Pagamento", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radioVendaVista)
                    .addComponent(radioVendaParcelado))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(radioVendaVista)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioVendaParcelado)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Pagamento à Vista"));

        labelPagoVenda.setText("PAGO");

        valorTotalVenda.setEditable(false);

        labelTotalVenda.setText("TOTAL");

        labelTrocoVenda.setText("TROCO");

        valorTrocoVenda.setEditable(false);

        calculaTrocoVenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/equal.png"))); // NOI18N
        calculaTrocoVenda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                calculaTrocoVendaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelPagoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(valorPagoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelTotalVenda)
                    .addComponent(valorTotalVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labelTrocoVenda)
                    .addComponent(valorTrocoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(calculaTrocoVenda)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(calculaTrocoVenda)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(labelPagoVenda)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(valorPagoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(labelTotalVenda)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(valorTotalVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(labelTrocoVenda)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(valorTrocoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Pagamento Parcelado"));

        labelParcelasVenda.setText("PARCELAS");

        try {
            dataVencimentoParcela.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        labelDataVencimentoParcela.setText("VENCIMENTO");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(parcelas, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelParcelasVenda))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelDataVencimentoParcela)
                    .addComponent(dataVencimentoParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelParcelasVenda)
                    .addComponent(labelDataVencimentoParcela))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(parcelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dataVencimentoParcela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        checkClienteCadastrado.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        checkClienteCadastrado.setText("Cliente não cadastrado");
        checkClienteCadastrado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                checkClienteCadastradoMouseClicked(evt);
            }
        });
        checkClienteCadastrado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkClienteCadastradoActionPerformed(evt);
            }
        });

        btnBuscaDocumentoCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/search.png"))); // NOI18N
        btnBuscaDocumentoCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBuscaDocumentoClienteMouseClicked(evt);
            }
        });

        try {
            buscaDocumentoCliente.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        nomeBuscaCliente.setEditable(false);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(checkClienteCadastrado))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(buscaDocumentoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBuscaDocumentoCliente))
                            .addComponent(nomeBuscaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addComponent(checkClienteCadastrado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnBuscaDocumentoCliente)
                    .addComponent(buscaDocumentoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nomeBuscaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Produtos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        procuraProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/search.png"))); // NOI18N
        procuraProduto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                procuraProdutoMouseClicked(evt);
            }
        });

        deleteProdutoVenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete_icon.png"))); // NOI18N
        deleteProdutoVenda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteProdutoVendaMouseClicked(evt);
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

        jLabel7.setText("TOTAL");

        totalVendaProduto.setEditable(false);

        jLabel2.setText("DESCONTO");

        descontoVenda.setModel(new javax.swing.SpinnerNumberModel(0, 0, 100, 1));

        jLabel60.setText("%");

        jLabel58.setText("QUANTIDADE");

        quantidadeVendaProduto.setEditable(false);

        jLabel5.setFont(new java.awt.Font("Dialog", 2, 11)); // NOI18N
        jLabel5.setText("* O desconto somente será aplicado a compras à vista.");

        jLabel6.setFont(new java.awt.Font("Dialog", 2, 11)); // NOI18N
        jLabel6.setText("* Atualize o desconto clicando novamente na opção À Vista, em Método de Pagamento.");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(codigoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(procuraProduto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteProdutoVenda))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(28, 28, 28)
                                .addComponent(totalVendaProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(6, 6, 6)
                                .addComponent(descontoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel60)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel58)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(quantidadeVendaProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(deleteProdutoVenda)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(codigoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(procuraProduto)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(totalVendaProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel58)
                    .addComponent(quantidadeVendaProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(descontoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel60))
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout vendasLayout = new javax.swing.GroupLayout(vendas);
        vendas.setLayout(vendasLayout);
        vendasLayout.setHorizontalGroup(
            vendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vendasLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(vendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(vendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(vendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(vendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(fecharVenda))
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        vendasLayout.setVerticalGroup(
            vendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vendasLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(vendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(vendasLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Clientes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

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

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 14, Short.MAX_VALUE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Dados Pessoais", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        jLabel27.setText("Nome");

        jLabel28.setText("E-Mail");

        jLabel30.setText("Data de Nascimento");

        jLabel31.setText("CPF");

        jLabel35.setText("Sexo");

        sexoCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Feminino" }));

        jLabel32.setText("Telefone");

        addTelCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add.png"))); // NOI18N
        addTelCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addTelClienteMouseClicked(evt);
            }
        });

        labelTelefone2Cliente.setText("Telefone");

        delTel2Cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete_icon.png"))); // NOI18N
        delTel2Cliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                delTel2ClienteMouseClicked(evt);
            }
        });

        labelTelefone3Cliente.setText("Telefone");

        delTel3Cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete_icon.png"))); // NOI18N
        delTel3Cliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                delTel3ClienteMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel27)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel28)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(emailCliente)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel31)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(documentoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel35)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(sexoCliente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dataNascimentoCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE))
                        .addContainerGap(17, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(labelTelefone3Cliente)
                                    .addComponent(labelTelefone2Cliente))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(ddd2Cliente)
                                    .addComponent(ddd3Cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addComponent(telefone2Cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(delTel2Cliente))
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addComponent(telefone3Cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(delTel3Cliente))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel32)
                                .addGap(12, 12, 12)
                                .addComponent(dddCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(telefoneCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addTelCliente)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27)
                    .addComponent(jLabel31)
                    .addComponent(documentoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35)
                    .addComponent(sexoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel28)
                        .addComponent(emailCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel30)
                        .addComponent(dataNascimentoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel32)
                        .addComponent(dddCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(telefoneCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(addTelCliente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(delTel3Cliente)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(ddd2Cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(telefone2Cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(labelTelefone2Cliente))
                            .addComponent(delTel2Cliente))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelTelefone3Cliente)
                            .addComponent(ddd3Cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(telefone3Cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Endereço", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        jLabel37.setText("CEP");

        jLabel38.setText("Logradouro");

        jLabel39.setText("N°");

        jLabel40.setText("Bairro");

        jLabel41.setText("Cidade");

        jLabel42.setText("Estado");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel40)
                                .addGap(39, 39, 39)
                                .addComponent(bairroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel37)
                                .addGap(49, 49, 49)
                                .addComponent(cepCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)))
                        .addComponent(jLabel41)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cidadeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel42))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel38)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(logradouroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel39)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(numeroCliente)
                    .addComponent(estadosCliente, 0, 61, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(cepCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(logradouroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39)
                    .addComponent(numeroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(bairroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41)
                    .addComponent(cidadeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42)
                    .addComponent(estadosCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout clientesLayout = new javax.swing.GroupLayout(clientes);
        clientes.setLayout(clientesLayout);
        clientesLayout.setHorizontalGroup(
            clientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(clientesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(clientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, clientesLayout.createSequentialGroup()
                        .addComponent(refreshCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(confirmCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteCliente))
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        clientesLayout.setVerticalGroup(
            clientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(clientesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(clientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(clientesLayout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(clientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(refreshCliente, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(confirmCliente, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(deleteCliente, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(82, Short.MAX_VALUE))
        );

        mainPanel.add(clientes, "clientes");

        deleteProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete.png"))); // NOI18N
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
        });

        refreshProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/refresh-page-option.png"))); // NOI18N
        refreshProduto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refreshProdutoMouseClicked(evt);
            }
        });

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Produtos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        tabelaProduto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome", "R$", "QTD", "Total"
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

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/search.png"))); // NOI18N

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addComponent(jLabel8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(129, 129, 129))
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cadastro de Produto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        imagemProduto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imagemProduto.setText("IMAGEM");
        imagemProduto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        imagemProduto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        imagemProduto.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        imagemProduto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                imagemProdutoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                imagemProdutoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                imagemProdutoMouseExited(evt);
            }
        });

        jLabel56.setText("Descrição");

        descricaoProduto.setColumns(20);
        descricaoProduto.setRows(5);
        jScrollPane5.setViewportView(descricaoProduto);

        jLabel54.setText("Quantidade");

        quantidadeProduto.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        jLabel55.setText("Preço");

        jLabel3.setFont(new java.awt.Font("Dialog", 2, 11)); // NOI18N
        jLabel3.setText("* Apenas imagens no formato png ou jpg são permitidas.");

        jLabel26.setFont(new java.awt.Font("Dialog", 2, 11)); // NOI18N
        jLabel26.setText("* Tamanho recomendado 200x200 px.");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(imagemProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel26)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addComponent(jLabel56)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 476, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel54)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(quantidadeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel55)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(precoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imagemProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel26)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel56)
                .addGap(8, 8, 8)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel54)
                    .addComponent(quantidadeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel55)
                    .addComponent(precoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout produtosLayout = new javax.swing.GroupLayout(produtos);
        produtos.setLayout(produtosLayout);
        produtosLayout.setHorizontalGroup(
            produtosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(produtosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(produtosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(produtosLayout.createSequentialGroup()
                        .addComponent(refreshProduto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(confirmProduto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteProduto)))
                .addContainerGap(224, Short.MAX_VALUE))
        );
        produtosLayout.setVerticalGroup(
            produtosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(produtosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(produtosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(produtosLayout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(produtosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(refreshProduto)
                            .addComponent(deleteProduto)
                            .addComponent(confirmProduto))))
                .addContainerGap(604, Short.MAX_VALUE))
        );

        mainPanel.add(produtos, "produtos");

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel1.setText("PANEL COMPRAS - UNDER CONSTRUCTION...");

        javax.swing.GroupLayout comprasLayout = new javax.swing.GroupLayout(compras);
        compras.setLayout(comprasLayout);
        comprasLayout.setHorizontalGroup(
            comprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(comprasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        comprasLayout.setVerticalGroup(
            comprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(comprasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(975, Short.MAX_VALUE))
        );

        mainPanel.add(compras, "compras");

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel4.setText("PANEL RELATORIOS - UNDER CONSTRUCTION...");

        javax.swing.GroupLayout relatoriosLayout = new javax.swing.GroupLayout(relatorios);
        relatorios.setLayout(relatoriosLayout);
        relatoriosLayout.setHorizontalGroup(
            relatoriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(relatoriosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        relatoriosLayout.setVerticalGroup(
            relatoriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(relatoriosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(975, Short.MAX_VALUE))
        );

        mainPanel.add(relatorios, "relatorios");

        refreshFornecedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/refresh-page-option.png"))); // NOI18N
        refreshFornecedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refreshFornecedorMouseClicked(evt);
            }
        });

        deleteFornecedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete.png"))); // NOI18N
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

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Fornecedores", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

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

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Dados do Fornecedor", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        jLabel33.setText("Nome Fantasia");

        jLabel43.setText("CNPJ");

        jLabel34.setText("E-mail");

        jLabel45.setText("Telefone");

        labelTelefone2Fornecedor.setText("Telefone 2");

        labelTelefone3Fornecedor.setText("Telefone 3");

        delTel3Fornecedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete_icon.png"))); // NOI18N
        delTel3Fornecedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                delTel3FornecedorMouseClicked(evt);
            }
        });

        delTel2Fornecedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete_icon.png"))); // NOI18N
        delTel2Fornecedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                delTel2FornecedorMouseClicked(evt);
            }
        });

        addTelefoneFornecedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add.png"))); // NOI18N
        addTelefoneFornecedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addTelefoneFornecedorMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel33)
                            .addComponent(jLabel43))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(documentoFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nomeFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel34)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(emailFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelTelefone3Fornecedor, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(labelTelefone2Fornecedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ddd3Fornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ddd2Fornecedor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dddFornecedor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(telefone2Fornecedor)
                            .addComponent(telefoneFornecedor)
                            .addComponent(telefone3Fornecedor))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(addTelefoneFornecedor, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(delTel2Fornecedor, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(delTel3Fornecedor, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(267, 267, 267)))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(nomeFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34)
                    .addComponent(emailFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(documentoFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(dddFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(telefoneFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addTelefoneFornecedor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTelefone2Fornecedor)
                    .addComponent(ddd2Fornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(telefone2Fornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(delTel2Fornecedor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTelefone3Fornecedor)
                    .addComponent(ddd3Fornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(telefone3Fornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(delTel3Fornecedor))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Endereço", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        jLabel48.setText("CEP");

        jLabel49.setText("Logradouro");

        jLabel50.setText("Nº");

        jLabel51.setText("Bairro");

        jLabel52.setText("Cidade");

        jLabel53.setText("Estado");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel49)
                    .addComponent(jLabel48)
                    .addComponent(jLabel51))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(bairroFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel52)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cidadeFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cepFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(logradouroFornecedor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel53)
                    .addComponent(jLabel50))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(numeroFornecedor)
                    .addComponent(estadosFornecedor, 0, 69, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(cepFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49)
                    .addComponent(logradouroFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel50)
                    .addComponent(numeroFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel51)
                    .addComponent(bairroFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel52)
                    .addComponent(cidadeFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel53)
                    .addComponent(estadosFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout fornecedoresLayout = new javax.swing.GroupLayout(fornecedores);
        fornecedores.setLayout(fornecedoresLayout);
        fornecedoresLayout.setHorizontalGroup(
            fornecedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fornecedoresLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(fornecedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(fornecedoresLayout.createSequentialGroup()
                        .addComponent(refreshFornecedor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(confirmFornecedor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteFornecedor))
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        fornecedoresLayout.setVerticalGroup(
            fornecedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fornecedoresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(fornecedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(fornecedoresLayout.createSequentialGroup()
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(fornecedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(refreshFornecedor, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(confirmFornecedor, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(deleteFornecedor, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainPanel.add(fornecedores, "fornecedores");

        delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete.png"))); // NOI18N
        delete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteMouseClicked(evt);
            }
        });

        confirm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/checked (1).png"))); // NOI18N
        confirm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                confirmMouseClicked(evt);
            }
        });

        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/refresh-page-option.png"))); // NOI18N
        btnRefresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRefreshMouseClicked(evt);
            }
        });

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Usuários", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

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

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Dados Pessoais", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        jLabel9.setText("Nome");

        nome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nomeMouseClicked(evt);
            }
        });

        jLabel10.setText("E-Mail");

        sexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Feminino" }));

        jLabel25.setText("Sexo");

        jLabel11.setText("Data de Nascimento");

        jLabel13.setText("Telefone");

        labelTelefone2.setText("Telefone 2");

        labelTelefone3.setText("Telefone 3");

        addTelefone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add.png"))); // NOI18N
        addTelefone.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addTelefoneMouseClicked(evt);
            }
        });

        delTel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete_icon.png"))); // NOI18N
        delTel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                delTel2MouseClicked(evt);
            }
        });

        delTel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete_icon.png"))); // NOI18N
        delTel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                delTel3MouseClicked(evt);
            }
        });

        jLabel12.setText("CPF");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(labelTelefone3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ddd3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(telefone3, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(delTel3))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelTelefone2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addComponent(ddd2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(telefone2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(delTel2))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                                .addComponent(ddd, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(telefone, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addTelefone))))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel16Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nome, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel16Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(documento, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22)))
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(sexo, 0, 82, Short.MAX_VALUE)
                            .addComponent(dataNascimento))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(documento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel25)
                    .addComponent(sexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(dataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(addTelefone)
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(ddd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(telefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(delTel2)
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelTelefone2)
                        .addComponent(ddd2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(telefone2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelTelefone3)
                        .addComponent(ddd3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(telefone3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(delTel3)))
        );

        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Endereço", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        jLabel15.setText("CEP");

        jLabel16.setText("Logradouro");

        jLabel17.setText("Nº");

        jLabel20.setText("Estado");

        jLabel19.setText("Cidade");

        jLabel18.setText("Bairro");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(49, 49, 49)
                        .addComponent(cep, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addGap(39, 39, 39)
                                .addComponent(bairro, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cidade, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(logradouro)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(estados, 0, 65, Short.MAX_VALUE)
                            .addComponent(numero))))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(cep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(logradouro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(numero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(bairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel19)
                        .addComponent(cidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(estados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Dados de Acesso", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        jLabel22.setText("Usuário");

        jLabel23.setText("Senha");

        checkSenha.setText("Cadastrar Nova Senha");
        checkSenha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                checkSenhaMouseClicked(evt);
            }
        });

        jLabel24.setText("Acesso");

        acessos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vendedor", "Administrador" }));

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(checkSenha)
                            .addComponent(senha, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(acessos, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(checkSenha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23)
                    .addComponent(senha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(acessos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout usuariosLayout = new javax.swing.GroupLayout(usuarios);
        usuarios.setLayout(usuariosLayout);
        usuariosLayout.setHorizontalGroup(
            usuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(usuariosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(usuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(usuariosLayout.createSequentialGroup()
                        .addComponent(btnRefresh)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(confirm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(delete))
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        usuariosLayout.setVerticalGroup(
            usuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(usuariosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(usuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(usuariosLayout.createSequentialGroup()
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addGroup(usuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnRefresh)
                            .addGroup(usuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(delete, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(confirm, javax.swing.GroupLayout.Alignment.TRAILING)))))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        mainPanel.add(usuarios, "usuarios");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Usuário"));

        logado.setText("usuário");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logado)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logado)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jToolBar1.setRollover(true);

        btnVenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cart.png"))); // NOI18N
        btnVenda.setText("Vendas");
        btnVenda.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnVenda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnVendaMouseClicked(evt);
            }
        });
        btnVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVendaActionPerformed(evt);
            }
        });
        jToolBar1.add(btnVenda);

        btnCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cliente.png"))); // NOI18N
        btnCliente.setText("Clientes");
        btnCliente.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnClienteMouseClicked(evt);
            }
        });
        btnCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClienteActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCliente);

        btnProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/clothes.png"))); // NOI18N
        btnProduto.setText("Produtos");
        btnProduto.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProdutoActionPerformed(evt);
            }
        });
        jToolBar1.add(btnProduto);

        btnCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buying.png"))); // NOI18N
        btnCompra.setText("Compras");
        btnCompra.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompraActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCompra);

        btnRelatorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/report.png"))); // NOI18N
        btnRelatorio.setText("Relatórios");
        btnRelatorio.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnRelatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRelatorioActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRelatorio);

        btnFornecedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/truck.png"))); // NOI18N
        btnFornecedor.setText("Fornecedores");
        btnFornecedor.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFornecedorActionPerformed(evt);
            }
        });
        jToolBar1.add(btnFornecedor);

        btnUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/users.png"))); // NOI18N
        btnUsuario.setText("Usuários");
        btnUsuario.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuarioActionPerformed(evt);
            }
        });
        jToolBar1.add(btnUsuario);

        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logout.png"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSair.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSairMouseClicked(evt);
            }
        });
        jToolBar1.add(btnSair);

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

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
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 844, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(logo))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        menu = "vendas";
        limpaCampos();
    }//GEN-LAST:event_btnVendaActionPerformed

    private void btnClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClienteActionPerformed
        CardLayout card = (CardLayout)mainPanel.getLayout();
        card.show(mainPanel, "clientes");
        menu = "clientes";
        limpaCampos();
    }//GEN-LAST:event_btnClienteActionPerformed

    private void btnProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProdutoActionPerformed
        CardLayout card = (CardLayout)mainPanel.getLayout();
        card.show(mainPanel, "produtos");
        menu = "produtos";
        limpaCampos();
    }//GEN-LAST:event_btnProdutoActionPerformed

    private void btnCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompraActionPerformed
        CardLayout card = (CardLayout)mainPanel.getLayout();
        card.show(mainPanel, "compras");
        menu = "compras";
        limpaCampos();
    }//GEN-LAST:event_btnCompraActionPerformed

    private void btnRelatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRelatorioActionPerformed
        CardLayout card = (CardLayout)mainPanel.getLayout();
        card.show(mainPanel, "relatorios");
        menu = "relatorios";
        limpaCampos();
    }//GEN-LAST:event_btnRelatorioActionPerformed

    private void btnFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFornecedorActionPerformed
        CardLayout card = (CardLayout)mainPanel.getLayout();
        card.show(mainPanel, "fornecedores");
        menu = "fornecedores";
        limpaCampos();
    }//GEN-LAST:event_btnFornecedorActionPerformed

    private void btnUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuarioActionPerformed
        CardLayout card = (CardLayout)mainPanel.getLayout();
        card.show(mainPanel, "usuarios");
        menu = "usuarios";
        limpaCampos();
    }//GEN-LAST:event_btnUsuarioActionPerformed
    /*Menu click*/
    private void btnVendaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVendaMouseClicked
        
    }//GEN-LAST:event_btnVendaMouseClicked

    private void btnClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClienteMouseClicked
        
    }//GEN-LAST:event_btnClienteMouseClicked

   
    public void limpaCampos() {
        
        if(menu.equals("usuarios")){
            for (Component C : usuarios.getComponents()){
            
                if (C instanceof JTextField){

                    ((JTextComponent) C).setText(null);
                }
                
            }
            tabelaUsuario.clearSelection();
            
            checkSenha.setEnabled(false);
            senha.setEnabled(true);
            senha.setEditable(true);
        }
        
        if(menu.equals("clientes")){
            for (Component C : clientes.getComponents()){
            
                if (C instanceof JTextField){

                    ((JTextComponent) C).setText(null);
                }
                
            }
            tabelaCliente.clearSelection();
            
        }
        
        if(menu.equals("fornecedores")){
            for (Component C : fornecedores.getComponents()){
            
                if (C instanceof JTextField){

                    ((JTextComponent) C).setText(null);
                }
                
            }
            tabelaFornecedor.clearSelection();
            
        }
        
        if(menu.equals("produtos")){
            for (Component C : produtos.getComponents()){
            
                if (C instanceof JTextField){

                    ((JTextComponent) C).setText(null);
                }
                
            }
            tabelaProduto.clearSelection();
            descricaoProduto.setText(null);
            imagemProduto.setIcon(null);
            imagemProduto.setText("IMAGEM");
            quantidadeProduto.setValue(1);
            
        }
        
        if(menu.equals("vendas")){
            for (Component C : vendas.getComponents()){
            
                if (C instanceof JTextField){

                    ((JTextComponent) C).setText(null);
                }
                
            }
            tabelaVendaProduto.clearSelection();
            ((DefaultTableModel)tabelaVendaProduto.getModel()).setNumRows(0);
            descontoVenda.setValue(0);
            
            labelParcelasVenda.setEnabled(false);
            parcelas.setEnabled(false);
            labelDataVencimentoParcela.setEnabled(false);
            dataVencimentoParcela.setEnabled(false);

            
            labelPagoVenda.setEnabled(false);
            labelTotalVenda.setEnabled(false);
            labelTrocoVenda.setEnabled(false);

            valorPagoVenda.setEnabled(false);
            valorTotalVenda.setEnabled(false);
            valorTrocoVenda.setEnabled(false);

            calculaTrocoVenda.setEnabled(false);
            
        }
        
        
        labelTelefone2.setEnabled(false);
        ddd2.setEnabled(false);
        telefone2.setEnabled(false);
        delTel2.setEnabled(false);
        labelTelefone3.setEnabled(false);
        ddd3.setEnabled(false);
        telefone3.setEnabled(false);
        delTel3.setEnabled(false);
        
        labelTelefone2Cliente.setEnabled(false);
        ddd2Cliente.setEnabled(false);
        telefone2Cliente.setEnabled(false);
        delTel2Cliente.setEnabled(false);
        labelTelefone3Cliente.setEnabled(false);
        ddd3Cliente.setEnabled(false);
        telefone3Cliente.setEnabled(false);
        delTel3Cliente.setEnabled(false);
        
        labelTelefone2Fornecedor.setEnabled(false);
        ddd2Fornecedor.setEnabled(false);
        telefone2Fornecedor.setEnabled(false);
        delTel2Fornecedor.setEnabled(false);
        labelTelefone3Fornecedor.setEnabled(false);
        ddd3Fornecedor.setEnabled(false);
        telefone3Fornecedor.setEnabled(false);
        delTel3Fornecedor.setEnabled(false);
        
        
    }

    public void preencheFormulario() throws NumberFormatException {
       
        if(menu.equals("usuarios")){
            DecimalFormat df = new DecimalFormat("00");
            DecimalFormat dff = new DecimalFormat("0000");
            Long id = Long.parseLong((String) tabelaUsuario.getValueAt(tabelaUsuario.getSelectedRow(), 0));
            FuncionarioDAO dao = new FuncionarioDAO();
            Funcionario funcionario = dao.getById(id);

            checkSenha.setEnabled(true);
            checkSenha.setSelected(false);
            senha.setEditable(false);
            senha.setEnabled(false);


            nome.setText(funcionario.getNome());
            email.setText(funcionario.getEmail());

            String dia = (df.format(funcionario.getDataNascimento().get(Calendar.DAY_OF_MONTH)));
            String mes = (df.format(funcionario.getDataNascimento().get(Calendar.MONTH)+1));
            String ano = dff.format(funcionario.getDataNascimento().get(Calendar.YEAR));

            dataNascimento.setText(dia+"/"+mes+"/"+ano);
            documento.setText(funcionario.getDocumento().getNumero());

            if(funcionario.getTelefones().size() == 1){
                labelTelefone2.setEnabled(false);
                ddd2.setEnabled(false);
                ddd2.setText(null);
                telefone2.setEnabled(false);
                telefone2.setText(null);
                delTel2.setEnabled(false);

                labelTelefone3.setEnabled(false);
                ddd3.setEnabled(false);
                ddd3.setText(null);
                telefone3.setEnabled(false);
                telefone3.setText(null);
                delTel3.setEnabled(false);

                ddd.setText(funcionario.getTelefones().get(0).getDdd());
                telefone.setText(funcionario.getTelefones().get(0).getNumero());
            }


            if(funcionario.getTelefones().size() == 2){
                labelTelefone2.setEnabled(true);
                ddd2.setEnabled(true);
                telefone2.setEnabled(true);
                delTel2.setEnabled(true);

                labelTelefone3.setEnabled(false);
                ddd3.setEnabled(false);
                ddd3.setText(null);
                telefone3.setEnabled(false);
                telefone3.setText(null);
                delTel3.setEnabled(false);

                ddd.setText(funcionario.getTelefones().get(0).getDdd());
                telefone.setText(funcionario.getTelefones().get(0).getNumero());
                ddd2.setText(funcionario.getTelefones().get(1).getDdd());
                telefone2.setText(funcionario.getTelefones().get(1).getNumero());
            }


            if(funcionario.getTelefones().size() == 3){
                labelTelefone2.setEnabled(true);
                ddd2.setEnabled(true);
                telefone2.setEnabled(true);
                delTel2.setEnabled(true);

                ddd2.setText(funcionario.getTelefones().get(1).getDdd());
                telefone2.setText(funcionario.getTelefones().get(1).getNumero());

                labelTelefone3.setEnabled(true);
                ddd3.setEnabled(true);
                telefone3.setEnabled(true);
                delTel3.setEnabled(true);

                ddd.setText(funcionario.getTelefones().get(0).getDdd());
                telefone.setText(funcionario.getTelefones().get(0).getNumero());
                ddd2.setText(funcionario.getTelefones().get(1).getDdd());
                telefone2.setText(funcionario.getTelefones().get(1).getNumero());
                ddd3.setText(funcionario.getTelefones().get(2).getDdd());
                telefone3.setText(funcionario.getTelefones().get(2).getNumero());
            }




            cep.setText(funcionario.getEndereco().getCep());
            logradouro.setText(funcionario.getEndereco().getLogradouro());
            numero.setText(funcionario.getEndereco().getNumero());
            bairro.setText(funcionario.getEndereco().getBairro().getNome());
            cidade.setText(funcionario.getEndereco().getBairro().getCidade().getNome());
            usuario.setText(funcionario.getUsuario());



            senha.setText(null);


            acessos.setSelectedItem(funcionario.getAcesso().toString());

            estados.setSelectedItem(funcionario.getEndereco().getBairro().getCidade().getEstado().getUf());
            sexo.setSelectedItem(funcionario.getSexo().toString());

            delete.setEnabled(true);
            confirm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit.png")));
        }
        
        
        if(menu.equals("clientes")){
            DecimalFormat df = new DecimalFormat("00");
            DecimalFormat dff = new DecimalFormat("0000");
            Long id = Long.parseLong((String) tabelaCliente.getValueAt(tabelaCliente.getSelectedRow(), 0));
            PessoaDAO dao = new PessoaDAO();
            Pessoa pessoa = dao.getById(id);

            nomeCliente.setText(pessoa.getNome());
            emailCliente.setText(pessoa.getEmail());

            String dia = (df.format(pessoa.getDataNascimento().get(Calendar.DAY_OF_MONTH)));
            String mes = (df.format(pessoa.getDataNascimento().get(Calendar.MONTH)+1));
            String ano = dff.format(pessoa.getDataNascimento().get(Calendar.YEAR));

            dataNascimentoCliente.setText(dia+"/"+mes+"/"+ano);
            documentoCliente.setText(pessoa.getDocumento().getNumero());

            if(pessoa.getTelefones().size() == 1){
                labelTelefone2Cliente.setEnabled(false);
                ddd2Cliente.setEnabled(false);
                ddd2Cliente.setText(null);
                telefone2Cliente.setEnabled(false);
                telefone2Cliente.setText(null);
                delTel2Cliente.setEnabled(false);

                labelTelefone3Cliente.setEnabled(false);
                ddd3Cliente.setEnabled(false);
                ddd3Cliente.setText(null);
                telefone3Cliente.setEnabled(false);
                telefone3Cliente.setText(null);
                delTel3Cliente.setEnabled(false);

                dddCliente.setText(pessoa.getTelefones().get(0).getDdd());
                telefoneCliente.setText(pessoa.getTelefones().get(0).getNumero());
            }


            if(pessoa.getTelefones().size() == 2){
                labelTelefone2Cliente.setEnabled(true);
                ddd2Cliente.setEnabled(true);
                telefone2Cliente.setEnabled(true);
                delTel2Cliente.setEnabled(true);

                labelTelefone3Cliente.setEnabled(false);
                ddd3Cliente.setEnabled(false);
                ddd3Cliente.setText(null);
                telefone3Cliente.setEnabled(false);
                telefone3Cliente.setText(null);
                delTel3Cliente.setEnabled(false);

                dddCliente.setText(pessoa.getTelefones().get(0).getDdd());
                telefoneCliente.setText(pessoa.getTelefones().get(0).getNumero());
                ddd2Cliente.setText(pessoa.getTelefones().get(1).getDdd());
                telefone2Cliente.setText(pessoa.getTelefones().get(1).getNumero());
            }


            if(pessoa.getTelefones().size() == 3){
                labelTelefone2Cliente.setEnabled(true);
                ddd2Cliente.setEnabled(true);
                telefone2Cliente.setEnabled(true);
                delTel2Cliente.setEnabled(true);

                ddd2Cliente.setText(pessoa.getTelefones().get(1).getDdd());
                telefone2Cliente.setText(pessoa.getTelefones().get(1).getNumero());

                labelTelefone3Cliente.setEnabled(true);
                ddd3Cliente.setEnabled(true);
                telefone3Cliente.setEnabled(true);
                delTel3Cliente.setEnabled(true);

                dddCliente.setText(pessoa.getTelefones().get(0).getDdd());
                telefoneCliente.setText(pessoa.getTelefones().get(0).getNumero());
                ddd2Cliente.setText(pessoa.getTelefones().get(1).getDdd());
                telefone2Cliente.setText(pessoa.getTelefones().get(1).getNumero());
                ddd3Cliente.setText(pessoa.getTelefones().get(2).getDdd());
                telefone3Cliente.setText(pessoa.getTelefones().get(2).getNumero());
            }




            cepCliente.setText(pessoa.getEndereco().getCep());
            logradouroCliente.setText(pessoa.getEndereco().getLogradouro());
            numeroCliente.setText(pessoa.getEndereco().getNumero());
            bairroCliente.setText(pessoa.getEndereco().getBairro().getNome());
            cidadeCliente.setText(pessoa.getEndereco().getBairro().getCidade().getNome());





            estadosCliente.setSelectedItem(pessoa.getEndereco().getBairro().getCidade().getEstado().getUf());
            sexoCliente.setSelectedItem(pessoa.getSexo().toString());

            deleteCliente.setEnabled(true);
            confirmCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit.png")));

        }
        
        if(menu.equals("fornecedores")){
            DecimalFormat df = new DecimalFormat("00");
            DecimalFormat dff = new DecimalFormat("0000");
            Long id = Long.parseLong((String) tabelaFornecedor.getValueAt(tabelaFornecedor.getSelectedRow(), 0));
            PessoaDAO dao = new PessoaDAO();
            Pessoa pessoa = dao.getById(id);

            nomeFornecedor.setText(pessoa.getNome());
            emailFornecedor.setText(pessoa.getEmail());

            
            documentoFornecedor.setText(pessoa.getDocumento().getNumero());

            if(pessoa.getTelefones().size() == 1){
                labelTelefone2Fornecedor.setEnabled(false);
                ddd2Fornecedor.setEnabled(false);
                ddd2Fornecedor.setText(null);
                telefone2Fornecedor.setEnabled(false);
                telefone2Fornecedor.setText(null);
                delTel2Fornecedor.setEnabled(false);

                labelTelefone3Fornecedor.setEnabled(false);
                ddd3Fornecedor.setEnabled(false);
                ddd3Fornecedor.setText(null);
                telefone3Fornecedor.setEnabled(false);
                telefone3Fornecedor.setText(null);
                delTel3Fornecedor.setEnabled(false);

                dddFornecedor.setText(pessoa.getTelefones().get(0).getDdd());
                telefoneFornecedor.setText(pessoa.getTelefones().get(0).getNumero());
            }


            if(pessoa.getTelefones().size() == 2){
                labelTelefone2Fornecedor.setEnabled(true);
                ddd2Fornecedor.setEnabled(true);
                telefone2Fornecedor.setEnabled(true);
                delTel2Fornecedor.setEnabled(true);

                labelTelefone3Fornecedor.setEnabled(false);
                ddd3Fornecedor.setEnabled(false);
                ddd3Fornecedor.setText(null);
                telefone3Fornecedor.setEnabled(false);
                telefone3Fornecedor.setText(null);
                delTel3Fornecedor.setEnabled(false);

                dddFornecedor.setText(pessoa.getTelefones().get(0).getDdd());
                telefoneFornecedor.setText(pessoa.getTelefones().get(0).getNumero());
                ddd2Fornecedor.setText(pessoa.getTelefones().get(1).getDdd());
                telefone2Fornecedor.setText(pessoa.getTelefones().get(1).getNumero());
            }


            if(pessoa.getTelefones().size() == 3){
                labelTelefone2Fornecedor.setEnabled(true);
                ddd2Fornecedor.setEnabled(true);
                telefone2Fornecedor.setEnabled(true);
                delTel2Fornecedor.setEnabled(true);

                ddd2Fornecedor.setText(pessoa.getTelefones().get(1).getDdd());
                telefone2Fornecedor.setText(pessoa.getTelefones().get(1).getNumero());

                labelTelefone3Fornecedor.setEnabled(true);
                ddd3Fornecedor.setEnabled(true);
                telefone3Fornecedor.setEnabled(true);
                delTel3Fornecedor.setEnabled(true);

                dddFornecedor.setText(pessoa.getTelefones().get(0).getDdd());
                telefoneFornecedor.setText(pessoa.getTelefones().get(0).getNumero());
                ddd2Fornecedor.setText(pessoa.getTelefones().get(1).getDdd());
                telefone2Fornecedor.setText(pessoa.getTelefones().get(1).getNumero());
                ddd3Fornecedor.setText(pessoa.getTelefones().get(2).getDdd());
                telefone3Fornecedor.setText(pessoa.getTelefones().get(2).getNumero());
            }




            cepFornecedor.setText(pessoa.getEndereco().getCep());
            logradouroFornecedor.setText(pessoa.getEndereco().getLogradouro());
            numeroFornecedor.setText(pessoa.getEndereco().getNumero());
            bairroFornecedor.setText(pessoa.getEndereco().getBairro().getNome());
            cidadeFornecedor.setText(pessoa.getEndereco().getBairro().getCidade().getNome());





            estadosFornecedor.setSelectedItem(pessoa.getEndereco().getBairro().getCidade().getEstado().getUf());
            

            deleteFornecedor.setEnabled(true);
            confirmFornecedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit.png")));

        }
        
        
        if(menu.equals("produtos")){
            DecimalFormat df = new DecimalFormat("0.00");
            
            Long id = Long.parseLong((String) tabelaProduto.getValueAt(tabelaProduto.getSelectedRow(), 0));
            ProdutoDAO dao = new ProdutoDAO();
            Produto produto = dao.getById(id);

            quantidadeProduto.setValue(produto.getQuantidade());
            precoProduto.setText(df.format(produto.getValorUnitario()));
            descricaoProduto.setText(produto.getDescricao());
            imagemProduto.setText(null);
            imagemProduto.setIcon(new javax.swing.ImageIcon(produto.getImagem()));
            
            caminhoArquivo = null;
            
            deleteProduto.setEnabled(true);
            confirmProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit.png")));

        }
    }

    private void btnSairMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSairMouseClicked
        this.dispose();
        new Login().setVisible(true);
    }//GEN-LAST:event_btnSairMouseClicked

    private void delTel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_delTel3MouseClicked
        Validacao valida = new Validacao();
        if(tabelaUsuario.getSelectedRow() > -1){
            if(valida.validaDdd(ddd3.getText()) && valida.validaTelefone(telefone3.getText())){
                TelefoneDAO dao = new TelefoneDAO();
                PessoaDAO pes = new PessoaDAO();
                Long id = Long.parseLong((String) tabelaUsuario.getValueAt(tabelaUsuario.getSelectedRow(), 0));

                Pessoa pessoa = pes.getById(id);

                for(int i = 0; i < pessoa.getTelefones().size(); i++){
                    if(pessoa.getTelefones().get(i).getDdd().equals(ddd3.getText())
                        && pessoa.getTelefones().get(i).getNumero().equals(telefone3.getText())){
                        dao.removeById(pessoa.getTelefones().get(i).getIdTelefone());
                    }
                }

                preencheFormulario();
            }
        }
        labelTelefone3.setEnabled(false);
        ddd3.setEnabled(false);
        ddd3.setText(null);
        telefone3.setEnabled(false);
        telefone3.setText(null);
        delTel3.setEnabled(false);

    }//GEN-LAST:event_delTel3MouseClicked

    private void delTel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_delTel2MouseClicked
        Validacao valida = new Validacao();
        if(tabelaUsuario.getSelectedRow() > -1){
            if(valida.validaDdd(ddd2.getText()) && valida.validaTelefone(telefone2.getText())){
                TelefoneDAO dao = new TelefoneDAO();
                PessoaDAO pes = new PessoaDAO();
                Long id = Long.parseLong((String) tabelaUsuario.getValueAt(tabelaUsuario.getSelectedRow(), 0));

                Pessoa pessoa = pes.getById(id);

                for(int i = 0; i < pessoa.getTelefones().size(); i++){
                    if(pessoa.getTelefones().get(i).getDdd().equals(ddd2.getText())
                        && pessoa.getTelefones().get(i).getNumero().equals(telefone2.getText())){
                        dao.removeById(pessoa.getTelefones().get(i).getIdTelefone());
                    }
                }

                preencheFormulario();
            }
        }
        labelTelefone2.setEnabled(false);
        ddd2.setEnabled(false);
        ddd2.setText(null);
        telefone2.setEnabled(false);
        telefone2.setText(null);
        delTel2.setEnabled(false);

    }//GEN-LAST:event_delTel2MouseClicked

    private void checkSenhaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_checkSenhaMouseClicked
        if(checkSenha.isSelected()){
            senha.setEnabled(true);
            senha.setEditable(true);
        }
        else{
            senha.setEnabled(false);
            senha.setEditable(false);
        }
    }//GEN-LAST:event_checkSenhaMouseClicked

    private void btnRefreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRefreshMouseClicked
        limpaCampos();
        delete.setEnabled(false);
        confirm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/checked (1).png")));
    }//GEN-LAST:event_btnRefreshMouseClicked

    private void confirmMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_confirmMouseClicked

        Validacao valida = new Validacao();

        if(valida.validaNome(nome.getText())){
            if(valida.validaEmail(email.getText())){
                if(valida.validaDataNascimento(dataNascimento.getText())){
                    if(valida.validaCpf(documento.getText())){
                        if(valida.validaDdd(ddd.getText())){
                            if(valida.validaTelefone(telefone.getText())){
                                if(valida.validaCep(cep.getText())){
                                    if(valida.validaEndereco(logradouro.getText())){
                                        if(valida.validaEndereco(bairro.getText())){
                                            if(valida.validaEndereco(cidade.getText())){
                                                if(valida.validaNumero(numero.getText())){
                                                    if(valida.validaUsuario(usuario.getText())){
                                                        if(!checkSenha.isSelected()){
                                                            senha.setText("tempsenha");
                                                        }
                                                        if(valida.validaSenha(senha.getText())){
                                                            if(valida.validaCombo(acessos.getSelectedIndex())){

                                                                List<Telefone> telefones = new ArrayList<>();

                                                                if(telefone.getText().length() == 8){
                                                                    telefones.add(new Telefone(ddd.getText(), telefone.getText(), TipoTelefone.Fixo));
                                                                }
                                                                else{
                                                                    telefones.add(new Telefone(ddd.getText(), telefone.getText(), TipoTelefone.Celular));
                                                                }

                                                                if(ddd2.isVisible() && valida.validaDdd(ddd2.getText())){
                                                                    if(valida.validaTelefone(telefone2.getText())){
                                                                        if(telefone2.getText().length() == 8){
                                                                            telefones.add(new Telefone(ddd2.getText(), telefone2.getText(), TipoTelefone.Fixo));
                                                                        }
                                                                        else{
                                                                            telefones.add(new Telefone(ddd2.getText(), telefone2.getText(), TipoTelefone.Celular));
                                                                        }
                                                                    }
                                                                }

                                                                if(ddd3.isVisible() && valida.validaDdd(ddd3.getText())){
                                                                    if(valida.validaTelefone(telefone3.getText())){
                                                                        if(telefone3.getText().length() == 8){
                                                                            telefones.add(new Telefone(ddd3.getText(), telefone3.getText(), TipoTelefone.Fixo));
                                                                        }
                                                                        else{
                                                                            telefones.add(new Telefone(ddd3.getText(), telefone3.getText(), TipoTelefone.Celular));
                                                                        }
                                                                    }
                                                                }

                                                                Calendar c = Calendar.getInstance();
                                                                int day   = Integer.parseInt(dataNascimento.getText().substring(0, 2));
                                                                int month = Integer.parseInt(dataNascimento.getText().substring(3, 5));
                                                                int year  = Integer.parseInt(dataNascimento.getText().substring(6, 10));
                                                                c.set(year, (month-1), day);
                                                                String tipoAcesso = acessos.getSelectedItem().toString().substring(0, 1);
                                                                if(!delete.isVisible()){
                                                                    /*Persistence With Hibernate - Good Luck For Us!!!!*/

                                                                    
                                                                    CidadeDAO      cidDAO = new CidadeDAO();
                                                                    BairroDAO      baiDAO = new BairroDAO();
                                     

                                                                    Cidade objCidade = null;
                                                                    for(int i = 0; i < cidDAO.findAll().size(); i++){
                                                                        if(cidDAO.findAll().get(i).getNome().equals(cidade.getText())){
                                                                            objCidade = cidDAO.findAll().get(i);
                                                                            break;
                                                                        }
                                                                    }

                                                                    if(objCidade == null){
                                                                        objCidade = new Cidade(cidade.getText());
                                                                    }

                                                                    Bairro objBairro = null;
                                                                    for(int i = 0; i < baiDAO.findAll().size(); i++){
                                                                        if(baiDAO.findAll().get(i).getNome().equals(bairro.getText())){
                                                                            objBairro = baiDAO.findAll().get(i);
                                                                            break;
                                                                        }
                                                                    }
                                                                    if(objBairro == null){
                                                                        objBairro = new Bairro(bairro.getText());
                                                                    }

                                                                    Endereco objEndereco       = new Endereco   (logradouro.getText(), numero.getText(), cep.getText());
                                                                    Documento objDocumento     = new Documento  (documento.getText(), TipoDocumento.CPF);

                                                                    /*Hash para Senha*/
                                                                    HashSha hash = new HashSha(senha.getText());
                                                                    String hashSenha = "";
                                                                    try {
                                                                        hashSenha = hash.hashSenha();
                                                                    } catch (NoSuchAlgorithmException ex) {
                                                                        Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                                                                    } catch (UnsupportedEncodingException ex) {
                                                                        Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                                                                    }

                                                                    Funcionario objFuncionario = new Funcionario(usuario.getText(), hashSenha, Acesso.valueOf(acessos.getSelectedItem().toString()), nome.getText(), email.getText(), c, Sexo.valueOf(sexo.getSelectedItem().toString()));

                                                                    FuncionarioDAO dao = new FuncionarioDAO();
                                                                    EstadoDAO est = new EstadoDAO();
                                                                    List<Estado> list = est.findByUf(estados.getSelectedItem().toString());

                                                                    for(int i = 0; i < list.size(); i++){
                                                                        list.get(i).addCidade(objCidade);
                                                                        objCidade.addBairro(objBairro);
                                                                        objBairro.addEndereco(objEndereco);
                                                                        objFuncionario.setEndereco(objEndereco);
                                                                        objFuncionario.setTelefone(telefones);
                                                                        objFuncionario.setDocumento(objDocumento);

                                                                        dao.merge(objFuncionario);
                                                                    }

                                                                    acao = "insert";
                                                                    Mensagem msg = new Mensagem();
                                                                    Thread mensagemT = new Thread(msg);
                                                                    mensagemT.start();

                                                                    limpaCampos();

                                                                    carregaDados();
                                                                }
                                                                else{
                                                                    /*Atualiza os Dados*/
                                                                    FuncionarioDAO dao = new FuncionarioDAO();
                                                                    CidadeDAO      cidDAO = new CidadeDAO();
                                                                    BairroDAO      baiDAO = new BairroDAO();

                                                                    Long id = Long.parseLong(
                                                                        (String) tabelaUsuario.getValueAt(tabelaUsuario.getSelectedRow(), 0)
                                                                    );

                                                                    Funcionario funcionario = dao.getById(id);

                                                                    funcionario.setNome(nome.getText());
                                                                    funcionario.setEmail(email.getText());
                                                                    funcionario.setDataNascimento(c);
                                                                    funcionario.getDocumento().setNumero(documento.getText());

                                                                    if(funcionario.getTelefones().size() == 1){
                                                                        if(ddd2.isVisible() && valida.validaDdd(ddd2.getText())){
                                                                            if(valida.validaTelefone(telefone2.getText())){
                                                                                funcionario.getTelefones().get(0).setDdd(ddd.getText());
                                                                                funcionario.getTelefones().get(0).setNumero(telefone.getText());

                                                                                if(ddd2.isVisible() && valida.validaDdd(ddd2.getText())){
                                                                                    if(valida.validaTelefone(telefone2.getText())){
                                                                                        if(telefone2.getText().length() == 8){
                                                                                            funcionario.addTelefone(new Telefone(ddd2.getText(), telefone2.getText(), TipoTelefone.Fixo));
                                                                                        }
                                                                                        else{
                                                                                            funcionario.addTelefone(new Telefone(ddd2.getText(), telefone2.getText(), TipoTelefone.Celular));
                                                                                        }
                                                                                    }
                                                                                }

                                                                                if(ddd3.isVisible() && valida.validaDdd(ddd3.getText())){
                                                                                    if(valida.validaTelefone(telefone3.getText())){
                                                                                        if(telefone3.getText().length() == 8){
                                                                                            funcionario.addTelefone(new Telefone(ddd3.getText(), telefone3.getText(), TipoTelefone.Fixo));
                                                                                        }
                                                                                        else{
                                                                                            funcionario.addTelefone(new Telefone(ddd3.getText(), telefone3.getText(), TipoTelefone.Celular));
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }

                                                                    if(funcionario.getTelefones().size() == 2){
                                                                        if(ddd2.isVisible() && valida.validaDdd(ddd2.getText())){
                                                                            if(valida.validaTelefone(telefone2.getText())){
                                                                                funcionario.getTelefones().get(0).setDdd(ddd.getText());
                                                                                funcionario.getTelefones().get(0).setNumero(telefone.getText());

                                                                                funcionario.getTelefones().get(1).setDdd(ddd2.getText());
                                                                                funcionario.getTelefones().get(1).setNumero(telefone2.getText());

                                                                                if(ddd3.isVisible() && valida.validaDdd(ddd3.getText())){
                                                                                    if(valida.validaTelefone(telefone3.getText())){
                                                                                        if(telefone3.getText().length() == 8){
                                                                                            funcionario.addTelefone(new Telefone(ddd3.getText(), telefone3.getText(), TipoTelefone.Fixo));
                                                                                        }
                                                                                        else{
                                                                                            funcionario.addTelefone(new Telefone(ddd3.getText(), telefone3.getText(), TipoTelefone.Celular));
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }

                                                                    if(funcionario.getTelefones().size() == 3){
                                                                        if(ddd2.isVisible() && valida.validaDdd(ddd2.getText())){
                                                                            if(valida.validaTelefone(telefone2.getText())){
                                                                                funcionario.getTelefones().get(0).setDdd(ddd.getText());
                                                                                funcionario.getTelefones().get(0).setNumero(telefone.getText());

                                                                                funcionario.getTelefones().get(1).setDdd(ddd2.getText());
                                                                                funcionario.getTelefones().get(1).setNumero(telefone2.getText());

                                                                                funcionario.getTelefones().get(2).setDdd(ddd3.getText());
                                                                                funcionario.getTelefones().get(2).setNumero(telefone3.getText());
                                                                            }
                                                                        }
                                                                    }

                                                                    funcionario.getEndereco().setLogradouro(logradouro.getText());
                                                                    funcionario.getEndereco().setNumero(numero.getText());
                                                                    funcionario.getEndereco().setCep(cep.getText());

                                                                    boolean existeBairro = false;
                                                                    Bairro exsBairro = null;
                                                                    for(int i = 0; i < baiDAO.findAll().size(); i++){

                                                                        if(baiDAO.findAll().get(i).getNome().equals(bairro.getText())){
                                                                            existeBairro = true;
                                                                            exsBairro = baiDAO.findAll().get(i);
                                                                            break;
                                                                        }
                                                                    }

                                                                    if(existeBairro){
                                                                        exsBairro.addEndereco(funcionario.getEndereco());
                                                                    }else{
                                                                        new Bairro(bairro.getText()).addEndereco(funcionario.getEndereco());
                                                                    }

                                                                    boolean existeCidade = false;
                                                                    Cidade exsCidade = null;
                                                                    for(int i = 0; i < cidDAO.findAll().size(); i++){

                                                                        if(cidDAO.findAll().get(i).getNome().equals(cidade.getText())){
                                                                            existeCidade = true;
                                                                            exsCidade = cidDAO.findAll().get(i);
                                                                            break;
                                                                        }
                                                                    }

                                                                    if(existeCidade){
                                                                        exsCidade.addBairro(funcionario.getEndereco().getBairro());
                                                                    }else{
                                                                        new Cidade(cidade.getText()).addBairro(funcionario.getEndereco().getBairro());

                                                                    }

                                                                    EstadoDAO      estDao = new EstadoDAO();
                                                                    Estado atualEstado = null;
                                                                    List<Estado> list = estDao.findByUf(estados.getSelectedItem().toString());
                                                                    for(int i = 0; i < list.size(); i++){
                                                                        atualEstado = list.get(i);
                                                                    }

                                                                    atualEstado.addCidade(funcionario.getEndereco().getBairro().getCidade());

                                                                    //funcionario.getEndereco().getBairro().setNome(bairro.getText());
                                                                    //funcionario.getEndereco().getBairro().getCidade().setNome(cidade.getText());
                                                                    funcionario.setDataNascimento(c);
                                                                    funcionario.setUsuario(usuario.getText());

                                                                    if(senha.isEditable()){
                                                                        /*Hex para Senha*/
                                                                        HashSha hash = new HashSha(senha.getText());
                                                                        String hashSenha = "";

                                                                        try {
                                                                            hashSenha = hash.hashSenha();
                                                                            funcionario.setSenha(hashSenha);
                                                                        } catch (NoSuchAlgorithmException ex) {
                                                                            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                                                                        } catch (UnsupportedEncodingException ex) {
                                                                            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                                                                        }
                                                                    }

                                                                    senha.setText("tempsenha");

                                                                    funcionario.setAcesso(Acesso.valueOf(acessos.getSelectedItem().toString()));
                                                                    funcionario.setSexo(Sexo.valueOf(sexo.getSelectedItem().toString()));

                                                                    dao.merge(funcionario);

                                                                    acao = "edit";
                                                                    Mensagem msg = new Mensagem();
                                                                    Thread mensagemT = new Thread(msg);
                                                                    mensagemT.start();

                                                                    limpaCampos();
                                                                    carregaDados();
                                                                }
                                                            }
                                                            else{
                                                                JOptionPane.showMessageDialog(null, "Selcione o tipo de acesso");
                                                                acessos.requestFocus();
                                                            }
                                                        }
                                                        else{
                                                            JOptionPane.showMessageDialog(null, "Senha Requerida");
                                                            senha.requestFocus();
                                                        }
                                                    }
                                                    else{
                                                        JOptionPane.showMessageDialog(null, "Usuário Requerido");
                                                        usuario.requestFocus();
                                                    }
                                                }
                                                else{
                                                    JOptionPane.showMessageDialog(null, "Número Requerido");
                                                    numero.requestFocus();
                                                }
                                            }
                                            else{
                                                JOptionPane.showMessageDialog(null, "Cidade Requerido");
                                                cidade.requestFocus();
                                            }
                                        }
                                        else{
                                            JOptionPane.showMessageDialog(null, "Bairro Requerido");
                                            bairro.requestFocus();
                                        }
                                    }
                                    else{
                                        JOptionPane.showMessageDialog(null, "Logradouro Requerido");
                                        logradouro.requestFocus();
                                    }
                                }
                                else{
                                    JOptionPane.showMessageDialog(null, "Cep Requerido");
                                    cep.requestFocus();
                                }
                            }
                            else{
                                JOptionPane.showMessageDialog(null, "Telefone Requerido");
                                telefone.requestFocus();
                            }
                        }else{
                            JOptionPane.showMessageDialog(null, "DDD Requerido");
                            ddd.requestFocus();
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "CPF Requerido");
                        documento.requestFocus();
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Data de Nascimento Requerido");
                    dataNascimento.requestFocus();
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "E-mail Requerido");
                email.requestFocus();
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Nome Requerido");
            nome.requestFocus();
        }
    }//GEN-LAST:event_confirmMouseClicked

    private void deleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteMouseClicked
        FuncionarioDAO dao = new FuncionarioDAO();
        Long id = Long.parseLong(
            (String) tabelaUsuario.getValueAt(tabelaUsuario.getSelectedRow(), 0)
        );

        dao.removeById(id);
        acao = "delete";
        Mensagem msg = new Mensagem();
        Thread mensagem = new Thread(msg);
        mensagem.start();
        limpaCampos();
        carregaDados();
    }//GEN-LAST:event_deleteMouseClicked

    private void addTelefoneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addTelefoneMouseClicked

        if(labelTelefone2.isVisible()){
            labelTelefone3.setEnabled(true);
            ddd3.setEnabled(true);
            telefone3.setEnabled(true);
            delTel3.setEnabled(true);
        }
        else{
            labelTelefone2.setEnabled(true);
            ddd2.setEnabled(true);
            telefone2.setEnabled(true);
            delTel2.setEnabled(true);
        }
    }//GEN-LAST:event_addTelefoneMouseClicked

    private void nomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nomeMouseClicked
        nome.setBackground(Color.white);
    }//GEN-LAST:event_nomeMouseClicked

    private void tabelaUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaUsuarioMouseClicked
        preencheFormulario();
    }//GEN-LAST:event_tabelaUsuarioMouseClicked

    private void addTelClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addTelClienteMouseClicked
        if(labelTelefone2Cliente.isVisible()){
            labelTelefone3Cliente.setEnabled(true);
            ddd3Cliente.setEnabled(true);
            telefone3Cliente.setEnabled(true);
            delTel3Cliente.setEnabled(true);
        }
        else{
            labelTelefone2Cliente.setEnabled(true);
            ddd2Cliente.setEnabled(true);
            telefone2Cliente.setEnabled(true);
            delTel2Cliente.setEnabled(true);
        }
    }//GEN-LAST:event_addTelClienteMouseClicked

    private void delTel2ClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_delTel2ClienteMouseClicked
        Validacao valida = new Validacao();
        if(tabelaCliente.getSelectedRow() > -1){
            if(valida.validaDdd(ddd2Cliente.getText()) && valida.validaTelefone(telefone2Cliente.getText())){
                TelefoneDAO dao = new TelefoneDAO();
                PessoaDAO pes = new PessoaDAO();
                Long id = Long.parseLong((String) tabelaCliente.getValueAt(tabelaCliente.getSelectedRow(), 0));

                Pessoa pessoa = pes.getById(id);

                for(int i = 0; i < pessoa.getTelefones().size(); i++){
                    if(pessoa.getTelefones().get(i).getDdd().equals(ddd2Cliente.getText())
                        && pessoa.getTelefones().get(i).getNumero().equals(telefone2Cliente.getText())){
                        dao.removeById(pessoa.getTelefones().get(i).getIdTelefone());
                    }
                }

                preencheFormulario();
            }
        }
        labelTelefone2Cliente.setEnabled(false);
        ddd2Cliente.setEnabled(false);
        ddd2Cliente.setText(null);
        telefone2Cliente.setEnabled(false);
        telefone2Cliente.setText(null);
        delTel2Cliente.setEnabled(false);
    }//GEN-LAST:event_delTel2ClienteMouseClicked

    private void delTel3ClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_delTel3ClienteMouseClicked
        Validacao valida = new Validacao();
        if(tabelaCliente.getSelectedRow() > -1){
            if(valida.validaDdd(ddd3Cliente.getText()) && valida.validaTelefone(telefone3Cliente.getText())){
                TelefoneDAO dao = new TelefoneDAO();
                PessoaDAO pes = new PessoaDAO();
                Long id = Long.parseLong((String) tabelaCliente.getValueAt(tabelaCliente.getSelectedRow(), 0));

                Pessoa pessoa = pes.getById(id);

                for(int i = 0; i < pessoa.getTelefones().size(); i++){
                    if(pessoa.getTelefones().get(i).getDdd().equals(ddd3Cliente.getText())
                        && pessoa.getTelefones().get(i).getNumero().equals(telefone3Cliente.getText())){
                        dao.removeById(pessoa.getTelefones().get(i).getIdTelefone());
                    }
                }

                preencheFormulario();
            }
        }
        labelTelefone3Cliente.setEnabled(false);
        ddd3Cliente.setEnabled(false);
        telefone3Cliente.setEnabled(false);
        delTel3Cliente.setEnabled(false);
        ddd3Cliente.setText(null);
        telefone3Cliente.setText(null);
       
    }//GEN-LAST:event_delTel3ClienteMouseClicked

    private void refreshClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refreshClienteMouseClicked
        limpaCampos();
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
                                                    JOptionPane.showMessageDialog(null, "ALL DONE!!!");
                                                    /*Persist Pessoa*/
                                                    
                                                    
                                                    List<Telefone> telefones = new ArrayList<>();

                                                    if(telefoneCliente.getText().length() == 8){
                                                        telefones.add(new Telefone(dddCliente.getText(), telefoneCliente.getText(), TipoTelefone.Fixo));
                                                    }
                                                    else{
                                                        telefones.add(new Telefone(dddCliente.getText(), telefoneCliente.getText(), TipoTelefone.Celular));
                                                    }

                                                    if(ddd2Cliente.isVisible() && valida.validaDdd(ddd2Cliente.getText())){
                                                        if(valida.validaTelefone(telefone2Cliente.getText())){
                                                            if(telefone2Cliente.getText().length() == 8){
                                                                telefones.add(new Telefone(ddd2Cliente.getText(), telefone2Cliente.getText(), TipoTelefone.Fixo));
                                                            }
                                                            else{
                                                                telefones.add(new Telefone(ddd2Cliente.getText(), telefone2Cliente.getText(), TipoTelefone.Celular));
                                                            }
                                                        }
                                                    }

                                                    if(ddd3Cliente.isVisible() && valida.validaDdd(ddd3Cliente.getText())){
                                                        if(valida.validaTelefone(telefone3Cliente.getText())){
                                                            if(telefone3Cliente.getText().length() == 8){
                                                                telefones.add(new Telefone(ddd3Cliente.getText(), telefone3Cliente.getText(), TipoTelefone.Fixo));
                                                            }
                                                            else{
                                                                telefones.add(new Telefone(ddd3Cliente.getText(), telefone3Cliente.getText(), TipoTelefone.Celular));
                                                            }
                                                        }
                                                    }

                                                    Calendar c = Calendar.getInstance();
                                                    int day   = Integer.parseInt(dataNascimentoCliente.getText().substring(0, 2));
                                                    int month = Integer.parseInt(dataNascimentoCliente.getText().substring(3, 5));
                                                    int year  = Integer.parseInt(dataNascimentoCliente.getText().substring(6, 10));
                                                    c.set(year, (month-1), day);
                                                    
                                                    if(!deleteCliente.isVisible()){
                                                        /*Persistence With Hibernate - Good Luck For Us!!!!*/

                                                        EstadoDAO      estDAO = new EstadoDAO();
                                                        CidadeDAO      cidDAO = new CidadeDAO();
                                                        BairroDAO      baiDAO = new BairroDAO();
                                                        EnderecoDAO    endDAO = new EnderecoDAO();
                                                        PessoaDAO      pesDAO = new PessoaDAO();
                                                        DocumentoDAO   docDAO = new DocumentoDAO();
                                                        TelefoneDAO    telDAO = new TelefoneDAO();

                                                        Cidade objCidade = null;
                                                        for(int i = 0; i < cidDAO.findAll().size(); i++){
                                                            if(cidDAO.findAll().get(i).getNome().equals(cidadeCliente.getText())){
                                                                objCidade = cidDAO.findAll().get(i);
                                                                break;
                                                            }
                                                        }

                                                        if(objCidade == null){
                                                            objCidade = new Cidade(cidadeCliente.getText());
                                                        }

                                                        Bairro objBairro = null;
                                                        for(int i = 0; i < baiDAO.findAll().size(); i++){
                                                            if(baiDAO.findAll().get(i).getNome().equals(bairroCliente.getText())){
                                                                objBairro = baiDAO.findAll().get(i);
                                                                break;
                                                            }
                                                        }
                                                        if(objBairro == null){
                                                            objBairro = new Bairro(bairroCliente.getText());
                                                        }

                                                        Endereco objEndereco       = new Endereco   (logradouroCliente.getText(), numeroCliente.getText(), cepCliente.getText());
                                                        Documento objDocumento     = new Documento  (documentoCliente.getText(), TipoDocumento.CPF);

                                                        Pessoa objPessoa = new Pessoa(nomeCliente.getText(), emailCliente.getText(), c, Sexo.valueOf(sexoCliente.getSelectedItem().toString())); 
                                                        EstadoDAO est = new EstadoDAO();
                                                        List<Estado> list = est.findByUf(estadosCliente.getSelectedItem().toString());

                                                        for(int i = 0; i < list.size(); i++){
                                                            list.get(i).addCidade(objCidade);
                                                            objCidade.addBairro(objBairro);
                                                            objBairro.addEndereco(objEndereco);
                                                            objPessoa.setEndereco(objEndereco);
                                                            objPessoa.setTelefone(telefones);
                                                            objPessoa.setDocumento(objDocumento);

                                                            pesDAO.merge(objPessoa);
                                                        }

                                                        acao = "insert";
                                                        Mensagem msg = new Mensagem();
                                                        Thread mensagemT = new Thread(msg);
                                                        mensagemT.start();

                                                        limpaCampos();

                                                        carregaDados();
                                                    
                                                    }
                                                    else{
                                                        /*Atualiza os Dados*/
                                                        PessoaDAO dao = new PessoaDAO();
                                                        CidadeDAO      cidDAO = new CidadeDAO();
                                                        BairroDAO      baiDAO = new BairroDAO();

                                                        Long id = Long.parseLong(
                                                            (String) tabelaCliente.getValueAt(tabelaCliente.getSelectedRow(), 0)
                                                        );

                                                        Pessoa pessoa = dao.getById(id);

                                                        pessoa.setNome(nomeCliente.getText());
                                                        pessoa.setEmail(emailCliente.getText());
                                                        pessoa.setDataNascimento(c);
                                                        pessoa.getDocumento().setNumero(documentoCliente.getText());

                                                        if(pessoa.getTelefones().size() == 1){
                                                            if(ddd2Cliente.isVisible() && valida.validaDdd(ddd2Cliente.getText())){
                                                                if(valida.validaTelefone(telefone2Cliente.getText())){
                                                                    pessoa.getTelefones().get(0).setDdd(dddCliente.getText());
                                                                    pessoa.getTelefones().get(0).setNumero(telefoneCliente.getText());

                                                                    if(ddd2Cliente.isVisible() && valida.validaDdd(ddd2Cliente.getText())){
                                                                        if(valida.validaTelefone(telefone2Cliente.getText())){
                                                                            if(telefone2Cliente.getText().length() == 8){
                                                                                pessoa.addTelefone(new Telefone(ddd2Cliente.getText(), telefone2Cliente.getText(), TipoTelefone.Fixo));
                                                                            }
                                                                            else{
                                                                                pessoa.addTelefone(new Telefone(ddd2Cliente.getText(), telefone2Cliente.getText(), TipoTelefone.Celular));
                                                                            }
                                                                        }
                                                                    }

                                                                    if(ddd3Cliente.isVisible() && valida.validaDdd(ddd3Cliente.getText())){
                                                                        if(valida.validaTelefone(telefone3Cliente.getText())){
                                                                            if(telefone3Cliente.getText().length() == 8){
                                                                                pessoa.addTelefone(new Telefone(ddd3Cliente.getText(), telefone3Cliente.getText(), TipoTelefone.Fixo));
                                                                            }
                                                                            else{
                                                                                pessoa.addTelefone(new Telefone(ddd3Cliente.getText(), telefone3Cliente.getText(), TipoTelefone.Celular));
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }

                                                        if(pessoa.getTelefones().size() == 2){
                                                            if(ddd2Cliente.isVisible() && valida.validaDdd(ddd2Cliente.getText())){
                                                                if(valida.validaTelefone(telefone2Cliente.getText())){
                                                                    pessoa.getTelefones().get(0).setDdd(dddCliente.getText());
                                                                    pessoa.getTelefones().get(0).setNumero(telefoneCliente.getText());

                                                                    pessoa.getTelefones().get(1).setDdd(ddd2Cliente.getText());
                                                                    pessoa.getTelefones().get(1).setNumero(telefone2Cliente.getText());

                                                                    if(ddd3Cliente.isVisible() && valida.validaDdd(ddd3Cliente.getText())){
                                                                        if(valida.validaTelefone(telefone3Cliente.getText())){
                                                                            if(telefone3Cliente.getText().length() == 8){
                                                                                pessoa.addTelefone(new Telefone(ddd3Cliente.getText(), telefone3Cliente.getText(), TipoTelefone.Fixo));
                                                                            }
                                                                            else{
                                                                                pessoa.addTelefone(new Telefone(ddd3Cliente.getText(), telefone3Cliente.getText(), TipoTelefone.Celular));
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }

                                                        if(pessoa.getTelefones().size() == 3){
                                                            if(ddd2Cliente.isVisible() && valida.validaDdd(ddd2Cliente.getText())){
                                                                if(valida.validaTelefone(telefone2Cliente.getText())){
                                                                    pessoa.getTelefones().get(0).setDdd(dddCliente.getText());
                                                                    pessoa.getTelefones().get(0).setNumero(telefoneCliente.getText());

                                                                    pessoa.getTelefones().get(1).setDdd(ddd2Cliente.getText());
                                                                    pessoa.getTelefones().get(1).setNumero(telefone2Cliente.getText());

                                                                    pessoa.getTelefones().get(2).setDdd(ddd3Cliente.getText());
                                                                    pessoa.getTelefones().get(2).setNumero(telefone3Cliente.getText());
                                                                }
                                                            }
                                                        }

                                                        pessoa.getEndereco().setLogradouro(logradouroCliente.getText());
                                                        pessoa.getEndereco().setNumero(numeroCliente.getText());
                                                        pessoa.getEndereco().setCep(cepCliente.getText());

                                                        boolean existeBairro = false;
                                                        Bairro exsBairro = null;
                                                        for(int i = 0; i < baiDAO.findAll().size(); i++){

                                                            if(baiDAO.findAll().get(i).getNome().equals(bairroCliente.getText())){
                                                                existeBairro = true;
                                                                exsBairro = baiDAO.findAll().get(i);
                                                                break;
                                                            }
                                                        }

                                                        if(existeBairro){
                                                            exsBairro.addEndereco(pessoa.getEndereco());
                                                        }else{
                                                            new Bairro(bairroCliente.getText()).addEndereco(pessoa.getEndereco());
                                                        }

                                                        boolean existeCidade = false;
                                                        Cidade exsCidade = null;
                                                        for(int i = 0; i < cidDAO.findAll().size(); i++){

                                                            if(cidDAO.findAll().get(i).getNome().equals(cidadeCliente.getText())){
                                                                existeCidade = true;
                                                                exsCidade = cidDAO.findAll().get(i);
                                                                break;
                                                            }
                                                        }

                                                        if(existeCidade){
                                                            exsCidade.addBairro(pessoa.getEndereco().getBairro());
                                                        }else{
                                                            new Cidade(cidadeCliente.getText()).addBairro(pessoa.getEndereco().getBairro());

                                                        }

                                                        EstadoDAO estDao = new EstadoDAO();
                                                        Estado atualEstado = null;
                                                        List<Estado> list = estDao.findByUf(estadosCliente.getSelectedItem().toString());
                                                        for(int i = 0; i < list.size(); i++){
                                                            atualEstado = list.get(i);
                                                        }

                                                        atualEstado.addCidade(pessoa.getEndereco().getBairro().getCidade());

                                                        
                                                        pessoa.setDataNascimento(c);
                                                        pessoa.setSexo(Sexo.valueOf(sexoCliente.getSelectedItem().toString()));

                                                        dao.merge(pessoa);

                                                        acao = "edit";
                                                        Mensagem msg = new Mensagem();
                                                        Thread mensagemT = new Thread(msg);
                                                        mensagemT.start();

                                                        limpaCampos();
                                                        carregaDados();
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
        preencheFormulario();
    }//GEN-LAST:event_tabelaClienteMouseClicked

    private void deleteClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteClienteMouseClicked
        PessoaDAO dao = new PessoaDAO();
        Long id = Long.parseLong(
            (String) tabelaCliente.getValueAt(tabelaCliente.getSelectedRow(), 0)
        );

        dao.removeById(id);
        acao = "delete";
        Mensagem msg = new Mensagem();
        Thread mensagem = new Thread(msg);
        mensagem.start();
        limpaCampos();
        carregaDados();
    }//GEN-LAST:event_deleteClienteMouseClicked

    private void addTelefoneFornecedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addTelefoneFornecedorMouseClicked
        if(labelTelefone2Fornecedor.isVisible()){
            labelTelefone3Fornecedor.setEnabled(true);
            ddd3Fornecedor.setEnabled(true);
            telefone3Fornecedor.setEnabled(true);
            delTel3Fornecedor.setEnabled(true);
        }
        else{
            labelTelefone2Fornecedor.setEnabled(true);
            ddd2Fornecedor.setEnabled(true);
            telefone2Fornecedor.setEnabled(true);
            delTel2Fornecedor.setEnabled(true);
        }
    }//GEN-LAST:event_addTelefoneFornecedorMouseClicked

    private void delTel2FornecedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_delTel2FornecedorMouseClicked
        Validacao valida = new Validacao();
        if(tabelaFornecedor.getSelectedRow() > -1){
            if(valida.validaDdd(ddd2Fornecedor.getText()) && valida.validaTelefone(telefone2Fornecedor.getText())){
                TelefoneDAO dao = new TelefoneDAO();
                PessoaDAO pes = new PessoaDAO();
                Long id = Long.parseLong((String) tabelaFornecedor.getValueAt(tabelaFornecedor.getSelectedRow(), 0));

                Pessoa pessoa = pes.getById(id);

                for(int i = 0; i < pessoa.getTelefones().size(); i++){
                    if(pessoa.getTelefones().get(i).getDdd().equals(ddd2Fornecedor.getText())
                        && pessoa.getTelefones().get(i).getNumero().equals(telefone2Fornecedor.getText())){
                        dao.removeById(pessoa.getTelefones().get(i).getIdTelefone());
                    }
                }

                preencheFormulario();
            }
        }
        labelTelefone2Fornecedor.setEnabled(false);
        ddd2Fornecedor.setEnabled(false);
        ddd2Fornecedor.setText(null);
        telefone2Fornecedor.setEnabled(false);
        telefone2Fornecedor.setText(null);
        delTel2Fornecedor.setEnabled(false);
    }//GEN-LAST:event_delTel2FornecedorMouseClicked

    private void delTel3FornecedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_delTel3FornecedorMouseClicked
       Validacao valida = new Validacao();
        if(tabelaFornecedor.getSelectedRow() > -1){
            if(valida.validaDdd(ddd3Fornecedor.getText()) && valida.validaTelefone(telefone3Fornecedor.getText())){
                TelefoneDAO dao = new TelefoneDAO();
                PessoaDAO pes = new PessoaDAO();
                Long id = Long.parseLong((String) tabelaFornecedor.getValueAt(tabelaFornecedor.getSelectedRow(), 0));

                Pessoa pessoa = pes.getById(id);

                for(int i = 0; i < pessoa.getTelefones().size(); i++){
                    if(pessoa.getTelefones().get(i).getDdd().equals(ddd3Fornecedor.getText())
                        && pessoa.getTelefones().get(i).getNumero().equals(telefone3Fornecedor.getText())){
                        dao.removeById(pessoa.getTelefones().get(i).getIdTelefone());
                    }
                }

                preencheFormulario();
            }
        }
        labelTelefone3Fornecedor.setEnabled(false);
        ddd3Fornecedor.setEnabled(false);
        ddd3Fornecedor.setText(null);
        telefone3Fornecedor.setEnabled(false);
        telefone3Fornecedor.setText(null);
        delTel3Fornecedor.setEnabled(false);
    }//GEN-LAST:event_delTel3FornecedorMouseClicked

    private void refreshFornecedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refreshFornecedorMouseClicked
        limpaCampos();
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


                                                List<Telefone> telefones = new ArrayList<>();

                                                if(telefoneFornecedor.getText().length() == 8){
                                                    telefones.add(new Telefone(dddFornecedor.getText(), telefoneFornecedor.getText(), TipoTelefone.Fixo));
                                                }
                                                else{
                                                    telefones.add(new Telefone(dddFornecedor.getText(), telefoneFornecedor.getText(), TipoTelefone.Celular));
                                                }

                                                if(ddd2Fornecedor.isVisible() && valida.validaDdd(ddd2Fornecedor.getText())){
                                                    if(valida.validaTelefone(telefone2Fornecedor.getText())){
                                                        if(telefone2Fornecedor.getText().length() == 8){
                                                            telefones.add(new Telefone(ddd2Fornecedor.getText(), telefone2Fornecedor.getText(), TipoTelefone.Fixo));
                                                        }
                                                        else{
                                                            telefones.add(new Telefone(ddd2Fornecedor.getText(), telefone2Fornecedor.getText(), TipoTelefone.Celular));
                                                        }
                                                    }
                                                }

                                                if(ddd3Fornecedor.isVisible() && valida.validaDdd(ddd3Fornecedor.getText())){
                                                    if(valida.validaTelefone(telefone3Fornecedor.getText())){
                                                        if(telefone3Fornecedor.getText().length() == 8){
                                                            telefones.add(new Telefone(ddd3Fornecedor.getText(), telefone3Fornecedor.getText(), TipoTelefone.Fixo));
                                                        }
                                                        else{
                                                            telefones.add(new Telefone(ddd3Fornecedor.getText(), telefone3Fornecedor.getText(), TipoTelefone.Celular));
                                                        }
                                                    }
                                                }

                                                Calendar c = Calendar.getInstance();
                                                c.set(0, 0, 0);

                                                if(!deleteCliente.isVisible()){
                                                    /*Persistence With Hibernate - Good Luck For Us!!!!*/

                                                    EstadoDAO      estDAO = new EstadoDAO();
                                                    CidadeDAO      cidDAO = new CidadeDAO();
                                                    BairroDAO      baiDAO = new BairroDAO();
                                                    EnderecoDAO    endDAO = new EnderecoDAO();
                                                    PessoaDAO      pesDAO = new PessoaDAO();
                                                    DocumentoDAO   docDAO = new DocumentoDAO();
                                                    TelefoneDAO    telDAO = new TelefoneDAO();

                                                    Cidade objCidade = null;
                                                    for(int i = 0; i < cidDAO.findAll().size(); i++){
                                                        if(cidDAO.findAll().get(i).getNome().equals(cidadeFornecedor.getText())){
                                                            objCidade = cidDAO.findAll().get(i);
                                                            break;
                                                        }
                                                    }

                                                    if(objCidade == null){
                                                        objCidade = new Cidade(cidadeFornecedor.getText());
                                                    }

                                                    Bairro objBairro = null;
                                                    for(int i = 0; i < baiDAO.findAll().size(); i++){
                                                        if(baiDAO.findAll().get(i).getNome().equals(bairroFornecedor.getText())){
                                                            objBairro = baiDAO.findAll().get(i);
                                                            break;
                                                        }
                                                    }
                                                    if(objBairro == null){
                                                        objBairro = new Bairro(bairroFornecedor.getText());
                                                    }

                                                    Endereco objEndereco = new Endereco   (logradouroFornecedor.getText(), numeroFornecedor.getText(), cepFornecedor.getText());
                                                    Documento objDocumento = new Documento  (documentoFornecedor.getText(), TipoDocumento.CNPJ);

                                                    Pessoa objPessoa = new Pessoa(nomeFornecedor.getText(), emailFornecedor.getText(), c, Sexo.Não_Definido); 
                                                    EstadoDAO est = new EstadoDAO();
                                                    List<Estado> list = est.findByUf(estadosFornecedor.getSelectedItem().toString());

                                                    for(int i = 0; i < list.size(); i++){
                                                        list.get(i).addCidade(objCidade);
                                                        objCidade.addBairro(objBairro);
                                                        objBairro.addEndereco(objEndereco);
                                                        objPessoa.setEndereco(objEndereco);
                                                        objPessoa.setTelefone(telefones);
                                                        objPessoa.setDocumento(objDocumento);

                                                        pesDAO.merge(objPessoa);
                                                    }

                                                    acao = "insert";
                                                    Mensagem msg = new Mensagem();
                                                    Thread mensagemT = new Thread(msg);
                                                    mensagemT.start();

                                                    limpaCampos();

                                                    carregaDados();

                                                }
                                                else{
                                                    /*Atualiza os Dados*/
                                                    PessoaDAO dao = new PessoaDAO();
                                                    CidadeDAO      cidDAO = new CidadeDAO();
                                                    BairroDAO      baiDAO = new BairroDAO();

                                                    Long id = Long.parseLong(
                                                        (String) tabelaFornecedor.getValueAt(tabelaFornecedor.getSelectedRow(), 0)
                                                    );

                                                    Pessoa pessoa = dao.getById(id);

                                                    pessoa.setNome(nomeFornecedor.getText());
                                                    pessoa.setEmail(emailFornecedor.getText());
                                                    pessoa.setDataNascimento(c);
                                                    pessoa.getDocumento().setNumero(documentoFornecedor.getText());

                                                    if(pessoa.getTelefones().size() == 1){
                                                        if(ddd2Fornecedor.isVisible() && valida.validaDdd(ddd2Fornecedor.getText())){
                                                            if(valida.validaTelefone(telefone2Fornecedor.getText())){
                                                                pessoa.getTelefones().get(0).setDdd(dddFornecedor.getText());
                                                                pessoa.getTelefones().get(0).setNumero(telefoneFornecedor.getText());

                                                                if(ddd2Fornecedor.isVisible() && valida.validaDdd(ddd2Fornecedor.getText())){
                                                                    if(valida.validaTelefone(telefone2Fornecedor.getText())){
                                                                        if(telefone2Fornecedor.getText().length() == 8){
                                                                            pessoa.addTelefone(new Telefone(ddd2Fornecedor.getText(), telefone2Fornecedor.getText(), TipoTelefone.Fixo));
                                                                        }
                                                                        else{
                                                                            pessoa.addTelefone(new Telefone(ddd2Fornecedor.getText(), telefone2Fornecedor.getText(), TipoTelefone.Celular));
                                                                        }
                                                                    }
                                                                }

                                                                if(ddd3Fornecedor.isVisible() && valida.validaDdd(ddd3Fornecedor.getText())){
                                                                    if(valida.validaTelefone(telefone3Fornecedor.getText())){
                                                                        if(telefone3Fornecedor.getText().length() == 8){
                                                                            pessoa.addTelefone(new Telefone(ddd3Fornecedor.getText(), telefone3Fornecedor.getText(), TipoTelefone.Fixo));
                                                                        }
                                                                        else{
                                                                            pessoa.addTelefone(new Telefone(ddd3Fornecedor.getText(), telefone3Fornecedor.getText(), TipoTelefone.Celular));
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }

                                                    if(pessoa.getTelefones().size() == 2){
                                                        if(ddd2Fornecedor.isVisible() && valida.validaDdd(ddd2Fornecedor.getText())){
                                                            if(valida.validaTelefone(telefone2Fornecedor.getText())){
                                                                pessoa.getTelefones().get(0).setDdd(dddFornecedor.getText());
                                                                pessoa.getTelefones().get(0).setNumero(telefoneFornecedor.getText());

                                                                pessoa.getTelefones().get(1).setDdd(ddd2Fornecedor.getText());
                                                                pessoa.getTelefones().get(1).setNumero(telefone2Fornecedor.getText());

                                                                if(ddd3Fornecedor.isVisible() && valida.validaDdd(ddd3Fornecedor.getText())){
                                                                    if(valida.validaTelefone(telefone3Fornecedor.getText())){
                                                                        if(telefone3Fornecedor.getText().length() == 8){
                                                                            pessoa.addTelefone(new Telefone(ddd3Fornecedor.getText(), telefone3Fornecedor.getText(), TipoTelefone.Fixo));
                                                                        }
                                                                        else{
                                                                            pessoa.addTelefone(new Telefone(ddd3Fornecedor.getText(), telefone3Fornecedor.getText(), TipoTelefone.Celular));
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }

                                                    if(pessoa.getTelefones().size() == 3){
                                                        if(ddd2Fornecedor.isVisible() && valida.validaDdd(ddd2Fornecedor.getText())){
                                                            if(valida.validaTelefone(telefone2Fornecedor.getText())){
                                                                pessoa.getTelefones().get(0).setDdd(dddFornecedor.getText());
                                                                pessoa.getTelefones().get(0).setNumero(telefoneFornecedor.getText());

                                                                pessoa.getTelefones().get(1).setDdd(ddd2Fornecedor.getText());
                                                                pessoa.getTelefones().get(1).setNumero(telefone2Fornecedor.getText());

                                                                pessoa.getTelefones().get(2).setDdd(ddd3Fornecedor.getText());
                                                                pessoa.getTelefones().get(2).setNumero(telefone3Fornecedor.getText());
                                                            }
                                                        }
                                                    }

                                                    pessoa.getEndereco().setLogradouro(logradouroFornecedor.getText());
                                                    pessoa.getEndereco().setNumero(numeroFornecedor.getText());
                                                    pessoa.getEndereco().setCep(cepFornecedor.getText());

                                                    boolean existeBairro = false;
                                                    Bairro exsBairro = null;
                                                    for(int i = 0; i < baiDAO.findAll().size(); i++){

                                                        if(baiDAO.findAll().get(i).getNome().equals(bairroFornecedor.getText())){
                                                            existeBairro = true;
                                                            exsBairro = baiDAO.findAll().get(i);
                                                            break;
                                                        }
                                                    }

                                                    if(existeBairro){
                                                        exsBairro.addEndereco(pessoa.getEndereco());
                                                    }else{
                                                        new Bairro(bairroFornecedor.getText()).addEndereco(pessoa.getEndereco());
                                                    }

                                                    boolean existeCidade = false;
                                                    Cidade exsCidade = null;
                                                    for(int i = 0; i < cidDAO.findAll().size(); i++){

                                                        if(cidDAO.findAll().get(i).getNome().equals(cidadeFornecedor.getText())){
                                                            existeCidade = true;
                                                            exsCidade = cidDAO.findAll().get(i);
                                                            break;
                                                        }
                                                    }

                                                    if(existeCidade){
                                                        exsCidade.addBairro(pessoa.getEndereco().getBairro());
                                                    }else{
                                                        new Cidade(cidadeFornecedor.getText()).addBairro(pessoa.getEndereco().getBairro());

                                                    }

                                                    EstadoDAO estDao = new EstadoDAO();
                                                    Estado atualEstado = null;
                                                    List<Estado> list = estDao.findByUf(estadosFornecedor.getSelectedItem().toString());
                                                    for(int i = 0; i < list.size(); i++){
                                                        atualEstado = list.get(i);
                                                    }

                                                    atualEstado.addCidade(pessoa.getEndereco().getBairro().getCidade());


                                                    pessoa.setDataNascimento(c);

                                                    dao.merge(pessoa);

                                                    acao = "edit";
                                                    Mensagem msg = new Mensagem();
                                                    Thread mensagemT = new Thread(msg);
                                                    mensagemT.start();

                                                    limpaCampos();
                                                    carregaDados();
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
        preencheFormulario();
    }//GEN-LAST:event_tabelaFornecedorMouseClicked

    private void deleteFornecedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteFornecedorMouseClicked
        PessoaDAO dao = new PessoaDAO();
        Long id = Long.parseLong(
            (String) tabelaFornecedor.getValueAt(tabelaFornecedor.getSelectedRow(), 0)
        );

        dao.removeById(id);
        acao = "delete";
        Mensagem msg = new Mensagem();
        Thread mensagem = new Thread(msg);
        mensagem.start();
        limpaCampos();
        carregaDados();
    }//GEN-LAST:event_deleteFornecedorMouseClicked

    private void imagemProdutoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imagemProdutoMouseClicked
        JFileChooser arquivo = new JFileChooser();

        int retorno = arquivo.showOpenDialog(arquivo);
        

        if (retorno == JFileChooser.APPROVE_OPTION) {
            caminhoArquivo = arquivo.getSelectedFile().getAbsolutePath();
            nomeArquivo = arquivo.getSelectedFile().getName();
            File selecionado = arquivo.getSelectedFile();

            String extensao = getExtensaoArquivo(selecionado);

//            if (!extensao.equals("png") || !extensao.equals("jpg")) {
//                    JOptionPane.showMessageDialog(null, "Arquivo Não Suportado!");
//                }
//            else{
                try {
                    BufferedImage original = ImageIO.read(new File(caminhoArquivo));
                    int tipoImagem = original.getType();
                    BufferedImage rImg = resizeImage(original, tipoImagem);
                    
//                    ImagemVIsualizacao.fundo = caminhoArquivo;
//                    JFrame preImg = new ImagemVIsualizacao();
//                    
//                    
//                    preImg.setEnabled(true);
                    imagemProduto.setText(null);
                    imagemProduto.setIcon(new javax.swing.ImageIcon(original));
                } catch (IOException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
                
//            }

        }
    }//GEN-LAST:event_imagemProdutoMouseClicked

    private void refreshProdutoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refreshProdutoMouseClicked
        limpaCampos();
        deleteProduto.setEnabled(false);
        confirmProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/checked (1).png")));
    }//GEN-LAST:event_refreshProdutoMouseClicked

    private void confirmProdutoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_confirmProdutoMouseClicked
          Validacao valida = new Validacao();
          
          if(valida.validaImagem(imagemProduto.getText())){
                  if(valida.validaPreco(precoProduto.getText())){
                        if(valida.validaTexto(descricaoProduto.getText())){
                            
                            
                            if(!deleteProduto.isVisible()){
                                FileInputStream origem;
                                FileOutputStream destino;
                                FileChannel fcOrigem;
                                FileChannel fcDestino;

                                /*Persistindo Produto*/
                                ProdutoDAO dao = new ProdutoDAO();

                                 try {

                                    URL resource = Principal.class.getResource("/produtos/");

                                    Produto objProduto = new Produto(
                                      descricaoProduto.getText(), Integer.parseInt(quantidadeProduto.getValue().toString()),
                                      Double.parseDouble(precoProduto.getText().replace("R$", "").replace(",", ".")), (Paths.get(resource.toURI()).toFile()+ "/" +nomeArquivo));

                                    origem = new FileInputStream(caminhoArquivo);
                                    destino = new FileOutputStream( Paths.get(resource.toURI()).toFile()+ "/" +nomeArquivo);

                                    fcOrigem = origem.getChannel();
                                    fcDestino = destino.getChannel();

                                    fcOrigem.transferTo(0, fcOrigem.size(), fcDestino);

                                    dao.persist(objProduto);

                                    origem.close();
                                    destino.close();
                                } catch (FileNotFoundException ex) {
                                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (IOException ex) {
                                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (URISyntaxException ex) {
                                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                                }



                                acao = "insert";
                                Mensagem msg = new Mensagem();
                                Thread mensagemT = new Thread(msg);
                                mensagemT.start();

                                limpaCampos();

                                carregaDados();
                            }
                            else{
                                /*Atualiza os Dados*/
                                ProdutoDAO dao = new ProdutoDAO();

                                Long id = Long.parseLong(
                                    (String) tabelaProduto.getValueAt(tabelaProduto.getSelectedRow(), 0)
                                );

                                Produto produto = dao.getById(id);

                                produto.setQuantidade(Integer.parseInt(quantidadeProduto.getValue().toString()));
                                produto.setValorUnitario(Double.parseDouble(precoProduto.getText().replace("R$", "").replace(",", ".")));
                                produto.setDescricao(descricaoProduto.getText());
                                
                                if(!caminhoArquivo.isEmpty()){
                                    FileInputStream origem;
                                    FileOutputStream destino;
                                    FileChannel fcOrigem;
                                    FileChannel fcDestino;
                                    
                                    File imagemAntiga = new File(produto.getImagem());
                                    imagemAntiga.delete();
                                    
                                    try {
                                        URL resource = Principal.class.getResource("/produtos/");
                                        origem = new FileInputStream(caminhoArquivo);
                                        JOptionPane.showMessageDialog(null, caminhoArquivo);
                                        JOptionPane.showMessageDialog(null, nomeArquivo);
                                        destino = new FileOutputStream( Paths.get(resource.toURI()).toFile()+ "/" +nomeArquivo);
                                        produto.setImagem((Paths.get(resource.toURI()).toFile()+ "/" +nomeArquivo));
                                        
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
                                
                                
                                dao.merge(produto);

                                acao = "edit";
                                Mensagem msg = new Mensagem();
                                Thread mensagemT = new Thread(msg);
                                mensagemT.start();

                                limpaCampos();
                                carregaDados();
                            
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

    private void imagemProdutoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imagemProdutoMouseEntered
        Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR); 
        setCursor(cursor);
    }//GEN-LAST:event_imagemProdutoMouseEntered

    private void imagemProdutoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imagemProdutoMouseExited
        Cursor cursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR); 
        setCursor(cursor);
    }//GEN-LAST:event_imagemProdutoMouseExited

    private void tabelaProdutoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaProdutoMouseClicked
        preencheFormulario();
    }//GEN-LAST:event_tabelaProdutoMouseClicked

    private void deleteProdutoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteProdutoMouseClicked
        ProdutoDAO dao = new ProdutoDAO();
        Long id = Long.parseLong(
            (String) tabelaProduto.getValueAt(tabelaProduto.getSelectedRow(), 0)
        );
        
        File imagemAntiga = new File(dao.getById(id).getImagem());
        imagemAntiga.delete();
        
        dao.removeById(id);
        acao = "delete";
        Mensagem msg = new Mensagem();
        Thread mensagem = new Thread(msg);
        mensagem.start();
        limpaCampos();
        carregaDados();
    }//GEN-LAST:event_deleteProdutoMouseClicked

    private void calculaTrocoVendaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_calculaTrocoVendaMouseClicked
        DecimalFormat df = new DecimalFormat("0.00");
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
    }//GEN-LAST:event_calculaTrocoVendaMouseClicked

    private void radioVendaParceladoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_radioVendaParceladoMouseClicked
        if(!quantidadeVendaProduto.getText().equals("")){
            DecimalFormat df = new DecimalFormat("0.00");
            for(int i = 2; i <= 10; i++){
                Double valor = Double.parseDouble(totalVendaProduto.getText().replace(",", "."));
                parcelas.addItem(i+"X "+df.format((valor/i)));
            }
            if(Integer.parseInt(quantidadeVendaProduto.getText()) > 0){
                /**/

                labelParcelasVenda.setEnabled(true);
                parcelas.setEnabled(true);
                labelDataVencimentoParcela.setEnabled(true);
                dataVencimentoParcela.setEnabled(true);

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
                parcelas.setEnabled(false);
                labelDataVencimentoParcela.setEnabled(false);
                dataVencimentoParcela.setEnabled(false);

                labelPagoVenda.setEnabled(true);
                labelTotalVenda.setEnabled(true);
                labelTrocoVenda.setEnabled(true);

                valorPagoVenda.setEnabled(true);
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

    private void checkClienteCadastradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkClienteCadastradoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkClienteCadastradoActionPerformed

    private void checkClienteCadastradoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_checkClienteCadastradoMouseClicked
        if(checkClienteCadastrado.isSelected()){
            buscaDocumentoCliente.setEnabled(false);
            btnBuscaDocumentoCliente.setEnabled(false);
            nomeBuscaCliente.setText("cliente");
            nomeBuscaCliente.setEnabled(false);
        }
        else{
            buscaDocumentoCliente.setText(null);
            buscaDocumentoCliente.setEnabled(true);
            btnBuscaDocumentoCliente.setEnabled(true);
            nomeBuscaCliente.setText(null);
            nomeBuscaCliente.setEnabled(true);
        }
    }//GEN-LAST:event_checkClienteCadastradoMouseClicked

    private void btnBuscaDocumentoClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscaDocumentoClienteMouseClicked
        boolean encontrou = false;
        PessoaDAO dao = new PessoaDAO();

        List<Pessoa> pessoas = dao.findAll();

        for(int i = 0; i < pessoas.size(); i++){
            if(pessoas.get(i).getDocumento().getNumero().equals(buscaDocumentoCliente.getText())){
                nomeBuscaCliente.setText(pessoas.get(i).getNome());
                encontrou = true;
                break;
            }
        }

        if(!encontrou){
            JOptionPane.showMessageDialog(null, "Cliente não encontrado");
            nomeBuscaCliente.setText(null);
        }
    }//GEN-LAST:event_btnBuscaDocumentoClienteMouseClicked

    private void fecharVendaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fecharVendaMouseClicked
        Validacao valida = new Validacao();
        if(!nomeBuscaCliente.getText().equals("")){
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

                            ControllerPessoa controlPessoa = new ControllerPessoa();
                            ControllerFuncionario controlFuncionario = new ControllerFuncionario();
                            ControllerVendas controlVenda = new ControllerVendas();

                            Map<String, String> mapFuncionario = controlFuncionario.searchFuncionario(Login.nome);
                            Map<String, String> mapPessoa = controlPessoa.searchPessoa(buscaDocumentoCliente.getText());

                            controlVenda.vender(
                                Long.valueOf(mapPessoa.get("idPessoa")),
                                Long.valueOf(mapFuncionario.get("idFuncionario")),
                                TipoVenda.Vista,
                                produtosVenda,
                                quantidades,
                                Double.parseDouble(descontoVenda.getValue().toString())
                            );

                            menu = "vendas";
                            limpaCampos();

                            JOptionPane.showMessageDialog(null, "Venda Realizada com Sucesso!");
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Calcule o troco para prosseguir.");
                        }
                    }
                    else{
                        /*Crediario*/
                        if(valida.validaDataNascimento(dataVencimentoParcela.getText())){
                            int dia   = Integer.parseInt(dataVencimentoParcela.getText().substring(0, 2));
                            int mes   = Integer.parseInt(dataVencimentoParcela.getText().substring(3, 5));
                            int ano   = Integer.parseInt(dataVencimentoParcela.getText().substring(6, 10));

                            int [] quantidades = new int[tabelaVendaProduto.getRowCount()];

                            int quantidadeParcelas =
                            Integer.parseInt(parcelas.getSelectedItem().toString().substring(0, 1));

                            //
                            Long[] produtosVenda = new Long[tabelaVendaProduto.getRowCount()];

                            for(int i = 0; i < tabelaVendaProduto.getRowCount(); i++){
                                Long id = Long.parseLong(tabelaVendaProduto.getValueAt(i, 0).toString());
                                int quantidade = Integer.parseInt(tabelaVendaProduto.getValueAt(i, 3).toString());

                                quantidades[i] = quantidade;
                                produtosVenda[i] = id;

                            }

                            ControllerPessoa controlPessoa = new ControllerPessoa();
                            ControllerFuncionario controlFuncionario = new ControllerFuncionario();
                            ControllerVendas controlVenda = new ControllerVendas();

                            Map<String, String> mapFuncionario = controlFuncionario.searchFuncionario(Login.nome);
                            Map<String, String> mapPessoa = controlPessoa.searchPessoa(buscaDocumentoCliente.getText());

                            controlVenda.vender(
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

                            menu = "vendas";
                            limpaCampos();

                            JOptionPane.showMessageDialog(null, "Venda Realizada com Sucesso!");
                        }
                        else{
                            JOptionPane.showMessageDialog(null,"Informa a data de vencimento da primeira parcela.");
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

    private void deleteProdutoVendaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteProdutoVendaMouseClicked
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

        if(quantidadeVendaProduto.getText().equals("0")){
            redefineEstilo();
        }
    }//GEN-LAST:event_deleteProdutoVendaMouseClicked

    private void procuraProdutoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_procuraProdutoMouseClicked
        Long id = Long.parseLong(codigoProduto.getText());

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
                        if(tabelaVendaProduto.getValueAt(i, 0).equals(codigoProduto.getText())){
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
                    codigoProduto.setText(null);
                }
            }
            catch(NullPointerException e){
                JOptionPane.showMessageDialog(null, "Produto não encontrado");
                codigoProduto.setText(null);
            }
    }//GEN-LAST:event_procuraProdutoMouseClicked

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
        codigoProduto.setText(null);
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
                if ("Windows".equals(info.getName())) {
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
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> acessos;
    private javax.swing.JButton addTelCliente;
    private javax.swing.JButton addTelefone;
    private javax.swing.JButton addTelefoneFornecedor;
    private javax.swing.JTextField bairro;
    private javax.swing.JTextField bairroCliente;
    private javax.swing.JTextField bairroFornecedor;
    private javax.swing.JButton btnBuscaDocumentoCliente;
    private javax.swing.JButton btnCliente;
    private javax.swing.JButton btnCompra;
    private javax.swing.JButton btnFornecedor;
    private javax.swing.JButton btnProduto;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnRelatorio;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnUsuario;
    private javax.swing.JButton btnVenda;
    private javax.swing.JFormattedTextField buscaDocumentoCliente;
    private javax.swing.JButton calculaTrocoVenda;
    private javax.swing.JTextField cep;
    private javax.swing.JTextField cepCliente;
    private javax.swing.JTextField cepFornecedor;
    private javax.swing.JCheckBox checkClienteCadastrado;
    private javax.swing.JCheckBox checkSenha;
    private javax.swing.JTextField cidade;
    private javax.swing.JTextField cidadeCliente;
    private javax.swing.JTextField cidadeFornecedor;
    private javax.swing.JPanel clientes;
    private javax.swing.JTextField codigoProduto;
    private javax.swing.JPanel compras;
    private javax.swing.JButton confirm;
    private javax.swing.JButton confirmCliente;
    private javax.swing.JButton confirmFornecedor;
    private javax.swing.JButton confirmProduto;
    private javax.swing.JTextField dataNascimento;
    private javax.swing.JTextField dataNascimentoCliente;
    private javax.swing.JFormattedTextField dataVencimentoParcela;
    private javax.swing.JTextField ddd;
    private javax.swing.JTextField ddd2;
    private javax.swing.JTextField ddd2Cliente;
    private javax.swing.JTextField ddd2Fornecedor;
    private javax.swing.JTextField ddd3;
    private javax.swing.JTextField ddd3Cliente;
    private javax.swing.JTextField ddd3Fornecedor;
    private javax.swing.JTextField dddCliente;
    private javax.swing.JTextField dddFornecedor;
    private javax.swing.JButton delTel2;
    private javax.swing.JButton delTel2Cliente;
    private javax.swing.JButton delTel2Fornecedor;
    private javax.swing.JButton delTel3;
    private javax.swing.JButton delTel3Cliente;
    private javax.swing.JButton delTel3Fornecedor;
    private javax.swing.JButton delete;
    private javax.swing.JButton deleteCliente;
    private javax.swing.JButton deleteFornecedor;
    private javax.swing.JButton deleteProduto;
    private javax.swing.JButton deleteProdutoVenda;
    private javax.swing.JSpinner descontoVenda;
    private javax.swing.JTextArea descricaoProduto;
    private javax.swing.JTextField documento;
    private javax.swing.JTextField documentoCliente;
    private javax.swing.JTextField documentoFornecedor;
    private javax.swing.JLabel eagles;
    private javax.swing.JTextField email;
    private javax.swing.JTextField emailCliente;
    private javax.swing.JTextField emailFornecedor;
    private javax.swing.JComboBox<String> estados;
    private javax.swing.JComboBox<String> estadosCliente;
    private javax.swing.JComboBox<String> estadosFornecedor;
    private javax.swing.JButton fecharVenda;
    private javax.swing.JPanel fornecedores;
    private javax.swing.JLabel imagemProduto;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel labelDataVencimentoParcela;
    private javax.swing.JLabel labelPagoVenda;
    private javax.swing.JLabel labelParcelasVenda;
    private javax.swing.JLabel labelTelefone2;
    private javax.swing.JLabel labelTelefone2Cliente;
    private javax.swing.JLabel labelTelefone2Fornecedor;
    private javax.swing.JLabel labelTelefone3;
    private javax.swing.JLabel labelTelefone3Cliente;
    private javax.swing.JLabel labelTelefone3Fornecedor;
    private javax.swing.JLabel labelTotalVenda;
    private javax.swing.JLabel labelTrocoVenda;
    private javax.swing.JLabel logado;
    private javax.swing.JLabel logo;
    private javax.swing.JTextField logradouro;
    private javax.swing.JTextField logradouroCliente;
    private javax.swing.JTextField logradouroFornecedor;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTextField nome;
    private javax.swing.JTextField nomeBuscaCliente;
    private javax.swing.JTextField nomeCliente;
    private javax.swing.JTextField nomeFornecedor;
    private javax.swing.JTextField numero;
    private javax.swing.JTextField numeroCliente;
    private javax.swing.JTextField numeroFornecedor;
    private javax.swing.ButtonGroup opcoesPagamento;
    private javax.swing.JComboBox<String> parcelas;
    private javax.swing.JTextField precoProduto;
    private javax.swing.JButton procuraProduto;
    private javax.swing.JPanel produtos;
    private javax.swing.JSpinner quantidadeProduto;
    private javax.swing.JTextField quantidadeVendaProduto;
    private javax.swing.JRadioButton radioVendaParcelado;
    private javax.swing.JRadioButton radioVendaVista;
    private javax.swing.JButton refreshCliente;
    private javax.swing.JButton refreshFornecedor;
    private javax.swing.JButton refreshProduto;
    private javax.swing.JPanel relatorios;
    private javax.swing.JPasswordField senha;
    private javax.swing.JComboBox<String> sexo;
    private javax.swing.JComboBox<String> sexoCliente;
    private javax.swing.JTable tabelaCliente;
    private javax.swing.JTable tabelaFornecedor;
    private javax.swing.JTable tabelaProduto;
    private javax.swing.JTable tabelaUsuario;
    private javax.swing.JTable tabelaVendaProduto;
    private javax.swing.JTextField telefone;
    private javax.swing.JTextField telefone2;
    private javax.swing.JTextField telefone2Cliente;
    private javax.swing.JTextField telefone2Fornecedor;
    private javax.swing.JTextField telefone3;
    private javax.swing.JTextField telefone3Cliente;
    private javax.swing.JTextField telefone3Fornecedor;
    private javax.swing.JTextField telefoneCliente;
    private javax.swing.JTextField telefoneFornecedor;
    private javax.swing.JTextField totalVendaProduto;
    private javax.swing.JTextField usuario;
    private javax.swing.JPanel usuarios;
    private javax.swing.JTextField valorPagoVenda;
    private javax.swing.JTextField valorTotalVenda;
    private javax.swing.JTextField valorTrocoVenda;
    private javax.swing.JPanel vendas;
    // End of variables declaration//GEN-END:variables
}
