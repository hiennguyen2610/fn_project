var UNPAID = 'UNPAID'
var PAID = 'PAID'

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

    for (i = 0; i < list.length; i++) {
        var st = `<div class="dropdown dropdown-action">
                    <a href="#" class="action-icon dropdown-toggle" data-toggle="dropdown" aria-expanded="false"><i class="fa fa-ellipsis-v"></i></a>
                    <div class="dropdown-menu dropdown-menu-right">`;
        var ngs = list[i].patient.dob;
        if(list[i].patient.dob == null){
            ngs = ''
        }
        var paystatus = ''
        var checs = '';
        var num = 0;
        if(list[i].paymentStatus == PAID){
            paystatus = `<p style="color: green;font-weight: bold">${list[i].paymentStatus}</p>`
            checs = 'checked'
            num = 1;
        }if(list[i].paymentStatus == UNPAID){
            paystatus = `<p style="color: red">${list[i].paymentStatus}</p>`
        }
        var checkbox = `<label class="checkbox-custom">Confirm
                            <input onchange="confirmPay(${list[i].id}, ${num}, this)" ${checs} type="checkbox">
                            <span class="checkmark-checkbox"></span>
                        </label>`
        if(list[i].amount == 0){
            checkbox = ''
        }
        main += `<tr>
                    <td>${list[i].id}</td>
                    <td>${list[i].patient.name}</td>
                    <td>${ngs}</td>
                    <td>${list[i].doctor.user.name}</td>
                    <td>${list[i].speciality.name}</td>
                    <td>${list[i].appointmentDate}</td>
                    <td>${list[i].appointmentTime}</td>
                    <td>${list[i].appointmentStatus}</td>
                    <td>${paystatus}</td>
                    <td>${formatmoney(list[i].amount)}</td>
                    <td>${formatmoney(list[i].paidAmount)}</td>
                    <td class="text-right">
                        ${checkbox}
                        <i onclick="loadDsDv(${list[i].id})" class="fa fa-eye pointer"></i>
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

async function confirmPay(id, status,e) {
    var str = 'Xác nhận thanh toán?'
    if(status == 1){
        str = 'Xác nhận hủy thanh toán?'
    }
    var con = confirm(str);
    if(con){
        var urls = 'http://localhost:8080/api/v1/appointment/public/confirm-pay?idapp='+id;
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
    else{
        if (status == 1){
            e.checked = true;
        }
        else{
            e.checked = false;
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
    var amount = 0;
    for(i=0; i<list.length; i++){
        main += ` <tr>
                        <td>${list[i].service.id}</td>
                        <td>${list[i].service.name}</td>
                        <td>${formatmoney(list[i].service.price)}</td>
                   </tr>`
        amount = Number(amount) + Number(list[i].service.price);
    }
    main += `<tr>
                <td></td><td></td>
                <td style="color: red;font-weight: bold">${formatmoney(amount)}</td>
           </tr>`
    document.getElementById("listdvtvb").innerHTML = main
}

function printInvoice(){
    var divToPrint=document.getElementById('tables');

    var newWin=window.open('','Print-Window');

    newWin.document.open();

    newWin.document.write(`<html>
     <head>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    </head>
    <body onload="window.print()">
        <h3 style="text-align: center; margin-bottom: 50px">HÓA ĐƠN KHÁM BỆNH</h3>
        ${divToPrint.innerHTML}
    </body>
    </html>`);

    newWin.document.close();

    setTimeout(function(){newWin.close();},10);

}

function formatmoney(money) {
    var VND = new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND',
    });
    return VND.format(money);
}