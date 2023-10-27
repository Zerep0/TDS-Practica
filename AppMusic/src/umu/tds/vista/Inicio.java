package umu.tds.vista;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import umu.tds.helper.Fuente;
import umu.tds.helper.HiperVinculo;
import umu.tds.helper.Placeholder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Inicio extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	private JFrame frame;
	private JPanel PanelBotonesInicio;
	private JPanel PanelLogin;
	private JPanel PanelGit;
	private JButton btnLogin;
	private JButton btnGit;
	private static final String PLACEHOLDER_USUARIO = "User";
	private static final String PLACEHOLDER_PASSWORD = "Password";
	private static final String REGISTRO = "Registro";
	private static final String LINK_REGISTRO = "click here";
	private JPasswordField Password;
	private JLabel lblNewLabel;
	private JLabel etqRegistro;
	private Placeholder placeholder;
	private Fuente font;
	private HiperVinculo hiperVinculo;
	private JTextField Usuario;
	public Inicio(JFrame frame) throws FontFormatException, IOException {
		this.frame = frame;
		inicialize();
	}
	
	void inicialize() throws FontFormatException, IOException
	{
		// HELPER
		placeholder = new Placeholder();
		font = new Fuente();
		hiperVinculo = new HiperVinculo();
		
		setBackground(new Color(18, 159, 186));
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{0, 30, 0, 15, 0, 30, 0};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gbl_panel.columnWeights = new double[]{1.0, 1.0};
		setLayout(gbl_panel);
		
		JLabel etiqueta = new JLabel("AppMusic");
		etiqueta.setForeground(new Color(255, 255, 255));
		etiqueta.setFont(new Font("Bauhaus 93", Font.BOLD, 40));	
		
		JPanel PanelCentrado = new JPanel();
		PanelCentrado.setBackground(new Color(18, 159, 186));
		GridBagConstraints gbc_PanelCentrado = new GridBagConstraints();
		gbc_PanelCentrado.gridwidth = 2;
		gbc_PanelCentrado.insets = new Insets(0, 0, 5, 0);
		gbc_PanelCentrado.gridx = 0;
		gbc_PanelCentrado.gridy = 0;
		add(PanelCentrado,gbc_PanelCentrado);
		PanelCentrado.setLayout(new GridBagLayout());
		
		JLabel TituloMusica = new JLabel("AppMusic");
		TituloMusica.setForeground(new Color(255, 255, 255));
		TituloMusica.setBackground(new Color(255, 255, 255));
		TituloMusica.setFont(font.getFont());
		GridBagConstraints gbc_TituloMusica = new GridBagConstraints();
		gbc_TituloMusica.gridx = 0;
		gbc_TituloMusica.gridy = 0;
		PanelCentrado.add(TituloMusica, gbc_TituloMusica);
		
		Usuario = new JTextField();
		Usuario.setFont(new Font("Arial", Font.ITALIC, 14));
		Usuario.setText(PLACEHOLDER_USUARIO);
		Usuario.setPreferredSize(new Dimension(150, 30));
		GridBagConstraints gbc_Usuario = new GridBagConstraints();
		gbc_Usuario.gridwidth = 2;
		gbc_Usuario.insets = new Insets(0, 0, 5, 5);
		gbc_Usuario.gridx = 0;
		gbc_Usuario.gridy = 2;
		add(Usuario, gbc_Usuario);
		Usuario.setColumns(25);
		
		Password = new JPasswordField();
		Password.setText("Password");
		Password.setEchoChar((char) 0);
		Password.setFont(new Font("Arial", Font.ITALIC, 14));
		Password.setColumns(25);
		GridBagConstraints gbc_Password = new GridBagConstraints();
		gbc_Password.gridwidth = 2;
		gbc_Password.insets = new Insets(0, 0, 5, 0);
		gbc_Password.gridx = 0;
		gbc_Password.gridy = 4;
		
		add(Password, gbc_Password);
		Password.setPreferredSize(new Dimension(150, 30));
		
		lblNewLabel = new JLabel("Not Registered?, ");
		lblNewLabel.setBackground(new Color(217, 255, 255));
		lblNewLabel.setForeground(new Color(217, 255, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 5;
		
		add(lblNewLabel, gbc_lblNewLabel);
		
		etqRegistro = new JLabel("<html><font color=\"white\"><a href=''>click here</a></font></html>");
		etqRegistro.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		etqRegistro.setForeground(new Color(128, 255, 255));
		etqRegistro.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_etqRegistro = new GridBagConstraints();
		gbc_etqRegistro.anchor = GridBagConstraints.WEST;
		gbc_etqRegistro.insets = new Insets(0, 0, 5, 0);
		gbc_etqRegistro.gridx = 1;
		gbc_etqRegistro.gridy = 5;
		
		add(etqRegistro, gbc_etqRegistro);
		
		PanelBotonesInicio = new JPanel();
		PanelBotonesInicio.setBackground(new Color(18, 159, 186));
		PanelBotonesInicio.setForeground(new Color(0, 128, 255));
		GridBagConstraints gbc_PanelBotonesInicio = new GridBagConstraints();
		gbc_PanelBotonesInicio.gridwidth = 2;
		gbc_PanelBotonesInicio.fill = GridBagConstraints.BOTH;
		gbc_PanelBotonesInicio.gridx = 0;
		gbc_PanelBotonesInicio.gridy = 6;
		
		add(PanelBotonesInicio, gbc_PanelBotonesInicio);
		
		PanelLogin = new JPanel();
		PanelLogin.setBackground(new Color(18, 159, 186));
		PanelLogin.setBorder(new EmptyBorder(0,0,0,30));
		PanelBotonesInicio.add(PanelLogin);
		
		btnLogin = new JButton("LOGIN");
		btnLogin.setBorderPainted(false);
		btnLogin.setFont(new Font("Arial", Font.BOLD, 12));
		btnLogin.setBackground(new Color(230, 254, 255));
		btnLogin.setBorder(UIManager.getBorder("CheckBox.border"));
		PanelLogin.add(btnLogin);
		btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		PanelGit = new JPanel();
		PanelGit.setBackground(new Color(18, 159, 186));	
		PanelBotonesInicio.add(PanelGit);
		
		btnGit = new JButton("LOGIN WITH GIT");
		btnGit.setBorderPainted(false);
		btnGit.setFont(new Font("Arial", Font.BOLD, 12));
		btnGit.setBackground(new Color(230, 254, 255));
		btnGit.setBorder(UIManager.getBorder("CheckBox.border"));
		PanelGit.add(btnGit);
		btnGit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		// EVENTOS
		placeholder.crearPlaceholderText(Usuario, PLACEHOLDER_USUARIO);
		placeholder.crearPlaceholderPassword(Password, PLACEHOLDER_PASSWORD);
		hiperVinculo.crearHiperVinculo(etqRegistro,REGISTRO,LINK_REGISTRO,frame);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardlayout = (CardLayout) frame.getContentPane().getLayout();
				cardlayout.show(frame.getContentPane(), "Menu");
			}
		});
		
		
		
	}

}
