package es.unizar.eina.M34_comidas.send;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/** Concrete implementor utilizando la actividad de envío de SMS. No funciona en el emulador si no se ha configurado previamente */
public class SMSImplementor implements SendImplementor {

    /** actividad desde la cual se abrirá la actividad de envío de SMS */
    private Activity sourceActivity;

    /** Constructor
     * @param source actividad desde la cual se abrirá la actividad de envío de SMS
     */
    public SMSImplementor(Activity source){
        setSourceActivity(source);
    }

    /**  Actualiza la actividad desde la cual se abrirá la actividad de envío de SMS */
    public void setSourceActivity(Activity source) {
        sourceActivity = source;
    }

    /**  Recupera la actividad desde la cual se abrirá la actividad de envío de SMS */
    public Activity getSourceActivity(){
        return sourceActivity;
    }

    /**
     * Implementación del método send utilizando la aplicación de envío de SMS
     * @param phone teléfono
     * @param message cuerpo del mensaje
     */
    public void send (String phone, String message) {
        Uri smsUri = Uri.parse("sms:" + phone);
        Intent sendIntent = new Intent(Intent.ACTION_VIEW, smsUri);
        sendIntent.putExtra(" sms_body ", message);
        getSourceActivity().startActivity(sendIntent);
    }
}
