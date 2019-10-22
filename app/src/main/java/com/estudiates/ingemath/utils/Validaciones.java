package com.estudiates.ingemath.utils;

import android.util.Patterns;

import java.util.regex.Pattern;

public class Validaciones {

    public static boolean validarContrasena(String contrasena) {
        if(!contrasena.isEmpty()){
            contrasena.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d)(?=.*[#$^+=!*()@%&]).{8,}$");
            return true;
        }else{
            return false;
        }

    }

    public static boolean validarCorreo(String correo) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(correo).matches();
    }
}
