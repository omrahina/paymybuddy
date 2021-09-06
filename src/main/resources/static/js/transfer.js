$(function (){

    $("#addBtn").on('click', function() {
        $('#addConnectionModal').modal('show');
    });

    $(".modalClose").on('click', function() {
        $("#email").val('');
        $('#addConnectionModal').modal('hide');
    });

    $(".modalSubmit").on('click', function() {
        // $("#addConnectionForm").submit();
        let form = $("#addConnectionForm");
        let connection = $('#email').val();
        $.ajax({
            url: form.attr('action'),
            data: form.serialize(),
            type: "POST",
            success: function(response) {
                console.log(response);
                console.log(connection);
                $('#addSuccess').show();
                $('#selectConnection').append(`<option> ${connection} </option>`);
                $('#addConnectionModal').modal('hide');

            },
            error: function(error) {
                console.log(error.responseText);
                $('#failure').html(`<h6> ${error.responseText} </h6>`);
            }
        })

    });

    // $('#sendMoney').submit(function (event){
    //     let buddyEmail = $('#selectConnection').val();
    //     event.preventDefault();
    //     console.log(buddyEmail);
    // });

});