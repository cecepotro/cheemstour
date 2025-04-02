package mx.itson.cheemstour

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import mx.itson.cheemstour.entities.Trip
import mx.itson.cheemstour.utils.RetrofitUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        getTrips()
    }

    fun getTrips() {
        val call: Call<List<Trip>> = RetrofitUtil.getApi()!!.getTrips()
        call.enqueue(object: Callback<List<Trip>> {
            override fun onResponse(call: Call<List<Trip>>, response: Response<List<Trip>>) {
                val trips : List<Trip> = response.body()!!
                for(t in trips) {
                    t.name?.let { Log.d("Trip", it) }
                }
            }

            override fun onFailure(call: Call<List<Trip>>, t: Throwable) {
                Log.e("Error", "Error en la llamada API")
            }

        })
    }
}