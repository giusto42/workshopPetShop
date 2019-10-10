package petshop.workshop.com.di

import dagger.Component
import petshop.workshop.com.PetShopApplication
import petshop.workshop.com.view.addPetsView.AddPetActivity
import petshop.workshop.com.view.fragments.GalleryFragment
import petshop.workshop.com.view.fragments.PetsListFragment
import petshop.workshop.com.view.petDetailsView.PetDetailsActivity
import petshop.workshop.com.view.pets.PetActivity
import petshop.workshop.com.viewmodel.PetsViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {

    fun inject(petShopApplication: PetShopApplication)
    fun inject(petsViewModel: PetsViewModel)
    fun inject(petActivity: PetActivity)
    fun inject(petsListFragment: PetsListFragment)
    fun inject(galleryFragment: GalleryFragment)
    fun inject(petDetailsActivity: PetDetailsActivity)
    fun inject(addPetActivity: AddPetActivity)
}