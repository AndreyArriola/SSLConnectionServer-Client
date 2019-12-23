/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteglobal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.net.ssl.SSLSocket;


/**
 *
 * @author arrio
 */
public class Cliente extends Thread{
    
    private DataOutputStream dos = null;
	private DataInputStream dis = null;
	int accion;
	int chirimoyas;
	SSLSocket cliente;
/**
 * Constructor para intercambio de informacion con socket cliente
 * @param cliente SSL socket cliente
 * @param accion opcion a realizar en el servidor
 */
	public Cliente(SSLSocket cliente, int accion) {
            
		this.accion = accion;
		this.cliente = cliente;

		try {

			OutputStream os = cliente.getOutputStream();
			InputStream is = cliente.getInputStream();

			dos = new DataOutputStream(os);
			dis = new DataInputStream(is);

		} catch (IOException ex) {
			System.out.println(ex.getMessage());
                        
                      
		}
	}
/**
 * Metodo ejecutor del hilo, encargado de realizar una opcion u otra, para intercambio de informacion con socket servidor
 */
	@Override
	public void run() {

		try {
			dos.writeInt(accion);
			if (accion == 1) {
				System.out.println("El numero de chirimoyas: " + dis.readInt());
			}
			else if (accion == 2 || accion == 3) {
				dos.writeInt(chirimoyas);
				System.out.println("Se ha realizado correctamente " + dis.readBoolean());
			}else if(accion == 4) {
				System.out.println("Se cierra coneccion cliente - servidor ");
				cliente.close();
			}
		} catch (IOException ex) {
			System.out.println("Se a desconectado del servidor " + ex.getMessage());
                       
		}

	}
/**
 * Para introducir chirimoyas
 * @param chirimoyas 
 */
	public void setchirimoyas(int chirimoyas) {
		this.chirimoyas = chirimoyas;
	}

}
