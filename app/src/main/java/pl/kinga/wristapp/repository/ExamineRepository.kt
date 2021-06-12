package pl.kinga.wristapp.repository

import android.util.Log
import pl.kinga.wristapp.network.RetrofitServices
import pl.kinga.wristapp.SessionManager
import pl.kinga.wristapp.domain.Examine
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ExamineRepository {

    suspend fun saveExamine(examine: Examine)
}

interface ExamineService {

    @POST("api/examines")
    suspend fun saveExamine(@Body examine: Examine, @Header("X-code") code: String): Response<Unit>

}

class ExamineRepositoryImpl : ExamineRepository {

    private val examineService: ExamineService get() = RetrofitServices.examineService

    override suspend fun saveExamine(examine: Examine) {
        try { // TODO: dev workaround
            val res = examineService.saveExamine(examine, SessionManager.code)
            Log.d("RESP", res.code().toString())
        } catch (e: Exception) {
            Log.e("ERR", e.toString())
        }
    }
}