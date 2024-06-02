package com.apprajapati.compose_todo.ui.screens.appbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apprajapati.compose_todo.R
import com.apprajapati.compose_todo.data.models.Priority
import com.apprajapati.compose_todo.ui.components.PriorityItem
import com.apprajapati.compose_todo.ui.theme.APP_BAR_HEIGHT
import com.apprajapati.compose_todo.ui.theme.SMALL_PADDING
import com.apprajapati.compose_todo.ui.theme.Typography
import com.apprajapati.compose_todo.ui.theme.md_theme_light_primaryContainer
import com.apprajapati.compose_todo.ui.viewmodels.SharedViewModel
import com.apprajapati.compose_todo.util.SearchAppbarState
import com.apprajapati.compose_todo.util.TrailingIconState

@Composable
fun ListAppbar(
    mViewModel: SharedViewModel,
    searchState: SearchAppbarState,
    searchTextState: String
) {
    when (searchState) {
        SearchAppbarState.CLOSED -> {
            DefaultAppBar(
                onSearchClicked = {
                    mViewModel.searchAppbarState.value = SearchAppbarState.OPENED
                }, onFilterClicked = {

                }, onDeleteAll = {

                })
        }

        else -> {
            SearchAppBar(
                searchString = searchTextState,
                onTextChange = { searchString ->
                    mViewModel.searchTextState.value = searchString
                },
                onCloseClicked = {
                    mViewModel.searchAppbarState.value = SearchAppbarState.CLOSED
                    mViewModel.searchTextState.value = ""
                },
                onSearchClicked = {})
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultAppBar(
    onSearchClicked: () -> Unit,
    onFilterClicked: (Priority) -> Unit,
    onDeleteAll: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.top_bar_title_tasks))
        },
        actions = {
            DefaultAppbarActions(onSearchClicked, onFilterClicked, onDeleteAll)
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = md_theme_light_primaryContainer)
    )
}

@Composable
fun DefaultAppbarActions(
    onSearchClicked: () -> Unit,
    onFilterClicked: (Priority) -> Unit,
    onDeleteAll: () -> Unit
) {

    DefaultAppbarActionSort(onFilterClicked)
    DefaultAppbarActionSearch(onSearchClicked)
    DefaultAppbarActionDeleteAll(onDeleteAll)

}

@Composable
fun DefaultAppbarActionSearch(onSearchClicked: () -> Unit) {
    IconButton(
        onClick = onSearchClicked,
        content = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = stringResource(id = R.string.search_icon_content_desc),
                tint = Color.Black
            )
        })
}

@Composable
fun DefaultAppbarActionDeleteAll(onDeleteAll: () -> Unit) {
    var isOpen by remember {
        mutableStateOf(false)
    }

    IconButton(
        onClick = {
            isOpen = true
        },
        content = {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = stringResource(id = R.string.more_menu_drop_down),
                tint = Color.Black
            )
        })
    DropdownMenu(expanded = isOpen, onDismissRequest = { isOpen = false }) {
        DropdownMenuItem(
            text = {
                Text(
                    modifier = Modifier.padding(SMALL_PADDING),
                    text = stringResource(id = R.string.menu_delete_all),
                    style = Typography.titleSmall
                )
            },
            onClick = {
                isOpen = false
                onDeleteAll()
            })
    }

}

@Composable
fun DefaultAppbarActionSort(onFilterClicked: (Priority) -> Unit) {

    var expanded by remember {
        mutableStateOf(false)
    }

    IconButton(
        onClick = {
            expanded = true
        }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_filter_list),
            contentDescription = stringResource(id = R.string.filter_icon_content_desc),
            tint = Color.Black
        )
    }
    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        DropdownMenuItem(
            text = { PriorityItem(priority = Priority.LOW) },
            onClick = {
                expanded = false
                onFilterClicked(Priority.LOW)
            })
        DropdownMenuItem(
            text = { PriorityItem(priority = Priority.MEDIUM) },
            onClick = {
                expanded = false
                onFilterClicked(Priority.MEDIUM)

            })
        DropdownMenuItem(
            text = { PriorityItem(priority = Priority.HIGH) },
            onClick = {
                expanded = false
                onFilterClicked(Priority.HIGH)
            })
        DropdownMenuItem(
            text = { PriorityItem(priority = Priority.NONE) },
            onClick = {
                expanded = false
                onFilterClicked(Priority.NONE)
            })


    }
}

@Composable
fun SearchAppBar(
    searchString: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit
) {

    var trailingIconState by remember {
        mutableStateOf(TrailingIconState.CLOSE)
    }

    Surface(
        shape = RectangleShape,// RoundedCornerShape(50),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 55.dp)
            .height(APP_BAR_HEIGHT),
        shadowElevation = 4.dp,
        // color = MaterialTheme.colorScheme.primary,
        //RoundedCornerShape(50)
    ) {
        TextField(
            value = searchString,
            onValueChange = {
                onTextChange(it)
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    modifier = Modifier.alpha(0.5f),
                    text = stringResource(id = R.string.search_bar_placeholder_string),
                    //color = Color.White
                )
            },
            textStyle = TextStyle(color = MaterialTheme.colorScheme.contentColorFor(Color.White)),
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    modifier = Modifier.alpha(0.5f),
                    contentDescription = stringResource(id = R.string.search_bar_placeholder_string)
                )
            },
            trailingIcon = {
                IconButton(onClick = {
                    when (trailingIconState) {
                        TrailingIconState.DELETE -> {
                            onTextChange("")
                            trailingIconState = TrailingIconState.CLOSE
                        }

                        TrailingIconState.CLOSE -> {
                            if (searchString.isNotEmpty()) {
                                onTextChange("")
                            } else {
                                onCloseClicked()
                                trailingIconState = TrailingIconState.DELETE
                            }
                        }
                    }
                }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(id = R.string.search_bar_close_button)
                    )
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                onSearchClicked(searchString)
            }),
            colors = TextFieldDefaults.colors(
                cursorColor = MaterialTheme.colorScheme.contentColorFor(
                    md_theme_light_primaryContainer
                ),
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            )
        )
    }
}


@Composable
@Preview
fun DefaultAppBarPreview() {
    DefaultAppBar(onSearchClicked = {}, onFilterClicked = {}, onDeleteAll = {})
}

@Composable
@Preview
fun SearchAppbarPreview() {
    SearchAppBar(searchString = "Search", onTextChange = {}, onCloseClicked = {}) {

    }
}