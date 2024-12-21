package hoods.com.quotesyt.utils

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class PerferenceDatastore(context: Context) {

    val Context.dataSource: DataStore<Preferences> by preferencesDataStore("MY_NAME")
    val pref = context.dataSource

    companion object {
        val TOKEN_KEY = stringPreferencesKey("TOKEN")
    }

    suspend fun setUserToken(token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            pref.edit {
                it[TOKEN_KEY] = token
            }
        }
    }

    suspend fun removeToken() {
        CoroutineScope(Dispatchers.IO).launch {
            pref.edit {
                it[TOKEN_KEY] = ""
            }
            Log.e("Remove Token","Removed token")
        }
    }

     fun getToken() = pref.data.map {
        it[TOKEN_KEY] ?: ""
    }

}