package com.example.g4m3s4fr33.data.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.g4m3s4fr33.WaifuViewModel
import com.example.g4m3s4fr33.data.model.gamingstuff.IWantToPlayUnrealTournament
import com.example.g4m3s4fr33.data.model.gamingstuff.SixteenTimesTheDetail
import com.example.g4m3s4fr33.databinding.FavoriteListItemBinding

class RageQuitAdapter(
    private val rageQuitList: List<SixteenTimesTheDetail>,
) : RecyclerView.Adapter<RageQuitAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: FavoriteListItemBinding) :
    RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = FavoriteListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return rageQuitList.size
    }

    // TODO should work but i dont get the needed List (see viewmodel and senpairepo)
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val brokenDisc = rageQuitList[position]

        holder.binding.tvFavListItem.text = brokenDisc.title
        holder.binding.ivFavListItem.load(brokenDisc.thumbnail)

    }

}