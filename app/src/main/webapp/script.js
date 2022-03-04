$('#search-form').on("submit", function() {
    $.get('/api/query?' + $('#search-form').serialize(), function( data ) {
     console.log(data)
       var tmpl = $.templates("#result-entry");
       const results = $("#result");
       results.empty()
       for (const element of data) {
            console.log(element)
            var data = {
               name: element.accessor.displayName,
               line: element.line
            };
            results.append(tmpl.render(data))
       }
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
