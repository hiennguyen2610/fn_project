async function loadAllSpecialy() {
    var url = 'http://localhost:8080/api/v1/speciality/admin/find-all';
    const response = await fetch(url, {
        method: 'GET',
        headers: new Headers({
        })
    });
    var list = await response.json();
    var main = '';
    for (i = 0; i < list.length; i++) {
        main += ` <option value="${list[i].id}">${list[i].name}</option>`
    }
    document.getElementById("listSpecialy").innerHTML = main
}