import java.awt.FlowLayout;

import javax.swing.JButton;

import javax.swing.JFrame;

class Main {
  public static void main(String[] args) {
    JFrame janela = new JFrame("Observador");
    janela.setSize(600, 200);
    janela.setLayout(new FlowLayout());
    
    JButton botao = new JButton("clicar!");
    janela.add(botao);
    janela.setVisible(true);

  }
}