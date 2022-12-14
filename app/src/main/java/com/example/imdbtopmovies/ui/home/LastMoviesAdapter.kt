package com.example.imdbtopmovies.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.imdbtopmovies.databinding.ItemLastMoviesBinding
import com.example.imdbtopmovies.models.home.TopMoviesResponse.Data
import javax.inject.Inject

class LastMoviesAdapter @Inject constructor() :
    RecyclerView.Adapter<LastMoviesAdapter.ViewHolder>() {

    //binding
    lateinit var binding: ItemLastMoviesBinding

    private var moviesList = emptyList<Data>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemLastMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(moviesList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount() = moviesList.size

    private var onClickListener: ((Data) -> Unit)? = null

    fun setOnClickListener(onClickListener: ((Data) -> Unit)) {
        this.onClickListener = onClickListener
    }

    fun setData(data: List<Data>) {
        val diffUtilCallBack = DiffCallBack(moviesList, data)
        val differ = DiffUtil.calculateDiff(diffUtilCallBack)
        moviesList = data
        differ.dispatchUpdatesTo(this)
    }

    class DiffCallBack(
        private val oldItems: List<Data>,
        private val newItems: List<Data>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldItems.size
        }

        override fun getNewListSize(): Int {
            return newItems.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition] === newItems[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition] === newItems[newItemPosition]
        }
    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Data) {
            binding.apply {
                movieTitle.text = data.title
                movieCountry.text = data.country
                movieYear.text = data.year
                movieRating.text = data.imdbRating
//                movieName.text = data.title
//                movieInfo.text = "${data.imdbRating} | ${data.country} | ${data.year} "
                moviePosterImage.load(data.poster) {
                    crossfade(true)
                    crossfade(500)
                }

//                Click Listener
                root.setOnClickListener {
                    onClickListener?.let {
                        it(data)
                    }
                }
            }
        }

    }


}