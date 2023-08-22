async function loadAllSpecialy() {
    var url = 'http://localhost:8080/api/v1/speciality/admin/find-all';
    const response = await fetch(url, {
        method: 'GET',
        headers: new Headers({
        })
    });
    var list = await response.json();
    var main = '<option value="">Tất cả chuyên khoa</option>';
    for (i = 0; i < list.length; i++) {
        main += ` <option value="${list[i].id}">${list[i].name}</option>`
    }
    document.getElementById("listSpecialy").innerHTML = main
}


async function searchDoctors(page) {
    var param = document.getElementById("param").value
    var special = document.getElementById("listSpecialy").value

    var url = 'http://localhost:8080/api/v1/doctor/admin/all-page?param='+param+"&size=3&page="+page;
    if(special != "" && special != null){
        url = 'http://localhost:8080/api/v1/doctor/admin/all-page?param='+param+'&idspecialy='+special+"&size=3&page="+page;
    }
    const response = await fetch(url, {
        method: 'GET',
        headers: new Headers({
        })
    });
    var result = await response.json();
    var list = result.content;
    var totalPage = result.totalPages
    var main = '';

    for (i = 0; i < list.length; i++) {
        var ck = '';
        for(j=0; j<list[i].specialities.length; j++){
            ck += list[i].specialities[j].name +"<br>"
        }
        main += `<tr>
                    <td>${list[i].user.name}</td>
                    <td>${ck}</td>
                    <td>${list[i].doctorLevel}</td>
                    <td>${list[i].user.gender}</td>
                    <td>${list[i].address}</td>
                    <td class="text-right">
                        <div class="d-flex justify-content-between">
                            <a class="btn btn-link p-0 submit-update-btn" onclick="loadDetail(${list[i].id})">
                                <i class="fa-solid fa-pencil fa-lg text-primary"></i>
                            </a>
                            <button onclick="deleteDoctor(${list[i].id})" class="btn btn-link p-0 mr-2 submit-delete-btn">
                                <span><i class="fa-solid fa-trash-can fa-lg text-danger"></i></span>
                            </button>
                        </div>
                    </td>
                </tr>`
    }
    document.getElementById("listDoctor").innerHTML = main
    var mainpage = ''
    for(i=1; i<= totalPage; i++){
        mainpage += '<li onclick="searchDoctors('+(Number(i)-1)+')" class="page-item"><a class="page-link">'+i+'</a></li>'
    }
    document.getElementById("listpage").innerHTML = mainpage
}

async function loadDetail(doctorId){
    $.ajax({
        url: "/api/v1/admin/doctor/" + doctorId,
        type: "GET",
        success: function (response) {
            $("#update-doctor #phone").val(response.phone)
            $("#update-doctor #doctorLevel").val(response.doctorLevel)
            $("#update-doctor #address").val(response.address)
            $("#update-doctor #introduce").val(response.introduce)
            $("#update-doctor #iddoctorupdate").val(doctorId)
            $("#update-doctor #id").val(doctorId)
            let specialities = [];
            response.specialities.forEach(speciality => {
                specialities.push(speciality.id);
            });
            $("#update-doctor #speciality").val(specialities).trigger('change')
            $("#update-doctor-modal").modal("show");
        },
        error: function (response) {
            console.log(response)
        }
    })
}

async function deleteDoctor(doctorId){
    Swal.fire({
        title: 'Bạn có chắc muốn xóa bác sĩ này không?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                url: '/api/v1/admin/doctor/' + doctorId,
                type: 'DELETE',
                success: function (response) {
                    Swal.fire({
                        title: 'Xóa bác sĩ thành công',
                        icon: 'success',
                        showConfirmButton: false,
                        timer: 1500
                    }).then((result) => {
                        if (result.dismiss === Swal.DismissReason.timer) {
                            window.location.href = 'http://localhost:8080/admin/doctors';
                        }
                    });
                },
                error: function (xhr, status, error) {
                    console.log(error);
                    Swal.fire({
                        title: 'Đã xảy ra lỗi khi xóa bác sĩ',
                        icon: 'error'
                    });
                }
            });
        }
    });
}