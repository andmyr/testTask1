package and.od.ua.atttest1

import and.od.ua.atttest1.Constants.CURRENT_VP_ITEM_ID
import and.od.ua.atttest1.Constants.KEY_MSG
import and.od.ua.atttest1.Constants.RC_PERMISSION
import and.od.ua.atttest1.Constants.SHOW_VP
import and.od.ua.atttest1.Constants.TAG_1
import and.od.ua.atttest1.Constants.TAG_2
import and.od.ua.atttest1.Constants.URL
import and.od.ua.atttest1.repository.ImageController
import and.od.ua.atttest1.repository.RequestTask
import and.od.ua.atttest1.ui.description.ViewPagerFragment
import and.od.ua.atttest1.ui.list.RecyclerViewFragment
import and.od.ua.atttest1.utils.Utils
import and.od.ua.atttest1.utils.Utils.hideKeyboard
import android.app.SearchManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private var vpFragment: ViewPagerFragment? = null
    private var recyclerViewFragment: RecyclerViewFragment? = null
    private var searchView: SearchView? = null
    private var isDrawerFixed: Boolean = false

    private var vpOnScreen = false;
    private var vpCurrentItemI = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        isDrawerFixed = resources.getBoolean(R.bool.isDrawerFixed)

        drawerLayout = findViewById(R.id.activity_main)
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        if (isDrawerFixed) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN)
            drawerLayout.setScrimColor(Color.TRANSPARENT)
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        nav_view.setNavigationItemSelectedListener(object :
            NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.nav_home -> {
                        vpOnScreen = false
                        vpCurrentItemI = ""
                        showHomeFragment()
                    }
                    else -> return true
                }
                return true
            }
        })

        if (Utils.verifyStoragePermissions(this)) {
            startApp()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Utils.requestPermissions(this)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.action_search).actionView as SearchView?
        searchView?.setSearchableInfo(
            searchManager.getSearchableInfo(componentName)
        )
        searchView?.maxWidth = Int.MAX_VALUE
        searchView?.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val currentFragment = supportFragmentManager.findFragmentByTag(TAG_2)
                if (currentFragment is RecyclerViewFragment) {
                    currentFragment.presenter.setFilter(query)
                }
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                val currentFragment = supportFragmentManager.findFragmentByTag(TAG_2)
                if (currentFragment is RecyclerViewFragment) {
                    currentFragment.presenter.setFilter(query)
                }
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (closeSearchView()) return
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT)
            return
        }

        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
        } else {
            vpOnScreen = false
            vpCurrentItemI = ""
            supportFragmentManager.popBackStack()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            RC_PERMISSION -> {
                if ((grantResults.isNotEmpty())) {
                    for (grantResult in grantResults) {
                        if (grantResult == PackageManager.PERMISSION_DENIED) {
                            return
                        }
                    }
                    startApp()
                } else {
                    Log.d("TAG", "No permissions")
                }
            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.run {
            putBoolean(SHOW_VP, vpOnScreen)
            putString(CURRENT_VP_ITEM_ID, vpCurrentItemI)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState?.run {
            if (getBoolean(SHOW_VP)) {
                val itemId = getString(CURRENT_VP_ITEM_ID)
                itemId?.run {
                    openDetails(this)
                }
            }
        }
    }

    fun openDetails(itemId: String) {
        hideKeyboard(this)
        closeSearchView()
        val fragment: Fragment? = supportFragmentManager.findFragmentByTag(TAG_1)
        if (fragment == null
            || fragment.isResumed.not()
        ) {
            vpFragment = ViewPagerFragment()
            val bundle = Bundle()
            bundle.putString(KEY_MSG, itemId)
            vpFragment?.arguments = bundle
            val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            val layout = if (isDrawerFixed
            ) {
                R.id.frame_layout
            } else {
                hideSearchMenu()
                R.id.nav_host_fragment
            }

            fragmentTransaction.replace(
                layout,
                vpFragment as Fragment,
                TAG_1
            ).addToBackStack(TAG_1)
            fragmentTransaction.commit()
            supportFragmentManager.executePendingTransactions()
        } else {
            showSearchMenu()
            (fragment as ViewPagerFragment).presenter.setCurrentItem(itemId)
        }
        vpCurrentItemI = itemId
        vpOnScreen = true
    }

    fun showSearchMenu() {
        searchView?.visibility = View.VISIBLE
    }

    private fun closeSearchView(): Boolean {
        if (searchView != null
            && searchView?.isIconified == false
        ) {
            searchView?.isIconified = true
            return true
        }
        return false
    }

    private fun startApp() {
        ImageController.init(this)
        recyclerViewFragment = RecyclerViewFragment()
        vpFragment = ViewPagerFragment()
        showHomeFragment()
        RequestTask().execute(URL)
    }

    private fun hideSearchMenu() {
        searchView?.visibility = View.INVISIBLE
    }

    private fun showHomeFragment() {
        val fragmentTransaction: FragmentTransaction =
            supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(
            R.id.nav_host_fragment,
            recyclerViewFragment as Fragment,
            TAG_2
        )
        fragmentTransaction.commit()
    }
}