package es.unizar.eina.M34_comidas.send;

import android.app.Activity;

/** Implementa la interfaz de la abstraccion utilizando (delegando a) una referencia a un objeto de tipo implementor  */
public class SendAbstractionImpl implements SendAbstraction {
	
	/** objeto delegado que facilita la implementacion del metodo send */
	private SendImplementor implementor;
	
	/** Constructor de la clase. Inicializa el objeto delegado
	 * @param sourceActivity actividad desde la cual se abrirá la actividad encargada de realizar el envío
	 * @param method parametro potencialmente utilizable para instanciar el objeto delegado
	 */
	public SendAbstractionImpl(Activity sourceActivity, String method) {
		if (method.equalsIgnoreCase("SMS"))
			implementor = new SMSImplementor(sourceActivity);
		else
			implementor = new WhatsAppImplementor(sourceActivity);
	}

	/** Envia la correo con el asunto (subject) y cuerpo (body) que se reciben como parametros a traves de un objeto delegado
     * @param message cuerpo del mensaje
     */
	public void send(String phone, String message) {
		implementor.send(phone, message);
	}
}
