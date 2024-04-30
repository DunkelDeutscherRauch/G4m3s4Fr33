package com.example.g4m3s4fr33.data.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.g4m3s4fr33.data.model.gamingstuff.SixteenTimesTheDetail
import com.example.g4m3s4fr33.databinding.FavoriteListItemBinding
import com.example.g4m3s4fr33.parasocial_relationship.WaifuViewModel
import com.example.g4m3s4fr33.ui.FavoriteListFragmentDirections

class RageQuitAdapter(
    private val rageQuitList: List<SixteenTimesTheDetail>,
    private val viewModel: WaifuViewModel
) : RecyclerView.Adapter<RageQuitAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: FavoriteListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            FavoriteListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return rageQuitList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val brokenDisc = rageQuitList[position]

        holder.binding.tvFavListItem.text = brokenDisc.title
        holder.binding.ivFavListItem.load(brokenDisc.thumbnail)

        holder.binding.btnFavListItem.setOnClickListener {
            viewModel.deleteFavGame(brokenDisc.id)
        }

        holder.binding.root.setOnClickListener {
            holder.itemView.findNavController().navigate(
                FavoriteListFragmentDirections.actionFavoriteListFragmentToFavoriteDetailFragment(
                    brokenDisc.id
                )
            )
        }

    }

}