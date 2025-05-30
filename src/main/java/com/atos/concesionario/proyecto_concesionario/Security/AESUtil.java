package com.atos.concesionario.proyecto_concesionario.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.*;
import java.util.Base64;

public class AESUtil {
	private static final String SECRET_KEY = "claveSecreta1234"; // 🔐 Usa 16 caracteres (AES-128)
    private static final String INIT_VECTOR = "RandomInitVector"; // 🔐 16 caracteres también

    public static String encrypt(String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING"); // Configuración segura
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.getEncoder().encodeToString(encrypted); // Encode para almacenar como texto

        } catch (Exception ex) {
            throw new RuntimeException("Error al encriptar DNI", ex);
        }
    }

    public static String decrypt(String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));
            return new String(original);

        } catch (Exception ex) {
            throw new RuntimeException("Error al desencriptar DNI", ex);
        }
    }
}
