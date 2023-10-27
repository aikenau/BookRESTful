class XMLHandler {

    mimeType = 'application/xml';

    getFormData(formId) {

        let formData = $(formId).serializeArray();
        let xmlDoc = new DOMParser().parseFromString('<?xml version="1.0" encoding="UTF-8"?><book></book>', 'application/xml');
        let root = xmlDoc.documentElement;

        for (let i = 0; i < formData.length; i++) {
            let el = xmlDoc.createElement(formData[i].name);
            el.textContent = formData[i].value;
            root.appendChild(el);
        }

        return new XMLSerializer().serializeToString(xmlDoc);
    }

    getBookObjectFromResponse(response) {
        let xmlData = response.documentElement;
        let bookId = xmlData.querySelector('id').textContent;
        let title = xmlData.querySelector('title').textContent;
        let author = xmlData.querySelector('author').textContent;
        let date = xmlData.querySelector('date').textContent;
        let genres = xmlData.querySelector('genres').textContent;
        let characters = xmlData.querySelector('characters').textContent;
        let synopsis = xmlData.querySelector('synopsis').textContent;
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
        $(response).find('book').each(function () {
            let book = {
                id: $(this).find('id').text(),
                title: $(this).find('title').text(),
                author: $(this).find('author').text(),
                date: $(this).find('date').text(),
                genres: $(this).find('genres').text(),
                characters: $(this).find('characters').text(),
                synopsis: $(this).find('synopsis').text()
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
        });
    }

    formattedBookID(bookId) {
        console.log('delete id is ' + bookId);
        return '<?xml version="1.0" encoding="UTF-8"?><id>' + bookId + '</id>';
    }

}