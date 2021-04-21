package pkg;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSplitPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;

/**
 * Анализ функционирования распределенных вычислительных систем. Павский К.В. 3
 * семестр. РГР Процесс рождения и гибели Континуальный подход
 * 
 */

class GraphicPanel extends JPanel {
	private Data d;
	private Data d2;
	private int width;
	private int height;
	private Graphics g;

	public GraphicPanel(String lamb, String mu, String n, String i, String t, String h, String k) {

		super();
		double ll = Double.valueOf(lamb);
		double mm = Double.valueOf(mu);
		double hh = Double.valueOf(h);
		int nn = Integer.valueOf(n);
		int tt = (int) (Double.valueOf(t) / Double.valueOf(h));
		int kk = Integer.valueOf(k);
		int ii = Integer.valueOf(i);

		Experiment e = new Experiment(ll, mm, nn, tt, hh, kk, ii);
		Analitic a = new Analitic(ll, mm, nn, tt, hh, kk, ii);
		this.d = e.start();
		this.d2 = a.start();
	}

	public void paint(Graphics g) {
		super.paint(g);
		width = getWidth(); // сохраняем текущую ширину панели
		height = getHeight(); // и высоту
		drawGraphic(g);
	}

	private void drawGrid(Graphics g, int w, int h) {
		g.setColor(Color.LIGHT_GRAY); // задаем серый цвет
		for (int x = 0; x < w; x++) { // цикл от центра до правого края
			g.drawLine(x * width / w, 0, x * width / w, height); // вертикальная линия
		}
		for (int y = 0; y < h; y++) { // цикл от центра до верхнего края
			g.drawLine(0, height - y * height / h, width, height - y * height / h); // горизонтальная линия
		}
		g.setColor(Color.BLACK);
		for (int x = 0; x < w; x++) { // цикл от центра до правого края
			g.drawString(Integer.toString(x), x * width / w, height);
		}
		for (int y = 0; y < h; y++) { // цикл от центра до верхнего края
			g.drawString(Integer.toString(y), 0, height - y * height / h);
		}
	}

	public void drawGraphic(Graphics g) {

		int w = (int) (d.w);
		int h = (int) Math.max((d.h + 1), (d2.h + 1));

		int x[] = new int[d.T];
		int y1[] = new int[d.T];
		int y2[] = new int[d.T];

		for (int i = 0; i < d.T; i++) {
			g.setColor(Color.cyan);
			x[i] = (int) (d.x[i] * width / w); // H
			y1[i] = (int) (height - (d.y1[i] * height / h));
			g.drawOval(x[i], y1[i], 4, 4);
			g.setColor(Color.blue);
			y2[i] = (int) (height - (d.y2[i] * height / h));
			g.drawOval(x[i], y2[i], 4, 4);
		}

		drawGrid(g, w, h); // рисуем сетку
		g.setColor(Color.red); // MAGENTA
		for (int i = 1; i < d.T; i++) {
			g.setColor(Color.red);

			x[i] = (int) (d2.x[i] * width / w); // H
			y1[i] = (int) (height - (d2.y1[i] * height / h));

			g.drawLine(x[i - 1], y1[i - 1], x[i], y1[i]);
			g.setColor(Color.MAGENTA);
			y2[i] = (int) (height - (d2.y2[i] * height / h));
			g.drawLine(x[i - 1], y2[i - 1], x[i], y2[i]);

		}
	}
}

public class ModelVS extends JFrame {

	private JPanel contentPane;
	JSplitPane splitPane;
	private JTextField textField_mu;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JTextField textField_lamb;
	private JLabel lblNewLabel_2;
	private JTextField textField_N;
	private JLabel lblNewLabel_3;
	private JTextField textField_i;
	private JLabel lblNewLabel_4;
	private JTextField textField_t;
	private JLabel lblNewLabel_5;
	private JTextField textField_h;
	private JLabel lblNewLabel_6;
	private JTextField textField_k;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModelVS frame = new ModelVS();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ModelVS() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));

		setContentPane(contentPane);

		splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		contentPane.add(splitPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		splitPane.setLeftComponent(panel);

		lblNewLabel_1 = new JLabel("\u03BB");
		panel.add(lblNewLabel_1);

		textField_lamb = new JTextField();
		textField_lamb.setText("0.0001");
		panel.add(textField_lamb);
		textField_lamb.setColumns(5);

		lblNewLabel = new JLabel("\u00B5");
		panel.add(lblNewLabel);

		textField_mu = new JTextField();
		textField_mu.setText("1");
		panel.add(textField_mu);
		textField_mu.setColumns(5);

		JButton btnNewButton = new JButton("\u041F\u043E\u0441\u0442\u0440\u043E\u0438\u0442\u044C");
		btnNewButton.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				GraphicPanel GraphicPanel2 = new GraphicPanel(textField_lamb.getText(), textField_mu.getText(),
						textField_N.getText(), textField_i.getText(), textField_t.getText(), textField_h.getText(),
						textField_k.getText());

				splitPane.setRightComponent(GraphicPanel2);
			}
		});

		lblNewLabel_2 = new JLabel("N");
		panel.add(lblNewLabel_2);

		textField_N = new JTextField();
		textField_N.setText("10000");
		panel.add(textField_N);
		textField_N.setColumns(10);

		lblNewLabel_3 = new JLabel("i");
		panel.add(lblNewLabel_3);

		textField_i = new JTextField();
		textField_i.setText("5");
		panel.add(textField_i);
		textField_i.setColumns(5);

		lblNewLabel_4 = new JLabel("t");
		panel.add(lblNewLabel_4);

		textField_t = new JTextField();
		textField_t.setText("6");
		panel.add(textField_t);
		textField_t.setColumns(5);

		lblNewLabel_5 = new JLabel("h");
		panel.add(lblNewLabel_5);

		textField_h = new JTextField();
		textField_h.setText("0.01");
		panel.add(textField_h);
		textField_h.setColumns(5);

		lblNewLabel_6 = new JLabel("K");
		panel.add(lblNewLabel_6);

		textField_k = new JTextField();
		textField_k.setText("1000");
		panel.add(textField_k);
		textField_k.setColumns(10);
		panel.add(btnNewButton);
//		panel.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{lblNewLabel_1, textField_lamb, lblNewLabel, textField_mu, lblNewLabel_2, textField_N, lblNewLabel_3, textField_i, lblNewLabel_4, textField_t, lblNewLabel_5, textField_h, lblNewLabel_6, textField_k, btnNewButton}));

	}

}
