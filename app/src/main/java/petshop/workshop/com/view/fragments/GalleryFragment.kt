package petshop.workshop.com.view.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.gallery_fragment.*
import petshop.workshop.com.PetShopApplication
import petshop.workshop.com.R
import petshop.workshop.com.viewmodel.PetsViewModel
import javax.inject.Inject

class GalleryFragment : Fragment() {

    @Inject
    lateinit var petsViewModel: PetsViewModel

    private lateinit var compositeDisposable: CompositeDisposable
    private var previousCounter = -1
    private var nextCounter = 1

    private var contentList: List<Pair<String, Bitmap>> = listOf()
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

        next.setOnClickListener {
            updateView(contentList[nextCounter])
            previousCounter++
            nextCounter++
            handleButtonsVisibility()
        }

        previous.setOnClickListener {
            updateView(contentList[previousCounter])
            nextCounter--
            previousCounter--
            handleButtonsVisibility()
        }

        petsViewModel.subscribeForGallery()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                contentList = it
                updateView(it.first())
            }.addTo(compositeDisposable)

        petsViewModel.prepareGallery()
    }

    private fun updateView(content: Pair<String, Bitmap>) {
        petName.text = content.first
        petImage.setImageBitmap(content.second)
    }

    private fun handleButtonsVisibility() {
        if (nextCounter >= contentList.size) {
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

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}