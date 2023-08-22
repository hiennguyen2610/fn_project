
async function loadAllService(page) {
    var url = 'http://localhost:8080/api/v1/service/public/all?size=3&page='+page;
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
        main += `<tr>
                    <td>${list[i].id}</td>
                    <td>${list[i].name}</td>
                    <td>${formatmoney(list[i].price)}</td>
                    <td class="text-right">
                        <div class="d-flex justify-content-between">
                            <a class="btn btn-link p-0 submit-update-btn" onclick="loadChitiet(${list[i].id},'${list[i].name}', ${list[i].price})">
                                <i class="fa-solid fa-pencil fa-lg text-primary"></i>
                            </a>
                            <button onclick="deleteService(${list[i].id})" class="btn btn-link p-0 mr-2 submit-delete-btn">
                                <span><i class="fa-solid fa-trash-can fa-lg text-danger"></i></span>
                            </button>
                        </div>
                    </td>
                </tr>`
    }
    document.getElementById("listservie").innerHTML = main
    var mainpage = ''
    for(i=1; i<= totalPage; i++){
        mainpage += '<li onclick="loadAllService('+(Number(i)-1)+')" class="page-item"><a class="page-link">'+i+'</a></li>'
    }
    document.getElementById("listpage").innerHTML = mainpage
}

function loadChitiet(id, name, price){
    $("#update-service-modal").modal("show");
    document.getElementById("idservice").value = id
    document.getElementById("name").value = name
    document.getElementById("price").value = price
}

async function deleteService(id){
    Swal.fire({
        title: 'Bạn có chắc muốn xóa dịch vụ này không?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                url: '/api/v1/service/admin/delete?id=' + id,
                type: 'DELETE',
                success: function (response) {
                    Swal.fire({
                        title: 'Xóa dịch vụ thành công',
                        icon: 'success',
                        showConfirmButton: false,
                        timer: 1500
                    }).then((result) => {
                        if (result.dismiss === Swal.DismissReason.timer) {
                            window.location.href = 'http://localhost:8080/admin/service';
                        }
                    });
                },
                error: function (xhr, status, error) {
                    console.log(error);
                    Swal.fire({
                        title: 'Đã xảy ra lỗi khi xóa dịch vụ',
                        icon: 'error'
                    });
                }
            });
        }
    });
}

function formatmoney(money) {
    var VND = new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND',
    });
    return VND.format(money);
}