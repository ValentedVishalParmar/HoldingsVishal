package com.dataholding.vishal.core.util

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import java.io.File
import java.security.MessageDigest

object SecurityUtils {
    /**
     * Checks if a debugger is attached to the app process.
     */
    fun isDebuggerAttached(): Boolean = android.os.Debug.isDebuggerConnected()

    /**
     * Checks if the device is rooted.
     */
    fun isDeviceRooted(): Boolean {
        val paths = arrayOf(
            "/system/app/Superuser.apk", "/sbin/su", "/system/bin/su",
            "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su",
            "/system/sd/xbin/su", "/system/bin/failsafe/su", "/data/local/su"
        )
        return paths.any { File(it).exists() }
    }

    /**
     * Checks if the app has been tampered with (signature check).
     * Replace EXPECTED_SIGNATURE with your release signature hash.
     */
    fun isAppTampered(context: Context): Boolean {
        val expectedSignature = "YOUR_RELEASE_SIGNATURE_HASH" // TODO: Replace with your real release signature hash
        return try {
            val packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_SIGNING_CERTIFICATES)
            } else {
                context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_SIGNATURES)
            }
            val signatures = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageInfo.signingInfo?.apkContentsSigners
            } else {
                @Suppress("DEPRECATION")
                packageInfo.signatures
            }
            val actual = MessageDigest.getInstance("SHA-256").digest(signatures?.get(0)?.toByteArray()).joinToString("") { "%02x".format(it) }
            actual != expectedSignature
        } catch (e: Exception) {
            true // If check fails, assume tampered
        }
    }
} 