package com.example.pasiekaapp

import kotlinx.coroutines.flow.Flow

interface BaseUseCase<in Parameter: BaseUseCase.Params, out DataType: Any> {

    interface Params

    suspend operator fun invoke(parameter: Parameter): Flow<Result<DataType>>
}
