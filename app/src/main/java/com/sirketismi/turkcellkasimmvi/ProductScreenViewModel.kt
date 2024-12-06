package com.sirketismi.turkcellkasimmvi

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProductScreenViewModel : BaseViewModel<ProductContract.Event, ProductContract.State, ProductContract.Effect>() {

    override fun setInitialState() = ProductContract.State(isLoading = true, isError = false, products = emptyList(), error = null)

    override fun handleEvents(event: ProductContract.Event) {
        when (event) {
            is ProductContract.Event.Retry -> {
                // log retry api
                setEffect { ProductContract.Effect.showToastMessage }
            }

            is ProductContract.Event.BackButtonClick -> {
                setEffect { ProductContract.Effect.Navigation.Back }
            }
            is ProductContract.Event.GetProducts -> {
                getProducts()
            }
            is ProductContract.Event.DeleteProduct -> {
                deleteProduct()
            }
            is ProductContract.Event.OnProductSelected -> {
                setEffect { ProductContract.Effect.Navigation.ProductDetail }
            }

        }


    }

    fun deleteProduct() {
        viewModelScope.launch {
            setState { copy(isLoading = true, isError = false) }
            delay(2000)
            setState { copy(isLoading = false, isError = true, error = "Silerken bir hata oluştu") }
        }
    }

    fun getProducts() {
        viewModelScope.launch {
            setState { copy(isLoading = true, isError = false) }
            delay(2000)

            var products = mutableListOf<Product>()
            products.add(Product(title = "Ürün Title-1", name = "Ürün İsmi-1"))
            products.add(Product(title = "Ürün Title-2", name = "Ürün İsmi-2"))
            products.add(Product(title = "Ürün Title-3", name = "Ürün İsmi-3"))

            setState { copy(isLoading = false, isError = false, products = products) }


            setEffect { ProductContract.Effect.showToastMessage }

        }
    }


}


class ProductContract {
    sealed class Event : ViewEvent {
        object Retry : Event()
        object BackButtonClick : Event()
        object GetProducts: Event()
        object OnProductSelected: Event()
        data class DeleteProduct(val productId: String) : Event()
    }

    data class State(
        val isLoading: Boolean,
        val isError : Boolean,
        val products: List<Product>,
        val error: String?
    ) :  ViewState

    sealed class Effect : ViewSideEffect {
        object ReloadData : Effect()
        object showToastMessage: Effect()

        sealed class Navigation : Effect() {
            object Back : Navigation()
            object ProductDetail : Navigation()
        }
    }
}

data class Product(val title : String, var name : String)