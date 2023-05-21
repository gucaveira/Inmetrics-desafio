package com.gustavo.rocha.testing.model

import com.gustavo.rocha.core.domain.modal.ReposUser

class UserReposFactory {

    fun create(repos: Repos) = when (repos) {

        Repos.AgendamentoWorkManager -> ReposUser(
            htmlURL = "https://github.com/gucaveira/agendamento_WorkManager",
            description = "agendamento de tarefea com WorkManager"
        )
        Repos.Aluvery -> ReposUser(
            "https://github.com/gucaveira/Aluvery",
            description = "curso sobre compose do android"
        )
    }

    sealed class Repos {
        object AgendamentoWorkManager : Repos()
        object Aluvery : Repos()
    }
}