package petshop.workshop.com.view.pets

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_pet.*
import petshop.workshop.com.PetShopApplication
import petshop.workshop.com.R
import petshop.workshop.com.view.BaseActivity
import petshop.workshop.com.viewmodel.PetsViewModel
import javax.inject.Inject

class PetActivity : BaseActivity() {

    @Inject
    lateinit var petsViewModel: PetsViewModel

    init {
        PetShopApplication.diInjector().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet)
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right)

        petsViewModel.addDummyPetsToDB()
        val navController = findNavController(R.id.mainNavFragment)

        // Set up ActionBar
        navigationView.setupWithNavController(navController)
    }
}