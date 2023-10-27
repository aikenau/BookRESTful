class JSONHandler {

    mimeType = 'application/json';

    getFormData(formId) {
        let formData = $(formId).serializeArray();
        let jsonData = {};

        formData.forEach(function (item) {
            jsonData[item.name] = item.value;
        });

        return JSON.stringify(jsonData);
    }


    getBookObjectFromResponse(response) {
        let bookId = response.id;
        let title = response.title;
        let author = response.author;
        let date = response.date;
        let genres = response.genres;
        let characters = response.characters;
        let synopsis = response.synopsis;

        return {
            bookId: bookId,
            title: title,
            author: author,
            date: date,
            genres: genres,
            characters: characters,
            synopsis: synopsis
        }
    }

    responseToInsertTable(response) {
        let book = this.getBookObjectFromResponse(response);
        let rowHtml = "<tr data-book-id='" + book.bookId + "'>" +
            "<td>" + book.bookId + "</td>" +
            "<td class='title'>" + book.title + "</td>" +
            "<td class='author'>" + book.author + "</td>" +
            "<td class='date'>" + book.date + "</td>" +
            "<td class='genres'>" + book.genres + "</td>" +
            "<td class='characters'>" + book.characters + "</td>" +
            "<td class='synopsis'>" + book.synopsis + "</td>" +
            "<td><button class='btn btn-primary' onclick='openUpdateModal(" + book.bookId + ")'>Update</button></td>" +
            "<td><button class='btn btn-danger' onclick='deleteSingleBook(" + book.bookId + ")' id='deleteBtn'>Delete</button></td>" +
            "</tr>";
        $('#bookTable tbody').prepend(rowHtml);
    }


    responseToUpdateTable(response) {

        let book = this.getBookObjectFromResponse(response);

        let tableRow = $('tr[data-book-id="' + book.bookId + '"]');
        tableRow.find('.title').text(book.title);
        tableRow.find('.author').text(book.author);
        tableRow.find('.date').text(book.date);
        tableRow.find('.genres').text(book.genres);
        tableRow.find('.characters').text(book.characters);
        tableRow.find('.synopsis').text(book.synopsis);
    }

    prepareForUpdateForm(response) {

        let book = this.getBookObjectFromResponse(response);

        $('#idShowOnly').val(book.bookId);
        $('#UpdateFormId').val(book.bookId);
        $('#UpdateFormTitle').val(book.title);
        $('#UpdateFormAuthor').val(book.author);
        $('#UpdateFormDate').val(book.date);
        $('#UpdateFormGenres').val(book.genres);
        $('#UpdateFormCharacters').val(book.characters);
        $('#UpdateFormSynopsis').val(book.synopsis);
    }

    constructMainTable(response) {
        let tbody = $("#bookTable tbody");
        tbody.empty();
        console.log('FRONT-constructMainTable with mimeType ' + this.mimeType);
        $.each(response, function (index, book) {
            let rowHtml = "<tr data-book-id='" + book.id + "'>" +
                "<td>" + book.id + "</td>" +
                "<td class='title'>" + book.title + "</td>" +
                "<td class='author'>" + book.author + "</td>" +
                "<td class='date'>" + book.date + "</td>" +
                "<td class='genres'>" + book.genres + "</td>" +
                "<td class='characters'>" + book.characters + "</td>" +
                "<td class='synopsis'>" + book.synopsis + "</td>" +
                "<td><button class='btn btn-primary' onclick='openUpdateModal(" + book.id + ")'>Update</button></td>" +
                "<td><button class='btn btn-danger' onclick='deleteSingleBook(" + book.id + ")' id='deleteBtn'>Delete</button></td>" +
                "</tr>";
            tbody.append(rowHtml);
        });
    }


    formattedBookID(bookId) {
        return JSON.stringify({id: bookId});
    }


}