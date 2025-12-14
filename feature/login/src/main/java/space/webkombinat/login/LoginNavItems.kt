package space.webkombinat.login

sealed class LoginNavItems(
    val route: String,
    val label: String,
) {
    object Login: LoginNavItems(
        route = "login_google",
        label = "ログイン"
    )
    object UserData: LoginNavItems(
        route = "user_data",
        label = "ユーザーデータ"
    )
    companion object {
        val loginRoutes by lazy { setOf(Login.route, UserData.route) }
    }
}