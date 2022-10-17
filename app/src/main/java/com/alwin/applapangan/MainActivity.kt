package com.alwin.applapangan

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.IntentCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.alwin.applapangan.databinding.ActivityMainBinding
import com.alwin.applapangan.models.login.ResponseProfile
import com.alwin.applapangan.ui.login.LoginActivity
import com.alwin.applapangan.utils.ApiInterface
import com.alwin.applapangan.utils.Constant
import com.alwin.applapangan.utils.ServiceGenerator
import com.driver.nyaku.models.BaseResponse
import com.driver.nyaku.ui.BaseActivity
import com.gorontalodigital.preference.Prefuser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : BaseActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getData()
        //  binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        val headerContainer: View = nav_view.getHeaderView(0)
        headerContainer.tv_logout.setOnClickListener {
            finishAffinity()
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            Prefuser().setToken(null)
        }

        // val drawerLayout: DrawerLayout = binding.drawerLayout
//        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_transaksi
            ), drawer_layout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        nav_view.setupWithNavController(navController)
    }

    private fun getData() {
        val api = ServiceGenerator.createService(
            ApiInterface::class.java,
            Prefuser().getToken(),
            Constant.PASS
        )

        showLoading(this)
        api.profile.enqueue(object : Callback<BaseResponse<ResponseProfile>> {
            override fun onResponse(
                call: Call<BaseResponse<ResponseProfile>>,
                response: Response<BaseResponse<ResponseProfile>>
            ) {
                hideLoading()
                if (response.isSuccessful) {
                    if (response.body()?.status == true) {
                        val headerContainer: View = nav_view.getHeaderView(0)
                        val tv_name = headerContainer.tv_name
                        val tv_email = headerContainer.tv_email
                        tv_email.text = response.body()?.data?.email.toString()
                        tv_name.text = response.body()?.data?.name.toString()

                    } else {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        showErrorMessage("Gagal dapatakan data profile,${jObjError.getString("message")}")

                    }
                } else {
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    showErrorMessage("Gagal dapatakan data profile,${jObjError.getString("message")}")

                }
            }

            override fun onFailure(call: Call<BaseResponse<ResponseProfile>>, t: Throwable) {
                hideLoading()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        if (id == R.id.logout) {
            finish()
            Prefuser().setToken(null)
            startActivity(Intent(this,LoginActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}