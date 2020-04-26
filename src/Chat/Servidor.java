package Chat;

import javax.swing.*;

import Mensajes.Mensajes;

import java.awt.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor  {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MarcoServidor mimarco=new MarcoServidor();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
	}	
}
//runable es un hilo que sirve poara que se ejecute cosas en segundo plano
class MarcoServidor extends JFrame implements Runnable{
	
	public MarcoServidor(){
		
		setBounds(1200,300,280,350);				
			
		JPanel milamina= new JPanel();
		
		milamina.setLayout(new BorderLayout());
		
		areatexto=new JTextArea();
		
		milamina.add(areatexto,BorderLayout.CENTER);
		
		add(milamina);
		
		setVisible(true);
		//aca se declara el hilo
		Thread mihilo = new Thread(this);
		mihilo.start();
		
		}
	
	private	JTextArea areatexto;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			ServerSocket servidor = new ServerSocket(1324);
			
			String nick, ip, mensaje;
			
			Mensajes paquete_recibido;
			
			while(true) {
				
			Socket misocket= servidor.accept();
			
			ObjectInputStream datos_entrantes = new ObjectInputStream(misocket.getInputStream());
			//alamacenamos los datos del mesnaje
			paquete_recibido=(Mensajes) datos_entrantes.readObject();
			
			nick = paquete_recibido.getNick();
			ip=paquete_recibido.getIp();
			mensaje=paquete_recibido.getMensaje();
			
			areatexto.append("\n"+nick+":  "+mensaje+" para: "+ip);
			//cremos el socket que mandara el mensaje
			System.out.println(ip);
			
			Socket Envia_Usuario = new Socket(ip, 8080);
			
			ObjectOutputStream dardatos= new ObjectOutputStream(Envia_Usuario.getOutputStream());
			
			dardatos.writeObject(paquete_recibido);
			
			dardatos.close();
			
			Envia_Usuario.close();
			
			misocket.close();
			
			}
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
	}
}
