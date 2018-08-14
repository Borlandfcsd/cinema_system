Vue.component('date-field',{
    data:function () {
        return{

        }
    },
    template:'<input type="datetime-local"/>'
});


var createSessionApp = new Vue({
    el:"#createSessionApp",
    data:{
    },
    computed:{
        getDate:function () {
            var date = new Date();
                date.setSeconds(0);
                date.setMilliseconds(0);
            var iso = date.toISOString();
            return iso.substring(0,iso.length-8);
        }
    }
});