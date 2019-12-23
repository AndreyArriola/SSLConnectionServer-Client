/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.net.ssl.SSLSocket;
import practica.pkg3.Servidor;

/**
 *clase para manejar coneccion con el socket cliente
 * @author arrio
 */
public class HandlerThread extends Thread {
	private SSLSocket cliente;
	private boolean cerrarConexion;
/**
 * Constructor para inicializar hilos
 * @param cliente socket cliente
 */
	public HandlerThread(SSLSocket cliente) {
		this.cliente = cliente;
		this.start();

	}
/**
 * Metodo para ejecutar hilo
 */
	@Override
	public void run() {
		System.out.println("Procesando peticion de cliente ");
		DataOutputStream dos = null;
		DataInputStream dis = null;

		try {

			InputStream Is = cliente.getInputStream();
			OutputStream Os = cliente.getOutputStream();
			dos = new DataOutputStream(Os);
			dis = new DataInputStream(Is);

		} catch (IOException ex) {
			System.out.println("Ha ocurrido un error la establecer canal de entrada/salida " + ex.getMessage());
		}

		int nuevaChirimoya;
		int accion = 0;
		//Mientras que no se cierra coneccion
		while(!this.cerrarConexion) {

			try {
                            //Se lee accion enviada por el socket cliente, en casa de fallo se sale.
				accion = dis.readInt();
			} catch (IOException ex) {
				System.out.println("Ha ocurrido un error al leer accion " + ex.getMessage());
                                accion = 4;
			}
	
			System.out.println("La opcion marcarda es " + accion);
			switch (accion) {
                                //Consulta de numero de chirimoyas
				case 1:
		
					try {
                                            //Se envia el numero de chirimoyas
						dos.writeInt(Servidor.getInstancia().getChirimoyas());
					} catch (IOException ex) {
						System.out.println("Ha surgido un error al enviar respuesta " + ex.getMessage());
					}
		
					break;
                                //Se añade el numero de chirimoyas        
				case 2:
					try {
						nuevaChirimoya = dis.readInt();
						Servidor.getInstancia().setChirimoyas(nuevaChirimoya + Servidor.getInstancia().getChirimoyas());
						dos.writeBoolean(true);
					} catch (IOException ex) {
						System.out.println("Ha ocurrido un error al leer chirimoyas " + ex.getMessage());
					}
					break;
                                //Se resta el numero de chirimoyas        
				case 3:
		
					try {
						nuevaChirimoya = dis.readInt();
						Servidor.getInstancia().setChirimoyas(Servidor.getInstancia().getChirimoyas()-nuevaChirimoya);
						dos.writeBoolean(true);
					} catch (IOException ex) {
						System.out.println("Ha ocurrido un error al leer chirimoyas " + ex.getMessage());
					}
		
					break;
                                // Para cerrar        
				case 4:
					System.out.println("Opcion marcada 4, cerrando");
					this.cerrarConexion = true;
				default:
					System.out.println("Ingrese una opcion correcta ");	
					
			}
		}
                
		cerrarConexionSocket();

	}
/**
 * Metodo para cerrar coneccion serversocket cliente
 */
	public void cerrarConexionSocket() {

		System.out.println("Se cierra coneccion");
		if (this.cliente != null) {
			try {
				this.cliente.close();
			} catch (IOException e) {
				System.out.println("Fallo al cerrar coneccion. Excepcion: " + e.getMessage());
			}
		}
	}
	/**
         * Metodo para inicializar el cierre de conecciones
         * @param cerrarConexion True cierra conexion
         */
	public void setCerrarConexion(boolean cerrarConexion) {
		this.cerrarConexion = cerrarConexion;
	}
}
