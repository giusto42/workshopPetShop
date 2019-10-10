package petshop.workshop.com.view.addPetsView

import android.os.Bundle
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.add_pet_activity.*
import petshop.workshop.com.PetShopApplication
import petshop.workshop.com.R
import petshop.workshop.com.view.BaseActivity
import petshop.workshop.com.viewmodel.PetsViewModel
import javax.inject.Inject

class AddPetActivity : BaseActivity() {

    @Inject
    lateinit var petsViewModel: PetsViewModel

    private lateinit var compositeDisposable: CompositeDisposable

    init {
        PetShopApplication.diInjector().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_pet_activity)

        back.setOnClickListener { this.onBackPressed() }
    }
}