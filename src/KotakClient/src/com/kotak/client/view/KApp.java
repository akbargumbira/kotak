/*
 * KApp.java
 *
 * Created on Apr 30, 2011, 5:33:38 PM
 */
package com.kotak.client.view;

import com.kotak.client.KDaemon;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import com.kotak.client.Main;
import com.kotak.client.model.KAppData;
import com.kotak.util.KFileSystem;
import com.kotak.util.KLogger;

/**
 *
 * @author user
 */
public class KApp extends javax.swing.JFrame {

    private boolean pressed = false;
    private int deltaX = 0;
    private int deltaY = 0;
    private boolean login = false;
    private KDaemon daemon;

    /** Creates new form KApp */
    public KApp() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel = new javax.swing.JPanel();
        jTabbedPane = new javax.swing.JTabbedPane();
        jPanelAccount = new javax.swing.JPanel();
        jLabelPassword = new javax.swing.JLabel();
        jLabelEmail = new javax.swing.JLabel();
        jTextFieldEmail = new javax.swing.JTextField();
        jPasswordField = new javax.swing.JPasswordField();
        jButtonLoginLogout = new javax.swing.JButton();
        jPanelFolder = new javax.swing.JPanel();
        jTextFieldFolder = new javax.swing.JTextField();
        jButtonOpen = new javax.swing.JButton();
        jPanelServer = new javax.swing.JPanel();
        jButtonSave = new javax.swing.JButton();
        jTextFieldURL = new javax.swing.JTextField();
        jLabelPassword1 = new javax.swing.JLabel();
        jLabelURL = new javax.swing.JLabel();
        jTextFieldPort = new javax.swing.JTextField();
        jPanelRevert = new javax.swing.JPanel();
        jLabelEmail2 = new javax.swing.JLabel();
        jTextFieldRevision = new javax.swing.JTextField();
        jButtonRevert = new javax.swing.JButton();
        jMenuBar = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItemExit = new javax.swing.JMenuItem();
        jMenuHelp = new javax.swing.JMenu();
        jMenuItemAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Kotak");
        setAlwaysOnTop(true);
        setBounds(new java.awt.Rectangle(100, 100, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                KApp.this.windowClosed(evt);
            }
        });

        jLabelPassword.setText("password");

        jLabelEmail.setText("email");

        jTextFieldEmail.setText("your email");

        jButtonLoginLogout.setText("Logout");
        jButtonLoginLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoginLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelAccountLayout = new javax.swing.GroupLayout(jPanelAccount);
        jPanelAccount.setLayout(jPanelAccountLayout);
        jPanelAccountLayout.setHorizontalGroup(
            jPanelAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAccountLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAccountLayout.createSequentialGroup()
                        .addGroup(jPanelAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelEmail)
                            .addComponent(jLabelPassword))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
                            .addComponent(jPasswordField, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)))
                    .addComponent(jButtonLoginLogout, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanelAccountLayout.setVerticalGroup(
            jPanelAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAccountLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPassword))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonLoginLogout)
                .addGap(23, 23, 23))
        );

        jTabbedPane.addTab("Account", null, jPanelAccount, "Login or Logout");

        jTextFieldFolder.setText("your working folder");

        jButtonOpen.setText("Open");
        jButtonOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOpenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelFolderLayout = new javax.swing.GroupLayout(jPanelFolder);
        jPanelFolder.setLayout(jPanelFolderLayout);
        jPanelFolderLayout.setHorizontalGroup(
            jPanelFolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFolderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelFolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldFolder, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                    .addComponent(jButtonOpen, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanelFolderLayout.setVerticalGroup(
            jPanelFolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFolderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextFieldFolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonOpen)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        jTabbedPane.addTab("Working Folder", null, jPanelFolder, "Change Working Folder");

        jButtonSave.setText("Save");
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });

        jTextFieldURL.setText("server url");

        jLabelPassword1.setText("Port");

        jLabelURL.setText("URL");

        jTextFieldPort.setText("server url");

        javax.swing.GroupLayout jPanelServerLayout = new javax.swing.GroupLayout(jPanelServer);
        jPanelServer.setLayout(jPanelServerLayout);
        jPanelServerLayout.setHorizontalGroup(
            jPanelServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelServerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelServerLayout.createSequentialGroup()
                        .addGroup(jPanelServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelPassword1)
                            .addComponent(jLabelURL))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldURL, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                            .addComponent(jTextFieldPort, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)))
                    .addComponent(jButtonSave, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanelServerLayout.setVerticalGroup(
            jPanelServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelServerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelURL, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldURL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPassword1)
                    .addComponent(jTextFieldPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonSave)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jTabbedPane.addTab("Server", jPanelServer);

        jLabelEmail2.setText("revision");

        jTextFieldRevision.setText("revision number");

        jButtonRevert.setText("Revert");
        jButtonRevert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRevertActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelRevertLayout = new javax.swing.GroupLayout(jPanelRevert);
        jPanelRevert.setLayout(jPanelRevertLayout);
        jPanelRevertLayout.setHorizontalGroup(
            jPanelRevertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRevertLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelRevertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelRevertLayout.createSequentialGroup()
                        .addComponent(jLabelEmail2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldRevision, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE))
                    .addComponent(jButtonRevert, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanelRevertLayout.setVerticalGroup(
            jPanelRevertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRevertLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelRevertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelEmail2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldRevision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonRevert)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        jTabbedPane.addTab("Revert", jPanelRevert);

        javax.swing.GroupLayout jPanelLayout = new javax.swing.GroupLayout(jPanel);
        jPanel.setLayout(jPanelLayout);
        jPanelLayout.setHorizontalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
        );
        jPanelLayout.setVerticalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
        );

        jMenuBar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                windowPress(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                windowRelease(evt);
            }
        });
        jMenuBar.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                windowDrag(evt);
            }
        });

        jMenuFile.setText("File");
        jMenuFile.add(jSeparator1);

        jMenuItemExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemExit.setText("Exit");
        jMenuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExitActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemExit);

        jMenuBar.add(jMenuFile);

        jMenuHelp.setText("Help");

        jMenuItemAbout.setText("About");
        jMenuHelp.add(jMenuItemAbout);

        jMenuBar.add(jMenuHelp);

        setJMenuBar(jMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void renderAccount() {
        // Login / Logout
        if (login) {
            jTextFieldEmail.setEnabled(false);
            jPasswordField.setEnabled(false);
            jButtonLoginLogout.setText("Logout");
        } else {
            jTextFieldEmail.setEnabled(true);
            jPasswordField.setEnabled(true);
            jButtonLoginLogout.setText("Login");
        }
    }

    private void login() {
        boolean success = false;

        // Get username
        String email = jTextFieldEmail.getText();

        // Get password
        String password = new String(jPasswordField.getPassword());

        // TODO Login
        // Connect to Server

        success = true;

        if (success) {
            // Login is true
            login = true;

            // Render
            renderAccount();
            
            // Init Daemon
            initDaemon();

            // Save Data
            saveAccountData();
        } else {
            JOptionPane.showMessageDialog(this, "email and password don't match.", "Login Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void logout() {
        // Login is false
        login = false;

        // Render
        renderAccount();
        
        // Init Daemon
        initDaemon();

        // Save Data
        saveAccountData();
    }

    private void saveAccountData() {
        KAppData.getInstance().setLogin(login);
        KAppData.getInstance().setUsername(jTextFieldEmail.getText());
        KAppData.getInstance().setPassword(new String(jPasswordField.getPassword()));
        try {
            KAppData.save();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(KApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(KApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void windowDrag(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_windowDrag
        this.setBounds(evt.getXOnScreen() - deltaX, evt.getYOnScreen() - deltaY, this.getWidth(), this.getHeight());
    }//GEN-LAST:event_windowDrag

    private void windowPress(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_windowPress
        if (!pressed) {
            pressed = true;
            deltaX = evt.getXOnScreen() - this.getX();
            deltaY = evt.getYOnScreen() - this.getY();
        }
    }//GEN-LAST:event_windowPress

    private void windowRelease(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_windowRelease
        pressed = false;
    }//GEN-LAST:event_windowRelease

    private void jMenuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemExitActionPerformed
        dispose();
    }//GEN-LAST:event_jMenuItemExitActionPerformed

    private void jButtonLoginLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoginLogoutActionPerformed
        if (login) {
            // Logout
            logout();
        } else {
            // Login
            login();
        }
    }//GEN-LAST:event_jButtonLoginLogoutActionPerformed

    private void jButtonOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOpenActionPerformed
        // Remember old path
        String oldPath = jTextFieldFolder.getText();

        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                // Get New Folder Path
                String newPath = jfc.getSelectedFile().getAbsolutePath();

                // Move 'Kotak' to new path
                String folderName = KAppData.getInstance().getWorkingFolderName();
                KLogger.writeln("Move " + folderName + " from " + oldPath + " to " + newPath);
                KFileSystem.move(folderName, oldPath, newPath);

                // Set new Folder Path
                KAppData.getInstance().setWorkingFolderPath(newPath);
                jTextFieldFolder.setText(newPath);

                // Save
                KAppData.save();
            } catch (IOException ex) {
                Logger.getLogger(KApp.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(KApp.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(KApp.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }//GEN-LAST:event_jButtonOpenActionPerformed

    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
        // Get URL & port from text field
        String newURL = jTextFieldURL.getText();
        String newPort = jTextFieldPort.getText();

        // Set to KAppData
        KAppData.getInstance().setServerURL(newURL);
        KAppData.getInstance().setServerPort(Integer.parseInt(newPort));

        // Save
        try {
            KAppData.save();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(KApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(KApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonSaveActionPerformed

    private void jButtonRevertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRevertActionPerformed
        // TODO Revert / List Repo
    }//GEN-LAST:event_jButtonRevertActionPerformed

    private void windowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_windowClosed
        Main.daemon.close();
        System.out.println("closed");
    }//GEN-LAST:event_windowClosed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonLoginLogout;
    private javax.swing.JButton jButtonOpen;
    private javax.swing.JButton jButtonRevert;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JLabel jLabelEmail;
    private javax.swing.JLabel jLabelEmail2;
    private javax.swing.JLabel jLabelPassword;
    private javax.swing.JLabel jLabelPassword1;
    private javax.swing.JLabel jLabelURL;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenu jMenuHelp;
    private javax.swing.JMenuItem jMenuItemAbout;
    private javax.swing.JMenuItem jMenuItemExit;
    private javax.swing.JPanel jPanel;
    private javax.swing.JPanel jPanelAccount;
    private javax.swing.JPanel jPanelFolder;
    private javax.swing.JPanel jPanelRevert;
    private javax.swing.JPanel jPanelServer;
    private javax.swing.JPasswordField jPasswordField;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JTextField jTextFieldEmail;
    private javax.swing.JTextField jTextFieldFolder;
    private javax.swing.JTextField jTextFieldPort;
    private javax.swing.JTextField jTextFieldRevision;
    private javax.swing.JTextField jTextFieldURL;
    // End of variables declaration//GEN-END:variables

    public void init() {
        jTextFieldFolder.setText(KAppData.getInstance().getWorkingFolderPath());
        jTextFieldURL.setText(KAppData.getInstance().getServerURL());
        jTextFieldPort.setText(String.valueOf(KAppData.getInstance().getServerPort()));
        jTextFieldEmail.setText(KAppData.getInstance().getEmail());
        jPasswordField.setText(KAppData.getInstance().getPassword());
        login = KAppData.getInstance().isLogin();

        // Render panel accout
        renderAccount();

        // Init Daemon
        initDaemon();
    }

    private void initDaemon() {
        if (login) {
            // Create Daemon
            daemon = new KDaemon(KAppData.getInstance().getEmail(), KAppData.getInstance().getPassword());

            // Start Daemon
            //daemon.start();
        } else {
            if (daemon != null) {
                // Close Daemon
                daemon.close();
            }
        }
    }
}
