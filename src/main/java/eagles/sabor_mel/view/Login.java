package eagles.sabor_mel.view;

import eagles.sabor_mel.control.ControllerFuncionario;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import javax.persistence.NoResultException;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

/**
 *
 * @author Tiago Lima Villalobos
 */
public class Login extends javax.swing.JFrame {
    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();
        if(checkCapsLock()){
            avisoCapsLock.setText("TECLA CAPS LOCK ATIVADA!");
        }
        else{
            avisoCapsLock.setText(null);
        }
        this.setLocationRelativeTo(null);
        login.requestFocus();
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
    }
    
    private boolean checkCapsLock(){
        boolean caps = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
        
        return caps;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        logo = new javax.swing.JLabel();
        titulo = new javax.swing.JLabel();
        labelLogin = new javax.swing.JLabel();
        login = new javax.swing.JTextField();
        labelSenha = new javax.swing.JLabel();
        senha = new javax.swing.JPasswordField();
        btnEntrar = new javax.swing.JButton();
        lblSair = new javax.swing.JLabel();
        avisoCapsLock = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/new_logo.png"))); // NOI18N

        titulo.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        titulo.setText("Login");

        labelLogin.setText("Usuário");

        login.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                loginKeyTyped(evt);
            }
        });

        labelSenha.setText("Senha");

        senha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                senhaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                senhaKeyTyped(evt);
            }
        });

        btnEntrar.setText("Entrar");
        btnEntrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEntrarMouseClicked(evt);
            }
        });

        lblSair.setText("sair...");
        lblSair.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblSair.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSairMouseClicked(evt);
            }
        });

        avisoCapsLock.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        avisoCapsLock.setForeground(new java.awt.Color(204, 0, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(titulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(logo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(labelLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(labelSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(login)
                                    .addComponent(senha, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(avisoCapsLock)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnEntrar)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblSair)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logo)
                .addGap(18, 18, 18)
                .addComponent(titulo)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelLogin)
                            .addComponent(login, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(senha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelSenha))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEntrar)
                        .addContainerGap(23, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSair)
                            .addComponent(avisoCapsLock))
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEntrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrarMouseClicked
        logar();
    }//GEN-LAST:event_btnEntrarMouseClicked

    public void logar() throws HeadlessException {
        if(!login.getText().equals("")){
            if(!senha.getText().equals("")){
                try {
                    ControllerFuncionario.searchFuncionario(login.getText(), senha.getText());
                    
                    this.dispose();
                    new Principal().setVisible(true);
                    
                }
                catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                    JOptionPane.showMessageDialog(null, "Erro no Algoritmo!\nEntrar em contato com o Administrador.");
                }
                catch (NoResultException nre){
                    JOptionPane.showMessageDialog(null, "Usuário não encontrado!");
                    login.setText(null);
                    senha.setText(null);
                    
                    login.requestFocus();
                }
                
            }
            else{
                JOptionPane.showMessageDialog(null, "Informe a senha!");
                senha.requestFocus();
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Informe o usuário!");
            login.requestFocus();
        }
    }

    private void lblSairMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSairMouseClicked
        System.exit(0);
    }//GEN-LAST:event_lblSairMouseClicked

    private void loginKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_loginKeyTyped
       if(checkCapsLock()){
            avisoCapsLock.setText("TECLA CAPS LOCK ATIVADA!");
        }
        else{
            avisoCapsLock.setText(null);
        }
    }//GEN-LAST:event_loginKeyTyped

    private void senhaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_senhaKeyTyped
        if(checkCapsLock()){
            avisoCapsLock.setText("TECLA CAPS LOCK ATIVADA!");
        }
        else{
            avisoCapsLock.setText(null);
        }
    }//GEN-LAST:event_senhaKeyTyped

    private void senhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_senhaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) { 
            logar();
        } 
    }//GEN-LAST:event_senhaKeyPressed

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel avisoCapsLock;
    private javax.swing.JButton btnEntrar;
    private javax.swing.JLabel labelLogin;
    private javax.swing.JLabel labelSenha;
    private javax.swing.JLabel lblSair;
    public static javax.swing.JTextField login;
    private javax.swing.JLabel logo;
    public static javax.swing.JPasswordField senha;
    private javax.swing.JLabel titulo;
    // End of variables declaration//GEN-END:variables
}
