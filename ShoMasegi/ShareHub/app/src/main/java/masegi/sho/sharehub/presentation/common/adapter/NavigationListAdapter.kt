package masegi.sho.sharehub.presentation.common.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import masegi.sho.sharehub.R
import masegi.sho.sharehub.data.model.NavigationItem
import masegi.sho.sharehub.databinding.ItemMenuBinding

/**
 * Created by masegi on 2018/02/20.
 */

class NavigationListAdapter(
        context: Context,
        private val list: List<NavigationItem>
) : ArrayAdapter<NavigationItem>(context, R.layout.item_menu, list) {


    // MARK: - Property

    private var inflater: LayoutInflater = LayoutInflater.from(context)


    // MARK: - ArrayAdapter

    @SuppressLint("ResourceAsColor")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var binding: ItemMenuBinding?

        if (convertView == null) {

            binding = ItemMenuBinding.inflate(inflater, parent, false)
            binding.root.tag = binding
        }
        else {

            binding = convertView.tag as ItemMenuBinding
        }
        binding?.item = getItem(position)
        return binding?.root
    }

    override fun getCount(): Int = list.size

    override fun getItem(position: Int): NavigationItem {

        return list[position]
    }
}