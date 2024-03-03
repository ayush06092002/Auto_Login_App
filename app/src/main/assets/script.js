function fillForm(data) {
    if (data && data.username && data.password) {
        var usernameField = document.getElementById("ft_un");
        var passwordField = document.getElementById("ft_pd");
        if (usernameField && passwordField) {
            usernameField.value = data.username;
            passwordField.value = data.password;
            var submitButton = document.querySelector("button[type='submit']");
            if (submitButton) {
                submitButton.click();
            }
        }
    }
}
