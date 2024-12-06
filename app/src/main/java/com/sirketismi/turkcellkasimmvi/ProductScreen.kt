package com.sirketismi.turkcellkasimmvi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun ProductScreen(onProductDetail: () -> Unit) {
    val productViewModel : ProductScreenViewModel = viewModel()
    var isAlertMessageShown by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        productViewModel.effect.onEach {

            when(it) {
                is ProductContract.Effect.showToastMessage -> {
                    isAlertMessageShown = true
                }
                is ProductContract.Effect.Navigation.ProductDetail -> {
                    onProductDetail()
                }
                else-> {}
            }


        }.collect()
    }

    LaunchedEffect(Unit) {
        productViewModel.setEvent(ProductContract.Event.GetProducts)
    }

    val state = productViewModel.viewState.collectAsState()
    when {
        state.value.isLoading -> Progress()
        state.value.isError -> NetworkError()
        else -> ProductList(state.value.products)
    }

    Button(onClick = {
        productViewModel.setEvent(ProductContract.Event.OnProductSelected)
    }) {
        Text("Ürün Detayına Git")
    }

    if(isAlertMessageShown) {
        showAlertMessage("Bir hata oluştu", {
            isAlertMessageShown = false
        }, {
            isAlertMessageShown = false
        })
    }
}

@Composable
fun ProductList(products : List<Product>) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(Modifier.size(200.dp))
            LazyColumn {
                items(products) {
                    Text(it.title)
                    Text(it.name)
                }
            }
        }
}

@Composable
fun Progress() {
    Box(modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun NetworkError() {

}

@Preview
@Composable
fun PrevieFunction() {
    ProductScreen {

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun showAlertMessage(message : String, onApprove : ()->Unit, onCancel : ()->Unit) {
    BasicAlertDialog(onDismissRequest = {
    }) {

        Card (modifier = Modifier.wrapContentWidth().wrapContentHeight()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(message)

                Row {

                    TextButton (onClick = {
                        onCancel()
                    }) {
                        Text("Vazgeç")
                    }

                    TextButton (onClick = {
                        onApprove()
                    }) {
                        Text("Tamam")
                    }
                }
            }
        }

    }
}


























