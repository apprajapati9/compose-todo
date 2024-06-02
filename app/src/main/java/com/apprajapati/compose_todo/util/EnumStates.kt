package com.apprajapati.compose_todo.util

enum class SearchAppbarState {
    OPENED,
    CLOSED,
    TRIGGERED
}

//For search app bar trailing icon i.e close button state
enum class TrailingIconState {
    DELETE,  //Delete text
    CLOSE      // if text is empty then close the search bar to return to default app bar
}