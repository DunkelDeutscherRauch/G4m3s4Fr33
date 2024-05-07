package com.example.g4m3s4fr33.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.g4m3s4fr33.data.model.gamingstuff.IWantToPlayUnrealTournament
import com.example.g4m3s4fr33.databinding.GameListItemBinding
import com.example.g4m3s4fr33.ui.my_little_steam_clone.cozy_games.GameListFragmentDirections


class PlugAndPlayAdapter(
    private val tryHardList: List<IWantToPlayUnrealTournament>
) : RecyclerView.Adapter<PlugAndPlayAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: GameListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            GameListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return tryHardList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val gameAddiction = tryHardList[position]

        holder.binding.tvGameListItem.text = gameAddiction.title
        holder.binding.ivGameListItem.load(gameAddiction.thumbnail)

        holder.binding.root.setOnClickListener {
            holder.itemView.findNavController().navigate(
               GameListFragmentDirections.actionGamesListFragmentToGameDetailFragment(gameAddiction.id)
            )
        }
    }

}