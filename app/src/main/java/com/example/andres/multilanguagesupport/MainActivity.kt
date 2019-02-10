package com.example.andres.multilanguagesupport

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.support.annotation.StyleRes
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log

import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import com.example.andres.multilanguagesupport.MyContextWrapper.Companion.getSystemLocale
import com.example.andres.multilanguagesupport.MyContextWrapper.Companion.getSystemLocaleLegacy
import com.example.andres.multilanguagesupport.MyContextWrapper.Companion.setSystemLocale
import com.example.andres.multilanguagesupport.MyContextWrapper.Companion.setSystemLocaleLegacy
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.security.AccessController.getContext
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val LOCALE_KEY : String = "localekey"
    private val HINDI_LOCALE : String = "hi"
    private val ENGLISH_LOCALE: String = "en_US"
    private val LOCALE_PREF_KEY = "localePref"
    private lateinit var ivWelcome: ImageView
    private lateinit var locale: Locale

    lateinit var localeManager: LocaleManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        /*val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )*/
        /*drawer_layout.addDrawerListener(toggle)
        toggle.syncState()*/

        setTitle(R.string.app_name)

        /*val sp = getSharedPreferences(LOCALE_PREF_KEY, Context.MODE_PRIVATE)
        val localeString = sp.getString(LOCALE_KEY, ENGLISH_LOCALE)
        setupImageBasedOnLocale(localeString)*/

        nav_view.setNavigationItemSelectedListener(this)


    }

    private fun setupImageBasedOnLocale(localeString: String) {
        ivWelcome = findViewById(R.id.iv_welcome)
        if (localeString.equals(HINDI_LOCALE)) {

        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }*/
        val sharedPreferences = getSharedPreferences("localePref", Context.MODE_PRIVATE)

        if (item.itemId ==R.id.action_language) {
            val resources = getResources()

            val editor = sharedPreferences.edit()

            if(sharedPreferences.getString(LOCALE_KEY, ENGLISH_LOCALE).equals(HINDI_LOCALE)){
                //ocale = Locale.getDefault()
                editor.putString(LOCALE_KEY, ENGLISH_LOCALE)
            } else {
                //locale = Locale(HINDI_LOCALE)
                editor.putString(LOCALE_KEY, HINDI_LOCALE)
            }

            editor.apply()

            App.localeManager.setNewLocale(this, sharedPreferences.getString(LOCALE_KEY, ENGLISH_LOCALE))

            val configuration = resources.configuration
            //configuration.setLocale(locale)


           /* val language = sharedPreferences.getString(LOCALE_KEY,ENGLISH_LOCALE)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                locale = getSystemLocale(configuration)
            } else {
                locale = getSystemLocaleLegacy(configuration)
            }
            if (language != "" && !locale!!.getLanguage().equals(language)) {
                val locale = Locale(language)
                Locale.setDefault(locale)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    setSystemLocale(configuration, locale)
                } else {
                    setSystemLocaleLegacy(configuration, locale)
                }

            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                baseContext.createConfigurationContext(configuration)
            } else {
                baseContext.resources.updateConfiguration(configuration,
                    baseContext.resources.displayMetrics)
            }*/

            recreate()
        }

        return super.onOptionsItemSelected(item)
    }

    fun getMyContext(): Context {
        return MyContextWrapper.wrap(this, "fr")
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val  sharedPreferences = getSharedPreferences("localePref", Context.MODE_PRIVATE)
        val item = menu!!.getItem(0)
        if(sharedPreferences.getString(LOCALE_KEY, ENGLISH_LOCALE).equals(HINDI_LOCALE)){
            item.setTitle("English")
        }else {
            item.setTitle("Hindi")
        }

        return true

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(App.localeManager.setLocale(newBase!!))
    }
}
