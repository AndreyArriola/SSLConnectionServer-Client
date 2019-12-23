
package practica.pkg3;

import Handler.HandlerThread;
import java.io.IOException;
import java.util.ArrayList;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
/**
 * Clase encargada de la coneccion de los distintos socket cliente con el servidor
 * @author arrio
 */
public class Servidor extends Thread {
        //Lista de conecciones cliente - servidor
	private ArrayList<HandlerThread> conexiones = new ArrayList<>();
	private int chirimoyas;
	private SSLServerSocket sc;
	private boolean cerrarCon;
	private static Servidor servidorSingleTon = null;
/**
 * Constructor privado para evitar ser instanciado desde fuera de esta clase, para cumplir factor singleton
 */
	private Servidor() {
	}
/**
 * En caso de no existir instancia de Servidor, se crea una nueva, en caso contrario devuelve una existente
 * @return instancia Servidor
 */
	public static Servidor getInstancia() {

		if (servidorSingleTon == null) {
			servidorSingleTon = new Servidor();
		}
		return servidorSingleTon;
	}
/**
 * Metodo para inicializar clase Servidor
 * @param chirimoyas numero de chirimoyas
 * @param sc SSL server Socket
 */
	public void inicializar(int chirimoyas, SSLServerSocket sc) {

		this.chirimoyas = chirimoyas;
		this.sc = sc;

	}
/**
 * Obtener el nunero de chirimoyas
 * @return chirimoyas
 */
	public int getChirimoyas() {
		return chirimoyas;

	}

	/**
	 * Para asignar chirimoyas de forma sincronizada, ya que puede tener 2 clientes
	 * escribiendo a la vez.
	 * 
	 * @param chirimoyas numero de chirimoyas
	 */
	public synchronized void setChirimoyas(int chirimoyas) {
		this.chirimoyas = chirimoyas;
	}
/**
 * Metodo de ejecucion del hilo
 */
	@Override
	public void run() {

		while (!cerrarCon) {
			try {
				System.out.println("Esperando coneccion cliente");
				SSLSocket cliente = (SSLSocket) sc.accept();
				cliente.startHandshake();
				System.out.println("Se realiza coneccion Cliente - Servidor");
				this.conexiones.add(new HandlerThread(cliente));

			} catch (IOException ex) {
				System.out.println("Error al establecer conexion con el cliente" + ex.getMessage());
			}

		}
                cerrarConexiones();

	}
	/**
         * Inicia el proceso de cerrar conecciones cerrarCon = True
         * @param cerrarCon
         */
	public void setCerrarCon(boolean cerrarCon) {
		this.cerrarCon = cerrarCon;
	}
	/**
         * Metodo que cierra las conecciones abiertas con los distintos Socket cliente
         */
	private void cerrarConexiones() {
		
		for(HandlerThread item : conexiones) {
			item.setCerrarConexion(true);
		}
                System.out.println("Se an cerrados las conexiones con cliente ");
		
	}
}
