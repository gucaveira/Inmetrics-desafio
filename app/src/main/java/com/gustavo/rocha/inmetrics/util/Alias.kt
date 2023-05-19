package com.gustavo.rocha.inmetrics.util

import android.view.View
import com.gustavo.rocha.core.domain.modal.UserGitHub

typealias OnItemClickListener = (userDetail: UserGitHub, view: View) -> Unit
