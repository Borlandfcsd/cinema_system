var reserveApp = new Vue({
    el: '#reserveApp',
    data: {
        hide:true,
        seen: false,
        tickets:[],
        price:0.0

    },
    methods: {
        checkTickets:function (e) {
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
                        "email":"",
                        "row": row,
                        "place": place,
                        "price":price
                });
            }
            this.seen = this.tickets.length > 0;
            this.price = this.tickets.length * price;
        },
        restPost:function () {
            var prefix = "/sessionPage/reserveTickets";
            var email = document.getElementById("email").value;
            var sessionID =  document.getElementById("session_id").textContent;

            this.tickets.forEach(function(item, i, arr) {
                item.email = email;
            });
            var json = {
                "sessionID":sessionID,
                "tickets":this.tickets
            };
            var jsonString = JSON.stringify(json);
            console.log(jsonString);

            $.ajax({
                type: 'POST',
                url:  prefix,
                data: jsonString,
                contentType: "application/json; charset=utf-8",
                dataType: 'json',
                processData:false,
                mimeType: 'application/json',
                success: function(data) {
                    alert(data.message)
                    location.reload();
                }
            })
        }
    }
});
