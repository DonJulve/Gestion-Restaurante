package es.unizar.eina.M34_comidas.send;

import static android.content.pm.PackageManager.GET_ACTIVITIES;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

/** Concrete implementor utilizando la aplicación de WhatsApp. No funciona en el emulador si no se ha configurado previamente */
public class WhatsAppImplementor implements SendImplementor{
	
   /** actividad desde la cual se abrirá la aplicación de WhatsApp */
   private Activity sourceActivity;
   
   /** Constructor
    * @param source actividad desde la cual se abrira la aplicación de Whatsapp
    */
   public WhatsAppImplementor(Activity source){
	   setSourceActivity(source);
   }

   /**  Actualiza la actividad desde la cual se abrira la actividad de gestion de correo */
   public void setSourceActivity(Activity source) {
	   sourceActivity = source;
   }

   /**  Recupera la actividad desde la cual se abrira la aplicación de Whatsapp */
   public Activity getSourceActivity(){
     return sourceActivity;
   }

   /**
    * Implementacion del metodo send utilizando la aplicacion de WhatsApp
    * @param phone teléfono
    * @param message cuerpo del mensaje
    */
   public void send (String phone, String message) {

      PackageManager pm = getSourceActivity().getPackageManager();
      boolean app_installed = false ;
      try {
         pm.getPackageInfo("com. whatsapp ",
                 PackageManager.GET_ACTIVITIES) ;
         app_installed = true;
      } catch (PackageManager.NameNotFoundException e ) {
         app_installed = false;
      }
      if ( app_installed ) {
         Uri smsUri = Uri.parse("sms:" + phone );
         Intent sendIntent = new Intent (Intent.ACTION_SENDTO, smsUri);
         sendIntent.putExtra (Intent.EXTRA_TEXT, message);
         sendIntent.setPackage ("com.whatsapp ");
         getSourceActivity().startActivity (sendIntent);
      } else {
         Toast.makeText(getSourceActivity(), "WhatsApp not" +
                 "Installed", Toast.LENGTH_SHORT).show();
      }

   }

}
