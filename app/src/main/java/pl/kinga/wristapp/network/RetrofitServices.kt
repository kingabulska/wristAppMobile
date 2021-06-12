package pl.kinga.wristapp.network

import pl.kinga.wristapp.repository.ExamineService
import pl.kinga.wristapp.ui.SignInService
import retrofit2.Retrofit

object RetrofitServices {

    lateinit var examineService: ExamineService

    lateinit var signInService: SignInService

    var retrofit: Retrofit? = null
        set(value) {
            if (field == null) {
                field = value

                retrofit?.let { retro ->
                    examineService = retro.create(ExamineService::class.java)
                    signInService = retro.create(SignInService::class.java)
                }
            }
        }

}