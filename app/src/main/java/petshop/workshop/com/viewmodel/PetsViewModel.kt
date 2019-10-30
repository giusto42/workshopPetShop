package petshop.workshop.com.viewmodel

import android.annotation.SuppressLint
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import petshop.workshop.com.PetShopApplication
import petshop.workshop.com.persistence.Pet
import petshop.workshop.com.persistence.PetDao
import petshop.workshop.com.util.ContextProvider
import timber.log.Timber
import javax.inject.Inject

@SuppressLint("CheckResult")
class PetsViewModel @Inject constructor(contextProvider: ContextProvider) {

    @Inject
    lateinit var petDao: PetDao

    private val petsSubject: BehaviorSubject<List<Pet>> = BehaviorSubject.create()

    private var pets: List<Pet> = listOf()

    init {
        PetShopApplication.diInjector().inject(this)
    }

    fun subscribeForPets(): Observable<List<Pet>> = petsSubject.hide()

    fun preparePets(shouldReload: Boolean = false) {
        if (pets.isEmpty() || shouldReload) {
            petDao.getAllPets()
                .subscribeOn(Schedulers.computation())
                .subscribe({
                    pets = it
                    petsSubject.onNext(it)
                }, {
                    Timber.e(it)
                })
        } else {
            petsSubject.onNext(pets)
        }
    }

    fun addDummyPetsToDB() =
        Completable.fromAction { petDao.insertDataToDatabase(*Pet.dummyPetsData().toTypedArray()) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete { preparePets() }
            .subscribe()!!

    fun addPet(pet: Pet) =
        Completable.fromAction { petDao.insertDataToDatabase(pet) }
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnComplete { preparePets(shouldReload = true) }
        .subscribe()!!

}