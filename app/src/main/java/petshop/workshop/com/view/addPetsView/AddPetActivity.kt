package petshop.workshop.com.view.addPetsView

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.add_pet_activity.*
import petshop.workshop.com.PetShopApplication
import petshop.workshop.com.persistence.Pet
import petshop.workshop.com.view.BaseActivity
import petshop.workshop.com.viewmodel.PetsViewModel
import timber.log.Timber
import javax.inject.Inject


class AddPetActivity : BaseActivity() {

    @Inject
    lateinit var petsViewModel: PetsViewModel

    private val textFocusListener = View.OnFocusChangeListener { view, hasFocus ->
        if (!hasFocus) {
            Timber.d("$hasFocus")
            hideKeyboard(view)
        }
    }

    init {
        PetShopApplication.diInjector().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(petshop.workshop.com.R.layout.add_pet_activity)

        setupEditText()

        back.setOnClickListener { this.onBackPressed() }
        buttonSubmit.setOnClickListener { savePetToDb() }
    }

    private fun savePetToDb() {
        if (!checkValidDate())
            return

        val pet = Pet(
            name = nameField.editText!!.text.toString(),
            age = ageField.editText!!.text.toString().toInt(),
            type = typeField.editText!!.text.toString(),
            details = detailsField.editText!!.text.toString(),
            price = priceField.editText!!.text.toString().toFloat(),
            imageUrl = imageField.editText!!.text.toString()
        )

        petsViewModel.addPet(pet)

        this.onBackPressed()
    }

    private fun setupEditText() {
        nameField.setOnFocusChangeListener { view, b -> if (!b) hideKeyboard(view) }
        priceField.onFocusChangeListener = textFocusListener
    }

    private fun checkValidDate(): Boolean {

        if (nameField.editText!!.text.isNullOrBlank() ||
            ageField.editText!!.text.isNullOrBlank() ||
            typeField.editText!!.text.isNullOrBlank() ||
            detailsField.editText!!.text.isNullOrBlank() ||
            priceField.editText!!.text.isNullOrBlank() ||
            imageField.editText!!.text.isNullOrBlank()
        ) {
            return false
        }
        return true
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}