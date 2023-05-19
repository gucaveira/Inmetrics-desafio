package com.gustavo.rocha.inmetrics.ui.fragment.userdetail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailViewArg(
    val name: String,
    val imageUrl: String,
) : Parcelable