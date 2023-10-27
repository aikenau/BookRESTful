<!DOCTYPE html>
<html>

<head>
	<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.4/dist/jquery.min.js"></script>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet"
		integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">

	<title></title>

</head>

<body>
	<h1>Welcome to Book Library</h1>

	<!-- Insert Modal Start Here -->
	<div class="modal fade" id="InsertBookModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
		aria-labelledby="staticBackdropLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h1 class="modal-title fs-5" id="staticBackdropLabel">Add a new Book</h1>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<form action="./insert" class="mb-3" method="POST" id="InsertForm">

						<label>ID</label>
						<input type="text" class="form-control" name="id" id="InsertFormId" /><br>

						<label>Title</label>
						<input type="text" class="form-control" name="title" id="InsertFormTitle" /><br>

						<label>Author</label>
						<input type="text" class="form-control" name="author" id="InsertFormAuthor" /><br>

						<label>Date</label>
						<input type="date" class="form-control" name="date" id="InsertFormDate" /><br>

						<label>Genres</label>
						<input type="text" class="form-control" name="genres" id="InsertFormGenres" /><br>

						<label>Characters</label>
						<input type="text" class="form-control" name="characters" id="InsertFormCharacters" /><br>

						<label>Synopsis</label>
						<textarea class="form-control" name="synopsis" id="InsertFormSynopsis" rows="4" cols="50">
		</textarea>
						<br>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary" id="insertBtn">Submit</button>
					<div class="spinner-border text-primary d-none" id="InsertSpinner" role="status">
					</div>
				</div>

			</div>
		</div>
	</div>
	<!--  Insert Modal Ended Here-->

	<!--  Update Modal Start Here-->
	<div class="modal fade" id="UpdateBook" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
		aria-labelledby="updateBackdropLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h1 class="modal-title fs-5" id="updateBackdropLabel">Update Book</h1>
					<button type="button" class="btn-close" data-bs-toggle="modal" data-bs-target="#UpdateBook"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<form action="./update" class="mb-3" method="POST" id="UpdateForm">

						<label>ID</label>
						<input type="text" class="form-control bg-light text-muted" name="idSO" id="idShowOnly" readonly
							disabled /><br>
						<input type="hidden" name="id" id="UpdateFormId" />

						<label>Title</label>
						<input type="text" class="form-control" name="title" id="UpdateFormTitle" /><br>

						<label>Author</label>
						<input type="text" class="form-control" name="author" id="UpdateFormAuthor" /><br>

						<label>Date</label>
						<input type="date" class="form-control" name="date" id="UpdateFormDate" /><br>

						<label>Genres</label>
						<input type="text" class="form-control" name="genres" id="UpdateFormGenres" /><br>

						<label>Characters</label>
						<input type="text" class="form-control" name="characters" id="UpdateFormCharacters" /><br>

						<label>Synopsis</label>
						<textarea class="form-control" name="synopsis" id="UpdateFormSynopsis" rows="4"
							cols="50"></textarea>
						<br>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-bs-toggle="modal"
						data-bs-target="#UpdateBook">Close</button>
					<button type="button" class="btn btn-primary" id="updateBtn">Update</button>
					<div class="spinner-border text-primary d-none" id="UpdateSpinner" role="status">
					</div>
				</div>
			</div>
		</div>
	</div>

	<!--  Update Modal Ended -->

	<div class="row justify-content-end">
		<div class="col-lg-2 col-md-4 col-sm-12 text-end">
			<button class="btn btn-success btn-lg" data-bs-toggle="modal" data-bs-target="#InsertBookModal">Add New
				Book</button>
		</div>
		<div class="col-lg-2 col-md-4 col-sm-12">
			<div class="btn-group" role="group" aria-label="Basic radio toggle button group">
				<input type="radio" class="btn-check" name="btnradio" id="btnradio1" autocomplete="off"
					data-format="json" checked>
				<label class="btn btn-outline-primary" for="btnradio1">JSON</label>

				<input type="radio" class="btn-check" name="btnradio" id="btnradio2" autocomplete="off"
					data-format="xml">
				<label class="btn btn-outline-primary" for="btnradio2">XML</label>

				<input type="radio" class="btn-check" name="btnradio" id="btnradio3" autocomplete="off"
					data-format="txt">
				<label class="btn btn-outline-primary" for="btnradio3">TXT</label>
			</div>
		</div>
		<div class="col-lg-2 col-md-4 col-sm-12">
			<div class="input-group mb-6">
				<input type="text" class="form-control" aria-label="Text input with dropdown button" id="searchText"
					placeholder="Search in synopsis...">
				<button class="btn btn-outline-secondary" type="button" id="searchBtn">Go</button>
			</div>
		</div>
		<div class="col-lg-2 col-md-4 col-sm-12">

			<div class="spinner-border" style="width: 3rem; height: 3rem;" role="status" id="FetchSpinner">
			</div>

		</div>
	</div>

	<table class="table table-striped" id="bookTable">
		<thead>
			<tr>
				<th>ID</th>
				<th>Title</th>
				<th>Author</th>
				<th>Date</th>
				<th>Genres</th>
				<th>Characters</th>
				<th>Synopsis</th>
			</tr>
		</thead>
		<tbody class="table-group-divider">
		</tbody>
	</table>

	<script src="TXTHandler.js"></script>
	<script src="JSONHandler.js"></script>
	<script src="XMLHandler.js"></script>
	<script src="mainScripts.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.7/dist/umd/popper.min.js"
		integrity="sha384-zYPOMqeu1DAVkHiLqWBUTcbYfZ8osu1Nd6Z89ify25QV9guujx43ITvfi12/QExE" crossorigin="anonymous">
	</script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.min.js"
		integrity="sha384-Y4oOpwW3duJdCWv5ly8SCFYWqFDsfob/3GkgExXKV4idmbt98QcxXYs9UoXAB7BZ" crossorigin="anonymous">
	</script>

</body>

</html>