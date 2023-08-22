$(document).ready(function() {
    // Validation create speciality
    $("#create-speciality").validate({
        rules: {
            name: {
                required: true,
                minlength: 3,
                maxLength: 255,
            }
        },
        messages: {
            name: {
                required: "Vui lòng nhập tên chuyên khoa",
                minlength: "Tên chuyên khoa phải có ít nhất 3 ký tự",
                maxlength: "Tên chuyên khoa phải không được vượt quá 255 kí tự"
            }
        },
    });

    // Function create speciality
    $("#save-speciality").click(function (event) {
        event.preventDefault();
        let isValidCreatForm=$("#create-speciality").valid()
        if (!isValidCreatForm){
            return
        }
        let name = $("#create-speciality #name").val();
        let description = $("#create-speciality #description-speciality").val();
        console.log(name);
        let requestData = {
            name: name,
            description: description
        };
        $.ajax({
            url: '/api/v1/admin/specialities',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(requestData),
            success: function(response) {
                alert("Thêm chuyên khoa thành công!")
                window.location.href="http://localhost:8080/admin/departments"
            },
            error: function(xhr, status, error) {
                alert(xhr.responseJSON.message)
            }
        });
    });
})