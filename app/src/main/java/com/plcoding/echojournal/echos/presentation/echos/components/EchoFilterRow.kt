package com.plcoding.echojournal.echos.presentation.echos.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.plcoding.echojournal.R
import com.plcoding.echojournal.core.presentation.designsystem.chips.MultiChoiceChip
import com.plcoding.echojournal.core.presentation.designsystem.dropdown.Selectable
import com.plcoding.echojournal.core.presentation.designsystem.dropdown.SelectableDropdownOptionsMenu
import com.plcoding.echojournal.core.presentation.util.UiText
import com.plcoding.echojournal.echos.presentation.echos.EchosAction
import com.plcoding.echojournal.echos.presentation.echos.models.EchosFilterChip
import com.plcoding.echojournal.echos.presentation.echos.models.MoodChipContent
import com.plcoding.echojournal.echos.presentation.models.MoodUi

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EchoFilterRow(
    moodChipContent: MoodChipContent,
    hasActiveMoodFilters: Boolean,
    selectedEchoFilterChip: EchosFilterChip?,
    moods: List<Selectable<MoodUi>>,
    topicChipTitle: UiText,
    hasActiveTopicFilters: Boolean,
    topics: List<Selectable<String>>,
    onAction: (EchosAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var isExpanded by remember {
        mutableStateOf(false)
    }
    var dropDownOffset by remember {
        mutableStateOf(IntOffset.Zero)
    }

    val configuration = LocalConfiguration.current
    val dropDownMaxHeight = (configuration.screenHeightDp * 0.3f).dp
    FlowRow(
        modifier = modifier
            .padding(16.dp)
            .onGloballyPositioned{
                dropDownOffset = IntOffset(
                    x = 0,
                    y = it.size.height
                )
            },
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        MultiChoiceChip(
            displayText = moodChipContent.title.asString(),
            onClick = { onAction(EchosAction.OnMoodChipClick) },
            leadingContent = {
                if (moodChipContent.iconsRes.isNotEmpty()) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy((-4).dp),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        moodChipContent.iconsRes.forEach { iconRes ->
                            Image(
                                imageVector = ImageVector.vectorResource(iconRes),
                                contentDescription = moodChipContent.title.asString(),
                                modifier = Modifier.height(16.dp)
                            )
                        }

                    }
                }
            },
            isClearVisible = hasActiveMoodFilters,
            onClearButtonClick = {
                onAction(EchosAction.OnRemoveFiltersClick(EchosFilterChip.MOODS))
            },
            isHighlighted = hasActiveMoodFilters || selectedEchoFilterChip == EchosFilterChip.MOODS,
            isDropDownVisible = selectedEchoFilterChip == EchosFilterChip.MOODS,
            dropdownMenu = {
                SelectableDropdownOptionsMenu(
                    items = moods,
                    itemsDisplayText = { moodUi -> moodUi.title.asString(context) },
                    onDismiss = { onAction(EchosAction.OnDismissMoodDropdown) },
                    key = { moodUi -> moodUi.title },
                    onItemClick = { moodUi ->
                        onAction(EchosAction.OnFilterByMoodClick(moodUi.item))
                    },
                    dropdownOffset = dropDownOffset,
                    maxDropdownHeight = dropDownMaxHeight,
                    leadingIcon = { moodUi ->
                        Image(
                            imageVector = ImageVector.vectorResource(moodUi.iconSet.fill),
                            contentDescription = moodUi.title.asString(),
                            modifier = Modifier.padding(8.dp)
                        )
                    }

                )

            }
        )
        MultiChoiceChip(
            displayText = topicChipTitle.asString(),
            onClick = { onAction(EchosAction.OnTopicChipClick) },
            isClearVisible = hasActiveTopicFilters,
            onClearButtonClick = {
                onAction(EchosAction.OnRemoveFiltersClick(EchosFilterChip.TOPICS))
            },
            isHighlighted = hasActiveTopicFilters || selectedEchoFilterChip == EchosFilterChip.TOPICS,
            isDropDownVisible = selectedEchoFilterChip == EchosFilterChip.TOPICS,
            dropdownMenu = {
                if (topics.isEmpty()) {
                    SelectableDropdownOptionsMenu(
                        items = listOf(
                            Selectable(
                                item = stringResource(R.string.you_don_t_have_any_topics_yet),
                                selected = false
                            )
                        ),
                        itemsDisplayText = { it },
                        onDismiss = { onAction(EchosAction.OnDismissTopicDropdown) },
                        key = { it },
                        dropdownOffset = dropDownOffset,
                        maxDropdownHeight = dropDownMaxHeight,
                        onItemClick = {},
                    )
                } else {
                    SelectableDropdownOptionsMenu(
                        items = topics,
                        itemsDisplayText = { topic -> topic },
                        onDismiss = { onAction(EchosAction.OnDismissTopicDropdown) },
                        key = { topic -> topic },
                        onItemClick = { topic ->
                            onAction(EchosAction.OnFilterByTopicClick(topic.item))
                        },
                        dropdownOffset = dropDownOffset,
                        maxDropdownHeight = dropDownMaxHeight,
                        leadingIcon = { topic ->
                            Image(
                                imageVector = ImageVector.vectorResource(R.drawable.hashtag),
                                contentDescription = topic,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    )

                }
            }

        )
    }
}