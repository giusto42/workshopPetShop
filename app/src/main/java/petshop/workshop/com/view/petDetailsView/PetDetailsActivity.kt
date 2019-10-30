package petshop.workshop.com.view.petDetailsView

import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.activity_pet_details.*
import petshop.workshop.com.PetShopApplication
import petshop.workshop.com.R
import petshop.workshop.com.persistence.Pet
import petshop.workshop.com.view.BaseActivity
import petshop.workshop.com.viewmodel.PetsViewModel
import javax.inject.Inject

class PetDetailsActivity : BaseActivity() {

    @Inject
    lateinit var petsViewModel: PetsViewModel

    lateinit var pet: Pet

    init {
        PetShopApplication.diInjector().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_details)
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left)
        back.setOnClickListener { this.onBackPressed() }

        pet = PetDetailsActivityArgs.fromBundle(intent.extras!!).pet!!
        petName.text = pet.name
        petDetails.text = pet.details

        Glide
            .with(this)
            .load(pet.imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(petImage)
    }
}