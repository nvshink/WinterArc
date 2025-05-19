package com.nvshink.winterarc.ui.utils

import com.nvshink.winterarc.R

enum class SortTypes {
    NAME_ASC {
        override val stringResourceName = R.string.sort_type_by_name_ASC
    },
    NAME_DESC {
        override val stringResourceName = R.string.sort_type_by_name_DESC
    };

    abstract val stringResourceName: Int
}