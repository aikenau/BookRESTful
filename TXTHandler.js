class TXTHandler {

    mimeType = 'text/plain';

    getFormData(formId) {

        let id = $(formId + 'Id').val();
        let title = $(formId + 'Title').val();
        let author = $(formId + 'Author').val();
        let date = $(formId + 'Date').val();
        let genres = $(formId + 'Genres').val();
        let characters = $(formId + 'Characters').val();
        let synopsis = $(formId + 'Synopsis').val();

        let myData = id + '#' + title + '#' + author + '#' + date + '#' + genres + '#' + characters + '#' + synopsis;
        return myData;
    }


    getBookObjectFromResponse(response) {
        let responseData = response.split('#');
        let bookId = responseData[0];
        let title = responseData[1];
        let author = responseData[2];
        let date = responseData[3];
        let genres = responseData[4];
        let characters = responseData[5];
        let synopsis = responseData[6];
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
        let lines = response.split('\n');
        lines.forEach(function (line) {
            if (line.trim() != "") {
                let bookData = line.split('#');
                let book = {
                    id: bookData[0],
                    title: bookData[1],
                    author: bookData[2],
                    date: bookData[3],
                    genres: bookData[4],
                    characters: bookData[5],
                    synopsis: bookData[6]
                };

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
            }
        });
    }

    formattedBookID(bookId) {
        return bookId.toString();
    }

}