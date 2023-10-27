$(document).ready(function () {

    let defaultFormat = 'json';
    console.log("FRONT : The default data format is " + defaultFormat);
    fetchBooks(defaultFormat);

    $('input[name="btnradio"]').on('change', function (e) {
        let format = $(e.target).data('format');
        console.log("FRONT : format change to " + format);
        fetchBooks(format);
    });

    $('#insertBtn').on('click', function (e) {
        InsertSingleBook(e);
    });
    $('#updateBtn').on('click', function (e) {
        UpdateSingleBook(e);
    });
    $('#searchBtn').on('click', function (e) {
        GoSearch(e);
    });


});

function ReturnDataHandler(format) {
    if (format === "xml") {
        return new XMLHandler();
    } else if (format === "json") {
        return new JSONHandler();
    } else if (format === "txt") {
        return new TXTHandler();
    } else {
        console.log("FRONT - Something wrong in return data handler.");
        return new JSONHandler();
    }
}

function fetchBooks(format) {
    let url = './books-api?format=' + format;
    let dataHandler = ReturnDataHandler(format);
    $('#FetchSpinner').removeClass('d-none');

    let request = $.ajax({
        url: url,
        type: 'GET',
        contentType: dataHandler.mimeType
    });

    request.done(function (response) {
        dataHandler.constructMainTable(response);
        $('#FetchSpinner').addClass('d-none');
    });

    request.fail(function (jqXHR, textStatus, errorThrown) {
        console.log('Error fetching books');
        console.log(textStatus, errorThrown);
    });
}


function GoSearch(e) {

    e.preventDefault();
    $('#FetchSpinner').removeClass('d-none');


    let format = $('input[name="btnradio"]:checked').data('format');
    let dataHandler = ReturnDataHandler(format);
    let searchText = $("#searchText").val();
    let url = './books-api/search?searchText=' + searchText + '&format=' + format;

    let request = $.ajax({
        url: url,
        type: 'GET',
        contentType: dataHandler.mimeType
    });

    request.done(function (response) {
        dataHandler.constructMainTable(response);
        $('#FetchSpinner').addClass('d-none');
        console.log('loading success!');
    });

    request.fail(function (jqXHR, textStatus, errorThrown) {
        console.log('Error go search books');
        console.log(textStatus, errorThrown);
    });
}


function InsertSingleBook(e) {

    e.preventDefault();
    $('#InsertSpinner').removeClass('d-none');

    let format = $('input[name="btnradio"]:checked').data('format');
    let dataHandler = ReturnDataHandler(format);
    let data = dataHandler.getFormData('#InsertForm');

    console.log('Insert Form Data : ' + data);

    let request = $.ajax({
        url: './books-api',
        type: 'POST',
        data: data,
        contentType: dataHandler.mimeType
    });

    request.done(function (response) {
        console.log('FRONT-InsertSingleBook : Book successfully insert!');
        console.log('FRONT-InsertSingleBook : contentType ' + dataHandler.mimeType);
        $('#InsertSpinner').addClass('d-none');
        $('#InsertForm')[0].reset();
        $('#InsertBookModal').modal('hide');
        dataHandler.responseToInsertTable(response);
    });

    request.fail(function (jqXHR, textStatus, errorThrown) {
        $('#InsertSpinner').addClass('d-none');
        console.log('Error submitting the form');
        console.log(textStatus, errorThrown);
    });

}

function UpdateSingleBook(e) {

    e.preventDefault();
    $('#UpdateSpinner').removeClass('d-none');

    let format = $('input[name="btnradio"]:checked').data('format');
    let dataHandler = ReturnDataHandler(format);
    let data = dataHandler.getFormData('#UpdateForm');

    console.log('Update Form Data : ' + data);

    let request = $.ajax({
        url: './books-api',
        type: 'PUT',
        data: data,
        contentType: dataHandler.mimeType
    });

    request.done(function (response) {

        console.log('FRONT-UpdateSingleBook : update single book function updated success!');
        console.log('FRONT-UpdateSingleBook : contentType : ' + dataHandler.mimeType);

        $('#UpdateSpinner').addClass('d-none');
        $('#UpdateBook').modal('hide');

        dataHandler.responseToUpdateTable(response);
    });

    request.fail(function (jqXHR, textStatus, errorThrown) {

        $('#UpdateSpinner').addClass('d-none');

        console.log('Error updating the form');
        console.log(textStatus, errorThrown);
    });
}

function deleteSingleBook(bookId) {
    if (!confirm('Are you sure you want to delete this book?')) {
        return;
    }

    let format = $('input[name="btnradio"]:checked').data('format');
    let dataHandler = ReturnDataHandler(format);

    let request = $.ajax({
        url: './books-api',
        type: 'DELETE',
        data: dataHandler.formattedBookID(bookId),
        contentType: dataHandler.mimeType
    });

    request.done(function () {
        console.log('FRONT-deleteSingleBook : Delete single book successfully!');
        console.log('FRONT-deleteSingleBook : contentType : ' + dataHandler.mimeType);
        $('tr[data-book-id="' + bookId + '"]').remove();
    });

    request.fail(function (jqXHR, textStatus, errorThrown) {
        console.log('Error deleting the book');
        console.log(textStatus, errorThrown);
    });
}


function openUpdateModal(bookId) {
    let format = $('input[name="btnradio"]:checked').data('format');
    let dataHandler = ReturnDataHandler(format);

    let request = $.ajax({
        url: './books-api/single',
        type: 'GET',
        data: {id: bookId, format: format},
        contentType: dataHandler.mimeType
    });

    request.done(function (response) {
        console.log("FRONT-openUpdateModal : open modal window for update.");
        console.log("FRONT-openUpdateModal : contentType :" + dataHandler.mimeType);
        dataHandler.prepareForUpdateForm(response);
        $('#UpdateBook').modal('show');
    });

    request.fail(function (jqXHR, textStatus, errorThrown) {
        console.log('Error fetching book details');
        console.log(textStatus, errorThrown);
    });
}

