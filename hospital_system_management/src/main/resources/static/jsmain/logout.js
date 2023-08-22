$('#logout-button').click(function() {
    let jwtToken = getJwtToken()
    if (jwtToken) {
        $.ajax({
            url: '/api/v1/authentication/logout',
            type: 'POST',
            headers: {
                'Authorization': 'Bearer' + " " + jwtToken
            },
            success: function () {
                localStorage.clear()
                toastr.success("Thoát thành công")

                setTimeout(function () {
                    window.location.href = 'http://localhost:8080/user';
                }, 1000)
            },
            error: function () {

            }
        })
    } else {
        toastr.warning("Bạn chưa đăng nhập")
    }
});
function getJwtToken() {
    return localStorage.getItem('jwtToken')
}