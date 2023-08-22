async function loadAllPatients(page) {
    var param = document.getElementById("param").value

    var url = 'http://localhost:8080/api/v1/patient/public/findAllHasDiagnosi?page='+page+"&size=3&param="+param;
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
        var dob = list[i].dob == null ? "": list[i].dob
        main += `<tr>
                    <td>${list[i].id}</td>
                    <td>${list[i].name}</td>
                    <td>${dob}</td>
                    <td>${list[i].phone}</td>
                    <td>${list[i].address}</td>
                    <td>${list[i].email}</td>
                    <td class="text-right">
                        <i onclick="loadCTBenhAn(${list[i].id}, '${list[i].name}','${list[i].phone}','${list[i].dob}','${list[i].address}')" class="fa fa-eye pointer"></i>
                    </td>
                </tr>`
    }
    document.getElementById("listPat").innerHTML = main
    var mainpage = ''
    for(k=1; k<= totalPage; k++){
        mainpage += '<li onclick="loadAllPatients('+(Number(k)-1)+')" class="page-item"><a class="page-link">'+k+'</a></li>'
    }
    document.getElementById("listpage").innerHTML = mainpage
}


async function loadCTBenhAn(patientId, hoTen, sdt, ngSinh, DiaChi){
    if(ngSinh == null){
        ngSinh = ""
    }
    document.getElementById("hoTen").innerHTML = hoTen
    document.getElementById("ngsinh").innerHTML = ngSinh
    document.getElementById("sdt").innerHTML = sdt
    document.getElementById("diachi").innerHTML = DiaChi
    $("#chitietbenhan").modal('show')
    var urls = 'http://localhost:8080/api/v1/diagnosisDetail/public/find-by-patient?idPatient='+patientId;
    const res = await fetch(urls, {
        method: 'GET',
        headers: new Headers({
        })
    });
    var list = await res.json();
    var main = ''
    for(i=0; i<list.length; i++){
        main += `<tr>
                    <td>${list[i].service.name}</td>          
                    <td>${list[i].detail}</td>          
                    <td>${list[i].result}</td>          
                  </tr>`
    }
    document.getElementById("listctdv").innerHTML = main

    urls = 'http://localhost:8080/api/v1/diagnosis/public/findByPatient?idPatient='+patientId;
    const resp = await fetch(urls, {
        method: 'GET',
        headers: new Headers({
        })
    });
    var objs = await resp.json();
    document.getElementById("motangan").innerHTML = objs.detail
    document.getElementById("ketqua").innerHTML = objs.result
}

function hideCtBenhAn(){
    $("#chitietbenhan").modal('hide')
}


function printBenhAn(){
    var divToPrint=document.getElementById('ctbenhan');

    var newWin=window.open('patients','Print-Window');

    newWin.document.open();

    newWin.document.write(`<html>
    <head>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    </head>
    <body onload="window.print()">
    <h3 style="text-align: center; margin-bottom: 50px">HỒ SƠ BỆNH ÁN</h3>
    ${divToPrint.innerHTML}
    </body></html>`);
    newWin.document.close();

    setTimeout(function(){newWin.close();},10);
}
