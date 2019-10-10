package petshop.workshop.com.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.pets_list_fragment.*
import petshop.workshop.com.PetShopApplication
import petshop.workshop.com.R
import petshop.workshop.com.adapters.PetsListAdapter
import petshop.workshop.com.persistence.Pet
import petshop.workshop.com.view.fragments.PetsListFragmentDirections.actionPetsListFragmentToAddPetActivity
import petshop.workshop.com.view.fragments.PetsListFragmentDirections.actionPetsListFragmentToPetDetailsActivity
import petshop.workshop.com.viewmodel.PetsViewModel
import timber.log.Timber
import javax.inject.Inject

class PetsListFragment : Fragment() {

    @Inject
    lateinit var viewModel: PetsViewModel

    @Inject
    lateinit var petsViewModel: PetsViewModel

    private lateinit var compositeDisposable: CompositeDisposable

    private val redirectToPetDetailsActivity = actionPetsListFragmentToPetDetailsActivity()
    private val redirectToAddPetActivity = actionPetsListFragmentToAddPetActivity()

    init {
        PetShopApplication.diInjector().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.pets_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addPetButton.setOnClickListener {
            findNavController().navigate(redirectToAddPetActivity)
        }

        compositeDisposable = CompositeDisposable()
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.subscribeForPets()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.d("Debug: $it")
                this.updateRecycleView(it)
            }, {
                Timber.d(it)
            }).addTo(compositeDisposable)

        viewModel.preparePets()
    }

    private fun updateRecycleView(petList: List<Pet>) {
        recyclerView.adapter = PetsListAdapter(petList, requireContext()).onItemClicked {
            redirectToPetDetailsActivity.pet = it
            findNavController().navigate(redirectToPetDetailsActivity)
        }
    }
}