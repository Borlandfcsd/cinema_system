var movieSessionApp = new Vue({
    el: '#movieSessionApp',
    data: {
        duration: ""
    },
    methods:{
        refreshDuration:function(){
            var duration = $('option:selected', '#movie-list').attr('data-duration');
            var h = duration / 60 | 0;
            var m = duration % 60 | 0;
            this.duration = h + 'h. ' + m + " min."
        },
        setDate:function (e) {
            var date = e.currentTarget.getAttribute("data-date");
            $('#begin-date').val(date);
        }
    }

});