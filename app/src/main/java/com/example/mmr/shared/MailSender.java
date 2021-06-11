package com.example.mmr.shared;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.mmr.Config;

import java.util.Properties;
import java.util.Random;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {

    public static String code="NULL";

    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
    public static void sendVerificationEmail(Context context,String name, String email) /*throws MessagingException */ {
        Random random=new Random();
        code ="";
        for (int i=0;i<4;i++){
            int index = random.nextInt(10-1) + 1;
            code+=index;
        }
        String message="Bonjour "+name+"\nVotre code de verification est: "+code;
        //Mail(email, "Verifecation", message);

        LocalJavaMail javaMailAPI=new LocalJavaMail(context,email,message);
        javaMailAPI.execute();

    }

    public static boolean codeValid(String code){
        return MailSender.code.equals(code);
    }

    public static class LocalJavaMail extends AsyncTask<Void,Void,Void> {
        //Declaring Variables
        private Context context;

        //Information to send email
        private String email;
        private String message;

        //Class Constructor
        public LocalJavaMail(Context context, String email,String message) {
            //Initializing variables
            this.context = context;
            this.email = email;
            this.message = message;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //Showing a success message
            Toast.makeText(context, "Message Sent", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                GMailSender sender = new GMailSender(Config.EMAIL, Config.PASSWORD);
                sender.sendMail("Verification",
                        message,
                        Config.EMAIL,
                        email);
            } catch (Exception e) {
            }
            return null;
        }
    }
}
