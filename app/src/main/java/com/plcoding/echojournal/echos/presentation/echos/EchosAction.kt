package com.plcoding.echojournal.echos.presentation.echos

import com.plcoding.echojournal.echos.presentation.echos.models.EchosFilterChip

sealed interface EchosAction {

    data object OnMoodChipClick: EchosAction
    data object OnTopicChipClick: EchosAction
    data class OnRemoveFiltersClick(val filterType: EchosFilterChip): EchosAction
     data object OnFabClick: EchosAction
     data object OnFabLongClick: EchosAction

    data object OnSettingsClick : EchosAction

}