package me.asiimwedismas.kmega_mono.module_bakery.jobs

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.asiimwedismas.kmega_mono.module_bakery.domain.use_case.DatabaseSyncUseCase

@HiltWorker
class BakeryDBsync @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    val dBsync: DatabaseSyncUseCase,
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        withContext(Dispatchers.IO) {
            dBsync()
        }
        return Result.success()
    }

    companion object {
        const val WORK_NAME = "BAKERY_DB_SYNC"
        fun buildWorkRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<BakeryDBsync>().build()
        }
    }
}