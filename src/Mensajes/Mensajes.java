package Mensajes;

import java.io.Serializable;

public class Mensajes implements Serializable{
	private String nick;
	private String ip;
	private String mensaje;
	public Mensajes() {
		// TODO Auto-generated constructor stub
	}
	public Mensajes(String nick, String ip, String mensaje) {
		super();
		this.nick = nick;
		this.ip = ip;
		this.mensaje = mensaje;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
}
