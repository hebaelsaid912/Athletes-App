package com.hebaelsaid.android.athletesapp.ui.feature.homelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.hebaelsaid.android.athletesapp.BR
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hebaelsaid.android.athletesapp.data.model.AthletesListResponseModel
import com.hebaelsaid.android.athletesapp.databinding.HomeListItemBinding

private const val TAG = "AthletesListAdapter"
class AthletesListAdapter(private val onItemClickListener: AthletesListViewHolder.OnItemClickListener) :
    ListAdapter<AthletesListResponseModel.Athlete, AthletesListAdapter.AthletesListViewHolder>(
        AthletessModelDiffCallback()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AthletesListViewHolder {
        return AthletesListViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: AthletesListViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClickListener)
    }

    class AthletessModelDiffCallback :
        DiffUtil.ItemCallback<AthletesListResponseModel.Athlete>() {
        override fun areItemsTheSame(
            oldItem: AthletesListResponseModel.Athlete,
            newItem: AthletesListResponseModel.Athlete
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: AthletesListResponseModel.Athlete,
            newItem: AthletesListResponseModel.Athlete
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onViewRecycled(holder: AthletesListViewHolder) {
        super.onViewRecycled(holder)
        holder.recycle()
    }

    class AthletesListViewHolder(private val binding: HomeListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun create(parent: ViewGroup): AthletesListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = HomeListItemBinding.inflate(layoutInflater, parent, false)
                return AthletesListViewHolder(binding)
            }
        }

        fun bind(
            obj: AthletesListResponseModel.Athlete,
            onItemClickListener: OnItemClickListener
        ) {
            binding.setVariable(BR.model, obj)
            binding.executePendingBindings()
            itemView.setOnClickListener {
               // onItemClickListener.onItemClick(obj)
            }
        }

        fun recycle() {
            itemView.setOnClickListener(null)
        }

        interface OnItemClickListener {
            fun onItemClick(AthletesId: Int?)
        }
    }

}
