
package Chat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

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
		
        String []  listData = {"sd","sd"};
        
        list = new JList<String>(listData);
		
		list.setListData(listData);
		
		list.setVisibleRowCount(3);
		
		add(milamina);
		
		setVisible(true);
		}	
	private JList<String> list;
	
}
//runable es un hilo que sirve poara que se ejecute cosas en segundo plano
class LaminaMarcoCliente extends JPanel implements Runnable{
	
	public LaminaMarcoCliente(){
		
		nick=new JTextField(5);
		add(nick);
		
		JLabel texto=new JLabel("--CHAT--");
		
		add(texto);
		
		
		
		Chat=new JTextArea(12,20);

		ip=new JTextField(8);
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
				Socket misocket = new Socket("162.15.15.21",1324);
				//creamos al usuario
				Mensajes datos = new Mensajes();
				
				datos.setNick(nick.getText());
				datos.setIp(ip.getText());
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
				
				Chat.append("\n"+ mensajes_recibidos.getNick()+": "+mensajes_recibidos.getMensaje());
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}
		
	private JTextField campo1,nick,ip;
	
	private JButton miboton;
	
	private JTextArea Chat;

	
	
	
}


















