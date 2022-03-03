$('#search-form').on("submit", function() {
    $.get('/api/query?' + $('#search-form').serialize(), function( data ) {
       console.log(data)
    });
    return false;
})
$(function() {
    $("#search").autocomplete({
        source : function(request, response) {
            $.ajax({
                url : "/api/autocomplete",
                dataType : "json",
                data : {
                    input : request.term
                },
                success : function(data) {
                    response(data);
                }
            });
        },
        minLength : 2
    });
});
