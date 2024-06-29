package ddwu.com.mobile.finalreport.feature.community

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ddwu.com.mobile.finalreport.data.model.community.CommunityResponse
import ddwu.com.mobile.finalreport.databinding.ItemCommunityListBinding

class CommunityAdapter : ListAdapter<CommunityResponse, CommunityAdapter.CommunityViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityViewHolder {
        val binding = ItemCommunityListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommunityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommunityViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class CommunityViewHolder(private val binding: ItemCommunityListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CommunityResponse) {
            binding.apply {
                title.text = item.title ?: ""
                content.text = item.content ?: ""
                createdTime.text = item.createdTime ?: ""
                val name = "icon_" + item.iconType
                val resourceId = itemView.context.resources.getIdentifier(name, "drawable", itemView.context.packageName)
                if (resourceId != 0) {
                    val drawable = ContextCompat.getDrawable(itemView.context, resourceId)
                    image.setImageDrawable(drawable)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<CommunityResponse>() {
        override fun areItemsTheSame(oldItem: CommunityResponse, newItem: CommunityResponse): Boolean {
            return oldItem.diarySeq == newItem.diarySeq
        }

        override fun areContentsTheSame(oldItem: CommunityResponse, newItem: CommunityResponse): Boolean {
            return oldItem == newItem
        }
    }
}
