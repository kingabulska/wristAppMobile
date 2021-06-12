package pl.kinga.wristapp.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pl.kinga.wristapp.R
import pl.kinga.wristapp.network.RetrofitServices
import pl.kinga.wristapp.SessionManager
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val vm: SignInViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        hideLogin()
        vm.init()

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val codePref = sharedPref?.getString("code", null)

        if (codePref != null) {
            vm.login(codePref)
        }

        val signInButton = view?.findViewById<Button>(R.id.sign_in_button)
        val pwd = view?.findViewById<TextView>(R.id.password)!!
        signInButton?.setOnClickListener {
            vm.login(pwd.text.toString())
        }

        vm.status.observe(viewLifecycleOwner, Observer {
            when (it) {
                SignInStatus.Initial -> {
                    showLogin()
                }
                SignInStatus.Success -> {
                    sharedPref?.let {
                        with(it.edit()) {
                            putString("code", SessionManager.code)
                            commit()
                        }
                    }
                    val nav = findNavController()
                    nav.navigate(R.id.action_signInFragment_to_infoFragment)
                }
                SignInStatus.Problem -> {
                    showError("Błędny kod")
                }
                SignInStatus.ConnectionProblem -> {
                    showError("Problem z połączeniem")
                }
                else -> {}
            }
        })
    }

    private fun showError(str: String) {
        val errorTv = view?.findViewById<TextView>(R.id.errorTextView)
        errorTv?.text = str
    }

    private fun showLogin() {
        view?.findViewById<ConstraintLayout>(R.id.sign_layout)?.visibility = View.VISIBLE
        view?.findViewById<ConstraintLayout>(R.id.splash_layout)?.visibility = View.GONE
    }


    private fun hideLogin() {
        view?.findViewById<ConstraintLayout>(R.id.sign_layout)?.visibility = View.GONE
        view?.findViewById<ConstraintLayout>(R.id.splash_layout)?.visibility = View.VISIBLE
    }
}

class SignInViewModel : ViewModel() {

    private val signInService = RetrofitServices.signInService

    private val _status: MutableLiveData<SignInStatus> =
        MutableLiveData(SignInStatus.Splash)
    val status: LiveData<SignInStatus> get() = _status

    fun init() {
        viewModelScope.launch {
            delay(2500)
            _status.value = SignInStatus.Initial
        }
    }

    fun login(pwd: String) {
        viewModelScope.launch {
            try {
                val resp = signInService.login(LoginBody(pwd))

                if (resp.isSuccessful) {
                    SessionManager.code = pwd
                    _status.value = SignInStatus.Success
                } else {
                    _status.value = SignInStatus.Problem
                }
            } catch (e: HttpException) {
                _status.value = SignInStatus.ConnectionProblem
            }

        }
    }

}

sealed class SignInStatus {
    object Splash : SignInStatus()
    object Initial : SignInStatus()
    object Success : SignInStatus()
    object Problem : SignInStatus()
    object ConnectionProblem : SignInStatus()
}

interface SignInService {

    @POST("/api/login")
    suspend fun login(@Body login: LoginBody): Response<Unit>

}

data class LoginBody(
    val code: String
)