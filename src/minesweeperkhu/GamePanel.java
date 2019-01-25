package minesweeperkhu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GamePanel extends javax.swing.JFrame {

    MinesweeperCore minesweeperCore;

    public GamePanel() {
        minesweeperCore = new MinesweeperCore();
        initComponents();
    }

    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jSpinner1 = new javax.swing.JSpinner();
        jSpinner2 = new javax.swing.JSpinner();
        jSpinner3 = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();

        jButton5.setVisible(new File("_incomplete_game_log.txt").exists());

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ChangeListener preventListener = (ChangeEvent e) -> {
            if ((int) jSpinner1.getValue() < 9) {
                jSpinner1.setValue(9);
            }
        };

        jSpinner1.addChangeListener(preventListener);
        jSpinner2.addChangeListener(preventListener);
        jSpinner3.addChangeListener(preventListener);
        jSpinner1.setValue(9);
        jSpinner2.setValue(9);
        jSpinner3.setValue(9);

        jLabel1.setText("Name:");

        jTextField1.setText("Player1");

        jLabel2.setText("Game Mode");

        jButton2.setText("Start Game");
        jButton2.addActionListener((java.awt.event.ActionEvent evt) -> {
            jButton2ActionPerformed(evt);
        });

        jButton1.setText("Easy");
        jButton1.addActionListener((java.awt.event.ActionEvent evt) -> {
            jButton1ActionPerformed(evt);
        });

        jButton3.setText("Medium");
        jButton3.addActionListener((java.awt.event.ActionEvent evt) -> {
            jButton3ActionPerformed(evt);
        });

        jButton4.setText("Hard");
        jButton4.addActionListener((java.awt.event.ActionEvent evt) -> {
            jButton4ActionPerformed(evt);
        });

        jLabel3.setText("Width:");

        jLabel4.setText("Height:");

        jLabel5.setText("Mines:");

        jButton5.setText("Load");
        jButton5.addActionListener((java.awt.event.ActionEvent evt) -> {
            jButton5ActionPerformed(evt);
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(jLabel3))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(jLabel5))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 124, Short.MAX_VALUE)
                                                                .addComponent(jLabel4)))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(jSpinner3, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                                                        .addComponent(jSpinner2)
                                                        .addComponent(jSpinner1))
                                                .addGap(50, 50, 50))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                                .addContainerGap())))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(154, 154, 154)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jSpinner3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(7, 7, 7)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        jSpinner1.setValue(9);
        jSpinner2.setValue(9);
        jSpinner3.setValue(10);
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        jSpinner1.setValue(15);
        jSpinner2.setValue(15);
        jSpinner3.setValue(35);
    }

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
        jSpinner1.setValue(25);
        jSpinner2.setValue(16);
        jSpinner3.setValue(100);
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        minesweeperCore.width = (int) jSpinner1.getValue();
        minesweeperCore.height = (int) jSpinner2.getValue();
        minesweeperCore.mines = (int) jSpinner3.getValue();
        minesweeperCore.playerName = jTextField1.getText();
        jLabel6.setForeground(Color.red);
        if (minesweeperCore.width < 1 || minesweeperCore.width > 30) {
            jLabel6.setText("Invalid width number please try again (1 - 30)");
            return;
        }
        if (minesweeperCore.height < 1 || minesweeperCore.height > 20) {
            jLabel6.setText("Invalid height number please try again (1 - 20)");
            return;
        }
        if (minesweeperCore.mines < 1) {
            jLabel6.setText("Invalid mine number please try again");
            return;
        }
        if (minesweeperCore.mines > minesweeperCore.width * minesweeperCore.height / 3) {
            jLabel6.setText("Too much mines!!!");
            return;
        }
        minesweeperCore.setBoard();
        Game game = new Game(minesweeperCore);
        this.setVisible(false);
    }

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
        ArrayList<MinesweeperCore> saves = minesweeperCore.ReadPlayerObject();
        JDialog dialog = new JDialog(this, Dialog.ModalityType.DOCUMENT_MODAL);
        JLabel message = new JLabel("Chose YOur Save :", SwingConstants.CENTER);

        ArrayList<String> saveNames = new ArrayList<>();
        for (int i = 0; i < saves.size(); i++) {
            MinesweeperCore save = saves.get(i);
            saveNames.add((i + 1) + ". " + save.playerName);
        }

        JList list = new JList(saveNames.toArray(new String[saveNames.size()]));
        JScrollPane savesPanel = new JScrollPane(list);
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 2, 10, 0));
        JButton load = new JButton("Load"); 
        load.addActionListener((ActionEvent e) -> {
            dialog.dispose();
            minesweeperCore = saves.get(list.getSelectedIndex());

            File InCompleteFile = new File("_incomplete_game_log.txt");
            InCompleteFile.delete();
            try (ObjectOutputStream write_Obj = new ObjectOutputStream(new FileOutputStream(InCompleteFile, true))) {
                saves.remove(list.getSelectedIndex());
                write_Obj.writeObject(saves);
            } catch (IOException ex) {
                ArrayList<MinesweeperCore> minafesweeperCore = new ArrayList<>();
            }
            minesweeperCore.width = (int) jSpinner1.getValue();
            minesweeperCore.height = (int) jSpinner2.getValue();
            minesweeperCore.mines = (int) jSpinner3.getValue();
            minesweeperCore.playerName = jTextField1.getText();
            if (minesweeperCore.width < 1 || minesweeperCore.width > 30) {
                jLabel6.setText("Invalid width number please try again (1 - 30)");
                return;
            }
            if (minesweeperCore.height < 1 || minesweeperCore.height > 20) {
                jLabel6.setText("Invalid height number please try again (1 - 20)");
                return;
            }
            if (minesweeperCore.mines < 1) {
                jLabel6.setText("Invalid mine number please try again");
                return;
            }
            if (minesweeperCore.mines > minesweeperCore.width * minesweeperCore.height / 3) {
                jLabel6.setText("Too much mines!!!");
                return;
            }
            Game game = new Game(minesweeperCore);
            this.setVisible(false);
        });
        buttons.add(load);

        JPanel c = new JPanel();
        c.setLayout(new BorderLayout(20, 20));
        c.add(message, BorderLayout.NORTH);
        c.add(savesPanel, BorderLayout.CENTER);
        c.add(buttons, BorderLayout.SOUTH);
        c.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                dialog.dispose();
            }
        });
        dialog.setPreferredSize(new Dimension(250, 300));
        list.setPreferredSize(new Dimension(dialog.getWidth(), 200));
        dialog.setTitle("Load Game");
        dialog.add(c);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner jSpinner2;
    private javax.swing.JSpinner jSpinner3;
    private javax.swing.JTextField jTextField1;
}
