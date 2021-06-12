package pl.kinga.wristapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.kinga.wristapp.network.RetrofitServices
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .callTimeout(10, TimeUnit.SECONDS)
            .build()

        RetrofitServices.retrofit = Retrofit.Builder()
            .baseUrl("http://wristapp.azurewebsites.net")
            //.baseUrl("http://192.168.0.220:8089")
            .client(client)
            .addConverterFactory(JacksonConverterFactory.create(ObjectMapper().apply {
                registerModules(
                    JavaTimeModule(),
                    Jdk8Module(),
                    KotlinModule()
                )
            }))
            .build()

    }
}