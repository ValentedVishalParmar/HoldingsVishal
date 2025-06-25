package com.dataholding.vishal.core.util

import android.content.Context
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import android.util.Base64

object KeystoreUtils {
    private const val KEY_ALIAS = "room_db_key"
    private const val ANDROID_KEYSTORE = "AndroidKeyStore"
    private const val AES_MODE = "AES/GCM/NoPadding"
    private const val PREFS_NAME = "secure_prefs"
    private const val ENCRYPTED_KEY = "encrypted_room_key"
    private const val IV_KEY = "room_key_iv"

    fun getOrCreatePassphrase(context: Context): String {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val encryptedKeyBase64 = prefs.getString(ENCRYPTED_KEY, null)
        val ivBase64 = prefs.getString(IV_KEY, null)

        val secretKey = getOrCreateSecretKey(context)

        return if (encryptedKeyBase64 != null && ivBase64 != null) {
            // Decrypt and return
            val encryptedKey = Base64.decode(encryptedKeyBase64, Base64.DEFAULT)
            val iv = Base64.decode(ivBase64, Base64.DEFAULT)
            val cipher = Cipher.getInstance(AES_MODE)
            cipher.init(Cipher.DECRYPT_MODE, secretKey, GCMParameterSpec(128, iv))
            String(cipher.doFinal(encryptedKey))
        } else {
            // Generate, encrypt, and store
            val passphrase = generateRandomPassphrase()
            val cipher = Cipher.getInstance(AES_MODE)
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)
            val encryptedKey = cipher.doFinal(passphrase.toByteArray())
            val iv = cipher.iv
            prefs.edit()
                .putString(ENCRYPTED_KEY, Base64.encodeToString(encryptedKey, Base64.DEFAULT))
                .putString(IV_KEY, Base64.encodeToString(iv, Base64.DEFAULT))
                .apply()
            passphrase
        }
    }

    private fun getOrCreateSecretKey(context: Context): SecretKey {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }
        if (keyStore.containsAlias(KEY_ALIAS)) {
            return (keyStore.getEntry(KEY_ALIAS, null) as KeyStore.SecretKeyEntry).secretKey
        } else {
            val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE)
            val builder = KeyGenParameterSpec.Builder(
                KEY_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setRandomizedEncryptionRequired(true)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val hasStrongBox = context.packageManager.hasSystemFeature("android.hardware.strongbox_keystore")
                builder.setIsStrongBoxBacked(hasStrongBox)
            }
            keyGenerator.init(builder.build())
            return keyGenerator.generateKey()
        }
    }

    private fun generateRandomPassphrase(length: Int = 32): String {
        val charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..length)
            .map { charset.random() }
            .joinToString("")
    }
}