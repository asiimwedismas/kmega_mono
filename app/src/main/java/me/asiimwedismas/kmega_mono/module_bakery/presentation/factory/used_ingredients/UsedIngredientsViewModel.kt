package me.asiimwedismas.kmega_mono.module_bakery.presentation.factory.used_ingredients

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import me.asiimwedismas.kmega_mono.common.SearchDates
import me.asiimwedismas.kmega_mono.common.di.CoroutineDispatchersProvider
import me.asiimwedismas.kmega_mono.common.getNextDate
import me.asiimwedismas.kmega_mono.common.getPreviousDate
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.*
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.UsedIngredientsRepository
import me.asiimwedismas.kmega_mono.module_bakery.domain.use_case.ingredient.GetAllIngredients
import me.asiimwedismas.kmega_mono.module_bakery.presentation.common_components.AddProductFormState
import java.util.*
import javax.inject.Inject

@HiltViewModel
class UsedIngredientsViewModel @Inject constructor(
    private val usedIngredientRepository: UsedIngredientsRepository,
    private val coroutineDispatchersProvider: CoroutineDispatchersProvider,
    private val allIngredients: GetAllIngredients,
) : ViewModel() {

    private val dates: SearchDates = SearchDates()
    val ingredientsList: LiveData<List<BakeryIngredient>> = allIngredients()
    val addIngredientFormState =
        AddProductFormState<BakeryIngredient>("Select Ingredient", emptyList())

    private var usedIngredientsSheet: UsedIngredientsSheet = UsedIngredientsSheet()

    private val _itemsList = mutableStateOf<List<UsedIngredientItem>>(listOf())
    val itemsList: State<List<UsedIngredientItem>> = _itemsList

    private val _totalIngredientsCost = mutableStateOf(0L)
    val totalIngredientsCost: State<Long> = _totalIngredientsCost

    private val _showAddFab = mutableStateOf(false)
    val showAddFab: State<Boolean> = _showAddFab

    private val _showAddItemInput = mutableStateOf(false)
    val showAddItemInput: State<Boolean> = _showAddItemInput

    private val _editStatus = mutableStateOf<Boolean>(false)
    val editStatus: State<Boolean> = _editStatus

    private val _isLoadingData = mutableStateOf<Boolean>(true)
    val isLoadingData: State<Boolean> = _isLoadingData

    var fetchSheetJob: Job? = null

    init {
        fetchSheet()
    }

    fun changeDate(calendar: Calendar) {
        dates.instance = MutableLiveData(calendar)
        fetchSheet()
    }

    private fun fetchSheet() {
        fetchSheetJob?.cancel()

        fetchSheetJob = viewModelScope.launch {
            Log.e("FECTH", "production: ")
            dates.selectedDate.value?.let { date ->
                _isLoadingData.value = true
                usedIngredientsSheet = usedIngredientRepository.getSheetForDate(date)
                if (usedIngredientsSheet == UsedIngredientsSheet()) {
                    initialiseEmptySheet()
                }
                _showAddFab.value = !usedIngredientsSheet.lock_status
                _showAddItemInput.value = !_showAddFab.value
                mutateStates()
            }
        }
    }

    private fun saveIngredientsSheet() {
        viewModelScope.launch {
            usedIngredientRepository.save(usedIngredientsSheet)
            mutateStates()
        }
    }

    fun deleteIngredientsSheet() {
        viewModelScope.launch {
            usedIngredientRepository.delete(usedIngredientsSheet.document_id)
            usedIngredientsSheet = UsedIngredientsSheet()
            initialiseEmptySheet()
            mutateStates()
        }
    }

    fun deleteItem(item: UsedIngredientItem, index: Int) {
        if (usedIngredientsSheet.items[index] == item) {
            usedIngredientsSheet.items.removeAt(index)
            saveIngredientsSheet()
        }
    }

    private fun initialiseEmptySheet() {
        with(usedIngredientsSheet) {
            date = dates.selectedDate.value!!
            utc = dates.instance.value!!.timeInMillis
            document_id = date
            document_author_id = ""
            document_author_name = ""
        }
    }

    private fun mutateStates() {
        _editStatus.value = !usedIngredientsSheet.lock_status
        _itemsList.value = usedIngredientsSheet.items
        _totalIngredientsCost.value = usedIngredientsSheet.totalUsedIngredients.toLong()
        _isLoadingData.value = false
    }

    fun onAddFabClick() {
        _showAddItemInput.value = true
        _showAddFab.value = false
    }

    fun onIngredientSelected(ingredient: BakeryIngredient) {
        addIngredientFormState.qtyInputHint = "Used ${ingredient.ingredient_pack_unit}"
        addIngredientFormState.onProductSelected(ingredient)
    }

    fun onQueryChanged(newQuery: String) {
        addIngredientFormState.onQueryChanged(newQuery) { item, query ->
            item.ingredient_name.contains(query, true)
        }
    }

    fun onAddItem() {
        with(addIngredientFormState) {
            val ingredient = UsedIngredientItem(selectedOption!!, qty!!)
            usedIngredientsSheet.items.add(ingredient)
            saveIngredientsSheet()
            qtyInputHint = "Qty"
            clearInputs()
        }
    }
}