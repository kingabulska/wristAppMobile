package pl.kinga.wristapp

import android.os.Handler
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class SplashFragment : Fragment(R.layout.splash_screen) {
    override fun onResume() {
        super.onResume()

        Handler().postDelayed({goToStartFragment()}, 3) //dlugosc wyswietlania splash screen

    }

    private fun goToStartFragment(){
        val nav = findNavController()
        nav.navigate(R.id.action_splashFragment_to_infoFragment)
    }
}