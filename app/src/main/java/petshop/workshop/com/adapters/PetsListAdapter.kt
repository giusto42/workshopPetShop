package petshop.workshop.com.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import petshop.workshop.com.R
import petshop.workshop.com.persistence.Pet

class PetsListAdapter (private val petsList: List<Pet>, val context: Context): RecyclerView.Adapter<PetsListAdapter.PetsViewHolder>() {

    private var onItemClicked: OnPetActionHandler? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetsViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.pet_item, parent, false)
        return PetsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return petsList.size
    }

    override fun onBindViewHolder(holder: PetsViewHolder, position: Int) {
        holder.txtPetName.text = petsList[position].name
        holder.txtPetAge.text = petsList[position].age.toString()
        holder.txtPetPrice.text = context.getString(R.string.pet_price, petsList[position].price, "RON")
        holder.txtPetType.text = petsList[position].type

        holder.itemView.setOnClickListener {
            onItemClicked?.invoke(petsList[position])
        }
    }

    inner class PetsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtPetName: TextView = view.findViewById(R.id.petName)
        val txtPetAge: TextView = view.findViewById(R.id.petAge)
        val txtPetPrice: TextView = view.findViewById(R.id.petPrice)
        val txtPetType: TextView = view.findViewById(R.id.petType)
    }

    fun onItemClicked(handler: OnPetActionHandler): PetsListAdapter {
        this.onItemClicked = handler
        return this
    }
}

typealias OnPetActionHandler = (Pet) -> Unit