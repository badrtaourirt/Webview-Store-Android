 package com.app.guide;


 import android.app.Dialog;
 import android.content.Context;
 import android.content.Intent;
 import android.graphics.drawable.ColorDrawable;
 import android.os.Bundle;
 import android.provider.Settings;
 import android.view.View;
 import android.view.Window;
 import android.widget.TextView;



 public class CustomDialogClass extends Dialog  implements View.OnClickListener {



     public Context context;

     public TextView btn_ok,btn_cancel;

     public CustomDialogClass(Context context) {
         super(context);
        this.context=context;


     }



     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         requestWindowFeature(Window.FEATURE_NO_TITLE);
         setCancelable(false);
         setContentView(R.layout.custom_dialog);
         getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

         btn_ok =  findViewById(R.id.btn_ok);
         btn_ok.setOnClickListener(this);
         btn_cancel =  findViewById(R.id.btn_cancel);
         btn_cancel.setOnClickListener(this);



     }




     public void onClick(View v) {



         switch (v.getId()) {



             case R.id.btn_ok:

                 context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                 System.exit(0);

             break;

             case R.id.btn_cancel:

                 dismiss();

                 break;




         }
     }





 }