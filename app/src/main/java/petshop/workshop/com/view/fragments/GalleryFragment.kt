package petshop.workshop.com.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.gallery_fragment.*
import petshop.workshop.com.PetShopApplication
import petshop.workshop.com.R
import petshop.workshop.com.persistence.Pet
import petshop.workshop.com.viewmodel.PetsViewModel
import javax.inject.Inject

class GalleryFragment : Fragment() {

    @Inject
    lateinit var petsViewModel: PetsViewModel

    private lateinit var compositeDisposable: CompositeDisposable

    private var previousCounter = -1
    private var nextCounter = 1
    private var petList: List<Pet> = listOf()

    init {
        PetShopApplication.diInjector().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.gallery_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        compositeDisposable = CompositeDisposable()

        loadView()

        next.setOnClickListener {
            updateView(petList[nextCounter])
            previousCounter++
            nextCounter++
            handleButtonsVisibility()
        }

        previous.setOnClickListener {
            //Todo: Implement previous button action
        }
    }

    private fun updateView(pet: Pet) {
        petName.text = pet.name
        Glide
            .with(this)
            .load(pet.imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(petImage)
    }

    private fun handleButtonsVisibility() {
        if (nextCounter >= petList.size) {
            next.visibility = View.INVISIBLE
        } else {
            next.visibility = View.VISIBLE
        }

        if (previousCounter < 0) {
            previous.visibility = View.INVISIBLE
        } else {
            previous.visibility = View.VISIBLE
        }
    }

    private fun loadView() {
        petsViewModel.subscribeForPets()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                petList = it
                updateView(it.first())
            }.addTo(compositeDisposable)
        petsViewModel.preparePets()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}