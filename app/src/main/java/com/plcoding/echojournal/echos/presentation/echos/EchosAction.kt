package com.plcoding.echojournal.echos.presentation.echos

import com.plcoding.echojournal.echos.presentation.echos.models.EchosFilterChip
import com.plcoding.echojournal.echos.presentation.models.MoodUi

sealed interface EchosAction {

    data object OnMoodChipClick : EchosAction
    data class OnFilterByMoodClick(val moodUi: MoodUi) : EchosAction
    data object OnDismissMoodDropdown : EchosAction
    data object OnTopicChipClick : EchosAction

    data object OnDismissTopicDropdown : EchosAction

    data class OnFilterByTopicClick(val topic: String) : EchosAction
    data class OnRemoveFiltersClick(val filterType: EchosFilterChip) : EchosAction
    data object OnFabClick : EchosAction
    data object OnFabLongClick : EchosAction

    data object OnSettingsClick : EchosAction

}