var APPOINTMENT_CREATED = "Đã khởi tạo";
var ADMIN_APPROVED = "Admin đã phê duyệt";
var DOCTOR_REJECTED = "Bác sĩ từ chối";
var CANCELLED = "Đã hủy";


async function searchAppointment(page) {
    var param = document.getElementById("param").value
    var start = document.getElementById("start").value
    var end = document.getElementById("end").value

    var url = 'http://localhost:8080/api/v1/appointment/admin/all?page='+page+"&size=3&param="+param;
    if(start != "" && start != null && end != null && end != ""){
        url += '&start='+start+'&end='+end;
    }
    const response = await fetch(url, {
        method: 'GET',
        headers: new Headers({
        })
    });
    var result = await response.json();
    console.log(result);
    var list = result.content;
    var totalPage = result.totalPages
    var main = '';


    var urls = 'http://localhost:8080/api/v1/appointment/admin/all-status';
    const res = await fetch(urls, {
        method: 'GET',
        headers: new Headers({
        })
    });
    var liststa = await res.json();
    console.log(liststatus)

    var liststatus = [];
    for(j=0; j<liststa.length; j++){
        liststatus.push(liststa[j])
    }
    for (i = 0; i < list.length; i++) {
        var st = `<div class="dropdown dropdown-action">
                    <a href="#" class="action-icon dropdown-toggle" data-toggle="dropdown" aria-expanded="false"><i class="fa fa-ellipsis-v"></i></a>
                    <div class="dropdown-menu dropdown-menu-right">`;
        for(j=0; j<liststatus.length; j++){
            st += `<a onclick="activeApoi(${list[i].id},'${liststatus[j]}')" class="dropdown-item" href="#">${liststatus[j]}</a>`
        }
        st += `</div></div>`
        if(list[i].appointmentStatus === CANCELLED){
            st = '';
        }
        if(list[i].appointmentStatus === DOCTOR_REJECTED){
            st = `<button class="btn btn-primary" onclick="showAdd(${list[i].speciality.id},${list[i].doctor.id},${list[i].id}, '${list[i].speciality.name}')">Chọn bác sĩ</button>`;
        }
        var ngs = list[i].patient.dob;
        if(list[i].patient.dob == null){
            ngs = ''
        }
        main += `<tr>
                    <td>${list[i].id}</td>
                    <td>${list[i].patient.name}</td>
                    <td>${ngs}</td>
                    <td>${list[i].doctor.user.name}</td>
                    <td>${list[i].speciality.name}</td>
                    <td>${list[i].appointmentDate}</td>
                    <td>${list[i].appointmentTime}</td>
                    <td>
                       ${list[i].appointmentStatus}
                    </td>
                    <td class="text-right">
                        ${st}
                    </td>
                </tr>`
    }
    document.getElementById("listApp").innerHTML = main
    var mainpage = ''
    for(k=1; k<= totalPage; k++){
        mainpage += '<li onclick="searchAppointment('+(Number(k)-1)+')" class="page-item"><a class="page-link">'+k+'</a></li>'
    }
    document.getElementById("listpage").innerHTML = mainpage
}

async function showAdd(idspecialy, iddoctor,idappointment, ck){
    $('#chonbsmoi').modal("show");
    console.log((idspecialy))
    console.log(ck)
    idapp = idappointment;
    document.getElementById("chuyenkhoabs").innerHTML = ck;
    var urls = 'http://localhost:8080/api/v1/doctor/public/findDoctorBySpecialy?idspecialy='+idspecialy+'&iddoctor='+iddoctor;
    const res = await fetch(urls, {
        method: 'GET',
        headers: new Headers({
        })
    });
    var list = await res.json();
    console.log(list)
    var main = ''
    for(i=0; i<list.length; i++){
        main += `<option value="${list[i].id}">${list[i].user.name}</option>`
    }
    document.getElementById("listdoctor").innerHTML = main;
}

function hideAdd(){
    $("#chonbsmoi").modal("hide");
}

async function activeApoi(id, status) {
    var con = confirm("Bạn chắc chắn muốn chuyển trạng thái lịch hẹn?");
    if(con){
        var urls = 'http://localhost:8080/api/v1/appointment/admin/accept?id='+id+"&status="+status;
        const response = await fetch(urls, {
            method: 'POST',
            headers: new Headers({
            })
        });
        if (response.status < 300) {
            window.location.reload();
        }
        else {
            alert("thất bại")
        }
    }
}
var idapp = null;
async function changeDoctor() {
    var iddoctor = document.getElementById("listdoctor").value;
    var con = confirm("Xác nhận chuyển bác sĩ?");
    if(con){
        var urls = 'http://localhost:8080/api/v1/appointment/public/changeDoctor?idapp='+idapp+"&iddoctor="+iddoctor;
        const response = await fetch(urls, {
            method: 'POST',
            headers: new Headers({
            })
        });
        if (response.status < 300) {
            alert("Thành công!")
            window.location.reload();
        }
        else {
            alert("thất bại")
        }
    }
}