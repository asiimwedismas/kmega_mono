package me.asiimwedismas.kmega_mono.module_bakery.domain.use_case

import androidx.lifecycle.LiveData
import me.asiimwedismas.bakery_module.domain.model.BakeryIngredient
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.IngredientRepository

data class IngredientUseCases(
    val insertIngredients: InsertIngredients,
    val updateIngredients: UpdateIngredients,
    val deleteIngredients: DeleteIngredients,
    val getAllIngredients: GetAllIngredients,
)

class InsertIngredients(
    private val repository: IngredientRepository,
) {
    suspend operator fun invoke(vararg ingredients: BakeryIngredient) {
        repository.insert(*ingredients)
    }
}

class UpdateIngredients(
    private val repository: IngredientRepository,
) {
    suspend operator fun invoke(vararg ingredients: BakeryIngredient) {
        repository.update(*ingredients)
    }
}

class DeleteIngredients(
    private val repository: IngredientRepository,
) {
    suspend operator fun invoke(vararg ingredients: BakeryIngredient) {
        repository.delete(*ingredients)
    }
}

class GetAllIngredients(
    private val repository: IngredientRepository,
) {
    operator fun invoke(): LiveData<List<BakeryIngredient>> {
        return repository.getAllIngredients()
    }
}

