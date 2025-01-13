package jp.developer.bbee.assemblepc.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import jp.developer.bbee.assemblepc.common.defaultJson
import jp.developer.bbee.assemblepc.data.store.dataStore
import jp.developer.bbee.assemblepc.domain.model.Composition
import jp.developer.bbee.assemblepc.domain.repository.CurrentCompositionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CurrentCompositionRepositoryImpl @Inject constructor(
    private val context: Context,
) : CurrentCompositionRepository {

    override val currentCompositionFlow = getCurrentComposition()

    override suspend fun saveCurrentComposition(composition: Composition) {
        context.dataStore.edit { settings ->
            settings[CURRENT_COMPOSITION_KEY] = defaultJson.encodeToString(composition)
        }
    }

    override suspend fun clearCurrentComposition() {
        context.dataStore.edit { settings ->
            settings.remove(CURRENT_COMPOSITION_KEY)
        }
    }

    private fun getCurrentComposition(): Flow<Composition?> =
        context.dataStore.data
            .map { pref ->
                pref[CURRENT_COMPOSITION_KEY]
                    ?.let { defaultJson.decodeFromString<Composition>(it) }
            }

    companion object {
        private val CURRENT_COMPOSITION_KEY = stringPreferencesKey("current_composition")
    }
}
