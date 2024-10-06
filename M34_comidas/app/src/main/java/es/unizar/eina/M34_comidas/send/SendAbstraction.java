package es.unizar.eina.M34_comidas.send;

/** Define la interfaz de la abstracción */
public interface SendAbstraction {

	/** Definición del metodo que permite realizar el envío del mensaje con texto 'message'
     * @param message cuerpo del mensaje
     */
	public void send(String phone, String message);
}
