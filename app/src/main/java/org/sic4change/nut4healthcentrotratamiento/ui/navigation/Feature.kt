package org.sic4change.nut4healthcentrotratamiento.ui.navigation

enum class Feature(val route: String) {
    LOGIN("login"),

    HOME("home"),

    SETTINGS("settings"),

    TUTORS("tutors"),
    CREATETUTOR("createtutor"),
    EDITTUTOR("edittutor"),
    TUTORS_DETAIL("tutorsdetail"),

    CHILDS("childs"),
    CHILD_DETAIL ("childdetail"),
    CREATECHILD("createchild"),
    EDITCHILD("editchild"),

    CASES("cases"),
    CASE_DETAIL ("casedetail"),
    CREATECASE("createcase"),
    EDITCASE("editcase"),

    VISITS("visits"),
}