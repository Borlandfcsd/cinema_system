var addSessionApp = new Vue({
    el: '#addSessionApp',
    data: {
        duration: ""
    },
    methods:{
        refreshDuration:function(){
            var duration = $('option:selected', '#movie-list').attr('data-duration');
            var h = duration / 60 | 0;
            var m = duration % 60 | 0;
            this.duration = h + 'h. ' + m + " min."
        }
    }

});