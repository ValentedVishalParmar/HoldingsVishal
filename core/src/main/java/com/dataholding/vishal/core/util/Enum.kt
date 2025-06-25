package com.dataholding.vishal.core.util

import android.content.Context
import androidx.annotation.StringRes
import com.dataholding.vishal.core.R

/**
 * Enum representing top bar menu options.
 *
 * @property id The unique identifier for the menu item.
 */
enum class TopBarMenu(val id:Int) {
    SEARCH(1),
    VALUES(2)
}

/**
 * Enum representing error messages with string resource IDs.
 *
 * @property msg The string resource ID for the error message.
 */
enum class ErrorMsg(@StringRes val msg:Int)
{
    ERR_UNKNOWN_NETWORK(R.string.error_network),
    ERR_UNKNOWN(R.string.error_unknown);

    /**
     * Retrieves the UI string for the error message.
     *
     * @param context The context used to access resources.
     * @return The localized error message string.
     *
     * Usage:
     * val message = ErrorMsg.ERR_UNKNOWN.getUIMsg(context)
     */
    fun getUIMsg(context: Context): String {
        return context.getString(msg)
    }
}

enum class TopAppBarState {
    Portfolio,
}