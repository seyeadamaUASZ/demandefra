var myExtObject = (function () {
    var r = document.querySelector(':root');

    return {
        func1: function () {
            var rs = getComputedStyle(r);
            // Alert the value of the --blue variable
            //alert("The value of --colorPrimary is: " + rs.getPropertyValue('--colorPrimary'));
        },
        func2: function (colorPrimary,colorSecondary) {
            r.style.setProperty('--colorPrimary', colorPrimary);
            r.style.setProperty('--colorSecondary', colorSecondary);

        }
    }

})(myExtObject || {})


var webGlObject = (function () {
    return {
        init: function () {
            alert('webGlObject initialized');
        }
    }
})(webGlObject || {})



