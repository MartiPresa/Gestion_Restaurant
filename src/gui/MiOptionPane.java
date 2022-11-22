package gui;

import java.awt.Component;

import javax.swing.JOptionPane;

import util.Mensajes;

public class MiOptionPane implements InterfazOptionPanel {
    public MiOptionPane() {
        super();
    }


    @Override
    public void ShowMessage(Component parent, Mensajes mensaje) {
        JOptionPane.showMessageDialog(parent, mensaje);
    }

}
