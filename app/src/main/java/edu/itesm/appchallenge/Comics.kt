package edu.itesm.appchallenge

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Comics(var title: String, var desc: String, var path: String) : Parcelable {
    constructor():this("", "", "")
}
