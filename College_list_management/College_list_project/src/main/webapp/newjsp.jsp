<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
  <meta charset="utf-8"/>
  <title>College List</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body class="bg-light">
  <div class="container py-4">
    <h1 class="mb-12">Colleges</h1>
    <div class="row">
      <div class="col-md-12">
        <table class="table table-striped" id="college-table">
          <thead><tr><th>ID</th><th>Code</th><th>Name</th><th>District</th></tr></thead>
          <tbody></tbody>
        </table>
      </div>
      
    </div>
  </div>

<script>
function loadList(){
  $.getJSON(window.location.pathname.replace(/\/[^/]*$/, '') + '/api/colleges', function(data){
    const tbody = $('#college-table tbody').empty();
    data.forEach((c, idx) => {
      const row = $('<tr>');
      const idxCell = $('<td>').html('<a href="#" class="idx-link" data-code="'+c.code+'">'+(idx+1)+'</a>');
      row.append(idxCell);
      row.append($('<td>').text(c.code));
      row.append($('<td>').text(c.name));
      tbody.append(row);
    });
  });
}

function loadDetails(code){
  $.getJSON(window.location.pathname.replace(/\/[^/]*$/, '') + '/api/college', {id: code}, function(c){
    $('#c-name').text(c.name);
    $('#c-code').text('Code: ' + c.code);
    $('#c-district').text('District: ' + c.district);
    $('#c-address').text('Full: ' + c.name);
  });
}

$(document).on('click', '.idx-link', function(e){
  e.preventDefault();
  const code = $(this).data('code');
  loadDetails(code);
});

$(function(){ loadList(); });
</script>
</body>
</html>
