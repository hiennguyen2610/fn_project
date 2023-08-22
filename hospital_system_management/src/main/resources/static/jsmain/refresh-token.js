function refreshToken() {
    let jwt = localStorage.getItem("jwt")
    let refreshToken = localStorage.getItem("refreshToken")
    let formData = {
        refreshToken: refreshToken
    }
    if (!jwt) {
        return
    }
    $.ajax({
        url: '/api/v1/authentication/refresh-token',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(formData),
        headers: {
            'Authorization': 'Bearer' + " " + jwt
        },
        success: function (response) {
            localStorage.setItem("jwt", response.jwt)
        },
        error: function (xhr, status, error) {
            console.log(error)
        }
    });
}

setInterval(refreshToken, 29*60 * 1000);