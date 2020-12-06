import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class execut extends JFrame{
	ImageIcon imagem = new ImageIcon(getClass().getResource("Tabuleiro.png"));
	JLabel label = new JLabel(imagem);
	
	public execut() {
			
		add(label);
        //add(new JLabel(new ImageIcon(getClass().getResource("peao.png"))));
		setSize(1000, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(this);
		setVisible(true);
	}

	public static void main (String[] args) {
		new execut();
	}
}