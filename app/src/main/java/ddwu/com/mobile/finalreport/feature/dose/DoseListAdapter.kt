package ddwu.com.mobile.finalreport.feature.dose

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ddwu.com.mobile.finalreport.data.model.community.CommunityResponse
import ddwu.com.mobile.finalreport.databinding.ItemCommunityListBinding

class DoseListAdapter : ListAdapter<CommunityResponse, DoseListAdapter.DoseListViewHolder>(DiffCallback()) {

    private var onItemLongClickListener: ((CommunityResponse) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoseListViewHolder {
        val binding = ItemCommunityListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DoseListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DoseListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    fun setOnItemLongClickListener(listener: (CommunityResponse) -> Unit) {
        onItemLongClickListener = listener
    }

    inner class DoseListViewHolder(private val binding: ItemCommunityListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CommunityResponse) {
            binding.apply {
                title.text = item.title ?: ""
                content.text = item.content ?: ""
                createdTime.text = item.createdTime ?: ""
                btnAdd.text = "처방전 보기"
                val name = "icon_" + item.iconType
                val resourceId = itemView.context.resources.getIdentifier(name, "drawable", itemView.context.packageName)
                if (resourceId != 0) {
                    val drawable = ContextCompat.getDrawable(itemView.context, resourceId)
                    image.setImageDrawable(drawable)
                }

                root.setOnLongClickListener {
                    onItemLongClickListener?.invoke(item)
                    true
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
