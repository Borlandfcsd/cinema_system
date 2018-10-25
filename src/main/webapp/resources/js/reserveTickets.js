var reserveApp = new Vue({
    el: '#reserveApp',
    data: {
        hide:true,
        seen: false,
        tickets:[],
        price:0.0

    },
    methods: {
        selectTickets:function (e) {
            $(e.currentTarget).toggleClass("CHECKED");
            var row = e.currentTarget.getAttribute("data-row");
            var place = e.currentTarget.getAttribute("data-place");
            var price = e.currentTarget.getAttribute("data-price");
            var add = true;
            for(var i=0; i< this.tickets.length; i++) {
                if (this.tickets[i].row === row
                    && this.tickets[i].place === place) {
                    this.tickets.splice(i, 1);
                    add = false;
                }
            }
            if(add){
                this.tickets.push({
                    "email": {
                        "email": ""
                    },
                        "row": row,
                        "place": place,
                        "price":price
                });
            }
            this.seen = this.tickets.length > 0;
            this.price = this.tickets.length * price;
        },
        restPost: function (prefix) {
            var email = document.getElementById("email").value;
            var sessionID =  document.getElementById("session_id").textContent;

            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");

            this.tickets.forEach(function(item, i, arr) {
                item.email.email = email;
            });
            
            var json = {
                "sessionID":sessionID,
                "tickets":this.tickets
            };
            
            var jsonString = JSON.stringify(json);
            console.log(jsonString);
            
            $.ajaxSetup({
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header,token)
                }
            });
            
            $.ajax({
                type: 'POST',
                url:  prefix,
                data: jsonString,
                contentType: "application/json; charset=utf-8",
                dataType: 'json',
                processData:false,
                mimeType: 'application/json',
                success: function(data) {
                    alert(data.message);
                    location.href = "http://localhost:8080/sessionPage/" + sessionID;
                },
                failure:function () {
                    alert('You should Sign In for order a ticket');
                    location.href = "http://localhost:8080/signInUp"
                }
            })
        },
    }

});
