package umu.tds.vista;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.toedter.calendar.JDateChooser;

import umu.tds.controlador.ControladorAppMusic;
import umu.tds.helper.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Registro extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Create the panel.
	 */
	private JLabel lblNewLabel_1;
	private JPasswordField RPassword;
	private JTextField RUsuario;
	private JTextField REmail;
	private JButton btnVisibilidad;
	private boolean visibilidad;
	private JLabel etq1;
	private JLabel etqGoLogin;
	private JLabel etqFecha;
	private JButton btnSign;
	private JDateChooser Calendario;
	private static final String PLACEHOLDER_USUARIO = "User";
	private static final String PLACEHOLDER_PASSWORD = "Password";
	private static final String PLACEHOLDER_EMAIL = "Email";
	private static final String INICIO = "Inicio";
	private static final String LINK_INICIO = "go to Login";
	private JFrame frame;
	private Placeholder placeholder;
	private Fuente font;
	private HiperVinculo hiperVinculo;
	
	public Registro(JFrame frame) throws FontFormatException, IOException {
		this.frame = frame;
		inicialize();
	}
	
	void inicialize() throws FontFormatException, IOException
	{	
		// HELPER
		placeholder = new Placeholder();
		font = new Fuente();
		hiperVinculo = new HiperVinculo();
		
		setLayout(new GridBagLayout());
		JPanel PanelCentrado = new JPanel();
		setBackground(new Color(18, 159, 186));
		add(PanelCentrado,new GridBagConstraints());
		PanelCentrado.setBackground(new Color(18, 159, 186));
		GridBagLayout gbl_Registro = new GridBagLayout();
		gbl_Registro.rowHeights = new int[]{0, 0, 20, 18, 20, 0, 5, 20, 10, 0};
		gbl_Registro.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gbl_Registro.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
		gbl_Registro.columnWidths = new int[]{20, 20, 15, 128, 47};
		PanelCentrado.setLayout(gbl_Registro);
		
		lblNewLabel_1 = new JLabel("SIGN IN");
		lblNewLabel_1.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		lblNewLabel_1.setFont(font.getFont());
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.gridwidth = 3;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 0;
		PanelCentrado.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		RUsuario = new JTextField();
		RUsuario.setFont(new Font("Arial", Font.ITALIC, 14));
		RUsuario.setText(PLACEHOLDER_USUARIO);
		GridBagConstraints gbc_RUsuario = new GridBagConstraints();
		gbc_RUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_RUsuario.fill = GridBagConstraints.HORIZONTAL;
		gbc_RUsuario.gridx = 1;
		gbc_RUsuario.gridy = 1;
		PanelCentrado.add(RUsuario, gbc_RUsuario);
		RUsuario.setColumns(15);
		RUsuario.setPreferredSize(new Dimension(150,30));
				
		RPassword = new JPasswordField();	
		RPassword.setFont(new Font("Arial", Font.ITALIC, 14));
		RPassword.setText(PLACEHOLDER_PASSWORD);
		RPassword.setColumns(16);
		RPassword.setEchoChar((char)0);
		GridBagConstraints gbc_RPassword = new GridBagConstraints();
		gbc_RPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_RPassword.insets = new Insets(0, 0, 5, 5);
		gbc_RPassword.gridx = 3;
		gbc_RPassword.gridy = 1;
		PanelCentrado.add(RPassword, gbc_RPassword);
		RPassword.setPreferredSize(new Dimension(150,30));
		
		btnVisibilidad = new JButton("");
		btnVisibilidad.setBorderPainted(false);
		btnVisibilidad.setEnabled(false);
		btnVisibilidad.setBackground(new Color(18, 159, 186));
		btnVisibilidad.setIcon(new ImageIcon(Inicio.class.getResource("/ImagenesEntrada/ojo.png")));
		GridBagConstraints gbc_btnVisibilidad = new GridBagConstraints();
		gbc_btnVisibilidad.insets = new Insets(0, 0, 5, 0);
		gbc_btnVisibilidad.gridx = 4;
		gbc_btnVisibilidad.gridy = 1;
		PanelCentrado.add(btnVisibilidad, gbc_btnVisibilidad);
		
		REmail = new JTextField(); //Este mismo
		REmail.setText(PLACEHOLDER_EMAIL);
		REmail.setFont(new Font("Arial", Font.ITALIC, 14));
		GridBagConstraints gbc_email = new GridBagConstraints();
		gbc_email.anchor = GridBagConstraints.EAST;
		gbc_email.gridwidth = 3;
		gbc_email.insets = new Insets(0, 0, 5, 5);
		gbc_email.gridx = 1;
		gbc_email.gridy = 3;
		PanelCentrado.add(REmail, gbc_email);
		REmail.setColumns(34);
		REmail.setPreferredSize(new Dimension(150,30));
		
		etqFecha = new JLabel("Fecha de nacimiento");
		etqFecha.setFont(new Font("Arial", Font.BOLD, 14));
		etqFecha.setForeground(new Color(255, 255, 255));
		GridBagConstraints gbc_etqFecha = new GridBagConstraints();
		gbc_etqFecha.insets = new Insets(0, 0, 5, 5);
		gbc_etqFecha.gridx = 1;
		gbc_etqFecha.gridy = 5;
		PanelCentrado.add(etqFecha, gbc_etqFecha);
		
		Calendario = new JDateChooser();
		Calendario.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		Calendario.setDateFormatString("dd/MM/yyyy");
		GridBagConstraints gbc_Calendario = new GridBagConstraints();
		gbc_Calendario.insets = new Insets(0, 0, 5, 5);
		gbc_Calendario.fill = GridBagConstraints.BOTH;
		gbc_Calendario.gridx = 3;
		gbc_Calendario.gridy = 5;
		PanelCentrado.add(Calendario, gbc_Calendario);
		Calendario.setPreferredSize(new Dimension(150,30));
		
		etq1 = new JLabel("Already registered?, ");
		etq1.setBackground(new Color(217, 255, 255));
		etq1.setForeground(new Color(217, 255, 255));
		etq1.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_etq1 = new GridBagConstraints();
		gbc_etq1.gridwidth = 2;
		gbc_etq1.anchor = GridBagConstraints.EAST;
		gbc_etq1.insets = new Insets(0, 0, 5, 5);
		gbc_etq1.gridx = 1;
		gbc_etq1.gridy = 7;
		PanelCentrado.add(etq1, gbc_etq1);
		
		etqGoLogin = new JLabel("<html><font color=\"white\"><a href=''>go to Login</a></font></html>");
		etqGoLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		etqGoLogin.setForeground(new Color(128, 255, 255));
		etqGoLogin.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_etqGoLogin = new GridBagConstraints();
		gbc_etqGoLogin.anchor = GridBagConstraints.WEST;
		gbc_etqGoLogin.insets = new Insets(0, 0, 5, 5);
		gbc_etqGoLogin.gridx = 3;
		gbc_etqGoLogin.gridy = 7;
		PanelCentrado.add(etqGoLogin, gbc_etqGoLogin);
		
		btnSign = new JButton("SIGN IN");
		btnSign.setBorderPainted(false);
		btnSign.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSign.setFont(new Font("Arial", Font.BOLD, 12));
		btnSign.setBackground(new Color(230, 254, 255));
		btnSign.setBorder(UIManager.getBorder("CheckBox.border"));
		GridBagConstraints gbc_btnSign = new GridBagConstraints();
		gbc_btnSign.insets = new Insets(0, 0, 0, 5);
		gbc_btnSign.gridwidth = 3;
		gbc_btnSign.gridx = 1;
		gbc_btnSign.gridy = 9;
		PanelCentrado.add(btnSign, gbc_btnSign);
		
		
		
		
		
		// EVENTOS
		
		placeholder.crearPlaceholderText(RUsuario, PLACEHOLDER_USUARIO);
		placeholder.crearPlaceholderPassword(RPassword, PLACEHOLDER_PASSWORD,btnVisibilidad);
		placeholder.crearPlaceholderText(REmail, PLACEHOLDER_EMAIL);
		
		RPassword.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
            	btnVisibilidad.setEnabled(true);
            }

            public void removeUpdate(DocumentEvent e) {
               if (RPassword.getPassword().length == 0) {
            	   btnVisibilidad.setEnabled(false);
                }
            }

			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		btnVisibilidad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(visibilidad == true)
				{
					RPassword.setEchoChar('*');
					btnVisibilidad.setIcon(new ImageIcon("src/ImagenesEntrada/ojo.png"));
					visibilidad = false;
				}
				else
				{
					RPassword.setEchoChar((char) 0);
					btnVisibilidad.setIcon(new ImageIcon("src/ImagenesEntrada/visible.png"));
					visibilidad = true;
				}
			}
		});
		
		btnSign.addActionListener((e) -> {
			String login = RUsuario.getText();
			String password = new String(RPassword.getPassword());
			String email = REmail.getText();
			LocalDate fechaNacimiento = convertirFecha();
			Boolean iniciar = ControladorAppMusic.getInstancia().registrarUsuario(login,password,email,fechaNacimiento, this);
			if(iniciar)
			{		
				CardLayout cardlayout = (CardLayout) frame.getContentPane().getLayout();
				cardlayout.show(frame.getContentPane(), "Menu");
			}
		});
		
		hiperVinculo.crearHiperVinculo(etqGoLogin,INICIO,LINK_INICIO,frame);
		
		
		
	}
	
	private LocalDate convertirFecha() {
        Date fechaSeleccionada = Calendario.getDate();
        if(fechaSeleccionada == null)
        {
        	return null;
        }
        Instant instant = fechaSeleccionada.toInstant();
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate;
    }

}
