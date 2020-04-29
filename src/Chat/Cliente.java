
package Chat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.*;

import Mensajes.Mensajes;


public class Cliente {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MarcoCliente mimarco=new MarcoCliente();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}


class MarcoCliente extends JFrame{
	
	public MarcoCliente(){
		
		setBounds(600,300,280,350);
				
		LaminaMarcoCliente milamina=new LaminaMarcoCliente();
		add(milamina);
		
		setVisible(true);
		
		addWindowListener(new Usuario_conectado());
		
		}	
	
}
class Usuario_conectado extends WindowAdapter{

	public void windowOpened(WindowEvent e) {
		
		try {
			Socket misocket = new Socket("162.15.15.20",1324);
			Mensajes status_activo = new Mensajes();
			status_activo.setMensaje("conectado");
			status_activo.setNick("[SERVIDOR: ]");
			ObjectOutputStream conectado=new ObjectOutputStream(misocket.getOutputStream());
			conectado.writeObject(status_activo);
			misocket.close();
			System.out.println("llegue hasta el final");
		} catch (Exception e2) {
			e2.printStackTrace();
			// TODO: handle exception
		}
		
	}
	
}
//runable es un hilo que sirve poara que se ejecute cosas en segundo plano
class LaminaMarcoCliente extends JPanel implements Runnable{
	
	public LaminaMarcoCliente(){
		
username = JOptionPane.showInputDialog("Nombre: ");
		
		nick=new JLabel();
		
		nick.setText("Nick "+username);
		
		add(nick);
		
		JLabel texto=new JLabel("Conectados");
		
		add(texto);
		
		
		
		Chat=new JTextArea(12,20);

		ip=new JComboBox();
		
		add(ip);
		
		add(Chat);
	
		campo1=new JTextField(20);
	
		add(campo1);		
	
		miboton=new JButton("Enviar");
		
		EnviaTexto mievento = new EnviaTexto();
		
		miboton.addActionListener(mievento);
		
		add(miboton);	
		
		Thread hilo_recibe = new Thread(this);
		
		hilo_recibe.start();
	}
	
	
	private class EnviaTexto implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Chat.append("\n"+campo1.getText());
			
			try {
				Socket misocket = new Socket("162.15.15.20",1324);
				//creamos al usuario
				Mensajes datos = new Mensajes();
				
				datos.setNick(nick.getText().substring(5));
				datos.setIp(ip.getSelectedItem().toString());
				datos.setMensaje(campo1.getText());
				
				//obtenemos los objetos y lo ponemos en el socket
				ObjectOutputStream paquete_datos = new ObjectOutputStream(misocket.getOutputStream());
				
				paquete_datos.writeObject(datos);
				
				misocket.close();
				//convertimos el objeto en bytes para que puedan llegar al servidor
				
				
				
				
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println(e1.getMessage());
			}
			campo1.setText(null);
			//System.out.println(campo1.getText());
			
		}
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			ServerSocket ser_client = new ServerSocket(8080);
			
			Socket cliente;
			
			Mensajes mensajes_recibidos;
			
			while (true) {
				
				cliente=ser_client.accept();
				
				ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
				
				mensajes_recibidos = (Mensajes) entrada.readObject();
				
				if (!mensajes_recibidos.getMensaje().equals("conectado")) {
					Chat.append("\n"+ mensajes_recibidos.getNick()+": "+mensajes_recibidos.getMensaje());
					
				} else {
					ArrayList<String> ipsbox = new ArrayList<String>();
					this.ip.removeAllItems();
					for (String ip1 : mensajes_recibidos.getIps()) {
						this.ip.addItem(ip1);
					}

				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}
		
	private JTextField campo1;
	private String username;
	private JComboBox<String> ip;
	
	public JLabel nick;
	
	private JButton miboton;
	
	private JTextArea Chat;
	
}

















