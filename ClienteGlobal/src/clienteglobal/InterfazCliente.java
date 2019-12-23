/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteglobal;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 *
 * @author arrio
 */
public class InterfazCliente {

    /** Hilo principal que ejecuta la interfaz cliente
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
                System.out.println("******************************************\r\n");
		System.out.println("* PSP - Tarea Global - Almacen multiusuario \r\n");
		System.out.println("****************************************** \r\n");
		System.out.println("* Luis Andrey Arriola Vassiliev * \r\n");
		System.out.println("****************************************** \r\n");
		System.out.println("* 09109448E \r\n");
		System.out.println("****************************************** \r\n");
                
            final String IP_DEFECTO = "127.0.0.1"; 
            boolean salir = false;
            String ip;    
            int puerto;
            final int PUERTOPREDETERMINADO = 4444;
            
            // Se configuran jks para coneccion segura
                System.setProperty("javax.net.ssl.trustStore",
                "clientTrustedCerts.jks");
                System.setProperty("java.net.ssl.trustStorePassword",
                "claveClienteSecreta");
                
		Scanner info = new Scanner(System.in);
                //se lee IP, en caso de fallo, se establece una por defecto
		System.out.println("Ingresar IP :");
                try{
		ip = info.nextLine();
                }catch(NoSuchElementException | IllegalStateException ex  ){
                    System.out.println("La ip insertada no es valida " + ex.getMessage());
                    ip = IP_DEFECTO;
                    System.out.println("Se configura con la IP de defecto");
                }
                // se lee Puerto, en caso de fallo, se establece puerto predeterminado
		System.out.println("Ingresar Puerto :");
                
		try{
                puerto = Integer.parseInt(info.nextLine());
                }catch(NumberFormatException ex){
                    System.err.println(ex.getMessage());      
                    System.out.println("Puerto incorrecto, se configura puerto predeterminado " + PUERTOPREDETERMINADO);
                    puerto = PUERTOPREDETERMINADO;
                    
                }
		SSLSocket clientessl = null;

		try {
			// Creamos el socket cliente
                   SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
                    clientessl = (SSLSocket) sf.createSocket(ip,puerto);
                    clientessl.startHandshake();
		} catch (IOException ex) {
			System.out.println("Se ha producido el error" + ex.getMessage());
		}
		while (salir == false) {
			System.out.println("1. Consultar: ");
			System.out.println("2. Añadir: ");
			System.out.println("3. Retirar: ");
			System.out.println("4. Salir: ");
			int menu = 0;
                        try {
				menu = Integer.parseInt(info.nextLine());
			} catch (NumberFormatException ex) {
				System.err.println(ex.getMessage());
				System.out.println("As ingresado una opcion incorrecta");
				System.out.println("Ingresar un numero :");
			}
                        // Se instancia clase Cliente encargada del intercambio de informacion con el socket servidor
			Cliente cliente = new Cliente(clientessl, menu);
                        
			switch (menu) {

			case 1:
				System.out.println("Has elegido la opcion 1 ");
				cliente.start();
				break;
			case 2:
				System.out.println("Has elegido la opcion 2 ");
				System.out.println("Cuantas chirimoyas añadimos: ");
                                
                                try{
				cliente.setchirimoyas(Integer.parseInt(info.nextLine()));
				cliente.start();
                                }catch(NumberFormatException ex){
                                    System.err.println(ex.getMessage());
                                    System.out.println("Debe de ingresar un numero ");
                                }
                                
				break;
			case 3:
				System.out.println("Has elegido la opcion 3 ");
				System.out.println("Cuantas chirimoyas retiramos: ");
				 try{
				cliente.setchirimoyas(Integer.parseInt(info.nextLine()));
				cliente.start();
                                }catch(NumberFormatException ex){
                                    System.err.println(ex.getMessage());
                                    System.out.println("Debe de ingresar un numero ");
                                }
                                
				break;
			case 4:

				try {
					cliente.start();
					System.out.println("Se ha cerrado la coneccion");
				} catch (Exception ex) {
					System.out.println("Se ha producido un error al cerrar " + ex.getMessage());
				}
				salir = true;
				break;

			default:
				System.out.println("Elegir opcion del 1 al 4 ");
			}
		}System.out.println("Fin programa cliente ");
	}
    }
    

