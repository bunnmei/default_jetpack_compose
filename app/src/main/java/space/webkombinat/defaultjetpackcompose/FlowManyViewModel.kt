package space.webkombinat.defaultjetpackcompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FlowManyViewModel: ViewModel() {

    private val A_data = MutableStateFlow(0)
    val a_data = A_data.asStateFlow()

    private val B_data = MutableStateFlow(0)
    val b_data = B_data.asStateFlow()

    private val C_data = MutableStateFlow(0)
    val c_data = C_data.asStateFlow()

    private val D_data = MutableStateFlow(0)
    val d_data = D_data.asStateFlow()

    private val E_data = MutableStateFlow(0)
    val e_data = E_data.asStateFlow()

    private val F_data = MutableStateFlow(0)
    val f_data = F_data.asStateFlow()

    private val G_data = MutableStateFlow(0)
    val g_data = G_data.asStateFlow()

    private val H_data = MutableStateFlow(0)
    val h_data = H_data.asStateFlow()

    private val I_data = MutableStateFlow(0)
    val i_data = I_data.asStateFlow()

    private val J_data = MutableStateFlow(0)
    val j_data = J_data.asStateFlow()

    fun update() {
        var data = 0
        viewModelScope.launch {
            while (15 > data ) {
                A_data.value++
                B_data.value++
                C_data.value++
                D_data.value++
                E_data.value++
                F_data.value++
                G_data.value++
                H_data.value++
                I_data.value++
                J_data.value++
                data++
            }
        }
    }
}