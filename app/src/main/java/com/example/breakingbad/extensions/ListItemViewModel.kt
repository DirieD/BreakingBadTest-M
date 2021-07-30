package com.example.breakingbad.extensions

import com.example.breakingbad.extensions.GenericAdapter.OnListItemViewClickListener

//Generic viewModel class that is used with the generic adapter
abstract class ListItemViewModel {
    var adapterPosition: Int = -1
    var onListItemViewClickListener: OnListItemViewClickListener? = null
}
