package petshop.workshop.com.viewmodel

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import petshop.workshop.com.PetShopApplication
import petshop.workshop.com.persistence.Pet
import petshop.workshop.com.persistence.PetDao
import petshop.workshop.com.util.ContextProvider
import timber.log.Timber
import java.net.URL
import java.util.concurrent.Callable
import javax.inject.Inject

@SuppressLint("CheckResult")
class PetsViewModel @Inject constructor(contextProvider: ContextProvider) {

    @Inject
    lateinit var petDao: PetDao

    private val petsSubject: BehaviorSubject<List<Pet>> = BehaviorSubject.create()
    private val gallerySubject: BehaviorSubject<List<Pair<String, Bitmap>>> = BehaviorSubject.create()

    private var pets: List<Pet> = listOf()
    private var petsImage: List<Pair<String, Bitmap>> = listOf()

    init {
        PetShopApplication.diInjector().inject(this)
    }

    fun subscribeForPets(): Observable<List<Pet>> = petsSubject.hide()
    fun subscribeForGallery(): Observable<List<Pair<String, Bitmap>>> = gallerySubject.hide()

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

    fun prepareGallery(shouldReload: Boolean = false) {
        if (petsImage.isEmpty() || shouldReload) {
            val galleryListCallback = Callable<List<Pair<String, Bitmap>>> {
                //TODO: Uncomment the code
                pets.fold(mutableListOf()) { list, pet ->
//                    list.add(
//                        Pair(
//                            pet.name,
//                            BitmapFactory.decodeStream(URL(pet.imageUrl).openConnection().getInputStream())
//                        )
//                    )
                    return@fold list
                }
            }

            Single.fromCallable(galleryListCallback)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    petsImage = it
                    gallerySubject.onNext(it)
                }, {
                    Timber.e("Load images failed")
                    Timber.e(it)
                })
        } else {
            gallerySubject.onNext(petsImage)
        }
    }

    fun getImageForPet(petId: Int) = if (petsImage.isEmpty())  Pair("", "") else petsImage[petId - 1]

    //TODO: Uncomment the code and remove `null`
    fun addDummyPetsToDB() = null
//        Completable.fromAction { petDao.insertDataToDatabase(*Pet.dummyPetsData().toTypedArray()) }
//            .subscribeOn(Schedulers.computation())
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnComplete { preparePets() }
//            .subscribe()!!

    fun addPet(pet: Pet) =
        Completable.fromAction { petDao.insertDataToDatabase(pet) }
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnComplete { preparePets(shouldReload = true) }
        .subscribe()!!

}