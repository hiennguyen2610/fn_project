// Cấu hình thông tin Firebase của bạn
let firebaseConfig = {
    apiKey: "AIzaSyCPfxKN6sz8b7ndgoENeNqZ3FnwVjKTCZM",
    projectId: "hospital-management-syst-24909",
    storageBucket: "hospital-management-syst-24909.appspot.com",
};

// Khởi tạo Firebase
firebase.initializeApp(firebaseConfig);

// Lấy tham chiếu đến Firebase Storage bucket
let storage = firebase.storage();
let storageRef = storage.ref();


$(document).ready(function () {

    // Tìm kiếm category
    $("#search-category").keyup(function () {
        let searchText = $(this).val().toLowerCase();
        $("#categoryList .show-genres").each(function () {
            let categoryText = $(this).find(".text-15").text().toLowerCase();
            if (categoryText.includes(searchText)) {
                $(this).show();
            } else {
                $(this).hide();
            }
        });
    });

    //open chosen image form
    $('.btn-upload-image').click(() => {
        $('#fileInput').click();
    });

    // show image
    let chosenFile = null;
    $('#fileInput').change(event => {
        const tempFiles = event.target.files;
        if (!tempFiles || tempFiles.length === 0) {
            return;
        }
        chosenFile = tempFiles[0];
        const imageBlob = new Blob([chosenFile], {type: chosenFile.type});
        const imageUrl = URL.createObjectURL(imageBlob);
        $('#image-book .btn-upload-image').empty();
        let showImageHtml = `<img alt='Avatar' style="width: auto; height: 100%; object-fit: cover" src='${imageUrl}'/>`;
        $('#image-book .btn-upload-image').append(showImageHtml)
    });


    function uploadImageAndCreateBook() {
        let title = $("#title").val();
        let author = $("#author").val();
        let published = $("#published").val();
        let about = $("#about").val();

        let selectedCategories = [];
        $(":checkbox[name^='category-']:checked").each(function () {
            let categoryId = $(this).attr("id");
            selectedCategories.push(categoryId);
        });

        // Create the Book object (newBook) based on the CreateBookRequest structure
        let newBook = {
            image: "",
            title: title,
            categoryId: selectedCategories,
            author: author,
            description: about,
            published: published,
        };

        // Upload the image first
        if (chosenFile != null) {
            let imageName = chosenFile.name;

            // Tạo tham chiếu đến file trên Firebase Storage
            let imageRef = storageRef.child("images/" + imageName);

            // Tải lên ảnh lên Firebase Storage
            let uploadTask = imageRef.put(chosenFile);

            // Trả về một Promise để xử lý khi tải lên ảnh thành công
            return uploadTask.then(snapshot => {
                // Lấy URL download của ảnh
                return snapshot.ref.getDownloadURL();
            }).then(downloadURL => {
                // Sau khi lấy URL, gán vào newBook.image
                newBook.image = downloadURL;

                console.log(newBook)
                // Gửi yêu cầu POST để tạo sách với URL ảnh đã tải lên
                return $.ajax({
                    type: "POST",
                    url: "/api/v1/admin/book",
                    contentType: "application/json",
                    data: JSON.stringify(newBook),
                });
            }).then(response => {
                toastr.success("Create book success!");
                window.location.reload();
            }).catch(error => {
                toastr.warning("Create book not success!");
            });
        } else {
            // Create the book without the image URL
            $.ajax({
                type: "POST",
                url: "/api/v1/admin/book",
                contentType: "application/json",
                data: JSON.stringify(newBook),
                success: function (response) {
                    toastr.success("Create book success!");
                    window.location.reload();
                },
                error: function (error) {
                    toastr.warning("Create book not success!");
                }
            });
        }

    }

    $.validator.addMethod("pastDate", function (value, element) {
        // Lấy ngày hiện tại
        var currentDate = new Date();

        // Chuyển đổi giá trị ngày nhập vào sang đối tượng Date
        var inputDate = new Date(value);

        // So sánh ngày nhập vào với ngày hiện tại
        return inputDate < currentDate;
    }, "date must be the past date");

    $("#create-book-form").validate({
        onfocusout: false,
        onkeyup: false,
        onclick: false,
        errorPlacement: function (error, element) {
            error.addClass("error-message");
            error.insertAfter(element);
        },
        rules: {
            "title": {
                required: true
            },
            "author": {
                required: true
            },
            "published": {
                required: true,
                pastDate: true
            }
        },
        messages: {
            "title": {
                required: "*Enter title"
            },
            "author": {
                required: "*Enter author"
            },
            "published": {
                required: "*Enter published date",
                pastDate: "*Published date must be the past date"
            }
        },
        invalidHandler: function (form, validator) {
            // Tìm trường đầu tiên có lỗi
            let errors = validator.numberOfInvalids();
            if (errors) {
                let firstErrorElement = $(validator.errorList[0].element);
                // Cuộn trình duyệt đến trường đầu tiên có lỗi
                $('html, body').animate({
                    scrollTop: firstErrorElement.offset().top - 200 // Điều chỉnh vị trí cuộn để hiển thị tooltip không bị che khuất
                }, 500);
                firstErrorElement.focus(); // Đưa con trỏ vào trường đầu tiên có lỗi
            }
        }
    });

// Call the uploadImageAndCreateBook function when a form submit button is clicked
    $('#submitBtn').click(event => {
        event.preventDefault();
        let isValidForm = $("#create-book-form").valid();
        if (!isValidForm) return;
        uploadImageAndCreateBook();
    })

// $('.custom_shadow').on('keyup', function (event) {
//     if (event.which === 13 || event.keyCode === 13) {
//         event.preventDefault();
//         $('#submitBtn').click();
//     }
// })

})