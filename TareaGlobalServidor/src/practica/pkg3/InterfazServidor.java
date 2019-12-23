/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica.pkg3;

import java.io.IOException;
import java.util.Scanner;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

/**
 *
 * @author arrio
 */
public class InterfazServidor {

	private static final int PUERTOPREDETERMINADO = 4444;
/**
 * Hilo principal interfaz servidor
 * @param args 
 */
	public static void main(String[] args) {

		System.out.println("******************************************\r\n");
		System.out.println("* PSP - Tarea Global - Almacen multiusuario \r\n");
		System.out.println("****************************************** \r\n");
		System.out.println("* Luis Andrey Arriola Vassiliev * \r\n");
		System.out.println("****************************************** \r\n");
		System.out.println("* 09109448E \r\n");
		System.out.println("****************************************** \r\n");

		System.setProperty("javax.net.ssl.keyStore", "serverKey.jks");
		System.setProperty("javax.net.ssl.keyStorePassword", "claveSecreta");

		boolean salir = false;
                int puerto;
		int chirimoyas = 0;    
                SSLServerSocket servidorSSL = null;
		boolean chirimoyasLeidas = false;
                
		Scanner info = new Scanner(System.in);

                //El puerto ingresado por consola, en caso de fallo se establece uno por defecto
		System.out.println("Ingresar Puerto :");
		
		
		try {
			puerto = Integer.parseInt(info.nextLine());

		} catch (NumberFormatException ex) {

			System.err.println(ex.getMessage());
			System.out.println("Puerto incorrecto, se configura puerto predeterminado " + PUERTOPREDETERMINADO);
			puerto = PUERTOPREDETERMINADO;

		}
                // Se lee el numero de chirimoyas por consola, en caso de fallo se vuelve a pedir chirimoyas
		System.out.println("Ingresar Chirimoyas :");

		while (!chirimoyasLeidas) {

			try {

				chirimoyas = Integer.parseInt(info.nextLine());
				chirimoyasLeidas = true;

			} catch (NumberFormatException ex) {
				System.err.println(ex.getMessage());
				System.out.println("Numero de chirimoyas ingresadas no es correcta");
				System.out.println("Ingresar Chirimoyas :");
				chirimoyasLeidas = false;
			}

		}
                //Se establece coneccion segura server socket
		try {

			SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			servidorSSL = (SSLServerSocket) ssf.createServerSocket(puerto);
                        //Se obtiene instacia clase Servidor encargada de las conecciones con los socket cliente
			Servidor.getInstancia().inicializar(chirimoyas, servidorSSL);
                        //Se ejecuta en un hilo de forma paralela
			Servidor.getInstancia().start();

		} catch (IOException ex) {
			System.out.println("Se a producido el error" + ex.getMessage());
		}
		while (salir == false) {

			System.out.println("1. Consultar: ");
			System.out.println("2. Salir: ");
			int menu = 0;
			try {
                            //Se lee la opcion elegida, en caso de fallo entrara al case default
				menu = Integer.parseInt(info.nextLine());
			} catch (NumberFormatException ex) {
				System.err.println(ex.getMessage());
				System.out.println("As ingresado una opcion incorrecta");
				System.out.println("Ingresar un numero :");
			}

			switch (menu) {

			case 1:
				System.out.println("Ha escogido la opcion 1 ");
				System.out.println("el numero de chirimoyas es :" + Servidor.getInstancia().getChirimoyas());
				break;

			case 2:
				System.out.println("Ha elegido la opcion 2 ");
				salir = true;

				try {
                                    //Se cierra coneccion Socket servidor con socket cliente
					Servidor.getInstancia().setCerrarCon(true);
                                   //Se cierra socket servidor     
					servidorSSL.close();
                                        
				} catch (IOException ex) {
					System.out.println("Se ha producido un error" + ex.getMessage());
				}
				break;
			default:
				System.out.println("Elegir opcion del 1 al 2 ");
			}
		}
	}
}