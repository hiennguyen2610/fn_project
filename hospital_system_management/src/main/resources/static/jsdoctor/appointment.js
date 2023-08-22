var APPOINTMENT_CREATED = "Đã khởi tạo";
var ADMIN_APPROVED = "Admin đã phê duyệt";
var DOCTOR_REJECTED = "Bác sĩ từ chối";
var CANCELLED = "Đã hủy";

var UNPAID = 'UNPAID'
var PAID = 'PAID'

async function searchAppointment(page) {
    var param = document.getElementById("param").value
    var start = document.getElementById("start").value
    var end = document.getElementById("end").value

    var url = 'http://localhost:8080/api/v1/appointment/doctor/all-appointment?page='+page+"&size=3&param="+param;
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


    var urls = 'http://localhost:8080/api/v1/appointment/doctor/all-status';
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
        var st = '';
        var mo = ''
        for(j=0; j<liststatus.length; j++){
            st += `<a onclick="activeApoi(${list[i].id},'${liststatus[j]}')" class="dropdown-item" href="#">${liststatus[j]}</a>`
        }
        if(list[i].appointmentStatus === CANCELLED || list[i].appointmentStatus === DOCTOR_REJECTED){
            st = '';
            mo = ''
        }
        else{
            mo = `<i onclick="openChonDichVu(${list[i].id})" class="fa fa-edit pointer"></i>`
        }
        var paystatus = ''
        if(list[i].paymentStatus == PAID){
            paystatus = `<p style="color: green;font-weight: bold">${list[i].paymentStatus}</p>`
            mo = `<i onclick="loadDsDv(${list[i].id})" class="fa fa-edit pointer"></i>`
        }if(list[i].paymentStatus == UNPAID){
            paystatus = `<p style="color: red">${list[i].paymentStatus}</p>`
        }
        main += `<tr>
                    <td>${list[i].id}</td>
                    <td>${list[i].patient.name}</td>
                    <td></td>
                    <td>${list[i].doctor.user.name}</td>
                    <td>${list[i].speciality.name}</td>
                    <td>${list[i].appointmentDate}</td>
                    <td>${list[i].appointmentTime}</td>
                    <td>${list[i].appointmentStatus}</td>
                    <td>${paystatus}</td>
                    <td class="text-right">
                        <div class="dropdown dropdown-action">
                            <a href="#" class="action-icon dropdown-toggle" data-toggle="dropdown" aria-expanded="false"><i class="fa fa-ellipsis-v"></i></a>
                            <div class="dropdown-menu dropdown-menu-right">
                               ${st}
                            </div>
                       </div>
                       
                       ${mo}
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

var listService = [];
async function loadAllDv(){
    var url = 'http://localhost:8080/api/v1/service/public/all-service';
    const response = await fetch(url, {
        method: 'GET',
        headers: new Headers({
        })
    });
    var list = await response.json();
    if(response.status < 300){
        for (i = 0; i < list.length; i++) {
            listService.push(list[i]);
        }
    }
}


async function loadDsDv(idApp){
    $("#chitietdv").modal('show');
    document.getElementById("idapps").innerHTML = idApp
    var urls = 'http://localhost:8080/api/v1/diagnosisDetail/public/find-by-appointment?idApp='+idApp;
    const res = await fetch(urls, {
        method: 'GET',
        headers: new Headers({
        })
    });
    var list = await res.json();
    var main = ''
    ctkList = [];
    kqkList = [];
    for(i=0; i<list.length; i++){
        var kq = list[i].result;
        var ctk = list[i].detail;
        if(kq == null){
            kq = ""
        }if(ctk == null){
            ctk = ""
        }
        main += ` <tr>
                        <td>${list[i].service.id}</td>
                        <td>${list[i].service.name}</td>
                        <td><input value="${ctk}" class="form-control" type="text" id="ctk${list[i].id}"></td>
                        <td><textarea class="form-control" id="kqk${list[i].id}">${kq}</textarea></td>
                   </tr>`
        objCtk = {"id":list[i].id, "name":"ctk"+list[i].id}
        objKqk = {"id":list[i].id, "name":"kqk"+list[i].id}
        ctkList.push(objCtk)
        kqkList.push(objKqk)
    }
    document.getElementById("listdvtvb").innerHTML = main

    var urlp = 'http://localhost:8080/api/v1/diagnosis/public/findByAppointment?idApp='+idApp;
    const resp = await fetch(urlp, {
        method: 'GET',
        headers: new Headers({
        })
    });
    var diagnosis = await resp.json();
    console.log(diagnosis)
    var detail = diagnosis.detail;
    var results = diagnosis.result;
    if (detail == null) {
        detai= ""
    } if (results == null) {
        results= ""
    }
    idBenhAn = diagnosis.id
    idApps = diagnosis.appointment.id
    document.getElementById("ghichu").value = detail
    await new Promise(r => setTimeout(r, 500));
    tinyMCE.get('editor').setContent(results)
}


async function openChonDichVu(idapp){
    document.getElementById("idapp").value = idapp
    var urls = 'http://localhost:8080/api/v1/diagnosisDetail/public/find-by-appointment?idApp='+idapp;
    const res = await fetch(urls, {
        method: 'GET',
        headers: new Headers({
        })
    });
    var listDetail = await res.json();

    var main = ''
    for (i = 0; i < listService.length; i++) {
        var checks = false;
        for(j=0; j<listDetail.length; j++){
            if(listDetail[j].service.id == listService[i].id){
                checks = true;
            }
        }
        if(checks == true){
            main += `<option selected value="${listService[i].id}">${listService[i].name}</option>`
        }
        else{
            main += `<option value="${listService[i].id}">${listService[i].name}</option>`
        }
    }
    document.getElementById("services").innerHTML = main;
    const ser = $("#services");
    ser.select2({
        placeholder: "- Chọn dịch vụ",
    });
    $("#chondichvu").modal('show');
}

function closeChonDv(){
    $("#chondichvu").modal('hide');
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

var listTime = ['08:00','08:30','09:00','09:30','10:00','10:30','11:00','11:30','13:00','13:30','14:00','14:30','15:00','15:30','16:00','16:30']


async function loadAllSpeciality(){
    var url = 'http://localhost:8080/api/v1/speciality/public/findByDoctor';
    const response = await fetch(url, {
        method: 'GET',
        headers: new Headers({
        })
    });
    var list = await response.json();
    console.log(list)
    var main = '';
    for (i = 0; i < list.length; i++) {
        main += `<option data-value="2" value="${list[i].id}" class="option">${list[i].name}</option>`
    }
    document.getElementById("listSpeciality").innerHTML = main
    time = null;
    document.getElementById("datecs").innerHTML = ""
    document.getElementById("chooseDate").value = ""
    document.getElementById("listTime").innerHTML = "please choose a doctor and date"
}

async function loadTime(){
    var dates = document.getElementById("chooseDate").value;
    var url = 'http://localhost:8080/api/v1/appointment/doctor/my-appointment?date='+dates;
    const response = await fetch(url, {
        method: 'GET',
        headers: new Headers({
        })
    });
    var list = await response.json();
    console.log(list)

    var main = ''
    for(i=0; i<listTime.length; i++){
        var check = false;
        for(j=0; j<list.length; j++){
            var t = list[j].appointmentTime.split(":")[0] +":"+list[j].appointmentTime.split(":")[1]
            if(t == listTime[i]){
                check = true;
            }
        }
        if(check == false){
            main += `</span><span onclick="chooseTime(this,'${listTime[i]}')" class="time-item">${listTime[i]}</span>`
        }
        else{
            main += `</span><span class="time-item useds">${listTime[i]}</span>`
        }
    }
    document.getElementById("listTime").innerHTML = main
}

function chooseTime(e, chooseTime){
    var list = document.getElementById("listTime");
    var le = list.getElementsByClassName("time-item").length;
    for(i=0; i<le; i++){
        list.getElementsByClassName("time-item")[i].classList.remove('active');
    }
    e.classList.add("active");
    time = chooseTime;
    document.getElementById("datecs").innerHTML = "date: "+ document.getElementById("chooseDate").value +", time: "+time
}

var time = null;
async function createAppointment(){
    var url = 'http://localhost:8080/api/v1/appointment/doctor/create';
    var name = document.getElementById("name").value;
    var email = document.getElementById("email").value;
    var phone = document.getElementById("phone").value;
    var gender = document.getElementById("gender").value;
    var address = document.getElementById("address").value;
    var chooseDate = document.getElementById("chooseDate").value;
    var symptom = document.getElementById("message").value;
    var dob = document.getElementById("dob").value;
    var speciality = document.getElementById("listSpeciality").value;
    checkInput(name,email,phone,gender,address,speciality,chooseDate,symptom);
    var req = {
        "speciality":{"id":speciality},
        "appointmentDate":chooseDate,
        "dob":dob,
        "appointmentTime":time,
        "symptom":symptom,
        "email":email,
        "name":name,
        "phone":phone,
        "address":address,
        "gender":gender
    }

    const response = await fetch(url, {
        method: 'POST',
        headers: new Headers({
            'Content-Type': 'application/json'
        }),
        body: JSON.stringify(req)
    });
    if(response.status < 300){
        var result = await response.json();
        window.location.reload()
    }
}


function checkInput(name,email,phone,gender,address,speciality,doctor,chooseDate,symptom){
    if(name == "" || name == null){
        alert("name not blank"); return;
    }
    if(email == "" || email == null){
        alert("email not blank"); return;
    }
    if(phone == "" || phone == null){
        alert("phone not blank"); return;
    }
    if(address == "" || address == null){
        alert("address not blank"); return;
    }
    if(speciality == "" || speciality == null){
        alert("speciality not blank"); return;
    }
    if(chooseDate == "" || chooseDate == null){
        alert("chooseDate not blank"); return;
    }


}


async function logout(){
    var url = 'http://localhost:8080/api/v1/authentication/logout';
    const response = await fetch(url, {
        method: 'POST',
        headers: new Headers({
        })
    });
    if (response.status < 300){
        window.location.href = '../login'
    }
}


async function saveServiceToAppointment(){
    var idapp = document.getElementById("idapp").value;
    var listIdSev = $("#services").val();
    console.log(listIdSev)
    if(listIdSev.length < 1){
        alert("Chọn tối thiểu 1 dịch vụ")
    }
    var url = 'http://localhost:8080/api/v1/diagnosisDetail/doctor/create?idAppointment='+idapp;
    const response = await fetch(url, {
        method: 'POST',
        headers: new Headers({
            'Content-Type': 'application/json'
        }),
        body: JSON.stringify(listIdSev)
    });
    if(response.status < 300){
        window.location.reload()
    }
}


function hideDsdv(){
    $("#chitietdv").modal('hide');
}

var ctkList = [];
var kqkList = [];
var idBenhAn = -1;
var idApps = -1;
async function updateApp(){
    if(ctkList < 1){
        alert("Không có dịch vụ nào");
        return;
    }
    var lists = [];
    for(i=0; i<ctkList.length; i++){
        var ct = {
            "id":ctkList[i].id,
            "detail":document.getElementById(ctkList[i].name).value,
            "result":document.getElementById(kqkList[i].name).value
        }
        lists.push(ct);
    }
    console.log(lists);
    var url = 'http://localhost:8080/api/v1/diagnosisDetail/doctor/update';
    const res = await fetch(url, {
        method: 'POST',
        headers: new Headers({
            'Content-Type': 'application/json'
        }),
        body: JSON.stringify(lists)
    });

    var benhAn = {
        "id":idBenhAn,
        "appointment":{
            "id":idApps
        },
        "detail":document.getElementById("ghichu").value,
        "result":tinyMCE.get('editor').getContent()
    }
    url = 'http://localhost:8080/api/v1/diagnosis/public/save';
    const resp = await fetch(url, {
        method: 'POST',
        headers: new Headers({
            'Content-Type': 'application/json'
        }),
        body: JSON.stringify(benhAn)
    });
    if(resp.status < 300){
        alert("Thành công!")
        window.location.reload();
    }
}


function formatmoney(money) {
    var VND = new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND',
    });
    return VND.format(money);
}
